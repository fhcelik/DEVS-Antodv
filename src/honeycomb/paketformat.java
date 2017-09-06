package honeycomb;

import GenCol.entity;
import java.util.Vector;






public class paketformat extends entity {
	private int energy;
	private Object renergy;
	private String kaynak;
	
	private int x,y;
	private int precedence; // Priority (0-7, 7 highest)
	private int length; // Length of the complete packet
	private int id;
	private int ttl; // Time to Live. In seconds, but not quite.
	private int yolu;
	private Vector<Integer> tabuList; //to store packet source_hop(s) 
	private String d;
	private String adi;
	private IPAddress hedef;
	private Object rehedef;
	private Object rerep;
	private IPAddress adres;// Source and destination IP address
	private IPAddress sonraki_düðüm; 
	private IPAddress önceki_düðüm; 
	private int life;
	private int maliyet; 
	private String çýkýþ_port;
	private Object data;


	

	

	

	public paketformat(String name_) {
		super(name_);
		adi=name_;
		hedef=new IPAddress(0, 0, 0, 0);
		adres=new IPAddress(0, 0, 0, 0);
		sonraki_düðüm=new IPAddress(0, 0, 0, 0);
		önceki_düðüm=new IPAddress(0, 0, 0, 0);
		maliyet=0;
		çýkýþ_port="";
		rehedef="";
		energy=0;
		x=0;
		y=0;
		renergy="";
		kaynak="";
		rerep="";
		data="";
		tabuList=new Vector<Integer>();
	id=0;
		ttl=0;
		life=0;
		yolu=0;
	}
	
	public paketformat(paketformat other) {
		name=other.getName();
		adi=other.getAdi();
		hedef=other.getHedef();
		adres=other.getAdres();
		sonraki_düðüm=other.getSonraki_düðüm();
		önceki_düðüm=other.getÖnceki_düðüm();
		maliyet=other.getMaliyet();
		rehedef=other.getRehedef();
		energy=other.getEnergy();
		x=other.getX();
		y=other.getY();
		renergy=other.getRenergy();
		kaynak=other.getKaynak();
		rerep=other.getRerep();
		data=other.getData();
		çýkýþ_port=other.getCýkýþ_port();
		id=other.getId();
		ttl=other.getTtl();
		life=other.getTtl();
		yolu=other.getYolu();
	}

	


	public int getYolu() {
		return yolu;
	}

	public void setYolu(int yolu) {
		this.yolu = yolu;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public String getKaynak() {
		return kaynak;
	}



	

	public IPAddress getÖnceki_düðüm() {
		return önceki_düðüm;
	}

	public void setÖnceki_düðüm(IPAddress önceki_düðüm) {
		this.önceki_düðüm = önceki_düðüm;
	}

	public void setKaynak(String kaynak) {
		this.kaynak = kaynak;
	}



	public Object getRerep() {
		return rerep;
	}



	public void setRerep(Object rerep) {
		this.rerep = rerep;
	}



	public int getTtl() {
		return ttl;
	}

	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
	public Vector<Integer> getTabuList() {
		return tabuList;
	}

	public void setTabuList(Vector<Integer> tabuList) {
		this.tabuList = tabuList;
	}

	public paketformat(String name, IPAddress dest) {
		this(name);
		hedef = dest;
	}
	
	
	

	


	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEnergy() {
		return energy;
	}



	public void setEnergy(int energy) {
		this.energy = energy;
	}



	public Object getRenergy() {
		return renergy;
	}



	public void setRenergy(Object renergy) {
		this.renergy = renergy;
	}



	public Object getRehedef() {
		return rehedef;
	}



	public void setRehedef(Object rehedef) {
		this.rehedef = rehedef;
	}



	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getLength() {
		return length;
	}



	public void setLength(int length) {
		this.length = length;
	}



	public String getAdi() {
		return adi;
	}



	public void setAdi(String adi) {
		this.adi = adi;
	}



	public IPAddress getHedef() {
		return hedef;
	}



	public void setHedef(IPAddress hedef) {
		this.hedef = hedef;
	}



	public IPAddress getAdres() {
		return adres;
	}

	

	public void setAdres(IPAddress adres) {
		this.adres = adres;
	}



	public IPAddress getSonraki_düðüm() {
		return sonraki_düðüm;
	}



	public void setSonraki_düðüm(IPAddress sonraki_düðüm) {
		this.sonraki_düðüm = sonraki_düðüm;
	}



	public int getMaliyet() {
		return maliyet;
	}



	public void setMaliyet(int maliyet) {
		this.maliyet = maliyet;
	}



	public String getCýkýþ_port() {
		return çýkýþ_port;
	}



	public void setCýkýþ_port(String cýkýþ_port) {
		this.çýkýþ_port = cýkýþ_port;
	}



	public Object getData() {
		return data;
	}



	public void setData(Object data) {
		this.data = data;
	}
	

	public String pPrnt() {
	    if (hedef.getIntegerAddress()==0){
	    d="null";
	    } else d=hedef.toString();
	    return "Packet: " + name + "; packet id: " + 
	                       "; hedef: " + d + "; adres: " + adres +
	                       ",data: " + data + "; out port: " +
	                       çýkýþ_port + "; length: " +length+" B"+"; tabuList: "+tabuList
	;
	  }



}
