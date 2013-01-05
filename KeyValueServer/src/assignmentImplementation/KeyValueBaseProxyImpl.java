package assignmentImplementation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

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

public class KeyValueBaseProxyImpl implements KeyValueBaseProxy<KeyImpl,ValueListImpl> {
    
    private TimestampLog lastLSN;

	@Override
	public void init(String serverFilename)
			throws ServiceAlreadyInitializedException,
			ServiceInitializingException, FileNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ValueListImpl read(KeyImpl k) throws KeyNotFoundException,
			IOException, ServiceNotInitializedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(KeyImpl k, ValueListImpl v)
			throws KeyAlreadyPresentException, IOException,
			ServiceNotInitializedException {
		// TODO Auto-generated method stub
		
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
		
	}

}
