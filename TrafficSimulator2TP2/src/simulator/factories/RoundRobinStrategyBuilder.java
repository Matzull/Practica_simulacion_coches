package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy>{

	public RoundRobinStrategyBuilder() {
		super("round_robin_lss");
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		RoundRobinStrategy ret = new RoundRobinStrategy(1);
		
		if (data != null && data.has("timeslot")) {
			ret = new RoundRobinStrategy(data.getInt("timeslot"));
		}		
		return ret;
	}

}
