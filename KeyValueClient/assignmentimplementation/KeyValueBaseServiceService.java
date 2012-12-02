
package assignmentimplementation;

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
@WebServiceClient(name = "KeyValueBaseServiceService", targetNamespace = "http://assignmentImplementation/", wsdlLocation = "http://localhost:8080/keyvalue/kv?wsdl")
public class KeyValueBaseServiceService
    extends Service
{

    private final static URL KEYVALUEBASESERVICESERVICE_WSDL_LOCATION;
    private final static WebServiceException KEYVALUEBASESERVICESERVICE_EXCEPTION;
    private final static QName KEYVALUEBASESERVICESERVICE_QNAME = new QName("http://assignmentImplementation/", "KeyValueBaseServiceService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/keyvalue/kv?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        KEYVALUEBASESERVICESERVICE_WSDL_LOCATION = url;
        KEYVALUEBASESERVICESERVICE_EXCEPTION = e;
    }

    public KeyValueBaseServiceService() {
        super(__getWsdlLocation(), KEYVALUEBASESERVICESERVICE_QNAME);
    }

    public KeyValueBaseServiceService(WebServiceFeature... features) {
        super(__getWsdlLocation(), KEYVALUEBASESERVICESERVICE_QNAME, features);
    }

    public KeyValueBaseServiceService(URL wsdlLocation) {
        super(wsdlLocation, KEYVALUEBASESERVICESERVICE_QNAME);
    }

    public KeyValueBaseServiceService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, KEYVALUEBASESERVICESERVICE_QNAME, features);
    }

    public KeyValueBaseServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public KeyValueBaseServiceService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns KeyValueBaseService
     */
    @WebEndpoint(name = "KeyValueBaseServicePort")
    public KeyValueBaseService getKeyValueBaseServicePort() {
        return super.getPort(new QName("http://assignmentImplementation/", "KeyValueBaseServicePort"), KeyValueBaseService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns KeyValueBaseService
     */
    @WebEndpoint(name = "KeyValueBaseServicePort")
    public KeyValueBaseService getKeyValueBaseServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://assignmentImplementation/", "KeyValueBaseServicePort"), KeyValueBaseService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (KEYVALUEBASESERVICESERVICE_EXCEPTION!= null) {
            throw KEYVALUEBASESERVICESERVICE_EXCEPTION;
        }
        return KEYVALUEBASESERVICESERVICE_WSDL_LOCATION;
    }

}
