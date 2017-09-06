package honeycomb;


import java.util.Vector;



public class Yol {
	private int id;
	private int yolu;
	private String kaynak;
	private IPAddress önceki_düðüm;
	private IPAddress sonraki_düðüm;
	private int maliyet;
	private int energy;
	private double life;
	Vector<Yol> yt = new Vector<Yol>();
	public Yol(int yl,int idd,String ky,IPAddress h, IPAddress s, int m, int enr,double omur){
		yolu=yl;
		id=idd;
		önceki_düðüm=h;
		sonraki_düðüm=s;
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






	public IPAddress getÖnceki_düðüm() {
		return önceki_düðüm;
	}






	public void setÖnceki_düðüm(IPAddress önceki_düðüm) {
		this.önceki_düðüm = önceki_düðüm;
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
	
	public String toString(){
	return "---"+yolu+"---"+id+"---"+kaynak+"---"+önceki_düðüm+"-----"+sonraki_düðüm+"-----"+maliyet+"----"+energy+"---"+life;	
	}

}
