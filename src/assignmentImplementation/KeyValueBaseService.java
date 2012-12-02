package assignmentImplementation;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import keyValueBaseExceptions.BeginGreaterThanEndException;
import keyValueBaseExceptions.KeyAlreadyPresentException;
import keyValueBaseExceptions.KeyNotFoundException;
import keyValueBaseExceptions.ServiceAlreadyInitializedException;
import keyValueBaseExceptions.ServiceInitializingException;
import keyValueBaseExceptions.ServiceNotInitializedException;
import keyValueBaseInterfaces.Pair;
import keyValueBaseInterfaces.Predicate;
import assignmentImplementation.IndexImpl;
import assignmentImplementation.KeyImpl;
import assignmentImplementation.KeyValueBaseImpl;
import assignmentImplementation.ValueListImpl;

@WebService
@SOAPBinding(style = Style.RPC)
public class KeyValueBaseService {
	private KeyValueBaseImpl lort;

	
    public KeyValueBaseService(IndexImpl index) {
    	lort = new KeyValueBaseImpl(index);
    }

    @WebMethod
    public void init(String serverFilename)
            throws ServiceAlreadyInitializedException,
            ServiceInitializingException, FileNotFoundException {
    	lort.init(serverFilename);
    }

    @WebMethod
    public ValueListImpl read(KeyImpl k) throws KeyNotFoundException,
            IOException, ServiceNotInitializedException {
    	return lort.read(k);
    }

    
    @WebMethod
    public void insert(KeyImpl k, ValueListImpl v)
            throws KeyAlreadyPresentException, IOException,
            ServiceNotInitializedException {
    	lort.insert(k, v);
    }

    
    @WebMethod
    public void update(KeyImpl k, ValueListImpl newV)
            throws KeyNotFoundException, IOException,
            ServiceNotInitializedException {
    	lort.update(k, newV);
    }

    
    @WebMethod
    public void delete(KeyImpl k) throws KeyNotFoundException,
            ServiceNotInitializedException {
    	lort.delete(k);
    }

    
    @WebMethod
    public ArrayList<ValueListImpl> scan(KeyImpl begin, KeyImpl end,
            Predicate<ValueListImpl> p) throws IOException,
            BeginGreaterThanEndException, ServiceNotInitializedException {
    	return (ArrayList<ValueListImpl>)lort.scan(begin, end, p);
    }

    
    @WebMethod
    public ArrayList<ValueListImpl> atomicScan(KeyImpl begin, KeyImpl end,
            Predicate<ValueListImpl> p) throws IOException,
            BeginGreaterThanEndException, ServiceNotInitializedException {
    	return (ArrayList<ValueListImpl>)lort.atomicScan(begin, end, p);
    }

    
    @WebMethod
    public void bulkPut(ArrayList<Pair<KeyImpl, ValueListImpl>> mappings)
            throws IOException, ServiceNotInitializedException {
    	lort.bulkPut(mappings);
    }
}
