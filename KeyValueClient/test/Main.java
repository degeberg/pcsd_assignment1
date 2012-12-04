package test;

import assignmentimplementation.KeyImpl;
import assignmentimplementation.KeyValueBaseService;
import assignmentimplementation.KeyValueBaseServiceService;
import assignmentimplementation.ServiceAlreadyInitializedException_Exception;
import assignmentimplementation.ValueImpl;
import assignmentimplementation.ValueListImpl;

public class Main {
	
	static private KeyValueBaseService kv;

	public static void main(String[] args) throws Exception {
		KeyValueBaseServiceService s = new KeyValueBaseServiceService();
		kv = s.getKeyValueBaseServicePort();
		
		try {
			kv.init("/tmp/data.txt");
			System.out.println("Initialized.");
		} catch (ServiceAlreadyInitializedException_Exception e) {
			System.out.println("Already initialized.");
		}
		
		printKey(24);
		printKey(25);
		printKey(26);
		
		ValueListImpl vl = buildList(new int[]{1, 2, 3});
		KeyImpl key = new KeyImpl();
		key.setKey(26);
		kv.update(key, vl);
		
		printKey(26);
	}
	
	static private void printKey(int k) throws Exception {
		System.out.println("Key: " + k);
		KeyImpl key = new KeyImpl();
		key.setKey(k);
		
		ValueListImpl vl = kv.read(key);
		for (ValueImpl v : vl.getElements()) {
			System.out.println(v.getValue());
		}
		System.out.println();
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
}
