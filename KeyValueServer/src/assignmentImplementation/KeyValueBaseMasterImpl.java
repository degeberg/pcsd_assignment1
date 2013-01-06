package assignmentImplementation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import keyValueBaseExceptions.KeyAlreadyPresentException;
import keyValueBaseExceptions.KeyNotFoundException;
import keyValueBaseExceptions.ServiceAlreadyConfiguredException;
import keyValueBaseExceptions.ServiceAlreadyInitializedException;
import keyValueBaseExceptions.ServiceInitializingException;
import keyValueBaseExceptions.ServiceNotInitializedException;
import keyValueBaseInterfaces.Configuration;
import keyValueBaseInterfaces.KeyValueBaseMaster;
import keyValueBaseInterfaces.LogRecord;
import keyValueBaseInterfaces.Pair;
import keyValueBaseInterfaces.TimestampLog;
import clientClasses.KeyValueBaseSlaveServiceService;

public class KeyValueBaseMasterImpl extends KeyValueBaseReplicaImpl implements KeyValueBaseMaster<KeyImpl,ValueListImpl> {
    private ReplicatorImpl replicator;
    
    public KeyValueBaseMasterImpl(String storePath) {
        super(storePath);
        replicator = new ReplicatorImpl();
        replicator.start();
    }

    @Override
    public void init(String serverFilename)
            throws ServiceAlreadyInitializedException,
            ServiceInitializingException, FileNotFoundException {
        super.init(serverFilename);
        System.out.println("Master init");
        LogRecord record = CreateRecord("init", new String[]{serverFilename});
        replicate(record);      
    }
    
    synchronized private void replicate(LogRecord record) {
		try {
		    synchronized (lastLSN) {
		        if (record.getLSN().after(lastLSN)) {
        		    lastLSN = record.getLSN();
		        }
		    }
            replicator.makeStable(record).get();
            System.out.println("Successfully replicated " + record.getMethodName());
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
	public void insert(KeyImpl k, ValueListImpl v)
			throws KeyAlreadyPresentException, IOException,
			ServiceNotInitializedException {
        kv.insert(k, v);
		LogRecord record = CreateRecord("insert", new Object[]{k, v});
		replicate(record);
	}

	@Override
	public void update(KeyImpl k, ValueListImpl newV)
			throws KeyNotFoundException, IOException,
			ServiceNotInitializedException {
		kv.update(k, newV);
		LogRecord record = CreateRecord( "update", new Object[]{k, newV});
		replicate(record);
	}

	@Override
	public void delete(KeyImpl k) throws KeyNotFoundException,
			ServiceNotInitializedException {
		kv.delete(k);
		LogRecord record = CreateRecord( "delete", new Object[]{k});
		replicate(record);
	}

	@Override
	public void bulkPut(List<Pair<KeyImpl, ValueListImpl>> mappings)
			throws IOException, ServiceNotInitializedException {
	    kv.bulkPut(mappings);
		LogRecord record = CreateRecord( "bulkPut", new Object[]{mappings});
		replicate(record);
	}

	@Override
	public void config(Configuration conf)
			throws ServiceAlreadyConfiguredException {
	    ArrayList<KeyValueBaseSlaveServiceService> slaves = new ArrayList<>();
	    try {
        	for (String slaveUrl : conf.slaves) {
                slaves.add(new KeyValueBaseSlaveServiceService(new URL(slaveUrl)));
        	}
        	replicator.setSlaves(slaves);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	private LogRecord CreateRecord(String name, Object[] params) {
        return new LogRecord(kv.getClass(), name, params);
	}

}
