package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver{

	Controller _ctrl;
	List<Junction> _junctions = new ArrayList<Junction>();
	String _cols[] = {"Id", "Green", "Queues"};
	
	public JunctionsTableModel(Controller ctrl)
	{
		this._ctrl = ctrl;
		ctrl.addObserver(this);
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_junctions = map.getJunctions();
		fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		_junctions = map.getJunctions();
		fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_junctions = map.getJunctions();
		fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_junctions = map.getJunctions();
		fireTableDataChanged();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getRowCount() {
		return _junctions.size();
	}

	@Override
	public int getColumnCount() {
		return _cols.length;

	}

	@Override
	public String getColumnName(int column) {
		return _cols[column];
		}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String s = "";
		Junction j = _junctions.get(rowIndex);
	switch ( columnIndex ) {
		case 0:
				s = "" + j.getId();
				break;
				
		case 1:
			int g = j.getGreenLightIndex();
			s = (g != -1) ? j.getInRoads().get(j.getGreenLightIndex()).getId() : "None";
			break;
			
		case 2:
			for (Road r : j.getInRoads()) {
				s += r.getId() + ":[";
				for (Vehicle v : r.getVehicles()) {
					s += v.getId() + ", ";
				}
				s = s.substring(0, s.length()-2) + "] ";
			}
			break;
			
		default:
			assert(false);
			break;
	}
	
		return s;
	}
	
	

}