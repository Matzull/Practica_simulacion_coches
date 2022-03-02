package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject{

	private List<Road> incomingRoads;
	private Map<Junction,Road> exitRoads;
	private List<List<Vehicle>> queueRoads;
	private int currentGreen;
	private int lastgreenchange;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor,yCoor;
	private Map<Road, List<Vehicle>> roadQueueMap;
	
	
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) throws IllegalArgumentException {
			super(id);
			this.lsStrategy = lsStrategy;
			this.dqStrategy = dqStrategy;
			this.xCoor = xCoor;
			this.yCoor = yCoor;
			currentGreen = -1;
			incomingRoads = new ArrayList<Road>();
			queueRoads = new ArrayList<List<Vehicle>>();
			exitRoads = new HashMap<Junction,Road>();	
			roadQueueMap = new HashMap<Road, List<Vehicle>>();		
			
			if(lsStrategy != null && dqStrategy != null && xCoor >= 0 && yCoor >= 0) {
				throw new IllegalArgumentException("Cannot create junction with the arguments provided");
			}	
	}
	
	

	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	public void setCurrentGreen(int currentGreen) {
		this.currentGreen = currentGreen;
	}



	void addIncommingRoad(Road r) throws IllegalArgumentException{
		incomingRoads.add(r);

		List<Vehicle> queueVehicles = new LinkedList<Vehicle>();
		
		queueRoads.add(queueVehicles);
		
		roadQueueMap.put(r, queueVehicles);
		
		if(this!=r.getDst()) {
			throw new IllegalArgumentException();
		}
		
	}
		
	
	

}
