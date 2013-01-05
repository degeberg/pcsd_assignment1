package assignmentImplementation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import keyValueBaseExceptions.KeyAlreadyPresentException;
import keyValueBaseExceptions.KeyNotFoundException;
import keyValueBaseExceptions.ServiceAlreadyConfiguredException;
import keyValueBaseExceptions.ServiceNotInitializedException;
import keyValueBaseInterfaces.Configuration;
import keyValueBaseInterfaces.Pair;

@WebService
@SOAPBinding(style = Style.RPC)
public class KeyValueBaseMasterService extends KeyValueBaseReplicaService {
    
    private KeyValueBaseMasterImpl kv;

    public KeyValueBaseMasterService() throws IndexOutOfBoundsException,
            IOException {
        kv = new KeyValueBaseMasterImpl();
    }

    protected KeyValueBaseMasterImpl kv() {
        return kv;
    }
    
    @WebMethod
    public void insert(KeyImpl k, ValueListImpl v)
            throws KeyAlreadyPresentException, IOException,
            ServiceNotInitializedException {
    	kv().insert(k, v);
    }

    
    @WebMethod
    public void update(KeyImpl k, ValueListImpl newV)
            throws KeyNotFoundException, IOException,
            ServiceNotInitializedException {
    	kv().update(k, newV);
    }

    
    @WebMethod
    public void delete(KeyImpl k) throws KeyNotFoundException,
            ServiceNotInitializedException {
    	kv().delete(k);
    }
    
    @WebMethod
    public void bulkPut(BulkList bl)
            throws IOException, ServiceNotInitializedException
    {
        ArrayList<Pair<KeyImpl, ValueListImpl>> al = new ArrayList<Pair<KeyImpl, ValueListImpl>>();
        List<KeyImpl> ks = bl.getKeys();
        List<ValueListImpl> vls = bl.getValues();
        int n = Math.min(ks.size(), vls.size());
        for (int i = 0; i < n; ++i)
            al.add(new Pair<KeyImpl, ValueListImpl>(ks.get(i), vls.get(i)));
        
    	kv().bulkPut(al);
    }
    
    @WebMethod
    public void config(Configuration conf) throws ServiceAlreadyConfiguredException {
        kv().config(conf);
    }
}