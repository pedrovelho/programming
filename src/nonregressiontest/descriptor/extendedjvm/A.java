/*
 * ################################################################
 *
 * ProActive: The Java(TM) library for Parallel, Distributed,
 *            Concurrent computing with Security and Mobility
 *
 * Copyright (C) 1997-2002 INRIA/University of Nice-Sophia Antipolis
 * Contact: proactive-support@inria.fr
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://www.inria.fr/oasis/ProActive/contacts.html
 *  Contributor(s):
 *
 * ################################################################
 */
package nonregressiontest.descriptor.extendedjvm;

/**
 * @author  ProActive Team
 * @version 1.0 
 * @since   ProActive 2.2
 */
public class A {
	
	public A(){}
	
	public String getTiti(){
		return System.getProperty("titi");
	}
	
	public String getToto(){
		return System.getProperty("toto");
	}
	
	public String getTata(){
		return System.getProperty("tata");
	}
	
	public String getClassPath(){
		return System.getProperty("java.class.path");
	}
	
	public String getPolicy(){
		return System.getProperty("java.security.policy");
	}

}
