package assignmentImplementation;

import java.lang.reflect.InvocationTargetException;

import keyValueBaseInterfaces.KeyValueBaseSlave;
import keyValueBaseInterfaces.LogRecord;

public class KeyValueBaseSlaveImpl extends KeyValueBaseReplicaImpl implements KeyValueBaseSlave<KeyImpl, ValueListImpl> {

	public KeyValueBaseSlaveImpl(String storePath) {
        super(storePath);
    }

    @Override
	public void logApply(LogRecord record) {
		try {
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
