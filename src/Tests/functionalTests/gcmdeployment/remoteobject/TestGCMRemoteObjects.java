/*
 * ################################################################
 *
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 1997-2010 INRIA/University of 
 * 				Nice-Sophia Antipolis/ActiveEon
 * Contact: proactive@ow2.org or contact@activeeon.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; version 3 of
 * the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * If needed, contact us to obtain a release under GPL Version 2 
 * or a different license than the GPL.
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 * ################################################################
 * $$PROACTIVE_INITIAL_DEV$$
 */
package functionalTests.gcmdeployment.remoteobject;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;
import org.objectweb.proactive.ActiveObjectCreationException;
import org.objectweb.proactive.Body;
import org.objectweb.proactive.InitActive;
import org.objectweb.proactive.api.PAActiveObject;
import org.objectweb.proactive.core.node.Node;
import org.objectweb.proactive.core.node.NodeException;
import org.objectweb.proactive.gcmdeployment.GCMApplication;
import org.objectweb.proactive.gcmdeployment.GCMVirtualNode;

import functionalTests.GCMFunctionalTestDefaultNodes;


public class TestGCMRemoteObjects extends GCMFunctionalTestDefaultNodes {
    public TestGCMRemoteObjects() {
        super(1, 1);
    }

    @Test
    public void testRemote() throws ActiveObjectCreationException, NodeException, InterruptedException {
        Node node = super.getANode();
        AO ao = PAActiveObject.newActive(AO.class, new Object[] { super.gcmad }, node);
        Assert.assertTrue(ao.finished());
    }

    static public class AO implements Serializable, InitActive {
        /**
         * 
         */
        private static final long serialVersionUID = 430L;
        GCMApplication gcma;
        boolean success = true;

        public AO() {

        }

        public AO(GCMApplication gcma) {
            this.gcma = gcma;
        }

        public void initActivity(Body body) {
            try {
                GCMVirtualNode vn1 = gcma.getVirtualNode(GCMFunctionalTestDefaultNodes.VN_NAME);
                if (vn1 == null)
                    success = false;

                boolean atLeastOne = false;
                for (GCMVirtualNode vn : gcma.getVirtualNodes().values()) {
                    atLeastOne = true;
                    for (Node node : vn.getCurrentNodes()) {
                        System.out.println(node.getNodeInformation().getURL());
                    }
                }
                if (atLeastOne != true)
                    success = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public boolean finished() {
            return success;
        }
    }
}
