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
package nonregressiontest.activeobject.migration.strategy;

import org.objectweb.proactive.ProActive;

import nonregressiontest.descriptor.defaultnodes.TestNodes;

import testsuite.test.FunctionalTest;

public class Test extends FunctionalTest {
    A a;

    public Test() {
        super("Migration strategy",
            "Test migration strategy, with onDeparture and onArrival method");
    }

    /**
     * @see testsuite.test.FunctionalTest#action()
     */
    public void action() throws Exception {
        String[] nodesUrl = new String[3];
        nodesUrl[0] = TestNodes.getLocalVMNode().getNodeInformation().getURL();
        nodesUrl[1] = TestNodes.getSameVMNode().getNodeInformation().getURL();
        nodesUrl[2] = TestNodes.getRemoteVMNode().getNodeInformation().getURL();
        a = (A) ProActive.newActive(A.class.getName(), new Object[] { nodesUrl });
    }

    /**
     * @see testsuite.test.AbstractTest#initTest()
     */
    public void initTest() throws Exception {
    }

    /**
     * @see testsuite.test.AbstractTest#endTest()
     */
    public void endTest() throws Exception {
    }

    public boolean postConditions() throws Exception {
        return (a.getCounter() == 7);
    }
}
