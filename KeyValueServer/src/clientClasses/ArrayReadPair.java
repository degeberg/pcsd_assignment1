
package clientClasses;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for arrayReadPair complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="arrayReadPair">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LSN" type="{http://assignmentImplementation/}timestampLog" minOccurs="0"/>
 *         &lt;element name="VL" type="{http://assignmentImplementation/}valueListImpl" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "arrayReadPair", propOrder = {
    "lsn",
    "vl"
})
public class ArrayReadPair {

    @XmlElement(name = "LSN")
    protected TimestampLog lsn;
    @XmlElement(name = "VL", nillable = true)
    protected List<ValueListImpl> vl;

    /**
     * Gets the value of the lsn property.
     * 
     * @return
     *     possible object is
     *     {@link TimestampLog }
     *     
     */
    public TimestampLog getLSN() {
        return lsn;
    }

    /**
     * Sets the value of the lsn property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimestampLog }
     *     
     */
    public void setLSN(TimestampLog value) {
        this.lsn = value;
    }

    /**
     * Gets the value of the vl property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vl property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVL().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ValueListImpl }
     * 
     * 
     */
    public List<ValueListImpl> getVL() {
        if (vl == null) {
            vl = new ArrayList<ValueListImpl>();
        }
        return this.vl;
    }

}
