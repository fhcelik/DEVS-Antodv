package honeycomb;

/**
 * <p>Title: honeycomb</p>
 * <p>Description: The swarm network based on DEVS formalism</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Arizona State University</p>
 * @author Ahmet Zengin
 * @version 1.0
 */

import honeycomb.IPAddress;
import honeycomb.RouterAtomicMode;
import honeycomb.paketformat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import honeycomb.BRITE.Graph.ASNodeConf;
import honeycomb.BRITE.Graph.Edge;
import honeycomb.BRITE.Graph.Graph;
import honeycomb.BRITE.Graph.Node;
import honeycomb.BRITE.Graph.RouterNodeConf;
import honeycomb.BRITE.Main.BriteDevs;
import honeycomb.BRITE.Util.Util;
import view.modeling.ViewableAtomic;
import view.modeling.ViewableDigraph;

public class GeneratedNetwork extends ViewableDigraph {

	private Node[] nodes;
	private Edge[] edges;
	private int [][]gnode;
	
	private int yedek[];
	private int []m;
	private int []s;
	int xyhafiza[][] ;
	int energy;
	private topography tpy;
	
	private int transmission_range=250,sayma=0,say=0,en=0,son=0;	
	private double distance,olcekkatsayi=1,ilk,t=0,dis,z=0;
	private Graph g;
	private int tx,ty,x = 0, y = 20,x1,y1,x2,y2,a, i=0,v=0,b=0,mesafemiz;

	private final Color colors[] = { Color.yellow, Color.LIGHT_GRAY, Color.cyan,
			Color.gray, Color.green, Color.magenta, Color.orange, Color.pink,
			Color.red, Color.white };
	private int ASid;

	private double simulation_time=10.0;
	private String caption;

	public GeneratedNetwork(){
		this("Network", 1);

		// this.setPreferredSize(new Dimension(2000, 2000));
	}

	public GeneratedNetwork(String name, double processing_time){
		super(name);
		BriteDevs bt = new BriteDevs();
		g = bt.formTopology("GUI_GEN.conf", "seed_file");
		nodes = g.getNodesArray();
		edges = g.getEdgesArray();
		tx = bt.getLS("GUI_GEN.conf");
		ty = bt.getHS("GUI_GEN.conf");
		caption="honeycomb-DEVS Visulasiton Area ( "+ "Node Number= "+ nodes.length +")";
		if (tx>800)
			olcekkatsayi=400.0/tx;		
		tx=(int)((double) tx* olcekkatsayi);
		ty=(int)((double) ty* olcekkatsayi);		
		transmission_range = (int)(transmission_range *olcekkatsayi);	
	
		
		
		m=new int[nodes.length+1];
		s=new int[nodes.length+1];
		yedek=new int[5];
		gnode = new int[nodes.length+1][2];
		addTestInput("NIC-in", new paketformat("packet_data", new IPAddress(0, 0, 0,49)));
		xyhafiza = new int[(nodes.length+1)][7];
		energy=200000;
		make(processing_time);
	}

	
	protected void make(double processing_time) {
		
		
		addInport("in");
		addInport("inEvent");
		addOutport("out");
		addOutport("outEvent");
		
		Util.MSG("DEVS-Suite is processing...");
		HashMap<Integer, Integer> id2id = new HashMap<Integer, Integer>(
				nodes.length);
		for (int i = 0; i < nodes.length; ++i) {
			id2id.put(new Integer(nodes[i].getID()), new Integer(i));
		}
		Arrays.sort(nodes, Node.IDcomparator);
		Arrays.sort(edges, Edge.SrcIDComparator);

	
		ViewableAtomic[] d_nodes = new ViewableAtomic[nodes.length];
		for (int i = 1; i < d_nodes.length; i++) {
			if (nodes[i].getNodeConf() instanceof RouterNodeConf) {
				ASid = ((RouterNodeConf) nodes[i].getNodeConf()).getCorrAS();
			}
			if (nodes[i].getNodeConf() instanceof ASNodeConf) {
				ASid = nodes[i].getID();
			}
			x1 = (int)((double) nodes[i].getNodeConf().getX());
			y1 = (int)((double)nodes[i].getNodeConf().getY());
			gnode[i][0]=x1;
			gnode[i][1]=y1;
		}
		gnode[0][0]=10;
		gnode[0][1]=10;
		//	IPAddress ip=new IPAddress(i);
		//	ip.setByte4(ASid<0?0:ASid);
	

		
		
		
	/*	for (int j = 1; j< nodes.length; j++) {
		for (int i = 1; i < nodes.length; i++) {
			distance = Math.sqrt(((gnode[j][0] - gnode[i][0]) * (gnode[j][0] - gnode[i][0]))
					+ ((gnode[j][1] - gnode[i][1]) * (gnode[j][1] - gnode[i][1])));	
	//		System.out.println("adý: "+i+"...."+yedek+"...."+gnode[yedek][0]+"..."+gnode[i][0]+"..."+gnode[yedek][1]+"..."+ gnode[i][1]);	
			if(distance<35){
				if(var(i)==true){}
				son++;
				m[j]=son;
				
			}
		}}*/

		for (int i = 0; i < d_nodes.length; i++) {	
		
			
			//	System.out.println("adý: "+i);
			d_nodes[i] = new RouterAtomicMode("Node_" + i, new IPAddress(65536+i),processing_time,simulation_time,energy);
			//System.out.println("adý: "+d_nodes[i].getName());
		//	System.out.println("adý: "+gnode[i][0]);
			xyhafiza[i][0]=0;
			xyhafiza[i][1]=gnode[i][0];
			xyhafiza[i][2]=gnode[i][1];//kümebaþý
			xyhafiza[i][3]=gnode[i][0];
			xyhafiza[i][4]=gnode[i][1];
			xyhafiza[i][5]=v;
			xyhafiza[i][6]=i;
			add(d_nodes[i]);
			d_nodes[i].setPreferredLocation(new Point(x, y));
			
			
			if ((i + 1) % 4 == 0) {
				y += 150;
				x = 0;
			} else
				x += 210;
			d_nodes[i].setBackgroundColor(colors[Math.abs(ASid) % colors.length]);
				
		}
		tpy = new topography("Topography", xyhafiza,d_nodes.length, new IPAddress(0,0,0,0),tx,ty,simulation_time,transmission_range,1,caption );// ??? 0 ip problem olurmu
		add(tpy);
		tpy.setPreferredLocation(new Point(50, 50));
		tpy.grafikstart();

		for (int i = 0; i < d_nodes.length; i++) {
			
			
			d_nodes[i].addInport("NIC-in");
			d_nodes[i].addOutport("NIC-out");
			
		}
		
/*		for (int i = 1; i <= d_nodes.length-1; i++) {
			
			x1 = xyhafiza[i][1];
			y1 = xyhafiza[i][2];
			ilk=35;
			for (int j = 1; j <= d_nodes.length-1; j++) {
			
			if(xyhafiza[j][0]!=1 && i!=j){	
				
				x2 = xyhafiza[j][1];
				y2 = xyhafiza[j][2];
				
				
				distance = Math.sqrt(((x2 - x1) * (x2 - x1))
						+ ((y2 - y1) * (y2 - y1)));	
				if (distance<ilk && xyhafiza[i][0]!=1 ){
		//		System.out.println(i+"....."+j+"....."+distance);	
				addCoupling(d_nodes[i], "NIC-out", d_nodes[j], "NIC-in");
				addCoupling(d_nodes[j], "NIC-out", d_nodes[i], "NIC-in");
				
				}	
				
				else if (distance<ilk&& xyhafiza[i][0]==1){
	//				System.out.println(i+"..."+"x1..="+x1+"..."+"y1..="+y1+"..."+j+"..."+"x2..="+x2+"..."+"y2..="+y2+"...."+distance);
					ilk=distance;
					a=j;
			
				}
				
				else continue;
			}
			else continue;	
			
			}
			if (xyhafiza[i][0]==1)
			{	
		//	System.out.println(i+"........"+a+"..."+ilk);	
			addCoupling(d_nodes[i], "NIC-out", d_nodes[a], "NIC-in");
			addCoupling(d_nodes[a], "NIC-out", d_nodes[i], "NIC-in");	
			}
		}
		for (int i = 1; i < d_nodes.length; i++) {
			dist = Math.sqrt(((xyhafiza[i][1] - xyhafiza[0][1]) * (xyhafiza[i][1] - xyhafiza[0][1]))
					+ ((xyhafiza[i][2] - xyhafiza[0][2]) * (xyhafiza[i][2] - xyhafiza[0][2])));	
			if(dist<35 && xyhafiza[i][0]!=1 && i!=1){
				addCoupling(d_nodes[i], "NIC-out", d_nodes[0], "NIC-in");
				addCoupling(d_nodes[0], "NIC-out", d_nodes[i], "NIC-in");
			}
		}
		*/
		// link or couplings definitions");

	/*	for (int i = 0; i < edges.length; ++i) {
			Edge e = edges[i];
			int srcIndex = ((Integer) id2id
					.get(new Integer(e.getSrc().getID()))).intValue();
			int dstIndex = ((Integer) id2id
					.get(new Integer(e.getDst().getID()))).intValue();
			src = d_nodes[srcIndex];
			dst = d_nodes[dstIndex];
			
			src_port_no = "NIC" + (dst.getNumInports() + 1);
			src.addInport(src_port_no + "-in");
			src.addOutport(src_port_no + "-out");
			dst_port_no = "NIC" + (dst.getNumInports() + 1);
			dst.addInport(dst_port_no + "-in");
			dst.addOutport(dst_port_no + "-out");
			addCoupling(src, src_port_no + "-out", dst, dst_port_no + "-in");
			addCoupling(dst, dst_port_no + "-out", src, src_port_no + "-in");
		//	System.out.println(src_port_no + "-out"+"...."+dst_port_no + "-in");
		}*/
		
		for (int i = 0; i < d_nodes.length; i++) {
			d_nodes[i].addInport("inEvent");
			d_nodes[i].addOutport("outEvent");
			d_nodes[i].addInport("inTopo");
			d_nodes[i].addOutport("outTopo");
		}
		tpy.addInport("inEvent");
		tpy.addOutport("outEvent");
		addCoupling(this, "inEvent", tpy, "inEvent");
	    addCoupling(tpy, "outEvent", this, "outEvent" );
		for (int i=0; i < d_nodes.length; i++) {
		    addCoupling(this, "inEvent", d_nodes[i], "inEvent");
		    addCoupling(d_nodes[i], "outEvent", this, "outEvent" );
		     addCoupling(tpy, "NIC-out", d_nodes[i],"inTopo" );
		     addCoupling(d_nodes[i], "outTopo", tpy,"NIC-in"  );

		}
//		addCoupling(this, "in", d_nodes[0], "NIC1-in");
		initialize();
	
		/*
		 * 
		 * for (int i = 0; i < nodes.length; i++) { if (i != 0 && i % 10 == 0) {
		 * y += 200; x = 0; } ((ViewableComponent) withName("Router" + i))
		 * .setPreferredLocation(new Point(x, y)); x ; }
		 */
	}

	public void layoutForSimView() {
		preferredSize = new Dimension(1000, 10000);
		// ((ViewableComponent)withName("Experimental Frame")).setPreferredLocation(new
		// Point(428, 512));
		// ((ViewableComponent)withName("Network")).setPreferredLocation(new
		// Point(10, 18));
	}


	
	
}
