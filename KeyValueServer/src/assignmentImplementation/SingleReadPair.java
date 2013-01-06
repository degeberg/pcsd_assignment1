package assignmentImplementation;

import javax.xml.bind.annotation.XmlRootElement;

import keyValueBaseInterfaces.TimestampLog;

@XmlRootElement
public class SingleReadPair
{
    private TimestampLog LSN;
    private ValueListImpl vl;
    
    public SingleReadPair() {
        LSN = null;
        vl = new ValueListImpl();
    }
    
    public void setLSN(TimestampLog LSN) {
        this.LSN = LSN;
    }
    
    public void setVL(ValueListImpl vl) {
        this.vl = vl;
    }
    
    public TimestampLog getLSN() {
        return LSN;
    }
    
    public ValueListImpl getVL() {
        return vl;
    }
}
