package swarmNet.Components;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

public class RoutingTable {

	private Vector<int[]> link_costs; // adjacency matrix
	private int[] links;
	private int[] node_list;
	private Vector<NETPacket> td_base = new Vector<NETPacket>();
	private IPAddress address;
	private Vector<Route> neighbour_table;

	public RoutingTable(Vector<NETPacket> topology_database, Vector<Route> neighbours,
			IPAddress addr) {
		address = addr;
		Enumeration<NETPacket> e = topology_database.elements();
		while (e.hasMoreElements()) {
			NETPacket p = e.nextElement();
			if (p.getSource().getByte4() == address.getByte4())
				td_base.add(p);
		}
		// addr.setByte4(0);

		neighbour_table = neighbours;
		// td_base =shrinkTDbase(topology_database);
		// System.out.println(address+" "+td_base.size());
		link_costs = new Vector<int[]>();

		node_list = new int[td_base.size() + 1];
	}

	public int size() {
		return node_list.length;
	}

	public void setNode(int vertex, int address) {
		node_list[vertex] = address;
	}

	public int getNode(int vertex) {
		return node_list[vertex];
	}

	public void addLinkCost(int source, int destination, int cost) {
		links = new int[3];
		// link_costs[source][destination] = cost;
		links[0] = source;
		links[1] = destination;
		links[2] = cost;
		link_costs.add(links);
	}
	/*
	 * public boolean isLink(int source, int target) { return
	 * link_costs[source][target] > 0; }
	 * 
	 * public void removeLinkCost(int source, int target) {
	 * link_costs[source][target] = 0; }
	 * */
	 public int getCost(int source, int target) {
		 
		return 1;
	}
	 
	public ArrayList<Integer> neighbors(int vertex) {
		//System.out.print("neigbors of " + vertex +": ");
		if (vertex == address.getIntAddress()) {

			return getNeighbor(neighbour_table);

		} else {
			Enumeration<NETPacket> f = td_base.elements();
			while (f.hasMoreElements()) {
				NETPacket p = f.nextElement();
				if (vertex == p.getSource().getIntAddress()) {
					return getNeighbor((Vector<Route>) p.getData());
				}
			}
		}
		return null;
	}
	public ArrayList<Integer> getNeighbor(Vector<Route> neighbors) {
		final ArrayList<Integer> answer = new ArrayList<Integer>();
		Enumeration<Route> e = neighbors.elements();
		while (e.hasMoreElements()) {
			Route r = e.nextElement();
			if(r.getDest().getByte4()==address.getByte4()){
			answer.add(r.getDest().getIntAddress());
			//System.out.print(r.getDest().getIntAddress()+" ");
		}
			}
		//System.out.println();
		return answer;
	}
	
	 /*public void print() {
		for (int j = 0; j < link_costs.size(); j++) {
			
			System.out.print(node_list[j] + ": ");
			for (int i = 0; i < link_costs[j].length; i++) {
				if (link_costs[j][i] > 0)
					System.out.print(node_list[i] + ":" + link_costs[j][i] + " ");
			}
			System.out.println();
		}
	}
	 
	 /** public void printf() { for (int j = 0; j < link_costs.length; j++) { for
	 * (int i = 0; i < link_costs[j].length; i++) { // if (link_costs[j][i] > 0)
	 * System.out.print(link_costs[j][i] + "\t"); } System.out.println(); } }
	 */
	public void print(){
		for(int i=0; i<link_costs.size(); i++){
			int[] a=link_costs.elementAt(i);
			for(int j=0; j<a.length;j++){
				System.out.print(a[j]);
			}
			System.out.println();
		}
	}
	public void getRoute() {
		setNode(0, address.getIntAddress());
		for (int i = 0; i < td_base.size(); i++)
			setNode(i + 1, ((NETPacket) td_base.elementAt(i)).getSource().getIntAddress());
		orderNodesList();
		for (int i = 0; i < neighbour_table.size(); i++){
			if(((Route) (neighbour_table.elementAt(i))).getDest().getByte4()==address.getByte4())
			addLinkCost(address.getIntAddress(), ((Route) (neighbour_table.elementAt(i))).getDest()
					.getIntAddress(), (int) ((Route) (neighbour_table.elementAt(i))).getCost());
		}
		// System.out.println(td.size() + link_costs.length);
		for (int i = 0; i < td_base.size(); i++) {
			NETPacket p = td_base.elementAt(i);
			Vector<Route> v = (Vector<Route>) p.getData();
			// System.out.println(v.size());
			for (int j = 0; j < v.size(); j++) {
				if(((Route) (v.elementAt(j))).getDest().getByte4()==p.getSource().getByte4())
				addLinkCost(p.getSource().getIntAddress(),
						v.elementAt(j).getDest().getIntAddress(), v.elementAt(j).getCost());
			}
		}
		/*for(int i=0; i<node_list.length;i++){
			System.out.print("Nodes : " +node_list[i] + " ");
		}*/
		//System.out.println();
		//print();
		// printf();

	}

	public void orderNodesList() {
		int c;
		for (int i = 0; i < node_list.length - 1; i++) {
			for (int j = i + 1; j < node_list.length; j++)

				if (node_list[j] < node_list[i]) {
					c = node_list[i];
					node_list[i] = node_list[j];
					node_list[j] = c;
				}
		}

	}
	public int getNodeIndex(int ip){
		int x=-1;
		for(int i=0;i<node_list.length;i++){
			if(node_list[i]==ip)
				x=i;	
		}
		return x;
	}
	
}
