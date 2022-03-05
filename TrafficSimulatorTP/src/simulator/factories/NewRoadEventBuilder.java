package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event>{

	NewRoadEventBuilder(String type) {
		super(type);
	}

	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		String src = data.getString("src");
		String dest = data.getString("dest");
		int length = data.getInt("length");
		int co2Limit = data.getInt("co2limit");
		int maxSpeed = data.getInt("maxspeed");
		Weather weather = Weather.valueOf(data.getString("weather").toUpperCase());
		if(_type == "new_city_road")
		{
			return new NewCityRoadEvent(time, id, src, dest, length, co2Limit, maxSpeed, weather);
		}
		else {
			return new NewInterCityRoadEvent(time, id, src, dest, length, co2Limit, maxSpeed, weather);
		}
	}

}
