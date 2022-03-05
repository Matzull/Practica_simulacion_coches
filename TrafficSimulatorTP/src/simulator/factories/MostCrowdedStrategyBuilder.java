package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy>{

	MostCrowdedStrategyBuilder() {
		super("most_crowded_lss");
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		MostCrowdedStrategy ret = new MostCrowdedStrategy(1);
		
		if (data != null && data.has("timeslot")) {
			ret = new MostCrowdedStrategy(data.getInt("timeslot"));
		}		
		return ret;
	}
	
}
	