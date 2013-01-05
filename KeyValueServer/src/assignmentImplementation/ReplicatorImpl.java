package assignmentImplementation;

import java.util.ArrayList;
import java.util.concurrent.Callable;
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
    
    private ArrayList<KeyValueBaseSlaveServiceService> slaves;
    private ExecutorService executor;
    
    public ReplicatorImpl() {
        slaves = new ArrayList<>();
    }
    
    public void setSlaves(ArrayList<KeyValueBaseSlaveServiceService> slaves) {
        this.slaves = slaves;
    }

	@Override
	public Future<?> makeStable(final LogRecord record) {
        Future<?> f = executor.submit(new Callable<Void>() {
            public Void call() throws Exception {
                for (KeyValueBaseSlaveServiceService slave : slaves) {
                    try {
                        slave.getKeyValueBaseSlaveServicePort().logApply(record);
                    } catch (Exception e) {
                        slaves.remove(slave);
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

}
