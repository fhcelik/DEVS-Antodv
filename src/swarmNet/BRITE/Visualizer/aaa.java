package swarmNet.BRITE.Visualizer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import javax.swing.DebugGraphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class aaa extends JFrame {

	private final JScrollPane scrollPane = new JScrollPane();
	private final JPanel panel = new JPanel();
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					aaa frame = new aaa();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame
	 */
	public aaa() {
		super();
		setBounds(100, 100, 500, 375);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			jbInit();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		//
	}
	private void jbInit() throws Exception {
		
		
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(1500, 1500));
		scrollPane.setDebugGraphicsOptions(DebugGraphics.BUFFERED_OPTION);
		scrollPane.setAutoscrolls(true);
		
		scrollPane.setViewportView(panel);
	}
	public void paint(Graphics g){
		g.drawLine(5,
				   5,
				  1500,
				   1500);
		
		
	}

}
