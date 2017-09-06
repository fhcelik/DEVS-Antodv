package ospff;

import GenCol.entity;

public class lsapaket extends entity {
private int ip;
private String deger;
public lsapaket(String name,int ipadres) {
	super(name);
	ip=ipadres;
	
}
public lsapaket() {
	this("hello",1);	
}
	
public String adi(){
	return deger;
}
public int adres(){
	return ip;
}
}