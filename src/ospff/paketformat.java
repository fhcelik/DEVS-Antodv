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
	private IPAddress sonraki_d���m; 
	private int maliyet; 
	private String ��k��_port;
	private Object data;


	



	public paketformat(String name_) {
		super(name_);
		adi=name_;
		hedef=new IPAddress(0, 0, 0, 0);
		adres=new IPAddress(0, 0, 0, 0);
		sonraki_d���m=new IPAddress(0, 0, 0, 0);
		maliyet=0;
		��k��_port="";
	
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



	public IPAddress getSonraki_d���m() {
		return sonraki_d���m;
	}



	public void setSonraki_d���m(IPAddress sonraki_d���m) {
		this.sonraki_d���m = sonraki_d���m;
	}



	public int getMaliyet() {
		return maliyet;
	}



	public void setMaliyet(int maliyet) {
		this.maliyet = maliyet;
	}



	public String getC�k��_port() {
		return ��k��_port;
	}



	public void setC�k��_port(String c�k��_port) {
		this.��k��_port = c�k��_port;
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
	                       ��k��_port + "; length: " +length/1000+" KB"+"; tabuList: "+tabuList
	;
	  }




}
