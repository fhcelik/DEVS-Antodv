package ospff;

import GenCol.entity;
import java.util.Vector;




public class paketformat extends entity {
	private int precedence; // Priority (0-7, 7 highest)
	private int length; // Length of the complete packet
	private String id; // Unique packet id
	private double ttl; // Time to Live. In seconds, but not quite.
	private Vector tabuList; //to store packet source_hop(s) 
	private String d;
	private String adi;
	private IPAddress hedef;
	private IPAddress adres;// Source and destination IP address
	private IPAddress sonraki_düðüm; 
	private int maliyet; 
	private String çýkýþ_port;
	private Object data;


	



	public paketformat(String name_) {
		super(name_);
		adi=name_;
		hedef=new IPAddress(0, 0, 0, 0);
		adres=new IPAddress(0, 0, 0, 0);
		sonraki_düðüm=new IPAddress(0, 0, 0, 0);
		maliyet=0;
		çýkýþ_port="";
	
		tabuList=new Vector();
		id="";
		ttl=0;
	}

	public paketformat(String name, IPAddress dest) {
		this(name);
		hedef = dest;
	}
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
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
	    return "Packet: " + name + "; packet id: " + id +
	                       "; hedef: " + d + "; adres: " + adres +
	                       ",data: " + data + "; out port: " +
	                       çýkýþ_port + "; length: " +length/1000+" KB"+"; tabuList: "+tabuList
	;
	  }




}
