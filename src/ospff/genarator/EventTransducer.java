package ospff.genarator;

import view.modeling.ViewableAtomic;

import GenCol.*;
import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;

import ospff.IPAddress;
import ospff.paketformat;

import model.modeling.content;
import model.modeling.message;

public class EventTransducer extends ViewableAtomic {
	protected Hashtable<String, Object> arrived, solved;
	protected double clock, total_ta, observation_time;
	protected LinkedList<IPAddress> node_addrs;
	private paketformat packet, val;
	protected int x[];
	public DataOutputStream outFile;

	public EventTransducer() {
		this("EventTransducer", 20);
	}

	public EventTransducer(String name, double Observation_time) {
		super(name);

		addOutport("out");
		addInport("ariv");
		addInport("solved");
		addInport("start");
		observation_time = Observation_time;

		addTestInput("ariv", new paketformat("data"));
		addTestInput("solved", new paketformat("data"), 10);
	}

	public void initialize() {

		setBackgroundColor(Color.orange);
		phase = "initialization";
		sigma = INFINITY;
		clock = 0;
		total_ta = 0;
		arrived = new Hashtable<String, Object>();
		solved = new Hashtable<String, Object>();
		node_addrs = new LinkedList<IPAddress>();
		packet = new paketformat("nodeList");
		try {
			outFile = new DataOutputStream(new BufferedOutputStream(
					new FileOutputStream("output.csv")));
		} catch (Exception a) {
			System.out.println("Error: " + a.getMessage());
		}

	}

	public void deltext(double e, message x) {
		clock = clock + e;
		Continue(e);
		if (phaseIs("initialization")) {
			for (int i = 0; i < x.getLength(); i++) {
				if (messageOnPort(x, "solved", i)) {
					val = (paketformat) x.getValOnPort("solved", i);

					node_addrs.add(val.getAdres());
				}
			}
			holdIn("listCreating", 0);
		} else {
			for (int i = 0; i <x.getLength(); i++) {
				if (messageOnPort(x, "ariv", i)) {
					val = (paketformat) x.getValOnPort("ariv", i);
					arrived.put(val.getAdi(), new doubleEnt(clock));

			//		System.out.println(arrived.size());
				}
				if (messageOnPort(x, "start", i)) {
					initialize();
					holdIn("active", observation_time);
					sigma = observation_time;
					// clock = clock + sigma;
				}

				if (messageOnPort(x, "solved", i)) {

					val = (paketformat) x.getValOnPort("solved", i);
					//System.out.println(val.getAdi() + "burasý çalýþýr");
					if (arrived.containsKey(val.getAdi())) {
						//System.out.println("sdasdasd");
						// calculate the packet processing time and the total
						// through-time
						entity ent = (entity) arrived.get(val.getAdi());
						doubleEnt num = (doubleEnt) ent;
						double arrival_time = num.getv();
						double turn_around_time = clock - arrival_time;
						total_ta = total_ta + turn_around_time;

						solved.put(val.getAdi(), new doubleEnt(clock));
					}
				}
			}
		}
		// show_state();
		// Write starts data to file
		try {
			// outFile.writeBytes(
			// "Clock,Arrived Packets,Solved Packets,lost packets,TA,Thru\n");
			//System.out.println("varan paket" + arrived.size());
			//System.out.println("çözülen paket" + solved.size());
			outFile.writeBytes(clock + "," +
                    arrived.size() + "," 
					+ solved.size() + ";" + (arrived.size() - solved.size())
					+ ";" + compute_TA() + ";" + compute_Thru() + "\n");

			// else outFile.close();
		} catch (Exception a) {
			System.out.println("Error: " + a.getMessage());
		}
	}

	public void deltint() {
		passivate();
		show_state();
	}

	public message out() {

		message m = new message();
		if (phaseIs("listCreating")) {
			packet.setData(getNodeList());
			// packet.print();
			m.add(makeContent("out", packet));
		}
		if (phaseIs("active")) {
			content con = makeContent("out", new entity("TA: " + compute_TA()));
			m.add(con);
		}
		return m;

	}

	public double compute_TA() {
		double avg_ta_time = 0;
		if (!solved.isEmpty())
			avg_ta_time = ((double) total_ta) / solved.size();
		return avg_ta_time;
	}

	public double compute_Thru() {
		double thruput = 0;
		if (clock > 0)
			thruput = solved.size() / (double) clock;
		return thruput;
	}

	public Object getNodeList() {
		return node_addrs;
	}

	public int[] toIntArray() {
		x = new int[node_addrs.size()];
		for (int i = 0; i < node_addrs.size(); i++) {
			x[i] = ((IPAddress) node_addrs.get(i)).getIntegerAddress();
		}
		return x;
	}

	/*
	 * public void showState(){ super.showState();
	 * System.out.println("arrived packets: " + arrived.size());
	 * System.out.println("solved packets: " + solved.size());
	 * //System.out.println("size of the array: "+ x.length);
	 * //System.out.println("TA: "+compute_TA());
	 * //System.out.println("Thruput: "+compute_Thru()); }
	 */

	public void show_state() {
		int num_arrived = 0;
		int num_solved = 0;
		int total_hops = 0;
		System.out.println("state of  " + name + ": ");
		System.out.println("phase, sigma : " + phase + " " + sigma + " ");

		if (arrived != null)
			num_arrived = arrived.size();
		if (solved != null)
			num_solved = solved.size();

		// print the packet summary information
		System.out
				.println("arrived packets is " + num_arrived + " " + total_ta);
		System.out.println("solved packets is " + num_solved);
		System.out.println("lost packets is " + (num_arrived - num_solved));
		if (num_arrived > 0)
			System.out.println("Percentage of lost packets is "
					+ (double) (num_arrived - num_solved)
					/ (double) num_arrived);
		else
			System.out.println("No packets arrived");
		System.out.println("AVG TA = " + compute_TA());
		System.out.println("THRUPUT = " + compute_Thru());
	}

	public String showS() {
		String str;
		int num_arrived = 0;
		int num_solved = 0;
		int total_hops = 0;

		if (arrived != null)
			num_arrived = arrived.size();
		if (solved != null)
			num_solved = solved.size();
		if (num_arrived > 0) {
			str = "Percentage of lost packets is "
					+ (double) (num_arrived - num_solved)
					/ (double) num_arrived;
		}

		else {
			str = "No packets arrived";
		}

		// print the packet summary information
		return "clock: " + clock + "<br>" + "total_ta: " + total_ta + "<br>"
				+ "arrived packets is " + num_arrived + "<br>"
				+ "solved packets is " + num_solved + "<br>"
				+ "lost packets is " + (num_arrived - num_solved) + "<br>"
				+ str + "<br>" + "AVG TA = " + compute_TA() + "<br>"
				+ "THRUPUT = " + compute_Thru();

	}

	public String getTooltipText() {
		try {
			outFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.getTooltipText() + "<br>" + showS();
	}
}
