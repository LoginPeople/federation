//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-661 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.08 at 05:45:20 PM CST 
//


package org.jboss.identity.federation.saml.v2.ac.classes.mobileonefactorcontract;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuthenticatorBaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthenticatorBaseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{urn:oasis:names:tc:SAML:2.0:ac:classes:MobileOneFactorContract}AuthenticatorBaseType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:MobileOneFactorContract}DigSig"/>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:MobileOneFactorContract}ZeroKnowledge"/>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:MobileOneFactorContract}SharedSecretChallengeResponse"/>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:MobileOneFactorContract}SharedSecretDynamicPlaintext"/>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:MobileOneFactorContract}AsymmetricDecryption"/>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:MobileOneFactorContract}AsymmetricKeyAgreement"/>
 *         &lt;/choice>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:ac:classes:MobileOneFactorContract}Extension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthenticatorBaseType")
public class AuthenticatorBaseType
    extends OriginalAuthenticatorBaseType
{


}
