/*
 * The following source code is part of the WilmaScope 3D Graph Drawing Engine
 * which is distributed under the terms of the GNU Lesser General Public License
 * (LGPL - http://www.gnu.org/copyleft/lesser.html).
 *
 * As usual we distribute it with no warranties and anything you chose to do
 * with it you do at your own risk.
 *
 * Copyright for this work is retained by Tim Dwyer and the WilmaScope organisation
 * (www.wilmascope.org) however it may be used or modified to work as part of
 * other software subject to the terms of the LGPL.  I only ask that you cite
 * WilmaScope as an influence and inform us (tgdwyer@yahoo.com)
 * if you do anything really cool with it.
 *
 * The WilmaScope software source repository is hosted by Source Forge:
 * www.sourceforge.net/projects/wilma
 *
 * -- Tim Dwyer, 2001
 */
package org.wilmascope.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import org.wilmascope.control.*;

/**
 * A control panel allowing the user to complete or cancel the create cluster
 * action
 * @author Tim Dwyer
 */
import java.util.Vector;
public class ClusterPanel extends MultiPickPanel {

  public ClusterPanel(ControlPanel controlPanel, GraphControl.ClusterFacade cluster) {
    super(controlPanel, cluster);
  }

  void okButton_actionPerformed(ActionEvent e) {
    PickListener pl = GraphControl.getPickListener();
    if(pl.getPickedListSize()==0) {
      return;
    }
    Vector nodes = new Vector();
    while(pl.getPickedListSize()>0) {
      GraphControl.NodeFacade element = (GraphControl.NodeFacade)pl.pop();
      nodes.add(element);
    }
    GraphControl.ClusterFacade newCluster = cluster.addCluster(nodes);
    try {
      newCluster.addForce("Repulsion");
      newCluster.addForce("Spring");
      newCluster.addForce("Origin").setStrength(10f);
    } catch(Exception fe) {
      System.out.println("Couldn't add forces to graph root from WilmaMain, reason: "+fe.getMessage());
    }
    cluster.unfreeze();
    cleanup();
  }
  String getLabel() {
    return "Select nodes to cluster...";
  }
}
