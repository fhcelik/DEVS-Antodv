package honeycomb;

import java.util.Vector;

public class rreqyol {
	private int id;
	private IPAddress hedef;
	private double energy,ttl;
	


	public rreqyol(int kimlik, IPAddress h,double enr,double zaman) {
		id = kimlik;
		hedef = h;
		energy = enr;
		ttl=zaman;
	}

	public double getTtl() {
		return ttl;
	}

	public void setTtl(double ttl) {
		this.ttl = ttl;
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public IPAddress getHedef() {
		return hedef;
	}

	public void setHedef(IPAddress hedef) {
		this.hedef = hedef;
	}

	public String toString() {
		return "----" + id + "---" + hedef + "-------------" +energy+"------"+ttl ;
	}

}
