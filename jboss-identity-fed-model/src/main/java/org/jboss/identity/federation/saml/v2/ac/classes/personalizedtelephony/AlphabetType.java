//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-661 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.08 at 05:45:20 PM CST 
//


package org.jboss.identity.federation.saml.v2.ac.classes.personalizedtelephony;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AlphabetType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AlphabetType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="requiredChars" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="excludedChars" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="case" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AlphabetType")
public class AlphabetType {

    @XmlAttribute(required = true)
    protected String requiredChars;
    @XmlAttribute
    protected String excludedChars;
    @XmlAttribute(name = "case")
    protected String _case;

    /**
     * Gets the value of the requiredChars property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequiredChars() {
        return requiredChars;
    }

    /**
     * Sets the value of the requiredChars property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequiredChars(String value) {
        this.requiredChars = value;
    }

    /**
     * Gets the value of the excludedChars property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExcludedChars() {
        return excludedChars;
    }

    /**
     * Sets the value of the excludedChars property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExcludedChars(String value) {
        this.excludedChars = value;
    }

    /**
     * Gets the value of the case property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCase() {
        return _case;
    }

    /**
     * Sets the value of the case property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCase(String value) {
        this._case = value;
    }

}
