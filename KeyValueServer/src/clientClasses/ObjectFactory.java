
package clientClasses;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the assignmentimplementation package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _IOException_QNAME = new QName("http://assignmentImplementation/", "IOException");
    private final static QName _ServiceNotInitializedException_QNAME = new QName("http://assignmentImplementation/", "ServiceNotInitializedException");
    private final static QName _LengthPredicate_QNAME = new QName("http://assignmentImplementation/", "lengthPredicate");
    private final static QName _KeyNotFoundException_QNAME = new QName("http://assignmentImplementation/", "KeyNotFoundException");
    private final static QName _FileNotFoundException_QNAME = new QName("http://assignmentImplementation/", "FileNotFoundException");
    private final static QName _ServiceInitializingException_QNAME = new QName("http://assignmentImplementation/", "ServiceInitializingException");
    private final static QName _KeyImpl_QNAME = new QName("http://assignmentImplementation/", "keyImpl");
    private final static QName _ServiceAlreadyInitializedException_QNAME = new QName("http://assignmentImplementation/", "ServiceAlreadyInitializedException");
    private final static QName _BeginGreaterThanEndException_QNAME = new QName("http://assignmentImplementation/", "BeginGreaterThanEndException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: assignmentimplementation
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IOException }
     * 
     */
    public IOException createIOException() {
        return new IOException();
    }

    /**
     * Create an instance of {@link LengthPredicate }
     * 
     */
    public LengthPredicate createLengthPredicate() {
        return new LengthPredicate();
    }

    /**
     * Create an instance of {@link ServiceNotInitializedException }
     * 
     */
    public ServiceNotInitializedException createServiceNotInitializedException() {
        return new ServiceNotInitializedException();
    }

    /**
     * Create an instance of {@link KeyNotFoundException }
     * 
     */
    public KeyNotFoundException createKeyNotFoundException() {
        return new KeyNotFoundException();
    }

    /**
     * Create an instance of {@link FileNotFoundException }
     * 
     */
    public FileNotFoundException createFileNotFoundException() {
        return new FileNotFoundException();
    }

    /**
     * Create an instance of {@link KeyImpl }
     * 
     */
    public KeyImpl createKeyImpl() {
        return new KeyImpl();
    }

    /**
     * Create an instance of {@link ServiceInitializingException }
     * 
     */
    public ServiceInitializingException createServiceInitializingException() {
        return new ServiceInitializingException();
    }

    /**
     * Create an instance of {@link ServiceAlreadyInitializedException }
     * 
     */
    public ServiceAlreadyInitializedException createServiceAlreadyInitializedException() {
        return new ServiceAlreadyInitializedException();
    }

    /**
     * Create an instance of {@link BeginGreaterThanEndException }
     * 
     */
    public BeginGreaterThanEndException createBeginGreaterThanEndException() {
        return new BeginGreaterThanEndException();
    }

    /**
     * Create an instance of {@link Pair }
     * 
     */
    public Pair createPair() {
        return new Pair();
    }

    /**
     * Create an instance of {@link TimestampLog }
     * 
     */
    public TimestampLog createTimestampLog() {
        return new TimestampLog();
    }

    /**
     * Create an instance of {@link LogRecord }
     * 
     */
    public LogRecord createLogRecord() {
        return new LogRecord();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IOException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://assignmentImplementation/", name = "IOException")
    public JAXBElement<IOException> createIOException(IOException value) {
        return new JAXBElement<IOException>(_IOException_QNAME, IOException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceNotInitializedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://assignmentImplementation/", name = "ServiceNotInitializedException")
    public JAXBElement<ServiceNotInitializedException> createServiceNotInitializedException(ServiceNotInitializedException value) {
        return new JAXBElement<ServiceNotInitializedException>(_ServiceNotInitializedException_QNAME, ServiceNotInitializedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LengthPredicate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://assignmentImplementation/", name = "lengthPredicate")
    public JAXBElement<LengthPredicate> createLengthPredicate(LengthPredicate value) {
        return new JAXBElement<LengthPredicate>(_LengthPredicate_QNAME, LengthPredicate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KeyNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://assignmentImplementation/", name = "KeyNotFoundException")
    public JAXBElement<KeyNotFoundException> createKeyNotFoundException(KeyNotFoundException value) {
        return new JAXBElement<KeyNotFoundException>(_KeyNotFoundException_QNAME, KeyNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://assignmentImplementation/", name = "FileNotFoundException")
    public JAXBElement<FileNotFoundException> createFileNotFoundException(FileNotFoundException value) {
        return new JAXBElement<FileNotFoundException>(_FileNotFoundException_QNAME, FileNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceInitializingException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://assignmentImplementation/", name = "ServiceInitializingException")
    public JAXBElement<ServiceInitializingException> createServiceInitializingException(ServiceInitializingException value) {
        return new JAXBElement<ServiceInitializingException>(_ServiceInitializingException_QNAME, ServiceInitializingException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KeyImpl }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://assignmentImplementation/", name = "keyImpl")
    public JAXBElement<KeyImpl> createKeyImpl(KeyImpl value) {
        return new JAXBElement<KeyImpl>(_KeyImpl_QNAME, KeyImpl.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceAlreadyInitializedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://assignmentImplementation/", name = "ServiceAlreadyInitializedException")
    public JAXBElement<ServiceAlreadyInitializedException> createServiceAlreadyInitializedException(ServiceAlreadyInitializedException value) {
        return new JAXBElement<ServiceAlreadyInitializedException>(_ServiceAlreadyInitializedException_QNAME, ServiceAlreadyInitializedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BeginGreaterThanEndException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://assignmentImplementation/", name = "BeginGreaterThanEndException")
    public JAXBElement<BeginGreaterThanEndException> createBeginGreaterThanEndException(BeginGreaterThanEndException value) {
        return new JAXBElement<BeginGreaterThanEndException>(_BeginGreaterThanEndException_QNAME, BeginGreaterThanEndException.class, null, value);
    }

}