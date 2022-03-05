package simulator.factories;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.naming.ldap.StartTlsRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.NewCityRoadEvent;
import simulator.model.NewVehicleEvent;
import simulator.model.Weather;

public class NewVehicleEventBuilder extends Builder<Event>{

	NewVehicleEventBuilder() {
		super("new_vehicle");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		int maxSpeed = data.getInt("maxspeed");
		int Contclass = data.getInt("class");
		List<String> j=new ArrayList<String>();
		JSONArray itinerary = data.getJSONArray("itinerary");
		for(int i=0; i < itinerary.length(); i++) {
			j.add(itinerary.getString(i));
		}
		
		
		return new NewVehicleEvent(time, id, maxSpeed, Contclass, j);
	}
	
}
