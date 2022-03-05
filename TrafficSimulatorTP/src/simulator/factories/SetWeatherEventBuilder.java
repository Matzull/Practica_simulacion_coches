package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event>{

	SetWeatherEventBuilder() {
		super("set_weather");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		List<Pair<String, Weather>> ws = new ArrayList<>();
		String road;
		Weather weather;
		JSONArray arr = data.getJSONArray("info");
		for (int i = 0; i < arr.length(); i++) {
			road = arr.getJSONObject(i).getString("road");
			weather = Weather.valueOf(arr.getJSONObject(i).getString("weather").toUpperCase());
			ws.add(new Pair<String, Weather>(road, weather));
		}
		return new SetWeatherEvent(data.getInt("time"), null);
	}

}
