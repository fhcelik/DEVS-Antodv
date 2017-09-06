package honeycomb.genarator;




import view.modeling.ViewableAtomic;
import view.modeling.ViewableComponent;
import view.modeling.ViewableDigraph;

import honeycomb.paketformat;

import java.awt.*;
import java.io.*;
import GenCol.*;
import java.lang.*;
import java.util.Random;


public class EF extends ViewableDigraph {

  protected ViewableAtomic g;
  protected paketformat val;

  public EF(){
      super("ExperimentalFrame");
      efConstruct(10,50);
  }

  public EF(String nm,double int_arr_t,double observe_t){
      super(nm);
      efConstruct(int_arr_t,observe_t);
  }

  public void efConstruct(double int_arr_t,double observe_t){

      addInport("inEvent");
      addInport("start");
      addInport("stop");
      addOutport("outEvent");
      addOutport("result");

      ViewableAtomic g = new EventGenerator("EventGenr",int_arr_t);
      ViewableAtomic t = new EventTransducer("EventTransd",observe_t);

       add(g);
       add(t);
      addTestInput("start", new doubleEnt(1));
      addTestInput("start", new doubleEnt(2));
      addTestInput("start", new doubleEnt(0.1));
      addTestInput("start", new doubleEnt(0.001));




       //addTestInput("start", new doubleEnt(10));
       //addTestInput("start", new doubleEnt(5));
       //addTestInput("stop",new entity(""));

       addCoupling(g,"out",t,"ariv");
       addCoupling(t,"out",g,"stop");
       addCoupling(this,"start",g,"start");
       addCoupling(this,"start",t,"start");
       addCoupling(this,"stop",g,"stop");
       addCoupling(g,"out",this,"outEvent");
       addCoupling(t,"out",this,"result");
       addCoupling(this,"inEvent",t,"solved");
       //addCoupling(this,"inEvent",g,"start");
       initialize();

       preferredSize = new Dimension(279, 146);
       g.setPreferredLocation(new Point(6, 17));
       t.setPreferredLocation(new Point(-5, 81));
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
    public void layoutForSimView()
    {
        preferredSize = new Dimension(333, 144);
        ((ViewableComponent)withName("EventTransd")).setPreferredLocation(new Point(-6, 80));
        ((ViewableComponent)withName("EventGenr")).setPreferredLocation(new Point(9, 20));
    }
}
