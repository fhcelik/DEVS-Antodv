package swarmNet.Trace;

/**
 *
 * <p>Title: swarmNet</p>
 * <p>Description: The swarm network based on DEVS formalism</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Arizona State University</p>
 * @author Ahmet Zengin
 * @version 1.0
 */

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import model.modeling.content;
import model.modeling.message;
import swarmNet.Components.IPAddress;
import swarmNet.Components.NETPacket;
import view.modeling.ViewableAtomic;
import GenCol.doubleEnt;

/**
 * This class generates events for components with fixed time intervals
 */
public class EventGenerator extends ViewableAtomic {

	protected double int_arr_time;
	protected int count;
	protected Random rnd;
	protected ArrayList<IPAddress> nodeList;
	protected int[] x;
	private NETPacket packet, p;

	public EventGenerator() {
		this("EventGenr", 10);
	}

	public EventGenerator(String name, double Int_arr_time) {

		super(name);
		addOutport("out");
		addInport("stop");
		addInport("start");
		int_arr_time = Int_arr_time;
		addTestInput("start", new doubleEnt(1));
		/*
		 * addTestInput("start", new doubleEnt(10));
		 * 
		 * addTestInput("stop",new entity(""));
		 */
	}

	public void initialize() {
		phase = "initialization";
		sigma = INFINITY;
		count = 0;
		rnd = new Random();
		setBackgroundColor(Color.yellow);
		super.initialize();
	}

	public void deltext(double e, message x) {

		Continue(e);

		if (phaseIs("listCreated") || phaseIs("passive")) {
			for (int i = 0; i < x.getLength(); i++)
				if (messageOnPort(x, "start", i)) {
					// initialize();
					int_arr_time = ((doubleEnt) x.getValOnPort("start", i)).getv();		
				}
			holdIn("active", int_arr_time);
		}

		if (phaseIs("initialization")) {
			for (int i = 0; i < x.getLength(); i++) {
				if (messageOnPort(x, "stop", i)) {
					p = (NETPacket) x.getValOnPort("stop", i);
					nodeList = (ArrayList<IPAddress>) p.getData();
					// p.print();
				}
			}
			/*
			 * System.out.println("this is my array "); int[] a =
			 * toIntArray(nodeList); for (int i = 0; i < a.length; i++) {
			 * System.out.println(a[i]); }
			 */
			phase = "listCreated";
			//System.out.println("Int IP"+new IPAddress(0,0,5,17).getIntAddress());
			//toIntArray(nodeList);
		}
		if (phaseIs("active")) {
			for (int i = 0; i < x.getLength(); i++)
				if (messageOnPort(x, "stop", i)) {
					phase = "finishing";
					passivate();
				}
		}
	}

	public void deltint() {
		if (phaseIs("active")) {
			count = count + 1;
			holdIn("active", int_arr_time);
		}
		
		  if (count == 5){ passivate(); }
		 
	}

	public message out() {
		message m = new message();
		if (phaseIs("active")) {
			content con = makeContent("out", makePacketEvent("packet" + count));
			m.add(con);
		}
		return m;
	}

	// Compute next time a packet will be created
	// next_time = time - period*((new Float(Math.log(1 -
	// Math.random()))).floatValue());

	/**
	 * Make a packet event with specified name
	 * 
	 * @return packet created packet
	 */
	public NETPacket makePacketEvent(String name) {
		packet = new NETPacket(name);
		packet.setId((byte)2);
		packet.setSource(getRndIP());
		packet.setDest(getRndIP());
		if (packet.getSource().equals(packet.getDest()))
			packet.setDest(getRndIP());
		/*
		 * while (!packet.source.equals(packet.destination)) {
		 * packet.destination = new IPAddress(getRndIP()); }
		 */
		packet.setLength(552);
		// packet.ttl=2*int_arr_time;
		//System.out.println(packet.pPrnt());
		return packet;
	}

	/**
	 * Get random IP address from node ip list
	 * 
	 * @return IP address
	 */
	public IPAddress getRndIP() {
		
		return nodeList.get(rnd.nextInt(nodeList.size()));
	}

	/**
	 * Move the node IP address LinkedList to int array
	 * 
	 * @param nList
	 *            node IP list
	 * @return x[] array
	 */
	public int[] toIntArray(ArrayList<IPAddress> nList) {
		x = new int[nList.size()];
		for (int i = 0; i < nList.size(); i++) {
			x[i] = ((IPAddress) nList.get(i)).getIntAddress();
			System.out.println(x[i]);
		}
		return x;
	}

	public void showState() {
		super.showState();
		System.out.println("int_arr_t: " + int_arr_time);
	}

	public String getTooltipText() {
		return super.getTooltipText() + "\n" + " int_arr_time: " + int_arr_time
				+ "\n" + " count: " + count + "\n" + " nodeNo:"
				+ nodeList.size();

	}
}
