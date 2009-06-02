/*
 * ################################################################
 *
 * ProActive: The Java(TM) library for Parallel, Distributed,
 *            Concurrent computing with Security and Mobility
 *
 * Copyright (C) 1997-2009 INRIA/University of Nice-Sophia Antipolis
 * Contact: proactive@ow2.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version
 * 2 of the License, or any later version.
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
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 * ################################################################
 * $$PROACTIVE_INITIAL_DEV$$
 */
package org.objectweb.proactive.ic2d.timit.actions;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.objectweb.proactive.ic2d.timit.Activator;
import org.objectweb.proactive.ic2d.timit.data.BasicChartObject;
import org.objectweb.proactive.ic2d.timit.data.tree.TimerTreeHolder;


/**
 * This action is used when the user wants to view the timer tree of an active object.
 * @author The ProActive Team
 *
 */
public class ShowInTreeViewAction extends Action {
    public static final String SHOW_IN_TREE_VIEW_ACTION = "Show in Tree View";
    private BasicChartObject target;

    public ShowInTreeViewAction() {
        super.setId(SHOW_IN_TREE_VIEW_ACTION);
        super.setImageDescriptor(ImageDescriptor.createFromURL(FileLocator.find(Activator.getDefault()
                .getBundle(), new Path("icons/treeview.gif"), null)));
        super.setToolTipText(SHOW_IN_TREE_VIEW_ACTION);
        super.setEnabled(false);
    }

    public final void setTarget(final BasicChartObject target) {
        super.setEnabled(true);
        this.target = target;
    }

    @Override
    public final void run() {
        IWorkbench iworkbench = PlatformUI.getWorkbench();
        IWorkbenchWindow currentWindow = iworkbench.getActiveWorkbenchWindow();
        IWorkbenchPage page = currentWindow.getActivePage();
        try {
            page.showView("org.objectweb.proactive.ic2d.timit.views.TimerTreeView");
            if (target != null) {
                TimerTreeHolder.getInstance().provideChartObject(target, false);
                target = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}