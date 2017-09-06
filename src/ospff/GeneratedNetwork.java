package ospff;

/**
 * <p>Title: ospff</p>
 * <p>Description: The swarm network based on DEVS formalism</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Arizona State University</p>
 * @author Ahmet Zengin
 * @version 1.0
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;

import ospff.BRITE.Graph.ASNodeConf;
import ospff.BRITE.Graph.Edge;
import ospff.BRITE.Graph.Graph;
import ospff.BRITE.Graph.Node;
import ospff.BRITE.Graph.RouterNodeConf;
import ospff.BRITE.Main.BriteDevs;
import ospff.BRITE.Util.Util;
import ospff.IPAddress;
import ospff.paketformat;
import ospff.RouterAtomicMode;
import view.modeling.ViewableAtomic;
import view.modeling.ViewableDigraph;

public class GeneratedNetwork extends ViewableDigraph {

	private ViewableAtomic src;
	private ViewableAtomic dst;
	private String src_port_no;
	private String dst_port_no;
	private Node[] nodes;
	private Edge[] edges;
	private Graph g;
	private int x = 0, y = 20;
	private final Color colors[] = { Color.yellow, Color.LIGHT_GRAY, Color.cyan,
			Color.gray, Color.green, Color.magenta, Color.orange, Color.pink,
			Color.red, Color.white };
	private int ASid;

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
		make(processing_time);
		addTestInput("in", new paketformat("packet_data", new IPAddress(0, 0, 0,
				49)));

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
		for (int i = 0; i < d_nodes.length; i++) {
			if (nodes[i].getNodeConf() instanceof RouterNodeConf) {
				ASid = ((RouterNodeConf) nodes[i].getNodeConf()).getCorrAS();
			}
			if (nodes[i].getNodeConf() instanceof ASNodeConf) {
				ASid = nodes[i].getID();
			}
			IPAddress ip=new IPAddress(i);
			ip.setByte4(ASid<0?0:ASid);
			d_nodes[i] = new RouterAtomicMode("Router" + i, ip,processing_time,1);		
			add(d_nodes[i]);
			d_nodes[i].setPreferredLocation(new Point(x, y));
			if ((i + 1) % 4 == 0) {
				y += 150;
				x = 0;
			} else
				x += 210;
			d_nodes[i].setBackgroundColor(colors[Math.abs(ASid) % colors.length]);
					
		}

		// link or couplings definitions");

		for (int i = 0; i < edges.length; ++i) {
			Edge e = edges[i];
			int srcIndex = ((Integer) id2id
					.get(new Integer(e.getSrc().getID()))).intValue();
			int dstIndex = ((Integer) id2id
					.get(new Integer(e.getDst().getID()))).intValue();
			src = d_nodes[srcIndex];
			dst = d_nodes[dstIndex];
			
			src_port_no = "NIC" + (src.getNumInports() + 1);
			src.addInport(src_port_no + "-in");
			src.addOutport(src_port_no + "-out");
			dst_port_no = "NIC" + (dst.getNumInports() + 1);
			dst.addInport(dst_port_no + "-in");
			dst.addOutport(dst_port_no + "-out");
			addCoupling(src, src_port_no + "-out", dst, dst_port_no + "-in");
			addCoupling(dst, dst_port_no + "-out", src, src_port_no + "-in");
			
		}
		for (int i = 0; i < d_nodes.length; i++) {
			d_nodes[i].addInport("inEvent");
			d_nodes[i].addOutport("outEvent");
		}
		
		for (int i=0; i < d_nodes.length; i++) {
		     addCoupling(this, "inEvent", d_nodes[i], "inEvent");
		     addCoupling(d_nodes[i], "outEvent", this, "outEvent" );
		}
		addCoupling(this, "in", d_nodes[0], "NIC1-in");
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
