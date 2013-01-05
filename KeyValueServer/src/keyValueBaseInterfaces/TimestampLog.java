package keyValueBaseInterfaces;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class is used in LogRecord, and it represents
 * the LSN of a record.
 */
@XmlRootElement
public class TimestampLog implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private Long ind;

	public TimestampLog(Long ind) {
		this.ind = ind;
	}

	@SuppressWarnings("unused")
	private TimestampLog() {
	}
	
	public boolean before(TimestampLog t) {
		return this.compareTo(t) < 0;
	}
	
	public boolean after(TimestampLog t) {
		return this.compareTo(t) > 0;
	}
	
	public int compareTo(TimestampLog t) {
		return this.ind.compareTo(t.ind);
	}
	
	public TimestampLog inc() {
		this.ind++;
		return new TimestampLog(this.ind);
	}
	
	public String toString() {
		return this.ind.toString();
	}
	
	public Long toLong() {
	    return this.ind;
	}
	
}
