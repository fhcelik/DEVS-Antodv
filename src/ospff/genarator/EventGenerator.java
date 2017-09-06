package ospff.genarator;

/**
 *
 * <p>Title: swarmNet</p>
 * <p>Description: The swarm network based on DEVS formalism</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Arizona State University</p>
 * @author Ahmet Zengin
 * @version 1.0
 */
import view.modeling.ViewableAtomic;
import java.awt.*;
import java.util.Random;
import GenCol.*;
import java.util.LinkedList;

import ospff.IPAddress;
import ospff.paketformat;

import model.modeling.content;
import model.modeling.message;

/**
 * This class generates events for components with fixed time intervals
 */
public class EventGenerator extends ViewableAtomic {

  protected double int_arr_time;
  protected int count;
  protected Random rand;
  protected LinkedList nodeList;
  protected int[] x;
  private paketformat packet, p;

  public EventGenerator() {this("EventGenr", 10);}

  public EventGenerator(String name,double Int_arr_time){

   super(name);
   addOutport("out");
   addInport("stop");
   addInport("start");
   int_arr_time = Int_arr_time ;

   addTestInput("start", new doubleEnt(10));
   addTestInput("start", new doubleEnt(5));
   addTestInput("stop",new entity(""));
  }

  public void initialize(){
   phase = "initialization";
   sigma = INFINITY;
   count = 0;
   rand = new Random();
   setBackgroundColor(Color.yellow);
   super.initialize();
 }

  public void  deltext(double e,message x){

    Continue(e);

    if (phaseIs("listCreated")||phaseIs("passive")) {
      for (int i = 0; i < x.getLength(); i++)
        if (messageOnPort(x, "start", i)) {
          //initialize();
          int_arr_time = ( (doubleEnt) x.getValOnPort("start", i)).getv();
          
        }
      holdIn("active", int_arr_time);
    }

    if (phaseIs("initialization")) {
      for (int i = 0; i < x.getLength(); i++) {
        if (messageOnPort(x, "stop", i)) {
           p = (paketformat) x.getValOnPort("stop", i);
          
           nodeList = (LinkedList) p.getData();
          //p.print();
 
        }
      }
      /*System.out.println("this is my array ");
      int[] a = toIntArray(nodeList);
      for (int i = 0; i < a.length; i++) {
        System.out.println(a[i]);
      }*/
      phase = "listCreated";
    }
    if (phaseIs("active")) {
      for (int i = 0; i < x.getLength(); i++)
        if (messageOnPort(x, "stop", i)){
          phase = "finishing";
      passivate();
        }
    }
  }

  public void deltint(){
    if(phaseIs("active")){
      count = count +1;
      holdIn("active",int_arr_time);
    }
   /* if (count == 5){
      passivate();
    }*/
  
  }

  public message out(){
    message m = new message();
    if (phaseIs("active")) {
      content con = makeContent("out", makePacketEvent("data"+count));
      m.add(con);
    }
     return m;
  }
  //Compute next time a packet will be created
  //next_time = time - period*((new Float(Math.log(1 - Math.random()))).floatValue());

  /**
    * Make a packet event with specified name
    * @return packet created packet
    */
   public paketformat makePacketEvent(String name) {
     packet = new paketformat(name);
     packet.setAdres(new IPAddress(getRndIP()));
     packet.setHedef (new IPAddress(getRndIP()));
     if(packet.getAdres().equals(packet.getHedef()) )
        packet.setHedef( new IPAddress(getRndIP()));
      /*while (!packet.source.equals(packet.destination)) {
       packet.destination = new IPAddress(getRndIP());
     }*///(int)(Math.random()*nodeList.size())
     packet.setLength((int)(100000 * rand.nextDouble()));
     //packet.ttl=2*int_arr_time;
  //   System.out.println(packet.pPrnt());
    // for (int i=0;i<30;i++)System.out.println("pppp"+nodeList.get(i));
     return packet;
   }

   /**
    * Get random IP address from node ip list
    * @return IP address
    */
   public int getRndIP() {
     int[] x = toIntArray();
     double d = x.length * rand.nextDouble();
     int ip = x[(int)d];
     return ip;
   }

   /**
    * Move the node IP address LinkedList to int array
    * @param nList node IP list
    * @return x[] array
    */
   public int[] toIntArray() {
     x = new int[nodeList.size()];
     for (int i = 0; i < nodeList.size(); i++) {
       x[i] = ( (IPAddress) nodeList.get(i)).getIntegerAddress();
     }
     return x;
   }

   public void showState() {
     super.showState();
     System.out.println("int_arr_t: " + int_arr_time);
   }

   public String getTooltipText() {
     return
         super.getTooltipText()
         + "\n" + " int_arr_time: " + int_arr_time
         + "\n" + " count: " + count
         + "\n" + " nodeNo:" + nodeList.size();

   }
 }
