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

package org.wilmascope.viewplugin;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import javax.media.j3d.GeometryStripArray;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.swing.ImageIcon;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import org.wilmascope.graph.Edge;
import org.wilmascope.view.Colours;
import org.wilmascope.view.Constants;
import org.wilmascope.view.EdgeView;
import org.wilmascope.view.NodeView;
import org.wilmascope.view.Renderer2D;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;

/**
 * Graphical representation of the edge
 *
 * @author Tim Dwyer
 * @version 1.0
 */

public class AttenuatedEdgeView extends EdgeView {
  //
  // create the basic reference geometries from the shape sections of a cylinder
  //
  static float topRadius = 1f, bottomRadius = 1f;
  static Point3f[] tubePoints;
  static int[] tubeStripCounts;
  static NormalGenerator normalGenerator = new NormalGenerator();
  {
    Cylinder c = new Cylinder(1f, 1f);
    GeometryStripArray tubeGeometry = getGeometry(c, Cylinder.BODY);
    GeometryStripArray topGeometry = getGeometry(c, Cylinder.TOP);
    GeometryStripArray bottomGeometry = getGeometry(c, Cylinder.BOTTOM);
    tubePoints = new Point3f[tubeGeometry.getVertexCount()];
    tubeStripCounts = new int[tubeGeometry.getNumStrips()];
    loadGeometry(tubeGeometry, tubeStripCounts, tubePoints);
  }
  private static GeometryStripArray getGeometry(Cylinder c, int section) {
    return (GeometryStripArray) c.getShape(section).getGeometry();
  }
  private static void loadGeometry(
    GeometryStripArray geometry,
    int[] stripCounts,
    Point3f[] points) {
    for (int i = 0; i < points.length; i++) {
      points[i] = new Point3f();
    }
    geometry.getCoordinates(0, points);
    geometry.getStripVertexCounts(stripCounts);
  }

  float length = 1.0f;
  public AttenuatedEdgeView() {
    setTypeName("Attenuated Edge");
  }
  protected void setupDefaultMaterial() {

    Color3f aColor  = new Color3f(0.1f, 0.1f, 0.1f);
    Color3f eColor  = new Color3f(0.0f, 0.0f, 0.0f);
    Color3f dColor  = new Color3f(0.6f, 0.6f, 0.6f);
    Color3f sColor  = new Color3f(1.0f, 1.0f, 1.0f);

    Material m = new Material(aColor, eColor, dColor, sColor, 100.0f);
    m.setLightingEnable(true);
 
    setupDefaultAppearance(m);
  }
  protected void setupHighlightMaterial() {
    setupHighlightAppearance(Colours.yellowMaterial);
  } /**
   * adjust the radius of the top of the tube
   * @parameter r xScale factor, ie the resulting top radius will be r * node radius
   */
  static public void setTopRadius(float r) {
    topRadius = r;
  }
  /**
   * adjust the radius of the bottom of the tube
   * @parameter r xScale factor, ie the resulting bottom radius will be r * node radius
   */
  static public void setBottomRadius(float r) {
    bottomRadius = r;
  }
  public void init() {
    setRadius(0.1f);
    Color3f startColour = new Color3f(((NodeView)getEdge().getStart().getView()).getColour());
    Color3f endColour = new Color3f(((NodeView)getEdge().getEnd().getView()).getColour());
    setTopRadius(getEdge().getStart().getRadius()*9.5f);
    setBottomRadius(getEdge().getEnd().getRadius()*9.5f);

    Point3f[] taperedTubePoints = new Point3f[tubePoints.length];    
    Color3f[] tubeColours = new Color3f[tubePoints.length];

    for (int i = 0; i < tubePoints.length; i++) {
      if (i % 2 == 0) {
        taperedTubePoints[i] =
          new Point3f(
            tubePoints[i].x * topRadius,
            tubePoints[i].y,
            tubePoints[i].z * topRadius);
        tubeColours[i] = endColour;
      } else {
        taperedTubePoints[i] =
          new Point3f(
            -tubePoints[i].x * bottomRadius,
            tubePoints[i].y,
            -tubePoints[i].z * bottomRadius);
        tubeColours[i] = startColour;
      }
    }
    GeometryInfo gi = new GeometryInfo(GeometryInfo.TRIANGLE_STRIP_ARRAY);
    gi.setCoordinates(taperedTubePoints);
    gi.setColors(tubeColours);
    gi.setStripCounts(tubeStripCounts); 
    gi.recomputeIndices();
    normalGenerator.generateNormals(gi);
    Shape3D tubeShape = new Shape3D(gi.getGeometryArray(), getAppearance());
    makePickable(tubeShape);
    addTransformGroupChild(tubeShape);
  }
  /**
   * draw the edge correctly between the start and end nodes
   */
  public void draw() {
    Edge e = getEdge();
    e.recalculate();
    double l = e.getLength();
    // avoids non-affine transformations, by making sure edge always has
    // non-zero length
    if (e.getLength() == 0) {
      e.setVector(Constants.gc.getVector3f("MinVector"));
      l = e.getVector().length();
    }
    Vector3f v = new Vector3f(e.getVector());
    v.scaleAdd(0.5f, e.getStart().getPosition());
    setFullTransform(new Vector3d(getRadius(), l, getRadius()), v, getPositionAngle());
  }
  /**
   * 2D version of attenuated edge is just a two colour solid line.
   *   First half line is coloured same as start node
   *   Second half is coloured as for end node
   */
  public void draw2D(Renderer2D r, Graphics2D g, float transparency) {
    float thickness = r.scaleX(getRadius());
    g.setStroke(new BasicStroke(thickness));
    
    Point3f start = getEdge().getStart().getPosition();
    Point3f end = getEdge().getEnd().getPosition();
    Vector3f v = new Vector3f();
    v.sub(end,start);
    v.scale(0.5f);
    Point3f mid = new Point3f(start);
    mid.add(v);
    g.setColor(((NodeView)getEdge().getStart().getView()).getColour());
    r.linePath(g,start,mid);
    g.setColor(((NodeView)getEdge().getEnd().getView()).getColour());
    r.linePath(g,mid,end);
  }
  public ImageIcon getIcon() {
    return new ImageIcon("images/attenuatedEdge.png");
  }
}
