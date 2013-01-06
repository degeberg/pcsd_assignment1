import keyValueBaseInterfaces.Configuration;
import clientClasses.KeyImpl;
import clientClasses.KeyValueBaseProxyServiceService;
import clientClasses.ServiceAlreadyConfiguredException_Exception;
import clientClasses.ServiceAlreadyInitializedException_Exception;
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
		//printKey(25);
		//printKey(26);
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

}
