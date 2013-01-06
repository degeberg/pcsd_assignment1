
package clientClasses;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "KeyValueBaseSlaveServiceService", targetNamespace = "http://assignmentImplementation/", wsdlLocation = "http://localhost:8080/keyvalue/kvslave?wsdl")
public class KeyValueBaseSlaveServiceService
    extends Service
{

    private final static URL KEYVALUEBASESLAVESERVICESERVICE_WSDL_LOCATION;
    private final static WebServiceException KEYVALUEBASESLAVESERVICESERVICE_EXCEPTION;
    private final static QName KEYVALUEBASESLAVESERVICESERVICE_QNAME = new QName("http://assignmentImplementation/", "KeyValueBaseSlaveServiceService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/keyvalue/kvslave?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        KEYVALUEBASESLAVESERVICESERVICE_WSDL_LOCATION = url;
        KEYVALUEBASESLAVESERVICESERVICE_EXCEPTION = e;
    }

    public KeyValueBaseSlaveServiceService() {
        super(__getWsdlLocation(), KEYVALUEBASESLAVESERVICESERVICE_QNAME);
    }

    public KeyValueBaseSlaveServiceService(WebServiceFeature... features) {
        super(__getWsdlLocation(), KEYVALUEBASESLAVESERVICESERVICE_QNAME, features);
    }

    public KeyValueBaseSlaveServiceService(URL wsdlLocation) {
        super(wsdlLocation, KEYVALUEBASESLAVESERVICESERVICE_QNAME);
    }

    public KeyValueBaseSlaveServiceService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, KEYVALUEBASESLAVESERVICESERVICE_QNAME, features);
    }

    public KeyValueBaseSlaveServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public KeyValueBaseSlaveServiceService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns KeyValueBaseSlaveService
     */
    @WebEndpoint(name = "KeyValueBaseSlaveServicePort")
    public KeyValueBaseSlaveService getKeyValueBaseSlaveServicePort() {
        return super.getPort(new QName("http://assignmentImplementation/", "KeyValueBaseSlaveServicePort"), KeyValueBaseSlaveService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns KeyValueBaseSlaveService
     */
    @WebEndpoint(name = "KeyValueBaseSlaveServicePort")
    public KeyValueBaseSlaveService getKeyValueBaseSlaveServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://assignmentImplementation/", "KeyValueBaseSlaveServicePort"), KeyValueBaseSlaveService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (KEYVALUEBASESLAVESERVICESERVICE_EXCEPTION!= null) {
            throw KEYVALUEBASESLAVESERVICESERVICE_EXCEPTION;
        }
        return KEYVALUEBASESLAVESERVICESERVICE_WSDL_LOCATION;
    }

}