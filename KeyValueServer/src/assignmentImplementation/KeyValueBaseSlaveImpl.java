package assignmentImplementation;

import keyValueBaseInterfaces.KeyValueBaseSlave;
import keyValueBaseInterfaces.LogRecord;

public class KeyValueBaseSlaveImpl extends KeyValueBaseReplicaImpl implements KeyValueBaseSlave<KeyImpl, ValueListImpl> {

	@Override
	public void logApply(LogRecord record) {
		// TODO Auto-generated method stub
		
	}

}
