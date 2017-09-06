package swarmNet.Components;

/**
 * <p>
 * Title: swarmNet
 * </p>
 * <p>
 * Description: The swarm network based on DEVS formalism
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Arizona State University
 * </p>
 * 
 * @author Ahmet Zengin
 * @version 1.0
 */

public class Route {
	private IPAddress route_dest;

	private IPAddress route_nextHop;

	private byte route_cost;

	private String outPort;

	/**
	 * constructs a route
	 * 
	 * @param dest
	 *            destination of route
	 * @param nextHop
	 *            neighbour node of the route
	 * @param outP
	 *            out port name of the node
	 * @param cost
	 *            cost value of the route
	 */
	public Route(IPAddress dest, IPAddress nextHop, String outP, byte cost) {
		route_dest = dest;
		route_nextHop = nextHop;
		route_cost = cost;
		outPort = outP;
	}

	/**
	 * Get the destination address for this route.
	 * 
	 * @return destination IPAddress of the route
	 */
	public IPAddress getDest() {
		return route_dest;
	}

	/**
	 * Get the next hop on this route.
	 * 
	 * @return next hop ip address
	 */
	public IPAddress getNextHop() {
		return route_nextHop;
	}

	/**
	 * Get the output port of this route
	 * 
	 * @return outport name
	 */
	public String getOutPortName() {
		return outPort;
	}

	/**
	 * Get the cost for this route.
	 * 
	 * @return cost of the route
	 */
	public byte getCost() {
		return route_cost;
	}

	/**
	 * sets the next hop to route
	 * 
	 * @param nextHop
	 *            next hop ip address
	 */
	public void setNextHop(IPAddress nextHop) {
		route_nextHop = nextHop;
	}

	/**
	 * sets new cost value to route
	 * 
	 * @param cost
	 *            cost value
	 */
	public void setCost(byte cost) {
		route_cost = cost;
	}

	/**
	 * Does this route lead to the target IP address?
	 * 
	 * @param target
	 *            the target IP address
	 * @return true if target equals to destination
	 */
	public boolean match(IPAddress target) {
		return target.equals(route_dest);
	}

	/**
	 * checks whether two routes equal
	 * 
	 * @param r
	 *            route to be compared
	 * @return true if they are equal
	 */
	public boolean match(Route r) {
		return (r.getDest().equals(route_dest)
				&& r.getNextHop().equals(route_nextHop) && r.getOutPortName()
				.equals(outPort)/* &&r.getCost()==route_cost */);
	}

	/**
	 * generates a console output to print the route
	 * 
	 * @return string equal of route
	 */
	public String toString() {
		return "dest:" + route_dest + "   nextHop:" + route_nextHop
				+ "   Out Port:" + outPort + "  cost:" + route_cost+"\n";
	}

}
