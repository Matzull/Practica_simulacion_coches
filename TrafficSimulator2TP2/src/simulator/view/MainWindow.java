package simulator.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	private Controller _ctrl;
	
	public MainWindow(Controller ctrl) 
	{
		super("Traffic Simulator");
		_ctrl = ctrl;
		initGUI();
	}
	
	private void initGUI() 
	{
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
		mainPanel.add(new StatusBar(_ctrl),BorderLayout.PAGE_END);
		JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);
		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);
		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);
		// tables
		JPanel eventsView = createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events");
		eventsView.setPreferredSize(new Dimension(500, 200));
		Border EBorder = new TitledBorder(new EtchedBorder(), "Events");
        eventsView.setBorder(EBorder);
		tablesPanel.add(eventsView);
		/////////////////////////////
		JPanel vehiclesView = createViewPanel(new JTable(new VehiclesTableModel(_ctrl)), "Vehicles");
		vehiclesView.setPreferredSize(new Dimension(500, 200));
		Border VBorder = new TitledBorder(new EtchedBorder(), "Vehicles");
		vehiclesView.setBorder(VBorder);
		tablesPanel.add(vehiclesView);
		////////////////////////////
		JPanel roadsView = createViewPanel(new JTable(new RoadsTableModel(_ctrl)), "Roads");
		roadsView.setPreferredSize(new Dimension(500, 200));
		Border RBorder = new TitledBorder(new EtchedBorder(), "Roads");
		roadsView.setBorder(RBorder);
		tablesPanel.add(roadsView);
		////////////////////////////
		JPanel junctionsView = createViewPanel(new JTable(new JunctionsTableModel(_ctrl)), "Junctions");
		junctionsView.setPreferredSize(new Dimension(500, 200));
		Border JBorder = new TitledBorder(new EtchedBorder(), "Junctions");
		junctionsView.setBorder(JBorder);
		tablesPanel.add(junctionsView);
		// maps
		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
		mapView.setPreferredSize(new Dimension(500, 400));
		Border MBorder = new TitledBorder(new EtchedBorder(), "Map");
		mapView.setBorder(MBorder);
		mapsPanel.add(mapView);
		///////////////////////////
		JPanel mapView2 = createViewPanel(new MapByRoadComponent(_ctrl), "Map by Road");
		mapView2.setPreferredSize(new Dimension(500, 400));
		Border MRBorder = new TitledBorder(new EtchedBorder(), "Map by Road");
		mapView2.setBorder(MRBorder);
		mapsPanel.add(mapView2);
		//////////////////////////
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	
	private JPanel createViewPanel(JComponent c, String title) 
	{
		JPanel p = new JPanel( new BorderLayout() );
		// TODO add a framed border to p with title
		p.add(new JScrollPane(c));
		return p;
	}
}
