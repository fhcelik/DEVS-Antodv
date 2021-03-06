	package ServiceMod;
	
	import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import view.modeling.ViewableComponent;
import GenCol.Pair;
import GenService.ApplicationComposition;
import GenService.ServiceBroker;
import GenService.ServiceClient;
import GenService.ServiceLookupMessage;
import GenService.ServiceRouter;
	
	public class Primitive_SN_P1 extends ApplicationComposition{
		
		public final static double observation = 70;
		public ServiceBroker Broker;
		public ServiceRouter Router;
		
		public Primitive_SN_P1(){
			super("Voice Communication Service");
		}
		
		public void BrokerRouterConstruct(){
			
			//attributes 
			double available = 60;     //available time for broker
			double startTime = 0.5; 
		 	double bandwidth = 10;      //bandwidth for the network or router
            
		 	//Ceate an unique component
			Broker = new ServiceBroker("Broker", available, startTime);
			Router = new ServiceRouter("Router Link", bandwidth);			
			Broker.setBackgroundColor(Color.YELLOW);
			Router.setBackgroundColor(Color.PINK);
			
			BrokerList.add(Broker);
			RouterList.add(Router);			
		}
		
		public void PublisherConstruct(){
			
			ArrayList <Pair> Endpoints = new ArrayList <Pair> ();
	 		
			Endpoints.add(new Pair("qRate", "Double"));		
			VoiceComm Service1 = 
				new VoiceComm("VoiceComm", "Voice Communication", "Atomic", Endpoints, 1);
			Service1.setBackgroundColor(Color.CYAN);
			PublisherList.add(Service1);
		}	
		
		
		public void SubscriberConstruct(){
			
			//Construct ServiceLookup information: The list of service to subscribe
			ArrayList <ServiceLookupMessage> lookupList = new ArrayList <ServiceLookupMessage> ();
			lookupList.add(new ServiceLookupMessage("VoiceComm", "qRate", new Pair("Hz", 220500), 60));		
			ServiceClient Subscriber1 = new ServiceClient("Subscriber1", lookupList, 0.1);
			
			lookupList = new ArrayList <ServiceLookupMessage> ();
			lookupList.add(new ServiceLookupMessage("VoiceComm", "qRate", new Pair("Hz", 220500), 60));
			ServiceClient Subscriber2 = new ServiceClient("Subscriber2", lookupList, 0.1);
			
			lookupList = new ArrayList <ServiceLookupMessage> ();
			lookupList.add(new ServiceLookupMessage("VoiceComm", "qRate", new Pair("Hz", 220500), 60));
			ServiceClient Subscriber3 = new ServiceClient("Subscriber3", lookupList, 0.1);
			
			Subscriber1.setBackgroundColor(Color.GREEN);
			Subscriber2.setBackgroundColor(Color.GREEN);
			Subscriber3.setBackgroundColor(Color.GREEN);
			
			//Construct the subscriber list
			SubscriberList.add(Subscriber1);
			SubscriberList.add(Subscriber2);
			SubscriberList.add(Subscriber3);			
		}  
		
		public void TransducerConstruct(){
			BrokerTransd BroTrans  =  new BrokerTransd("BrokerTransd", observation);
			RouterTransd NecTrans  =  new RouterTransd("RouterTransd", observation);
			SubscriberTransd SubTrans1 =  new SubscriberTransd("Sub1Transd", observation);
			SubscriberTransd SubTrans2 =  new SubscriberTransd("Sub2Transd", observation);
			SubscriberTransd SubTrans3 =  new SubscriberTransd("Sub3Transd", observation);
			PublisherTransd PubTrans1 =  new PublisherTransd("VoiceCommTransd", observation);
				
			//Always the same order: Broker >> Network >> Subscriber >> Publisher
			TransducerList.add(BroTrans);
			TransducerList.add(NecTrans);
			TransducerList.add(SubTrans1);
			TransducerList.add(SubTrans2);
			TransducerList.add(SubTrans3);
			TransducerList.add(PubTrans1);
		}   
   
   
    
    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
        preferredSize = new Dimension(695, 374);
        ((ViewableComponent)withName("Subscriber3")).setPreferredLocation(new Point(15, 263));
        ((ViewableComponent)withName("VoiceComm")).setPreferredLocation(new Point(448, 71));
        ((ViewableComponent)withName("Sub3Transd")).setPreferredLocation(new Point(32, 315));
        ((ViewableComponent)withName("Broker")).setPreferredLocation(new Point(217, 33));
        ((ViewableComponent)withName("Sub1Transd")).setPreferredLocation(new Point(34, 77));
        ((ViewableComponent)withName("Subscriber1")).setPreferredLocation(new Point(15, 25));
        ((ViewableComponent)withName("BrokerTransd")).setPreferredLocation(new Point(247, 100));
        ((ViewableComponent)withName("RouterTransd")).setPreferredLocation(new Point(249, 233));
        ((ViewableComponent)withName("Subscriber2")).setPreferredLocation(new Point(15, 147));
        ((ViewableComponent)withName("VoiceCommTransd")).setPreferredLocation(new Point(459, 124));
        ((ViewableComponent)withName("Router Link")).setPreferredLocation(new Point(254, 165));
        ((ViewableComponent)withName("Sub2Transd")).setPreferredLocation(new Point(33, 199));
    }
}
