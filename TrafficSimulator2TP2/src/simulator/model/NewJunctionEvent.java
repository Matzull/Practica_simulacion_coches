package simulator.model;

public class NewJunctionEvent extends Event{

	private String id;
	private int xCoor;
	private int yCoor;
	private LightSwitchingStrategy lStrategy;
	private DequeuingStrategy dqStrategy;
	
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(time);
		this.id = id;
		this.lStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
	}

	@Override
	void execute(RoadMap map) {
		map.addJunction(new Junction(id, lStrategy, dqStrategy, xCoor, yCoor));		
	}

	@Override
	public String toString() 
	{
		return "New Junction " + id;
	}
}
