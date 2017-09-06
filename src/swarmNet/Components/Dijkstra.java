package swarmNet.Components;

import java.util.ArrayList;

public class Dijkstra {
	  
	    // Dijkstra's algorithm to find shortest path from s to all other nodes
	   public static int [] dijkstra (RoutingTable rt, int address) {
	         final int [] dist = new int [rt.size()];  // shortest known distance from "s"
	         final int [] pred = new int [rt.size()];  // preceeding node in path
	         final boolean [] visited = new boolean [rt.size()]; // all false initially
	   
	         for (int i=0; i<dist.length; i++) {
	           dist[i] = Integer.MAX_VALUE;
	        }

	        dist[rt.getNodeIndex(address)] = 0;
	  
	        for (int i=0; i<dist.length; i++) {
	           final int next = minVertex (dist, visited);
	           visited[next] = true;
	          // System.out.println("next :" + next);
	           // The shortest path to next is dist[next] and via pred[next].
	  
	           final ArrayList<Integer> n = rt.neighbors (rt.getNode(next));
	          // System.out.println(address +"'s neigbors" + n.size());
	           for (int j=0; j<n.size(); j++) {
	              final int v = n.get(j);
	              final int d = dist[next] + rt.getCost(next,v);
	             // System.out.print("selected node"+v + " and its index ");
	             // System.out.println(rt.getNodeIndex(v));
	              if (dist[rt.getNodeIndex(v)] > d) {
	                 dist[rt.getNodeIndex(v)] = d;
	                 pred[rt.getNodeIndex(v)] = next;
	              }
	           }
	        }
	        return pred;  // (ignore pred[s]==0!)
	     }
	  
	     private static int minVertex (int [] dist, boolean [] v) {
	        int x = Integer.MAX_VALUE;
	       int y = -1;   // graph not connected, or no unvisited vertices
	       for (int i=0; i<dist.length; i++) {
	          if (!v[i] && dist[i]<x) 
	          		{y=i; x=dist[i];}
	        }
	        return y;
	     }
	  
	     public static ArrayList<Integer> getRoute (RoutingTable G, int [] pred, int s, int e) {
	        final ArrayList<Integer> path = new ArrayList<Integer>();
	        /*int x = G.getNodeIndex(e);
	        while (x!=G.getNodeIndex(s)) {
	           path.add (0, G.getNode(x));
	           x = pred[G.getNodeIndex(x)];
	        }*/
	        int x = e;
	        while (x!=s) {
	           path.add (0, x);
	           x = G.getNode(pred[G.getNodeIndex(x)]);
	        }
	        path.add (0, s);
	        return path;
	     }
	 
	 }
