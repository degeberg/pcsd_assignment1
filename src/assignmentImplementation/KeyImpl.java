package assignmentImplementation;

import keyValueBaseInterfaces.Key;

public class KeyImpl implements Key<KeyImpl>
{
    private Integer k;
    
    public KeyImpl(Integer key) {
        this.k = key;
    }
    
    public Integer get() {
        return this.k;
    }

    @Override
    public int compareTo(KeyImpl other) {
        return k.compareTo(other.k);
    }
    
    public String toString() {
        return k.toString();
    }

}
