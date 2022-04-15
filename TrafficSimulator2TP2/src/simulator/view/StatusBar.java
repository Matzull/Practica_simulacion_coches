package simulator.view;

import java.util.List;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import java.awt.Dimension;
import java.awt.FlowLayout;

public class StatusBar extends JPanel implements TrafficSimObserver{

	Controller _ctrl;
	JLabel _time;
	JLabel _msg;
	
	public StatusBar(Controller ctrl) {
		this._ctrl = ctrl;
		ctrl.addObserver(this);
		initGUI();
	}

	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		Border border = new TitledBorder(new EtchedBorder());
		this.setBorder(border);
		
		_time = new JLabel("TimeStep: 0");
		this.add(_time);
		this.add(Box.createRigidArea(new Dimension(40, 0)));
		
		_msg = new JLabel();
		this.add(_msg);
		
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		_time.setText("TimeStep: " + time);		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_time.setText("TimeStep: " + time);	
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		_msg.setText("Event added: (" + e.toString() + ")");		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_msg.setText("Simulation reset");
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		//_msg.setText("Observer registered");		
	}

	@Override
	public void onError(String err) {
		_msg.setText("Error: " + err);		
	}

}
