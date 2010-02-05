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

/**
* @author Marcel Kolsteren
* @since Jan 24, 2010
*/
public class SamlEndpoint
{
   private Binding binding;

   private String location;

   private String responseLocation;

   private SamlService service;

   public SamlEndpoint(SamlService service, Binding binding, String location, String responseLocation)
   {
      super();
      this.service = service;
      this.binding = binding;
      this.location = location;
      this.responseLocation = responseLocation;
   }

   public SamlService getService()
   {
      return service;
   }

   public Binding getBinding()
   {
      return binding;
   }

   public String getLocation()
   {
      return location;
   }

   public String getResponseLocation()
   {
      return responseLocation != null ? responseLocation : location;
   }
}
