package org.wilmascope.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.wilmascope.columnlayout.ColumnLayout;
import org.wilmascope.control.GraphControl;
import org.wilmascope.graph.Cluster;
import org.wilmascope.graph.Node;
import org.wilmascope.graph.NodeList;
import org.wilmascope.view.GraphCanvas;

import com.sun.j3d.utils.picking.PickTool;
/**
 * <p>Description: </p>
 * <p>$Id$ </p>
 * <p>@author </p>
 * <p>@version $Revision$</p>
 *  unascribed
 *
 */

public class AxisPlaneControlFrame extends JFrame {

  public AxisPlaneControlFrame() {
    try {
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public AxisPlaneControlFrame(GraphControl gc) {
    canvas = gc.getGraphCanvas();
    root = gc.getRootCluster();
    NodeList l = root.getCluster().getAllNodes();
    for(l.resetIterator();l.hasNext();) {
      Node n = l.nextNode();
      if(n instanceof Cluster) {
        if(((Cluster)n).getLayoutEngine() instanceof ColumnLayout) {
          ColumnLayout c = (ColumnLayout)((Cluster)n).getLayoutEngine();
          strataSeparation = c.getStrataSeparation();
          strataCount = c.getStrataCount();
          break;
        }
      }
    }
    bottomLeft = new Point3f();
    Point3f topRight = new Point3f();
    centroid = new Point3f();
    l.getBoundingBoxCorners(bottomLeft,topRight,centroid);
    float width = topRight.x - bottomLeft.x;
    float height = topRight.y - bottomLeft.y;
    System.out.println("width="+width+",height="+height);
    axisPlaneBG = new BranchGroup();
    axisPlaneBG.setCapability(BranchGroup.ALLOW_DETACH);
    axisPlaneTG = new TransformGroup();
    axisPlaneTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    Appearance ap = new Appearance();
    TransparencyAttributes transparencyAttributes
     = new TransparencyAttributes(TransparencyAttributes.FASTEST, 0.6f);
    Material m = org.wilmascope.view.Colours.greyBlueMaterial;
    ap.setMaterial(m);
    ap.setTransparencyAttributes(transparencyAttributes);
    com.sun.j3d.utils.geometry.Box plane = new com.sun.j3d.utils.geometry.Box(width/1.9f,height/1.9f,0.01f,ap);
    for(int i=0;i<6;i++) {
      Shape3D shape = plane.getShape(i);
      shape.setCapability(Shape3D.ENABLE_PICK_REPORTING);
      try {
        PickTool.setCapabilities(shape, PickTool.INTERSECT_FULL);
      } catch(javax.media.j3d.RestrictedAccessException e) {
        //System.out.println("Not setting bits on already setup shared geometry");
      }
    }
    axisPlaneTG.addChild(plane);
    axisPlaneBG.addChild(axisPlaneTG);
    axisPlaneBG.compile();
    showAxisPlane();

    Transform3D trans = new Transform3D();
    trans.setTranslation(new Vector3f(centroid));
    axisPlaneTG.setTransform(trans);
    
    Cluster r = root.getCluster();
    while(r.getNodes().size()==1) {
      r = (Cluster)r.getNodes().get(0);
    }
    drawingPanel = new DrawingPanel(r, bottomLeft, topRight);
    setSelectedStratum(0);
    jbInit();
  }
  private void jbInit() {
    hBox = Box.createHorizontalBox();
    vBox = Box.createVerticalBox();
    upButton.setText("Up");
    upButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setSelectedStratum(++selectedStratum);
      }
    });
    downButton.setText("Down");
    downButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setSelectedStratum(--selectedStratum);
      }
    });
    showButton.setText("Show");
    showButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        showButton_actionPerformed(e);
      }
    });
    hideButton.setText("Hide");
    hideButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        hideButton_actionPerformed(e);
      }
    });
    printButton.setText("Print");
    printButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        printButton_actionPerformed(e);
      }
    });
    this.getContentPane().add(hBox, null);
    vBox.add(upButton, null);
    vBox.add(downButton, null);
    vBox.add(showButton, null);
    vBox.add(hideButton, null);
    vBox.add(printButton, null);
    hBox.add(vBox);
    hBox.add(drawingPanel);
  }
  
  void setSelectedStratum(int selectedStratum) {
    this.selectedStratum = selectedStratum;
    downButton.setEnabled(selectedStratum==0?false:true);
    upButton.setEnabled(selectedStratum==(strataCount-1)?false:true);
    drawingPanel.setStratum(selectedStratum);
    Transform3D trans = new Transform3D();
    centroid.z=bottomLeft.z+strataSeparation*selectedStratum;
    trans.setTranslation(new Vector3f(centroid));
    axisPlaneTG.setTransform(trans);
  }

  void hideButton_actionPerformed(ActionEvent e) {
    axisPlaneBG.detach();
    hideButton.setEnabled(false);
    showButton.setEnabled(true);
  }

  void showButton_actionPerformed(ActionEvent e) {
    showAxisPlane();
  }
  
  void printButton_actionPerformed(ActionEvent e) {
    PrinterJob printJob = PrinterJob.getPrinterJob();
    printJob.setPrintable(drawingPanel);
    PageFormat pf = printJob.pageDialog(printJob.defaultPage());
    if (printJob.printDialog()) {
      try {
        printJob.print();
      } catch (Exception ex) {
      }
    }
  }
  
  void showAxisPlane() {
    canvas.getTransformGroup().addChild(axisPlaneBG);
    showButton.setEnabled(false);
    hideButton.setEnabled(true);
  }

  void kill() {
    show(false);
    axisPlaneBG.detach();
  }
  Point3f centroid, bottomLeft;
  float strataSeparation;
  int strataCount;
  int selectedStratum = 0;
  GraphCanvas canvas;
  GraphControl.ClusterFacade root;
  TransformGroup axisPlaneTG;
  BranchGroup axisPlaneBG;
  JButton upButton = new JButton();
  JButton downButton = new JButton();
  Box hBox, vBox;
  JButton showButton = new JButton();
  JButton hideButton = new JButton();
  JButton printButton = new JButton();
  DrawingPanel drawingPanel;
}