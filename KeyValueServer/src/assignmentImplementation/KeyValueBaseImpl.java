package assignmentImplementation;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import keyValueBaseExceptions.BeginGreaterThanEndException;
import keyValueBaseExceptions.KeyAlreadyPresentException;
import keyValueBaseExceptions.KeyNotFoundException;
import keyValueBaseExceptions.ServiceAlreadyInitializedException;
import keyValueBaseExceptions.ServiceInitializingException;
import keyValueBaseExceptions.ServiceNotInitializedException;
import keyValueBaseInterfaces.KeyValueBase;
import keyValueBaseInterfaces.Pair;
import keyValueBaseInterfaces.Predicate;

public class KeyValueBaseImpl implements KeyValueBase<KeyImpl, ValueListImpl> {
    private boolean initialized;
    private IndexImpl index;

    public KeyValueBaseImpl(IndexImpl index) {
        initialized = false;
        this.index = index;
    }

    @Override
    public void init(String serverFilename)
            throws ServiceAlreadyInitializedException,
            ServiceInitializingException {
        if (initialized) {
            throw new ServiceAlreadyInitializedException();
        }

        Scanner s = null;
        BufferedReader b = null;

        try {
        	b = new BufferedReader(new FileReader(serverFilename));
        	String str;

            while ((str = b.readLine()) != null) {
            	s = new Scanner(str);
            	Integer key = s.nextInt();
            	ValueListImpl vl = new ValueListImpl();
            	System.out.print(key);
            	while (s.hasNextInt())
            	{
            		Integer x = s.nextInt();
            		System.out.print(" ");
            		System.out.print(x);
            		vl.add(new ValueImpl(x));
            	}
            	System.out.println();
                s.close();
            }
        } catch (Exception e) {
        	e.printStackTrace();
            throw new ServiceInitializingException("something is fucked up");
        } finally {
            if (s != null) {
                s.close();
            }
            if (b != null) {
            	try {
					b.close();
				} catch (IOException e) {
					throw new ServiceInitializingException("Error while closing IO stream.");
				}
            }
        }

        initialized = true;
    }

    @Override
    public ValueListImpl read(KeyImpl k) throws KeyNotFoundException,
            IOException, ServiceNotInitializedException {
        if (!initialized) {
            throw new ServiceNotInitializedException();
        }

        return index.get(k);
    }

    @Override
    public void insert(KeyImpl k, ValueListImpl v)
            throws KeyAlreadyPresentException, IOException,
            ServiceNotInitializedException {
        if (!initialized) {
            throw new ServiceNotInitializedException();
        }

        index.insert(k, v);
    }

    @Override
    public void update(KeyImpl k, ValueListImpl newV)
            throws KeyNotFoundException, IOException,
            ServiceNotInitializedException {
        if (!initialized) {
            throw new ServiceNotInitializedException();
        }

        index.update(k, newV);
    }

    @Override
    public void delete(KeyImpl k) throws KeyNotFoundException,
            ServiceNotInitializedException {
        if (!initialized) {
            throw new ServiceNotInitializedException();
        }

        index.remove(k);
    }

    @Override
    public List<ValueListImpl> scan(KeyImpl begin, KeyImpl end,
            Predicate<ValueListImpl> p) throws IOException,
            BeginGreaterThanEndException, ServiceNotInitializedException {
        if (!initialized) {
            throw new ServiceNotInitializedException();
        }

        ArrayList<ValueListImpl> list = new ArrayList<ValueListImpl>();
        for (ValueListImpl v : index.scan(begin, end)) {
            if (p.evaluate(v)) {
                list.add(v);
            }
        }
        return list;
    }

    @Override
    public List<ValueListImpl> atomicScan(KeyImpl begin, KeyImpl end,
            Predicate<ValueListImpl> p) throws IOException,
            BeginGreaterThanEndException, ServiceNotInitializedException {
        if (!initialized) {
            throw new ServiceNotInitializedException();
        }
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void bulkPut(List<Pair<KeyImpl, ValueListImpl>> mappings)
            throws IOException, ServiceNotInitializedException {
        if (!initialized) {
            throw new ServiceNotInitializedException();
        }
        
        index.bulkPut(mappings);
    }

}
