package simulator.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Vehicle.VehicleCompare;

public abstract class Road extends SimulatedObject{

	protected Junction src;
	protected Junction dst;
	protected int length;
	protected int max_speed;
	protected int speed_limit;
	protected int cont_alarm;
	protected Weather weather_condition;
	protected int total_cont;
	protected List<Vehicle> vehicles;
	protected VehicleCompare comp;
	
	protected Road(String id, Junction srcJunc, Junction destJunct, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id);
		if (junctionValid(srcJunc) && junctionValid(destJunct) && maxSpeedValid(maxSpeed) && contLimitValid(contLimit) && lengthValid(length) && weatherValid(weather))
		{
			this.src = srcJunc;
			this.dst = destJunct;
			this.max_speed = maxSpeed;
			this.speed_limit = maxSpeed;
			this.cont_alarm = contLimit;
			this.length = length;
			this.weather_condition = weather;
			this.total_cont = 0;
		}
		else {
			throw new IllegalArgumentException("Cannot create road with the arguments provided");
		}		
	}

	@Override
	protected void advance(int time) {
		reduceTotalContamination();
		updateSpeedLimit();
		for (Vehicle vehicle : vehicles) {
			vehicle.setspeed(calculateVehicleSpeed(vehicle));
			vehicle.advance(time);
		}
		vehicles.sort(new VehicleCompare());//desc order
	}

	@Override
	public JSONObject report() {
		JSONObject ret = new JSONObject();
		ret.put("id", getId());
		ret.put("speedlimit", speed_limit);
		ret.put("weather", weather_condition);
		ret.put("co2", total_cont);
		JSONArray vehicle_list = new JSONArray();
		for (Vehicle vehicle : vehicles) {
			vehicle_list.put(vehicle.report());
		}
		ret.put("vehicles", vehicle_list);
		return ret;
	}
	
	private boolean maxSpeedValid(int speed) {
		return speed > 0;
	}
	
	private boolean contLimitValid (int cont) {
		return cont >= 0;
	}
	
	private boolean lengthValid (int length) {
		return length > 0;
	}
	
	private boolean junctionValid(Junction junct) {
		return junct != null;
	}
	
	private boolean weatherValid(Weather weather) 
	{
		return weather != null;
	}
	
	private void enter(Vehicle v) {
		if (v.getLocation() != 0 || v.getSpeed() != 0) {
			throw new Myexception;
		}
		vehicles.add(v);
	}
	
	private void exit(Vehicle v) {
		vehicles.remove(v);
	}
	
	private void setWeather(Weather w) {
		if (!weatherValid(w)) {
			throw new Myexception;
		}
		this.weather_condition = w;
	}
	
	private void addContamination(int c) {
		if (!contLimitValid(c)) {
			throw new Myexception;
		}
		this.cont_alarm += c;
	}
	
	public int getLength() {
		return length;
	}
	
	public Junction getSrc() {
		return src;
	}

	public Junction getDst() {
		return dst;
	}

	public int getMaxSpeed() {
		return max_speed;
	}

	public int getSpeedLimit() {
		return speed_limit;
	}

	public int getContLimit() {
		return cont_alarm;
	}

	public Weather getWeather() {
		return weather_condition;
	}

	public int getTotalCO2() {
		return total_cont;
	}

	public List<Vehicle> getVehicles() {
		return Collections.unmodifiableList(vehicles);
	}

	abstract void reduceTotalContamination();
	
	abstract void updateSpeedLimit();
	
	private void moveToNextRoad()
	{
		
	}
	
	public void set() {
		
	}
	
	abstract int calculateVehicleSpeed(Vehicle v);

}
