package org.wilmascope.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/** About dialog box, gives general info About Wilma
 * @author $Author$
 * @version $Revision$
 */
public class About extends javax.swing.JDialog {

  private String filename;
  /** Initializes the Form
   * @param parent Frame of caller
   */
  public About(java.awt.Frame parent, String filename) {
    super (parent, true);
    this.filename = filename;
    initComponents ();
    pack ();
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the FormEditor.
   */
  private void initComponents () {//GEN-BEGIN:initComponents
    jMenuBar1 = new javax.swing.JMenuBar ();
    jMenu1 = new javax.swing.JMenu ();
    jMenuItem1 = new javax.swing.JMenuItem ();
    wilmaInfo = new javax.swing.JTextField ();

      jMenu1.setText ("About");
  
        jMenuItem1.setText ("Close");
        jMenuItem1.addActionListener (new java.awt.event.ActionListener () {
          public void actionPerformed (java.awt.event.ActionEvent evt) {
            jMenuItem1ActionPerformed (evt);
          }
        }
        );
    
        jMenu1.add (jMenuItem1);
      jMenuBar1.add (jMenu1);
    setTitle ("About");
    addWindowListener (new java.awt.event.WindowAdapter () {
      public void windowClosing (java.awt.event.WindowEvent evt) {
        closeDialog (evt);
      }
    }
    );

    wilmaInfo.setPreferredSize (new java.awt.Dimension(500, 254));
    wilmaInfo.setEditable (false);
    wilmaInfo.setMaximumSize (new java.awt.Dimension(500, 400));
    wilmaInfo.setFont (new java.awt.Font ("Curlz MT", 0, 18));
    wilmaInfo.setText ("WILMA\n  World for Interactive Localized Modeling Algorithms\n  or something similar...\n\nForced directed graphing algorithm based on: M. Huang, P. Eades,\nJ. Wang: \"On-line Animated Visualization of Huge Graphs using a \nModified Spring Algorithm\", a paper in the \"Journal of Visual\nLanguages and Computing\" (1998) pp623-645\n\nTim Dwyer - March 2000");


    //getContentPane ().add (wilmaInfo, java.awt.BorderLayout.CENTER);
    JLabel l = new JLabel(new ImageIcon(filename));
    getContentPane().add(l, BorderLayout.CENTER);

    setJMenuBar (jMenuBar1);

  }//GEN-END:initComponents

  private void jMenuItem1ActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
    // Add your handling code here:
    closeDialog(null);
  }//GEN-LAST:event_jMenuItem1ActionPerformed


  /** Closes the dialog */
  private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
    setVisible (false);
    dispose ();
  }//GEN-LAST:event_closeDialog


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JMenu jMenu1;
  private javax.swing.JMenuItem jMenuItem1;
  private javax.swing.JTextField wilmaInfo;
  // End of variables declaration//GEN-END:variables



}
