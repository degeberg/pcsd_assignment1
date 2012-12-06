package test;

import java.util.Random;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.ZipfDistributionImpl;

import assignmentimplementation.IOException_Exception;
import assignmentimplementation.KeyImpl;
import assignmentimplementation.KeyNotFoundException_Exception;
import assignmentimplementation.KeyValueBaseService;
import assignmentimplementation.ServiceNotInitializedException_Exception;
import assignmentimplementation.ValueListImpl;

public class Client2 extends Thread {
	
	private KeyValueBaseService kv;
	private ValueListImpl v;
	private long duration;
	private static ZipfDistributionImpl dist = new ZipfDistributionImpl(1000, 1.2);
	
	public Client2(KeyValueBaseService kv) {
		this.kv = kv;
		this.v = new ValueListImpl();
		duration = 0L;
	}
	
	public int getSize() {
		if (v == null)
			return 0;
		return v.getElements().size();
	}
	
	public long getDuration() {
		return duration;
	}

	@Override
	public void run() {
		KeyImpl key = new KeyImpl();
		int k = -1;
		try {
			k = dist.sample();
		} catch (MathException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("sampling: " + k);
		key.setKey(k);
        try {
        	long startTime = System.nanoTime();
			v = kv.read(key);
			long endTime = System.nanoTime();
			duration = endTime - startTime;
		} catch (IOException_Exception | KeyNotFoundException_Exception
				| ServiceNotInitializedException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
