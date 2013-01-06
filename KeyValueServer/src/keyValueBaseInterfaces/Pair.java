package keyValueBaseInterfaces;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({keyValueBaseInterfaces.TimestampLog.class})
public class Pair<K, V> {
	@XmlElement
	private K k;
	@XmlElement
	private V v;
	
	public Pair()
	{
	}
	
	public Pair (K k, V v){
		this.k = k;
		this.v = v;
	}
	
	public K getKey(){
		return k;
	}
	
	public V getValue(){
		return v;
	}
}
