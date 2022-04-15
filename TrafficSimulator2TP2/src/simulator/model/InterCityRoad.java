package simulator.model;

public class InterCityRoad extends Road {

	protected InterCityRoad(String id, Junction srcJunc, Junction destJunct, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunct, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		int x = 0;
		switch (weather_condition) {
		case SUNNY:
			x = 2;
			break;
		case CLOUDY:
			x = 3;
			break;
		case RAINY:
			x = 10;
			break;
		case WINDY:
			x = 10;
			break;
		case STORM:
			x = 20;
			break;
		default:
			break;
		}
		
		total_cont = ((100 - x) * total_cont)/100;	
	}

	@Override
	void updateSpeedLimit() {	
		speed_limit = (total_cont > cont_alarm) ? max_speed/2 : max_speed;
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		return (weather_condition == Weather.STORM) ? (speed_limit * 8)/10 : speed_limit;
	}

}
