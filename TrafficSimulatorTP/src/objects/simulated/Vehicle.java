package objects.simulated;

import java.util.List;

import org.json.JSONObject;
import simulator.model.SimulatedObject;
import simulator.model.VehicleStatus;

public class Vehicle extends SimulatedObject{

	//Attributes
	private List<Junction> itinerario;
	private int max_speed;
	private int cur_speed;
	private VehicleStatus status;
	private Road road;
	private int road_pos;
	private int cont;
	private int tot_cont;
	private int tot_distance;
	
	//Constructor
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public void advance(int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//Checkers (is valid)
	private boolean ismaxspeedvalid(int mspeed) { //True if mspeed is positive
		boolean ret;
		if (mspeed > 0) {
			ret = true;
		}
		else {
			ret = false;
		}
		return ret;
	}
	
	private boolean iscontvalid(int cont) { //True if cont between 0 and 10 included
		boolean ret;
		if (cont >= 0 && cont <= 10) {
			ret = true;
		}
		else {
			ret = false;
		}
		return ret;
	}
	
	private boolean isvalid(List<Junction> itinerary) { //True if cont between 0 and 10 included
		
	}	
	//Getters
	//Setters

}
