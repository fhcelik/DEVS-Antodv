package ospff.BRITE.GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;



public final class HelpPanel extends JDialog  implements ActionListener{
    JButton closeB = new JButton("Close Help Window");
 
    JScrollPane scrollPane1;
    JEditorPane editPane;
    LineBorder lineBorder1 = new LineBorder(java.awt.Color.black);
    ospff.BRITE.GUI.Brite parent =null;
    
    public void actionPerformed(ActionEvent e) {
	if (e.getSource().equals(closeB)) {
	  setVisible(false);
	}
    }

    public void processWindowEvent(WindowEvent e) {
	super.processWindowEvent(e);
	if (e.getSource() == this && e.getID() == e.WINDOW_CLOSING) {
	  setVisible(false);
	}
    }
   
    public JScrollPane getScroll() { return scrollPane1; }
    public JButton getButton() { return closeB; }
    public HelpPanel(ospff.BRITE.GUI.Brite parent) {
	super();
	super.dialogInit();
	setSize(500,500);
	setResizable(false);
	
	this.parent = parent; //we need this because sometimes we need to kill the process while its executing
	
	getContentPane().setLayout(null);
	getContentPane().setBackground(new java.awt.Color(204,204,204));
	
	getContentPane().add(closeB);
	closeB.setBounds(200, 470, 100, 21);
	closeB.setText("Close Window");
	closeB.setFont(new Font("SansSerif", Font.PLAIN, 10));
	closeB.setBorder(lineBorder1);
	closeB.addActionListener(this);
	closeB.setVisible(true);
	       
	editPane = new JEditorPane();
	String s= null;
	try {
	    String slash = System.getProperty("file.separator");
	    s = "file:parameterhelp.html";
	  editPane.setPage(new java.net.URL(s));
	}
	catch (Exception e) {
	  System.out.println("[BRITE ERROR] Could not read help file " + e);
	
	}
	editPane.setEditable(false);
	editPane.setBounds(10,10,480,450);
	scrollPane1 = new JScrollPane(editPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	getContentPane().add(scrollPane1);
	scrollPane1.setBounds(10, 10, 480, 450);
	
	setTitle("BRITE Help ");
	setSize(getPreferredSize());
    }


} 

