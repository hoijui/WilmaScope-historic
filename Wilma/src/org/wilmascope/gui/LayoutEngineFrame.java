package org.wilmascope.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.wilmascope.control.GraphControl;
import org.wilmascope.graph.Cluster;
import org.wilmascope.graph.LayoutEngine;
import org.wilmascope.layoutregistry.LayoutManager;
import org.wilmascope.layoutregistry.LayoutManager.UnknownLayoutTypeException;

/**
 * @author dwyer
 *
 * A frame with a panel for selecting the layout engine, and a panel
 * for controls specific to that engine
 */
public class LayoutEngineFrame extends JFrame {
  private Box box1;
  private JPanel jPanel1 = new JPanel();
  private JComboBox layoutEngineComboBox;
  private JPanel controlsPanel;
  GraphControl.ClusterFacade cluster;
  public LayoutEngineFrame(GraphControl.ClusterFacade cluster, String title) {
    setTitle (title);
    LayoutEngine layoutEngine = cluster.getLayoutEngine();
    layoutEngineComboBox = new JComboBox(LayoutManager.getInstance().getTypeList());
    controlsPanel=layoutEngine.getControls();
    this.cluster = cluster;
    ImageIcon icon = new ImageIcon("images/forces.png");
    this.setIconImage(icon.getImage());
    box1 = Box.createVerticalBox();
    layoutEngineComboBox.setSelectedItem(layoutEngine.getName());
    layoutEngineComboBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        layoutEngineComboBox_actionPerformed(e);
      }
    });
    this.getContentPane().add(box1, BorderLayout.CENTER);
    box1.add(jPanel1, null);
    box1.add(controlsPanel);
    jPanel1.add(layoutEngineComboBox, null);
    pack();
  }

  void layoutEngineComboBox_actionPerformed(ActionEvent e) {
    String s = (String)layoutEngineComboBox.getSelectedItem();
    box1.remove(controlsPanel);
    Cluster c = cluster.getCluster();
    LayoutEngine layoutEngine=null;
    try {
      layoutEngine = LayoutManager.getInstance().createLayout(s);
    } catch (UnknownLayoutTypeException e1) {
      e1.printStackTrace();
    }
    c.setLayoutEngine(layoutEngine);
    controlsPanel = layoutEngine.getControls();
    box1.add(controlsPanel);
    pack();
  }
}
