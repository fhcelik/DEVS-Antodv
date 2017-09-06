package honeycomb.BRITE.Visualizer;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.JScrollPane;

public class VisualizeNew extends JFrame {
	/**
	 * The number of nodes to be represented by veritices.
	 */
	private int numNodes = 0;

	/**
	 * The number of links to be represented by edges.
	 */
	private int numEdges = 0;

	/**
	 * Array of nodes.
	 */
	private Node nodes[] = null;

	/**
	 * Array of edges.
	 */
	private Edge edges[] = null;

	/**
	 * The canvas to draw on.
	 */
	private World world;


	private final JScrollPane scrollPane = new JScrollPane(world);
	private final Canvas canvas = new Canvas();
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VisualizeNew frame = new VisualizeNew(new BufferedReader(new FileReader("net.brite")));
					//frame.ciz("net.brite");
					System.out.println("asasa");
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
	public VisualizeNew(BufferedReader file) throws IOException {
		super();
		setBounds(1, 100, 500, 375);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//discard the first 4 linel
		for (int i=0; i<3; i++)
		{
			file.readLine();
		}

		//get the number of nodes
		StringTokenizer t = new StringTokenizer(file.readLine(), "()");
		t.nextToken();
		numNodes = Integer.parseInt((t.nextToken()).trim());
		System.out.println("numNodes: " + numNodes);
		nodes = new Node[numNodes];

		//for the number of nodes
		for (int i=0; i<nodes.length; i++)
		{
			//construct each node
			nodes[i] = new Node(file.readLine());
		}

		//discard the blank line
		file.readLine();
		file.readLine();

		//get the number of edges
		t = new StringTokenizer(file.readLine(), "()");
		t.nextToken();
		numEdges= Integer.parseInt(t.nextToken().trim());
		System.out.println("numEdges: " + numEdges);
		edges = new Edge[numEdges];

		//for the number of edges
		
		for (int i=0; i<edges.length; i++)
		{
			//construct each edge
			edges[i] = new Edge(file.readLine());
		}

		//construct the canvas to draw on.
		//TODO, should read world size from file
		world = new World(1400, 800, nodes);
		setLayout(new BorderLayout());
		setSize(new Dimension(1400, 800));
		add(world);
		show();
		try {
			jbInit();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		//
	}
	private void jbInit() throws Exception {
		
		System.currentTimeMillis();
		setTitle("DEVS-Suite Network Visualizer");
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		scrollPane.setViewportView(world);
	}
	
	public static void ciz(String outFile)
	{
		/*if (args.length != 1)
		{
			System.out.println("Error! Incorrect number of command line arguments.");
			usage();
		}
		else if ((args[0].equals("-help")) || (args[0].equals("--help")))
		{
			usage();
		}
		else*/
		{
			try
			{
				//try to open the file.
				BufferedReader file = new BufferedReader(new FileReader(outFile));
				//build the visualization
				Visualize t = new Visualize(file);
				//close the file
				file.close();
			}
			catch (FileNotFoundException e)
			{
				System.out.println("Error! could not open file: " + outFile);
				System.out.println(e);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Displays a usage message.
	 */
	public static void usage()
	{
		System.out.println("Usage:");
		System.out.println("	java Main <BRITE topology>");
	}


//////

	


	/**
	 * The world canvas to draw onto.
	 */
	private class World extends Canvas
	{
		/**
		 * Image that is used for double buffering.
		 */
		private final BufferedImage bi;
		
		/**
		 * The nodes to draw.
		 */
		private Node nodes[] = null;

		/**
		 * The width of the canvas.
		 */
		private int width = 0;
		
		/**
		 * The height of the canvas.
		 */
		private int height = 0;
		
		/**
		 * Default constructor.
		 *
		 * @param width The width of the canvas.
		 * @param height The height of the canvas.
		 * @param nodes The nodes.
		 */
		public World(final int width,
					 final int height,
					 final Node nodes[])
		{
			this.width = width;
			this.height = height;
			this.nodes = nodes;
			
			bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			buffer();
		}
		
		/**
		 * Re-paints the buffer.
		 */
		public void buffer()
		{
			Graphics2D g = bi.createGraphics();
			g.setBackground(Color.white);
			g.clearRect(0, 0, width, height);

			//draw the edges
			for (int i = 0;i < edges.length;i++)
			{
				edges[i].paint(g, nodes);
			}
			
			//draw the nodes
			for (int i = 0;i < nodes.length;i++)
			{
				nodes[i].paint(g);
				
			}
			
			g.dispose();
			super.repaint();
		}
		
		/**
		 * Paint method to re-draw the image.
		 *
		 * @param g The graphics object to draw with.
		 */
		public void paint(Graphics g)
		{
			g.drawImage(bi, 0, 100, this);
		}
	}
	
}

