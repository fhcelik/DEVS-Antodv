package honeycomb;

import honeycomb.BRITE.Graph.Graph;
import honeycomb.BRITE.Visualizer.Node;
import java.awt.geom.Point2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.modeling.digraph;
import model.modeling.message;
import model.simulation.coordinator;
import view.modeling.ViewableAtomic;
import view.modeling.ViewableDigraph;
import view.modeling.ViewableComponent;
import view.simView.SimView;
import GenCol.Pair;

import model.modeling.componentIterator;

/**
 * This Link class simply implements the network channel.
 */
public class topography extends ViewableAtomic {
	
	private Graphics g;
	private int xyhafiza[][];
	private int node;
	private IPAddress ip;
	private paketformat packet;
	private double simulation_time;
	private int en,boy,a;
	private double distance,ilk,processing_time,dist;
	private int area = 250, charea=105;
	private int step, transmission_range;
	private Queue queue;
	private int td[];
	String b;
	private Color m_tRed = new Color(255, 0, 0, 10);
	private Color m_tGreen = new Color(0, 255, 0, 150);
	private Color m_tBlue = new Color(0, 0, 255, 5);
	private Color m_tBlu = new Color(155, 155, 255, 150);
	private Color m_torange = new Color(192,192,192, 150);
	TopgraphyGraphic grfdnm = new TopgraphyGraphic();
	private String cpt;
	



	// private int td[]=new int[7];
	public topography(String name, int veri[][],int nod, IPAddress adres,int eni, int boyu,double simtime,int transrange,double islemzam, String cap) {
		super(name);
		xyhafiza=veri;
		node=nod;
		ip=adres;
		en=eni;
		boy=boyu;
		simulation_time=simtime;
		transmission_range=transrange;
		processing_time=islemzam;
		cpt=cap;
		addInport("NIC-in");
		addOutport("NIC-out");
		
		
	}

	public topography() throws IOException {

		this("Topography",null,10,new IPAddress(0, 0, 0, 1),800,800,100,100,1,"honeycomb-DEVS");

	}

	public void initialize() {

		phase = "idle";
		sigma = INFINITY;
		super.initialize();
		queue = new Queue(10000);
		// td = new int[7];
		// xyhafiza = new int[node + 1][7];
		// queue = new Vector<int[]>();
		// q = new swarmNet.Components.Queue(bw);
		// setBackgroundColor(new Color(Color.white));
		holdIn("setup", 0);

	}
	public static int mesafe(int xcv, int cvb){
		//System.out.println("soran="+xcv); 
		return xcv;
	} 

	public void grafikstart() {

		grfdnm.init();
	}

	public void deltext(double e, message x) {
		Continue(e);
		for (int i = 0; i < x.getLength(); i++) {			
			for (int k = 0; k < getNumInports(); k++) {
				
				String PN=(String)getInportNames().get(k);
			
				if (messageOnPort(x, PN, i)){
				// data = (int[])((NetworkPacket) x.getValOnPort(">>in",
				// i)).getData();
				// packet.setPort(PN);
				// queue.addElement(data);
				packet = new paketformat((paketformat) x.getValOnPort(PN,i));
				queue.enqueue(packet);
				holdIn("veri kuyrukta", 0);
			}
		}
	}
	}
	
	public void deltint() {
		//removeCoupling(new String("Node_0"), "NIC-out", new String("Node_2"), "NIC-in");
		//removeCoupling(new String("Node_0"), "NIC-out", new String("Node_4"), "NIC-in");
		//System.out.println("kopmasý lazým");
		
		if(queue.isEmpty())holdIn("boþda", INFINITY);
		else{
			
			
			packet = queue.dequeue();
			
		

			

		setSigma(processing_time);
		grfdnm.tekrarciz();
		}
		// holdIn("Data receaved", processor_speed);
		// if (packet.getName().equals("xyhafiza"))

		// q.dequeue();

		/*
		 * if (q.isEmpty()) { holdIn("up", INFINITY); } else
		 * holdIn("transmitting", p_delay); p = q.front();
		 */
		 
		
	}
	public void deltcon(double e, message x) {
		deltint();
		deltext(0, x);

	}
	public message out() {
		message m = new message();
	
		return m;
	}

	
	public class TopgraphyGraphic extends JPanel {

		JFrame jfr = new JFrame ("MANET-DEVS");
		private static final long serialVersionUID = 1L;

		public TopgraphyGraphic() {
			// jfr.repaint();
		}

		public void tekrarciz() {
			
			// addCoupling("node_1", ">>output", "node_6", ">>input");
			Random rnd = new Random();
			// componentIterator ac = getComponents().;
			
			for (int i = 0; i <= node-1; i++) {// koordina deðiþiklik
			
				/*
				 * who = xyhafiza[i][0]; xyhafiza[i][1] = xyhafiza[i][1]; xyhafiza[i][2] = xyhafiza[i][2]; xyhafiza[j][1] =
				 * xyhafiza[i][3]; xyhafiza[j][2] = xyhafiza[i][4]; v = xyhafiza[i][5];
				 */
				
				if (xyhafiza[i][1] == xyhafiza[i][3] && xyhafiza[i][2] == xyhafiza[i][4]) {
					// yeni koordinat üret
					xyhafiza[i][1] = xyhafiza[i][3];
					xyhafiza[i][2] = xyhafiza[i][4];
					xyhafiza[i][3] =(xyhafiza[i][1]-50)+rnd.nextInt(100);
					xyhafiza[i][4] =(xyhafiza[i][2]-50)+rnd.nextInt(100);
					if(xyhafiza[i][3]<1)xyhafiza[i][3]=5; 
					if(xyhafiza[i][4]<1)xyhafiza[i][4]=5;
					if(xyhafiza[i][3]>en)xyhafiza[i][3]=en-5;
					if(xyhafiza[i][4]>boy)xyhafiza[i][4]=boy-5;
					xyhafiza[i][5] = 2;//rnd.nextInt(10) + 1;
					
				}
				// if (phaseIs("setup")) {
				if (Math.abs(xyhafiza[i][1]-xyhafiza[i][3])<=xyhafiza[i][5])
					xyhafiza[i][1]=xyhafiza[i][3];
				if (Math.abs(xyhafiza[i][2]-xyhafiza[i][4])<=xyhafiza[i][5])
					xyhafiza[i][2]=xyhafiza[i][4];					                     
					
				distance = Math
						.sqrt(((xyhafiza[i][3] - xyhafiza[i][1]) * (xyhafiza[i][3] - xyhafiza[i][1]))
								+ ((xyhafiza[i][4] - xyhafiza[i][2]) * (xyhafiza[i][4] - xyhafiza[i][2])));

				
				if (xyhafiza[i][5] != 0
						&& (Math.abs(xyhafiza[i][3] - xyhafiza[i][1]) >= xyhafiza[i][5])) {// durma
					// hali
					step = (int) distance / xyhafiza[i][5];
					xyhafiza[i][1] = xyhafiza[i][1]
							+ ((xyhafiza[i][3] - xyhafiza[i][1]) / step);
					xyhafiza[i][2] = xyhafiza[i][2]
							+ ((xyhafiza[i][4] - xyhafiza[i][2]) / step);
				} else if ((xyhafiza[i][5] != 0 && (Math.abs(xyhafiza[i][3]
						- xyhafiza[i][1]) < xyhafiza[i][5]))) {
					xyhafiza[i][1] = xyhafiza[i][3];
					xyhafiza[i][2] = xyhafiza[i][4];
				}
				// }
			}

			jfr.repaint();
		}
		
		public void paint(Graphics g) {
			System.gc();
			super.paint(g);
			
			
			
			for (int i = 0; i <= node-1; i++) {// kapsama alanlarý
		
				g.setColor(m_tBlue);
				g.fillOval(xyhafiza[i][1] - area, xyhafiza[i][2] - area, area*2,area*2);
				g.setColor(Color.yellow);
				g.fillOval(xyhafiza[i][1] - 2, xyhafiza[i][2] - 2,20,20);
			
		
			}

			for (int i = 0; i <= node-1; i++) { // çizgiler
				
				ilk=250;
				for (int j = 0; j <= node-1; j++) {
					
					removeCoupling("node_"+i, ">>output", "node_"+j, ">>input");
					removeCoupling("node_"+j, ">>output", "node_"+i, ">>input");
					if(xyhafiza[j][0]!=1 && i!=j){	
						
						
						distance = Math.sqrt(((xyhafiza[j][1] - xyhafiza[i][1]) * (xyhafiza[j][1] - xyhafiza[i][1]))
								+ ((xyhafiza[j][2] - xyhafiza[i][2]) * (xyhafiza[j][2] - xyhafiza[i][2])));	
						
						if (distance<ilk && xyhafiza[i][0]!=1 ){
					//	System.out.println(i+"....."+j+"....."+distance);	
						addCoupling("Node_"+i, "NIC-out", "Node_"+j, "NIC-in");
						addCoupling("Node_"+j, "NIC-out","Node_"+i, "NIC-in");
						g.setColor(Color.gray);
						g.drawLine(xyhafiza[i][1], xyhafiza[i][2], xyhafiza[j][1],xyhafiza[j][2]);
						b=String.valueOf(i);
						g.setColor(Color.gray);
						g.drawString(b,(int)xyhafiza[i][1],xyhafiza[i][2]);
						}	
						
						
						else continue;
					}
					
					
					
					else continue;	
				}
				
			}
		
		for (int i = 0; i <= node-1; i++) {// hareket yonu
				g.setColor(Color.gray);
				g.drawLine(xyhafiza[i][1] + 7, xyhafiza[i][2] + 7, xyhafiza[i][3],
						xyhafiza[i][4]);
			}/*
			for (int i = 1; i <= node; i++) {// dugumler
				// g.setColor(m_tGreen);
				g.setColor(Color.green);
				g.fillOval(xyhafiza[i][1], xyhafiza[i][2], 15, 15);
			}
			for (int i =1; i <= node; i++) {// isimler
				g.setColor(Color.black);
				g.drawString(("" + xyhafiza[i][0]), xyhafiza[i][1] , xyhafiza[i][2]+12);
			}*/
		}

		public void add(String src, String p1, String dest, String p2) {
			digraph P = getMyParent();
			if (P != null) {
				// update its parent model's coupling info database
				P.addPair(new Pair(src, p1), new Pair(dest, p2));
				// update the corresponding simulator's coupling info database
				coordinator PCoord = P.getCoordinator();
				
				//PCoord.removeCoupling(src, p1, dest, p2);

				// PCoord.addCoupling(src, p1, dest, p2);
			}
			/*
			 * else{ // this is for the distributed simulation situation if
			 * (mySim instanceof RTCoupledSimulatorClient)
			 * ((RTCoupledSimulatorClient
			 * )mySim).addDistributedCoupling(src,p1,dest,p2); }
			 */
		}

		/*
		 * public void paintComponent (Graphics g) { super.paintComponent (g);
		 * Graphics2D g2 = (Graphics2D) g; g.setColor(m_tGreen); g.fillOval(xx,
		 * 10, 15, 15); }
		 */
		public void init() {
			// jfr.pack();
			jfr.setSize(en+50, boy+50);
			jfr.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			jfr.getContentPane().add(new TopgraphyGraphic());			
			jfr.setTitle(cpt);
			jfr.setVisible(true);
			
		}
	}
}
