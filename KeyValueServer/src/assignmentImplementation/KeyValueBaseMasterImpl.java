package assignmentImplementation;

import java.io.IOException;
import java.util.List;

import keyValueBaseExceptions.KeyAlreadyPresentException;
import keyValueBaseExceptions.KeyNotFoundException;
import keyValueBaseExceptions.ServiceAlreadyConfiguredException;
import keyValueBaseExceptions.ServiceNotInitializedException;
import keyValueBaseInterfaces.Configuration;
import keyValueBaseInterfaces.KeyValueBaseMaster;
import keyValueBaseInterfaces.Pair;

public class KeyValueBaseMasterImpl extends KeyValueBaseReplicaImpl implements KeyValueBaseMaster<KeyImpl,ValueListImpl> {

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
	public void bulkPut(List<Pair<KeyImpl, ValueListImpl>> mappings)
			throws IOException, ServiceNotInitializedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void config(Configuration conf)
			throws ServiceAlreadyConfiguredException {
		// TODO Auto-generated method stub
		
	}

}
