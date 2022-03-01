package simulator.model;

import java.time.chrono.Era;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.management.ValueExp;
import javax.print.attribute.standard.NumberOfInterveningJobs;

import org.json.JSONObject;

public class Junction extends SimulatedObject{

	private List<Road> innerRoads;
	private Map<Junction,Road> exitRoads;
	private List<List<Vehicle>> queueRoads;
	private int currentGreen;
	private LightSwitchingStrategy lsStrategy;
	private DequeingStrategy dqStrategy;
	private int xCoor,yCoor;
	private Map<Road, List<Vehicle>> roadQueueMap;
	
	
	
	Junction(String id, LightSwitchStrategy lsStrategy, DequeingStrategy dqStrategy, int xCoor, int yCoor) {
			super(id);
			//
			this.lsStrategy=lsStrategy;
			this.dqStrategy=dqStrategy;
			this.xCoor=xCoor;
			this.yCoor = yCoor;
			currentGreen=0;
			List<Road> innerRoads = new ArrayList<Road>();
			List<List<Vehicle>> queueRoads = new ArrayList<List<Vehicle>>();
			Map<Junction,Road> exitRoads = new HashMap<Junction,Road>();
		
			Map<Road, List<Vehicle>> roadQueueMap = new HashMap<Road, List<Vehicle>>();		
			
			if(lsStrategy!=null && dqStrategy!= null && xCoor >= 0 && yCoor >= 0) {
				throw new IllegalArgumentException("Junction Data not valid");
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
	
	void addIncommingRoad(Road r) {
		innerRoads.add(r);
		
		List<Vehicle> queueVehicles = new LinkedList<Vehicle>();
		
		queueRoads.add(queueVehicles);
		
		
		innerRoads.add(r);
		
		List<Vehicle> queueVehicles = new LinkedList<Vehicle>();
		
		queueRoads.add(queueVehicles);
		
	}
		
	
	

}
