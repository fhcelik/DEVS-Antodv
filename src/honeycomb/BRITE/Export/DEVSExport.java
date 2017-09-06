package honeycomb.BRITE.Export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import honeycomb.BRITE.Graph.Edge;
import honeycomb.BRITE.Graph.Graph;
import honeycomb.BRITE.Graph.Node;
import honeycomb.BRITE.Model.FileModel;
import honeycomb.BRITE.Model.ModelConstants;
import honeycomb.BRITE.Topology.Topology;
import honeycomb.BRITE.Util.Util;

public class DEVSExport {

	private Topology t;
	private BufferedWriter bw;
	private String fileName;

	public DEVSExport(Topology t, File outFile) {
		this.t = t;
		fileName=(""+outFile);
		fileName=fileName.substring(13, fileName.length()-5);
		try {
			bw = new BufferedWriter(new FileWriter(outFile));
		} catch (IOException e) {
			Util.ERR("Error creating BufferedWriter in DEVSExport: " + e);
		}
	}

	public void export() throws Exception {
		Util.MSG("Producing export file for DEVS-Suite ");
		Graph g = t.getGraph();

		Node[] nodes = g.getNodesArray();

		HashMap id2id = new HashMap(nodes.length);
		for (int i = 0; i < nodes.length; ++i) {
			id2id.put(new Integer(nodes[i].getID()), new Integer(i));
		}

		Arrays.sort(nodes, Node.IDcomparator);
		Edge[] edges = g.getEdgesArray();
		Arrays.sort(edges, Edge.SrcIDComparator);
			
		bw.write("// Export from BRITE topology");
		bw.newLine();
		bw.write("// Generator Model Used: " + t.getModel().toString());
		bw.newLine();
		bw.newLine();
		bw.newLine();
		bw.newLine();
		bw.write("package honeycomb;");
		bw.newLine();
		bw.write("import java.awt.Dimension;");bw.newLine();
		bw.write("import java.awt.Point;");bw.newLine();
		bw.write("import honeycomb.Components.IPAddress;");bw.newLine();
		bw.write("import honeycomb.Framework.Node;");bw.newLine();
		bw.write("import view.modeling.ViewableAtomic;");bw.newLine();
		bw.write("import view.modeling.ViewableComponent;");bw.newLine();
		bw.write("import view.modeling.ViewableDigraph;");bw.newLine();
		bw.write("public class "+fileName+" extends ViewableDigraph {");bw.newLine();
		bw.write("public "+fileName+"(){");bw.newLine();
		bw.write("this(\"Generated Network\", 1, 1);");bw.newLine();
		bw.write("}");bw.newLine();
		bw.write("public "+fileName+"(String name, double processing_time, double propagation_delay){");bw.newLine();
		bw.write("super(name);");bw.newLine();
		bw.write("make(processing_time, propagation_delay);");bw.newLine();
		bw.write("}");bw.newLine();

		bw.write("protected void make(double processing_time, double propagation_delay) {");
		bw.newLine();
		bw.write("addInport(\"in\");");
		bw.newLine();
		bw.write("addInport(\"inEvent\");");
		bw.newLine();
		bw.write("addInport(\"out\");");
		bw.newLine();
		bw.write("addInport(\"outEvent\");");
		bw.newLine();
		bw.newLine();
		bw.write("ViewableAtomic src;");bw.newLine();
		bw.write("ViewableAtomic dst;");bw.newLine();
		bw.write("String src_port_no;");bw.newLine();
		bw.write("String dst_port_no;");bw.newLine();
		bw.write("ViewableAtomic[] d_nodes = new ViewableAtomic["+nodes.length+"];");
		bw.newLine();
		bw.write("for (int i = 0; i < d_nodes.length; i++) {");
		bw.newLine();
		bw.write("d_nodes[i] = new Node(\"Router\" + i, new IPAddress(i),(processing_time), (byte)0);");
		bw.newLine();
		bw.write("add(d_nodes[i]);");
		bw.newLine();
		bw.write("\t}");
		bw.newLine();
		bw.newLine();

		bw.write("//link or couplings definitions");
		bw.newLine();
		for (int i = 0; i < edges.length; ++i) {
			Edge e = edges[i];
			int srcIndex = ((Integer) id2id.get(new Integer(e.getSrc().getID()))).intValue();		
			int dstIndex = ((Integer) id2id.get(new Integer(e.getDst().getID()))).intValue();
			bw.write(" src=d_nodes["+srcIndex+"];");bw.newLine();
			bw.write(" dst=d_nodes["+dstIndex+"];");bw.newLine();
			bw.write(" src_port_no=\"NIC\"+(src.getNumInports()+1);");bw.newLine();
			bw.write("src.addInport(src_port_no+\"-in\");");bw.newLine();
			bw.write("src.addOutport(src_port_no+\"-out\");");bw.newLine();

			bw.write(" dst_port_no=\"NIC\"+(dst.getNumInports()+1);");bw.newLine();
			bw.write("dst.addInport(dst_port_no+\"-in\");");bw.newLine();
			bw.write("dst.addOutport(dst_port_no+\"-out\");");bw.newLine();

			bw.write("addCoupling(src, src_port_no+\"-out\", dst, dst_port_no+\"-in\");");bw.newLine();
			bw.write("addCoupling(dst, dst_port_no+\"-out\", src, src_port_no+\"-in\");");bw.newLine();
		}
		bw.write("initialize();");bw.newLine();
		bw.write("int x = 0, y = 0;");bw.newLine();
		bw.write("preferredSize = new Dimension(1000, 1000);");bw.newLine();
		bw.write("for (int i = 0; i < d_nodes.length; i++) {");bw.newLine();
		bw.write("if (i != 0 && i % 10 == 0) {");bw.newLine();
		bw.write("y += 100;");bw.newLine();
		bw.write("x = 0;");bw.newLine();
		bw.write("}");bw.newLine();
		bw.write("((ViewableComponent) withName(\"Router\" + i)).setPreferredLocation(new Point(x, y));");bw.newLine();

		bw.write("x += 125;");bw.newLine();
		bw.write("}");bw.newLine();
		bw.write("preferredSize = new Dimension(508, 32 + 80 * 3);");bw.newLine();
		bw.write("}");bw.newLine();
		bw.write("}");bw.newLine();
		
/*
		// helper function to extract leaf nodes, i.e. nodes with degree 1
		bw.newLine();
		bw
				.write("#-------------  extract_leaf_nodes:  array with smallest degree nodes -----");
		bw.newLine();

		Node[] leaves = g.getLeafNodes();

		bw.write("proc extract_leaf_nodes{} {");
		bw.newLine();
		bw.newLine();
		int minDeg = g.getNumNeighborsOf(leaves[0]);
		bw.write("\t# minimum degree in this graph is: " + minDeg + ". ");
		bw.newLine();
		for (int i = 0; i < leaves.length; ++i) {
			bw.write("\tset leaf(" + i + ")  " + leaves[i]);
			bw.newLine();
		}

		bw.newLine();
		bw.write("}  #end function extract_leaf_nodes");
		bw.newLine();

		bw.newLine();
		bw
				.write("#----------  extract_nonleaf_nodes:  array with nodes which have degree > "
						+ minDeg + "  ---");
		bw.newLine();
		bw.write("proc extract_nonleaf_nodes{} {");
		bw.newLine();
		int nonLeafCount = 0;
		for (int i = 0; i < nodes.length; ++i) {
			int deg = 0;
			if ((deg = g.getNumNeighborsOf(nodes[i])) > minDeg) {
				bw.write("\tset non_leaf(" + nonLeafCount + ") " + nodes[i]
						+ "\t#deg=" + deg);
				;
				bw.newLine();
				++nonLeafCount;
			}
		}
		bw.newLine();
		bw.write("}  #end function extract_nonleaf_nodes");
		bw.newLine();
*/
		bw.close();
		Util.MSG("... DONE.");
	}

	public static void convert(String briteFile, int format) throws Exception {
		FileModel f = new FileModel(
				honeycomb.BRITE.Import.ImportConstants.BRITE_FORMAT, briteFile,
				format);
		Topology t = new Topology(f);
		DEVSExport ne = new DEVSExport(t, new File(briteFile + "_NS.tcl"));
		ne.export();
	}
	
	
	public static void main(String args[]) throws Exception {
		String briteFile = "";
		String routeroras = "";
		try {
			briteFile = args[0];
			routeroras = args[1];
		} catch (Exception e) {
			Util
					.ERR("Usage:  java Export.NSExport <brite-format-file> RT {| AS}");
		}

		int format = ModelConstants.RT_FILE;
		if (routeroras.equalsIgnoreCase("as"))
			format = ModelConstants.AS_FILE;

		FileModel f = new FileModel(
				honeycomb.BRITE.Import.ImportConstants.BRITE_FORMAT, briteFile,
				format);

		Topology t = new Topology(f);
		DEVSExport ne = new DEVSExport(t, new File(briteFile + "_DEVS.java"));
		ne.export();

	}

}
