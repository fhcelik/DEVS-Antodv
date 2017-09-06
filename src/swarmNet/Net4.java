package swarmNet;

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
import model.modeling.IOBasicDevs;
import model.modeling.componentIterator;
import swarmNet.Components.IPAddress;
import swarmNet.Components.NETPacket;
import swarmNet.Framework.Link;
import swarmNet.Framework.AtomicNode;
import view.modeling.ViewableComponent;
import view.modeling.ViewableDigraph;

public class Net4 extends ViewableDigraph {
    private AtomicNode n1,n2,n3,n4;
    private Link l1,l2,l3,l4;
 // public NETPacket n;
  //public componentIterator c;

  public Net4(){
    this("Network", 1, 1);
  }

  public Net4(String name, double processing_time, double propagation_delay){
    super(name);
    make(processing_time, propagation_delay);

   componentIterator c = getComponents().cIterator();
   int i=0;
   while(c.hasNext()) {
     IOBasicDevs iob=c.nextComponent();
     //System.out.println(iob.getName());
     if (iob.getName().substring(0,1).equals("R")){
       //System.out.println("this runs!!!"+iob.getName().substring(0,1));
       i++;
       NETPacket  n = new NETPacket("packet"+i);
       n.setDest(new IPAddress(0,0,0,3));
       addTestInput("in", n);
     }
   }
 }

 protected void make(double processing_time, double propagation_delay){


    addInport("in");
    addInport("inEvent");
    addOutport("out");
    addOutport("outEvent");

    //create the 4 router

    n1 = new AtomicNode("Router0", new IPAddress(0), (processing_time));
    n2 = new AtomicNode("Router1", new IPAddress(1), (processing_time));
    n3 = new AtomicNode("Router2", new IPAddress(2), processing_time);
    n4 = new AtomicNode("Router3", new IPAddress(3), processing_time);
    l1=new Link("Link0",250000, propagation_delay);//
    l2=new Link("Link1",250000, propagation_delay);
    l3=new Link("Link2",250000, propagation_delay);
    l4=new Link("Link3",250000, propagation_delay);
    add(n1);
    add(n2);
    add(n3);
    add(n4);
    add(l1);
    add(l2);
    add(l3);
    add(l4);

    //do coupling
    /*componentIterator ci=getComponents().cIterator();

   for (int i=0; i < getComponents().size(); i++) {
     IODevs d=ci.nextComponent();
     if(d.getName().startsWith("Link"))
    	 continue;
     addCoupling(this, "inEvent", d, "inEvent");
     addCoupling(d, "outEvent", this, "outEvent" );
   }*/

    addCoupling(this, "in", n1, "NIC1-in");
    addCoupling(n1, "NIC1-out", l1, "in");
    addCoupling(n2, "NIC1-out",l1, "in1");
    addCoupling(n2, "NIC2-out", l2, "in");
    addCoupling(n2, "NIC3-out", l3, "in");
    addCoupling(n3, "NIC1-out", l2, "in1");
    addCoupling(n3, "NIC2-out", l4, "in");
    addCoupling(n4, "NIC1-out", l3, "in1");
    addCoupling(n4, "NIC2-out", l4, "in1");
    addCoupling(l1, "out1", n1, "NIC1-in");
    addCoupling(l1, "out", n2, "NIC1-in");
    addCoupling(l2, "out1", n2, "NIC2-in");
    addCoupling(l2, "out", n3, "NIC1-in");
    addCoupling(l3, "out1", n2, "NIC3-in");
    addCoupling(l3, "out", n4, "NIC1-in");
    addCoupling(l4, "out1", n3, "NIC2-in");
    addCoupling(l4, "out", n4, "NIC2-in");
    initialize();

    preferredSize = new Dimension(508, 32 + 80 * 3);
  }

    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
        preferredSize = new Dimension(1216, 382);
        ((ViewableComponent)withName("Link1")).setPreferredLocation(new Point(647, 52));
        ((ViewableComponent)withName("Router0")).setPreferredLocation(new Point(8, 13));
        ((ViewableComponent)withName("Router1")).setPreferredLocation(new Point(439, 39));
        ((ViewableComponent)withName("Link3")).setPreferredLocation(new Point(679, 222));
        ((ViewableComponent)withName("Link0")).setPreferredLocation(new Point(219, 25));
        ((ViewableComponent)withName("Router3")).setPreferredLocation(new Point(432, 306));
        ((ViewableComponent)withName("Link2")).setPreferredLocation(new Point(450, 196));
        ((ViewableComponent)withName("Router2")).setPreferredLocation(new Point(882, 83));
    }
}