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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Title:        WilmaToo
 * Description:  Sequel to the ever popular Wilma graph drawing engine
 * Copyright:    Copyright (c) 2001
 * Company:      WilmaOrg
 * @author Tim Dwyer
 * @version 1.0
 */
import org.wilmascope.view.ViewManager;
import java.util.Vector;
import org.wilmascope.patterns.Prototype;
public class DropDownButtonPanel extends JPanel {
  JButton actionButton;
  JButton typeButton = new JButton();
  JPopupMenu typeMenu;
  GridBagLayout layout = new GridBagLayout();
  Prototype prototype;
  public DropDownButtonPanel(Action action, ViewManager.Registry r) {
    initTypeMenu(r);
    setLayout(layout);
    actionButton = new JButton(action);
    actionButton.setMinimumSize(new Dimension(28,28));
    actionButton.setPreferredSize(new Dimension(28,28));
    actionButton.setBorder(null);
    actionButton.setText("");
    typeButton.setBorder(null);
    final JRootPane rootPane = this.getRootPane();
    typeButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        typeMenu.show(typeButton,typeButton.getX(),typeButton.getY());
      }
    });
    this.setBorder(BorderFactory.createEtchedBorder());
    this.setPreferredSize(new Dimension(45,32));
    this.setMaximumSize(new Dimension(56,32));
    typeButton.setIcon(new ImageIcon("images/dropArrow.png"));
    this.add(actionButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.RELATIVE, new Insets(0, 0, 0, 0), 0, 0));
    this.add(typeButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.REMAINDER, new Insets(0, 0, 0, 0), 0, 0));
  }
  public void setToolTipText(String text) {
    actionButton.setToolTipText(text);
    typeButton.setToolTipText(text);
    super.setToolTipText(text);
  }
  void initTypeMenu(final ViewManager.Registry reg) {
    typeMenu = new JPopupMenu();
    String[] names=reg.getViewNames();
    typeMenu.removeAll();
    for(int i=0;i<names.length;i++) {
      final String name = names[i];
      JMenuItem typeMenuItem = new JMenuItem(name);
      try {
        typeMenuItem.setIcon(reg.getIcon(name));
      } catch(ViewManager.UnknownViewTypeException ex){
        ex.printStackTrace();
      }
      typeMenuItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          System.out.println(name);
          try {
            reg.setDefaultView(name);
            actionButton.setIcon(reg.getIcon());
          } catch (ViewManager.UnknownViewTypeException ex) {
            ex.printStackTrace();
          }
        }
      });
      typeMenu.add(typeMenuItem);
    }
  }

}
