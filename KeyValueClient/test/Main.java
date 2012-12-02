package test;

import assignmentimplementation.FileNotFoundException_Exception;
import assignmentimplementation.KeyValueBaseService;
import assignmentimplementation.KeyValueBaseServiceService;
import assignmentimplementation.ServiceAlreadyInitializedException_Exception;
import assignmentimplementation.ServiceInitializingException_Exception;

public class Main {

	public static void main(String[] args) {
		KeyValueBaseServiceService s = new KeyValueBaseServiceService();
		KeyValueBaseService kv = s.getKeyValueBaseServicePort();
		
		try {
			kv.init("/home/soerend/test.txt");
		} catch (FileNotFoundException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceAlreadyInitializedException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceInitializingException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
