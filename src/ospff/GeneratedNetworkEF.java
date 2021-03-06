package ospff;

/**
 * <p>Title: ospff</p>
 * <p>Description: The swarm network based on DEVS formalism</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Arizona State University</p>
 * @author Ahmet Zengin
 * @version 1.0
 */
import java.awt.Dimension;
import java.awt.Point;

import ospff.genarator.EF;
import view.modeling.ViewableComponent;
import view.modeling.ViewableDigraph;
import GenCol.doubleEnt;

public class GeneratedNetworkEF extends ViewableDigraph {
	
	public GeneratedNetworkEF() {
		super("SAMPLE GENERATED OSPF NETWORK ");
		ViewableDigraph swnt = new GeneratedNetwork("Network", 1);
		ViewableDigraph expf = new EF("Experimental Frame", 1, 1000);

		addInport("start");
		addInport("stop");

		add(expf);
		add(swnt);
		addTestInput("start", new doubleEnt(10));
		addTestInput("start", new doubleEnt(9));
		addTestInput("start", new doubleEnt(8));
		addTestInput("start", new doubleEnt(7));
		//addTestInput("start", new doubleEnt(6));
		addTestInput("start", new doubleEnt(5));
		//addTestInput("start", new doubleEnt(4));
		//addTestInput("start", new doubleEnt(3));
		//addTestInput("start", new doubleEnt(2));
		addTestInput("start", new doubleEnt(1));
		addTestInput("start", new doubleEnt(0.9));
		addTestInput("start", new doubleEnt(0.8));

		addCoupling(this, "start", expf, "start");
		addCoupling(this, "stop", expf, "stop");

		addCoupling(expf, "outEvent", swnt, "inEvent");
		addCoupling(swnt, "outEvent", expf, "inEvent");

		initialize();

		/*preferredSize = new Dimension(973, 555);
		expf.setPreferredLocation(new Point(304, 312));
		swnt.setPreferredLocation(new Point(25, 15));*/
	}

    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
        preferredSize = new Dimension(1203, 10000);
        ((ViewableComponent)withName("Experimental Frame")).setPreferredLocation(new Point(15, 15));
        ((ViewableComponent)withName("Network")).setPreferredLocation(new Point(15, 200));
    }
}
