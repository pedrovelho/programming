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
package org.objectweb.proactive.core.group.topology;

import org.objectweb.proactive.core.group.Group;
import org.objectweb.proactive.core.group.ProxyForGroup;
import org.objectweb.proactive.core.mop.ConstructionOfReifiedObjectFailedException;


/**
 * This class represents a group by a cycling two-dimensional topology.
 *
 * @author The ProActive Team
 */

public class Torus<E> extends Ring<E> { // implements Topology2D {

    /**
     * 
     */
    private static final long serialVersionUID = 430L;
    /** height of the two-dimensional topology group */
    protected int height; //  => Y => number of Rings

    /**
     * Construtor. The members of <code>g</code> are used to fill the topology group.
     * @param g - the group used a base for the new group (topology)
     * @param height - the heigth of the two-dimensional topology group
     * @param width - the width of the two-dimensional topology group
     * @throws ConstructionOfReifiedObjectFailedException
     */
    public Torus(Group<E> g, int height, int width) throws ConstructionOfReifiedObjectFailedException {
        super(g, height * width);
        this.height = height;
        this.width = width;
    }

    /**
     * Construtor. The members of <code>g</code> are used to fill the topology group.
     * @param g - the group used a base for the new group (topology)
     * @param nbMembers - the number of members of this Torus
     * @throws ConstructionOfReifiedObjectFailedException
     */
    protected Torus(Group<E> g, int nbMembers) throws ConstructionOfReifiedObjectFailedException {
        super(g, nbMembers);
    }

    /**
     * Return the max size of the Ring
     * @return the max size of the one-dimensional topology group (i.e. the Ring)
     */
    @Override
    public int getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the two-dimensional topology group (number of Rings)
     * @return the height of the two-dimensional topology group
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Returns the coordonate X for the specified position, according to the dimension
     * @param position
     * @return the coordonate X
     */
    private int getX(int position) {
        return position % this.width;
    }

    /**
     * Returns the coordonate Y for the specified position, according to the dimension
     * @param position
     * @return the coordonate Y
     */
    private int getY(int position) {
        return position % this.height;
    }

    /**
     * Returns the X-coordonate of the specified object
     * @param o - the object
     * @return the position (in X) of the object in the Torus
     */
    @Override
    public int getX(Object o) {
        return this.indexOf(o) % this.getWidth();
    }

    /**
     * Returns the Y-coordonate of the specified object
     * @param o - the object
     * @return the position (in Y) of the object in the Torus
     */
    public int getY(Object o) {
        return this.indexOf(o) / this.getWidth();
    }

    /**
     * Returns the Ring (one-dimensional topology group) with the specified number
     * @param Ring - the number of the Ring
     * @return the one-dimensional topology group formed by the Ring in the two-dimensional topology group, return <code>null</code> if the the specified Ring is incorrect
     */
    public Ring<E> Ring(int Ring) {
        if ((Ring < 0) || (Ring > this.getWidth())) {
            return null;
        }
        ProxyForGroup<E> tmp = null;
        try {
            tmp = new ProxyForGroup<E>(this.getTypeName());
        } catch (ConstructionOfReifiedObjectFailedException e) {
            e.printStackTrace();
        }
        int begining = Ring * this.getWidth();
        for (int i = begining; i < (begining + this.getWidth()); i++) {
            tmp.add(this.get(i));
        }
        Ring<E> result = null;
        try {
            result = new Ring<E>(tmp, this.getWidth());
        } catch (ConstructionOfReifiedObjectFailedException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Returns the Ring that contains the specified object
     * @param o - the object
     * @return the one-dimensional topology group formed by the Ring of the object in the two-dimensional topology group
     */
    public Ring<E> Ring(Object o) {
        return this.Ring(this.getY(this.indexOf(o)));
    }

    /**
     * Returns the column (one-dimensional topology group) with the specified number
     * @param column - the number of the Ring
     * @return the one-dimensional topology group formed by the column in the two-dimensional topology group, return <code>null</code> if the the specified Ring is incorrect
     */
    public Ring<E> column(int column) {
        if ((column < 0) || (column > this.getHeight())) {
            return null;
        }
        ProxyForGroup<E> tmp = null;
        try {
            tmp = new ProxyForGroup<E>(this.getTypeName());
        } catch (ConstructionOfReifiedObjectFailedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < this.getHeight(); i++) {
            tmp.add(this.get(column + (i * this.getWidth())));
        }
        Ring<E> result = null;
        try {
            result = new Ring<E>(tmp, this.getWidth());
        } catch (ConstructionOfReifiedObjectFailedException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Returns the column that contains the specified object
     * @param o - the object
     * @return the one-dimensional topology group formed by the column of the object in the two-dimensional topology group
     */
    public Ring<E> column(Object o) {
        return this.column(this.getX(this.indexOf(o)));
    }

    /**
     * Returns the object at the left of the specified object in the two-dimensional topology group
     * @param o - the specified object
     * @return the object at the left of <code>o<code>.
     */
    @Override
    public Object left(Object o) {
        int pos = this.indexOf(o);
        if ((pos % this.getWidth()) == 0) {
            return this.get(pos + (this.width - 1));
        } else {
            return this.get(pos - 1);
        }
    }

    /**
     * Returns the object at the right of the specified object in the two-dimensional topology group
     * @param o - the specified object
     * @return the object at the right of <code>o<code>.
     */
    @Override
    public Object right(Object o) {
        int pos = this.indexOf(o);
        if ((pos % this.getWidth()) == (this.getWidth() - 1)) {
            return this.get(pos - (this.width - 1));
        } else {
            return this.get(pos + 1);
        }
    }

    /**
     * Returns the object at the up of the specified object in the two-dimensional topology group
     * @param o - the specified object
     * @return the object at the up of <code>o<code>.
     */
    public Object up(Object o) {
        int pos = this.indexOf(o);
        if (pos < this.getWidth()) {
            return this.get(pos + ((this.height - 1) * this.width));
        } else {
            return this.get(pos - this.getWidth());
        }
    }

    /**
     * Returns the object at the down of the specified object in the two-dimensional topology group
     * @param o - the specified object
     * @return the object at the down of <code>o<code>.
     */
    public Object down(Object o) {
        int pos = this.indexOf(o);
        if (pos > (((this.getHeight() - 1) * this.getWidth()) - 1)) {
            return this.get(pos - ((this.height - 1) * this.width));
        } else {
            return this.get(pos + this.getWidth());
        }
    }
}
