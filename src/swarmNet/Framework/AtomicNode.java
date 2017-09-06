package swarmNet.Framework;

/**
 * <p>Title: swarmNet</p>
 * <p>Description: The swarm network based on DEVS formalism</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Arizona State University</p>
 * @author Ahmet Zengin
 * @version 1.0
 */

import java.awt.Color;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import model.modeling.message;
import swarmNet.Components.Dijkstra;
import swarmNet.Components.IPAddress;
import swarmNet.Components.NETPacket;
import swarmNet.Components.Queue;
import swarmNet.Components.Route;
import swarmNet.Components.RoutingTable;
import view.modeling.ViewableAtomic;

/**
 * This node class simply implements the network node.
 */
public class AtomicNode extends ViewableAtomic {

	private NETPacket packet;

	private Vector<Route> neighbour_table;

	private Vector<NETPacket> topology_database;
	
	private Hashtable<Integer, IPAddress> bgpTable;
	
	private int nextNodeOnRoute;

	private Queue queue;

	private double processing_time;

	private IPAddress address;

	private byte nic_n;

	private int packet_count, packet_destined;

	private String port_name;

	private byte version;

	int incomingLSA, discardedLSA, discardedData;
	
	
	//int as_no;

	/**
	 * Constructs a node with name, specific IP address, process time and NIC
	 * number
	 * 
	 * @param name
	 *            components name for DEVSJAVA simulation viewer
	 * @param addr
	 *            IP address of this node
	 * @param Processing_time
	 *            elapsed time for processing a packet
	 * @param numberOfNIC
	 *            network interface card number of node
	 */
	public AtomicNode(String name, IPAddress addr, double Processing_time) {
		super(name);
		address = addr;
		//as_no=as;
		nic_n = (byte) getNumInports();
		processing_time = Processing_time;
		addTestInput("NIC1-in", new NETPacket("packet_data", new IPAddress(0, 0, 0, 9)));

		/*
		 * for (int i = 0; i < nic_n; i++) { addInport("NIC" + (i + 1) + "-in");
		 * addOutport("NIC" + (i + 1) + "-out");
		 * 
		 * }
		 */
		// addInport("inEvent");
		// addOutport("outEvent");
		/*
		 * for (int i = 0; i < nic_n; i++) addTestInput("NIC" + (i + 1) + "-in",
		 * new NETPacket("packet" + (i + 1)));
		 */

	}

	public AtomicNode() {
		this("Router", new IPAddress(0, 0, 0, 1), (double) 10);
	}

	public void initialize() {
		//addCoupling("Router0", "NIC1-out", "Router1", "NIC1-in");
		super.initialize();
		
		phase = "startup";
		sigma = 0;
		
		neighbour_table = new Vector<Route>();
		topology_database = new Vector<NETPacket>();
		bgpTable=new Hashtable<Integer, IPAddress>();
		queue = new Queue(200000);
		packet = new NETPacket("packet");
		packet_count = 0;
		packet_destined = 0;
		version = 0;
		incomingLSA = 0;
		discardedLSA = 0;
		discardedData = 0;
		colorInit();
	}

	public void deltext(double e, message x) {
		Continue(e);
		for (int i = 0; i < x.getLength(); i++) {
			for (int k = 0; k < getNumInports(); k++) {
				port_name = (String) getInportNames().get(k);
				if (messageOnPort(x, port_name, i)) {
					packet = (NETPacket) x.getValOnPort(port_name, i);
					if (packet.getId() == 1)
						incomingLSA++;// for tracking
					packet.setPort(port_name);
					if (port_name.equals("inEvent") && packet.getId() == 2
							&& !packet.getSource().equals(address)) {
						// System.out.println(address +
						// " Data packet is Discarded");
						discardedData++;
						continue;
					}
					/*if(packet.getId() == 1 && packet.getNumHops()>20) {
						discardedLSA++;
						//System.out.println("iï¿½te oldu");
						continue;
						
					} else*/ if (packet.getId() == 1 && packet.getSource().equals(address)) {
						discardedLSA++;
						// System.out.println(address +
						// " LSA packet is Discarded");
						continue;
					} else if (packet.getId() == 1 && hasLSAversion(packet)) {
						//System.out.println(address + "niye dï¿½ï¿½medi" + packet.pPrnt());
						// System.out.println(address +
						// " LSA packet is Discarded");
						discardedLSA++;
						continue;
					} /*else if (packet.getId() == 1 && packet.getSource().getByte4()!=address.getByte4()){
						discardedLSA++;
						continue;
					}*/ else if (queue.enqueue(packet)) {
						holdIn("queuing", processing_time);
					} else {
						System.out.println(address + "discardedDueQueue");
						holdIn("congested", processing_time);
						setBackgroundColor(Color.red);
					}

				}
			}
		}
	}

	public void deltint() {
	removeCoupling("Router0", "NIC1-out", "Router1", "NIC1-in");
		formBGPTable();
		if (queue.isEmpty())
			holdIn("idle", INFINITY);
		 if (!queue.isEmpty()) {
			packet = queue.dequeue();// simply dequeues the queue
			if (packet != null) {
				if (packet.getId() == 0) {//hello
							neighbour_table.addElement(new Route(packet.getSource(), packet.getSource(),
							packet.getPort(), (byte) 1));
					// if (neighbour_table.size() == nic_n)
					holdIn("flooding", processing_time);
					// else
					// holdIn("addingNbor", processing_time);
				} else if (packet.getId() == 1 && hasLSAversion(packet)) {//lsa
					//System.out.println(address + "çalýþmamalý" + packet.pPrnt());
				} else if (packet.getId() == 1 && !hasLSAversion(packet)) {
					topology_database.add(packet);
					holdIn("new LSA added", processing_time);
					
				} else if (packet.getId() == 2 && !packet.getDest().equals(address)) {//data
					if(isNeighbor(packet.getDest())){
						nextNodeOnRoute=packet.getDest().getIntAddress();
					} else if (packet.getDest().getByte4()!=address.getByte4()){
						//System.out.println("Packet's origin "+ address + "dest : " +packet.getDest());
						if(bgpTable.get(packet.getDest().getByte4()).equals(address)){
							nextNodeOnRoute=bgpTable.get(packet.getDest().getByte4()).getIntAddress();
						}else if(isNeighbor(bgpTable.get(packet.getDest().getByte4()))){
							nextNodeOnRoute=(bgpTable.get(packet.getDest().getByte4())).getIntAddress();
						} else{		
						RoutingTable rt = new RoutingTable(topology_database, neighbour_table, address);
						rt.getRoute();
					//	System.out.println(address+""+);
						final int[] pred = Dijkstra.dijkstra(rt, address.getIntAddress());
						nextNodeOnRoute = Dijkstra.getRoute(rt, pred, address.getIntAddress(),
								bgpTable.get(packet.getDest().getByte4()).getIntAddress()).get(1);
						
						}
						} else if (packet.getDest().getByte4()==address.getByte4()){
					
					RoutingTable rt = new RoutingTable(topology_database, neighbour_table, address);
					rt.getRoute();
					final int[] pred = Dijkstra.dijkstra(rt, address.getIntAddress());
					nextNodeOnRoute = Dijkstra.getRoute(rt, pred, address.getIntAddress(),
							packet.getDest().getIntAddress()).get(1);
					}
					packet_count++;
					//System.out.println(name +" "+packet.pPrnt());
					holdIn("gettingRoute", processing_time);
					
				} else if (packet.getId() == 2 && packet.getDest().equals(address)) {
					// System.out.println(address + " subnetting"+packet );
					packet_destined++;
					holdIn("subNetting", processing_time);

				} else
					System.out.println(address + "burasý iþi bozar" + packet.pPrnt());

			} else if (packet == null)
				System.out.println(address + "sorun var demektir.");
		}
	}

	public void deltcon(double e, message x) {
		deltext(0, x);
		deltint();
	}

	public message out() {
		//removeCoupling("Router0", "NIC1-out", "Router1", "NIC1-in");
		//removeModel("Router1");
		message m = new message();
		if (phaseIs("flooding")&&neighbour_table.size()==getNumInports()-1) {
			for (int i = 0; i < getNumOutports(); i++){
				m.add(makeContent((String) (getOutportNames().get(i)), createLSA()));
			}//else System.out.println("null dï¿½ndï¿½");
			
		} else if (phaseIs("startup")) {
			for (int i = 0; i < getNumOutports(); i++)
				m.add(makeContent((String) (getOutportNames().get(i)), createHello()));
		} else if (phaseIs("new LSA added")) {
			for (int i = 0; i < getNumOutports(); i++) {
				if (((String) getOutportNames().get(i)).charAt(3) == packet.getPort().charAt(3))
					continue;//incoming link of the packet
				
				if (getNeighborSource((String) getOutportNames().get(i))!=(null)){
					if (packet.getSource().getByte4()==address.getByte4()&&
							getNeighborSource((String) getOutportNames().get(i)).getByte4()!=address.getByte4())
						continue; //packet is from relative and outgoing link is not relative 
					//else if (packet.getSource().getByte4()!=address.getByte4()&&isBGProuter())
					//{// packet is not relative and bgp router
						
					//}
				}
				NETPacket pct=new NETPacket(packet);
				pct.setSHop(address);
				pct.setNumHops((byte)(packet.getNumHops()+1));
				m.add(makeContent((String) (getOutportNames().get(i)), pct));
				//holdIn("forwardingLSA", processing_time);
			}
	} else if (phaseIs("gettingRoute")) {
			
			// System.out.println(outPort);
			m.add(makeContent(getNeigborPortName(nextNodeOnRoute), new NETPacket(packet)));
			// return m;
		} else if (phaseIs("subNetting"))
			m.add(makeContent("outEvent", new NETPacket(packet)));
		return m;
	}

	/*
	 * public void showState() { super.showState();
	 * 
	 * System.out.println(); System.out.println(name + "'s IP address: " +
	 * address + "\n" + "Number of packet in receiver queue: " +
	 * Interface.receiver_size() + "\n" + "Number of packet in sender queue: " +
	 * Interface.sender_size()); }
	 */

	public String getTooltipText() {
		return "IPAddress: " + address   /* + super.getTooltipText() */
				+ "<br>" +"AS number: " + address.getByte4()
				+ "<br>" + "QueueState: " + queue.size() + " packets("
				+ queue.getCurLength() / 1000 + "KB)" + "<br>" + "Number of Routed Packets: "
				+ packet_count + "<br>" + "Number of Destined Packets: " + packet_destined + "<br>"
				+ "Number of Discarded Packets: " + discardedData + "<br>" + discardedLSA + " of "
				+ incomingLSA + " LSA discarded" + "<br>" + "Neigbor Table :" + "<br>"
				+ "===================" + "<br>" + "Number of Neigbors: " + neighbour_table.size()
				+ "<br>" + neighbour_table 
				+ "<br>" + "BGP Table :" + "<br>"
				+ "===================" + "<br>" + "Number of networks: " + bgpTable.size()
				+ "<br>" + bgpTable 
				+ "<br>" + "Topology Database: " + "<br>"
				+ "===================" + "<br>" + "Number of LSA's: " + topology_database.size()
				+ "<br>" + topology_database ;
	}
	public String yaz(){
		String str="";
		for(int i=0;i<topology_database.size();i++) 
			str+= ((NETPacket)(topology_database.get(i))).pPrnt()+"\n";
		return str;
	}

	public void colorInit() {
		if (nic_n == 1)
			setBackgroundColor(new Color(125, 125, 125));
		else if (nic_n == 2)
			setBackgroundColor(new Color(155, 155, 155));
		else if (nic_n == 3)
			setBackgroundColor(Color.YELLOW);
		else if (nic_n == 4)
			setBackgroundColor(Color.green);
	}

	private void formBGPTable(){
		Enumeration<Route> g=neighbour_table.elements();	
		while(g.hasMoreElements()){
			Route r=g.nextElement();
			if (r.getDest().getByte4()!=address.getByte4()){
				bgpTable.put(r.getDest().getByte4(),r.getNextHop());
			}
		}
		Enumeration<NETPacket> e=topology_database.elements();
		while(e.hasMoreElements()){
			NETPacket p=e.nextElement();
			Vector<Route> v=(Vector<Route>)(p.getData());
			for(int i=0; i<v.size();i++){
				
				if(p.getSource().getByte4()==address.getByte4()){//paket akrabadan ise
					//System.out.println("burasï¿½ niye ï¿½alï¿½ï¿½ï¿½r");
				if (v.get(i).getDest().getByte4()!=address.getByte4()){//akraba komï¿½usu akraba deï¿½ilse
					if (!bgpTable.containsKey(v.get(i).getDest().getByte4())) //bgp tablosunda yok ise
						if(isNeighbor(p.getSource())){//paket komï¿½udan ise
						bgpTable.put(v.get(i).getDest().getByte4(), p.getSource());
				} else if(!isNeighbor(p.getSource())){
					bgpTable.put(v.get(i).getDest().getByte4(), p.getSHop());
				}
				}
			} else if(p.getSource().getByte4()!=address.getByte4()){//paket akrabadan deï¿½ilse
				if (v.get(i).getDest().getByte4()!=address.getByte4()){ //akraba komï¿½usu akraba deï¿½ilse 
					if (!bgpTable.containsKey(v.get(i).getDest().getByte4())){ //bgp tablosunda yok ise
						if(isNeighbor(p.getSource())){
						bgpTable.put(v.get(i).getDest().getByte4(), p.getSource());
				} else bgpTable.put(v.get(i).getDest().getByte4(), p.getSHop());
				}
				}	
			}
	}
	}
	}
	private boolean isBGProuter(){
		Enumeration<Route> e=neighbour_table.elements();	
		while(e.hasMoreElements())
			if((e.nextElement().getDest().getByte4()!=address.getByte4())) return true;	
		return false;		
	}
	private boolean isNeighbor(IPAddress ip){
		Enumeration<Route> e=neighbour_table.elements();	
		while(e.hasMoreElements()){
			IPAddress i=e.nextElement().getDest();
			if(i.equals(ip)) {
				//System.out.println("ï¿½ï¿½te bak : " +i+" eï¿½it "+ ip);
				return true;	
				}
		}
		return false;		
	}
	
	private boolean hasLSAversion(NETPacket packet) {
		for(int i=0; i<topology_database.size();i++){
			if(packet.getSource().getByte4()!=address.getByte4()){
				if(topology_database.get(i).getSource().getByte4()==packet.getSource().getByte4())
					return true;
			} else if(packet.getSource().getByte4()==address.getByte4())
			if(packet.getName().equals(topology_database.get(i).getName())){
				//System.out.println(address+packet.getName()+topology_database.get(i));
				return true;	
			}
		}
		return false;
	}
		
	public IPAddress getNeighborSource(String pname){
		Enumeration<Route> e=neighbour_table.elements();	
		while(e.hasMoreElements()){
			Route r=e.nextElement();
			if((r.getOutPortName().substring(0, 5) + "out").equals(pname)) return r.getDest();	
		}
		return null;
	}
	
	private String getNeigborPortName(int i){
		Enumeration<Route> e=neighbour_table.elements();	
		while(e.hasMoreElements()){
			Route r=e.nextElement();
			if (r.getDest().getIntAddress() == i)
				return r.getOutPortName().substring(0, 5) + "out";
		} 
		return null;
	}
	private NETPacket createHello() {
		addCoupling("Router0", "NIC2-out", "Router2", "NIC1-in");
		removeCoupling("Router0", "NIC1-out", "Router1", "NIC1-in");
		NETPacket p = new NETPacket("hello");
		p.setName("hello");
		p.setId((byte) 0);
		p.setSource(address);
		p.setPriority((byte) 7);
		p.setLength(20);
		return p;
	}

	private NETPacket createLSA() {
		NETPacket p = new NETPacket("LSA");
		p.setId((byte) 1);
		p.setName("LSA_v" + version + "of" + name);
		p.setSource(address);
		p.setSHop(address);
		p.setPriority((byte) 0);
		p.setLength(20);
		p.setData(neighbour_table);
		p.setNumHops((byte)0);
		//p.setSource_as_no(as_no);
		return p;
	}
}
