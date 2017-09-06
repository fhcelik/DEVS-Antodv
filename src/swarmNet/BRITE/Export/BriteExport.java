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
/*  Author:     Alberto Medina                                              */
/*              Anukool Lakhina                                             */
/*  Title:     BRITE: Boston university Representative Topology gEnerator   */
/*  Revision:  2.0         4/02/2001                                        */
/****************************************************************************/

package swarmNet.BRITE.Export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import swarmNet.BRITE.Graph.ASEdgeConf;
import swarmNet.BRITE.Graph.ASNodeConf;
import swarmNet.BRITE.Graph.Edge;
import swarmNet.BRITE.Graph.Graph;
import swarmNet.BRITE.Graph.GraphConstants;
import swarmNet.BRITE.Graph.Node;
import swarmNet.BRITE.Graph.NodeConf;
import swarmNet.BRITE.Graph.RouterEdgeConf;
import swarmNet.BRITE.Graph.RouterNodeConf;
import swarmNet.BRITE.Model.ModelConstants;
import swarmNet.BRITE.Topology.Topology;
import swarmNet.BRITE.Util.Util;

/**
   Export.BriteExport provides functionality to export a topology into
   a BRITE format file.  The BRITE format looks like:
   <br>
   
   <pre>
   Topology: ( [numNodes] Nodes, [numEdges] Edges )
   Model ( [ModelNum] ):  [Model.toString()]
   
   Nodes: ([numNodes]):
   [NodeID]  [x-coord]  [y-coord]  [inDegree] [outDegree] [ASid]  [type]
   [NodeID]  [x-coord]  [y-coord]  [inDegree] [outDegree] [ASid]  [type]
   [NodeID]  [x-coord]  [y-coord]  [inDegree] [outDegree] [ASid]  [type]
   ...

   Edges: ([numEdges]):
   [EdgeID]  [fromNodeID]  [toNodeID]  [Length]  [Delay]  [Bandwidth]  [ASFromNodeID]  [ASToNodeID]  [EdgeType]  [Direction]
   [EdgeID]  [fromNodeID]  [toNodeID]  [Length]  [Delay]  [Bandwidth]  [ASFromNodeID]  [ASToNodeID]  [EdgeType]  [Direction]
   [EdgeID]  [fromNodeID]  [toNodeID]  [Length]  [Delay]  [Bandwidth]  [ASFromNodeID]  [ASToNodeID]  [EdgeType]  [Direction]
   ...
   </pre>
   <br>
   Please see the BRITE User Manual (at http://www.cs.bu.edu/brite/docs.htm) for more information.
 */
public class BriteExport {
  
  private Topology t;
  private BufferedWriter bw;
  private Graph g;
  private String modelStr;
  private Node[] nodes;
  private Node n;
  private int x;
  private int y;
  private int specificNodeType;
  private int ASid;
  private int outdegree;
	private int indegree; 
	private int nodeID; 
    /**
       Class Constructor: Returns a BriteExport object which your code
       my keep around.  Does not actually write the topology to the
       file.  You must explicitly call the <code>export()</code> method of this
       object in order to write to the file.
       
       @param t the topology object to export
       @param outFile the destination file to write the topology to.
    */
    public BriteExport(Topology top, File outFile) {
	t = top;
	x=0;
	y=0;
	specificNodeType=-1;
	ASid=-1;
	outdegree=-1;
	indegree=-1;
	nodeID=-1;
      
	try {
	    bw = new BufferedWriter(new FileWriter(outFile));
	}
	catch (IOException e) {
	    Util.ERR(" Error creating BufferedWriter in BriteExport: " + e);
	}
	g = t.getGraph();
	modelStr = t.getModel().toString();
    }

    
    /**
       Writes the contents of the topolgy in the BRITE format to the
       destination file specified in the constructor.  
    */
    public void export() {
	Util.MSG("Exporting to BRITE...");
     	try {
	    bw.write("Topology: ( " + g.getNumNodes() + " Nodes, " + g.getNumEdges()+ " Edges )");
	    bw.newLine();
	    bw.write(modelStr);
	    bw.newLine();
	    bw.write("Nodes: ( "+g.getNumNodes()+" )");
	    bw.newLine();
	    
	    /*output nodes*/
	    // ArrayList nodes = g.getNodesVector();
	    
	    nodes = g.getNodesArray();
	    Arrays.sort(nodes, Node.IDcomparator);
	    for (int i=0; i< nodes.length; ++i) {
		n =  nodes[i];
		x = (int) ((NodeConf) n.getNodeConf()).getX();
		y = (int)  ((NodeConf)n.getNodeConf()).getY();
		outdegree = n.getOutDegree();
		indegree = n.getInDegree();
		nodeID = n.getID();
		
		if (n.getNodeConf() instanceof RouterNodeConf) { 
		    ASid = ((RouterNodeConf)n.getNodeConf()).getCorrAS();
		    specificNodeType = ((RouterNodeConf)n.getNodeConf()).getType();
		}
		if (n.getNodeConf() instanceof ASNodeConf) {
		    specificNodeType = ((ASNodeConf)n.getNodeConf()).getType();
		    ASid = nodeID;
		}
		
		bw.write(nodeID + "\t" + x + "\t" +y+ "\t" + indegree+ "\t" +outdegree+"\t"+ASid);
	
		if (n.getNodeConf() instanceof RouterNodeConf) {
		    if (specificNodeType == ModelConstants.RT_LEAF)
			bw.write("\tRT_LEAF");
		    else if (specificNodeType == ModelConstants.RT_BORDER) 
			bw.write("\tRT_BORDER");
		    else if (specificNodeType == ModelConstants.RT_STUB)
			bw.write("\tRT_STUB");
		    else if (specificNodeType == ModelConstants.RT_BACKBONE) 
			bw.write("\tRT_BACKBONE");
		    else 
			bw.write("\tRT_NODE");
		}
		else if (n.getNodeConf() instanceof ASNodeConf) {
		    if (specificNodeType == ModelConstants.AS_LEAF)
			bw.write("\tAS_LEAF");
		    else if (specificNodeType == ModelConstants.AS_BORDER) 
			bw.write("\tAS_BORDER");
		    else if (specificNodeType == ModelConstants.AS_STUB)
			bw.write("\tAS_STUB");
		    else if (specificNodeType == ModelConstants.AS_BACKBONE) 
			bw.write("\tAS_BACKBONE");
		    else 
			bw.write("\tAS_NODE");
		}
		

		bw.newLine();
	    }
	    bw.newLine();
	    bw.newLine();
	    /*output edges*/
	    
	    Edge[] edges = g.getEdgesArray();
	    //ArrayList edges = g.getEdgesVector();
	    bw.write("Edges: ( "+edges.length+" )");
	    bw.newLine();
	    
	    Arrays.sort(edges, Edge.IDcomparator);
	    for (int i=0; i<edges.length; ++i) {
		Edge e = (Edge) edges[i];
		Node src = e.getSrc();
		Node dst = e.getDst();
		double dist = e.getEuclideanDist();
		double delay = e.getDelay();
		int asFrom= src.getID();
		int asTo = dst.getID();
		if (src.getNodeConf() instanceof RouterNodeConf)
		    asFrom  =((RouterNodeConf) src.getNodeConf()).getCorrAS();
		if (dst.getNodeConf() instanceof RouterNodeConf)
		    asTo  =((RouterNodeConf) dst.getNodeConf()).getCorrAS();
		
		bw.write(e.getID() + "\t" + src.getID() + "\t" + dst.getID());
		bw.write("\t"+ dist + "\t" +delay+ "\t" + e.getBW());
		bw.write("\t"+ asFrom + "\t" + asTo);
		
		
		if (e.getEdgeConf() instanceof ASEdgeConf) {
		    int specificEdgeType = ((ASEdgeConf)e.getEdgeConf()).getType();
		    if (specificEdgeType == ModelConstants.E_AS_STUB)
			bw.write("\tE_AS_STUB");
		    else if (specificEdgeType == ModelConstants.E_AS_BORDER)
			bw.write("\tE_AS_BORDER");
		    else if (specificEdgeType == ModelConstants.E_AS_BACKBONE)/*backbone*/
			bw.write("\tE_AS_BACKBONE_LINK");
		    else 
		      bw.write("\tE_AS");
		}
		else  /*we have a router*/{
		  int specificEdgeType = ((RouterEdgeConf)e.getEdgeConf()).getType();
		  if (specificEdgeType == ModelConstants.E_RT_STUB)
			bw.write("\tE_RT_STUB");
		    else if (specificEdgeType == ModelConstants.E_RT_BORDER)
			bw.write("\tE_RT_BORDER");
		  else if (specificEdgeType == ModelConstants.E_RT_BACKBONE)/*backbone*/
			bw.write("\tE_RT_BACKBONE");
		    else 
			bw.write("\tE_RT");
		}
	       
		if (e.getDirection() == GraphConstants.DIRECTED) 
		    bw.write("\tD");
		else bw.write("\tU");
		
		bw.newLine();
		
	    }
	    bw.close();
	}
	catch (Exception e) {
	  System.out.println("[BRITE ERROR]: Error exporting to file. " + e);
	  e.printStackTrace();
	    System.exit(0);
	    
	}
	Util.MSG("... DONE.");
    }
    
}

  
    








