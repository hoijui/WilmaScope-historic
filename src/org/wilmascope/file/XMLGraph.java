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
package org.wilmascope.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 *
 * @author Tim Dwyer
 * @version 1.0
 */
public class XMLGraph {
  private Document doc;
  private DocumentBuilderFactory factory;
  private String fileName;
  private Element root;
  /** Create and load the Graph data structures from the data held
   * in the specified file
   * @param fileName the name of the xml file containing the graph data
   */
  public XMLGraph(String fileName) {
    this.fileName = fileName;
    factory = DocumentBuilderFactory.newInstance();
  }
  public void create() {
    try {
      DocumentBuilder builder = factory.newDocumentBuilder();
      doc = builder.newDocument();
      root = doc.createElement("WilmaGraph");
      Cluster rootCluster = new Cluster();
      root.appendChild(rootCluster.getElement());
      doc.appendChild(root);
    } catch (ParserConfigurationException pce) {
      // Parser with specified options can't be built
      pce.printStackTrace();
    }
  }
  public void load() throws IOException {
    //
    // turn the filename into a fully qualified URL
    //
    String uri = new File(fileName).getAbsolutePath();
    System.out.println(uri);

    factory.setNamespaceAware(true);
    factory.setValidating(true);
    factory.setIgnoringElementContentWhitespace(true);
    factory.setIgnoringComments(true);

    try {
      DocumentBuilder builder = factory.newDocumentBuilder();
      doc = builder.parse(uri);
    } catch (SAXException sxe) {
      // Error generated by this application
      // (or a parser-initialization error)
      Exception x = sxe;
      if (sxe.getException() != null)
        x = sxe.getException();
      x.printStackTrace();
    } catch (ParserConfigurationException pce) {
      // Parser with specified options can't be built
      pce.printStackTrace();

    } catch (IOException ioe) {
      // I/O error
      ioe.printStackTrace();
    }
    root = doc.getDocumentElement();
  }

  public abstract class XMLGraphElement {
    private Element element;
    protected XMLGraphElement(String name) {
      element = doc.createElement(name);
    }
    protected XMLGraphElement(Element element) {
      this.element = element;
    }
    protected Element getElement() {
      return element;
    }
    protected void setAttribute(String attribute, String value) {
      element.setAttribute(attribute, value);
    }
    protected void setAttribute(String attribute, float value) {
      setAttribute(attribute, Float.toString(value));
    }
    protected String getAttribute(String attribute) {
      return element.getAttribute(attribute);
    }
    protected float getFloatAttribute(String attribute) {
      return Float.parseFloat(getAttribute(attribute));
    }
    protected void createElement(String name) {
      element = doc.createElement(name);
    }
    protected NodeList getChildNodes() {
      return element.getChildNodes();
    }
    protected void appendChild(XMLGraphElement child) {
      element.appendChild(child.getElement());
    }
    protected Element[] getChildElements(String tag) {
      // I'd rather use something like getElementsByTagName but that seems to
      // do a pre-order traversal of the entire element tree rather than just
      // searching the immediate children.  Obviously with nested clusters
      // this would cause trouble
      NodeList children = getElement().getChildNodes();
      Element[] elements = new Element[children.getLength()];
      for (int i = 0; i < children.getLength(); i++) {
        elements[i] = (Element) children.item(i);
      }
      return elements;
    }
    protected Properties getProperties() {
      Properties ps = new Properties();
      Element[] children = getChildElements("Property");
      for (int i = 0; i < children.length; i++) {
        Element e = children[i];
        Property p = new Property(e);
        ps.setProperty(p.getKey(), p.getValue());
      }
      return ps;
    }
    protected ViewType getViewType() {
      Element es[] = getChildElements("ViewType");
      if (es.length > 0) {
        return new ViewType(es[0]);
      }
      return null;
    }
    protected ViewType setViewType(String type) {
      ViewType v = new ViewType(type);
      appendChild(v);
      return v;
    }
    protected void setProperties(Properties ps) {
      for (Enumeration keys = ps.keys(); keys.hasMoreElements();) {
        String key = (String) keys.nextElement();
        appendChild(new Property(key, ps.getProperty(key)));
      }
    }
  }
  public class Cluster extends Node {
    protected Cluster() {
      super("Cluster");
    }
    protected Cluster(Element clusterElement) {
      super(clusterElement);
    }
    public void load(Vector nodes, Vector edges, Vector clusters) {
      NodeList children = getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        if (!(children.item(i) instanceof Element)) {
          continue;
        }
        Element child = (Element) children.item(i);
        String tagName = child.getTagName();
        if (tagName.equals("LayoutEngineType")) {
          layoutEngineType = new LayoutEngineType(child);
        } else if (tagName.equals("ViewType")) {
          viewType = new ViewType(child);
        } else if (tagName.equals("Node")) {
          nodes.add(new Node(child));
        } else if (tagName.equals("Edge")) {
          edges.add(new Edge(child));
        } else if (tagName.equals("Cluster")) {
          clusters.add(new Cluster(child));
        }
      }
    }
    public LayoutEngineType setLayoutEngineType(String type) {
      LayoutEngineType t = new LayoutEngineType(type);
      appendChild(t);
      return t;
    }
    public Node addNode() {
      Node node = new Node();
      appendChild(node);
      return node;
    }
    public Cluster addCluster() {
      Cluster newCluster = new Cluster();
      appendChild(newCluster);
      return newCluster;
    }
    public Edge addEdge(String startID, String endID) {
      Edge edge = new Edge(startID, endID);
      appendChild(edge);
      return edge;
    }
    /**
     * @return the ViewType for this cluster previously loaded in a call to
     * {@link #load}
     */
    public ViewType getViewType() {
      return viewType;
    }
    /**
     * @return the LayoutEngineType for this cluster previously loaded in a call to
     * {@link #load}
     */
    public LayoutEngineType getLayoutEngineType() {
      return layoutEngineType;
    }
    ViewType viewType;
    LayoutEngineType layoutEngineType;
  }
  /** wrapper class for Node details
   */
  private static int idCounter = 0;
  public class Node extends XMLGraphElement {
    protected Node() {
      super("Node");
      setAttribute("ID", createNewID());
    }
    // at this stage the following is for clusters
    protected Node(String type) {
      super(type);
    }
    public final String getID() {
      return getAttribute("ID");
    }
    private String createNewID() {
      return "N" + idCounter++;
    }
    protected Node(Element nodeElement) {
      super(nodeElement);
    }
  }
  public class Edge extends XMLGraphElement {
    protected Edge(Element edgeElement) {
      super(edgeElement);
    }
    protected Edge(String startID, String endID) {
      super("Edge");
      setAttribute("StartID", startID);
      setAttribute("EndID", endID);
    }
    public String getStartID() {
      return getAttribute("StartID");
    }
    public String getEndID() {
      return getAttribute("EndID");
    }
  }
  public class Property extends XMLGraphElement {
    protected Property(Element e) {
      super(e);
    }
    protected Property(String key, String value) {
      super("Property");
      setAttribute("Key", key);
      setAttribute("Value", value);
    }
    protected String getKey() {
      return getAttribute("Key");
    }
    protected String getValue() {
      return getAttribute("Value");
    }
  }
  public class LayoutEngineType extends XMLGraphElement {
    protected LayoutEngineType(Element e) {
      super(e);
    }
    protected LayoutEngineType(String name) {
      super("LayoutEngineType");
      setAttribute("Name", name);
    }
    public String getName() {
      return getAttribute("Name");
    }
  }
  public class ViewType extends XMLGraphElement {
    protected ViewType(Element e) {
      super(e);
    }
    protected ViewType(String name) {
      super("ViewType");
      setAttribute("Name", name);
    }
    public String getName() {
      return getAttribute("Name");
    }
  }
  public Cluster getRootCluster() {
    return new Cluster((Element) root.getFirstChild());
  }
  /** write out the updated data
   */
  public void save() {
    try {
      FileWriter out = new FileWriter(fileName);
      // Use a Transformer for output
      TransformerFactory tFactory = TransformerFactory.newInstance();
      Transformer transformer = tFactory.newTransformer();
      transformer.setOutputProperty(
        OutputKeys.DOCTYPE_SYSTEM,
        "WilmaGraph.dtd");
      transformer.setOutputProperty(
        OutputKeys.INDENT,
        "yes");
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(out);
      transformer.transform(source, result);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
