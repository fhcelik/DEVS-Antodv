package ServiceMod;

import java.util.ArrayList;

import GenCol.Pair;
import GenService.ServiceProvider;

public class GetFedexRateService extends ServiceProvider{
	public GetFedexRateService(String name, String descpt, String svType, ArrayList <Pair> endpts, int processingTime){		
		super(name, descpt, svType, endpts, processingTime);
	}
	
	public Pair performService(Pair data){
		Double doubleVal;
		Pair returnVal = new Pair();
		
		doubleVal = Double.parseDouble(data.value.toString());
		
		returnVal.key = "double";
		returnVal.value = doubleVal *11.2;
		
		return returnVal;		
	}
}
