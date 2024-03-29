package assignmentImplementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import keyValueBaseExceptions.BeginGreaterThanEndException;
import keyValueBaseExceptions.KeyAlreadyPresentException;
import keyValueBaseExceptions.KeyNotFoundException;
import keyValueBaseInterfaces.Index;
import keyValueBaseInterfaces.Pair;

public class IndexImpl implements Index<KeyImpl,ValueListImpl>
{
    private StoreImpl store;
    private TreeMap<KeyImpl, Pair<Long, Integer>> positions; // Key -> (Position, Length)
    private ArrayList<Pair<Long, Long>> blocks;
    private LinkedList<LogEntry> transactionLog;
    private ValueSerializerImpl ser;
    
    public IndexImpl(String storePath) throws IndexOutOfBoundsException, IOException {
        store = new StoreImpl(storePath);
        positions = new TreeMap<>();
        blocks = new ArrayList<>();
        blocks.add(new Pair<>(0L, store.getTotalSize()));
        transactionLog = new LinkedList<>();
        ser = new ValueSerializerImpl();
    }

    @Override
    public void insert(KeyImpl k, ValueListImpl v)
            throws KeyAlreadyPresentException, IOException {
        if (positions.containsKey(k)) {
            throw new KeyAlreadyPresentException(k);
        }
        byte[] s = ser.toByteArray(v);
        long pos = -1;
        for (int i = 0; i < blocks.size(); ++i) {
            Pair<Long, Long> p = blocks.get(i);
            if (p.getValue() >= s.length) {
                pos = p.getKey();
                if (p.getValue() == s.length)
                    blocks.remove(i);
                else
                    blocks.set(i, new Pair<>(pos + s.length, p.getValue() - s.length));
            }
        }
        if (pos < 0)
            throw new IOException("No available space in mmap file");
        positions.put(k, new Pair<>(pos, s.length));
        store.write(pos, s);
    }

    @Override
    public void remove(KeyImpl k) throws KeyNotFoundException {
        if (!positions.containsKey(k)) {
            throw new KeyNotFoundException(k);
        }
        Pair<Long, Integer> p = positions.get(k);
        
        int i;
        for (i = 0; i < blocks.size(); ++i)
        {
            if (blocks.get(i).getKey() > p.getKey())
                break;
        }
        Pair<Long, Long> newblock = new Pair<>(p.getKey(), (long)p.getValue());
        // Check if i extends some free block. This should be at position i-1
        if (i > 0) {
            Pair<Long, Long> prev = blocks.get(i-1);
            if (prev.getKey() + prev.getValue() == p.getKey()) {
                blocks.set(i-1, new Pair<>(prev.getKey(), prev.getValue() + p.getValue()));
            }
            else
                blocks.add(i, newblock);
        }
        else
            blocks.add(0, newblock);
        if (i < blocks.size() - 1) {
            Pair<Long, Long> p2 = blocks.get(i);
            Pair<Long, Long> nextb = blocks.get(i+1);
            if (p2.getKey() + p2.getValue() == nextb.getKey()) {
                blocks.set(i, new Pair<>(p2.getKey(), p2.getValue() + nextb.getValue()));
                blocks.remove(i+1);
            }
        }
        
        positions.remove(k);
    }

    @Override
    public ValueListImpl get(KeyImpl k) throws KeyNotFoundException,
            IOException {
        Pair<Long, Integer> p = positions.get(k);
        if (p == null)
            throw new KeyNotFoundException(k);
        ValueListImpl res = ser.fromByteArray(store.read(p.getKey(), p.getValue()));
        return res;
    }

    @Override
    public void update(KeyImpl k, ValueListImpl v) throws KeyNotFoundException,
            IOException {
        transactionLog = new LinkedList<>();
        try {
            ValueListImpl old = get(k);
            remove(k);
            transactionLog.add(new LogEntry(OpType.DELETE, k, old));
            insert(k, v);
            transactionLog.add(new LogEntry(OpType.INSERT, k, old));
        } catch (Exception e) {
            rollbackTransaction();
        }
    }

    @Override
    public List<ValueListImpl> scan(KeyImpl begin, KeyImpl end)
            throws BeginGreaterThanEndException, IOException {
        if (begin.compareTo(end) > 0) {
            throw new BeginGreaterThanEndException(begin, end);
        }
        
        ArrayList<ValueListImpl> list = new ArrayList<ValueListImpl>();
        
        // Use subMap. It may be faster than just iterating through everything...
        // Unless Java is retarded. So it probably isn't :)))
        for (KeyImpl k : positions.subMap(begin, end).keySet())
        {
            try {
                list.add(this.get(k));
            } catch (KeyNotFoundException e) {
                // Will not happen
            }
        }
        
        return list;
    }

    @Override
    public List<ValueListImpl> atomicScan(KeyImpl begin, KeyImpl end)
            throws BeginGreaterThanEndException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void bulkPut(List<Pair<KeyImpl, ValueListImpl>> keys)
            throws IOException {
        
        transactionLog = new LinkedList<>();
        try {
            for (Pair<KeyImpl, ValueListImpl> p : keys) {
                if (positions.containsKey(p.getKey())) {
                    ValueListImpl old = get(p.getKey());
                    this.remove(p.getKey());
                    transactionLog.add(new LogEntry(OpType.DELETE, p.getKey(), old));
                }
                this.insert(p.getKey(), p.getValue());
                transactionLog.add(new LogEntry(OpType.INSERT, p.getKey(), null));
            }
        } catch (Exception e) {
            System.out.println("rolling back bulkPut");
            rollbackTransaction();
        }
    }
    
    /**
     * If this fails, we're just fucked...
     */
    private void rollbackTransaction() throws IOException {
        LogEntry l;
        while ((l = transactionLog.pollLast()) != null) {
            try {
                switch (l.getType()) {
                    case DELETE:
                        insert(l.getKey(), l.getValue());
                        break;
                    case INSERT:
                        remove(l.getKey());
                        break;
                }
            } catch (KeyNotFoundException|KeyAlreadyPresentException e) {
                // shouldn't happen
            }
        }
    }

    private class LogEntry {
        
        private OpType type;
        private KeyImpl key;
        private ValueListImpl value;
        
        public LogEntry(OpType type, KeyImpl key, ValueListImpl value) {
            this.type = type;
            this.key = key;
            this.value = value;
        }

        public OpType getType() {
            return type;
        }

        public ValueListImpl getValue() {
            return value;
        }
        
        public KeyImpl getKey() {
            return key;
        }
    }

    private enum OpType {
        DELETE, INSERT
    }

}