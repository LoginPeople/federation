//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.09.03 at 01:21:42 PM BRT 
//


package org.jboss.identity.federation.core.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				IDP Type defines the configuration for an Identity
 * 				Provider.
 * 			
 * 
 * <p>Java class for IDPType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IDPType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:jboss:identity-federation:config:1.0}ProviderType">
 *       &lt;sequence>
 *         &lt;element name="Encryption" type="{urn:jboss:identity-federation:config:1.0}EncryptionType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="AssertionValidity" type="{http://www.w3.org/2001/XMLSchema}long" default="300000" />
 *       &lt;attribute name="RoleGenerator" type="{http://www.w3.org/2001/XMLSchema}string" default="org.jboss.identity.federation.bindings.tomcat.TomcatRoleGenerator" />
 *       &lt;attribute name="AttributeManager" type="{http://www.w3.org/2001/XMLSchema}string" default="org.jboss.identity.federation.bindings.tomcat.TomcatAttributeManager" />
 *       &lt;attribute name="Encrypt" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IDPType", propOrder = {
    "encryption"
})
public class IDPType
    extends ProviderType
{

    @XmlElement(name = "Encryption")
    protected EncryptionType encryption;
    @XmlAttribute(name = "AssertionValidity")
    protected Long assertionValidity;
    @XmlAttribute(name = "RoleGenerator")
    protected String roleGenerator;
    @XmlAttribute(name = "AttributeManager")
    protected String attributeManager;
    @XmlAttribute(name = "Encrypt")
    protected Boolean encrypt;

    /**
     * Gets the value of the encryption property.
     * 
     * @return
     *     possible object is
     *     {@link EncryptionType }
     *     
     */
    public EncryptionType getEncryption() {
        return encryption;
    }

    /**
     * Sets the value of the encryption property.
     * 
     * @param value
     *     allowed object is
     *     {@link EncryptionType }
     *     
     */
    public void setEncryption(EncryptionType value) {
        this.encryption = value;
    }

    /**
     * Gets the value of the assertionValidity property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getAssertionValidity() {
        if (assertionValidity == null) {
            return  300000L;
        } else {
            return assertionValidity;
        }
    }

    /**
     * Sets the value of the assertionValidity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAssertionValidity(Long value) {
        this.assertionValidity = value;
    }

    /**
     * Gets the value of the roleGenerator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoleGenerator() {
        if (roleGenerator == null) {
            return "org.jboss.identity.federation.bindings.tomcat.TomcatRoleGenerator";
        } else {
            return roleGenerator;
        }
    }

    /**
     * Sets the value of the roleGenerator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoleGenerator(String value) {
        this.roleGenerator = value;
    }

    /**
     * Gets the value of the attributeManager property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeManager() {
        if (attributeManager == null) {
            return "org.jboss.identity.federation.bindings.tomcat.TomcatAttributeManager";
        } else {
            return attributeManager;
        }
    }

    /**
     * Sets the value of the attributeManager property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeManager(String value) {
        this.attributeManager = value;
    }

    /**
     * Gets the value of the encrypt property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isEncrypt() {
        if (encrypt == null) {
            return false;
        } else {
            return encrypt;
        }
    }

    /**
     * Sets the value of the encrypt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEncrypt(Boolean value) {
        this.encrypt = value;
    }

}
