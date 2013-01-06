package assignmentImplementation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import keyValueBaseExceptions.BeginGreaterThanEndException;
import keyValueBaseExceptions.KeyNotFoundException;
import keyValueBaseExceptions.ServiceAlreadyInitializedException;
import keyValueBaseExceptions.ServiceInitializingException;
import keyValueBaseExceptions.ServiceNotInitializedException;
import keyValueBaseInterfaces.KeyValueBaseReplica;
import keyValueBaseInterfaces.Pair;
import keyValueBaseInterfaces.Predicate;
import keyValueBaseInterfaces.TimestampLog;

public class KeyValueBaseReplicaImpl implements KeyValueBaseReplica<KeyImpl, ValueListImpl> {
    protected KeyValueBaseImpl kv;
    protected TimestampLog lastLSN;
    
    public KeyValueBaseReplicaImpl(String storePath) {
        lastLSN = new TimestampLog(0L);
        try {
            IndexImpl index = new IndexImpl(storePath);
            kv = new KeyValueBaseImpl(index);
        } catch (IndexOutOfBoundsException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	@Override
	public void init(String serverFilename)
			throws ServiceAlreadyInitializedException,
			ServiceInitializingException, FileNotFoundException {
	    System.out.println("Replica init");
		kv.init(serverFilename);
	}

	@Override
	public Pair<TimestampLog, ValueListImpl> read(KeyImpl k)
			throws KeyNotFoundException, IOException,
			ServiceNotInitializedException {
	    System.out.println("replica read");
	    Pair<TimestampLog, ValueListImpl> p = new Pair<>(lastLSN, kv.read(k));
	    System.out.println("replica done read");
	    return p;
	}

	@Override
	public Pair<TimestampLog, List<ValueListImpl>> scan(KeyImpl begin,
			KeyImpl end, Predicate<ValueListImpl> p) throws IOException,
			BeginGreaterThanEndException, ServiceNotInitializedException {
	    return new Pair<>(lastLSN, kv.scan(begin, end, p));
	}

	@Override
	public Pair<TimestampLog, List<ValueListImpl>> atomicScan(KeyImpl begin,
			KeyImpl end, Predicate<ValueListImpl> p) throws IOException,
			BeginGreaterThanEndException, ServiceNotInitializedException {
	    return new Pair<>(lastLSN, kv.atomicScan(begin, end, p));
	}

}
