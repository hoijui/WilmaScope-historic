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
 *
 */
package org.wilmascope.gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.media.j3d.TransparencyAttributes;
import javax.swing.JPanel;
import javax.vecmath.Point2f;
import javax.vecmath.Point3f;

import org.wilmascope.columnlayout.NodeColumnLayout;
import org.wilmascope.forcelayout.ForceLayout;
import org.wilmascope.graph.Cluster;
import org.wilmascope.graph.Edge;
import org.wilmascope.graph.EdgeList;
import org.wilmascope.graph.LayoutEngine;
import org.wilmascope.graph.Node;
import org.wilmascope.graph.NodeList;
import org.wilmascope.view.GraphCanvas;
import org.wilmascope.view.GraphElementView;
import org.wilmascope.view.Renderer2D;
import org.wilmascope.view.View2D;
/**
 * @author dwyer
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DrawingPanel extends JPanel implements Printable {
	public static int RENDER_SLICE = 0;
	public static int RENDER_UNION = 1;
	int renderStyle = RENDER_SLICE;
   
	float orbitSeparation = 1.0f;
	float orbits = 1.0f;	  

	public DrawingPanel(Cluster root, Point3f bottomLeft, Point3f topRight) {
		this.root = root;
		this.bottomLeft = bottomLeft;
		this.topRight = topRight;
		// want the aspect ratio to be preserved but the image scaled to fit either
		// the
		// maximal width (640) or the maximal height (480) depending on which
		// aspect is
		// larger
		float w = topRight.x - bottomLeft.x;
		float h = topRight.y - bottomLeft.y;
		float scale = (w > h) ? 640f / w : 480f / h;
		setPreferredSize(new Dimension((int) (w * scale), (int) (h * scale)));
		setBackground(Color.white);
	}
	public DrawingPanel(Cluster root, Point3f bottomLeft, Point3f topRight,
			int renderStyle) {
		this(root, bottomLeft, topRight);
		this.renderStyle = renderStyle;
	}
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		int w = getWidth();
		int h = getHeight();
		if (bi == null || bi.getWidth() != w || bi.getHeight() != h) {
			bi = (BufferedImage) createImage(w, h);
			Graphics2D big = bi.createGraphics();
			big.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			render(w, h, big);
		}
		// Draws the buffered image to the screen.
		g2.drawImage(bi, 0, 0, this);
	}
	void render(int w, int h, Graphics2D g) {
		if (renderStyle == RENDER_UNION) {
			unionRender(w, h, g);
		} else {
			sliceRender(w, h, g);
		}
	}
	void sliceRender(int w, int h, Graphics2D g) {
		g.setColor(Color.black);
		// Clears the rectangle that was previously drawn.
		g.setBackground(new Color(0.8f, 0.8f, 1f));
		g.clearRect(0, 0, w, h);
		// Draws and fills the newly positioned rectangle to the buffer.
		g.setPaint(Color.black);
		NodeList columns = root.getNodes();
		int orbits = 0; float orbitSep = 0; Point3f clusterCentre = new Point3f();
		for (Node tmp : columns) {
			if (tmp instanceof Cluster) {
				Cluster c = (Cluster) tmp;
				
				NodeList nodes = new NodeList(c.getNodes());
				for (Node n:nodes) {
					float distance = Math.abs(n.getPosition().z - (float) stratum);
					// the following is better because it doesn't rely on column
					// separation being 1f
					if (n.getLayout() instanceof NodeColumnLayout) {
						distance = Math.abs(((NodeColumnLayout) n.getLayout()).getStratum()
								- stratum);
					}
					if (distance < 0.01f) {
						draw2D(w, h, g, n);
					}
				}	
				float distance = Math.abs(c.getPosition().z - (float) stratum);
				if (distance < 0.01f) {
					LayoutEngine l = c.getLayoutEngine();
					if(l instanceof ForceLayout) {
					    ForceLayout fl = (ForceLayout)l;
					    orbits = fl.getOrbits();
					    orbitSep = fl.getOrbitSeparation();
					    clusterCentre = c.getPosition();
					};
				}
				
			} else {
				EdgeList outEdges = tmp.getOutEdges();
				float distance = Math.abs(tmp.getPosition().z - (float) stratum);
				if (distance < 0.01f) {
					draw2D(w, h, g, tmp);
				}
			}
		}

		// Plot the orbits...
		Renderer2D renderer = new Renderer2D(bottomLeft, topRight, w, h);
		for (int i=0; i<orbits; i++) {

			Point2f midPointOnScreen = renderer.getScreenPoint(clusterCentre);
			int midXOnScreen = (int) midPointOnScreen.x;
			int midYOnScreen = (int) midPointOnScreen.y;
			// g.fillRect(midXOnScreen, midYOnScreen, 2, 2);

			Point3f nodePosition = new Point3f((i+1)*orbitSep,0,clusterCentre.z);
			Point2f pointOnCircleOnScreen = renderer.getScreenPoint(nodePosition);
			float xRadiusOnScreen =  pointOnCircleOnScreen.distance(midPointOnScreen);
			nodePosition = new Point3f(0,(i+1)*orbitSep,clusterCentre.z);
			pointOnCircleOnScreen = renderer.getScreenPoint(nodePosition);
			float yRadiusOnScreen =  pointOnCircleOnScreen.distance(midPointOnScreen);
			
			int leftX = midXOnScreen - (int) xRadiusOnScreen;
			int leftY = midYOnScreen - (int) yRadiusOnScreen;
			int width = 2 * (int) xRadiusOnScreen;
			int height = 2 * (int) yRadiusOnScreen;
			
			g.draw(new Ellipse2D.Float(leftX, leftY, width, height));
		}
	}
	

	
	private int getNodeOrbit(Node n) {
		int orbit = 0;
	    String ocS = n.getProperties().getProperty("OrbitConstraint");
	    if(ocS !=null) {
	      orbit = Integer.parseInt(ocS);
	    } else {
			System.err.println("WARNING: Cannot parse orbit constraint!");
	        orbit=1;
	    }
		return orbit;
	}
	/**
	 * @param w
	 * @param h
	 * @param g
	 * @param n
	 */
	private void draw2D(int w, int h, Graphics2D g, Node startNode) {

		// draw the node
		View2D v = (View2D) startNode.getView();
		v.draw2D(new Renderer2D(bottomLeft, topRight, w, h), g, 1f);

		int startNodeLevelConstraint = getLevelConstraintProperty(startNode);

		EdgeList outEdges = startNode.getOutEdges();
		
		for (Edge e:outEdges) {

			Node endNode = e.getEnd();
			int endNodeLevelConstraint = getLevelConstraintProperty(endNode);
			// Show only Edges between on the level
			if (startNodeLevelConstraint != endNodeLevelConstraint) {
				continue;
			}

			// Draw the edge
			v = (View2D) e.getView();
			v.draw2D(new Renderer2D(bottomLeft, topRight, w, h), g, 1f);
		}
	}
	
	private int getLevelConstraintProperty(Node startNode) {
		String strS = startNode.getProperties().getProperty("LevelConstraint");
		int startNodeLevelConstraint = 0;
		if(strS!=null) {
			startNodeLevelConstraint = Integer.parseInt(strS);
		}
		return startNodeLevelConstraint;
	}
	void unionRender(int w, int h, Graphics2D g) {
		g.setColor(Color.black);
		// Clears the rectangle that was previously drawn.
		g.setBackground(Color.white);
		g.clearRect(0, 0, w, h);
		// Draws and fills the newly positioned rectangle to the buffer.
		g.setPaint(Color.black);
		NodeList columns = root.getNodes();
		for (Node col:columns) {
			Cluster c = (Cluster)col;
			NodeList nodes = new NodeList(c.getNodes());
			for (Node n:nodes) {
				EdgeList outEdges = n.getOutEdges();
				View2D v = (View2D) n.getView();
				v.draw2D(new Renderer2D(bottomLeft, topRight, w, h), g, 0.2f);
				for (Edge e:outEdges) {
					v = (View2D)e.getView();
					v.draw2D(new Renderer2D(bottomLeft, topRight, w, h), g, 0.2f);
				}
				//setViewTransparency((float)distance/5f, (GraphElementView)
				// n.getView());
				//for (outEdges.resetIterator(); outEdges.hasNext();) {
				//setViewTransparency((float)distance/5f,(GraphElementView)outEdges.nextEdge().getView());
				//}
			}
		}
	}
	private void setViewTransparency(float transparency, GraphElementView v) {
		GraphCanvas gc = v.getGraphCanvas();
		if (transparency < 1f) {
			v.show(gc);
			v.setTransparencyAttributes(new TransparencyAttributes(
					TransparencyAttributes.NICEST, transparency));
		} else {
			v.hide();
		}
	}
	public int print(Graphics g, PageFormat pf, int pi) throws PrinterException {
		if (pi >= 1) {
			return Printable.NO_SUCH_PAGE;
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		g2d.translate(pf.getImageableWidth() / 2, pf.getImageableHeight() / 2);
		Dimension d = getSize();
		double scale = Math.min(pf.getImageableWidth() / d.width, pf
				.getImageableHeight()
				/ d.height);
		if (scale < 1.0) {
			g2d.scale(scale, scale);
		}
		g2d.translate(-d.width / 2.0, -d.height / 2.0);
		render(d.width, d.height, g2d);
		return Printable.PAGE_EXISTS;
	}
	BufferedImage bi;
	Cluster root;
	Point3f bottomLeft, topRight;
	float stratum = 0;
	/**
	 * @param i
	 */
	public void setStratum(float z) {
		stratum = z;
		bi = null;
		updateUI();

		// Read the parameter for Orbits and OrbitSeparation for this cluster.
		NodeList columns = root.getNodes();
		for (Node tmp : columns) {
			if (tmp instanceof Cluster) {
				Cluster c = (Cluster) tmp;
				NodeList nodes = new NodeList(c.getNodes());
				for (Node n:nodes) {
					float distance = Math.abs(n.getPosition().z - (float) stratum);
					// the following is better because it doesn't rely on column
					// separation being 1f
					if (n.getLayout() instanceof NodeColumnLayout) {
						distance = Math.abs(((NodeColumnLayout) n.getLayout()).getStratum()
								- stratum);
					}
					if (distance < 0.01f) {
					
					    String orbitSepString = c.getLayoutEngine().getProperties().getProperty("OrbitSeparation");
					    if(orbitSepString !=null) {
					      orbitSeparation=Float.parseFloat(orbitSepString);
					    } else {
					      orbitSeparation=1f;
					    }
					    String orbitString = c.getLayoutEngine().getProperties().getProperty("Orbits");
					    if(orbitString !=null) {
					      orbits=Float.parseFloat(orbitString);
					    } else {
					      orbits=1f;
					    }
						// Finished, as we found the correct cluster for this stratum
						break;
					}
				}
			}
		}


	}
}
