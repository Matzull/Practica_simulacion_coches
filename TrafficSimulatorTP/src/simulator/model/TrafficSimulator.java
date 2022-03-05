package simulator.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator {

	private RoadMap simObj;
	
	private List<Event> events;
	
	private int timeStep;
	
	public TrafficSimulator() {
		simObj = new RoadMap();
		events = new SortedArrayList<Event>(new Comparator<Event>() {
			@Override
			public int compare(Event o1, Event o2) {
				return (o1.getTime() < o2.getTime()) ? -1 : (o1.getTime() > o2.getTime()) ? 1 : 0;
			}		
		}
		);

		timeStep = 0;
	}
	
	public void addEvent(Event e) 
	{
		events.add(e);
	}
	
	public void advance()
	{
		timeStep++;
		List<Event> remove = new ArrayList<Event>();
		for (Event event : events) {
			if (event.getTime() == timeStep) {
				event.execute(simObj);
				remove.add(event);
			}
		}
		events.removeAll(remove);
		for (Junction junction : simObj.getJunctions()) {
			junction.advance(timeStep);
		}
		for (Road road : simObj.getRoads()) {
			road.advance(timeStep);
		}
	}
	
	public void reset() 
	{
		simObj.reset();
		events.clear();
		timeStep = 0;
	}
	
	public JSONObject report()
	{
		JSONObject ret = new JSONObject();
		ret.put("time", timeStep);
		ret.put("state", simObj.report());
		return ret;
	}
}
