package assignmentImplementation;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import keyValueBaseExceptions.BeginGreaterThanEndException;
import keyValueBaseExceptions.KeyNotFoundException;
import keyValueBaseExceptions.ServiceAlreadyInitializedException;
import keyValueBaseExceptions.ServiceInitializingException;
import keyValueBaseExceptions.ServiceNotInitializedException;
import keyValueBaseInterfaces.Pair;
import keyValueBaseInterfaces.Predicate;
import keyValueBaseInterfaces.TimestampLog;

@WebService
@SOAPBinding(style = Style.RPC)
abstract public class KeyValueBaseReplicaService {
	protected KeyValueBaseSlaveImpl lort;
	
	abstract protected KeyValueBaseReplicaImpl kv();

	@WebMethod
	public void dummy(LengthPredicate p) { }

    @WebMethod
    public void init(String serverFilename)
            throws ServiceAlreadyInitializedException,
            ServiceInitializingException, FileNotFoundException {
    	kv().init(serverFilename);
    }

    @WebMethod
    public Pair<TimestampLog, ValueListImpl> read(KeyImpl k) throws KeyNotFoundException,
            IOException, ServiceNotInitializedException {
    	return kv().read(k);
    }

    @WebMethod
    public Pair<TimestampLog, ValueListImpl[]> scan(KeyImpl begin, KeyImpl end,
            Predicate<ValueListImpl> p) throws IOException,
            BeginGreaterThanEndException, ServiceNotInitializedException {
        Pair<TimestampLog, List<ValueListImpl>> pair = kv().scan(begin, end, p);
        return new Pair<>(pair.getKey(), pair.getValue().toArray(new ValueListImpl[]{}));
    }

    
    @WebMethod
    public Pair<TimestampLog, ValueListImpl[]> atomicScan(KeyImpl begin, KeyImpl end,
            Predicate<ValueListImpl> p) throws IOException,
            BeginGreaterThanEndException, ServiceNotInitializedException {
        Pair<TimestampLog, List<ValueListImpl>> pair = kv().atomicScan(begin, end, p);
        return new Pair<>(pair.getKey(), pair.getValue().toArray(new ValueListImpl[]{}));
    }
}