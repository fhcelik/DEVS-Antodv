package ospff;
import java.util.Enumeration;
import java.util.Vector;
import model.modeling.message;
import view.modeling.ViewableAtomic;

public class RouterAtomicMode extends ViewableAtomic {

	
	private int port;
	private IPAddress ip;
	private double iþlem_zamaný;
	
	private Vector<Yol> yt;
	
	private Vector<paketformat> kuyruk;
	private paketformat paket;
	private boolean sonuc=true;
	private int paket_hareket;
	private String portadi;
	
	int portno;

	public RouterAtomicMode(String name, IPAddress ipadres, double iþlemzamaný,
			int port_sayisi) {
		super(name);
		ip = ipadres;
		iþlem_zamaný = iþlemzamaný;
		port = port_sayisi;
		addInport("inEvent");
		addOutport("outEvent");
		for (portno = 0; portno < port; portno++) {
			addInport("eternet_giriþi" + portno);
			addOutport("eternet_çýkýþý" + portno);
		}
		for (portno = 0; portno < port; portno++) {
		addTestInput("eternet_girisi" + portno, new paketformat("data"));
		}
	}

	public RouterAtomicMode() {
		this("Yönlendirici", new IPAddress(0, 0, 0, 1), 1, 1);
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
					
				paket.setCýkýþ_port("eternet_çýkýþý"+PN.substring(14));
				kuyruk.addElement(paket);
				
				holdIn("paket_alýndý", iþlem_zamaný);
				
				}
				
				
				if (PN.equals("inEvent")) {
		          
					paket = (paketformat) x.getValOnPort("inEvent", i);
				//	System.out.println("Paket :"+paket.getHedef());
					if (paket.getAdres().equals(ip)) {
		            	 
		              paket.setCýkýþ_port("outevent");
						kuyruk.addElement(paket);
		              holdIn("paket_alýndý", iþlem_zamaný);
		              
		            }
		            continue;
		          }
				}
				}
		}
			}
		
			
		



	public void deltint() {
	
if(kuyruk.isEmpty())
	holdIn("boþda", INFINITY);
else  	{
	paket=kuyruk.remove(0);

	if (paket.getAdi().equals("hello")) {
		//System.out.println("önce"+getName()+"  "+paket.getAdres());
		if (paket.getAdres().getByte2()!=ip.getByte2()){	
			paket.setId("bgp");
		Yol y=new Yol(paket.getId(),new IPAddress(paket.getAdres().getByte4(),paket.getAdres().getByte3(),paket.getAdres().getByte2(),paket.getAdres().getByte1()),new IPAddress(paket.getAdres().getByte4(),paket.getAdres().getByte3(),paket.getAdres().getByte2(),paket.getAdres().getByte1()),paket.getMaliyet(),paket.getCýkýþ_port());		
		yt.addElement(y);
		
		}
		else {
			Yol y=new Yol(paket.getId(),paket.getAdres(),paket.getAdres(),paket.getMaliyet(),paket.getCýkýþ_port());
			yt.addElement(y);}
	//	System.out.println(y);
		//	System.out.println(paket.getAdres());
				int a=1;
			
				if (getNumInports()-2>a++)holdIn("komþu_eklendi", iþlem_zamaný);
				else holdIn("rip_gönderme", iþlem_zamaný);
				
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
				Yol y=new Yol(v.get(i).getId(),v.get(i).getHedef(),paket.getAdres(),paket.getMaliyet(),paket.getCýkýþ_port());
				yt.addElement(y);
				holdIn("RIP_güncellendi", iþlem_zamaný);		
				
				}
			else if(sonuc==true && paket.getAdres().getByte2()==ip.getByte2()){
					paket.setMaliyet(v.get(i).getMaliyet()+1);
					Yol y=new Yol(v.get(i).getId(),v.get(i).getHedef(),paket.getAdres(),paket.getMaliyet(),paket.getCýkýþ_port());
					yt.addElement(y);
					
					holdIn("RIP_güncellendi", iþlem_zamaný);	
				}
			}
			
		}
			

	
	
	
	
	else if (paket.getAdi().startsWith("data")) {
	
		boolean c=false;
				if (paket.getHedef().equals(ip)) holdIn("data_alindi", iþlem_zamaný);
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
						
				
					holdIn("data_güncellendi", iþlem_zamaný);
				
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
				m.add(makeContent("eternet_çýkýþý" + k, hello_oluþtur()));
				
			}
			m.add(makeContent("outEvent", hello_oluþtur()));

		} else if (phaseIs("rip_gönderme")) {

			for (int k = 0; k < getNumInports()-1; k++) {
				
				m.add(makeContent("eternet_çýkýþý" + k, RIP_oluþtur()));

			}
		} else if (phaseIs("RIP_güncellendi")) {

			for (int k = 0; k < getNumInports()-1; k++) {
				m.add(makeContent("eternet_çýkýþý" + k, RIP_oluþtur()));
			}
		}
	
		
		 else if (phaseIs("data_güncellendi")){ 
			
		 m.add(makeContent(portadi,paket)); }
		
		 else if (phaseIs("data_alindi")){ 
			//	System.out.println(ip+"data gönderiliyor");
			 m.add(makeContent("outEvent",paket)); }
			 
		return m;

	}

	public String getTooltipText() {

		return "IP : " +ip+ "<br>"+
				"Komþu Tablosu: " +"<br>"
				+ "hedef düðüm"+ "....."+ "sonraki adým" +"....."+"hop sayýsý"+"...."+"çýkýþ portu"+ "<br>"
				+yaz();
				
	}

	
	private paketformat hello_oluþtur() {
		paket_hareket=1;
		paketformat p = new paketformat("hello");
		p.setAdi("hello");
		p.setAdres(ip);
		p.setSonraki_düðüm(ip);
		p.setMaliyet(paket_hareket);
		p.setData(ip);
		return p;
	}
	
	private paketformat data_oluþtur() {
		paketformat p = new paketformat("data");
		p.setAdi("data");
		p.setAdres(paket.getAdres());
		p.setHedef(paket.getHedef());
		p.setData(paket.getData());
		
	
		return p;
	}
	private paketformat RIP_oluþtur() {
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