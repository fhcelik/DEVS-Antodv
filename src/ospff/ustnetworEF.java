package ospff;

/**
 * <p>Title: swarmNet</p>
 * <p>Description: The swarm network based on DEVS formalism</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Arizona State University</p>
 * @author Ahmet Zengin
 * @version 1.0
 */




import java.awt.*;
import ospff.genarator.*;

import GenCol.*;

import view.modeling.ViewableComponent;
import view.modeling.ViewableDigraph;
public class ustnetworEF extends ViewableDigraph {

public ustnetworEF(){
    super("SAMPLE SWARM NETWORK 11");
    ViewableDigraph swnt = new ustnetwor("Network",1,1);
    ViewableDigraph  expf = new EF("Experimental Frame", 1,1000);

    addInport("start");
    addInport("stop");

    add(expf);
    add(swnt);

    addTestInput("start", new doubleEnt(1));
    addTestInput("start", new doubleEnt(2));
    addTestInput("start", new doubleEnt(3));


    addCoupling(this,"start",expf,"start");
    addCoupling(this,"stop",expf,"stop");

    addCoupling(expf,"outEvent",swnt,"inEvent");
    addCoupling(swnt,"outEvent",expf,"inEvent");

    initialize();

    preferredSize = new Dimension(1493, 5716);
    expf.setPreferredLocation(new Point(398, 18));
    swnt.setPreferredLocation(new Point(21, 171));
 }
    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
        preferredSize = new Dimension(1035, 5716);
        ((ViewableComponent)withName("Experimental Frame")).setPreferredLocation(new Point(398, 18));
        ((ViewableComponent)withName("Network")).setPreferredLocation(new Point(58, 246));
    }
}
