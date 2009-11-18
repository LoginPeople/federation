//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema.  
// Generated on: 2009.09.03 at 01:21:42 PM BRT  
//


package org.jboss.identity.federation.core.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Aspects involved in trust decisions such as the domains that the IDP or the Service Provider trusts.
 * 
 * <p>Java class for TrustType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TrustType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Domains" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TrustType", propOrder = {
    "domains"
})
public class TrustType {

    @XmlElement(name = "Domains", required = true)
    protected String domains;

    /**
     * Gets the value of the domains property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomains() {
        return domains;
    }

    /**
     * Sets the value of the domains property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomains(String value) {
        this.domains = value;
    }

}
