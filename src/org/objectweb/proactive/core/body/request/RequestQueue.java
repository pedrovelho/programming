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
package org.objectweb.proactive.core.body.request;

import org.objectweb.proactive.core.event.RequestQueueEventListener;

public interface RequestQueue {

  /**
   *   Returns an iterator over all the requests in the request queue. It is up to the programmer
   *   to protect himself against any change in the request queue while using this iterator.
   */
  public java.util.Iterator iterator();

  public boolean isEmpty();

  public int size();

  public boolean hasRequest(String s);

  public void clear();

  /**
   * Returns the oldest request from the queue or null if the queue is empty
   * Do not remove it from the queue
   * @return the oldest request or null
   */
  public Request getOldest();

  /**
   * Returns the oldest request whose method name is s or null if no match
   * Do not remove it from the queue
   * @param methodName the name of the method to look for
   * @return the oldest matching request or null
   */
  public Request getOldest(String methodName);

  /**
   * Returns the oldest request that matches the criteria defined by the given filter
   * Do not remove it from the request line
   * @param requestFilter the filter accepting request on a given criteria
   * @return the oldest matching request or null
   */
  public Request getOldest(RequestFilter requestFilter);

  /**
   * Removes the oldest request from the queue and returns it
   * Null is returned is the queue is empty
   * @return the oldest request or null
   */
  public Request removeOldest();

  /**
   * Removes the oldest request whose method name is s and returns it.
   * Null is returned is no match
   * @param methodName the name of the method to look for
   * @return the oldest matching request or null
   */
  public Request removeOldest(String methodName);

  /**
   * Removes the oldest request that matches the criteria defined by the given filter
   * Null is returned is no match
   * @param requestFilter the filter accepting request on a given criteria
   * @return the oldest matching request or null
   */
  public Request removeOldest(RequestFilter requestFilter);

  /**
   * Returns the youngest request from the queue or null if the queue is empty
   * Do not remove it from the request line
   * @return the youngest request or null
   */
  public Request getYoungest();

  /**
   * Returns the youngest request whose method name is s or null if no match
   * Do not remove it from the request line
   * @param methodName the name of the method to look for
   * @return the youngest matching request or null
   */
  public Request getYoungest(String methodName);

  /**
   * Returns the youngest request that matches the criteria defined by the given filter
   * Do not remove it from the request line
   * @param requestFilter the filter accepting request on a given criteria
   * @return the youngest matching request or null
   */
  public Request getYoungest(RequestFilter requestFilter);

  /**
   * Removes the youngest request from the queue and returns it
   * Null is returned is the queue is empty
   * @return the youngest request or null
   */
  public Request removeYoungest();

  /**
   * Removes the youngest request whose method name is s and returns it.
   * Null is returned is no match
   * @param methodName the name of the method to look for
   * @return the youngest matching request or null
   */
  public Request removeYoungest(String methodName);

  /**
   * Removes the youngest request that matches the criteria defined by the given filter
   * Null is returned is no match
   * @param requestFilter the filter accepting request on a given criteria
   * @return the youngest matching request or null
   */
  public Request removeYoungest(RequestFilter requestFilter);

  /**
   * Adds the given request to the end of the queue
   * @param request the request to add
   */
  public void add(Request request);

  /**
   * Adds the given request to the front of the queue before all 
   * other request already in the queue
   * @param request the request to add
   */
  public void addToFront(Request request);

  /**
   * Processes all requests in the queue using  the given RequestProcessor.
   * Requests are removed from the queue if the method processRequest of the
   * processor return true.
   * @param processor the RequestProcessor to use
   */
  public void processRequests(RequestProcessor processor);

  public void addRequestQueueEventListener(RequestQueueEventListener listener);

  public void removeRequestQueueEventListener(RequestQueueEventListener listener);
}