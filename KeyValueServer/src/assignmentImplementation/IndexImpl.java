package assignmentImplementation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
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
    
    public IndexImpl() throws IndexOutOfBoundsException, IOException {
        store = new StoreImpl();
        positions = new TreeMap<KeyImpl, Pair<Long, Integer>>();
        blocks = new ArrayList<Pair<Long, Long>>();
        blocks.add(new Pair<Long, Long>(0L, store.getTotalSize()));
    }

    @Override
    public void insert(KeyImpl k, ValueListImpl v)
            throws KeyAlreadyPresentException, IOException {
        if (positions.containsKey(k)) {
            throw new KeyAlreadyPresentException(k);
        }
        byte[] s = serialize(v);
        long pos = -1;
        for (int i = 0; i < blocks.size(); ++i) {
            Pair<Long, Long> p = blocks.get(i);
            if (p.getValue() >= s.length) {
                pos = p.getKey();
                if (p.getValue() == s.length)
                    blocks.remove(i);
                else
                    blocks.set(i, new Pair<Long, Long>(pos + s.length, p.getValue() - s.length));
            }
        }
        if (pos < 0)
            throw new IOException("No available space in mmap file");
        positions.put(k, new Pair<Long, Integer>(pos, s.length));
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
        Pair<Long, Long> newblock = new Pair<Long, Long>(p.getKey(), (long)p.getValue());
        // Check if i extends some free block. This should be at position i-1
        if (i > 0) {
            Pair<Long, Long> prev = blocks.get(i-1);
            if (prev.getKey() + prev.getValue() == p.getKey()) {
                blocks.set(i-1, new Pair<Long, Long>(prev.getKey(), prev.getValue() + p.getValue()));
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
                blocks.set(i, new Pair<Long, Long>(p2.getKey(), p2.getValue() + nextb.getValue()));
                blocks.remove(i+1);
            }
        }
        
        positions.remove(k);
    }

    @Override
    public ValueListImpl get(KeyImpl k) throws KeyNotFoundException,
            IOException {
        Pair<Long, Integer> p = positions.get(k);
        if (p == null) {
            System.out.println("" + positions.size());
            System.out.println("" + blocks.size());
        }
        ValueListImpl res = (ValueListImpl) deserialize(store.read(p.getKey(), p.getValue()));
        return res;
    }

    @Override
    public void update(KeyImpl k, ValueListImpl v) throws KeyNotFoundException,
            IOException {
        remove(k);
        try {
            insert(k, v);
        } catch (KeyAlreadyPresentException e) {
            // cannot happen
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
        for (Pair<KeyImpl, ValueListImpl> p : keys) {
            if (positions.containsKey(p.getKey())) {
                try {
                    this.update(p.getKey(), p.getValue());
                } catch (KeyNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                try {
                    this.insert(p.getKey(), p.getValue());
                } catch (KeyAlreadyPresentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    
    private Object deserialize(byte[] buf) throws IOException {
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buf));
        try {
            return in.readObject();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } finally {
            in.close();
        }
    }
    
    private byte[] serialize(Serializable o) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(b);
        try {
            out.writeObject(o);
        } finally {
            out.close();
        }
        return b.toByteArray();
    }

}