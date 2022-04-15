package simulator.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver>{

	private RoadMap simObj;
	
	private List<Event> events;
	
	private List<TrafficSimObserver> observers; 
	
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
		observers = new ArrayList<TrafficSimObserver>();
		timeStep = 0;
	}
	
	public void addEvent(Event e) 
	{
		events.add(e);
		for(TrafficSimObserver observer : observers)
		{
			observer.onEventAdded(simObj, events, e, timeStep);
		}
	}
	
	public void advance()
	{
		timeStep++;
		for(TrafficSimObserver observer : observers)
		{
			observer.onAdvanceStart(simObj, events, timeStep);
		}
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
		for(TrafficSimObserver observer : observers)
		{
			observer.onAdvanceEnd(simObj, events, timeStep);
		}
	}
	
	public void reset() 
	{
		simObj.reset();
		events.clear();
		timeStep = 0;
		for(TrafficSimObserver observer : observers)
		{
			observer.onReset(simObj, events, timeStep);
		}
	}
	
	public JSONObject report()
	{
		JSONObject ret = new JSONObject();
		ret.put("time", timeStep);
		ret.put("state", simObj.report());
		return ret;
	}

	
	@Override
	public void addObserver(TrafficSimObserver o) {
		observers.add(o);
		for(TrafficSimObserver observer : observers)
		{
			observer.onRegister(simObj, events, timeStep);
		}
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		observers.remove(o);		
	}

}
