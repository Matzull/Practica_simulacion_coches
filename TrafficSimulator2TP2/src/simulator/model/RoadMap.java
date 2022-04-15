package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	
	List<Junction> junctionList;
	List<Road> roadList;
	List<Vehicle> vehicleList;
	Map<String, Junction> junctionMap;
	Map<String, Road> roadMap;
	Map<String, Vehicle> vehicleMap;
	
	RoadMap() {
		junctionList = new ArrayList<Junction>();
		roadList = new ArrayList<Road>();
		vehicleList = new ArrayList<Vehicle>();
		
		junctionMap = new HashMap<String, Junction>();
		roadMap = new HashMap<String, Road>();
		vehicleMap = new HashMap<String, Vehicle>();
	}
	
	public void addJunction(Junction j) throws IllegalArgumentException{
		if (junctionMap.get(j.getId()) != null) {
			throw new IllegalArgumentException();
		}
		junctionList.add(j);
		junctionMap.put(j.getId(), j);
	}
	
	public void addRoad(Road r) throws IllegalArgumentException{
		if (roadMap.get(r.getId()) != null || !junctionList.contains(r.getSrc()) || !junctionList.contains(r.getDest())) {
			throw new IllegalArgumentException();
		}
		roadList.add(r);
		roadMap.put(r.getId(), r);
	}
	
	public void addVehicle(Vehicle v)throws IllegalArgumentException {
		if (vehicleMap.get(v.getId()) != null) {
			throw new IllegalArgumentException();
		}
		boolean con;
		Junction src;
		Junction dst;
		for (int i = 0; i < v.getItinerary().size() - 1; i++) {
			con = false;
			src = v.getItinerary().get(i);
			dst = v.getItinerary().get(i + 1);
			for (Road road : roadList) {
				if (road.getSrc() == src && road.getDest() == dst) {
					con = true;
				}
			}
				if (!con) {
					throw new IllegalArgumentException();
				}
		}
		vehicleList.add(v);
		vehicleMap.put(v.getId(), v);
	}
	
	public Junction getJunction(String id)
	{
		return junctionMap.get(id);		
	}
	
	public Road getRoad(String id)
	{
		return roadMap.get(id);	
	}
	
	public Vehicle getVehicle(String id)
	{
		return vehicleMap.get(id);	
	}
	
	public List<Junction>getJunctions()
	{
		return Collections.unmodifiableList(new ArrayList<>(junctionList));
	}
		
	public List<Road>getRoads()
	{
		return Collections.unmodifiableList(new ArrayList<>(roadList));
	}
	
	public List<Vehicle>getVehicles()
	{
		return Collections.unmodifiableList(new ArrayList<>(vehicleList));
	}
	
	public void reset() 
	{
		junctionList.clear();
		roadList.clear();
		vehicleList.clear();
		junctionMap.clear();
		roadMap.clear();
		vehicleMap.clear();
	}
	
	public JSONObject report() 
	{
		JSONObject ret = new JSONObject();
		JSONArray temp = new JSONArray();
		for (Junction junction : junctionList) {
			temp.put(junction.report());
		}
		
		ret.put("junctions", temp);
		temp = new JSONArray();
		
		for (Road road : roadList) {
			temp.put(road.report());
		}
		
		ret.put("roads", temp);
		temp = new JSONArray();
		
		for (Vehicle vehicle : vehicleList) {
			temp.put(vehicle.report());
		}
		
		ret.put("vehicles", temp);
		return ret;
	}
}