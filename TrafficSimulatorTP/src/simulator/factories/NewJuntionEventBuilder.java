package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJuntionEventBuilder extends Builder<Event>{

	private Factory<LightSwitchingStrategy> lsStrategy;
	private Factory<DequeuingStrategy> dqStrategy;
	
	NewJuntionEventBuilder(Factory<LightSwitchingStrategy> lsStrategy, Factory<DequeuingStrategy> dqStrategy) {
		super("new_junction");
		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		int coor0 = data.getJSONArray("coor").getInt(0);
		int coor1 = data.getJSONArray("coor").getInt(1);
		
		LightSwitchingStrategy lss = lsStrategy.createInstance(data.getJSONObject("ls_strategy"));
		DequeuingStrategy dqs = dqStrategy.createInstance(data.getJSONObject("dq_strategy"));
		
		return new NewJunctionEvent(time, id, lss, dqs, coor0, coor1);
	}

}
