package ServiceMod;

import java.util.ArrayList;

import GenCol.Pair;
import GenService.ServiceProvider;

public class USZipService extends ServiceProvider{	

	public USZipService(String name, String descpt, String svType, ArrayList <Pair> endpts, int processingTime){		
		super(name, descpt, svType, endpts, processingTime);
	}
	
	public Pair performService(Pair data){
		double sizeOfmsgs = 32;
		Double doubleVal;
		Pair returnVal = new Pair();
		
		doubleVal = 
			Double.parseDouble(data.value.toString());
		
		//if the zip code is 85281, then return tempe
		if(doubleVal == 85281){
			returnVal.key = "String";
			returnVal.value = "tempe";
		}
		else{
			returnVal.key = "String";
			returnVal.value = "No Found";
		}
		ServiceReturn.setSize(sizeOfmsgs);
		return returnVal;
	}
}
