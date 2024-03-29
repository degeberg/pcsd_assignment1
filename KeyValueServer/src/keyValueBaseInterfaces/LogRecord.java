package keyValueBaseInterfaces;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import assignmentImplementation.KeyImpl;
import assignmentImplementation.ValueImpl;
import assignmentImplementation.ValueListImpl;

/**
 * 
 * @author PCSD - DIKU
 *
 * This class contains all the information necessary
 * for a log request. It provides methods to work
 * with that information as well as to serialize
 * its objects to write them to and from disk.
 * 
 * Note: This class is capable of serializing the parameters
 * even if they do not implement the <i>Serializable</i>
 * interface. To accomplish that the class of the parameter
 * has to implement a constructor that accepts a single
 * <i>String</i>, which is produced by the same class's
 * <i>toString()</i> method.
 */
@XmlRootElement
@XmlSeeAlso({KeyImpl.class, ValueImpl.class, ValueListImpl.class})
public class LogRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private static TimestampLog lastTimestamp = new TimestampLog(0L);

	@XmlElement
	private String className;
	@XmlElement
	private String methodName;
	@XmlElement
	private int numberParam;
	@XmlElement
	private TimestampLog LSN;
	@XmlElement(name = "object_array")
	private Object[] params;
	
	public static void dummy(KeyImpl k, ValueImpl v, ValueListImpl vl) { }

	public LogRecord(Class<?> srcClass, String methodName, Object[] params) {
		this(srcClass.getCanonicalName(), methodName, params);
	}
	
	public LogRecord(String srcClass, String methodName, Object[] params) {
		this.className = srcClass;
		this.methodName = methodName;
		this.params = params;
		this.numberParam = params.length;
		synchronized (LogRecord.lastTimestamp) {
			this.LSN = LogRecord.lastTimestamp.inc();
		}
	}
	
	@SuppressWarnings("unused")
	private LogRecord() {
	}
	
	public String getSrcClass() {
		return this.className;
	}
	
	public String getMethodName() {
		return this.methodName;
	}
	
	public int getNumParams() {
		return this.numberParam;
	}
	
	public TimestampLog getLSN() {
		return this.LSN;
	}
	
	public Object[] getParams() {
		return this.params;
	}
	
	public Object invoke(Object src) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, ClassNotFoundException {

		Class<?>[] paramsClass = new Class<?>[this.numberParam];
		for (int i=0; i<this.numberParam; i++)
			paramsClass[i] = this.params[i].getClass();
		
		Method m =  Class.forName(this.className).getMethod(methodName, paramsClass);
		return m.invoke(src, this.params);
	}
	
}
