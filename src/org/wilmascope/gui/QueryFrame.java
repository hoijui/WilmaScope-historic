package org.wilmascope.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import org.wilmascope.control.GraphControl;
import org.wilmascope.control.ColumnCluster;
import org.wilmascope.forcelayout.BalancedEventClient;
import org.wilmascope.view.ElementData;
import org.wilmascope.view.EdgeView;
import java.util.*;

/**
 * A demonstration of how to construct graph visualisations from an online
 * query of data from the CityWatch database.
 */
public class QueryFrame extends JFrame {

  public QueryFrame(GraphControl c) {
    this.graphRoot = c.getRootCluster();
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** set up the gui -- autogenerated by JBuilder */
  private void jbInit() throws Exception {
    jLabel2.setText("End Date:");
    endDateField.setPreferredSize(new Dimension(100, 27));
    endDateField.setText("01-apr-01");
    okButton.setText("OK");
    okButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okButton_actionPerformed(e);
      }
    });
    jLabel1.setText("Start Date:");
    fmMovementPanel.setLayout(gridLayout1);
    startDateField.setPreferredSize(new Dimension(100, 27));
    startDateField.setText("01-jan-01");
    gridLayout1.setRows(2);
    gridLayout1.setColumns(2);
    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelButton_actionPerformed(e);
      }
    });
    jLabel3.setText("First Fund Manager:");
    fundmanField.setText("Sharelink");
    fmMovement2Panel.setLayout(gridLayout2);
    jLabel4.setText("Start Date");
    jLabel5.setText("End Date");
    startDate2Field.setText("1-dec-01");
    endDate2Field.setText("12-dec-01");
    gridLayout2.setColumns(2);
    gridLayout2.setRows(6);
    jLabel6.setText("Sector Code");
    sectorField.setText("97");
    wormRadioButton.setText("Worms");
    wormRadioButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        wormRadioButton_actionPerformed(e);
      }
    });
    dotColumnsRadioButton.setActionCommand("dotColumnsRadioButton");
    dotColumnsRadioButton.setSelected(true);
    dotColumnsRadioButton.setText("Dot Columns");
    dotColumnsRadioButton
      .addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dotColumnsRadioButton_actionPerformed(e);
      }
    });
    forceColumnRadioButton.setText("Force Directed Columns");
    forceColumnRadioButton
      .addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        forceColumnRadioButton_actionPerformed(e);
      }
    });
    sectorPanel.setLayout(gridLayout3);
    jLabel7.setText("Start Date");
    jLabel8.setText("End Date");
    sectorStartField.setText("01-dec-2001");
    sectorEndField.setText("12-dec-2001");
    gridLayout3.setColumns(2);
    gridLayout3.setRows(4);
    jLabel9.setText("Visible Edges");
    planarSectorRadioButton.setText("Layout in plane");
    sectorEdgeSlider.setMajorTickSpacing(20);
    sectorEdgeSlider.setMinorTickSpacing(10);
    sectorEdgeSlider.setPaintLabels(true);
    sectorEdgeSlider.setPaintTicks(true);
    this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(okButton, null);
    this.getContentPane().add(queryPane, BorderLayout.NORTH);
    queryPane.add(fmMovementPanel, "FM Movement");
    fmMovementPanel.add(jLabel1, null);
    fmMovementPanel.add(startDateField, null);
    fmMovementPanel.add(jLabel2, null);
    fmMovementPanel.add(endDateField, null);
    queryPane.add(ownershipPanel, "Ownership");
    ownershipPanel.add(jLabel3, null);
    ownershipPanel.add(fundmanField, null);
    queryPane.add(fmMovement2Panel, "FM Movement Across Time");
    fmMovement2Panel.add(jLabel4, null);
    fmMovement2Panel.add(startDate2Field, null);
    fmMovement2Panel.add(jLabel5, null);
    fmMovement2Panel.add(endDate2Field, null);
    fmMovement2Panel.add(jLabel6, null);
    fmMovement2Panel.add(sectorField, null);
    fmMovement2Panel.add(dotColumnsRadioButton, null);
    fmMovement2Panel.add(jPanel3, null);
    fmMovement2Panel.add(forceColumnRadioButton, null);
    fmMovement2Panel.add(jPanel2, null);
    fmMovement2Panel.add(wormRadioButton, null);
    queryPane.add(sectorPanel, "Sector Movement");
    jPanel1.add(cancelButton, null);
    pack();
    styleButtonGroup.add(wormRadioButton);
    styleButtonGroup.add(dotColumnsRadioButton);
    styleButtonGroup.add(forceColumnRadioButton);
    sectorPanel.add(jLabel7, null);
    sectorPanel.add(sectorStartField, null);
    sectorPanel.add(jLabel8, null);
    sectorPanel.add(sectorEndField, null);
    sectorPanel.add(jLabel9, null);
    sectorPanel.add(sectorEdgeSlider, null);
    sectorPanel.add(planarSectorRadioButton, null);
  }

  void cancelButton_actionPerformed(ActionEvent e) {
    this.dispose();
  }

  /** The query for the selected panel is initiated when the OK button is pressed.
   */
  void okButton_actionPerformed(ActionEvent ev) {
    Component selected = queryPane.getSelectedComponent();

    if (selected == fmMovementPanel) {
      fmMovementQuery(startDateField.getText(), endDateField.getText());
    } else if (selected == ownershipPanel) {
      ownershipQuery(fundmanField.getText());
    } else if (selected == fmMovement2Panel) {
      fmMovementAcrossTimeQuery(
        startDate2Field.getText(),
        endDate2Field.getText());
    } else if (selected == sectorPanel) {
      sectorQuery(sectorStartField.getText(), sectorEndField.getText());
    }
    this.hide();
  }
  //====================================================================
  // BEGIN OWNERSHIP VIEW
  Hashtable fmList = new Hashtable();
  Hashtable companyList = new Hashtable();
  /**
   * All nodes in the ownership view have a sub-class of this referenced by
   * their UserData pointer, defining a custom action for Wilma to add to
   * the node's Options menu (the right click menu).
   * We define an action which either expands or collapses the node's neighbours
   * in the bipartite company / fund manager graph.
   */
  public abstract class QueryNodeData extends ElementData {
    /**
     * When the user requests to see the node's neighbours the following method
     * is called, it sets up the action to take when the node is collapsed
     */
    void setExpanded() {
      setActionDescription("Hide Neighbours...");
      setActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          collapseNeighbours();
        }
      });
      expanded = true;
    }

    /**
     * The node's neighbours are hidden, so prepare an action which will expand
     * the node's neighbours when the user requests it.
     */
    void setCollapsed() {
      setActionDescription("Expand Neighbours...");
      setActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          expandNeighbours();
        }
      });
      expanded = false;
    }

    /**
     * add a node to this node's list of neighbours
     */
    void addNeighbour(GraphControl.NodeFacade n, String key) {
      neighbours.put(key, n);
    }

    /**
     * define the following method to properly clean up
     * this node's neighbours on collapsing.
     */
    abstract void collapseNeighbours();
    /**
     * define the following method to perform the appropriate query to expand
     * this node's neighbours
     */
    abstract void expandNeighbours();

    /** an expanded node's neighbours are visible */
    boolean expanded;
    /** shared reference to the SQL connection */
    Statement stmt;
    /** list of neighbours for this node */
    Hashtable neighbours = new Hashtable();
    /** a reference back to the node which uses this class */
    GraphControl.NodeFacade node;
  }

  /**
   * UserData class for company nodes.
   */
  public class CompanyNodeData extends QueryNodeData {
    public CompanyNodeData(
      GraphControl.NodeFacade companyNode,
      Statement stmt,
      String epic) {
      this.stmt = stmt;
      this.epic = epic;
      this.node = companyNode;
      setCollapsed();
    }

    /**
     * Elide the fund manager neighbours of this company that are not referenced
     * by any other visible companies.
     */
    void collapseNeighbours() {
      for (Enumeration e = neighbours.keys(); e.hasMoreElements();) {
        String fmcode = (String) e.nextElement();
        GraphControl.NodeFacade n =
          (GraphControl.NodeFacade) fmList.get(fmcode);

        // if there are no other references to this fund manager then delete it
        // from the visible Wilma graph, remove it from the list of all visible
        // fund manager nodes and remove it from this company node's list of
        // neighbours
        if (n.getDegree() == 1) {
          fmList.remove(fmcode);
          n.delete();
          neighbours.remove(fmcode);
        }
      }
      // allow layout to be recomputed
      graphRoot.unfreeze();
      setCollapsed();
    }

    /** run a query to find and display the neighbours for this company node.
     * if any neighbours are already visible as neighbours of another company
     * then create an edge showing the link to the shared neighbour.
     */
    void expandNeighbours() {
      try {
        ResultSet r =
          stmt.executeQuery(
            "select fmcode, fund_man, shares*share_pric as val "
              + "from holders "
              + "where epic = '"
              + epic
              + "' "
              + "order by val");
        int i = 0;

        while (r.next() && i++ < 10) {
          String fmcode = r.getString("fmcode");
          String fundman = r.getString("fund_man");
          GraphControl.NodeFacade n =
            (GraphControl.NodeFacade) fmList.get(fmcode);

          if (n == null) {
            n = graphRoot.addNode("DefaultNodeView");
            // fund managers are green
            n.setColour(0.0f, 0.8f, 0.0f);
            n.setLabel(fundman);
            n.setPosition(node.getPosition());
            FMNodeData data = new FMNodeData(n, stmt, fmcode);

            data.addNeighbour(node, epic);
            n.setUserData(data);
            fmList.put(fmcode, n);
          }

          if (neighbours.get(fmcode) == null) {
            neighbours.put(fmcode, n);
            GraphControl.EdgeFacade e =
              graphRoot.addEdge(n, node, "Plain Edge", 0.005f);
          }
        }
        graphRoot.unfreeze();
        setExpanded();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    }
    /** UK stock market code for the Company */
    String epic;
  }

  /** NodeData for fund manager nodes, defining actions for expanding and
   *  collapsing the fund managers neighbouring company nodes.
   */
  public class FMNodeData extends QueryNodeData {
    public FMNodeData(
      GraphControl.NodeFacade fmnode,
      Statement stmt,
      String fmcode) {
      this.stmt = stmt;
      this.fmcode = fmcode;
      this.node = fmnode;
      setCollapsed();
    }

    /**
     * Elide the company neighbours of this company that are not referenced
     * by any other visible fund managers.
     */
    void collapseNeighbours() {
      for (Enumeration e = neighbours.keys(); e.hasMoreElements();) {
        String epic = (String) e.nextElement();
        GraphControl.NodeFacade n =
          (GraphControl.NodeFacade) companyList.get(epic);

        if (n.getDegree() == 1) {
          companyList.remove(epic);
          n.delete();
          neighbours.remove(epic);
        }
      }
      graphRoot.unfreeze();
      setCollapsed();
    }

    /** run a query to find and display the neighbours for this fundman node.
     * if any neighbours are already visible as neighbours of another fundman
     * then create an edge showing the link to the shared neighbour.
     */
    void expandNeighbours() {
      try {
        ResultSet r =
          stmt.executeQuery(
            "select epic, full_name, shares*share_pric as val "
              + "from holders "
              + "where fmcode = "
              + fmcode
              + " "
              + "order by val");
        int i = 0;

        while (r.next() && i++ < 10) {
          String epic = r.getString("epic");
          String fullName = r.getString("full_name");
          GraphControl.NodeFacade n =
            (GraphControl.NodeFacade) companyList.get(epic);

          if (n == null) {
            n = graphRoot.addNode("DefaultNodeView");
            n.setLabel(fullName);
            n.setPosition(node.getPosition());
            CompanyNodeData data = new CompanyNodeData(n, stmt, epic);

            data.addNeighbour(node, fmcode);
            n.setUserData(data);
            companyList.put(epic, n);
          }

          if (neighbours.get(epic) == null) {
            neighbours.put(epic, n);
            GraphControl.EdgeFacade e =
              graphRoot.addEdge(node, n, "Plain Edge", 0.005f);
          }
        }
        graphRoot.unfreeze();
        setExpanded();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    }
    /** the unique fund manager code for this node */
    String fmcode;
  }

  void ownershipQuery(String startFundman) {

    fmList.clear();
    companyList.clear();
    try {
      Connection con = DriverManager.getConnection(url, userName, password);
      Statement stmt = con.createStatement();
      ResultSet r =
        stmt.executeQuery(
          "select fmcode, fund_man "
            + "from market "
            + "where fund_man like '%"
            + startFundman
            + "%' ");

      r.next();
      String fmcode = r.getString("fmcode");
      String fundman = r.getString("fund_man");
      GraphControl.NodeFacade n = graphRoot.addNode("DefaultNodeView");

      n.setColour(0f, 0.8f, 0f);
      n.setLabel(fundman);
      n.setUserData(new FMNodeData(n, stmt, fmcode));
      fmList.put(fmcode, n);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

  }

  // END OWNERSHIP VIEW
  //========================================================================
  // BEGIN FUND MANAGER MOVEMENT VIEW

  void fmMovementQuery(String startDate, String endDate) {

    nodes.clear();
    String maxMinQueryString =
      "select max(val) as maxVal, min(val) as minVal "
        + "from (select b.fund_man, b.sector, s.sector, "
        + "      sum(b.shares*b.share_pric) + sum(s.shares*s.share_pric) as val "
        + "      from buy b, sell s "
        + "      where b.fund_man = s.fund_man "
        + "      and b.notified between date('"
        + startDate
        + "') "
        + "                         and date('"
        + endDate
        + "') "
        + "      and s.notified between date('"
        + startDate
        + "') "
        + "                         and date('"
        + endDate
        + "')"
        + "      group by b.fund_man, b.sector, s.sector) as subselect";

    String queryString =
      "select b.fund_man, b.sector as buy_sector, s.sector as sell_sector, "
        + " b.sec_name as buy_sec_name, s.sec_name as sell_sec_name, "
        + " sum(b.shares*b.share_pric) + sum(s.shares*s.share_pric) as value, "
        + " count(*) "
        + "from buy b, sell s "
        + "where b.fund_man = s.fund_man "
        + "and b.notified between date('"
        + startDate
        + "') and date('"
        + endDate
        + "') "
        + "and s.notified between date('"
        + startDate
        + "') and date('"
        + endDate
        + "') "
        + "group by 1,2,3,4,5 ";

    try {
      Connection con = DriverManager.getConnection(url, userName, password);
      Statement stmt = con.createStatement();
      ResultSet r = stmt.executeQuery(maxMinQueryString);

      r.next();
      float minValue = r.getFloat("minVal");
      float maxValue = r.getFloat("maxVal");

      r = stmt.executeQuery(queryString);
      while (r.next()) {
        System.out.println(r.getString(1));
        String buySector = r.getString("buy_sector");
        String sellSector = r.getString("sell_sector");

        if (buySector.equals(sellSector)) {
          continue;
        }
        addNode(buySector, r.getString("buy_sec_name"));
        addNode(sellSector, r.getString("sell_sec_name"));
        float shareValue = r.getFloat("value");
        float weight = (shareValue - minValue) / (maxValue - minValue);

        addEdge(sellSector, buySector, weight);
        System.out.println(
          "Buy Sector = " + buySector + ", Sell Sector = " + sellSector);
      }
      stmt.close();
      con.close();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    graphRoot.unfreeze();
  }

  // END FUND MANAGER MOVEMENT VIEW
  // ========================================================================
  // BEGIN FUND MANAGER MOVEMENT ACROSS TIME VIEW

  // we've defined a set of tables for company lookups at each time interval
  // and a set for looking up holding changes (diffs) for a particular time
  // This class just gives us a record us a reference to one of each for each
  // time period.
  class Table {
    String diffs;
    String company;
    Table(String d, String c) {
      diffs = d;
      company = c;
    }
  }

  // at one point I was drawing each time period separately.  This class
  // defined a callback for when the graph for one time period was settled
  // so the next could be generated.  Currently not used!
  class Client implements BalancedEventClient {
    Statement s;
    Vector t;
    Client(Statement s, Vector t) {
      this.s = s;
      this.t = t;
    }

    public void callback() {
      drawLevels(s, t);
    }
  }
  void fmMovementAcrossTimeQuery(String startDate, String endDate) {
    graphRoot.deleteAll();
    if (ColumnCluster.getColumnStyle() == ColumnCluster.DOTCOLUMNS) {
      graphRoot = graphRoot.addCluster();
      graphRoot.hide();
      graphRoot.setLayoutEngine(
        new org.wilmascope.dotlayout.DotLayout(graphRoot.getCluster()));
    } else if (ColumnCluster.getColumnStyle() == ColumnCluster.FORCECOLUMNS) {
      (
        (org.wilmascope.forcelayout.ForceLayout) graphRoot
          .getLayoutEngine())
          .setVelocityAttenuation(
        0.005f);
    }

    String queryString =
      "select diffs_table, 'company_'||to_char(to_date(company_table, 'YYYY-MM-DD'),'YYYYMMDD') as company_table "
        + "from diffs_tables "
        + "where startdate >= '"
        + startDate
        + "' "
        + "  and enddate <= '"
        + endDate
        + "'";

    columns = new TreeMap();
    Vector tables = new Vector();

    level = 0;
    try {
      Connection connection =
        DriverManager.getConnection(url, userName, password);
      Statement stmt = connection.createStatement();
      ResultSet outer = stmt.executeQuery(queryString);

      while (outer.next()) {
        tables.add(new Table(outer.getString(1), outer.getString(2)));
      }
      //Client c = new Client(stmt,tables);
      //graphRoot.setBalancedEventClient(c);
      //c.callback();
      maxLevel = tables.size();
      while (drawLevels(stmt, tables)) {
      };
      graphRoot.unfreeze();
      stmt.close();
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println(queryString);
    }
  }
  int maxLevel = 0;
  boolean drawLevels(Statement s, Vector tables) {
    if (tables.size() == 0)
      return false;
    Table t = (Table) tables.remove(0);

    nextLevel(s, t.diffs, t.company, Integer.parseInt(sectorField.getText()));
    //graphRoot.unfreeze();
    return true;
  }
  void nextLevel(
    Statement s,
    String diffsTable,
    String companyTable,
    int sector) {
    String queryString =
      new String(
        "select s.epic, b.epic, s.share_pric, b.share_pric, sum(s.new_shares * s.share_pric + b.new_shares * b.share_pric) as value, count(*) "
          + "from "
          + diffsTable
          + " s, "
          + diffsTable
          + " b "
          + "where s.fmcode = b.fmcode "
          + "  and s.epic != b.epic "
          + "  and s.holding_change = 'SELL' "
          + "  and b.holding_change = 'BUY' "
          + "  and s.sector = "
          + sector
          + " "
          + "  and b.sector = s.sector "
          + "group by 1,2,3,4");

    try {
      ResultSet r = s.executeQuery(queryString);

      if (r.getFetchSize() == 0)
        return;
      System.out.println("commonlevel = " + level);
      while (r.next()) {
        String epic1 = r.getString(1);
        String epic2 = r.getString(2);
        float sharePrice1 = r.getFloat(3);
        float sharePrice2 = r.getFloat(4);
        float value = r.getFloat(5);
        int count = r.getInt(6);

        System.out.println("c1=" + epic1 + ",c2=" + epic2 + ",count=" + count);
        ColumnCluster a = addColumn(epic1, sharePrice1);
        ColumnCluster b = addColumn(epic2, sharePrice2);
        float minValue = 1000f;
        float maxValue = 100000f;
        float weight = (value - minValue) / (maxValue - minValue);

        addColumnEdge(
          a,
          b,
          weight,
          new EdgeOption(epic1, epic2, diffsTable, sector));
      }
      level++;
      for (Iterator i = columns.keySet().iterator(); i.hasNext();) {
        String id = (String) i.next();
        ColumnCluster c = (ColumnCluster) columns.get(id);

        if (c.getNextLevel() < level) {
          r =
            s.executeQuery(
              "select share_pric from "
                + companyTable
                + " where epic = '"
                + id
                + "'");
          float radius = c.getTopNode().getRadius();
          if (r.next()) {
            radius = r.getFloat("share_pric");
          }
          c.addNode(radius);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println(queryString);
    }
  }
  TreeMap columns;
  public static int level = 0;
  ColumnCluster addColumn(String id, float value) {
    ColumnCluster c = (ColumnCluster) columns.get(id);

    if (c == null) {
      c = new ColumnCluster(id, graphRoot, value, 1f, level);
      columns.put(id, c);
    }
    if (c.getNextLevel() < level + 1) {
      c.addNode(value);
    }
    return c;
  }

  void addColumnEdge(
    ColumnCluster from,
    ColumnCluster to,
    float weight,
    EdgeOption edgeOption) {
    float radius = 0.005f * (2 * weight + 1);

    GraphControl.NodeFacade start = from.getTopNode();
    GraphControl.NodeFacade end = to.getTopNode();
    GraphControl.EdgeFacade edge;
    if (ColumnCluster.getColumnStyle() == ColumnCluster.DOTCOLUMNS) {
      edge = graphRoot.addEdge(start, end, "Spline", radius);
    } else {
      edge = graphRoot.addEdge(start, end, "Arrow", radius);
    }

    edge.setWeight(weight);
    edge.setColour(1f, 1f, 1f * (float) level / (float) maxLevel);
    edge.setUserData(edgeOption);
  }
  class EdgeOption extends ElementData {
    EdgeOption(
      final String startEPIC,
      final String endEPIC,
      final String diffsTable,
      final int sector) {
      setActionDescription("Show Fund Manager details...");
      setActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          GraphControl.EdgeFacade edge =
            org.wilmascope.gui.EdgeOptionsMenu.getSelectedEdge();
          System.out.println(startEPIC + "->" + endEPIC);
          String queryString =
            new String(
              "select s.fund_man, sum(s.new_shares * s.share_pric + b.new_shares * b.share_pric) as value, count(*) "
                + "from "
                + diffsTable
                + " s, "
                + diffsTable
                + " b "
                + "where s.fmcode = b.fmcode "
                + "  and b.epic = '"
                + endEPIC
                + "'"
                + "  and s.epic = '"
                + startEPIC
                + "'"
                + "  and s.holding_change = 'SELL' "
                + "  and b.holding_change = 'BUY' "
                + "  and s.sector = "
                + sector
                + " "
                + "  and b.sector = s.sector "
                + "group by 1");

          try {
            Connection connection =
              DriverManager.getConnection(url, userName, password);
            Statement stmt = connection.createStatement();
            ResultSet r = stmt.executeQuery(queryString);

            if (r.getFetchSize() == 0)
              return;
            System.out.println("commonlevel = " + level);
            while (r.next()) {
              String fmcode = r.getString("fund_man");
              float value = r.getFloat("value");
              int count = r.getInt(3);

              System.out.println(
                "fmcode=" + fmcode + ", value=" + value + ", count=" + count);
            }
          } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(queryString);
          }
        }
      });
    }
  }

  void addNode(String id, String label) {
    if (!nodes.containsKey(id)) {
      GraphControl.NodeFacade n = graphRoot.addNode("LabelOnly");

      n.setLabel(label);
      nodes.put(id, n);
    }
  }

  void addEdge(String fromID, String toID, float weight) {
    float radius = 0.005f * (2 * weight + 1);

    GraphControl.NodeFacade start = (GraphControl.NodeFacade) nodes.get(fromID);
    GraphControl.NodeFacade end = (GraphControl.NodeFacade) nodes.get(toID);
    GraphControl.EdgeFacade edge =
      graphRoot.addEdge(start, end, "Arrow", radius);

    edge.setWeight(weight);
  }
  void addEdge(String fromID, String toID, float weight, String type) {
    float radius = 0.01f * (weight);

    GraphControl.NodeFacade start = (GraphControl.NodeFacade) nodes.get(fromID);
    GraphControl.NodeFacade end = (GraphControl.NodeFacade) nodes.get(toID);
    GraphControl.EdgeFacade edge = graphRoot.addEdge(start, end, type, radius);
    float defaultStiffness =
      ((org.wilmascope.forcelayout.EdgeForceLayout) edge.getEdge().getLayout())
        .getStiffness();
    (
      (org.wilmascope.forcelayout.EdgeForceLayout) edge
        .getEdge()
        .getLayout())
        .setStiffness(
      5f * (weight - 1f) * defaultStiffness);
    edge.setWeight(weight);
  }

  void dotColumnsRadioButton_actionPerformed(ActionEvent e) {
    ColumnCluster.setColumnStyle(ColumnCluster.DOTCOLUMNS);
  }

  void forceColumnRadioButton_actionPerformed(ActionEvent e) {
    ColumnCluster.setColumnStyle(ColumnCluster.FORCECOLUMNS);
  }

  void wormRadioButton_actionPerformed(ActionEvent e) {
    ColumnCluster.setColumnStyle(ColumnCluster.WORMS);
  }
  // END FUND MANAGER MOVEMENT ACCROSS TIME VIEW
  //==========================================================================
  // BEGIN SECTOR VIEW
  void sectorQuery(String startDate, String endDate) {
    org.wilmascope.forcelayout.ForceLayout l =
      (org.wilmascope.forcelayout.ForceLayout) graphRoot.getLayoutEngine();
    l.getForce("Spring").setStrengthConstant(0.5f);
    l.getForce("Repulsion").setStrengthConstant(5f);
    //l.removeForce(l.getForce("Origin"));
    l.getForce("Origin").setStrengthConstant(4f);
    nodes.clear();
    String maxMinQueryString =
      "select max(weight) as max_weight, min(weight) as min_weight from ( "
        + "select buy_sector, sell_sector, sum(weight) as weight "
        + "from sector_movement_200112 "
        + "where start_date between '"
        + startDate
        + "' and '"
        + endDate
        + "' "
        + "group by 1,2 ) as foo";

    String queryString =
      "select buy_sector, sell_sector, "
        + "sum(weight) as weight "
        + "from sector_movement_200112 "
        + "where start_date between '"
        + startDate
        + "' and '"
        + endDate
        + "' "
        + "group by 1,2 "
        + "order by 3 desc";

    try {
      Connection con = DriverManager.getConnection(url, userName, password);
      Statement stmt = con.createStatement();
      ResultSet r = stmt.executeQuery(maxMinQueryString);
      r.next();
      float minWeight = r.getFloat("min_weight");
      float maxWeight = r.getFloat("max_weight");

      r = stmt.executeQuery(queryString);
      int i = 0;
      int visibleEdges = sectorEdgeSlider.getValue();
      boolean planar = planarSectorRadioButton.isSelected();
      while (r.next()) {
        String buySector = r.getString("buy_sector");
        String sellSector = r.getString("sell_sector");

        addSectorNode(buySector, planar, con);
        addSectorNode(sellSector, planar, con);
        float weight = r.getFloat("weight");
        weight = (weight - minWeight) / (maxWeight - minWeight) + 1f;
        if (i++ < visibleEdges) {
          addEdge(sellSector, buySector, weight, "Arrow");
        } else {
          addEdge(sellSector, buySector, weight, "NONE");
        }
        System.out.println(
          "Buy Sector = " + buySector + ", Sell Sector = " + sellSector);
      }
      stmt.close();
      con.close();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      System.out.println(queryString);
    }
    graphRoot.unfreeze();
  }

  /** add a sector node, looking up the short name and market capitalisation of
   *  the sector from a precomputed table
   */
  void addSectorNode(String id, boolean planar, Connection con) {
    if (!nodes.containsKey(id)) {
      GraphControl.NodeFacade n = graphRoot.addNode();
      n.setUserData(new SectorNodeData(id));
      nodes.put(id, n);
      String query =
        "select case when short_sec is null then to_char(sector,'99') else short_sec end as name, "
          + "cap_total from all_sectors where sector = "
          + id;
      try {
        Statement stmt = con.createStatement();
        ResultSet r = stmt.executeQuery(query);
        r.next();
        String label = r.getString(1);
        float cap = r.getFloat(2);
        if (planar) {
          n.setLevelConstraint(0);
        }
        n.setLabel(label);
        // I've hard coded the max and min market caps accross all sectors
        // cos I'm lazy and this is just meant to be a demo.
        // should get these in another query
        cap = ((cap - 96f) / (256665f - 96f));
        // Colour from dark to light blue, the lighter the colour the higher
        // the market cap
        n.setColour(cap, cap, 1f);
        // The bigger the node the larger the market cap
        n.setRadius((1 + cap) * n.getRadius());
        stmt.close();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
        System.out.println(query);
      }
    }
  }
  /** defines a custom action for the node options menu for Sector nodes.
   * The user can use this to initiate a fund manager movement query for the
   * chosen sector.
   */
  class SectorNodeData extends ElementData {
    String id;
    SectorNodeData(final String id) {
      this.id = id;
      setActionDescription("Zoom Sector...");
      setActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          thisframe.show();
          queryPane.setSelectedComponent(fmMovement2Panel);
          startDate2Field.setText(sectorStartField.getText());
          endDate2Field.setText(sectorEndField.getText());
          sectorField.setText(id);
        }
      });
    }
  }
  // END SECTOR VIEW
  //=========================================================================
  // global variables - DOH!
  /** the root cluster to which we will add all nodes and edges */
  GraphControl.ClusterFacade graphRoot;
  // handy reference for use in anonymous inner classes
  QueryFrame thisframe = this;
  /** User name for connecting to SQL database */
  String userName = "dwyer";
  /** password for connecting to SQL database */
  String password = "";
  /** location of the database */
  String url = new String("jdbc:postgresql://mother/citywatch");

  //=========================================================================
  // GUI objects, generated by JBuilder
  JPanel jPanel2 = new JPanel();
  JPanel jPanel3 = new JPanel();
  JPanel sectorPanel = new JPanel();
  GridLayout gridLayout3 = new GridLayout();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  JTextField sectorStartField = new JTextField();
  JTextField sectorEndField = new JTextField();
  JLabel jLabel9 = new JLabel();
  JSlider sectorEdgeSlider = new JSlider();
  JRadioButton planarSectorRadioButton = new JRadioButton();
  JLabel jLabel2 = new JLabel();
  JTextField endDateField = new JTextField();
  JPanel jPanel1 = new JPanel();
  JButton okButton = new JButton();
  Hashtable nodes = new Hashtable();
  JTabbedPane queryPane = new JTabbedPane();
  JPanel fmMovementPanel = new JPanel();
  JLabel jLabel1 = new JLabel();
  GridLayout gridLayout1 = new GridLayout();
  JTextField startDateField = new JTextField();
  JPanel ownershipPanel = new JPanel();
  JButton cancelButton = new JButton();
  JLabel jLabel3 = new JLabel();
  JTextField fundmanField = new JTextField();
  JPanel fmMovement2Panel = new JPanel();
  GridLayout gridLayout2 = new GridLayout();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JTextField startDate2Field = new JTextField();
  JTextField endDate2Field = new JTextField();
  JLabel jLabel6 = new JLabel();
  JTextField sectorField = new JTextField();
  JRadioButton wormRadioButton = new JRadioButton();
  JRadioButton dotColumnsRadioButton = new JRadioButton();
  JRadioButton forceColumnRadioButton = new JRadioButton();
  ButtonGroup styleButtonGroup = new ButtonGroup();
}