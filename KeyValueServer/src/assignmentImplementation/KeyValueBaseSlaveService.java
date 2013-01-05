package assignmentImplementation;
import java.io.IOException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import keyValueBaseInterfaces.LogRecord;

@WebService
@SOAPBinding(style = Style.RPC)
public class KeyValueBaseSlaveService extends KeyValueBaseReplicaService {
    
    private KeyValueBaseSlaveImpl kv;

    public KeyValueBaseSlaveService() throws IndexOutOfBoundsException,
            IOException {
        kv = new KeyValueBaseSlaveImpl();
    }

    protected KeyValueBaseSlaveImpl kv() {
        return kv;
    }
    
    @WebMethod
	public void logApply(LogRecord record) {
        kv().logApply(record);
    }
    
}