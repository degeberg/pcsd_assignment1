package assignmentImplementation;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import keyValueBaseInterfaces.LogRecord;
import keyValueBaseInterfaces.Replicator;
import clientClasses.KeyValueBaseSlaveServiceService;

/**
 * This class extends your MyLogger from assignment 2 to use its
 * logic, and it is a suggestion. You can choose to follow it
 * or lint to your last assignment's implementation in your own way.
 */

public class ReplicatorImpl extends Thread implements Replicator {
    
    private ConcurrentLinkedQueue<KeyValueBaseSlaveServiceService> slaves;
    private ExecutorService executor;
    
    public ReplicatorImpl() {
        slaves = new ConcurrentLinkedQueue<>();
    }
    
    public void setSlaves(ArrayList<KeyValueBaseSlaveServiceService> slaves) {
        synchronized (slaves) {
            this.slaves.addAll(slaves);
        }
    }

	@Override
	public Future<?> makeStable(final LogRecord record) {
	    System.out.println("Replicate " + record.getMethodName() + " to " + slaves.size() + " slaves.");
        Future<?> f = executor.submit(new Callable<Void>() {
            public Void call() throws Exception {
                System.out.println("Callable");
                clientClasses.LogRecord log = createLog(record);
                
                for (KeyValueBaseSlaveServiceService slave : slaves) {
                    try {
                        System.err.println("Replicating to slave");
                        slave.getKeyValueBaseSlaveServicePort().logApply(log);
                        System.err.println("Replicated to slave");
                    } catch (Exception e) {
                        synchronized (slaves) {
                            slaves.remove(slave);
                        }
                    }
                }
                return null;
            }
        });
		return f;
	}

	@Override
	public void run() {
        executor = Executors.newFixedThreadPool(1);
	}
	
	private clientClasses.LogRecord createLog(LogRecord record) {
        clientClasses.LogRecord log = new clientClasses.LogRecord();
        log.setClassName(record.getSrcClass());
        clientClasses.TimestampLog lsn = new clientClasses.TimestampLog();
        lsn.setInd(record.getLSN().toLong());
        log.setLSN(lsn);
        log.setMethodName(record.getMethodName());
        log.setNumberParam(record.getNumParams());
        for (Object s : record.getParams()) {
            log.getObjectArray().add(s);
        }
        
        return log;
	}

}
