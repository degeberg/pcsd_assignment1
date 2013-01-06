package assignmentImplementation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import keyValueBaseExceptions.BeginGreaterThanEndException;
import keyValueBaseExceptions.KeyAlreadyPresentException;
import keyValueBaseExceptions.KeyNotFoundException;
import keyValueBaseExceptions.ServiceAlreadyConfiguredException;
import keyValueBaseExceptions.ServiceAlreadyInitializedException;
import keyValueBaseExceptions.ServiceInitializingException;
import keyValueBaseExceptions.ServiceNotInitializedException;
import keyValueBaseInterfaces.Configuration;
import keyValueBaseInterfaces.KeyValueBaseProxy;
import keyValueBaseInterfaces.Pair;
import keyValueBaseInterfaces.Predicate;
import keyValueBaseInterfaces.TimestampLog;
import clientClasses.BeginGreaterThanEndException_Exception;
import clientClasses.FileNotFoundException_Exception;
import clientClasses.IOException_Exception;
import clientClasses.KeyAlreadyPresentException_Exception;
import clientClasses.KeyNotFoundException_Exception;
import clientClasses.KeyValueBaseMasterServiceService;
import clientClasses.KeyValueBaseSlaveServiceService;
import clientClasses.ServiceAlreadyConfiguredException_Exception;
import clientClasses.ServiceAlreadyInitializedException_Exception;
import clientClasses.ServiceInitializingException_Exception;
import clientClasses.ServiceNotInitializedException_Exception;

public class KeyValueBaseProxyImpl implements KeyValueBaseProxy<KeyImpl,ValueListImpl> {
    
    private TimestampLog lastLSN;
    ArrayList<KeyValueBaseSlaveServiceService> slaves = new ArrayList<>();
    KeyValueBaseMasterServiceService master;
    private Random r;
    
    public KeyValueBaseProxyImpl() {
        r = new Random();
    }
    
	@Override
	public void init(String serverFilename)
			throws ServiceAlreadyInitializedException,
			ServiceInitializingException, FileNotFoundException {
	    try {
            master.getKeyValueBaseMasterServicePort().init(serverFilename);
        } catch (FileNotFoundException_Exception e) {
            throw new FileNotFoundException();
        } catch (ServiceAlreadyInitializedException_Exception e) {
            throw new ServiceAlreadyInitializedException();
        } catch (ServiceInitializingException_Exception e) {
            throw new ServiceInitializingException();
        }
	}

	@Override
	public ValueListImpl read(KeyImpl k) throws KeyNotFoundException,
			IOException, ServiceNotInitializedException {
	    int n = r.nextInt(slaves.size() + 1);
	    
	    clientClasses.Pair pair;
	    
	    try {
    	    if (n < slaves.size()) {
    	        clientClasses.KeyValueBaseSlaveService slave = slaves.get(n).getKeyValueBaseSlaveServicePort();
    	        try {
    	            System.out.println("Read from slave " + n);
                    pair = slave.read(makeKey(k));
    	        } catch (IOException_Exception e) {
    	            slaves.remove(n);
    	            throw new IOException();
    	        }
    	    } else {
    	        try {
    	            System.out.println("Read from master");
        	        pair = master.getKeyValueBaseMasterServicePort().read(makeKey(k));
    	        } catch (IOException_Exception e) {
    	            throw new IOException();
    	        }
    	    }
	    }
	    catch (KeyNotFoundException_Exception e) {
	        throw new KeyNotFoundException(k);
        } catch (ServiceNotInitializedException_Exception e) {
            throw new ServiceNotInitializedException();
        }
	    
	    clientClasses.TimestampLog fakeLog = (clientClasses.TimestampLog) pair.getK();
	    TimestampLog log = new TimestampLog(fakeLog.getInd());
	    
	    if (lastLSN.after(log)) {
	        return read(k);
	    }
	    
	    clientClasses.ValueListImpl fakeVl = (clientClasses.ValueListImpl) pair.getV();
	    ValueListImpl vl = new ValueListImpl();
	    for (clientClasses.ValueImpl fakeV : fakeVl.getElements()) {
	        ValueImpl v = new ValueImpl();
	        v.setValue(fakeV.getValue());
	        vl.add(v);
	    }
	    
	    return vl;
	}

	@Override
	public void insert(KeyImpl k, ValueListImpl v)
			throws KeyAlreadyPresentException, IOException,
			ServiceNotInitializedException {
	    clientClasses.KeyImpl k2 = makeKey(k);
	    clientClasses.ValueListImpl vl2 = makeVL(v);
	    try {
            master.getKeyValueBaseMasterServicePort().insert(k2, vl2);
        } catch (IOException_Exception e) {
            throw new IOException();
        } catch (KeyAlreadyPresentException_Exception e) {
            throw new KeyAlreadyPresentException(k);
        } catch (ServiceNotInitializedException_Exception e) {
            throw new ServiceNotInitializedException();
        }
	}

	@Override
	public void update(KeyImpl k, ValueListImpl v)
			throws KeyNotFoundException, IOException,
			ServiceNotInitializedException
	{
        clientClasses.KeyImpl k2 = makeKey(k);
        clientClasses.ValueListImpl vl2 = makeVL(v);	
        try {
            master.getKeyValueBaseMasterServicePort().update(k2, vl2);
        } catch (IOException_Exception e) {
            throw new IOException();
        } catch (KeyNotFoundException_Exception e) {
            throw new KeyNotFoundException(k);
        } catch (ServiceNotInitializedException_Exception e) {
            throw new ServiceNotInitializedException();
        }
	}

	@Override
	public void delete(KeyImpl k) throws KeyNotFoundException,
			ServiceNotInitializedException
	{
        clientClasses.KeyImpl k2 = makeKey(k);
        try {
            master.getKeyValueBaseMasterServicePort().delete(k2);
        } catch (KeyNotFoundException_Exception e) {
            throw new KeyNotFoundException(k);
        } catch (ServiceNotInitializedException_Exception e) {
            throw new ServiceNotInitializedException();
        }
	}

	@Override
	public List<ValueListImpl> scan(KeyImpl begin, KeyImpl end,
			Predicate<ValueListImpl> p) throws IOException,
			BeginGreaterThanEndException, ServiceNotInitializedException
	{
	    clientClasses.KeyImpl kb = makeKey(begin);
	    clientClasses.KeyImpl ke = makeKey(end);
	    LengthPredicate lp = (LengthPredicate)p;
	    clientClasses.LengthPredicate lp2 = new clientClasses.LengthPredicate();
	    lp2.setLength(lp.getLength());
	    clientClasses.Pair pa;
	    try {
            pa = master.getKeyValueBaseMasterServicePort().scan(kb, ke, lp2);
        } catch (BeginGreaterThanEndException_Exception e) {
            throw new BeginGreaterThanEndException();
        } catch (IOException_Exception e) {
            throw new IOException();
        } catch (ServiceNotInitializedException_Exception e) {
            throw new ServiceNotInitializedException();
        }
	    clientClasses.TimestampLog tsl = (clientClasses.TimestampLog)pa.getK();
	    clientClasses.ValueListImpl[] vll = (clientClasses.ValueListImpl[])pa.getV();
	    TimestampLog tsl2 = new TimestampLog(tsl.getInd());
	    
	    if (tsl2.before(lastLSN)) {
	        return scan(begin, end, p);
	    }
	    
	    ArrayList<ValueListImpl> al = new ArrayList<ValueListImpl>();
	    for (clientClasses.ValueListImpl vllort : vll) {
	        al.add(getVL(vllort));
	    }
	    
	    return al;
	}

	@Override
	public List<ValueListImpl> atomicScan(KeyImpl begin, KeyImpl end,
			Predicate<ValueListImpl> p) throws IOException,
			BeginGreaterThanEndException, ServiceNotInitializedException
	{
        clientClasses.KeyImpl kb = makeKey(begin);
        clientClasses.KeyImpl ke = makeKey(end);
        LengthPredicate lp = (LengthPredicate)p;
        clientClasses.LengthPredicate lp2 = new clientClasses.LengthPredicate();
        lp2.setLength(lp.getLength());
        clientClasses.Pair pa;
        try {
            pa = master.getKeyValueBaseMasterServicePort().atomicScan(kb, ke, lp2);
        } catch (BeginGreaterThanEndException_Exception e) {
            throw new BeginGreaterThanEndException();
        } catch (IOException_Exception e) {
            throw new IOException();
        } catch (ServiceNotInitializedException_Exception e) {
            throw new ServiceNotInitializedException();
        }
        clientClasses.TimestampLog tsl = (clientClasses.TimestampLog)pa.getK();
        clientClasses.ValueListImpl[] vll = (clientClasses.ValueListImpl[])pa.getV();
        TimestampLog tsl2 = new TimestampLog(tsl.getInd());
        
        if (tsl2.before(lastLSN)) {
            return scan(begin, end, p);
        }
        
        ArrayList<ValueListImpl> al = new ArrayList<ValueListImpl>();
        for (clientClasses.ValueListImpl vllort : vll) {
            al.add(getVL(vllort));
        }
        
        return al;
	}

	@Override
	public void bulkPut(List<Pair<KeyImpl, ValueListImpl>> mappings)
			throws IOException, ServiceNotInitializedException
	{
	    clientClasses.BulkList bl = new clientClasses.BulkList();
	    for (Pair<KeyImpl, ValueListImpl> p : mappings) {
	        bl.getKeys().add(makeKey(p.getKey()));
	        bl.getVals().add(makeVL(p.getValue()));
	    }
	    try {
            master.getKeyValueBaseMasterServicePort().bulkPut(bl);
        } catch (IOException_Exception e) {
            throw new IOException();
        } catch (ServiceNotInitializedException_Exception e) {
            throw new ServiceNotInitializedException();
        }
	}

	@Override
	public void config(Configuration conf)
			throws ServiceAlreadyConfiguredException {
	    if (master != null) {
	        throw new ServiceAlreadyConfiguredException();
	    }
	    
        try {
            for (String slaveUrl : conf.slaves) {
                slaves.add(new KeyValueBaseSlaveServiceService(new URL(slaveUrl)));
            }
            master = new KeyValueBaseMasterServiceService(new URL(conf.master));
            
            clientClasses.Configuration fakeConf = new clientClasses.Configuration();
			fakeConf.setMaster(conf.master);
			for (String slave : conf.slaves) {
			    fakeConf.getSlaves().add(slave);
			}
            
            try {
                master.getKeyValueBaseMasterServicePort().config(fakeConf);
            } catch (ServiceAlreadyConfiguredException_Exception e) {
                throw new ServiceAlreadyConfiguredException();
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

    // Private shit!
    private static clientClasses.KeyImpl makeKey(KeyImpl k) {
        clientClasses.KeyImpl k2 = new clientClasses.KeyImpl();
        k2.setKey(k.getKey());
        return k2;
    }
    
    private static clientClasses.ValueImpl makeV(ValueImpl v) {
        clientClasses.ValueImpl v2 = new clientClasses.ValueImpl();
        v2.setValue(v.getValue());
        return v2;
    }
    
    private static clientClasses.ValueListImpl makeVL(ValueListImpl vl) {
        clientClasses.ValueListImpl vl2 = new clientClasses.ValueListImpl();
        for (ValueImpl v : vl.toList()) {
            vl2.getElements().add(makeV(v));
        }
        return vl2;
    }
    
    private static ValueListImpl getVL(clientClasses.ValueListImpl vl) {
        ValueListImpl vl2 = new ValueListImpl();
        for (clientClasses.ValueImpl v : vl.getElements()) {
            ValueImpl v2 = new ValueImpl();
            v2.setValue(v.getValue());
            vl2.add(v2);
        }
        return vl2;
    }
}
