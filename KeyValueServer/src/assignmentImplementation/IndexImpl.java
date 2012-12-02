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
    private long nextAvailable; // TODO: Find better way of allocating space
    
    public IndexImpl() throws IndexOutOfBoundsException, IOException {
        store = new StoreImpl();
        positions = new TreeMap<KeyImpl, Pair<Long, Integer>>();
        nextAvailable = 0;
    }

    @Override
    synchronized public void insert(KeyImpl k, ValueListImpl v)
            throws KeyAlreadyPresentException, IOException {
        if (positions.containsKey(k)) {
            throw new KeyAlreadyPresentException(k);
        }
        byte[] s = serialize(v);
        positions.put(k, new Pair<Long, Integer>(nextAvailable, s.length));
        store.write(nextAvailable, s);
        nextAvailable += s.length;
    }

    @Override
    synchronized public void remove(KeyImpl k) throws KeyNotFoundException {
        if (!positions.containsKey(k)) {
            throw new KeyNotFoundException(k);
        }
        positions.remove(k);
    }

    @Override
    synchronized public ValueListImpl get(KeyImpl k) throws KeyNotFoundException,
            IOException {
        Pair<Long, Integer> p = positions.get(k);
        ValueListImpl res = (ValueListImpl) deserialize(store.read(p.getKey(), p.getValue()));
        return res;
    }

    @Override
    synchronized public void update(KeyImpl k, ValueListImpl v) throws KeyNotFoundException,
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
        
        for (KeyImpl k : positions.keySet()) {
            if (k.compareTo(begin) < 0)
                continue;
            if (k.compareTo(end) > 0) 
                break;
            
            try {
                list.add(this.get(k));
            } catch (KeyNotFoundException e) {
                // won't happen
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
    synchronized public void bulkPut(List<Pair<KeyImpl, ValueListImpl>> keys)
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