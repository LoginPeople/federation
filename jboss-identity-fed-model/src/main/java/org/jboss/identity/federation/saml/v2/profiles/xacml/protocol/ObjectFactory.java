//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-661 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.01.08 at 03:14:41 PM CST 
//


package org.jboss.identity.federation.saml.v2.profiles.xacml.protocol;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the oasis.xacml._2_0.saml.protocol.schema.os package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _XACMLAuthzDecisionQuery_QNAME = new QName("urn:oasis:xacml:2.0:saml:protocol:schema:os", "XACMLAuthzDecisionQuery");
    private final static QName _XACMLPolicyQuery_QNAME = new QName("urn:oasis:xacml:2.0:saml:protocol:schema:os", "XACMLPolicyQuery");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: oasis.xacml._2_0.saml.protocol.schema.os
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link XACMLAuthzDecisionQueryType }
     * 
     */
    public XACMLAuthzDecisionQueryType createXACMLAuthzDecisionQueryType() {
        return new XACMLAuthzDecisionQueryType();
    }

    /**
     * Create an instance of {@link XACMLPolicyQueryType }
     * 
     */
    public XACMLPolicyQueryType createXACMLPolicyQueryType() {
        return new XACMLPolicyQueryType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XACMLAuthzDecisionQueryType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:xacml:2.0:saml:protocol:schema:os", name = "XACMLAuthzDecisionQuery")
    public JAXBElement<XACMLAuthzDecisionQueryType> createXACMLAuthzDecisionQuery(XACMLAuthzDecisionQueryType value) {
        return new JAXBElement<XACMLAuthzDecisionQueryType>(_XACMLAuthzDecisionQuery_QNAME, XACMLAuthzDecisionQueryType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XACMLPolicyQueryType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:xacml:2.0:saml:protocol:schema:os", name = "XACMLPolicyQuery")
    public JAXBElement<XACMLPolicyQueryType> createXACMLPolicyQuery(XACMLPolicyQueryType value) {
        return new JAXBElement<XACMLPolicyQueryType>(_XACMLPolicyQuery_QNAME, XACMLPolicyQueryType.class, null, value);
    }

}
