package ospff;


import java.util.Vector;

public class Yol {
	private String id;
	private IPAddress hedef;
	private IPAddress sonraki_d���m;
	private int maliyet;
	private String port_isim;
	Vector<Yol> yt = new Vector<Yol>();
	public Yol(String kimlik,IPAddress h, IPAddress s, int m, String c){
		id=kimlik;
		hedef=h;
		sonraki_d���m=s;
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
	public String getPort_isim() {
		return port_isim;
	}
	public void setPort_isim(String port_isim) {
		this.port_isim = port_isim;
	}
	public String toString(){
	return "----"+id+"---"+hedef+"-------------"+sonraki_d���m+"---------------"+maliyet+"------------"+port_isim;	
	}

}
