
package clientClasses;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "ServiceAlreadyInitializedException", targetNamespace = "http://assignmentImplementation/")
public class ServiceAlreadyInitializedException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private ServiceAlreadyInitializedException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public ServiceAlreadyInitializedException_Exception(String message, ServiceAlreadyInitializedException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public ServiceAlreadyInitializedException_Exception(String message, ServiceAlreadyInitializedException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: clientClasses.ServiceAlreadyInitializedException
     */
    public ServiceAlreadyInitializedException getFaultInfo() {
        return faultInfo;
    }

}
