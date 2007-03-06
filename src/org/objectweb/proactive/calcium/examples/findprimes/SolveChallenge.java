/*
 * ################################################################
 *
 * ProActive: The Java(TM) library for Parallel, Distributed,
 *            Concurrent computing with Security and Mobility
 *
 * Copyright (C) 1997-2007 INRIA/University of Nice-Sophia Antipolis
 * Contact: proactive@objectweb.org
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
package org.objectweb.proactive.calcium.examples.findprimes;

import org.objectweb.proactive.calcium.interfaces.Execute;

public class SolveChallenge implements Execute<Challenge,Primes>{
	
	
	public Primes execute(Challenge param) {
		
		Primes primes = new Primes();
		
		for(int i=param.min;i<=param.max;i++){
			if(isPrime(i)){
				primes.primes.add(new Integer(i));
			}
		}
		
		return primes;
	}
	
	private boolean isPrime(int p){
		
		for(int i=2;i<p;i++){
			if(p%i==0) return false;
		}
		return true;
	}
}
