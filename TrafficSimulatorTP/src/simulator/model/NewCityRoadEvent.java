package simulator.model;

public class NewCityRoadEvent extends NewRoadEvent{
	
	public NewCityRoadEvent(int time, String id, Junction srcJunc, Junction destJunc, int length, int co2Limit, int maxSpeed, Weather weather)
	{
		super(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
	}

	@Override
	void execute(RoadMap map) {
		map.addRoad(new CityRoad(id, srcJunc, destJunc, maxSpeed, co2Limit, length, weather));		
	}
}
