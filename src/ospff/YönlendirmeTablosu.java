package ospff;

import java.util.Enumeration;
import java.util.Vector;

public class YönlendirmeTablosu {
	Vector<Yol> yt = new Vector<Yol>();

	public void yolEkle(Yol y) {
		yt.addElement(y);

	}
	public boolean yolSorgu(int adres){
		for (int i = 0; i < yt.size(); i++) {
	
				return true;
		}
		return false;		
	}

	public boolean yolBosmu(){
		if (yt.isEmpty()){
			return true;
		}else return false;
	}
	
	
	public String toString(){
		String str="";
		Enumeration e=yt.elements();
		while(e.hasMoreElements()){
			str+= e.nextElement().toString()+"\n";	
		}
		return str;
		
	}

}
