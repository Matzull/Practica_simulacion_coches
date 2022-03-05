package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;

public class Junction extends SimulatedObject{

	private List<Road> incomingRoads;
	private Map<Junction,Road> exitRoads;
	private List<List<Vehicle>> queueRoads;
	private Map<Road, List<Vehicle>> roadQueueMap;
	private int currentGreen;
	private int lastSwitchingTime;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor,yCoor;
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) throws IllegalArgumentException {
			super(id);
			this.lsStrategy = lsStrategy;
			this.dqStrategy = dqStrategy;
			this.xCoor = xCoor;
			this.yCoor = yCoor;
			this.lastSwitchingTime=0;
			currentGreen = -1;
			incomingRoads = new ArrayList<Road>();
			queueRoads = new ArrayList<List<Vehicle>>();
			exitRoads = new HashMap<Junction,Road>();	
			roadQueueMap = new HashMap<Road, List<Vehicle>>();		
			
			if(lsStrategy == null || dqStrategy == null || xCoor < 0 || yCoor < 0) {
				throw new IllegalArgumentException("Cannot create junction with the arguments provided");
			}	
	}
	
	@Override
	void advance(int time) {
		
		int oldCurrentGreen = currentGreen; 	
		
		if(currentGreen!=-1) {			
			
			List<Vehicle> vehicleAux = dqStrategy.dequeue(queueRoads.get(currentGreen)); // si no seria roadQueueMap.get(incomingRoads.get(currentGreen));
			
			for(Vehicle v : vehicleAux) {
				v.moveToNextRoad();			
			}
			
			queueRoads.get(currentGreen).removeAll(vehicleAux);//juan dice que esta bien 
			
		}	
			
		currentGreen = lsStrategy.chooseNextGreen(incomingRoads, queueRoads, currentGreen, lastSwitchingTime, time);	
		
		if(currentGreen!=oldCurrentGreen) {
			lastSwitchingTime=time;
		}		
		
	}
	
	public void setCurrentGreen(int currentGreen) {
		this.currentGreen = currentGreen;
	}

	public void addIncommingRoad(Road r) throws IllegalArgumentException{
		incomingRoads.add(r);

		List<Vehicle> queueVehicles = new LinkedList<Vehicle>();
		
		queueRoads.add(queueVehicles);
		
		roadQueueMap.put(r, queueVehicles);
		
		if(this != r.getDest()) {
			throw new IllegalArgumentException();
		}
	}
	
	public void addOutGoingRoad(Road r) throws IllegalArgumentException{
		
		Junction j = r.getDest();
		
		if (r.getSrc() == this && exitRoads.get(j) == null) {
			exitRoads.put(j, r);
		}
		else {
			throw new IllegalArgumentException();
		}		
	}

	void enter(Vehicle v) {//esta funcion no escribe en la road de v, escribe en queueRoad y RoadqueueMap
		
		int index;
		
		index = queueRoads.indexOf(v.getRoad());		
		
		queueRoads.get(index).add(v);
		
	}
	
	public Road roadTo(Junction j) {
		
		return exitRoads.get(j);
		
	}
	
	public JSONObject report()
	{
		JSONObject ret = new JSONObject();
		ret.put("id", getId());
		ret.put("green", (currentGreen == -1) ? "none" : incomingRoads.get(currentGreen).getId());
		JSONArray arr = new JSONArray();
		JSONArray qs = new JSONArray();
		JSONObject q = new JSONObject();
		for (Road road : roadQueueMap.keySet()) {
			q.put("road", road.getId());
			for (Vehicle vehicle : roadQueueMap.get(road)) {
				arr.put(vehicle.getId());
			}
			q.put("vehicles", arr);
			qs.put(q);
			arr = new JSONArray();
			q = new JSONObject();
		}	
		ret.put("queues", qs);
		return ret;
	}

}
