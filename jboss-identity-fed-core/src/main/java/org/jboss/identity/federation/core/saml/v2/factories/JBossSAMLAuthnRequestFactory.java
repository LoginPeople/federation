/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors. 
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.identity.federation.core.saml.v2.factories;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jboss.identity.federation.core.exceptions.ConfigurationException;
import org.jboss.identity.federation.core.saml.v2.constants.JBossSAMLConstants;
import org.jboss.identity.federation.core.saml.v2.util.XMLTimeUtil;
import org.jboss.identity.federation.core.util.JAXBUtil;
import org.jboss.identity.federation.saml.v2.assertion.NameIDType;
import org.jboss.identity.federation.saml.v2.protocol.AuthnRequestType;
import org.xml.sax.SAXException;

/**
 * Factory for SAML2 AuthnRequest
 * @author Anil.Saldhana@redhat.com
 * @since Dec 9, 2008
 */
public class JBossSAMLAuthnRequestFactory
{ 
   private static String pkgName = "org.jboss.identity.federation.saml.v2.protocol:org.jboss.identity.xmlsec.w3.xmldsig";
   private static String schemaLocation = "schema/saml/v2/saml-schema-protocol-2.0.xsd";
   
   /**
    * Create a AuthnRequestType
    * @return
    */
   public static AuthnRequestType createAuthnRequestType()
   {
      AuthnRequestType authnRequestType = SAMLProtocolFactory.getObjectFactory().createAuthnRequestType();
      return authnRequestType;
   }
   
   /**
    * Create an AuthnRequestType
    * @param id Id of the request
    * @param assertionConsumerURL URL of the requestor where the response assertion is requested
    * @param issuerValue URL of the issuer
    * @return
    * @throws ConfigurationException  
    */
   public static AuthnRequestType createAuthnRequestType(String id, 
         String assertionConsumerURL, String destination, String issuerValue) throws ConfigurationException  
   {
      XMLGregorianCalendar issueInstant = XMLTimeUtil.getIssueInstant(); 
      
      AuthnRequestType authnRequest = SAMLProtocolFactory.getObjectFactory().createAuthnRequestType();
      authnRequest.setID(id);
      authnRequest.setVersion(JBossSAMLConstants.VERSION_2_0.get());
      authnRequest.setAssertionConsumerServiceURL(assertionConsumerURL);
      authnRequest.setProtocolBinding(JBossSAMLConstants.HTTP_POST_BINDING.get());
      authnRequest.setDestination(destination);
      authnRequest.setIssueInstant(issueInstant);
      
      //Create an issuer 
      NameIDType issuer = JBossSAMLBaseFactory.createNameID();
      issuer.setValue(issuerValue);
      
      authnRequest.setIssuer(issuer);
      
      return authnRequest;
      
   }
   
   /**
    * Create a JAXBElement for the AuthnRequestType
    * @param authnRequestType
    * @return
    */
   public static JAXBElement<AuthnRequestType> createAuthnRequestType(AuthnRequestType authnRequestType)
   {
      return SAMLProtocolFactory.getObjectFactory().createAuthnRequest(authnRequestType);
   } 
   
   /**
    * Get the validating marshaller
    * @param schemaValidation Whether schema validation is needed
    * @return
    * @throws JAXBException 
    * @throws SAXException  
    */
   public static Marshaller getValidatingMarshaller(boolean schemaValidation) throws SAXException, JAXBException 
   {
      if(schemaValidation)
         return JAXBUtil.getValidatingMarshaller(pkgName, schemaLocation);
      else
         return JAXBUtil.getMarshaller(pkgName);
   }
   
   /**
    * Get the validating unmarshaller
    * @param schemaValidation whether schema validation is needed
    * @return
    * @throws SAXException 
    * @throws JAXBException  
    */
   public static Unmarshaller getValidatingUnmarshaller(boolean schemaValidation) throws JAXBException, SAXException 
   {
      if(schemaValidation)
         return JAXBUtil.getValidatingUnmarshaller(pkgName, schemaLocation);
      else
         return JAXBUtil.getUnmarshaller(pkgName);
   }
}