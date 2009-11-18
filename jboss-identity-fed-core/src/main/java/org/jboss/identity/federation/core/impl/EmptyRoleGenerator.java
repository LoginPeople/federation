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
package org.jboss.identity.federation.core.impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.jboss.identity.federation.core.interfaces.RoleGenerator;

/**
 * A Role Generator that generates no roles
 * @author Anil.Saldhana@redhat.com
 * @since Oct 12, 2009
 */
public class EmptyRoleGenerator implements RoleGenerator
{
   /**
    * @see {@code RoleGenerator#generateRoles(Principal)}
    */
   public List<String> generateRoles(Principal principal)
   {
      return new ArrayList<String>();
   }
}