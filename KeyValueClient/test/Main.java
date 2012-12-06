package test;

import java.util.List;

import assignmentimplementation.BeginGreaterThanEndException_Exception;
import assignmentimplementation.BulkList;
import assignmentimplementation.IOException_Exception;
import assignmentimplementation.KeyImpl;
import assignmentimplementation.KeyValueBaseService;
import assignmentimplementation.KeyValueBaseServiceService;
import assignmentimplementation.LengthPredicate;
import assignmentimplementation.ServiceAlreadyInitializedException_Exception;
import assignmentimplementation.ServiceNotInitializedException_Exception;
import assignmentimplementation.ValueImpl;
import assignmentimplementation.ValueListImpl;
import assignmentimplementation.ValueListImplArray;

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
		
		testScan();
		
		testBulkPut();
		
		key = new KeyImpl();
        key.setKey(42);
        
        ValueImpl v = new ValueImpl();
        v.setValue(-1);
        
        vl = new ValueListImpl();
        vl.getElements().add(v);
        
        //kv.insert(key,  vl);
		
		Client2 threads[] = new Client2[50000];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Client2(kv);
		}
		long startTime = System.nanoTime();
		for (int i = 0; i < threads.length; ++i) {
			threads[i].start();
		}
		for (int i = 0; i < threads.length; ++i) {
			threads[i].join();
		}
		long endTime = System.nanoTime();
		long duration = endTime - startTime;
		System.out.println("Elapsed time to process all threads: " + duration + " nanoseconds.");
		long avgDelay = 0;
		int totSize = 0;
		for (int i = 0; i < threads.length; ++i) {
			avgDelay += threads[i].getDuration();
			totSize += threads[i].getSize();
		}
		avgDelay /= threads.length;
		System.out.println("Transferred a total of " + totSize + " values with average request time of " + avgDelay + " ns.");
	}
	
	static private void testBulkPut() throws Exception
	{
		KeyImpl k1 = new KeyImpl();
		k1.setKey(-1);
		KeyImpl k2 = new KeyImpl();
		k2.setKey(10);
		ValueListImpl vl = buildList(new int[]{4,1,5});

		BulkList bl = new BulkList();
		bl.getKeys().add(k1);
		bl.getVals().add(vl);
		bl.getKeys().add(k2);
		bl.getVals().add(vl);
		
		kv.bulkPut(bl);
		
		printKey(-1);
		printKey(10);
	}
	
	static private void testScan() throws BeginGreaterThanEndException_Exception, IOException_Exception, ServiceNotInitializedException_Exception
	{
		LengthPredicate p = new LengthPredicate();
		p.setLength(5);
		KeyImpl kbegin = new KeyImpl();
		kbegin.setKey(24);
		KeyImpl kend = new KeyImpl();
		kend.setKey(27);
		
		ValueListImplArray tmp = kv.scan(kbegin, kend, p);
		List<ValueListImpl> l = tmp.getItem();
		System.out.println("Got list of length: " + l.size());
		for (ValueListImpl vl2 : l) {
			System.out.println("Resp 1:");
			for (ValueImpl v : vl2.getElements()) {
				System.out.println(v.getValue());
			}
			System.out.println();
		}
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
