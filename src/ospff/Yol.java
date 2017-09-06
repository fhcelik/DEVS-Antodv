package ospff;


import java.util.Vector;

public class Yol {
	private String id;
	private IPAddress hedef;
	private IPAddress sonraki_düðüm;
	private int maliyet;
	private String port_isim;
	Vector<Yol> yt = new Vector<Yol>();
	public Yol(String kimlik,IPAddress h, IPAddress s, int m, String c){
		id=kimlik;
		hedef=h;
		sonraki_düðüm=s;
		maliyet=m;
		port_isim=c;	
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IPAddress getHedef() {
		return hedef;
	}
	public void setHedef(IPAddress hedef) {
		this.hedef = hedef;
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
	public String getPort_isim() {
		return port_isim;
	}
	public void setPort_isim(String port_isim) {
		this.port_isim = port_isim;
	}
	public String toString(){
	return "----"+id+"---"+hedef+"-------------"+sonraki_düðüm+"---------------"+maliyet+"------------"+port_isim;	
	}

}
