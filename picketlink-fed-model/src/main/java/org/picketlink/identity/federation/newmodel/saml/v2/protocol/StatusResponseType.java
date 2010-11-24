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
package org.picketlink.identity.federation.newmodel.saml.v2.protocol;

import javax.xml.datatype.XMLGregorianCalendar;

import org.picketlink.identity.federation.newmodel.saml.v2.assertion.NameIDType;
import org.picketlink.identity.federation.saml.v2.SAML2Object;
import org.w3c.dom.Element;


/**
 * <p>Java class for StatusResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StatusResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:assertion}Issuer" minOccurs="0"/>
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:protocol}Extensions" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:protocol}Status"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="InResponseTo" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="Version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="IssueInstant" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="Destination" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="Consent" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */ 
public class StatusResponseType implements SAML2Object 
{
   protected NameIDType issuer;
   protected Element signature; 
   protected ExtensionsType extensions; 
   protected StatusType status; 
   protected String id; 
   protected String inResponseTo; 
   protected String version; 
   protected XMLGregorianCalendar issueInstant; 
   protected String destination; 
   protected String consent;

   /**
    * Gets the value of the issuer property.
    * 
    * @return
    *     possible object is
    *     {@link NameIDType }
    *     
    */
   public NameIDType getIssuer() {
      return issuer;
   }

   /**
    * Sets the value of the issuer property.
    * 
    * @param value
    *     allowed object is
    *     {@link NameIDType }
    *     
    */
   public void setIssuer(NameIDType value) {
      this.issuer = value;
   }

   /**
    * Gets the value of the signature property.
    * 
    * @return
    *     possible object is
    *     {@link SignatureType }
    *     
    */
   public Element getSignature() {
      return signature;
   }

   /**
    * Sets the value of the signature property.
    * 
    * @param value
    *     allowed object is
    *     {@link SignatureType }
    *     
    */
   public void setSignature( Element value) {
      this.signature = value;
   }

   /**
    * Gets the value of the extensions property.
    * 
    * @return
    *     possible object is
    *     {@link ExtensionsType }
    *     
    */
   public ExtensionsType getExtensions() {
      return extensions;
   }

   /**
    * Sets the value of the extensions property.
    * 
    * @param value
    *     allowed object is
    *     {@link ExtensionsType }
    *     
    */
   public void setExtensions(ExtensionsType value) {
      this.extensions = value;
   }

   /**
    * Gets the value of the status property.
    * 
    * @return
    *     possible object is
    *     {@link StatusType }
    *     
    */
   public StatusType getStatus() {
      return status;
   }

   /**
    * Sets the value of the status property.
    * 
    * @param value
    *     allowed object is
    *     {@link StatusType }
    *     
    */
   public void setStatus(StatusType value) {
      this.status = value;
   }

   /**
    * Gets the value of the id property.
    * 
    * @return
    *     possible object is
    *     {@link String }
    *     
    */
   public String getID() {
      return id;
   }

   /**
    * Sets the value of the id property.
    * 
    * @param value
    *     allowed object is
    *     {@link String }
    *     
    */
   public void setID(String value) {
      this.id = value;
   }

   /**
    * Gets the value of the inResponseTo property.
    * 
    * @return
    *     possible object is
    *     {@link String }
    *     
    */
   public String getInResponseTo() {
      return inResponseTo;
   }

   /**
    * Sets the value of the inResponseTo property.
    * 
    * @param value
    *     allowed object is
    *     {@link String }
    *     
    */
   public void setInResponseTo(String value) {
      this.inResponseTo = value;
   }

   /**
    * Gets the value of the version property.
    * 
    * @return
    *     possible object is
    *     {@link String }
    *     
    */
   public String getVersion() {
      return version;
   }

   /**
    * Sets the value of the version property.
    * 
    * @param value
    *     allowed object is
    *     {@link String }
    *     
    */
   public void setVersion(String value) {
      this.version = value;
   }

   /**
    * Gets the value of the issueInstant property.
    * 
    * @return
    *     possible object is
    *     {@link XMLGregorianCalendar }
    *     
    */
   public XMLGregorianCalendar getIssueInstant() {
      return issueInstant;
   }

   /**
    * Sets the value of the issueInstant property.
    * 
    * @param value
    *     allowed object is
    *     {@link XMLGregorianCalendar }
    *     
    */
   public void setIssueInstant(XMLGregorianCalendar value) {
      this.issueInstant = value;
   }

   /**
    * Gets the value of the destination property.
    * 
    * @return
    *     possible object is
    *     {@link String }
    *     
    */
   public String getDestination() {
      return destination;
   }

   /**
    * Sets the value of the destination property.
    * 
    * @param value
    *     allowed object is
    *     {@link String }
    *     
    */
   public void setDestination(String value) {
      this.destination = value;
   }

   /**
    * Gets the value of the consent property.
    * 
    * @return
    *     possible object is
    *     {@link String }
    *     
    */
   public String getConsent() {
      return consent;
   }

   /**
    * Sets the value of the consent property.
    * 
    * @param value
    *     allowed object is
    *     {@link String }
    *     
    */
   public void setConsent(String value) {
      this.consent = value;
   }

}