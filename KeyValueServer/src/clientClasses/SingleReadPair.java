
package clientClasses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for singleReadPair complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="singleReadPair">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LSN" type="{http://assignmentImplementation/}timestampLog" minOccurs="0"/>
 *         &lt;element name="VL" type="{http://assignmentImplementation/}valueListImpl" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "singleReadPair", propOrder = {
    "lsn",
    "vl"
})
public class SingleReadPair {

    @XmlElement(name = "LSN")
    protected TimestampLog lsn;
    @XmlElement(name = "VL")
    protected ValueListImpl vl;

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
     * @return
     *     possible object is
     *     {@link ValueListImpl }
     *     
     */
    public ValueListImpl getVL() {
        return vl;
    }

    /**
     * Sets the value of the vl property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValueListImpl }
     *     
     */
    public void setVL(ValueListImpl value) {
        this.vl = value;
    }

}
