//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-661 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.08 at 05:45:20 PM CST 
//


package org.jboss.identity.federation.saml.v2.ac.classes.pgp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActivationLimitType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ActivationLimitType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:PGP}ActivationLimitDuration"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:PGP}ActivationLimitUsages"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:PGP}ActivationLimitSession"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivationLimitType", propOrder = {
    "activationLimitDuration",
    "activationLimitUsages",
    "activationLimitSession"
})
public class ActivationLimitType {

    @XmlElement(name = "ActivationLimitDuration")
    protected ActivationLimitDurationType activationLimitDuration;
    @XmlElement(name = "ActivationLimitUsages")
    protected ActivationLimitUsagesType activationLimitUsages;
    @XmlElement(name = "ActivationLimitSession")
    protected ActivationLimitSessionType activationLimitSession;

    /**
     * Gets the value of the activationLimitDuration property.
     * 
     * @return
     *     possible object is
     *     {@link ActivationLimitDurationType }
     *     
     */
    public ActivationLimitDurationType getActivationLimitDuration() {
        return activationLimitDuration;
    }

    /**
     * Sets the value of the activationLimitDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActivationLimitDurationType }
     *     
     */
    public void setActivationLimitDuration(ActivationLimitDurationType value) {
        this.activationLimitDuration = value;
    }

    /**
     * Gets the value of the activationLimitUsages property.
     * 
     * @return
     *     possible object is
     *     {@link ActivationLimitUsagesType }
     *     
     */
    public ActivationLimitUsagesType getActivationLimitUsages() {
        return activationLimitUsages;
    }

    /**
     * Sets the value of the activationLimitUsages property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActivationLimitUsagesType }
     *     
     */
    public void setActivationLimitUsages(ActivationLimitUsagesType value) {
        this.activationLimitUsages = value;
    }

    /**
     * Gets the value of the activationLimitSession property.
     * 
     * @return
     *     possible object is
     *     {@link ActivationLimitSessionType }
     *     
     */
    public ActivationLimitSessionType getActivationLimitSession() {
        return activationLimitSession;
    }

    /**
     * Sets the value of the activationLimitSession property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActivationLimitSessionType }
     *     
     */
    public void setActivationLimitSession(ActivationLimitSessionType value) {
        this.activationLimitSession = value;
    }

}
