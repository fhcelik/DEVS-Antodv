package swarmNet.Trace;

/**
 * <p>Title: swarmNet</p>
 * <p>Description: The swarm network based on DEVS formalism</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Arizona State University</p>
 * @author Ahmet Zengin
 * @version 1.0
 */

import java.awt.Dimension;
import java.awt.Point;

import swarmNet.Components.NETPacket;
import view.modeling.ViewableAtomic;
import view.modeling.ViewableComponent;
import view.modeling.ViewableDigraph;
import GenCol.doubleEnt;

public class EF
    extends ViewableDigraph {

  protected ViewableAtomic g;
  protected NETPacket val;

  public EF() {
    super("ExperimentalFrame");
    efConstruct(10, 50);
  }

  public EF(String nm, double int_arr_t, double observe_t) {
    super(nm);
    efConstruct(int_arr_t, observe_t);
  }

  public void efConstruct(double int_arr_t, double observe_t) {

    addInport("inEvent");
    addInport("start");
    addInport("stop");
    addOutport("outEvent");
    addOutport("result");

    ViewableAtomic g = new EventGenerator("EventGenr", int_arr_t);
    ViewableAtomic t = new EventTransducer("EventTransd", observe_t);

    add(g);
    add(t);
    addTestInput("start", new doubleEnt(0.0035));

    addTestInput("start", new doubleEnt(1));
    //addTestInput("start", new doubleEnt(5));
    //addTestInput("stop",new entity(""));

    addCoupling(g, "out", t, "ariv");
    addCoupling(t, "out", g, "stop");
    addCoupling(this, "start", g, "start");
    addCoupling(this, "start", t, "start");
    addCoupling(this, "stop", g, "stop");
    addCoupling(g, "out", this, "outEvent");
    addCoupling(t, "out", this, "result");
    addCoupling(this, "inEvent", t, "solved");
    //addCoupling(this,"inEvent",g,"start");
    initialize();

    preferredSize = new Dimension(279, 146);
    g.setPreferredLocation(new Point(6, 17));
    t.setPreferredLocation(new Point( -5, 81));
  }

  /* public void deltext(double e, message x)  {
     for (int i = 0; i < x.getLength(); i++)
       if (messageOnPort(x, "inEvent", i)) {
         val = (NETPacket) x.getValOnPort("solved", i);
         if (val.id.equals("agent")) {

           removeCoupling(this, "inEvent", g, "start");
           initialize();
         }
       }
   }


   /**
    * Automatically generated by the SimView program.
    * Do not edit this manually, as such changes will get overwritten.
    */
   public void layoutForSimView() {
     preferredSize = new Dimension(333, 144);
     ( (ViewableComponent) withName("EventTransd")).setPreferredLocation(new
         Point( -5, 80));
     ( (ViewableComponent) withName("EventGenr")).setPreferredLocation(new
         Point(6, 17));
   }
}
