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
package org.jboss.test.identity.federation.bindings.config;

import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import junit.framework.TestCase;

import org.jboss.identity.federation.core.config.IDPType;
import org.jboss.identity.federation.core.config.KeyValueType;
import org.jboss.identity.federation.core.config.MetadataProviderType;
import org.jboss.identity.federation.core.config.TrustType;
import org.jboss.identity.federation.core.util.JAXBUtil;


/**
 * Config for the SAMLv2 Metadata Profile
 * @author Anil.Saldhana@redhat.com
 * @since Apr 22, 2009
 */
public class MetadataConfigUnitTestCase extends TestCase
{
   String config = "config/test-metadata-config-";
   
   @SuppressWarnings("unchecked")
   public void testMetadata() throws Exception
   {
      Object object = this.unmarshall(config + "1.xml");
      assertNotNull("IDP is not null", object);
      assertTrue(object instanceof JAXBElement);
      IDPType idp = ((JAXBElement<IDPType>) object).getValue();
      assertEquals("20000", 20000L, idp.getAssertionValidity());
      assertEquals("somefqn", idp.getRoleGenerator());

      TrustType trust = idp.getTrust();
      assertNotNull("Trust is not null", trust);
      String domains = trust.getDomains();
      assertTrue("localhost trusted", domains.indexOf("localhost") > -1);
      assertTrue("jboss.com trusted", domains.indexOf("jboss.com") > -1);
      
      MetadataProviderType metaDataProvider = idp.getMetaDataProvider();
      assertNotNull("MetadataProvider is not null", metaDataProvider);
      assertEquals("org.jboss.test.somefqn", metaDataProvider.getClassName());
      
      List<KeyValueType> keyValues = metaDataProvider.getOption();
      assertTrue(1 == keyValues.size());
      KeyValueType kvt = keyValues.get(0);
      assertEquals("FileName", kvt.getKey());
      assertEquals("myfile", kvt.getValue());
   }
   
   private Object unmarshall(String configFile) throws Exception
   {
      String schema = "schema/config/jboss-identity-fed.xsd";

      ClassLoader tcl = Thread.currentThread().getContextClassLoader();
      InputStream is = tcl.getResourceAsStream(configFile);
      assertNotNull("Inputstream not null", is);

      Unmarshaller un = 
         JAXBUtil.getValidatingUnmarshaller("org.jboss.identity.federation.core.config",
            schema);
      return un.unmarshal(is);
   }
}