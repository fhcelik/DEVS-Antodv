package ospff;
import java.util.Enumeration;
import java.util.Vector;
import model.modeling.message;
import view.modeling.ViewableAtomic;

public class RouterAtomicMode extends ViewableAtomic {

	
	private int port;
	private IPAddress ip;
	private double i�lem_zaman�;
	
	private Vector<Yol> yt;
	
	private Vector<paketformat> kuyruk;
	private paketformat paket;
	private boolean sonuc=true;
	private int paket_hareket;
	private String portadi;
	
	int portno;

	public RouterAtomicMode(String name, IPAddress ipadres, double i�lemzaman�,
			int port_sayisi) {
		super(name);
		ip = ipadres;
		i�lem_zaman� = i�lemzaman�;
		port = port_sayisi;
		addInport("inEvent");
		addOutport("outEvent");
		for (portno = 0; portno < port; portno++) {
			addInport("eternet_giri�i" + portno);
			addOutport("eternet_��k���" + portno);
		}
		for (portno = 0; portno < port; portno++) {
		addTestInput("eternet_girisi" + portno, new paketformat("data"));
		}
	}

	public RouterAtomicMode() {
		this("Y�nlendirici", new IPAddress(0, 0, 0, 1), 1, 1);
	}

	public void initialize() {
		phase = "kurulum";
		sigma = 0;
		super.initialize();
		yt = new Vector<Yol>();
	
		kuyruk = new Vector<paketformat>();
		
	}

	public void deltext(double e, message x) {
		Continue(e);
		for (int i = 0; i < x.getLength(); i++) {			
			for (int k = 0; k < getNumInports(); k++) {
				String PN=(String)getInportNames().get(k);
				if (messageOnPort(x, PN, i)){
					 if (!PN.equals("inEvent")){						 
					paket = (paketformat) x.getValOnPort(PN,
							i);
					
				paket.setC�k��_port("eternet_��k���"+PN.substring(14));
				kuyruk.addElement(paket);
				
				holdIn("paket_al�nd�", i�lem_zaman�);
				
				}
				
				
				if (PN.equals("inEvent")) {
		          
					paket = (paketformat) x.getValOnPort("inEvent", i);
				//	System.out.println("Paket :"+paket.getHedef());
					if (paket.getAdres().equals(ip)) {
		            	 
		              paket.setC�k��_port("outevent");
						kuyruk.addElement(paket);
		              holdIn("paket_al�nd�", i�lem_zaman�);
		              
		            }
		            continue;
		          }
				}
				}
		}
			}
		
			
		



	public void deltint() {
	
if(kuyruk.isEmpty())
	holdIn("bo�da", INFINITY);
else  	{
	paket=kuyruk.remove(0);

	if (paket.getAdi().equals("hello")) {
		//System.out.println("�nce"+getName()+"  "+paket.getAdres());
		if (paket.getAdres().getByte2()!=ip.getByte2()){	
			paket.setId("bgp");
		Yol y=new Yol(paket.getId(),new IPAddress(paket.getAdres().getByte4(),paket.getAdres().getByte3(),paket.getAdres().getByte2(),paket.getAdres().getByte1()),new IPAddress(paket.getAdres().getByte4(),paket.getAdres().getByte3(),paket.getAdres().getByte2(),paket.getAdres().getByte1()),paket.getMaliyet(),paket.getC�k��_port());		
		yt.addElement(y);
		
		}
		else {
			Yol y=new Yol(paket.getId(),paket.getAdres(),paket.getAdres(),paket.getMaliyet(),paket.getC�k��_port());
			yt.addElement(y);}
	//	System.out.println(y);
		//	System.out.println(paket.getAdres());
				int a=1;
			
				if (getNumInports()-2>a++)holdIn("kom�u_eklendi", i�lem_zaman�);
				else holdIn("rip_g�nderme", i�lem_zaman�);
				
		} 
	else if (paket.getAdi().equals("RIP")) {
		Vector<Yol> v=(Vector<Yol>)(paket.getData());
		//System.out.println(paket.get);
	//		System.out.println("sonra"+getName()+"  "+paket.getMaliyet());
			for (int i=0;i<v.size();i++){	
				for (int t=0;t<yt.size();t++){		
				if (v.get(i).getHedef()==yt.get(t).getHedef()|| v.get(i).getHedef()==ip ) {
					sonuc=false;
					break;
					
				}
				if (v.get(i).getId()=="bgp" && v.get(i).getHedef().getByte2()==yt.get(t).getHedef().getByte2()&& v.get(i).getMaliyet()>yt.get(t).getMaliyet() ) {
					sonuc=false;
					break;
					
				}
				
				else { sonuc=true;
						
				}
				
				
				 

			}
				if (sonuc==true && v.get(i).getId()=="bgp"){
				
				paket.setMaliyet(v.get(i).getMaliyet()+1);
				Yol y=new Yol(v.get(i).getId(),v.get(i).getHedef(),paket.getAdres(),paket.getMaliyet(),paket.getC�k��_port());
				yt.addElement(y);
				holdIn("RIP_g�ncellendi", i�lem_zaman�);		
				
				}
			else if(sonuc==true && paket.getAdres().getByte2()==ip.getByte2()){
					paket.setMaliyet(v.get(i).getMaliyet()+1);
					Yol y=new Yol(v.get(i).getId(),v.get(i).getHedef(),paket.getAdres(),paket.getMaliyet(),paket.getC�k��_port());
					yt.addElement(y);
					
					holdIn("RIP_g�ncellendi", i�lem_zaman�);	
				}
			}
			
		}
			

	
	
	
	
	else if (paket.getAdi().startsWith("data")) {
	
		boolean c=false;
				if (paket.getHedef().equals(ip)) holdIn("data_alindi", i�lem_zaman�);
				else{ for (int i=0;i<yt.size();i++){
					if (paket.getHedef().equals(yt.get(i).getHedef()))
					{
					
					portadi= yt.get(i).getPort_isim();
				
					c=true;
					break;
					
					}}
					if(c==false){for (int i=0;i<yt.size();i++){
				 if ((paket.getHedef().getByte2()==yt.get(i).getHedef().getByte2())){
						
						portadi= yt.get(i).getPort_isim();
						c=true;
						break;
					}}}
				/*	if(c==false){
						for (int i=0;i<yt.size();i++){
						if (ip.getByte2()!= yt.get(i).getHedef().getByte2()){
						portadi= yt.get(i).getPort_isim();
						
					}}}*/
						
				
					holdIn("data_g�ncellendi", i�lem_zaman�);
				
	}}}
	
}
	public void deltcon(double e, message x) {
		deltext(0, x);
		deltint();
	}

	public message out() {
		message m = new message();

		if (phaseIs("kurulum")) {
			for (int k = 0; k < getNumInports()-1; k++) {
				m.add(makeContent("eternet_��k���" + k, hello_olu�tur()));
				
			}
			m.add(makeContent("outEvent", hello_olu�tur()));

		} else if (phaseIs("rip_g�nderme")) {

			for (int k = 0; k < getNumInports()-1; k++) {
				
				m.add(makeContent("eternet_��k���" + k, RIP_olu�tur()));

			}
		} else if (phaseIs("RIP_g�ncellendi")) {

			for (int k = 0; k < getNumInports()-1; k++) {
				m.add(makeContent("eternet_��k���" + k, RIP_olu�tur()));
			}
		}
	
		
		 else if (phaseIs("data_g�ncellendi")){ 
			
		 m.add(makeContent(portadi,paket)); }
		
		 else if (phaseIs("data_alindi")){ 
			//	System.out.println(ip+"data g�nderiliyor");
			 m.add(makeContent("outEvent",paket)); }
			 
		return m;

	}

	public String getTooltipText() {

		return "IP : " +ip+ "<br>"+
				"Kom�u Tablosu: " +"<br>"
				+ "hedef d���m"+ "....."+ "sonraki ad�m" +"....."+"hop say�s�"+"...."+"��k�� portu"+ "<br>"
				+yaz();
				
	}

	
	private paketformat hello_olu�tur() {
		paket_hareket=1;
		paketformat p = new paketformat("hello");
		p.setAdi("hello");
		p.setAdres(ip);
		p.setSonraki_d���m(ip);
		p.setMaliyet(paket_hareket);
		p.setData(ip);
		return p;
	}
	
	private paketformat data_olu�tur() {
		paketformat p = new paketformat("data");
		p.setAdi("data");
		p.setAdres(paket.getAdres());
		p.setHedef(paket.getHedef());
		p.setData(paket.getData());
		
	
		return p;
	}
	private paketformat RIP_olu�tur() {
		paketformat p = new paketformat("RIP");
		p.setAdi("RIP");
		p.setAdres(ip);
		p.setData(yt);
	//	p.setMaliyet(paket_hareket++);
		return p;
	}

	public String yaz(){
		String str=" ";
		Enumeration e=yt.elements();
		while(e.hasMoreElements()){
			str+=e.nextElement().toString()+"\n";	
		}
		return str;
		
	}	
	

}