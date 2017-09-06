package swarmNet.Trace;

/**
 * <p>Title: swarmNet</p>
 * <p>Description: The swarm network based on DEVS formalism</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Arizona State University</p>
 * @author Ahmet Zengin
 * @version 1.0
 */


import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import model.modeling.content;
import model.modeling.message;
import swarmNet.Components.IPAddress;
import swarmNet.Components.NETPacket;
import view.modeling.ViewableAtomic;
import GenCol.Function;
import GenCol.doubleEnt;
import GenCol.entity;


public class EventTransducer extends ViewableAtomic {
  protected Function  arrived, solved;
  protected double clock,total_ta,observation_time;
  protected ArrayList<IPAddress> node_addrs;
  private NETPacket packet, val;
  protected int x[];
  public DataOutputStream outFile;
  int count;

   public EventTransducer(){
    this("EventTransducer", 20);
  }

  public EventTransducer(String name, double Observation_time){
    super(name);

    addOutport("out");
    addInport("ariv");
    addInport("solved");
    addInport("start");
    observation_time = Observation_time;

   /* addTestInput("ariv",new NETPacket("packet"));
    addTestInput("solved",new NETPacket("packet"),10);*/
  }

  public void initialize(){

    setBackgroundColor(Color.orange);
    phase = "initialization";
    sigma = INFINITY;
    clock = 0;
    total_ta = 0;
    arrived = new Function();
    solved = new Function();
    node_addrs=new ArrayList<IPAddress>();
    packet=new NETPacket("nodeList");
    count=0;
    try {
    outFile = new DataOutputStream(
                       new BufferedOutputStream(
                             new FileOutputStream("output.csv")));
    }
    catch (Exception a) {
     System.out.println("Error: " + a.getMessage());
   }
 }

 public void  deltext(double e, message  x){
   clock = clock + e;
   Continue(e);
   if (phaseIs("initialization")) {
     for (int i = 0; i < x.getLength(); i++) {
       if (messageOnPort(x, "solved", i)) {
         val = (NETPacket) x.getValOnPort("solved", i);
         node_addrs.add(val.getSource());
       }
     }
     holdIn("listCreating", clock);
   }
   else
     for (int i = 0; i < x.size(); i++) {
       if (messageOnPort(x, "ariv", i)) {
         val = (NETPacket) x.getValOnPort("ariv", i);
         arrived.put(val.getName(), new doubleEnt(clock));
       }
       else if (messageOnPort(x, "start", i)) {
         initialize();
         holdIn("active", observation_time);
         sigma = observation_time;
         //clock = clock + sigma;
       }

       else if (messageOnPort(x, "solved", i)) {
    	   //count++;
    	   //System.out.println(count);
         val = (NETPacket) x.getValOnPort("solved", i);
         if (arrived.containsKey(val.getName())) {
           // calculate the packet processing time and the total through-time
           entity ent = (entity) arrived.assoc(val.getName());
           doubleEnt num = (doubleEnt) ent;
           double arrival_time = num.getv();
           double turn_around_time = clock - arrival_time;
           total_ta = total_ta + turn_around_time;
           //System.out.println("TRANSDUCER total_ta: "+total_ta);
           solved.put(val, new doubleEnt(clock));
         }
       }
     }
   //show_state();
   //Write starts data to file
   try {
     //outFile.writeBytes(
        // "Clock,Arrived Packets,Solved Packets,lost packets,TA,Thru\n");
	
     outFile.writeBytes(System.currentTimeMillis()+clock + ";" +
                        arrived.size() + ";" +
                        solved.size() + ";" +
                        (arrived.size() - solved.size()) + ";" +
                        compute_TA() + ";" +
                        compute_Thru() + "\n");
     
     //else outFile.close();
   }
   catch (Exception a) {
     System.out.println("Error: " + a.getMessage());
   }
 }

  public void deltint() {
	  
	  //System.out.println((arrived.size()+" "+ solved.size()));
	  passivate();
   // show_state();
  }
	public void deltcon(double e, message x) {
		deltext(0, x);
		deltint();
	}
 public message out(){

   message m = new message();
   if (phaseIs("listCreating")) {
     packet.setData(getNodeList());
     //packet.print();
     m.add(makeContent("out", packet));
   }
   if (phaseIs("active")) {
     content con = makeContent("out", new entity("TA: " + compute_TA()));
     m.add(con);
   }
   return m;

 }

 public double compute_TA() {
   double avg_ta_time = 0;
   if (!solved.isEmpty())
     avg_ta_time = ((double) total_ta) / solved.size();
   return avg_ta_time;
 }

 public double compute_Thru() {
   double thruput = 0;
   if (clock > 0)
     thruput = solved.size() / (double) clock;
   return thruput;
 }

 public ArrayList<IPAddress> getNodeList() {
   return node_addrs;
 }

 public int[] toIntArray() {
   x = new int[node_addrs.size()];
   for (int i = 0; i < node_addrs.size(); i++) {
     x[i] = ( (IPAddress) node_addrs.get(i)).getIntAddress();
   }
   return x;
 }

 /*public void show_state() {
   int num_arrived = 0;
   int num_solved = 0;
   int total_hops = 0;
   System.out.println("state of  " + name + ": ");
   System.out.println("phase, sigma : "
                      + phase + " " + sigma + " ");

   if (arrived != null)
     num_arrived = arrived.size();
   if (solved != null)
     num_solved = solved.size();

     // print the packet summary information
   System.out.println("arrived packets is " + num_arrived+" "+ total_ta);
   System.out.println("solved packets is " + num_solved);
   System.out.println("lost packets is " + (num_arrived - num_solved));
   if (num_arrived > 0)
     System.out.println("Percentage of lost packets is " +
                        (double) (num_arrived - num_solved) /
                        (double) num_arrived);
   else
     System.out.println("No packets arrived");
   System.out.println("AVG TA = " + compute_TA());
   System.out.println("THRUPUT = " + compute_Thru());
 }*/

 public String showS() {
   String str;
   int num_arrived = 0;
   int num_solved = 0;
   int total_hops = 0;

   if (arrived != null)
     num_arrived = arrived.size();
   if (solved != null)
     num_solved = solved.size();
   if (num_arrived > 0) {
     str = "Percentage of lost packets is " +
         (double) (num_arrived - num_solved) / (double) num_arrived;
   }

   else {
     str = "No packets arrived";
   }
   try{
		outFile.close();
	  }catch(Exception a){}

   // print the packet summary information
   return "clock: "+clock
       + "<br>" + "total_ta: "+total_ta
       + "<br>" + "arrived packets is " + num_arrived
       + "<br>" + "solved packets is " + num_solved
       + "<br>" + "lost packets is " + (num_arrived - num_solved)
       + "<br>" + str
       + "<br>" + "AVG TA = " + compute_TA()
       + "<br>" + "THRUPUT = " + compute_Thru();

 }

 public String getTooltipText() {
   return super.getTooltipText()
       + "<br>" + showS();
 }
 }
