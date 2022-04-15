package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Road;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver{

	Controller _ctrl;
	List<Road> _roads = new ArrayList<Road>();
	String _cols[] = {"Id", "Length", "Weather", "Max. Speed", "Speed Limit", "Total CO2", "CO2 Limit"};
	
	public RoadsTableModel(Controller ctrl)
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
		_roads = map.getRoads();
		fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		_roads = map.getRoads();
		fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_roads = map.getRoads();
		fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_roads = map.getRoads();
		fireTableDataChanged();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getRowCount() {
		return _roads.size();
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
		Road r = _roads.get(rowIndex);
	switch ( columnIndex ) {
		case 0:
				s = "" + r.getId();
				break;
		case 1:
				s = Integer.toString(r.getLength());
				break;
				
		case 2:
			switch (r.getWeather()) {
				case SUNNY:
					s = "SUNNY";
					break;
				case RAINY:
					s = "RAINY";
					break;
				case WINDY:
					s = "WINDY";
					break;
				case CLOUDY:
					s = "CLOUDY";
					break;
				case STORM:
					s = "STORM";
					break;
			}
			break;
			
		case 3:
				s = Integer.toString(r.getLength());
				break;
			
		case 4:
				s = Integer.toString(r.getMaxSpeed());
				break;
			
		case 5:
				s = Integer.toString(r.getSpeedLimit());
				break;
				
		case 6:
				s = Integer.toString(r.getTotalCO2());
				break;
				
		case 7:
			s = Integer.toString(r.getContLimit());
			break;
			
		default:
			assert(false);
	}
	
		return s;
	}
	
	

}
