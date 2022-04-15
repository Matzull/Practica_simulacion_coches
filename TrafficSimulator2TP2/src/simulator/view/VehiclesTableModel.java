package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver{

	Controller _ctrl;
	List<Vehicle> _vehicles = new ArrayList<Vehicle>();
	String _cols[] = {"Id", "Status", "Itinerary", "CO2 class", "Max Speed", "Speed", "Total CO2", "Distance"};
	
	public VehiclesTableModel(Controller ctrl)
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
		_vehicles = map.getVehicles();
		fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		_vehicles = map.getVehicles();
		fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_vehicles = map.getVehicles();
		fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_vehicles = map.getVehicles();
		fireTableDataChanged();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getRowCount() {
		return _vehicles.size();
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
		Vehicle v = _vehicles.get(rowIndex);
	switch ( columnIndex ) {
		case 0:
				s = "" + v.getId();
				break;
				
		case 1:
			switch (v.getStatus()) {
				case PENDING:
					s = "Pending";
					break;
				case TRAVELING:
					s = v.getRoad().getId() + ":" +v.getLocation();
					break;
				case WAITING:
					s = "Waiting:"+v.getRoad().getDest().getId();
					break;
				case ARRIVED:
					s = "Arrived";
					break;
			}
			break;
			
		case 2:
			s = "[";//work on this
			for(Junction j : v.getItinerary())
			{
				s += j.getId() + ", ";
			}
			s = s.substring(0, s.length()-2) + "]";
			break;
			
		case 3:
			s = Integer.toString(v.getContClass());
			break;
			
		case 4: 
			s = Integer.toString(v.getMaxSpeed());
			break;
			
		case 5: 
			s = Integer.toString(v.getSpeed());
			break;
		
		case 6:
			s = Integer.toString(v.getTotalCO2());
			break;
			
		case 7:
			s = Integer.toString(v.getTotalDistance());
			break;
			
		default:
			assert(false);
			break;
	}
	
		return s;
	}
	
	

}