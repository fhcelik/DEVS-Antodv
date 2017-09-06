package swarmNet.Framework;

/**
 * <p>Title: swarmNet</p>
 * <p>Description: The swarm network based on DEVS formalism</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Arizona State University</p>
 * @author Ahmet Zengin
 * @version 1.0
 */

//import genDevs.modeling.content;
//import genDevs.modeling.message;
import java.awt.Color;
import java.util.LinkedList;

import model.modeling.content;
import model.modeling.message;
import view.modeling.ViewableAtomic;
//import simView.ViewableAtomic;

/**
 * This Link class simply implements the network channel.
 */
public class Link extends ViewableAtomic {
	private double p_delay; // propogation delay of link (in sec.)- 1e-3 sec.
	private int bw; // bandwidth of a link(in bytes/sec)- 193000 bytes/sec
	private content con;
	private LinkedList<content> queue;
	private String port_name;

	public Link(String name, int bandwidth, double propagation_delay) {
		super(name);
		p_delay = propagation_delay;
		bw = bandwidth;
		addInport("in");
		addInport("in1");
		// addInport("inEvent");
		addOutport("out");
		addOutport("out1");
		// addOutport("outEvent");
	}

	public Link() {
		this("Link", 193000, 0.001);
	}

	public void initialize() {
		phase = "up";
		sigma = INFINITY;
		queue = new LinkedList<content>();
		super.initialize();
		setBackgroundColor(new Color(205, 241, 31));
	}

	public void deltext(double e, message x) {
		Continue(e);
		for (int i = 0; i < x.getLength(); i++) {
			for (int k = 0; k < getNumInports(); k++) {
				port_name = (String) getInportNames().get(k);
				if (messageOnPort(x, port_name, i)) {
					if (port_name.equals("in"))
						con = makeContent("out", x.getValOnPort("in", i));
					else
						con = makeContent("out1", x.getValOnPort("in1", i));
					queue.add(con);
					holdIn("busy", p_delay);
				}
			}
		}
	}

	public void deltint() {
		con = queue.removeFirst();
		if (!queue.isEmpty()) {
			// System.out.println(name + con);
			holdIn("busy", p_delay);
		} else
			holdIn("up", INFINITY);
	}

	public void deltcon(double e, message x) {
		deltint();
		deltext(0, x);

	}

	public message out() {
		message m = new message();
		m.add(con);
		return m;
	}

	/*
	 * public void showState() { super.showState(); //System.out.println();
	 * System.out.println("Number of packet in queue: " + q.size()); }
	 */

	public String getTooltipText() {
		return super.getTooltipText() + "<br>" + "Bandwidth: " + bw / 1000
				+ "KB" + "<br>" + "#ofPacketsInLink: " + queue.size() + "<br>"
				+ "BandwidthUtilization: " + "KB";
	}

}
