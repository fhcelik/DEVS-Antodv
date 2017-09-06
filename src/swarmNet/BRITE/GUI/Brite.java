package swarmNet.BRITE.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import controller.ControllerInterface;


public final class Brite extends JDialog implements ActionListener, Runnable {

	private final JCheckBox enableVisualizerCheckBox = new JCheckBox();
	public Brite(ControllerInterface c){
		super();
		controller=c;
		try {
			jbInit();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	public void init() {
		getContentPane().setLayout(null);
		getContentPane().setBackground(new java.awt.Color(204, 204, 204));
		setSize(494, 442);
		JLabel1.setText("Topology Type:");
		getContentPane().add(JLabel1);
		JLabel1.setForeground(java.awt.Color.black);
		JLabel1.setFont(new Font("SansSerif", Font.BOLD, 12));
		JLabel1.setBounds(36, 12, 156, 22);
		getContentPane().add(TopologyType);
		TopologyType.setFont(new Font("SansSerif", Font.PLAIN, 12));
		TopologyType.setBounds(170, 12, 202, 26);
		TopologyType.addActionListener(this);

		HelpButton.setText("Help");
		HelpButton.setBorder(lineBorder1);
		getContentPane().add(HelpButton);
		HelpButton.setForeground(java.awt.Color.black);
		HelpButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
		// HelpButton.setBounds(24, 474, 50,21);
		HelpButton.setBounds(24, 369, 50, 21);
		HelpButton.addActionListener(this);

		/* BEGIN: run C++ or Java exe choice */
		/* END: C++ or Java exe choice */

		/* BEGIN: Build Topology Button */
		BuildTopology.setText("Build Topology");
		BuildTopology.setActionCommand("Build Topology");
		BuildTopology.setBorder(lineBorder1);
		getContentPane().add(BuildTopology);
		BuildTopology.setForeground(java.awt.Color.black);
		BuildTopology.setFont(new Font("SansSerif", Font.PLAIN, 12));
		BuildTopology.setBounds(348, 369, 108, 21);
		BuildTopology.addActionListener(this);
		/* END: Build Topology Button */

		getContentPane().add(logo);
		logo.setBorder(null);
		logo.setBounds(389, 2, 67, 65);
		logo.addActionListener(this);

		getContentPane().add(JTabbedPane1);
		JTabbedPane1.setBackground(new java.awt.Color(153, 153, 153));
		JTabbedPane1.setBounds(24, 48, 432, 315);
		JTabbedPane1.add(asPanel);
		JTabbedPane1.add(rtPanel);
		JTabbedPane1.add(tdPanel);
		JTabbedPane1.add(buPanel);
		JTabbedPane1.setTitleAt(0, "AS");
		JTabbedPane1.setTitleAt(1, "Router");
		JTabbedPane1.setTitleAt(2, "Top Down");
		JTabbedPane1.setTitleAt(3, "Bottom Up");
		JTabbedPane1.setSelectedIndex(0);
		JTabbedPane1.setSelectedComponent(asPanel);
		rtPanel.EnableComponents(false);
		rtDisabled = true;
		hDisabled = true;
		tdPanel.EnableComponents(false);
		buPanel.EnableComponents(false);

		// create status window where output of executable will be written
		sd.setSize(400, 200);
		// sd.setSize(sd.getPreferredSize());
		sd.setVisible(false);

		aboutPanel.setSize(300, 300);
		aboutPanel.setVisible(false);

		hPanel.setSize(500, 500);
		hPanel.setVisible(false);
		setTitle("BRITE Topology Generator for DEVS-Suite");
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(HelpButton)) {
			hPanel.setVisible(true);
			return;
		}
		if (e.getSource().equals(logo)) {
			aboutPanel.setVisible(true);
			return;
		}
		String level = (String) TopologyType.getSelectedItem();
		if (e.getSource().equals(TopologyType)) {
			level = (String) TopologyType.getSelectedItem();
			if (level.equals(AS_TOPOLOGY)) {
				JTabbedPane1.setSelectedComponent(asPanel);
				rtPanel.EnableComponents(false);
				tdPanel.EnableComponents(false);
				buPanel.EnableComponents(false);
				asPanel.EnableComponents(true);

			} else if (level.equals(ROUTER_TOPOLOGY)) {
				JTabbedPane1.setSelectedComponent(rtPanel);
				tdPanel.EnableComponents(false);
				buPanel.EnableComponents(false);
				asPanel.EnableComponents(false);
				rtPanel.EnableComponents(true);

			} else if (level.equals(TOPDOWN_TOPOLOGY)) {
				JTabbedPane1.setSelectedComponent(tdPanel);
				tdPanel.EnableComponents(true);
				buPanel.EnableComponents(false);
				asPanel.EnableComponents(true);
				asPanel.EnableBW(false);
				rtPanel.EnableComponents(true);
				rtPanel.EnableBW(false);
			} else if (level.equals(BOTTOMUP_TOPOLOGY)) {
				JTabbedPane1.setSelectedComponent(buPanel);
				buPanel.EnableComponents(true);
				asPanel.EnableComponents(false);
				tdPanel.EnableComponents(false);
				rtPanel.EnableComponents(true);

			}
		}

		else if (e.getSource().equals(BuildTopology)) {
			String args = " GUI_GEN.conf  " + "aaa";
			BuildTopology.setEnabled(false);

			MakeConfFile(level);
			//swarmNet.BRITE.Main.BriteDevs b = new swarmNet.BRITE.Main.BriteDevs();
			//b.formTopology("GUI_GEN.conf", "seed_file");

			// BuildTopology.setEnabled(true);
			this.setVisible(false);
			// runExecutable(args);
			String[] val   = {"swarmNet", "GeneratedNetworkEF"};
            controller.userGesture(controller.LOAD_MODEL_GESTURE,val);
            if(enableVisualizerCheckBox.isSelected())
            {
            	swarmNet.BRITE.Visualizer.Visualize.ciz("net.brite");
            	
            }
			//View v=new View(controller);
			//v.uploadModel(val);
 
		}
	}

	// this is for C++ version
	public void ConvertBriteToExportFormat(String file) throws Exception {
		Rectangle rect = sdLog.getVisibleRect();
		int a = sdLog.getScrollableBlockIncrement(rect,
				SwingConstants.VERTICAL, 1);
		rect.setLocation((int) rect.getX(), (int) rect.getY() + a);
		sdLog.scrollRectToVisible(rect);

		File f = new File(file);
		if (!f.exists()) {
			sdLog.append(" Cannot find file " + file
					+ " to convert to export format..");
			return;
		}

		int format = swarmNet.BRITE.Model.ModelConstants.RT_FILE;
		String topologyType = (String) TopologyType.getSelectedItem();
		if (topologyType.equals(AS_TOPOLOGY))
			format = swarmNet.BRITE.Model.ModelConstants.AS_FILE;
		a = sdLog.getScrollableBlockIncrement(rect, SwingConstants.VERTICAL, 1);
		rect.setLocation((int) rect.getX(), (int) rect.getY() + a);
		sdLog.scrollRectToVisible(rect);
		sdLog.paintImmediately(sdLog.getVisibleRect());

	}

	public void run() {
		String args = " GUI_GEN.conf ";

		/* make sure all import files are first converted to brite format */
		if (asPanel.needConvert) {
			asPanel.ConvertFileToBriteFormat();
		}
		if (rtPanel.needConvert) {
			rtPanel.ConvertFileToBriteFormat();
		}

		// runExecutable(args);

		BuildTopology.setEnabled(true);
	}

	private void MakeConfFile(String topologyType) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
					"GUI_GEN.conf")));
			bw.write("#This config file was generated by the BRITE GUI. ");
			bw.newLine();
			bw.newLine();
			bw.write("BriteConfig");
			bw.newLine();
			bw.newLine();
			if (topologyType.equals(TOPDOWN_TOPOLOGY)) {
				tdPanel.WriteConf(bw);
				/*
				 * tdPanel handles this stuff now b/c we need to set bandwidth
				 * from td params:: bw.newLine(); asPanel.WriteConf(bw);
				 * bw.newLine(); rtPanel.WriteConf(bw);
				 */
			} else if (topologyType.equals(BOTTOMUP_TOPOLOGY)) {
				buPanel.WriteConf(bw);
				bw.newLine();
				rtPanel.WriteConf(bw);
				bw.newLine();
			} else if (topologyType.equals(AS_TOPOLOGY))
				asPanel.WriteConf(bw);
			else if (topologyType.equals(ROUTER_TOPOLOGY))
				rtPanel.WriteConf(bw);
			bw.newLine();
		    bw.write("BeginOutput");
		    bw.newLine();
		    bw.write("\tBRITE = ");
			bw.write("1 ");
		    bw.write("\t #1=output in BRITE format, 0=do not output in BRITE format");
		    bw.newLine();
			bw.close();
		} catch (IOException e) {
			System.out.println("[BRITE ERROR]:  Cannot create config file. ");
			e.printStackTrace();
			return;
		}
	}

	public void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == e.WINDOW_CLOSING) {
			sd.dispose();
			hPanel.dispose();
			//System.exit(0);
			this.setVisible(false);
		}
	}

	String ROUTER_TOPOLOGY = "1 Level: ROUTER (IP) ONLY";
	String AS_TOPOLOGY = "1 Level: AS ONLY";
	String TOPDOWN_TOPOLOGY = "2 Level: TOP-DOWN";
	String BOTTOMUP_TOPOLOGY = "2 Level: BOTTOM-UP";
	String[] TopologyTypeData = { AS_TOPOLOGY, ROUTER_TOPOLOGY,
			TOPDOWN_TOPOLOGY, BOTTOMUP_TOPOLOGY };
	JComboBox TopologyType = new JComboBox(TopologyTypeData);

	String JAVAEXE = "Use Java Exe";
	String CPPEXE = "Use C++ Exe";
	String exeData[] = { JAVAEXE, CPPEXE };

	LineBorder lineBorder1 = new LineBorder(java.awt.Color.black);
	JLabel JLabel1 = new JLabel();

	JButton logo = new JButton(new ImageIcon("brite4.jpg"));
	JButton BuildTopology = new JButton();
	JButton HelpButton = new JButton();

	StatusDialog sd = new StatusDialog(this);
	JTextArea sdLog;
	private Thread runThread = null;
	public Process p = null;

	JTabbedPane JTabbedPane1 = new JTabbedPane();
	HelpPanel hPanel = new HelpPanel(this);
	AboutPanel aboutPanel = new AboutPanel();
	ASPanel asPanel = new ASPanel();
	RouterPanel rtPanel = new RouterPanel();

	TDPanel tdPanel = new TDPanel(this);
	BUPanel buPanel = new BUPanel(this);
	boolean rtDisabled = false;
	boolean asDisabled = false;
	boolean hDisabled = true;
	private ControllerInterface controller;
	private void jbInit() throws Exception {
		
		System.currentTimeMillis();
		getContentPane().add(enableVisualizerCheckBox);
		enableVisualizerCheckBox.setBackground(new Color(233, 150, 122));
		enableVisualizerCheckBox.addActionListener(new EnableVisualizerCheckBoxActionListener());
		enableVisualizerCheckBox.setText("Enable Visualizer");
		enableVisualizerCheckBox.setBounds(137, 369, 141, 24);
	}
	private class EnableVisualizerCheckBoxActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			enableVisualizerCheckBox_actionPerformed(e);
		}
	}
	protected void enableVisualizerCheckBox_actionPerformed(ActionEvent e) {
		
	}
}
