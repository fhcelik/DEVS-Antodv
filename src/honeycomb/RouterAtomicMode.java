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
	private double i�lem_zaman�;
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
	
	public RouterAtomicMode(String name, IPAddress ipadres, double i�lemzaman�,double sim_time, int energ) {
		super(name);
		ip = ipadres;
		i�lem_zaman� = i�lemzaman�;
		energy=energ;
		addTestInput("NIC-in", new paketformat("packet_data", new IPAddress(0, 0, 0, 9)));
		simulation_time = 10000;
		
		
		
	}

	public RouterAtomicMode() {
		this("Y�nlendirici", new IPAddress(0, 0, 0, 1), 1,10100,1);
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
				

				
				paket.setC�k��_port("NIC-out");
				
				if(paket.getAdi().startsWith("data")){
						if (PN.equals("inEvent")){
							if (paket.getAdres().equals(ip)) {
					//			System.out.println(getName()+".detex.tttttinevent.."+paket.getAdi());
				              paket.setC�k��_port("outEvent");
				              if(queue.enqueue(new paketformat(paket)))
				              holdIn("data_al�nd�", i�lem_zaman�);
				            }
						
					}
						else if(PN.equals("NIC-in")){
							if(queue.enqueue(new paketformat(paket)))
					//			System.out.println(getName()+".detex.tttttinevent.."+paket.getAdi());		
							 holdIn("data_al�nd�", i�lem_zaman�);
					}
						
						
					}
				
				else if(paket.getAdi().equalsIgnoreCase("hello")){
					
							if(queue.enqueue(new paketformat(paket)))
							holdIn("hello_al�nd�", i�lem_zaman�);
				}
				
				else if(paket.getAdi().equalsIgnoreCase("IIZC") && ip.getByte3()!=0){
				
		
							if(queue.enqueue(new paketformat(paket)))

							holdIn("iizc_al�nd�", i�lem_zaman�);
 					}
				
				else if(paket.getAdi().equalsIgnoreCase("GIZC") && ip.getByte3()!=0){
					
							if(queue.enqueue(new paketformat(paket))){
					//			System.out.println(getName()+".detex.tttttinevent.."+paket.getAdi()+"adres0"+paket.getHedef()+"adres"+paket.getAdres());
								holdIn("gizc_al�nd�", i�lem_zaman�);}
							
					}
				else if(paket.getAdi().equalsIgnoreCase("mesaj")){
					
					if(queue.enqueue(new paketformat(paket))){
					holdIn("mesaj_al�nd�", i�lem_zaman�);}
		}
				
				
//				}
/*				if (PN.equals("inEvent")) {
		          
					paket = new paketformat((paketformat) x.getValOnPort("inEvent", i));
					
					if (paket.getAdres().equals(ip)) {
					System.out.println(getName()+".detex.tttttinevent.."+paket.getAdi());
		              paket.setC�k��_port("outEvent");
		              kuyruk.addElement(paket);
		              
		              phase="paket_al�nd�";
		      				
		      		
		            }
		           
		          }*/
			
				}
				}
		}
		
			}
			
public void deltint() {
	
sor=true;
 //if(energy<50 && !getName().equalsIgnoreCase("Node_0")) holdIn("bo�da", INFINITY);

if(queue.isEmpty() && deger.isEmpty())
	holdIn("bo�da", INFINITY);

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
		paketim.setSonraki_d���m(tabu.get(z).getSonraki_d���m());
		yedekforward=tabu.get(z).getSonraki_d���m();
		yedpaket.removeAllElements();
		holdIn("data_forward", i�lem_zaman�);
	
	
		}
	}
	}
	else{ holdIn("BEKLEME", i�lem_zaman�);}
	}

else {	

	paket_hold=new paketformat(queue.dequeue());
	
	
	if (paket_hold.getAdi().equals("hello")) {	

		
	
		Yol y=new Yol(0,paket_hold.getId(),paket_hold.getAdi(),new IPAddress(paket_hold.getAdres().getByte4(),paket_hold.getAdres().getByte3(),paket_hold.getAdres().getByte2(),paket_hold.getAdres().getByte1()),new IPAddress(paket_hold.getAdres().getByte4(),paket_hold.getAdres().getByte3(),paket_hold.getAdres().getByte2(),paket_hold.getAdres().getByte1()),paket_hold.getMaliyet(),paket_hold.getEnergy(),sayac);		
		yt.addElement(y);
		holdIn("kom�u_eklendi", i�lem_zaman�);	
	
	
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
//	System.out.println(" 15 doldu gidi�.."+getName()+paket.getKaynak()+"..sayac.."+sayan);
//				System.out.println("son.."+getName()+paket.getKaynak()+"..enerji.."+paket_hold.getEnergy());	

//	holdIn("tamam", INFINITY);

//System.out.println(getName()+".paketttt.."+paket_hold);
	for(int z=0;z<tabu.size();z++){
		if(tabu.get(z).getYolu()==kod1 && tabu.get(z).getId()==kod2 ){
		
	//		System.out.println(getName()+"..yol....TABU.."+tabu.get(z).getKaynak()+"..YEDEH.."+yedpaket.get(s));
		
		paket_forward.addAll(yedpaket);
		paketim.setSonraki_d���m(tabu.get(z).getSonraki_d���m());
		yedekforward=tabu.get(z).getSonraki_d���m();
		yedpaket.removeAllElements();
		holdIn("data_forward", i�lem_zaman�);
	
	
		}
	}
	}
/*	else if(paket_hold.getAdi().startsWith("mesaj")&&(paket_hold.getX()==ip.getIntegerAddress()-65536 || paket_hold.getY()==ip.getIntegerAddress() ))
	{System.out.println(getName()+".gelen.."+(ip.getIntegerAddress()-65536)+"......"+paket_hold.getX()+"....."+paket_hold.getY());
		if(paket_hold.getX()==ip.getIntegerAddress()-65536){
			ip.setByte3(0);
			System.out.println(getName()+".paketttt.."+ip);
			holdIn("mesaj_i�lendi", i�lem_zaman�);
		}
		else if(paket_hold.getY()==ip.getIntegerAddress()){ 
			ip.setByte3(1);
			System.out.println(getName()+".paketttt.."+ip);
			holdIn("mesaj_i�lendi", i�lem_zaman�);
		}
	}*/
	else if (paket_hold.getAdi().startsWith("data")&& ip.getByte3()!=0 && paket_hold.getSonraki_d���m().equals(ip)) {
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
					paketim.setSonraki_d���m(tabu.get(i).getSonraki_d���m());
				

					tab=false;
	
					break;
					}
				else if(/*!tabu.get(i).getSonraki_d���m().equals(new IPAddress(0,1,0,0))*/tabu.get(i).getId()!=tabu.get(i).getYolu()){
		///			System.out.println(getName()+"data g�nderi..."+paket_hold.getAdi()+tab+"...tabu"+tabu.get(i).getKaynak());
					paketim=new paketformat(paket_hold);
					paketim.setSonraki_d���m(tabu.get(i).getSonraki_d���m());
					t_adres=false;
				}
			}
			}
		if(tab==true && !ip.equals(new IPAddress(0,1,0,0))){
		for (int i=0;i<yt.size();i++){
//			System.out.println(getName()+"...yt"+yt.get(i).get�nceki_d���m());
			if(yt.get(i).get�nceki_d���m().equals(new IPAddress(0,1,0,0))  ){
				paketim=new paketformat(paket_hold);
				paketim.setSonraki_d���m(new IPAddress(0,1,0,0));
//				System.out.println(getName()+"...ssssss�f�r");
			
				sifir=false;

				
			}
			
			if ((yt.get(i).get�nceki_d���m().equals(paket_hold.getAdres()))){
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
	//		System.out.println("..............DATA UL�ATI"+paket_hold.getAdi());
			paketim=new paketformat(paket_hold);
			holdIn("veri_alindi", i�lem_zaman�);
			paket_hold=null;
		}	
		else if((adres==false || deger==false) && sifir==false){
	//		System.out.println(getName()+"...DATA ALDI.."+paket_hold.getAdi()+"...sifir...");
			
			paket_hold=null;
					holdIn("data_g�nder",i�lem_zaman�); 
			//		holdIn("tamam", INFINITY);	
		}
		else if(t_adres==true && sifir==false){
					System.out.println(getName()+"...DATA ALDI.."+paket_hold.getAdi()+"...sifir...");
					
					paket_hold=null;
							holdIn("data_g�nder",i�lem_zaman�); 
					//		holdIn("tamam", INFINITY);	
				}
		else if(tab==false || t_adres==false){
//			System.out.println(getName()+"...data ald�.."+paket_hold.getAdi()+"....tabu....");	
					holdIn("data_al",i�lem_zaman�); 
					paket_hold=null;
	//				holdIn("tamam", INFINITY);	
		}
				
		else if((adres==false || deger==false) && sifir==true && !ip.equals(new IPAddress(0,1,0,0) ) ){
		//	System.out.println(getName()+"...BA�LADI");
			
			rreqid.addElement(paket_hold.getAdi());
			
			Yol ya=new Yol((ip.getIntegerAddress()-65536),ip.getIntegerAddress()-65536,paket_hold.getAdi(),ip,new IPAddress(0,1,0,0),1,energy,sayac);
			tabu.addElement(ya);		
					
//					System.out.println(getName()+"...rreqid"+rreqid);
					
					paketim=new paketformat( RREQ_olu�tur((ip.getIntegerAddress()-65536),ip.getIntegerAddress()-65536,"IIZC",paket_hold.getAdi(),ip,energy,1));
				
					holdIn("IIZC_g�nder", i�lem_zaman�);
					paket_hold=null;
		}
//		else 	holdIn("bo�da", INFINITY);	//*******************bo�daddadda	
		
		
		}

	
	else if (paket_hold.getAdi().equals("IIZC")&& ip.getByte3()!=0 && !ip.equals(new IPAddress(0,1,0,0))&& rreqch()==true ) {
		
		
		boolean sonuc=true;
		boolean rreph=true;
		enr=energy+paket_hold.getEnergy();

		
//		System.out.println(getName()+"...IIZC:"+paket_hold.getAdres());
		rreqid.addElement(paket_hold.getKaynak());
	
		
		
		
		
		for (int i=0;i<yt.size();i++){
			if(yt.get(i).get�nceki_d���m().equals(new IPAddress(0,1,0,0))){
				rreph=false;
	//			System.out.println(getName()+"..."+paket.getId()+"rrepg�nder."+rehedef);
		//		holdIn("bo�da", INFINITY);
				holdIn("GIZC_g�nder", i�lem_zaman�);
				break;	
			}
			else if(paket_hold.getMaliyet()>=15){
				//System.out.println(getName()+"...15 i ge�ti"); 
				holdIn("tamam", INFINITY);
				}
			else {sonuc=false;
	//		System.out.println(getName()+"..."+paket_hold.getId()+"rreqal�nd�..kaynak.."+paket_hold.getKaynak());
				holdIn("IIZC_al�nd�", i�lem_zaman�);	
				}								
		}
		
	if(rreph==false ){
	
		

		Yol ya=new Yol(paket_hold.getYolu(),ip.getIntegerAddress()-65536,paket_hold.getKaynak(),paket_hold.getAdres(),new IPAddress(0,1,0,0),paket_hold.getMaliyet(),enr,sayac);		
		tabu.addElement(ya);
		
		

		
				for(int i=0;i<tabu.size();i++)
				{
					if(tabu.get(i).getKaynak().equalsIgnoreCase(paket_hold.getKaynak()))
					hedefip=tabu.get(i).get�nceki_d���m();
		
				}
		
				
						
				
				
				
		paketim=new paketformat(RREP_olu�tur(paket_hold.getYolu(),ip.getIntegerAddress()-65536,"GIZC",paket_hold.getKaynak(),hedefip,ip,enr,paket_hold.getMaliyet()+1));
	
		paket_hold=null;
	
	}
	else if(sonuc==false){

		Yol ya=new Yol(paket_hold.getYolu(),paket_hold.getId(),paket_hold.getKaynak(),paket_hold.getAdres(),new IPAddress(0,1,0,0),paket_hold.getMaliyet(),enr,sayac);		
		tabu.addElement(ya);
		paketim=new paketformat(RREQ_olu�tur(paket_hold.getYolu(),paket_hold.getId(),paket_hold.getAdi(),paket_hold.getKaynak(),ip,enr,paket_hold.getMaliyet()+1));
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
				if(tabu.get(i).getSonraki_d���m().equals(new IPAddress(0,1,0,0))){
				tabu.get(i).setSonraki_d���m(paket_hold.getAdres());
				tabu.get(i).setId(paket_hold.getId());
				hedefip=tabu.get(i).get�nceki_d���m();
			
				}
				else{
	//			System.out.println(getName()+"...hedef..."+hedefip+"adres.."+paket_hold.getAdres());
				eki=false;
				dene=tabu.get(i).get�nceki_d���m();
				}
				if(tabu.get(i).get�nceki_d���m().equals(ip)){ 
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
			
					paketim=new paketformat(RREP_olu�tur(paket_hold.getYolu(),paket_hold.getId(),"GIZC",paket_hold.getKaynak(),hedefip,ip,paket_hold.getEnergy(),paket_hold.getMaliyet()));
			//		holdIn("bo�da", INFINITY);
					holdIn("GIZC_g�nder", i�lem_zaman�);
				
			} 
			else {/*
				for (int i=0;i<yedpaket.size();i++){
				if(paket_hold.getKaynak().equalsIgnoreCase(yedpaket.get(i).getAdi())){
					paketim=yedpaket.get(i);
					
				}
			}
			
				paketim.setSonraki_d���m(paket_hold.getAdres());
				holdIn("data_forward", i�lem_zaman�);*/
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
		
	
	else if (paket_hold.getAdi().startsWith("data") && ip.getByte3()==0 && paket_hold.getSonraki_d���m().equals(ip)) {
		
		paketim=new paketformat(paket_hold);
		paketim.setSonraki_d���m(yt.get(0).get�nceki_d���m());
	
	holdIn("data_g�ncellendi", i�lem_zaman�);				
			
	}//datason
	
	}

if(sor==true && sayan>=1)sayan=sayan+1;

//simulation_time--;

/*if (simulation_time % 50==0){
//	System.out.println("simulation_time..."+simulation_time);
	holdIn("Hello_g�nder", i�lem_zaman�);
	}*/




	}
	

	public void deltcon(double e, message x) {
		deltext(0, x);
		deltint();
	}
		
	public message out() {
		message m = new message();
		
		if (phaseIs("kurulum")) {	
			m.add(makeContent("NIC-out", hello_olu�tur()));		
			m.add(makeContent("outEvent", hello_olu�tur()));
			
		}
		
		
/*		else if (phaseIs("Hello_g�nder")) {	
		//	yt.removeAllElements();
			m.add(makeContent("NIC-out", hello_olu�tur()));		
			m.add(makeContent("outEvent", hello_olu�tur()));
		
	//		holdIn("Hello_g�nder", i�lem_zaman�);
		}*/
		 else if (phaseIs("IIZC_g�nder")){ 
//			 System.out.println(getName()+"...��k��BA�LADI...paket"+paketim.getRehedef()+"..kaynak."+paketim.getKaynak());
			 
			 m.add(makeContent("NIC-out",new paketformat(paketim))); 
	//iizc++;
	//System.out.println(getName()+"..IIZC...."+iizc);
			 paket_hold=null;	 
		 }
		 else if (phaseIs("IIZC_al�nd�")){ 
//			 System.out.println(getName()+"...��k��BA�LADI...paket"+paketim.getRehedef()+"..kaynak."+paketim.getKaynak());
//			 System.out.println(getName()+"...DEVAMI...paket"+paketim.getRehedef()+"..kaynak."+paketim.getKaynak());
			 m.add(makeContent("NIC-out",new paketformat(paketim))); 
		
			 paket_hold=null; 
		 }
		
		 else if (phaseIs("GIZC_g�nder")){ 
//			 System.out.println(getName()+"...BA�LADIRREP...paket"+new paketformat(paket));
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
			paketim.setSonraki_d���m(yedekforward);//System.out.println(getName()+"...paktim"+paketim+"..sonraki"+paketim.getSonraki_d���m());
			 m.add(makeContent("NIC-out",new paketformat(paketim))); 
			 }
			 paket_forward.removeAllElements();
		
			 paket_hold=null;	 
		 }
		 else if (phaseIs("data_al")){ 
	//		 System.out.println(getName()+"��k��...data...paket"+new paketformat(paketim));
		//	 System.out.println(getName()+"...giden...paket"+paketim);
			 m.add(makeContent("NIC-out",new paketformat(paketim)));
		
			 paket_hold=null;
			 }
		 else if (phaseIs("data_g�nder")){ 
//			 System.out.println(getName()+"...data...paket"+new paketformat(paketim));
			 
			 m.add(makeContent("NIC-out",new paketformat(paketim)));
		
			 paket_hold=null;
			 }
		 else if (phaseIs("data_g�ncellendi")){ 
		
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
				+"---"+"Yol"+"---"+"id"+"---"+"�zci.id"+"---"+ "�nceki_d���m"+ "-----"+"Sonraki_d���m"+"-----"+"hop say�s�"+"----"+"Enerji"+"---"+"�m�r"+ "<br>"
				+write()+
				"Kom�u Tablosu: " +"<br>"
				+ "izci.id tarihi..:" 
				+yaz()+"<br>"+
		"Kom�u Tablosu: "+"<br>"
		+ "hedef d���m"+ "....."+ "Sonra ad�m" +"....."+"hop say�s�"+"...."+"��k�� portu"+ "<br>"
		+ciz();
	}

	
	private paketformat hello_olu�tur() {
		paket_hareket=1;
		paketformat p = new paketformat("hello"+ip);
		p.setAdi("hello");
		p.setAdres(ip);
		p.setId(yt.size());
		p.setSonraki_d���m(ip);
		p.setMaliyet(paket_hareket);
		p.setData(ip);
	
		return p;
	}

	private paketformat enerji_g�nder(){
		paketformat p = new paketformat("enerji"+ip);
		p.setAdi("enerji");
		p.setAdres(ip);
		p.setEnergy(energy);
		return p;
	}
	
	private paketformat RREQ_olu�tur(int yol,int idd,String IIZC,String kayna, IPAddress adre,int renerg,int mali) {
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
	

	private paketformat RREP_olu�tur(int yol,int idd,String GIZC,String kayna,IPAddress hede, IPAddress adre,int energ,int mali) {
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