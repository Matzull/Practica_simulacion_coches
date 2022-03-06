package simulator.control;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {
	 
	private TrafficSimulator sim;
	private Factory<Event> events_factory;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		// TODO Auto-generated constructor stub
		if(sim == null && eventsFactory == null) {
			throw new IllegalArgumentException();
		}
		this.sim=sim;
		this.events_factory=eventsFactory;
	}
	
	public void loadEvents(InputStream in)
	{	
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray arr = jo.getJSONArray("events");
		for (int i = 0; i < arr.length(); i++) {
			if(events_factory.createInstance(arr.getJSONObject(i))==null) {
				throw new IllegalArgumentException();
			}
			else {				
				sim.addEvent(events_factory.createInstance(arr.getJSONObject(i)));
			}	
		}
	}
	
	public void run(int n, OutputStream out) {

        PrintStream o = new PrintStream(out);

        o.println("{"  +"\n"+ "  \"states\": [");
        for (int i = 0; i < n - 1; i++) {
            sim.advance();
            o.println(sim.report() + ",");
        }

        sim.advance();
        o.println(sim.report());
        o.println("]"+ "\n" + "}");

        o.close();

    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
