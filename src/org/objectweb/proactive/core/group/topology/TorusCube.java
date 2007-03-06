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
package org.objectweb.proactive.core.group.topology;

import org.objectweb.proactive.core.group.Group;
import org.objectweb.proactive.core.group.ProxyForGroup;
import org.objectweb.proactive.core.mop.ConstructionOfReifiedObjectFailedException;


/**
 * This class represents a group by a cycling three-dimensional topology.
 *
 * @author Laurent Baduel
 */
public class TorusCube extends Torus { // implements Topology3D {

    /** depth of the three-dimensional topology group */
    protected int depth; //  => Y => number of Toruss

    /**
     * Construtor. The members of <code>g</code> are used to fill the topology group.
     * @param g - the group used a base for the new group (topology)
     * @param height - the heigth of the three-dimensional topology group
     * @param width - the width of the three-dimensional topology group
     * @param depth - the depth of the three-dimensional topology group
     * @throws ConstructionOfReifiedObjectFailedException
     */
    public TorusCube(Group g, int height, int width, int depth)
        throws ConstructionOfReifiedObjectFailedException {
        super(g, height * width * depth);
        this.height = height;
        this.width = width;
        this.depth = depth;
    }

    /**
     * Returns the width of the three-dimensional topology group (number of cloumns)
     * @return the width of the three-dimensional topology group
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the three-dimensional topology group (number of Rings)
     * @return the height of the three-dimensional topology group
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Returns the height of the three-dimensional topology group (number of Rings)
     * @return the height of the three-dimensional topology group
     */
    public int getDepth() {
        return this.depth;
    }

    /**
     * Returns the coordonate X for the specified position, according to the dimension
     * @param position
     * @return the coordonate X
     */
    private int getX(int position) {
        return (position % (this.width * this.height)) % this.width;
    }

    /**
     * Returns the coordonate Y for the specified position, according to the dimension
     * @param position
     * @return the coordonate Y
     */
    private int getY(int position) {
        return (position % (this.width * this.height)) % this.height;
    }

    /**
     * Returns the coordonate Z for the specified position, according to the dimension
     * @param position
     * @return the coordonate Z
     */
    private int getZ(int position) {
        return position / (this.width * this.height);
    }

    /**
     * Returns the object at the left of the specified object in the three-dimensional topology group
     * @param o - the specified object
     * @return the object at the left of <code>o<code>. If there is no object at the left of <code>o</code>, return <code>null</code>
     */
    public Object left(Object o) {
        int positionX = this.getX(this.indexOf(o));
        if (positionX != 0) {
            return this.get(positionX - 1);
        } else {
            return null;
        }
    }

    /**
     * Returns the object at the right of the specified object in the three-dimensional topology group
     * @param o - the specified object
     * @return the object at the right of <code>o<code>. If there is no object at the right of <code>o</code>, return <code>null</code>
     */
    public Object right(Object o) {
        int positionX = this.getX(this.indexOf(o));
        if (positionX != this.getWidth()) {
            return this.get(positionX + 1);
        } else {
            return null;
        }
    }

    /**
     * Returns the object at the up of the specified object in the three-dimensional topology group
     * @param o - the specified object
     * @return the object at the up of <code>o<code>. If there is no object at the up of <code>o</code>, return <code>null</code>
     */
    public Object up(Object o) {
        int positionY = this.getY(this.indexOf(o));
        if (positionY != 0) {
            return this.get(positionY - this.getWidth());
        } else {
            return null;
        }
    }

    /**
     * Returns the object at the down of the specified object in the three-dimensional topology group
     * @param o - the specified object
     * @return the object at the down of <code>o<code>. If there is no object at the down of <code>o</code>, return <code>null</code>
     */
    public Object down(Object o) {
        int positionY = this.getY(this.indexOf(o));
        if (positionY != this.getHeight()) {
            return this.get(positionY + this.getWidth());
        } else {
            return null;
        }
    }

    /**
     * Returns the object ahead the specified object in the three-dimensional topology group
     * @param o - the specified object
     * @return the object at the down of <code>o<code>. If there is no object at the down of <code>o</code>, return <code>null</code>
     */
    public Object ahead(Object o) {
        int positionZ = this.getZ(this.indexOf(o));
        if (positionZ != 0) {
            return this.get(positionZ - (this.getWidth() * this.getHeight()));
        } else {
            return null;
        }
    }

    /**
     * Returns the object behind the specified object in the three-dimensional topology group
     * @param o - the specified object
     * @return the object at the down of <code>o<code>. If there is no object at the down of <code>o</code>, return <code>null</code>
     */
    public Object behind(Object o) {
        int positionZ = this.getZ(this.indexOf(o));
        if (positionZ != this.getDepth()) {
            return this.get(positionZ + (this.getWidth() * this.getHeight()));
        } else {
            return null;
        }
    }

    /**
     * Returns the horizontal Ring (one-dimensional topology group) that contains the object
     * @param o - the object
     * @return the one-dimensional topology group formed by the horizontal Ring that contains the object in the three-dimensional topology group
     */
    public Ring RingX(Object o) {
        int position = this.indexOf(o);
        int posY = this.getY(position);
        int posZ = this.getZ(position);

        ProxyForGroup tmp = null;
        try {
            tmp = new ProxyForGroup(this.getTypeName());
        } catch (ConstructionOfReifiedObjectFailedException e) {
            e.printStackTrace();
        }

        int begining = (posZ * (this.getHeight() * this.getWidth())) +
            (posY * this.getWidth());
        for (int i = begining; i < (begining + this.getWidth()); i++) {
            tmp.add(this.get(i));
        }
        Ring result = null;
        try {
            result = new Ring((Group) tmp, this.getWidth());
        } catch (ConstructionOfReifiedObjectFailedException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Returns the vertical Ring (kind of column)(one-dimensional topology group) that contains the object
     * @param o - the object
     * @return the one-dimensional topology group formed by the vertical Ring that contains the object in the three-dimensional topology group
     */
    public Ring RingY(Object o) {
        int position = this.indexOf(o);
        int posX = this.getX(position);
        int posZ = this.getZ(position);

        ProxyForGroup tmp = null;
        try {
            tmp = new ProxyForGroup(this.getTypeName());
        } catch (ConstructionOfReifiedObjectFailedException e) {
            e.printStackTrace();
        }

        int begining = (posZ * (this.getHeight() * this.getWidth())) + posX;
        for (int i = 0; i < this.getHeight(); i++) {
            tmp.add(this.get(begining + (i * this.getWidth())));
        }
        Ring result = null;
        try {
            result = new Ring((Group) tmp, this.getWidth());
        } catch (ConstructionOfReifiedObjectFailedException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Returns the Ring in depth (one-dimensional topology group) that contains the object
     * @param o - the object
     * @return the one-dimensional topology group formed by the Ring in depth that contains the object in the three-dimensional topology group
     */
    public Ring RingZ(Object o) {
        int position = this.indexOf(o);
        int posY = this.getY(position);
        int posZ = this.getZ(position);

        ProxyForGroup tmp = null;
        try {
            tmp = new ProxyForGroup(this.getTypeName());
        } catch (ConstructionOfReifiedObjectFailedException e) {
            e.printStackTrace();
        }

        int begining = (posY * this.getWidth()) + posY;
        for (int i = 0; i < this.getDepth(); i++) {
            tmp.add(this.get(begining +
                    (i * (this.getWidth() * this.getHeight()))));
        }
        Ring result = null;
        try {
            result = new Ring((Group) tmp, this.getWidth());
        } catch (ConstructionOfReifiedObjectFailedException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Returns the Torus in X (two-dimensional topology group) that contains the object
     * @param o - the object
     * @return the two-dimensional topology group formed by the Torus in X that contains the object in the three-dimensional topology group
     */
    public Torus TorusX(Object o) {
        ProxyForGroup tmp = null;
        try {
            tmp = new ProxyForGroup(this.getTypeName());
        } catch (ConstructionOfReifiedObjectFailedException e) {
            e.printStackTrace();
        }

        int begining = this.getX(this.indexOf(o));
        ;
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getDepth(); j++) {
                tmp.add(this.get((begining + (i * this.getWidth())) +
                        (j * (this.getWidth() * this.getHeight()))));
            }
        }
        Torus result = null;
        try {
            result = new Torus((Group) tmp, this.getWidth(), this.getDepth());
        } catch (ConstructionOfReifiedObjectFailedException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Returns the Torus in Y (two-dimensional topology group) that contains the object
     * @param o - the object
     * @return the two-dimensional topology group formed by the Torus in Y that contains the object in the three-dimensional topology group
     */
    public Torus TorusY(Object o) {
        ProxyForGroup tmp = null;
        try {
            tmp = new ProxyForGroup(this.getTypeName());
        } catch (ConstructionOfReifiedObjectFailedException e) {
            e.printStackTrace();
        }

        int begining = this.getY(this.indexOf(o)) * this.getWidth();
        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getDepth(); j++) {
                tmp.add(this.get((begining + (i * this.getWidth())) +
                        (j * (this.getWidth() * this.getHeight()))));
            }
        }
        Torus result = null;
        try {
            result = new Torus((Group) tmp, this.getWidth(), this.getDepth());
        } catch (ConstructionOfReifiedObjectFailedException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Returns the Torus in Z (two-dimensional topology group) that contains the object
     * @param o - the object
     * @return the two-dimensional topology group formed by the Torus in Z that contains the object in the three-dimensional topology group
     */
    public Torus TorusZ(Object o) {
        ProxyForGroup tmp = null;
        try {
            tmp = new ProxyForGroup(this.getTypeName());
        } catch (ConstructionOfReifiedObjectFailedException e) {
            e.printStackTrace();
        }

        int begining = this.getZ(this.indexOf(o)) * (this.getWidth() * this.getHeight());
        for (int i = 0; i < (this.getWidth() * this.getHeight()); i++) {
            tmp.add(this.get(begining + i));
        }
        Torus result = null;
        try {
            result = new Torus((Group) tmp, this.getWidth(), this.getDepth());
        } catch (ConstructionOfReifiedObjectFailedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
