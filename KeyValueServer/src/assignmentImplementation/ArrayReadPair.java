package assignmentImplementation;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import keyValueBaseInterfaces.TimestampLog;

@XmlRootElement
public class ArrayReadPair
{
    private TimestampLog LSN;
    private ArrayList<ValueListImpl> vl;
    
    public ArrayReadPair()
    {
        LSN = null;
        vl = new ArrayList<ValueListImpl>();
    }
    
    public void setLSN(TimestampLog LSN) {
        this.LSN = LSN;
    }
    
    public void setVL(ArrayList<ValueListImpl> vl) {
        this.vl = vl;
    }
    
    public TimestampLog getLSN() {
        return LSN;
    }
    
    public ArrayList<ValueListImpl> getVL() {
        return vl;
    }
}
