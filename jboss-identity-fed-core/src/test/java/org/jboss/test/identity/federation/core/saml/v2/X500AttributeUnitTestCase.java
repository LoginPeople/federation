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
package org.jboss.test.identity.federation.core.saml.v2;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;

import junit.framework.TestCase;

import org.jboss.identity.federation.core.constants.AttributeConstants;
import org.jboss.identity.federation.core.saml.v2.common.IDGenerator;
import org.jboss.identity.federation.core.saml.v2.constants.JBossSAMLURIConstants;
import org.jboss.identity.federation.core.saml.v2.factories.JBossSAMLAuthnResponseFactory;
import org.jboss.identity.federation.core.saml.v2.factories.SAMLProtocolFactory;
import org.jboss.identity.federation.core.saml.v2.holders.IDPInfoHolder;
import org.jboss.identity.federation.core.saml.v2.holders.IssuerInfoHolder;
import org.jboss.identity.federation.core.saml.v2.holders.SPInfoHolder;
import org.jboss.identity.federation.core.saml.v2.util.DocumentUtil;
import org.jboss.identity.federation.core.saml.v2.util.StatementUtil;
import org.jboss.identity.federation.saml.v2.assertion.AssertionType;
import org.jboss.identity.federation.saml.v2.assertion.AttributeStatementType;
import org.jboss.identity.federation.saml.v2.protocol.ResponseType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Unit test the X500 Profile of SAML2
 * @author Anil.Saldhana@redhat.com
 * @since Sep 14, 2009
 */
public class X500AttributeUnitTestCase extends TestCase
{
   public void testX500Marshalling() throws Exception
   {
      Map<String,Object> attributes = new HashMap<String, Object>();
      attributes.put(AttributeConstants.EMAIL_ADDRESS, "test@a");
      attributes.put(AttributeConstants.GIVEN_NAME, "anil");
      
      AttributeStatementType attrStat = StatementUtil.createAttributeStatement(attributes);
      
      IssuerInfoHolder issuerHolder = new IssuerInfoHolder("http://idp");
      issuerHolder.setStatusCode(JBossSAMLURIConstants.STATUS_SUCCESS.get());
      
      IDPInfoHolder idp = new IDPInfoHolder();
      idp.setNameIDFormatValue(IDGenerator.create());
      
      ResponseType rt = JBossSAMLAuthnResponseFactory.createResponseType("response111",
             new SPInfoHolder(), idp, issuerHolder);
      assertNotNull(rt);
      
      AssertionType assertion = (AssertionType) rt.getAssertionOrEncryptedAssertion().get(0);
      assertion.getStatementOrAuthnStatementOrAuthzDecisionStatement().add(attrStat);
      
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      
      Marshaller marshaller = JBossSAMLAuthnResponseFactory.getValidatingMarshaller(false);
      JAXBElement<ResponseType> jaxb = SAMLProtocolFactory.getObjectFactory().createResponse(rt);
      marshaller.marshal(jaxb, baos);
      //marshaller.marshal(jaxb, System.out);
      
      Document samlDom = DocumentUtil.getDocument(new String(baos.toByteArray()));
      NodeList nl = samlDom.getElementsByTagName("Attribute");     
      assertEquals("nodes = 2", 2, nl.getLength());
      
      String x500NS = JBossSAMLURIConstants.X500_NSURI.get();
      String encodingLocalName = "Encoding";
      
      Element attrib = (Element) nl.item(0);
      assertTrue("Has ldap encoding?", attrib.hasAttributeNS( x500NS, encodingLocalName));
      assertEquals("LDAP", 
            attrib.getAttributeNodeNS(x500NS, encodingLocalName).getNodeValue()); 
      
      NodeList nla = 
         attrib.getElementsByTagNameNS(JBossSAMLURIConstants.ASSERTION_NSURI.get(),
               "AttributeValue");
      
      Node attribNode = nla.item(0);
      String nodeValue = attribNode.getTextContent();
      assertTrue(nodeValue.equals("test@a") || nodeValue.equals("anil"));
   }
}