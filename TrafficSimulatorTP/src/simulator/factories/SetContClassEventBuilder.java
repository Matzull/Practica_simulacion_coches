package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetContClassEventBuilder extends Builder<Event>{

	SetContClassEventBuilder() {
		super("set_cont_class");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		List<Pair<String, Integer>> contClassArr = new ArrayList<>();
		String vehicle;
		int contClass;
		JSONArray arr = data.getJSONArray("info");
		for (int i = 0; i < arr.length(); i++) {
			vehicle = arr.getJSONObject(i).getString("vehicle");
			contClass = arr.getJSONObject(i).getInt("class");
			contClassArr.add(new Pair<String, Integer>(vehicle, contClass));
		}
		return new NewSetContClassEvent(data.getInt("time"), contClassArr);
	}

}
