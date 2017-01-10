
package cz.cvut.fel.aos.printclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for print complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="print">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="jsonstring" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "print", propOrder = {
    "jsonstring"
})
public class Print {

    protected String jsonstring;

    /**
     * Gets the value of the jsonstring property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJsonstring() {
        return jsonstring;
    }

    /**
     * Sets the value of the jsonstring property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJsonstring(String value) {
        this.jsonstring = value;
    }

}
