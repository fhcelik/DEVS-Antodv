/****************************************************************************/
/*                  Copyright 2001, Trustees of Boston University.          */
/*                               All Rights Reserved.                       */
/*                                                                          */
/* Permission to use, copy, or modify this software and its documentation   */
/* for educational and research purposes only and without fee is hereby     */
/* granted, provided that this copyright notice appear on all copies and    */
/* supporting documentation.  For any other uses of this software, in       */
/* original or modified form, including but not limited to distribution in  */
/* whole or in part, specific prior permission must be obtained from Boston */
/* University.  These programs shall not be used, rewritten, or adapted as  */
/* the basis of a commercial software or hardware product without first     */
/* obtaining appropriate licenses from Boston University.  Boston University*/
/* and the author(s) make no representations about the suitability of this  */
/* software for any purpose.  It is provided "as is" without express or     */
/* implied warranty.                                                        */
/*                                                                          */
/****************************************************************************/
/*                                                                          */
/*  Author:    Alberto Medina                                               */
/*             Anukool Lakhina                                              */
/*  Title:     BRITE: Boston university Representative Topology gEnerator   */
/*  Revision:  2.0         4/02/2001                                        */
/****************************************************************************/

package honeycomb.BRITE.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

import honeycomb.BRITE.Util.Util;


/**
   An Internet topology is represented as graph with the nodes
   representing routers (or ASs, depending on the topology level) and
   the edges representing links between them.   <p> We use an adjacency list
   representation as our Graph implementation. Nodes are represented
   by their own class, Node and similary, edges by a class, Edge.  The
   Graph implementation is independent from any semantics attached to
   a Graph, its nodes, and its edges.  As such, if you decide to use a
   different representation of a graph, you can do so by simply
   replacing this representation with your own and not affecting
   BRITE's generation (as long as you expose a similar interface).
   The idea is to allow for the ability to "plug and play" different
   graph representations depending on your need.  (Some graph
   representations might be faster than others for specific analysis
   tasks etc) <p>
   
   We now provide implementation details of this Graph representation:
   <br> This Graph representation has three HashMaps:
   <code>Nodes</code>, <code>Edges</code> and
   <code>adjList</code>. The <code>Nodes</code> HashMap contains as
   keys the node-ids and as values, the <code>Node</code> objects
   themselves. Similarly, the <code>Edges</code> HashMap contains
   Edge-IDs as keys and <code>Edge</code> objects as values.  The
   <code>adjList</code> HashMap contains as keys node-ids of source
   nodes and destination node-ids as values.  (How NodeIDs and EdgeIDs
   are computed can be found in the documentation for the Node and
   Edge class respectively.)
   
*/
public class Graph /*TODO: implements graphAlgs*/ {
   
  
    protected  int numNodes;
    protected  int numEdges;
    
    protected   HashMap Nodes;  /*this is our repository of nodes & edges*/
    protected   HashMap Edges;
  

    protected   HashMap adjList;   /*this is adjaceny list representation of the 
				     graph with nodeIDs */
    
   
  protected Node[] nodesArray;
  protected Edge[] edgesArray;
  protected boolean nodesArrayCached = false;
  protected boolean edgesArrayCached = false;
  
  /**
       Create a graph with default initial capacity. The graph can grow beyond
       the initial capacity as you add more nodes.
    */
    public Graph() {
	Nodes = new HashMap();
	Edges = new HashMap();
	adjList = new HashMap();
	//shift =  Node.getNodeCount()+1;
    }
    
    /**
       Create a graph with specified intial capacity.  The graph can
       growth beyond this but its initial size is set.  If you know the
       approximate size of the graph, use this constructor as it helps
       performance.
       @param numNodes The initial number of nodes in the graph
    */
    public Graph(int numNodes) { 
	Nodes = new HashMap(numNodes); 
	Edges = new HashMap(2*numNodes);
	adjList = new HashMap(numNodes); 
    }    
  

  
    
    /**Given a vector of graphs, produce a single graph with
       disconnected components.  Useful for flattening/combining
       different graphs.  This may be a memory-expensive operation since
       you are creating a copy of all the nodes and edges of all the
       graphs.  */
    public Graph(ArrayList graphs) {
	
	//assumption: all graphs in vector are same size, but if not,
	//thats ok because datastructure automatically  increases
	int N = graphs.size() * (((Graph)graphs.get(0)).getNumNodes());;
	Nodes = new HashMap(N);
	Edges = new HashMap(N);
	adjList = new HashMap(N);
	int size = graphs.size();
	
	for (int i=0; i<size; ++i) {
	    Graph g = (Graph) graphs.get(i);
	    Nodes.putAll(g.getNodes());      
	    Edges.putAll(g.getEdges());
	    //System.out.println("Dumping Edges of graph: " + i+ "\n===========\n");
	    System.out.println(Edges.toString());
	    //System.out.println("\n\n");
	    adjList.putAll(g.getAdjList());  
	} 
	//System.out.println(Edges);
	numNodes = Nodes.size();
	numEdges = Edges.size();
	
    }
    
    /**
       The addEdge method checks if the Edge is directed or
       undirected.  If it is, it calls addDirectedEdge.  Otherwise, it
       calls addUndirectedEdge.
       @param e The edge to be added */
    public void addEdge(Edge e) {
	
	edgesArrayCached=false;
	nodesArrayCached=false;

	++numEdges;
	
	if (e.getDirection() == GraphConstants.DIRECTED) 
	    addDirectedEdge(e);
	else addUndirectedEdge(e);
		
    }
    
    
    /**
       The addUndirectedEdge method adds the given edge's source node-id and
       destination node-id to the adjList twice: once with the source
       node-id as the key and next with the desitnation node-id as the
       key.  We only increment the numEdges count once however.  Both
       the indegree and outdegree of both nodes is incremented.
       Finally, the edge itself is added to the Edges HashMap.

       @param e The edge to be added to our graph */
    private void addUndirectedEdge(Edge e) {
	
	/*since this is an undirected graph, add edge from src to dest
	  and another from dest to src*/
	Node src = e.getSrc();
	Node dst = e.getDst();

	if (src==null) {
	  //dumpToOutput();
	  Util.ERR("src is null! in addEdge() of UndirectedGraph ");
	 
	}
	if (dst==null) {
	  //dumpToOutput();
	  Util.ERR("dst is null! in addEdge() of UndirectedGraph");
	}

	int intID = Edge.computeID(src.getID(), dst.getID());
	Edge a;
	if (intID==-1) {
	    long longID = Edge.computeLongID(src.getID(), dst.getID());
	    a = (Edge) Edges.put(new Long(longID), e);

	}
	else { 
	  a=(Edge)Edges.put(new Integer(intID), e);

	}
	
	/*increment in/out degree of both nodes*/
	src.incrementInDegree();
	src.incrementOutDegree();
	dst.incrementInDegree();
	dst.incrementOutDegree();

	/*add to adj list*/
	Integer srcID = new Integer(src.getID());
	Integer dstID = new Integer(dst.getID());
	
	if (!adjList.containsKey(srcID)){
	    Nodes.put(srcID, src); //new node
	    ++numNodes;

	    HashSet vect = new HashSet();
	    vect.add(dstID);
	    adjList.put(srcID, vect);
	}
	else {
	    HashSet vect = (HashSet) adjList.get(srcID);
	    vect.add(dstID);
	    adjList.put(srcID, vect);
	}
	
	/*now add reverse direction edge*/
	if (!adjList.containsKey(dstID)) {
	    Nodes.put(dstID, dst); //new node
	    ++numNodes; 

	    HashSet vect = new HashSet();
	    vect.add(srcID);
	    adjList.put(dstID, vect);
	    
	}  
	else {
	    HashSet vect = (HashSet) adjList.get(dstID);
	    vect.add(srcID);
	    adjList.put(dstID, vect);
	}
	
	
    }
    


    /**
       The addDirectedEdge method of a Graph takes the source node of
       the edge to be added and adds it as a key in the adjList.  The
       destination node-id is added as one of the values of this
       source node-id.  The indegree of the destination node and the
       outdegree of the source node is incremented here.  Next, the
       edge itself is added to the Edges hashmap. Finally, the number
       of edges counter is incremented.

       @param e the Edge to be added to our graph 
    */
    private void addDirectedEdge(Edge e) {
      Node src = e.getSrc();
	Node dst = e.getDst();
	
	if (src==null) 
	    Util.ERR("src is null! in addEdge() DirectedGraph");
	if (dst==null)
	    Util.ERR("dst is null! in addEdge() DirectedGraph");
	Edge a;
	int intID = Edge.computeDirectedID(src.getID(), dst.getID());
	if (intID==-1) {
	    long longID = Edge.computeDirectedLongID(src.getID(), dst.getID());
	    a = (Edge) Edges.put(new Long(longID), e);
	}
	else a=(Edge) Edges.put(new Integer(intID), e);
	
	if (a!=null)
	  removeEdge(a); // --numEdges;  //this was a repeat edge and therefore is thrown out.
	
	/*increment inDegree of dst and outdegree of src*/ 
	dst.incrementInDegree();
	src.incrementOutDegree();
	
	/*add to adj list*/
	Integer srcID = new Integer(src.getID());
	Integer dstID = new Integer(dst.getID());
	if (!adjList.containsKey(srcID)){
	    Nodes.put(srcID, src); //new node
	    ++numNodes;
	    HashSet vect = new HashSet();
	    vect.add(dstID);
	    adjList.put(srcID, vect);
	}
	else {
	    HashSet vect = (HashSet) adjList.get(srcID);
	    vect.add(dstID);
	    adjList.put(srcID, vect);
	}
	
    }
  
  public void removeEdge(Node src, Node dst) {
    edgesArrayCached=false;
    nodesArrayCached=false;
    
    /*remove edge from Edges Hashmap*/
    Edge e;
    int srcID = src.getID();
    int dstID = dst.getID();
    int intID = Edge.computeID(srcID, dstID);
    if (intID==-1) {
      long longID = Edge.computeLongID(srcID, dstID);
      e = (Edge) Edges.get(new Long(longID));

    }
    else e = (Edge) Edges.get(new Integer(intID));

    removeEdge(e);
    
  }
  
  /**
     Removes Edge e from this graph.  Decrements the in/out degrees of
     the q source and destination nodes and updates the datastructure
     and the <pre>numEdges</pre> variable of this class.

     @param e the Edge to be removed from our graph 
  */
  public void removeEdge(Edge e) {
    
    edgesArrayCached=false;
    nodesArrayCached=false;
  
    Node src = e.getSrc();
    Node dst = e.getDst();
    
    Integer srcID = new Integer(src.getID());
    Integer dstID = new Integer(dst.getID());
    

    /*decrement src and dst degrees*/
    src.setOutDegree(src.getOutDegree()-1);
    dst.setInDegree(dst.getInDegree()-1);
    /*remove dst and src from adjList representation*/
    if (adjList.containsKey(srcID)) {
     HashSet a = (HashSet) adjList.get(srcID);
      a.remove(dstID);
      //System.out.print("removing one way...");
      adjList.put(srcID, a);
    }
    /*if undirected, remove the other way too*/
    if (e.getDirection() == GraphConstants.UNDIRECTED) {
      
      src.setInDegree(src.getInDegree()-1);
      dst.setOutDegree(dst.getOutDegree()-1);
      if (adjList.containsKey(dstID)) {
	HashSet a = (HashSet) adjList.get(dstID);
	a.remove(srcID);
	adjList.put(dstID, a);
	//System.out.println("... and other way!");
      }
    }
    /*remove edge from Edges Hashmap*/
    int intID = Edge.computeID(srcID.intValue(), dstID.intValue());
    if (intID==-1) {
      long longID = Edge.computeLongID(srcID.intValue(), dstID.intValue());
      Edges.remove(new Long(longID));
    }
    else Edges.remove(new Integer(intID));
    
    --numEdges;
    
  }
  
    
    /** given source and destination nodes, returns true if an edge
	exists between those nodes
	@param src  the srouce node
	@param dst  the destination node
    */
    public boolean hasEdge(Node src, Node dst) {
    
      return (hasEdge(src.getID(), dst.getID()));
    }
    
  /** given IDs of source and destination nodes, returns true if
      an edge between the corresponding nodes exists.
      @param srcID  the node id of the source node
      @param dstID  the node if of the destination node
  */
  public boolean hasEdge(int srcID, int dstID) {
      int intID = Edge.computeID(srcID, dstID);
    if (intID==-1) {
	long longID = Edge.computeLongID(srcID, dstID);
	if (Edges.containsKey(new Long(longID)))
	    return true;
    }
    else {
	if (Edges.containsKey(new Integer(intID)))
	return true;
    }
    return false;
  }
    
    public Edge getEdge(Node src, Node dst) {
	int srcID =src.getID();
	int dstID = dst.getID();
	int intID = Edge.computeID(srcID, dstID);
	if (intID==-1) {
	    long longID = Edge.computeLongID(srcID, dstID);
	    return (Edge) Edges.get(new Long(longID));
	}
	return (Edge)Edges.get(new Integer(intID));
    }
    
  
    /** add a node to our adjacency list. 
	@param n the node to be added
    */
    public void addNode(Node n) {
      nodesArrayCached=false;
      Integer nID = new Integer(n.getID());
      Nodes.put(nID, n);
      if (!adjList.containsKey(nID)){
	adjList.put(nID, new HashSet());
	++numNodes;
      }
    }

  /** returns true if the graph contains a node with specified node-id
      @param id  the Integer and int representation of the node
  */
  public boolean hasNode(Integer id) {
      return Nodes.containsKey(id); }
  public boolean hasNode(int nID) {
    return Nodes.containsKey(new Integer(nID)); }
    
  /** 
	removes node and all edges incident to it.  uses getIncidentEdges() to determine
	incident edges. WARNING: may leave graph disconnected.  make sure to extract max connected
	component of graph after this.
	@param n the node to remove
    */
  public void removeNode(Node n) {
    nodesArrayCached=false;
    edgesArrayCached=false;
    
    //remove node, remove incident edges, remove resulting disconnected nodes
    Edge[] edges =  getIncidentEdges(n);
    for (int i=0; i<edges.length; ++i) {
      removeEdge(edges[i]);
    }
    Integer nid = new Integer(n.getID());
    if (adjList.containsKey(nid))
      adjList.remove(nid);
    Nodes.remove(nid);
    --numNodes;
      
  }
    
    /**
       a useful debug routine.  dumps the graph (nodes and edges) in NLANR ASConnlist-list format. 
       to the standard output stream
       The output format looks like:
       <pre>
       from ->  to1, to2, to3
       from2 -> to7, to8, to9
       ...
       NumEdges = <numEdges>
       NumNodes = <numNodes>
       </pre>
    */
    public void dumpToOutput() {  
	Iterator kI = adjList.keySet().iterator();
	
	while (kI.hasNext()) {
	    Integer n = (Integer) kI.next();
	    HashSet v = (HashSet)adjList.get(n);
	    if (v.size()==0) 
		continue;
	    System.out.print(n+" -> ");
	    int size = v.size();
	    Iterator iter = v.iterator();
	    while (iter.hasNext()) {
	      Integer ni = (Integer) iter.next();
	      System.out.print(ni+", ");
	    }
	    System.out.println();
	}
	System.out.println("NumEdges = " + numEdges);
	System.out.println("NumNodes = "+numNodes);
    }
    
   
    /**
       Given a node, returns all edges that are incident to this
       node (incoming and outgoing).
       @param src  The source node to examine
       @return Edge[]  Array of incoming & outgoing nodes
    */
    public Edge[] getIncidentEdges(Node src) {
	//get neighbors of src
	Node[] neighbors = getNeighborsOf(src);
	Edge[] v = new Edge[neighbors.length];

	for (int i=0; i<neighbors.length; ++i) {
	    Node dst = neighbors[i];
	    int edgeID = Edge.computeID(src.getID(), dst.getID());
	    if (edgeID!=-1) {
		Edge e = (Edge) Edges.get(new Integer(edgeID));
		v[i]=e;
		if (e==null) { Util.ERR("In Graph.getIncidentEdges(): AdjList has null e");  System.exit(0); }
	    }
	    else {
		long longID = Edge.computeLongID(src.getID(), dst.getID());
		Edge e2 = (Edge) Edges.get(new Long(longID));
		v[i]=e2;
		if (e2==null) { Util.ERR("In Graph.getIncidentEdges(): AdjList has null e");  System.exit(0); }
	    }
	}
	return v;
    }
   

    /** Returns an ArrayList representation (copy) of the nodes in this
	graph.  If you want to iterate through nodes, use
	getNodesArray() instead since that would be faster to iterate
	through.
    */
    public ArrayList getNodesVector() { 
      return new ArrayList(Nodes.values()); }
    
    /**     Returns an Array representation (copy of references) of the ndoes in this graph*/
    public Node[] getNodesArray() { 
	if (!nodesArrayCached)
	nodesArray= (Node[]) Nodes.values().toArray(new Node[numNodes]); 
      nodesArrayCached=true;
      return nodesArray;
    }
    
    /** Returns an ArrayList representation (copy of references) of all the edges in this graph*/
    public ArrayList getEdgesVector() { return new ArrayList(Edges.values()); }
  
    /** returns an Array representation (copy of references) of all the edges in this graph*/
    public Edge[] getEdgesArray() { 
	if (!edgesArrayCached)
	    edgesArray=  (Edge[]) Edges.values().toArray(new Edge[Edges.size()]); 
	edgesArrayCached=true;
	return edgesArray;
    }
   
    /** get number of nodes in this graph*/
    public int getNumNodes() { return numNodes; }
    
    /** get number of edges in this graph*/
    public int getNumEdges() { return numEdges; }
    
    /** returns a HashMap representation of the Nodes in the graph. The
	keys are the Node-IDs and the values are the Node object
	references themselves.  WARNING: This is not a copy of the
	graph, so changes here will affect the graph structure!
    */
    public HashMap getNodes() { return Nodes; }
   
    /** returns a HashMap representation of the Edges in the graph. The
	keys are the Edge-IDs (as computed by the Edge.ComputeID() method)
	and the values are the Node object references themselves.
	WARNING: This is not a copy of the graph, so changes here will
	affect the graph structure!
    */
    public HashMap getEdges() { return Edges; }
    
    /** returns a HashMap representation of the adjacency list. The
	keys are node-ids of the source nodes and the values are an
	Arraylist of node-ids of destination nodes.
	WARNING: This is not a copy of the graph, so changes here will
	affect the graph structure!  */
    public HashMap getAdjList() { return adjList; }
    
    /** Given Node n, returns number of neighbors of N. 
	@param AtomicNode n
	@return int number of neighbors
     */
    public int getNumNeighborsOf(Node n) {
	Integer nID = new Integer(n.getID());
	return ( (HashSet)adjList.get(nID)).size();
    }
    
    /** Given node n, returns an array of Node objects that are n's neighbors.
	@param AtomicNode n
	@return Node[] Array of nodes
    */
    public Node[] getNeighborsOf(Node n) {
	Integer nID = new Integer(n.getID());
	HashSet neighborID = (HashSet) adjList.get(nID);
	int size = neighborID.size();
	Node[] neighbors = new Node[size];
	
	Iterator iter = neighborID.iterator();
	int i=0;
	while (iter.hasNext()) {
	  int id = ((Integer)iter.next()).intValue();
	  neighbors[i]=(Node)getNodeFromID(id);
	  ++i;
	}
	return neighbors;
    }

    /** a lookup function to get a node from its id.  assumes id is valid.  returns null if invalid id
     */
    public Node getNodeFromID(int id) {
		return (Node) Nodes.get(new Integer(id));
    }

    /** returns the kth node in the ordering of the nodes.  useful when you want a random node from the graph.
	If k > numNodes, returns null	
     */
    public Node getKthNode(int k) {
	if (k<numNodes)
	    return ((Node[])getNodesArray())[k];
	else return null;
	/*
	Node n= (Node) Nodes.get(new Integer(k+shift));
	return n;
	*/
    }
    
    /** returns the node with smallest out degree*/
    public Node getSmallestDegreeNode() { 
	Node[] v = this.getNodesArray();
	int maxDeg = v[0].getOutDegree();
	int ret = v[0].getID(); 
       
	for (int i=1; i<v.length; ++i) {
	    Node n = v[i];
	    int nDeg = n.getOutDegree();
	    if (nDeg < maxDeg) {
		maxDeg= nDeg;
		ret = n.getID();
	    }
	}
	return  getNodeFromID(ret);
    }

    /** returns the leaf nodes of a graph.  That is, the nodes with smallest degree*/
    public Node[] getLeafNodes() {
	ArrayList leafNodes = new ArrayList();
	Node[] v = this.getNodesArray();
	Node n = this.getSmallestDegreeNode();
	leafNodes.add(n);
	int nDeg = n.getOutDegree(); 
	for (int i=0; i<v.length; ++i) {
	    Node u = v[i];
	    if (u.getOutDegree() == nDeg && u!=n) {
		leafNodes.add(u);
	    }
	}
	int size = leafNodes.size();
	return (Node[])leafNodes.toArray(new Node[size]);
    }

    /** returns the node with smallest degree that is more than or equal to k*/
    public Node getSmallestDegreeNodeThreshold(int k) {
	/*get smallest deg node more than k*/
	Node[] v = this.getNodesArray();
	int maxDeg = v[0].getOutDegree();
	int ret =v[0].getID();
	for (int i=1; i<v.length; ++i) {
	    Node n =  v[i];
	    int nDeg = n.getOutDegree();
	    if (nDeg < maxDeg && nDeg>=k) {
		maxDeg= nDeg;
		ret = n.getID();
	    }
	}
	return  getNodeFromID(ret);
    }

    
    /** does a depth first traversal of the graph, marking all visited
	nodes black.  return total number of visited nodes.  accepts node to
	start on (by default nodesV[0]).    initializes all nodes to white before starting so you
	don't need to do the initialization*/
    public int dfs(Node startNode) {
	/*make sure all nodes are colored white before starting*/
	Node[] nodesV = getNodesArray();
	for (int i=0; i<nodesV.length; ++i) {
	    Node n =  nodesV[i];
	    n.setColor(GraphConstants.COLOR_WHITE);
	}
	int totalVisited=0;
	Stack dfsStack = new Stack();
	Node v;
	if (startNode==null)  v = nodesV[0];
	else   v = startNode;
	
	v.setColor(GraphConstants.COLOR_BLACK);
	dfsStack.push(v);
	while (!dfsStack.isEmpty()) {
	  Node top =(Node) dfsStack.peek();
	  int visited=0;
	  Node[] neighbors = getNeighborsOf(top);
	  for (int i=0; i<neighbors.length; ++i) {
	    Node ni = neighbors[i];
	    if (ni.getColor()==GraphConstants.COLOR_WHITE) {
	      ni.setColor(GraphConstants.COLOR_BLACK);
	      dfsStack.push(ni);
	      ++visited;
	    }
	  }
	  if (visited==0 || neighbors.length==0)
	    dfsStack.pop();
	}
	for (int i=0; i<nodesV.length; ++i) {
	    Node n = nodesV[i];
	    if (n.getColor()==GraphConstants.COLOR_BLACK)
		++totalVisited;
	}
	return totalVisited;
    }

    /** returns true if the graph is connected, false if it has disconnected components.  uses dfs()*/
    public boolean isConnected() {
	/*do dfs and mark all nodes reachable*/
	int totalMarked = dfs(null);	

	if (totalMarked<numNodes) return false; 
	else return true;
	
	/*Node[] nodesV = getNodesArray();
	  boolean isConnected=true;
	  for (int i=0; i<nodesV.length; ++i) {
	  Node n =  nodesV[i];
	  if (n.getColor() != GraphConstants.COLOR_BLACK)
	  isConnected=false;
	  
	  }
	  return isConnected;
	*/
    }


    /** XXX hack.  Greedy Algorithm to extract largest connected component in a disconnected graph */
    public Graph largestConnectedComponent() {
	Node[] nodesV = getNodesArray();
	int sizeOfCC=0;
	int numReached = dfs(nodesV[0]);
	HashSet reached = new HashSet(numReached); //these are all the nodes we reach starting from start node (nodesV[0])
	for (int i=0; i<nodesV.length; ++i) {
	    Node n = nodesV[i];
	    if (n.getColor() == GraphConstants.COLOR_BLACK)
		reached.add(n);
	}

	//if the size of our cc is smaller than half the total number of nodes, then there is a possiblity
	//that there is another cc which is larger and we must find it.
	//otherwise, since g is disconnected, this is the largest cc!
	if ((numReached*2)<nodesV.length) {
	    for (int i=0; i<nodesV.length; ++i) {
		Node n = nodesV[i];
		if (reached.contains(n)) continue;
		
		//otherwise this node was not reached in our previous dfs traversal
		
		int count = dfs(n);
		if (count>reached.size()) {  
		    //traversing from this node gives a bigger connected component, so should use this instead.
		    reached.clear(); 
		    for (int j=0; j<nodesV.length; ++j) {
			if (nodesV[j].getColor() == GraphConstants.COLOR_BLACK)
			    reached.add(nodesV[j]);
		    }
		    if ((reached.size()*2)>=(nodesV.length)) break;
		}

	    }
	}
	
	Graph lcc = new Graph(sizeOfCC);
	Edge[] edges = getEdgesArray();
	for (int k=0; k<edges.length; ++k) {
	    Edge e = edges[k];
	    Node eSrc = e.getSrc();
	    Node eDst = e.getDst();
	    if (reached.contains(eSrc) && reached.contains(eDst)) {
		lcc.addNode(eSrc);
		lcc.addNode(eDst);
		lcc.addEdge(e);
	    }
	}
	
	Util.MSG("LCC Created.   NumNodes = "+ lcc.getNumNodes() +" | NumEdges = " + lcc.getNumEdges());
	return lcc;

	
    }

    /** helper function to mark all nodes in the graph a specific color. (for color, see GraphConstants)*/
  public void markAllNodes(int color) {
        Node[] nodesV = getNodesArray();
	for (int i=0; i<nodesV.length; ++i) {
	    Node n =  nodesV[i];
	    n.setColor(color);
	}
	
    }
    
    
}














