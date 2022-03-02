package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent{
	
	public NewInterCityRoadEvent(int time, String id, Junction srcJunc, Junction destJunc, int length, int co2Limit, int maxSpeed, Weather weather)
	{
		super(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
	}

	@Override
	void execute(RoadMap map) {
		map.addRoad(new InterCityRoad(id, srcJunc, destJunc, maxSpeed, co2Limit, length, weather));		
	}
}
