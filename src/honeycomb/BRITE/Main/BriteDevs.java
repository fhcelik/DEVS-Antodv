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

package honeycomb.BRITE.Main;

import java.io.File;
import java.util.HashSet;



import honeycomb.BRITE.Export.BriteExport;
import honeycomb.BRITE.Graph.Edge;
import honeycomb.BRITE.Graph.Graph;
import honeycomb.BRITE.Graph.Node;
import honeycomb.BRITE.Model.Model;
import honeycomb.BRITE.Topology.Topology;
import honeycomb.BRITE.Util.RandomGenManager;
import honeycomb.BRITE.Util.Util;

final public class BriteDevs {
	private Topology t;
	public int getLS(String fn){
		String filename = "";

		/* get config file, get output file */
		try {
			filename = fn;
		} catch (Exception e) {
			Util.ERR("Usage:  java Main.Main config_file output_file seed_file");	
			System.exit(0);
		}
		/* create our glorious model and give it a random gen manager */
		Model m = ParseConfFile.Parse(filename);
		return m.getLS();
		}
	public int getHS(String fn){
		String filename = "";

		/* get config file, get output file */
		try {
			filename = fn;
		} catch (Exception e) {
			Util.ERR("Usage:  java Main.Main config_file output_file seed_file");	
			System.exit(0);
		}
		/* create our glorious model and give it a random gen manager */
		Model m = ParseConfFile.Parse(filename);
		return m.getHS();
		}
	private Model m;
	public Graph formTopology(String fn, String sf) {
		String filename = "";
		String outFile = "net";
		String seedFile = "";

		/* get config file, get output file */
		try {
			filename = fn;
			seedFile = sf;
		} catch (Exception e) {
			Util.ERR("Usage:  java Main.Main config_file output_file seed_file");	
			System.exit(0);
		}
		RandomGenManager rgm = new RandomGenManager();
		rgm.parse(seedFile);
		Node n=new Node();
		n.nodeCount=-1;
		Edge e=new Edge(null, null);
		e.edgeCount=-1;
		/* create our glorious model and give it a random gen manager */
		
		Model m = ParseConfFile.Parse(filename);
		m.setRandomGenManager(rgm);

		/* now create our wonderful topology. ie call model.generate() */
		t = new Topology(m);

		/* check if our wonderful topology is connected */
		Util.MSGN("Checking for connectivity:");
		Graph g = t.getGraph();
		boolean isConnected = (g.isConnected());
		if (isConnected)
			System.out.println("\tConnected");
		else
			System.out.println("\t***NOT*** Connected");

		/* beging output of topology */
		HashSet exportFormats = ParseConfFile.ParseExportFormats();
		ParseConfFile.close();
		// outFile = outFile.substring(0, outFilelastIndexOf('.')); //don't want
		// extension since we assign ours

		// /*export to brite format outfile
		if (exportFormats.contains("BRITE")) {
			Util.MSG("Exporting Topology in BRITE format to: " + outFile
					+ ".brite");
			BriteExport be = new BriteExport(t, new File(outFile + ".brite"));
			be.export();
			//honeycomb.BRITE.Visualizer.Visualize.ciz("net.brite");
		}
		
		/* outputting seed file */
		Util.MSG("Exporting random number seeds to seedfile");
		rgm.export("last_seed_file", "seed_file");

		/* we're done (and hopefully successfully) */
		Util.MSG("Topology Generation Complete.");

		// t.dumpToOutput();
		return g;
	}
public Model getModel(){
		
		return m;
	}
}
