package assignmentImplementation;

import keyValueBaseInterfaces.Value;

public class ValueImpl implements Value
{
    private static final long serialVersionUID = 4117889269011986973L;
    
    private Integer v;
    
    public ValueImpl(Integer val) {
        this.v = val;
    }
    
    public Integer get() {
        return this.v;
    }

    public String toString() {
        return v.toString();
    }
    
}

