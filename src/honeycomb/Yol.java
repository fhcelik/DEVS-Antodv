package honeycomb;


import java.util.Vector;



public class Yol {
	private int id;
	private int yolu;
	private String kaynak;
	private IPAddress �nceki_d���m;
	private IPAddress sonraki_d���m;
	private int maliyet;
	private int energy;
	private double life;
	Vector<Yol> yt = new Vector<Yol>();
	public Yol(int yl,int idd,String ky,IPAddress h, IPAddress s, int m, int enr,double omur){
		yolu=yl;
		id=idd;
		�nceki_d���m=h;
		sonraki_d���m=s;
		maliyet=m;	
		energy=enr;
		kaynak=ky;
		life=omur;
	}
	
	

	


	public int getYolu() {
		return yolu;
	}






	public void setYolu(int yolu) {
		this.yolu = yolu;
	}






	public double getLife() {
		return life;
	}






	public void setLife(double life) {
		this.life = life;
	}






	public String getKaynak() {
		return kaynak;
	}






	public void setKaynak(String kaynak) {
		this.kaynak = kaynak;
	}






	public int getEnergy() {
		return energy;
	}



	public void setEnergy(int energy) {
		this.energy = energy;
	}



	


	
	public int getId() {
		return id;
	}






	public void setId(int id) {
		this.id = id;
	}






	public IPAddress get�nceki_d���m() {
		return �nceki_d���m;
	}






	public void set�nceki_d���m(IPAddress �nceki_d���m) {
		this.�nceki_d���m = �nceki_d���m;
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
	
	public String toString(){
	return "---"+yolu+"---"+id+"---"+kaynak+"---"+�nceki_d���m+"-----"+sonraki_d���m+"-----"+maliyet+"----"+energy+"---"+life;	
	}

}
