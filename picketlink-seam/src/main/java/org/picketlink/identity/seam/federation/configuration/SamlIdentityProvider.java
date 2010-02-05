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
package org.picketlink.identity.seam.federation.configuration;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.security.cert.X509Certificate;
import javax.xml.bind.JAXBElement;

import org.picketlink.identity.federation.saml.v2.metadata.IDPSSODescriptorType;
import org.picketlink.identity.federation.saml.v2.metadata.KeyDescriptorType;
import org.picketlink.identity.federation.saml.v2.metadata.KeyTypes;
import org.picketlink.identity.seam.federation.SamlProfile;
import org.picketlink.identity.xmlsec.w3.xmldsig.X509DataType;

/**
* @author Marcel Kolsteren
* @since Dec 31, 2009
*/
public class SamlIdentityProvider
{
   private String entityId;

   private Map<SamlProfile, SamlService> services = new HashMap<SamlProfile, SamlService>();

   private PublicKey publicKey;

   private boolean wantAuthnRequestsSigned;

   private boolean wantSingleLogoutMessagesSigned;

   private boolean singleLogoutMessagesSigned;

   public SamlIdentityProvider(String entityId, IDPSSODescriptorType IDPSSODescriptor)
   {
      this.entityId = entityId;

      wantAuthnRequestsSigned = IDPSSODescriptor.isWantAuthnRequestsSigned();

      services.put(SamlProfile.SINGLE_SIGN_ON, new SamlService(SamlProfile.SINGLE_SIGN_ON, IDPSSODescriptor
            .getSingleSignOnService()));
      services.put(SamlProfile.SINGLE_LOGOUT, new SamlService(SamlProfile.SINGLE_LOGOUT, IDPSSODescriptor
            .getSingleLogoutService()));

      for (KeyDescriptorType keyDescriptor : IDPSSODescriptor.getKeyDescriptor())
      {
         if (keyDescriptor.getUse().equals(KeyTypes.SIGNING))
         {
            for (Object content : keyDescriptor.getKeyInfo().getContent())
            {
               if (content instanceof JAXBElement<?> && ((JAXBElement<?>) content).getValue() instanceof X509DataType)
               {
                  X509DataType X509Data = (X509DataType) ((JAXBElement<?>) content).getValue();
                  for (Object object : X509Data.getX509IssuerSerialOrX509SKIOrX509SubjectName())
                  {
                     if (object instanceof JAXBElement<?>)
                     {
                        JAXBElement<?> el = (JAXBElement<?>) object;
                        if (el.getName().getLocalPart().equals("X509Certificate"))
                        {
                           byte[] certificate = (byte[]) el.getValue();
                           try
                           {
                              X509Certificate cert = X509Certificate.getInstance(certificate);
                              publicKey = cert.getPublicKey();
                           }
                           catch (javax.security.cert.CertificateException e)
                           {
                              throw new RuntimeException(e);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public String getEntityId()
   {
      return entityId;
   }

   public void setEntityId(String entityId)
   {
      this.entityId = entityId;
   }

   public SamlService getService(SamlProfile service)
   {
      return services.get(service);
   }

   public PublicKey getPublicKey()
   {
      return publicKey;
   }

   public void setPublicKey(PublicKey publicKey)
   {
      this.publicKey = publicKey;
   }

   public boolean isWantAuthnRequestsSigned()
   {
      return wantAuthnRequestsSigned;
   }

   public void setWantAuthnRequestsSigned(boolean wantAuthnRequestsSigned)
   {
      this.wantAuthnRequestsSigned = wantAuthnRequestsSigned;
   }

   public boolean isWantSingleLogoutMessagesSigned()
   {
      return wantSingleLogoutMessagesSigned;
   }

   public void setWantSingleLogoutMessagesSigned(boolean wantSingleLogoutMessagesSigned)
   {
      this.wantSingleLogoutMessagesSigned = wantSingleLogoutMessagesSigned;
   }

   public boolean isSingleLogoutMessagesSigned()
   {
      return singleLogoutMessagesSigned;
   }

   public void setSingleLogoutMessagesSigned(boolean singleLogoutMessagesSigned)
   {
      this.singleLogoutMessagesSigned = singleLogoutMessagesSigned;
   }
}
