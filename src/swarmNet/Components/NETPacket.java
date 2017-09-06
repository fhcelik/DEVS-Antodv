package swarmNet.Components;

/**
 * <p>Title: swarmNet</p>
 * <p>Description: The swarm network based on DEVS formalism</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Arizona State University</p>
 * @author Ahmet Zengin
 * @version 1.0
 */

//import java.util.Vector;

import GenCol.entity;

/**
 * NETPacket implements a standard IP packet.
 */
public class NETPacket extends entity {

	// private final static int HEADER_SIZE = 20; // Header size of the packet
	// in bytes
	

	private String name;

	private IPAddress source, destination;// Source and destination IP address
	
	private int source_as_no;

	private byte precedence; // Priority (0-7, 7 highest)

	private int length; // Length of the complete packet

	private byte id; // Unique packet id 0=hello 1=LSA 2:data 3:bgp

	// private double ttl; // Time to Live. In seconds, but not quite.

	private Object data; // The data carried by this packet. It does not have

	// to be of size 'length' and is intended for internal purposes
	private IPAddress source_hop; // The current source (i.e. not original

	// source but last/this hop)
	private IPAddress destination_hop; // Current (i.e. not ultimate but next

	// hop)

	// destination
	private byte numHops; // number of packet hops

	private String p_port; // packet output port name

	//private Vector tabuList; // to store packet source_hop(s)

	private String d;

	/**
	 * constructs a packet in specified name and assign default values to packet
	 * variables
	 * 
	 * @param name
	 *            name of the packet
	 */
	public NETPacket(String name_) {
		super(name_);
		name=name_;
		precedence = 0;
		length = 0;
		id = -1;
		numHops = 0;
		// ttl = 0;
		data = "";
		p_port = "";
		source = new IPAddress(0, 0, 0, -1);
		destination = new IPAddress(0, 0, 0, -1);
		source_hop = new IPAddress(0, 0, 0, -1);
		destination_hop = new IPAddress(0, 0, 0, -1);
		//tabuList = new Vector();
	}

	/**
	 * constructs a packet specified name and destination ip
	 * 
	 * @param name
	 *            name of the packet
	 * @param dest
	 *            destination ip address
	 */
	public NETPacket(String name, IPAddress dest) {
		this(name);
		destination = dest;
	}


	  /**
	   * this clones the packet
	   * @param other other packets to be cloned
	   */
	  public NETPacket(NETPacket other) {
	    name=other.name;
	    source = new IPAddress(other.source);
	    destination = new IPAddress(other.destination);
	    source_hop = new IPAddress(other.source_hop);
	    destination_hop = new IPAddress(other.destination_hop);
	    precedence = other.precedence;
	    length = other.length;
	    id = other.id;
	    numHops = other.numHops;
	    data = other.data;
	    p_port = other.p_port;
	    //tabuList=(Vector)other.tabuList.clone();
	    }
	/**
	 * this returns packet name
	 * 
	 * @return packet name
	 */
	public String getName() {
		return name;
	}

	public IPAddress getSource() {
		return source;
	}

	public IPAddress getDest() {
		return destination;
	}

	public IPAddress getSHop() {
		return source_hop;
	}

	public byte getPriority() {
		return precedence;
	}

	public int getLength() {
		return length;
	}

	public byte getNumHops() {
		return numHops;
	}

	public Object getData() {
		return data;
	}

	public String getPort() {
		return p_port;
	}

	
	/**
	 * this sets new name to packet
	 * 
	 * @param Name
	 *            name of the packet
	 */
	public void setName(String Name) {
		name = Name;
	}

	public void setSource(IPAddress s) {
		source = s;
	}
	public void setDest(IPAddress s) {
		destination = s;
	}

	public void setSHop(IPAddress sh) {
		source_hop = sh;
	}

	public void setPriority(byte b) {
		precedence = b;
	}

	public void setLength(int l) {
		length = l;
	}

	public void setNumHops(byte b) {
		numHops = b;
	}

	public void setData(Object d) {
		data = d;
	}

	public void setPort(String s) {
		p_port = s;
	}
	public void setId(byte i){
		id=i;
	}
	public byte getId(){
		return id;
	}
	public int getSource_as_no() {
		return source_as_no;
	}

	public void setSource_as_no(int source_as_no) {
		this.source_as_no = source_as_no;
	}
	/**
	 * this clones the packet
	 * 
	 * @param other
	 *            other packets to be cloned
	 * 
	 * public NETPacket(NETPacket other) { name=other.name; source = new
	 * IPAddress(other.source); destination = new IPAddress(other.destination);
	 * source_hop = new IPAddress(other.source_hop); destination_hop = new
	 * IPAddress(other.destination_hop); precedence = other.precedence; length =
	 * other.length; id = other.id; numHops = other.numHops; ttl = other.ttl;
	 * data = other.data; p_port = other.p_port;
	 * tabuList=(Vector)other.tabuList.clone(); }
	 * 
	 * /** checks equality of both packet
	 * @param m
	 *            packet to be compared
	 * @return true if they are equal
	 */
	public boolean equal(entity m) {
		if (m == this)
			return true;
		else
			return false;
	}

	/**
	 * generates a console output of packet variables
	 * 
	 * @return packet variables in string
	 */
	public String pPrnt() {
		if (destination.getIntAddress() == 0) {
			d = "null";
		} else
			d = destination.toString();
		return "Packet: " + name + "; packet id: " + id + "; destination: " + d
				+ "; source: " + source + "; sourceHop: " + source_hop
				+ "; destinationHop: " + destination_hop + "; number of hops: "
				+ numHops + "; data: " + data + "; out port: " + p_port
				+ "; length: " + length / 1000 + " KB" /*+ "; tabuList: "
				+ tabuList*/;
	}
	public String toString(){
		return name+"\n";
	}

	
}
