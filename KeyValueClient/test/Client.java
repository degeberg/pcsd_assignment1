package test;

import java.util.Random;

import assignmentimplementation.KeyImpl;
import assignmentimplementation.KeyValueBaseService;
import assignmentimplementation.ValueImpl;
import assignmentimplementation.ValueListImpl;

public class Client extends Thread {
	
	private KeyValueBaseService kv;
	private int threadNumber;
	
	public Client(KeyValueBaseService kv, int threadNumber) {
		this.kv = kv;
		this.threadNumber = threadNumber;
	}

	@Override
	public void run() {
		Random rand = new Random();
		int min = 100;
		int max = 1500;

		int randomNum = rand.nextInt(max - min + 1) + min;

        try {
        	Thread.sleep(randomNum);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        KeyImpl key = new KeyImpl();
        key.setKey(42);
        
        ValueImpl v = new ValueImpl();
        v.setValue(threadNumber);
        
        ValueListImpl vl = new ValueListImpl();
        vl.getElements().add(v);
        
        try {
        	kv.update(key, vl);
        	Thread.sleep(randomNum);
        	synchronized (kv) {
	        	System.out.println("Thread " + threadNumber + ":");
	        	Main.printKey(42);
        	}
        } catch (Exception e) {
			e.printStackTrace();
        }
	}

}
