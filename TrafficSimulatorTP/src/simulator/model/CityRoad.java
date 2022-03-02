package simulator.model;

public class CityRoad extends Road {

	protected CityRoad(String id, Junction srcJunc, Junction destJunct, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunct, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {	
		int reduction = (weather_condition == Weather.STORMY || weather_condition == Weather.WINDY) ? 10 : 2 ;
		total_cont = (total_cont - reduction < 0) ? 0 : total_cont - reduction;
	}

	@Override
	void updateSpeedLimit() {	
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {			
		v.setSpeed(((11-v.getContClass())*speed_limit)/11);
		return 0;
	}

}
