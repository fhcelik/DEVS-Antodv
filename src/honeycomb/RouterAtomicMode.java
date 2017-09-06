package honeycomb;
import honeycomb.genarator.EventGenerator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Vector;

import GenCol.doubleEnt;


import model.modeling.message;

import view.modeling.ViewableAtomic;


public class RouterAtomicMode extends ViewableAtomic {

	private Queue queue;
	
	private IPAddress ip,hedefip,dene,yedekforward;
	private double iþlem_zamaný;
	private int energy;
	
	
	private Vector<Yol> tabu;
	private Vector<Yol> yt;
	private int kod1=0,kod2=0;
	private double Hello_interval = 1000, simulation_time = 10000;
	private paketformat paket,paket_hold,paketim;
	private Vector<paketformat> yedpaket,paket_forward;
	private Vector<Integer> deger;
	private int [][] yol_kod;
	private int paket_hareket,enr;
	private int yol=0,sayan=0, gizc=0,iizc=0;
	private double say,sayac=0;
	private Vector<String> rreqid;
	private Vector<Integer> rrepid;
	private boolean sor=true;
	String data;
	int portno;
	
	public RouterAtomicMode(String name, IPAddress ipadres, double iþlemzamaný,double sim_time, int energ) {
		super(name);
		ip = ipadres;
		iþlem_zamaný = iþlemzamaný;
		energy=energ;
		addTestInput("NIC-in", new paketformat("packet_data", new IPAddress(0, 0, 0, 9)));
		simulation_time = 10000;
		
		
		
	}

	public RouterAtomicMode() {
		this("Yönlendirici", new IPAddress(0, 0, 0, 1), 1,10100,1);
	}

	public void initialize() {
		phase = "kurulum";
		sigma = 0;
		super.initialize();
		yt = new Vector<Yol>();
		
		queue = new Queue(256000);
		rreqid = new Vector<String>();
		rrepid = new Vector<Integer>();
		tabu = new Vector<Yol>();
		
	
		paket_hold=null;
		yedpaket= new Vector<paketformat>();
		paket_forward= new Vector<paketformat>();
		deger = new Vector<Integer>();
		yol_kod=new int[10000][2];
		paket=new paketformat("paket");
		
	}

	public void deltext(double e, message x) {
		Continue(e);
		int t=0;
	//	tabutemizle();
		energy--;
		for (int i = 0; i < x.getLength(); i++) {			
			for (int k = 0; k < getNumInports(); k++) {
		
				String PN=(String)getInportNames().get(k);
			
				if (messageOnPort(x, PN, i)){
					
	//			if (PN.equals("NIC-in")){
					
		
				paket = new paketformat((paketformat) x.getValOnPort(PN,i));
				

				
				paket.setCýkýþ_port("NIC-out");
				
				if(paket.getAdi().startsWith("data")){
						if (PN.equals("inEvent")){
							if (paket.getAdres().equals(ip)) {
					//			System.out.println(getName()+".detex.tttttinevent.."+paket.getAdi());
				              paket.setCýkýþ_port("outEvent");
				              if(queue.enqueue(new paketformat(paket)))
				              holdIn("data_alýndý", iþlem_zamaný);
				            }
						
					}
						else if(PN.equals("NIC-in")){
							if(queue.enqueue(new paketformat(paket)))
					//			System.out.println(getName()+".detex.tttttinevent.."+paket.getAdi());		
							 holdIn("data_alýndý", iþlem_zamaný);
					}
						
						
					}
				
				else if(paket.getAdi().equalsIgnoreCase("hello")){
					
							if(queue.enqueue(new paketformat(paket)))
							holdIn("hello_alýndý", iþlem_zamaný);
				}
				
				else if(paket.getAdi().equalsIgnoreCase("IIZC") && ip.getByte3()!=0){
				
		
							if(queue.enqueue(new paketformat(paket)))

							holdIn("iizc_alýndý", iþlem_zamaný);
 					}
				
				else if(paket.getAdi().equalsIgnoreCase("GIZC") && ip.getByte3()!=0){
					
							if(queue.enqueue(new paketformat(paket))){
					//			System.out.println(getName()+".detex.tttttinevent.."+paket.getAdi()+"adres0"+paket.getHedef()+"adres"+paket.getAdres());
								holdIn("gizc_alýndý", iþlem_zamaný);}
							
					}
				else if(paket.getAdi().equalsIgnoreCase("mesaj")){
					
					if(queue.enqueue(new paketformat(paket))){
					holdIn("mesaj_alýndý", iþlem_zamaný);}
		}
				
				
//				}
/*				if (PN.equals("inEvent")) {
		          
					paket = new paketformat((paketformat) x.getValOnPort("inEvent", i));
					
					if (paket.getAdres().equals(ip)) {
					System.out.println(getName()+".detex.tttttinevent.."+paket.getAdi());
		              paket.setCýkýþ_port("outEvent");
		              kuyruk.addElement(paket);
		              
		              phase="paket_alýndý";
		      				
		      		
		            }
		           
		          }*/
			
				}
				}
		}
		
			}
			
public void deltint() {
	
sor=true;
 //if(energy<50 && !getName().equalsIgnoreCase("Node_0")) holdIn("boþda", INFINITY);

if(queue.isEmpty() && deger.isEmpty())
	holdIn("boþda", INFINITY);

else if(queue.isEmpty() && !deger.isEmpty())
	{//System.out.println(getName()+".sayan.."+sayan+".deger.."+deger);System.out.println(getName()+"...yedek...paket"+yedpaket);
	if(sayan>=15){ //	System.out.println(getName()+".sayan.."+sayan);
		sayan=0;
	int ilk=deger.get(0);//System.out.println(getName()+"..deger."+deger.get(0));
	for(int m=0;m<deger.size();m++){
		if(ilk<=deger.get(m)){ilk=deger.get(m);
		kod1=yol_kod [m][0];
		 kod2=yol_kod [m][1];//System.out.println(getName()+"..deger."+yol_kod [m][0]+".."+yol_kod [m][1]);
		 }
	}
	for(int z=0;z<tabu.size();z++){
		if(tabu.get(z).getYolu()==kod1 && tabu.get(z).getId()==kod2 ){
		
	//		System.out.println(getName()+"..yol....TABU.."+tabu.get(z).getKaynak()+"..YEDEH.."+yedpaket.get(s));
		
		paket_forward.addAll(yedpaket);
		paketim.setSonraki_düðüm(tabu.get(z).getSonraki_düðüm());
		yedekforward=tabu.get(z).getSonraki_düðüm();
		yedpaket.removeAllElements();
		holdIn("data_forward", iþlem_zamaný);
	
	
		}
	}
	}
	else{ holdIn("BEKLEME", iþlem_zamaný);}
	}

else {	

	paket_hold=new paketformat(queue.dequeue());
	
	
	if (paket_hold.getAdi().equals("hello")) {	

		
	
		Yol y=new Yol(0,paket_hold.getId(),paket_hold.getAdi(),new IPAddress(paket_hold.getAdres().getByte4(),paket_hold.getAdres().getByte3(),paket_hold.getAdres().getByte2(),paket_hold.getAdres().getByte1()),new IPAddress(paket_hold.getAdres().getByte4(),paket_hold.getAdres().getByte3(),paket_hold.getAdres().getByte2(),paket_hold.getAdres().getByte1()),paket_hold.getMaliyet(),paket_hold.getEnergy(),sayac);		
		yt.addElement(y);
		holdIn("komþu_eklendi", iþlem_zamaný);	
	
	
		} 	

	else if(sayan>=15){ //System.out.println(getName()+".her yer dolu sayan.."+sayan+"..deger.."+deger);System.out.println(getName()+"...yedek...paket"+yedpaket);	
		sayan=0;
		
	int ilk=deger.get(0);//System.out.println(getName()+".her yer dolu .."+deger.get(0));
	for(int m=0;m<deger.size();m++){
		if(ilk<=deger.get(m)){ilk=deger.get(m);
							 kod1=yol_kod [m][0];
							 kod2=yol_kod [m][1];//System.out.println(getName()+"..deger."+yol_kod [m][0]+".."+yol_kod [m][1]);
		}
	}
//	System.out.println(" 15 doldu gidiþ.."+getName()+paket.getKaynak()+"..sayac.."+sayan);
//				System.out.println("son.."+getName()+paket.getKaynak()+"..enerji.."+paket_hold.getEnergy());	

//	holdIn("tamam", INFINITY);

//System.out.println(getName()+".paketttt.."+paket_hold);
	for(int z=0;z<tabu.size();z++){
		if(tabu.get(z).getYolu()==kod1 && tabu.get(z).getId()==kod2 ){
		
	//		System.out.println(getName()+"..yol....TABU.."+tabu.get(z).getKaynak()+"..YEDEH.."+yedpaket.get(s));
		
		paket_forward.addAll(yedpaket);
		paketim.setSonraki_düðüm(tabu.get(z).getSonraki_düðüm());
		yedekforward=tabu.get(z).getSonraki_düðüm();
		yedpaket.removeAllElements();
		holdIn("data_forward", iþlem_zamaný);
	
	
		}
	}
	}
/*	else if(paket_hold.getAdi().startsWith("mesaj")&&(paket_hold.getX()==ip.getIntegerAddress()-65536 || paket_hold.getY()==ip.getIntegerAddress() ))
	{System.out.println(getName()+".gelen.."+(ip.getIntegerAddress()-65536)+"......"+paket_hold.getX()+"....."+paket_hold.getY());
		if(paket_hold.getX()==ip.getIntegerAddress()-65536){
			ip.setByte3(0);
			System.out.println(getName()+".paketttt.."+ip);
			holdIn("mesaj_iþlendi", iþlem_zamaný);
		}
		else if(paket_hold.getY()==ip.getIntegerAddress()){ 
			ip.setByte3(1);
			System.out.println(getName()+".paketttt.."+ip);
			holdIn("mesaj_iþlendi", iþlem_zamaný);
		}
	}*/
	else if (paket_hold.getAdi().startsWith("data")&& ip.getByte3()!=0 && paket_hold.getSonraki_düðüm().equals(ip)) {
	//	System.out.println(getName()+".paketttt..");
		boolean sifir=true;
		boolean tab=true;
		boolean adres=true;
		boolean deger=true;
		boolean t_adres=true;
	
		
		if(!tabu.isEmpty()){
			for (int i=0;i<tabu.size();i++){
				
				if(paket_hold.getAdi().equalsIgnoreCase(tabu.get(i).getKaynak())&& tabu.get(i).getId()!=tabu.get(i).getYolu()){
				
					paketim=new paketformat(paket_hold);
					paketim.setSonraki_düðüm(tabu.get(i).getSonraki_düðüm());
				

					tab=false;
	
					break;
					}
				else if(/*!tabu.get(i).getSonraki_düðüm().equals(new IPAddress(0,1,0,0))*/tabu.get(i).getId()!=tabu.get(i).getYolu()){
		///			System.out.println(getName()+"data gönderi..."+paket_hold.getAdi()+tab+"...tabu"+tabu.get(i).getKaynak());
					paketim=new paketformat(paket_hold);
					paketim.setSonraki_düðüm(tabu.get(i).getSonraki_düðüm());
					t_adres=false;
				}
			}
			}
		if(tab==true && !ip.equals(new IPAddress(0,1,0,0))){
		for (int i=0;i<yt.size();i++){
//			System.out.println(getName()+"...yt"+yt.get(i).getÖnceki_düðüm());
			if(yt.get(i).getÖnceki_düðüm().equals(new IPAddress(0,1,0,0))  ){
				paketim=new paketformat(paket_hold);
				paketim.setSonraki_düðüm(new IPAddress(0,1,0,0));
//				System.out.println(getName()+"...ssssssýfýr");
			
				sifir=false;

				
			}
			
			if ((yt.get(i).getÖnceki_düðüm().equals(paket_hold.getAdres()))){
				yedpaket.addElement(paket_hold);
				//			System.out.println(getName()+"yedekpaket..."+yedpaket);
							adres=false;
							

				}
			}
		if ((paket_hold.getAdres().equals(ip))){
			yedpaket.addElement(paket_hold);
	//		System.out.println(getName()+"yedekpaket..."+yedpaket);
			deger=false;
			

		}
		}
			
		if(ip.equals(new IPAddress(0,1,0,0))){
	//		System.out.println("..............DATA ULÞATI"+paket_hold.getAdi());
			paketim=new paketformat(paket_hold);
			holdIn("veri_alindi", iþlem_zamaný);
			paket_hold=null;
		}	
		else if((adres==false || deger==false) && sifir==false){
	//		System.out.println(getName()+"...DATA ALDI.."+paket_hold.getAdi()+"...sifir...");
			
			paket_hold=null;
					holdIn("data_gönder",iþlem_zamaný); 
			//		holdIn("tamam", INFINITY);	
		}
		else if(t_adres==true && sifir==false){
					System.out.println(getName()+"...DATA ALDI.."+paket_hold.getAdi()+"...sifir...");
					
					paket_hold=null;
							holdIn("data_gönder",iþlem_zamaný); 
					//		holdIn("tamam", INFINITY);	
				}
		else if(tab==false || t_adres==false){
//			System.out.println(getName()+"...data aldý.."+paket_hold.getAdi()+"....tabu....");	
					holdIn("data_al",iþlem_zamaný); 
					paket_hold=null;
	//				holdIn("tamam", INFINITY);	
		}
				
		else if((adres==false || deger==false) && sifir==true && !ip.equals(new IPAddress(0,1,0,0) ) ){
		//	System.out.println(getName()+"...BAÞLADI");
			
			rreqid.addElement(paket_hold.getAdi());
			
			Yol ya=new Yol((ip.getIntegerAddress()-65536),ip.getIntegerAddress()-65536,paket_hold.getAdi(),ip,new IPAddress(0,1,0,0),1,energy,sayac);
			tabu.addElement(ya);		
					
//					System.out.println(getName()+"...rreqid"+rreqid);
					
					paketim=new paketformat( RREQ_oluþtur((ip.getIntegerAddress()-65536),ip.getIntegerAddress()-65536,"IIZC",paket_hold.getAdi(),ip,energy,1));
				
					holdIn("IIZC_gönder", iþlem_zamaný);
					paket_hold=null;
		}
//		else 	holdIn("boþda", INFINITY);	//*******************boþdaddadda	
		
		
		}

	
	else if (paket_hold.getAdi().equals("IIZC")&& ip.getByte3()!=0 && !ip.equals(new IPAddress(0,1,0,0))&& rreqch()==true ) {
		
		
		boolean sonuc=true;
		boolean rreph=true;
		enr=energy+paket_hold.getEnergy();

		
//		System.out.println(getName()+"...IIZC:"+paket_hold.getAdres());
		rreqid.addElement(paket_hold.getKaynak());
	
		
		
		
		
		for (int i=0;i<yt.size();i++){
			if(yt.get(i).getÖnceki_düðüm().equals(new IPAddress(0,1,0,0))){
				rreph=false;
	//			System.out.println(getName()+"..."+paket.getId()+"rrepgönder."+rehedef);
		//		holdIn("boþda", INFINITY);
				holdIn("GIZC_gönder", iþlem_zamaný);
				break;	
			}
			else if(paket_hold.getMaliyet()>=15){
				//System.out.println(getName()+"...15 i geçti"); 
				holdIn("tamam", INFINITY);
				}
			else {sonuc=false;
	//		System.out.println(getName()+"..."+paket_hold.getId()+"rreqalýndý..kaynak.."+paket_hold.getKaynak());
				holdIn("IIZC_alýndý", iþlem_zamaný);	
				}								
		}
		
	if(rreph==false ){
	
		

		Yol ya=new Yol(paket_hold.getYolu(),ip.getIntegerAddress()-65536,paket_hold.getKaynak(),paket_hold.getAdres(),new IPAddress(0,1,0,0),paket_hold.getMaliyet(),enr,sayac);		
		tabu.addElement(ya);
		
		

		
				for(int i=0;i<tabu.size();i++)
				{
					if(tabu.get(i).getKaynak().equalsIgnoreCase(paket_hold.getKaynak()))
					hedefip=tabu.get(i).getÖnceki_düðüm();
		
				}
		
				
						
				
				
				
		paketim=new paketformat(RREP_oluþtur(paket_hold.getYolu(),ip.getIntegerAddress()-65536,"GIZC",paket_hold.getKaynak(),hedefip,ip,enr,paket_hold.getMaliyet()+1));
	
		paket_hold=null;
	
	}
	else if(sonuc==false){

		Yol ya=new Yol(paket_hold.getYolu(),paket_hold.getId(),paket_hold.getKaynak(),paket_hold.getAdres(),new IPAddress(0,1,0,0),paket_hold.getMaliyet(),enr,sayac);		
		tabu.addElement(ya);
		paketim=new paketformat(RREQ_oluþtur(paket_hold.getYolu(),paket_hold.getId(),paket_hold.getAdi(),paket_hold.getKaynak(),ip,enr,paket_hold.getMaliyet()+1));
//		System.out.println(getName()+"..TTL..."+paket_hold.getTtl());
		paket_hold=null;
	}
	
	
		
	
	}
	

		
	else if (paket_hold.getAdi().equals("GIZC")&& ip.getByte3()!=0 && !ip.equals(new IPAddress(0,1,0,0)) && paket_hold.getHedef().equals(ip) ) {
		boolean veri=true;
		boolean eki=true;
		
		for(int i=0;i<tabu.size();i++)
		{
			if(tabu.get(i).getYolu()==paket_hold.getYolu()){	
				if(tabu.get(i).getSonraki_düðüm().equals(new IPAddress(0,1,0,0))){
				tabu.get(i).setSonraki_düðüm(paket_hold.getAdres());
				tabu.get(i).setId(paket_hold.getId());
				hedefip=tabu.get(i).getÖnceki_düðüm();
			
				}
				else{
	//			System.out.println(getName()+"...hedef..."+hedefip+"adres.."+paket_hold.getAdres());
				eki=false;
				dene=tabu.get(i).getÖnceki_düðüm();
				}
				if(tabu.get(i).getÖnceki_düðüm().equals(ip)){ 
					veri = false;
	//				System.out.println(getName()+"...gelen yollar kaynak..."+paket_hold.getYolu()+"hedef.."+paket_hold.getId());
				
				}
				
			}
			
		}
			if(eki==false){
				Yol ek=new Yol(paket_hold.getYolu(),paket_hold.getId(),paket_hold.getKaynak(),dene,paket_hold.getAdres(),paket_hold.getMaliyet(),paket_hold.getEnergy(),sayac);		
				tabu.addElement(ek);
			}
			if(veri==true){		
			
					paketim=new paketformat(RREP_oluþtur(paket_hold.getYolu(),paket_hold.getId(),"GIZC",paket_hold.getKaynak(),hedefip,ip,paket_hold.getEnergy(),paket_hold.getMaliyet()));
			//		holdIn("boþda", INFINITY);
					holdIn("GIZC_gönder", iþlem_zamaný);
				
			} 
			else {/*
				for (int i=0;i<yedpaket.size();i++){
				if(paket_hold.getKaynak().equalsIgnoreCase(yedpaket.get(i).getAdi())){
					paketim=yedpaket.get(i);
					
				}
			}
			
				paketim.setSonraki_düðüm(paket_hold.getAdres());
				holdIn("data_forward", iþlem_zamaný);*/
	//			Yol ya=new Yol(paket_hold.getYolu(),paket_hold.getId(),paket_hold.getKaynak(),ip,paket_hold.getAdres(),paket_hold.getMaliyet(),paket_hold.getEnergy(),sayac);		
	//			tabu.addElement(ya);
				int son=paket_hold.getEnergy()/paket_hold.getMaliyet();
				deger.addElement(son);
				yol_kod[deger.size()-1][0]=paket_hold.getYolu();
				yol_kod[deger.size()-1][1]=paket_hold.getId();
			//	System.out.println(getName()+"...gelen yollar kaynak..."+yol_kod[deger.size()-1][0]+"hedef.."+yol_kod[deger.size()-1][1]+"..deger.."+son);
				sayan=sayan+1;
		//		System.out.println(getName()+"...sayanilk..."+sayan);
				phase = "BEKLEME";
				sor=false;
				}
		
		}
		
	
	else if (paket_hold.getAdi().startsWith("data") && ip.getByte3()==0 && paket_hold.getSonraki_düðüm().equals(ip)) {
		
		paketim=new paketformat(paket_hold);
		paketim.setSonraki_düðüm(yt.get(0).getÖnceki_düðüm());
	
	holdIn("data_güncellendi", iþlem_zamaný);				
			
	}//datason
	
	}

if(sor==true && sayan>=1)sayan=sayan+1;

//simulation_time--;

/*if (simulation_time % 50==0){
//	System.out.println("simulation_time..."+simulation_time);
	holdIn("Hello_gönder", iþlem_zamaný);
	}*/




	}
	

	public void deltcon(double e, message x) {
		deltext(0, x);
		deltint();
	}
		
	public message out() {
		message m = new message();
		
		if (phaseIs("kurulum")) {	
			m.add(makeContent("NIC-out", hello_oluþtur()));		
			m.add(makeContent("outEvent", hello_oluþtur()));
			
		}
		
		
/*		else if (phaseIs("Hello_gönder")) {	
		//	yt.removeAllElements();
			m.add(makeContent("NIC-out", hello_oluþtur()));		
			m.add(makeContent("outEvent", hello_oluþtur()));
		
	//		holdIn("Hello_gönder", iþlem_zamaný);
		}*/
		 else if (phaseIs("IIZC_gönder")){ 
//			 System.out.println(getName()+"...çýkýþBAÞLADI...paket"+paketim.getRehedef()+"..kaynak."+paketim.getKaynak());
			 
			 m.add(makeContent("NIC-out",new paketformat(paketim))); 
	//iizc++;
	//System.out.println(getName()+"..IIZC...."+iizc);
			 paket_hold=null;	 
		 }
		 else if (phaseIs("IIZC_alýndý")){ 
//			 System.out.println(getName()+"...çýkýþBAÞLADI...paket"+paketim.getRehedef()+"..kaynak."+paketim.getKaynak());
//			 System.out.println(getName()+"...DEVAMI...paket"+paketim.getRehedef()+"..kaynak."+paketim.getKaynak());
			 m.add(makeContent("NIC-out",new paketformat(paketim))); 
		
			 paket_hold=null; 
		 }
		
		 else if (phaseIs("GIZC_gönder")){ 
//			 System.out.println(getName()+"...BAÞLADIRREP...paket"+new paketformat(paket));
		//	 System.out.println(getName()+"..TTL..."+paketim.getHedef());
			 m.add(makeContent("NIC-out",new paketformat(paketim))); 
		gizc++;
	//	System.out.println(getName()+"..GIZC...."+gizc);
			 paket_hold=null; 
		 }
		
		
		
		 else if (phaseIs("data_forward")){ 
//			 System.out.println(getName()+"...data ilk...paket"+new paketformat(paket));
			 deger.removeAllElements();
		//	 System.out.println(getName()+"...paket...paket"+paket_forward);
		//	 yol_kod.removeAllElements();
			 for(int z=0;z<paket_forward.size();z++){
			paketim=new paketformat(paket_forward.get(z));
			paketim.setSonraki_düðüm(yedekforward);//System.out.println(getName()+"...paktim"+paketim+"..sonraki"+paketim.getSonraki_düðüm());
			 m.add(makeContent("NIC-out",new paketformat(paketim))); 
			 }
			 paket_forward.removeAllElements();
		
			 paket_hold=null;	 
		 }
		 else if (phaseIs("data_al")){ 
	//		 System.out.println(getName()+"çýkýþ...data...paket"+new paketformat(paketim));
		//	 System.out.println(getName()+"...giden...paket"+paketim);
			 m.add(makeContent("NIC-out",new paketformat(paketim)));
		
			 paket_hold=null;
			 }
		 else if (phaseIs("data_gönder")){ 
//			 System.out.println(getName()+"...data...paket"+new paketformat(paketim));
			 
			 m.add(makeContent("NIC-out",new paketformat(paketim)));
		
			 paket_hold=null;
			 }
		 else if (phaseIs("data_güncellendi")){ 
		
		 m.add(makeContent("NIC-out",new paketformat(paketim))); 
		
		 paket_hold=null;
		 }
		
		 else if (phaseIs("veri_alindi")){ 
			 
			 m.add(makeContent("outEvent",new paketformat(paketim))); 
			
			 paket_hold=null;
		 }
			 
		return m;

	}

	public String getTooltipText() {

		return "IP : " +ip+ "<br>"+
				"YT: " +"<br>"
				+"---"+"Yol"+"---"+"id"+"---"+"Ýzci.id"+"---"+ "önceki_düðüm"+ "-----"+"Sonraki_düðüm"+"-----"+"hop sayýsý"+"----"+"Enerji"+"---"+"Ömür"+ "<br>"
				+write()+
				"Komþu Tablosu: " +"<br>"
				+ "izci.id tarihi..:" 
				+yaz()+"<br>"+
		"Komþu Tablosu: "+"<br>"
		+ "hedef düðüm"+ "....."+ "Sonra adým" +"....."+"hop sayýsý"+"...."+"çýkýþ portu"+ "<br>"
		+ciz();
	}

	
	private paketformat hello_oluþtur() {
		paket_hareket=1;
		paketformat p = new paketformat("hello"+ip);
		p.setAdi("hello");
		p.setAdres(ip);
		p.setId(yt.size());
		p.setSonraki_düðüm(ip);
		p.setMaliyet(paket_hareket);
		p.setData(ip);
	
		return p;
	}

	private paketformat enerji_gönder(){
		paketformat p = new paketformat("enerji"+ip);
		p.setAdi("enerji");
		p.setAdres(ip);
		p.setEnergy(energy);
		return p;
	}
	
	private paketformat RREQ_oluþtur(int yol,int idd,String IIZC,String kayna, IPAddress adre,int renerg,int mali) {
		paketformat p = new paketformat("IIZC");
		p.setYolu(yol);
		p.setId(idd);
		p.setAdi(IIZC);
		p.setKaynak(kayna);
		p.setAdres(adre);
		p.setEnergy(renerg);
		p.setMaliyet(mali);
		
		return p;
	}
	

	private paketformat RREP_oluþtur(int yol,int idd,String GIZC,String kayna,IPAddress hede, IPAddress adre,int energ,int mali) {
		paketformat p = new paketformat("GIZC");
		p.setId(idd);
		p.setAdi(GIZC);
		p.setKaynak(kayna);
		p.setAdres(adre);
		p.setHedef(hede);
		p.setEnergy(energ);
		p.setYolu(yol);
		p.setMaliyet(mali);
	
		return p;
		
	}


	public String yaz(){
		String str=" ";
		Enumeration e=rreqid.elements();
		while(e.hasMoreElements()){
			str+=e.nextElement().toString();	
		}
		return str;
		
	}	
	public String write(){
		String str=" ";
		Enumeration e=tabu.elements();
		while(e.hasMoreElements()){
			str+=e.nextElement().toString()+"\n";	
		}
		return str;
		
	}	
	public String ciz(){
		String str=" ";
		Enumeration e=yt.elements();
		while(e.hasMoreElements()){
			str+=e.nextElement().toString()+"\n";	
		}
		return str;
		
	}	
	public boolean rreqch(){
		boolean m=true;
//		c.out.println(getName()+"...PAKET KAYNAK.."+paket_hold.getKaynak());
		for (int i=0;i<rreqid.size();i++){
			if (rreqid.get(i).equalsIgnoreCase(paket_hold.getKaynak())){	
				m=false;
				
				break;
			}
		}
		return m;
	}
	
public void tabutemizle(){

	sayac=simulation_time;
	
	
/*		for(int i=0;i<tabu.size();i++){
		
			if(say-tabu.get(i).getLife()>100){
			
			tabu.remove(i);
			
						
			} 
			else continue;
		
		}*/
		
}

public void yttemizle(){
	for(int i=0;i<yt.size();i++){
		if(yt.isEmpty()) break;
		else if(yt.get(i).getLife()>say+30)yt.remove(i);
		else continue;
	}
}
}