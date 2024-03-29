
package clientClasses;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "KeyValueBaseSlaveService", targetNamespace = "http://assignmentImplementation/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface KeyValueBaseSlaveService {


    /**
     * 
     * @param logRecord
     */
    @WebMethod
    @Action(input = "http://assignmentImplementation/KeyValueBaseSlaveService/logApplyRequest", output = "http://assignmentImplementation/KeyValueBaseSlaveService/logApplyResponse")
    public void logApply(
        @WebParam(name = "log_record", partName = "log_record")
        LogRecord logRecord);

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @Action(input = "http://assignmentImplementation/KeyValueBaseSlaveService/dummyRequest", output = "http://assignmentImplementation/KeyValueBaseSlaveService/dummyResponse")
    public void dummy(
        @WebParam(name = "arg0", partName = "arg0")
        LengthPredicate arg0);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns clientClasses.ArrayReadPair
     * @throws BeginGreaterThanEndException_Exception
     * @throws IOException_Exception
     * @throws ServiceNotInitializedException_Exception
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://assignmentImplementation/KeyValueBaseSlaveService/atomicScanRequest", output = "http://assignmentImplementation/KeyValueBaseSlaveService/atomicScanResponse", fault = {
        @FaultAction(className = IOException_Exception.class, value = "http://assignmentImplementation/KeyValueBaseSlaveService/atomicScan/Fault/IOException"),
        @FaultAction(className = BeginGreaterThanEndException_Exception.class, value = "http://assignmentImplementation/KeyValueBaseSlaveService/atomicScan/Fault/BeginGreaterThanEndException"),
        @FaultAction(className = ServiceNotInitializedException_Exception.class, value = "http://assignmentImplementation/KeyValueBaseSlaveService/atomicScan/Fault/ServiceNotInitializedException")
    })
    public ArrayReadPair atomicScan(
        @WebParam(name = "arg0", partName = "arg0")
        KeyImpl arg0,
        @WebParam(name = "arg1", partName = "arg1")
        KeyImpl arg1,
        @WebParam(name = "arg2", partName = "arg2")
        Predicate arg2)
        throws BeginGreaterThanEndException_Exception, IOException_Exception, ServiceNotInitializedException_Exception
    ;

    /**
     * 
     * @param arg0
     * @throws FileNotFoundException_Exception
     * @throws ServiceInitializingException_Exception
     * @throws ServiceAlreadyInitializedException_Exception
     */
    @WebMethod
    @Action(input = "http://assignmentImplementation/KeyValueBaseSlaveService/initRequest", output = "http://assignmentImplementation/KeyValueBaseSlaveService/initResponse", fault = {
        @FaultAction(className = ServiceAlreadyInitializedException_Exception.class, value = "http://assignmentImplementation/KeyValueBaseSlaveService/init/Fault/ServiceAlreadyInitializedException"),
        @FaultAction(className = ServiceInitializingException_Exception.class, value = "http://assignmentImplementation/KeyValueBaseSlaveService/init/Fault/ServiceInitializingException"),
        @FaultAction(className = FileNotFoundException_Exception.class, value = "http://assignmentImplementation/KeyValueBaseSlaveService/init/Fault/FileNotFoundException")
    })
    public void init(
        @WebParam(name = "arg0", partName = "arg0")
        String arg0)
        throws FileNotFoundException_Exception, ServiceAlreadyInitializedException_Exception, ServiceInitializingException_Exception
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns clientClasses.SingleReadPair
     * @throws IOException_Exception
     * @throws KeyNotFoundException_Exception
     * @throws ServiceNotInitializedException_Exception
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://assignmentImplementation/KeyValueBaseSlaveService/readRequest", output = "http://assignmentImplementation/KeyValueBaseSlaveService/readResponse", fault = {
        @FaultAction(className = KeyNotFoundException_Exception.class, value = "http://assignmentImplementation/KeyValueBaseSlaveService/read/Fault/KeyNotFoundException"),
        @FaultAction(className = IOException_Exception.class, value = "http://assignmentImplementation/KeyValueBaseSlaveService/read/Fault/IOException"),
        @FaultAction(className = ServiceNotInitializedException_Exception.class, value = "http://assignmentImplementation/KeyValueBaseSlaveService/read/Fault/ServiceNotInitializedException")
    })
    public SingleReadPair read(
        @WebParam(name = "arg0", partName = "arg0")
        KeyImpl arg0)
        throws IOException_Exception, KeyNotFoundException_Exception, ServiceNotInitializedException_Exception
    ;

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns clientClasses.ArrayReadPair
     * @throws BeginGreaterThanEndException_Exception
     * @throws IOException_Exception
     * @throws ServiceNotInitializedException_Exception
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://assignmentImplementation/KeyValueBaseSlaveService/scanRequest", output = "http://assignmentImplementation/KeyValueBaseSlaveService/scanResponse", fault = {
        @FaultAction(className = IOException_Exception.class, value = "http://assignmentImplementation/KeyValueBaseSlaveService/scan/Fault/IOException"),
        @FaultAction(className = BeginGreaterThanEndException_Exception.class, value = "http://assignmentImplementation/KeyValueBaseSlaveService/scan/Fault/BeginGreaterThanEndException"),
        @FaultAction(className = ServiceNotInitializedException_Exception.class, value = "http://assignmentImplementation/KeyValueBaseSlaveService/scan/Fault/ServiceNotInitializedException")
    })
    public ArrayReadPair scan(
        @WebParam(name = "arg0", partName = "arg0")
        KeyImpl arg0,
        @WebParam(name = "arg1", partName = "arg1")
        KeyImpl arg1,
        @WebParam(name = "arg2", partName = "arg2")
        Predicate arg2)
        throws BeginGreaterThanEndException_Exception, IOException_Exception, ServiceNotInitializedException_Exception
    ;

}
