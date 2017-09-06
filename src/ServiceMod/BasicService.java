
package ServiceMod;

import java.util.ArrayList;

import GenCol.Pair;
import GenService.ServiceProvider;

	public class BasicService extends ServiceProvider{
	
		public BasicService(String name, 
							String descpt, 
							String svType, 
							ArrayList <Pair> endpts, 
							double processingTime){		
			super(name, descpt, svType, endpts, processingTime);	
		}
		
		public Pair performService(Pair data){
			//doing nothing, just return
			return data;
		}
	}

	