//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-661 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.08 at 05:45:20 PM CST 
//


package org.jboss.identity.federation.saml.v2.ac.classes.spki;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuthnMethodBaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthnMethodBaseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:SPKI}PrincipalAuthenticationMechanism" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:SPKI}Authenticator" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:SPKI}AuthenticatorTransportProtocol" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:SPKI}Extension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "principalAuthenticationMechanism",
    "authenticator",
    "authenticatorTransportProtocol",
    "extension"
})
@XmlSeeAlso({
    AuthnMethodBaseType.class
})
public class OriginalAuthnMethodBaseType {

    @XmlElement(name = "PrincipalAuthenticationMechanism")
    protected PrincipalAuthenticationMechanismType principalAuthenticationMechanism;
    @XmlElement(name = "Authenticator")
    protected AuthenticatorBaseType authenticator;
    @XmlElement(name = "AuthenticatorTransportProtocol")
    protected AuthenticatorTransportProtocolType authenticatorTransportProtocol;
    @XmlElement(name = "Extension")
    protected List<ExtensionType> extension;

    /**
     * Gets the value of the principalAuthenticationMechanism property.
     * 
     * @return
     *     possible object is
     *     {@link PrincipalAuthenticationMechanismType }
     *     
     */
    public PrincipalAuthenticationMechanismType getPrincipalAuthenticationMechanism() {
        return principalAuthenticationMechanism;
    }

    /**
     * Sets the value of the principalAuthenticationMechanism property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrincipalAuthenticationMechanismType }
     *     
     */
    public void setPrincipalAuthenticationMechanism(PrincipalAuthenticationMechanismType value) {
        this.principalAuthenticationMechanism = value;
    }

    /**
     * Gets the value of the authenticator property.
     * 
     * @return
     *     possible object is
     *     {@link AuthenticatorBaseType }
     *     
     */
    public AuthenticatorBaseType getAuthenticator() {
        return authenticator;
    }

    /**
     * Sets the value of the authenticator property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthenticatorBaseType }
     *     
     */
    public void setAuthenticator(AuthenticatorBaseType value) {
        this.authenticator = value;
    }

    /**
     * Gets the value of the authenticatorTransportProtocol property.
     * 
     * @return
     *     possible object is
     *     {@link AuthenticatorTransportProtocolType }
     *     
     */
    public AuthenticatorTransportProtocolType getAuthenticatorTransportProtocol() {
        return authenticatorTransportProtocol;
    }

    /**
     * Sets the value of the authenticatorTransportProtocol property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthenticatorTransportProtocolType }
     *     
     */
    public void setAuthenticatorTransportProtocol(AuthenticatorTransportProtocolType value) {
        this.authenticatorTransportProtocol = value;
    }

    /**
     * Gets the value of the extension property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extension property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtension().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExtensionType }
     * 
     * 
     */
    public List<ExtensionType> getExtension() {
        if (extension == null) {
            extension = new ArrayList<ExtensionType>();
        }
        return this.extension;
    }

}
