package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {

	int timeSlot;
	
	private MostCrowdedStrategy(int timeSlot) {
		this.timeSlot=timeSlot;
	}	
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,int currTime) {		
		int ret;
		int index = 0;
		int biggest = 0;
		List<Vehicle> aux = new ArrayList<Vehicle>();	
		if(roads.size()==0) {
			ret = -1;
		}
		else {
			if(currGreen == -1) {
				for (List<Vehicle> lane : qs) {
					if(lane.size() > biggest) {											
						biggest = lane.size();		
						index = qs.indexOf(lane);
					}
				}			
				ret = index;
			}
			else if(currTime - lastSwitchingTime < timeSlot) {
				ret = currGreen;
			}
			else {
				int p;
				for (int i = currGreen + 1; i < qs.size() + currGreen + 1; i++) {	
					p = i % qs.size();					
					if(qs.get(p).size() > biggest) {
						biggest = qs.get(p).size();
						index = p;
					}					
				}
				ret = index;
			}
		}
		return ret;
	}

}
