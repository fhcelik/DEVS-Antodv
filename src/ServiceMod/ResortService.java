package ServiceMod;

import java.util.ArrayList;

import GenCol.Pair;
import GenService.ServiceProvider;

public class ResortService extends ServiceProvider{	

	public ResortService(String name, String descpt, String svType, ArrayList <Pair> endpts, int processingTime){		
		super(name, descpt, svType, endpts, processingTime);
	}
	
	public Pair performService(Pair data){
		double sizeOfmsgs = 32;
		Pair returnVal = new Pair();
		
		//if argument is tempe, 
		//then return pheonix resort
		if(data.value.toString().equals("tempe")){
			returnVal.key = "String";
			returnVal.value = "Pheonix Resort";
		}
		else{
			returnVal.key = "String";
			returnVal.value = "No Found";
		}
		
		ServiceReturn.setSize(sizeOfmsgs);
		return returnVal;
	}
}
