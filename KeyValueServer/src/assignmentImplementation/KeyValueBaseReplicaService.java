package assignmentImplementation;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
    public SingleReadPair read(KeyImpl k) throws KeyNotFoundException,
            IOException, ServiceNotInitializedException {
        SingleReadPair p = new SingleReadPair();
        Pair<TimestampLog, ValueListImpl> p2 = kv().read(k);
        p.setLSN(p2.getKey());
        p.setVL(p2.getValue());
    	return p;
    }

    @WebMethod
    public ArrayReadPair scan(KeyImpl begin, KeyImpl end,
            Predicate<ValueListImpl> p) throws IOException,
            BeginGreaterThanEndException, ServiceNotInitializedException {
        Pair<TimestampLog, List<ValueListImpl>> pair = kv().scan(begin, end, p);
        ArrayReadPair pa = new ArrayReadPair();
        pa.setLSN(pair.getKey());
        pa.setVL((ArrayList<ValueListImpl>)pair.getValue());
        return pa;
    }
    
    @WebMethod
    public ArrayReadPair atomicScan(KeyImpl begin, KeyImpl end,
            Predicate<ValueListImpl> p) throws IOException,
            BeginGreaterThanEndException, ServiceNotInitializedException {
        Pair<TimestampLog, List<ValueListImpl>> pair = kv().atomicScan(begin, end, p);
        ArrayReadPair pa = new ArrayReadPair();
        pa.setLSN(pair.getKey());
        pa.setVL((ArrayList<ValueListImpl>)pair.getValue());
        return pa;
    }
}