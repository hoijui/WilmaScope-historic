package org.wilmascope.columnlayout;

import java.util.Properties;

import org.wilmascope.graph.LayoutEngine;
import org.wilmascope.graph.Cluster;
import org.wilmascope.graph.Node;
import org.wilmascope.graph.NodeList;

import javax.swing.JPanel;
import javax.vecmath.Point3f;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

/**
 * Column layout simply places each node in the cluster at the same x,y position
 * as the root cluster but with the z determined by each node's stratum
 */
public class ColumnLayout implements LayoutEngine {

	public ColumnLayout(Cluster root) {
		this.root = root;
		root.setLayoutEngine(this);
		strataSeparation = 1f;
	}

	public void calculateLayout() {
	}
	public boolean applyLayout() {
		Point3f rootPosition = root.getPosition();
		NodeList nodes = root.getNodes();
		root.setMass(1f);
		for (nodes.resetIterator(); nodes.hasNext();) {
			Node node = nodes.nextNode();
			Point3f position = node.getPosition();
			NodeColumnLayout nodeLayout = (NodeColumnLayout) node.getLayout();
			position.x = rootPosition.x;
			position.y = rootPosition.y;
			position.z = rootPosition.z + strataSeparation * nodeLayout.getStratum();
		}
		return true;
	}
	public void setBalanced(boolean balanced) {
		/**@todo: Implement this org.wilmascope.graph.LayoutEngine method*/
		throw new java.lang.UnsupportedOperationException(
			"Method setBalanced() not yet implemented.");
	}
	public org.wilmascope.graph.NodeLayout createNodeLayout(Node n) {
		return new NodeColumnLayout(strataCount++);
	}
	public org.wilmascope.graph.EdgeLayout createEdgeLayout(
		org.wilmascope.graph.Edge e) {
		return new EdgeColumnLayout();
	}
	public void setBaseStratum(int stratum) {
		baseStratum = strataCount = stratum;
	}
	public int getBaseStratum() {
		return baseStratum;
	}
	public int getStrataCount() {
		return strataCount;
	}
	public void skipStratum() {
		strataCount++;
	}
	public void reset() {
	}
	Cluster root;
  public void setStrataSeparation(float strataSeparation) {
    this.strataSeparation = strataSeparation;
  }
  public float getStrataSeparation() {
    return strataSeparation;
  }
  public float getHeight() {
    NodeList nodes = root.getAllNodes();
    float zMax = Float.MIN_VALUE, zMin = Float.MAX_VALUE;
    for (nodes.resetIterator(); nodes.hasNext();) {
      org.wilmascope.graph.Node n = nodes.nextNode();
      Point3f t = n.getPosition();
      if (t.z > zMax) {
        zMax = t.z;
      }
      if (t.z < zMin) {
        zMin = t.z;
      }
    }
    zMax+=((NodeColumnLayout)nodes.get(nodes.size()-1).getLayout()).getHeight()/2;
    zMin-=((NodeColumnLayout)nodes.get(0).getLayout()).getHeight()/2;
    return zMax - zMin;
  }
	private float strataSeparation;
	int strataCount = 0;
	int baseStratum = 0;
	/* (non-Javadoc)
	 * @see org.wilmascope.graph.LayoutEngine#getControls()
	 */
	public JPanel getControls() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.wilmascope.graph.LayoutEngine#getProperties()
	 */
	public Properties getProperties() {
    Properties p = new Properties();
    p.setProperty("BaseLevel", "" + getBaseStratum());
    p.setProperty("LevelSeparation", ""+getStrataSeparation());
		return p;
	}

	/* (non-Javadoc)
	 * @see org.wilmascope.graph.LayoutEngine#setProperties(java.util.Properties)
	 */
	public void setProperties(Properties p) {
    setBaseStratum(Integer.parseInt(p.getProperty("BaseLevel","0")));
    setStrataSeparation(Float.parseFloat(p.getProperty("LevelSeparation","1.0")));
	}
}