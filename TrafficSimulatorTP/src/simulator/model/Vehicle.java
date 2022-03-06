
package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject{

	//Attributes
	private List<Junction> itinerary;
	private int itineraryIndex;
	private int maxSpeed;
	private int Speed;
	private VehicleStatus status;
	private Road road;
	private int Location;
	private int contClass;
	private int totalCO2;
	private int tot_distance;
	
	//Constructor
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		this.itinerary = itinerary;
		itineraryIndex = 0;
		if (!isMaxSpeedValid(maxSpeed) || !isContValid(contClass) || !isValidItinerary(itinerary)) {
			throw new IllegalArgumentException();
		}
		this.maxSpeed = maxSpeed;
		this.Speed = 0;
		this.status = VehicleStatus.PENDING;
		this.contClass = contClass;
		road = null;
		Location = 0;
		totalCO2 = 0;
		tot_distance = 0;
	}

	@Override
	public JSONObject report() {
		JSONObject ret = new JSONObject();
		ret.put("id", getId());
		ret.put("speed", Speed);
		ret.put("distance", tot_distance);
		ret.put("co2", totalCO2);
		ret.put("class", contClass);
		ret.put("status", status.toString());
		if (status == VehicleStatus.TRAVELING || status == VehicleStatus.WAITING) {
			ret.put("road", road.toString());
			ret.put("location", Location);
		}
		return ret;
	}
	
	private boolean isMaxSpeedValid(int mspeed) { //True if mspeed is positive
		boolean ret;
		if (mspeed > 0) {
			ret = true;
		}
		else {
			ret = false;
		}
		return ret;
	}
	
	private boolean isContValid(int cont) { //True if cont between 0 and 10 included
		boolean ret;
		if (cont >= 0 && cont <= 10) {
			ret = true;
		}
		else {
			ret = false;
		}
		return ret;
	}
	
	private boolean isValidItinerary(List<Junction> itinerary) { //True if itinerary >= 2
		boolean ret = false;
		if (Collections.unmodifiableList(new ArrayList<>(itinerary)).size() > 1) {
			ret = true;
		}
		return ret;
	}	

	public void setSpeed(int s)throws IllegalArgumentException{	
		if (status == VehicleStatus.TRAVELING) {
			if (!isMaxSpeedValid(s)) {
				throw new IllegalArgumentException();
			}
			Speed = (s < maxSpeed) ? s : maxSpeed;
		}
		
		else {
			Speed = 0;
		}   
	}

	void setContClass(int c)throws IllegalArgumentException{
		if(!isContValid(c))
		{
			throw new IllegalArgumentException();
		}
		contClass = c;
	}
	
	private void setState(VehicleStatus status) {
		this.status = status;
		if (status != VehicleStatus.TRAVELING) {
			setSpeed(0);
		}
	}
	
	public void advance(int time) {
		
		if (this.status == VehicleStatus.TRAVELING) 
		{
			int aux=Location;
			
			if(Location + Speed < road.getLength()) {
				Location += Speed;
			}
			else {
				Location=road.getLength();
			}
			
			tot_distance += Location - aux;
			
			int c = contClass * (Location-aux);			
			totalCO2 += c;
			road.addContamination(c);		
			if(Location >= road.getLength() ) {
				Speed = 0;
				itineraryIndex++;
				status = VehicleStatus.WAITING;				
				road.getDest().enter(this);
			}
		}
		
		else{
			Speed=0;	
		}
	}
	
	public List<Junction> getItinerary() {
		return Collections.unmodifiableList(new ArrayList<>(itinerary));
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public int getSpeed() {
		return Speed;
	}

	public VehicleStatus getStatus() {
		return status;
	}

	public Road getRoad() {
		return road;
	}

	public int getLocation() {
		return Location;
	}

	public int getContClass() {
		return contClass;
	}
	
	public int getTotalCO2() {
		return totalCO2;
	}
	
	public void moveToNextRoad() {
		if(status != VehicleStatus.PENDING && status != VehicleStatus.WAITING)
		{
			throw new IllegalArgumentException();
		}
		if(itinerary.size() -1 == itineraryIndex) {
			road.exit(this);
			status=VehicleStatus.ARRIVED;
		}
		else if(status==VehicleStatus.PENDING) {
			road=itinerary.get(0).roadTo(itinerary.get(1));
			road.enter(this);
			status = VehicleStatus.TRAVELING;
		}
		else {		
			road.exit(this);
			Location = 0;
			road=itinerary.get(itineraryIndex).roadTo(itinerary.get(itineraryIndex+1));
			status = VehicleStatus.TRAVELING;
			road.enter(this);		
		}
	}
}




