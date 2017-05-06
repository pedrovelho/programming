/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package org.objectweb.proactive.extensions.gcmdeployment.GCMApplication;

import static org.objectweb.proactive.extensions.gcmdeployment.GCMDeploymentLoggers.GCMA_LOGGER;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.objectweb.proactive.core.ProActiveException;
import org.objectweb.proactive.core.ProActiveTimeoutException;
import org.objectweb.proactive.core.node.Node;
import org.objectweb.proactive.core.remoteobject.AbstractRemoteObjectFactory;
import org.objectweb.proactive.core.remoteobject.RemoteObject;
import org.objectweb.proactive.core.remoteobject.RemoteObjectFactory;
import org.objectweb.proactive.core.remoteobject.RemoteObjectHelper;
import org.objectweb.proactive.core.remoteobject.adapter.Adapter;
import org.objectweb.proactive.core.remoteobject.exception.UnknownProtocolException;
import org.objectweb.proactive.core.util.URIBuilder;
import org.objectweb.proactive.core.util.log.ProActiveLogger;
import org.objectweb.proactive.core.xml.VariableContract;
import org.objectweb.proactive.gcmdeployment.GCMApplication;
import org.objectweb.proactive.gcmdeployment.GCMVirtualNode;
import org.objectweb.proactive.gcmdeployment.Topology;


public class GCMApplicationRemoteObjectAdapter extends Adapter<GCMApplication> implements GCMApplication {
    long deploymentId;

    Set<String> virtualNodeNames;

    URI baseUri;

    @Override
    protected void construct() {
        deploymentId = target.getDeploymentId();
        virtualNodeNames = target.getVirtualNodeNames();

        try {
            RemoteObjectFactory rof;
            rof = AbstractRemoteObjectFactory.getDefaultRemoteObjectFactory();
            baseUri = rof.getBaseURI();
        } catch (UnknownProtocolException e) {
            ProActiveLogger.logImpossibleException(GCMA_LOGGER, e);
        } catch (ProActiveException e) {
            GCMA_LOGGER.error("Failed to determine Remote Object Base URI", e);
            ;
        }
    }

    public VariableContract getVariableContract() {
        return target.getVariableContract();
    }

    @SuppressWarnings("unchecked")
    public GCMVirtualNode getVirtualNode(String vnName) {
        GCMVirtualNode vn = null;
        long deploymentId = target.getDeploymentId();
        String name = deploymentId + "/VirtualNode/" + vnName;

        // Hack. Dunno how to uri.clone()
        // DO NOT use the three args version of buildURI. It silently replaces the hostname.
        URI uri = URIBuilder.buildURI(baseUri.getUserInfo(),
                                      baseUri.getHost(),
                                      name,
                                      baseUri.getScheme(),
                                      baseUri.getPort(),
                                      baseUri.getQuery(),
                                      false);

        try {
            RemoteObject ro = RemoteObjectHelper.lookup(uri);
            vn = (GCMVirtualNode) RemoteObjectHelper.generatedObjectStub(ro);
        } catch (ProActiveException e) {
            GCMA_LOGGER.error("Virtual Node \"" + vnName + "\" is not exported as " + uri);
        }
        return vn;
    }

    public Map<String, GCMVirtualNode> getVirtualNodes() {
        Map<String, GCMVirtualNode> map = new HashMap<String, GCMVirtualNode>();

        for (String vnName : virtualNodeNames) {
            map.put(vnName, this.getVirtualNode(vnName));
        }

        return map;
    }

    public boolean isStarted() {
        return target.isStarted();
    }

    public void kill() {
        target.kill();
    }

    public void startDeployment() {
        target.startDeployment();
    }

    public void updateTopology(Topology topology) throws ProActiveException {
        target.updateTopology(topology);
    }

    public void waitReady() {
        target.waitReady();
    }

    public long getDeploymentId() {
        return target.getDeploymentId();
    }

    public Set<String> getVirtualNodeNames() {
        return target.getVirtualNodeNames();
    }

    public URL getDescriptorURL() {
        return target.getDescriptorURL();
    }

    public List<Node> getAllNodes() {
        return target.getAllNodes();
    }

    public String getDebugInformation() {
        return target.getDebugInformation();
    }

    public Topology getTopology() throws ProActiveException {
        return target.getTopology();
    }

    public void waitReady(long timeout) throws ProActiveTimeoutException {
        target.waitReady(timeout);
    }
}
