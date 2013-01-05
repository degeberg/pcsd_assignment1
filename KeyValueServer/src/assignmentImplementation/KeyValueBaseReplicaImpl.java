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
    
    public KeyValueBaseReplicaImpl() {
        try {
            IndexImpl index = new IndexImpl();
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
		kv.init(serverFilename);
	}

	@Override
	public Pair<TimestampLog, ValueListImpl> read(KeyImpl k)
			throws KeyNotFoundException, IOException,
			ServiceNotInitializedException {
	    return new Pair<>(lastLSN, kv.read(k));
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
