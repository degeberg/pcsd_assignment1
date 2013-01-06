import java.util.List;

import keyValueBaseInterfaces.Configuration;
import clientClasses.BeginGreaterThanEndException_Exception;
import clientClasses.IOException_Exception;
import clientClasses.KeyImpl;
import clientClasses.KeyValueBaseProxyServiceService;
import clientClasses.ServiceAlreadyConfiguredException_Exception;
import clientClasses.ServiceAlreadyInitializedException_Exception;
import clientClasses.ServiceNotInitializedException_Exception;
import clientClasses.ValueImpl;
import clientClasses.ValueListImpl;


public class Main {
    static private clientClasses.KeyValueBaseProxyService kv;

    public static void main(String[] args) throws Exception {
        KeyValueBaseProxyServiceService service = new KeyValueBaseProxyServiceService();
        kv = service.getKeyValueBaseProxyServicePort();
        
		try {
			Configuration realConfig = new Configuration();
			clientClasses.Configuration config = new clientClasses.Configuration();
			config.setMaster(realConfig.master);
			for (String slave : realConfig.slaves) {
			    config.getSlaves().add(slave);
			}
			kv.config(config);
			kv.init("/tmp/data.txt");
			System.out.println("Initialized.");
		} catch (ServiceAlreadyInitializedException_Exception|ServiceAlreadyConfiguredException_Exception e) {
			System.out.println("Already initialized.");
		}
		
		printKey(24);
		testScan();

        printKey(24);
        ValueListImpl vl = buildList(new int[]{1, 2, 3});
        KeyImpl key = new KeyImpl();
        key.setKey(26);
        kv.update(key, vl);
        
        printKey(26);
    }

    static private ValueListImpl buildList(int[] e) {
        ValueListImpl vl = new ValueListImpl();
        for (Integer a : e) {
            ValueImpl v = new ValueImpl();
            v.setValue(a);
            vl.getElements().add(v);
        }
        return vl;
    }
    
	static public void printKey(int k) throws Exception {
		System.out.println("Key: " + k);
		KeyImpl key = new KeyImpl();
		key.setKey(k);
		
		ValueListImpl vl = kv.read(key);
		for (ValueImpl v : vl.getElements()) {
			System.out.println(v.getValue());
		}
		System.out.println();
	}
	
    static private void testScan() throws BeginGreaterThanEndException_Exception, IOException_Exception, ServiceNotInitializedException_Exception
    {
        clientClasses.LengthPredicate p = new clientClasses.LengthPredicate();
        p.setLength(5);
        KeyImpl kbegin = new KeyImpl();
        kbegin.setKey(24);
        KeyImpl kend = new KeyImpl();
        kend.setKey(27);
            
        clientClasses.ValueListImplArray tmp = kv.scan(kbegin, kend, p);
        List<ValueListImpl> l = tmp.getItem();
        System.out.println("Got list of length: " + l.size());
        for (ValueListImpl vl2 : l) {
            System.out.println("Resp:");
            for (ValueImpl v : vl2.getElements()) {
                System.out.println(v.getValue());
            }
            System.out.println();
        }
    }

}
