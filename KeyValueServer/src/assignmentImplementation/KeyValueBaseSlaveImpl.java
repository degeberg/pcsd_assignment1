package assignmentImplementation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import keyValueBaseExceptions.KeyNotFoundException;
import keyValueBaseExceptions.ServiceNotInitializedException;
import keyValueBaseInterfaces.KeyValueBaseSlave;
import keyValueBaseInterfaces.LogRecord;
import keyValueBaseInterfaces.Pair;
import keyValueBaseInterfaces.TimestampLog;

public class KeyValueBaseSlaveImpl extends KeyValueBaseReplicaImpl implements KeyValueBaseSlave<KeyImpl, ValueListImpl> {

	public KeyValueBaseSlaveImpl(String storePath) {
        super(storePath);
    }
    
    @Override
    public Pair<TimestampLog, ValueListImpl> read(KeyImpl k)
            throws KeyNotFoundException, IOException,
            ServiceNotInitializedException {
        System.out.println("Slave read");
        return super.read(k);
    }

    @Override
	public void logApply(LogRecord record) {
		try {
		    System.out.println("Applying log: " + record.getMethodName());
		    synchronized (lastLSN) {
		        if (record.getLSN().after(lastLSN)) {
        		    lastLSN = record.getLSN();
		        }
		    }
            record.invoke(kv);
        } catch (SecurityException | NoSuchMethodException
                | IllegalArgumentException | IllegalAccessException
                | InvocationTargetException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

}
