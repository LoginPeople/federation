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
package org.jboss.identity.federation.core.saml.v2.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.identity.federation.core.config.KeyValueType;
import org.jboss.identity.federation.core.exceptions.ConfigurationException;
import org.jboss.identity.federation.core.handler.config.Handler;
import org.jboss.identity.federation.core.handler.config.Handlers;
import org.jboss.identity.federation.core.saml.v2.impl.DefaultSAML2HandlerConfig;
import org.jboss.identity.federation.core.saml.v2.interfaces.SAML2Handler;
import org.jboss.identity.federation.core.saml.v2.interfaces.SAML2HandlerConfig;

/**
 * Deals with SAML2 Handlers
 * @author Anil.Saldhana@redhat.com
 * @since Oct 7, 2009
 */
public class HandlerUtil
{
   public static Set<SAML2Handler> getHandlers(Handlers handlers) throws ConfigurationException
   {
      if(handlers == null)
         throw new IllegalArgumentException("handlers is null");
      List<Handler> handlerList = handlers.getHandler();

      Set<SAML2Handler> handlerSet = new HashSet<SAML2Handler>();

      for(Handler handler : handlerList)
      {
         String clazzName = handler.getClazz();

         ClassLoader tcl = SecurityActions.getContextClassLoader();
         Class<?> clazz;
         try
         {
            clazz = tcl.loadClass(clazzName);

            SAML2Handler samlhandler = (SAML2Handler) clazz.newInstance();
            List<KeyValueType> options = handler.getOption();

            Map<String, Object> mapOptions = new HashMap<String, Object>();

            for(KeyValueType kvtype : options)
            {
               mapOptions.put(kvtype.getKey(), kvtype.getValue());
            }
            SAML2HandlerConfig handlerConfig = new DefaultSAML2HandlerConfig();
            handlerConfig.set(mapOptions);
            
            samlhandler.initHandlerConfig(handlerConfig);

            handlerSet.add(samlhandler);
         }
         catch (ClassNotFoundException e)
         {
            throw new ConfigurationException(e);
         }
         catch (InstantiationException e)
         {
            throw new ConfigurationException(e);
         }
         catch (IllegalAccessException e)
         {
            throw new ConfigurationException(e);
         }
      } 
      
      return handlerSet;
   }

}