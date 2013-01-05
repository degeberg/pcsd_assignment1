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
import clientClasses.FileNotFoundException_Exception;
import clientClasses.IOException_Exception;
import clientClasses.KeyNotFoundException_Exception;
import clientClasses.KeyValueBaseMasterServiceService;
import clientClasses.KeyValueBaseSlaveServiceService;
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
	
	private static clientClasses.KeyImpl makeKey(KeyImpl k) {
	    clientClasses.KeyImpl k2 = new clientClasses.KeyImpl();
	    k2.setKey(k.getKey());
	    return k2;
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
                    pair = slave.read(makeKey(k));
    	        } catch (IOException_Exception e) {
    	            slaves.remove(n);
    	            throw new IOException();
    	        }
    	    } else {
    	        try {
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
	    
	}

	@Override
	public void update(KeyImpl k, ValueListImpl newV)
			throws KeyNotFoundException, IOException,
			ServiceNotInitializedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(KeyImpl k) throws KeyNotFoundException,
			ServiceNotInitializedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ValueListImpl> scan(KeyImpl begin, KeyImpl end,
			Predicate<ValueListImpl> p) throws IOException,
			BeginGreaterThanEndException, ServiceNotInitializedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValueListImpl> atomicScan(KeyImpl begin, KeyImpl end,
			Predicate<ValueListImpl> p) throws IOException,
			BeginGreaterThanEndException, ServiceNotInitializedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void bulkPut(List<Pair<KeyImpl, ValueListImpl>> mappings)
			throws IOException, ServiceNotInitializedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void config(Configuration conf)
			throws ServiceAlreadyConfiguredException {
        try {
            for (String slaveUrl : conf.slaves) {
                slaves.add(new KeyValueBaseSlaveServiceService(new URL(slaveUrl)));
            }
            master = new KeyValueBaseMasterServiceService(new URL(conf.master));
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

}
