package assignmentImplementation;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import keyValueBaseExceptions.KeyAlreadyPresentException;
import keyValueBaseExceptions.KeyNotFoundException;
import keyValueBaseExceptions.ServiceAlreadyConfiguredException;
import keyValueBaseExceptions.ServiceNotInitializedException;
import keyValueBaseInterfaces.Configuration;
import keyValueBaseInterfaces.KeyValueBaseMaster;
import keyValueBaseInterfaces.LogRecord;
import keyValueBaseInterfaces.Pair;

public class KeyValueBaseMasterImpl extends KeyValueBaseReplicaImpl implements KeyValueBaseMaster<KeyImpl,ValueListImpl> {
    private ReplicatorImpl replicator;
    
    public KeyValueBaseMasterImpl() {
        super();
        replicator = new ReplicatorImpl();
        replicator.start();
    }
    
    private void replicate(LogRecord record) {
		try {
		    synchronized (lastLSN) {
		        if (record.getLSN().after(lastLSN)) {
        		    lastLSN = record.getLSN();
		        }
		    }
            replicator.makeStable(record).get();
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
		LogRecord record = new LogRecord(kv.getClass(), "insert", new Object[]{k, v});
		replicate(record);
	}

	@Override
	public void update(KeyImpl k, ValueListImpl newV)
			throws KeyNotFoundException, IOException,
			ServiceNotInitializedException {
		kv.update(k, newV);
		LogRecord record = new LogRecord(kv.getClass(), "update", new Object[]{k, newV});
		replicate(record);
	}

	@Override
	public void delete(KeyImpl k) throws KeyNotFoundException,
			ServiceNotInitializedException {
		kv.delete(k);
		LogRecord record = new LogRecord(kv.getClass(), "delete", new Object[]{k});
		replicate(record);
	}

	@Override
	public void bulkPut(List<Pair<KeyImpl, ValueListImpl>> mappings)
			throws IOException, ServiceNotInitializedException {
	    kv.bulkPut(mappings);
		LogRecord record = new LogRecord(kv.getClass(), "bulkPut", new Object[]{mappings});
		replicate(record);
	}

	@Override
	public void config(Configuration conf)
			throws ServiceAlreadyConfiguredException {
		// TODO Auto-generated method stub
	}

}
