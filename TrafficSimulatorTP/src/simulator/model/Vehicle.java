package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject{

	//Attributes
	private List<Junction> itinerary;
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
		// TODO Auto-generated constructor stub
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
			ret.put("road", road.getId());
			ret.put("location", Location);
		}
		return ret;
	}
	
	private boolean isMaxSpeedValid(int mspeed) { //True if mspeed is positive
		boolean ret;
		if (mspeed >= 0) {
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
		if (!isMaxSpeedValid(s)) {
			throw new IllegalArgumentException();
		}
		Speed = (s < maxSpeed) ? s : maxSpeed;    
	}

	private void setContaminationClass(int c)throws IllegalArgumentException{
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
			
			if(Location+maxSpeed < road.getLength()) {
				Location += maxSpeed;
			}
			else {
				Location=road.getLength();
			}
			
			int c = contClass * (Location-aux);			
			totalCO2 += c;
			road.addContamination(c);
			
			if(Location >= road.getLength()) {
				//llamar a metodo de junction
				status = VehicleStatus.WAITING;
			}
		}
	}

	public List<Junction> getItinerary() {
		return itinerary;
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
	
	public static class VehicleCompare implements Comparator<Vehicle>
	{
		@Override
		public int compare(Vehicle o1, Vehicle o2) {
			int ret;
			if (o1.getLocation() < o2.getLocation()) {
				ret = -1;
			}
			else if (o1.getLocation() > o2.getLocation()) {
				ret = 1;
			}
			else {
				ret = 0;
			}
			return ret;
		}
	}

}






