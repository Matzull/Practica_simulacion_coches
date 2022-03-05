package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class NewSetContClassEvent extends Event{

	List<Pair<String,Integer>> cs;
	
	
	
	public NewSetContClassEvent(int time, List<Pair<String,Integer>> cs) {
		super(time);
		this.cs=cs;		
		if(cs == null ) {
			throw new IllegalArgumentException();
		}		
	}
	
	@Override
	void execute(RoadMap map) {
		for(Pair<String,Integer> c : cs) {
			if(map.getVehicle(c.getFirst())==null) {
				throw new IllegalArgumentException();
			}			
			map.getVehicle(c.getFirst()).setContClass(c.getSecond());		
		}
	}

}
