package simulator.view;

import javax.swing.JPanel;
import javax.swing.JSpinner;

import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.Weather;

import javax.swing.AbstractSpinnerModel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ChangeWeatherClassDialog extends JDialog{

	int _status;
	
	private JComboBox <Road>_roadCB;
	private DefaultComboBoxModel<Road> _roadCBm;
	private JComboBox <Weather>_weatherCB;
	private DefaultComboBoxModel<Weather> _weatherCBm;
	private JSpinner _tickSP;
	private RoadMap _rm;
	
	JLabel _label;
	
	public ChangeWeatherClassDialog(JFrame parent)
	{
		super(parent, true);
		initGUI();
	}
	
	private void initGUI()
	{		
		this.setTitle("Change Weather");		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		//TODO
		mainPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		JLabel label = new JLabel(" Schedule an event to change the weather of a road after a given number of simulation ticks from now");
		label.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(label);
		setContentPane(mainPanel);
		
		
		JPanel selectPanel = new JPanel();
		selectPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		mainPanel.add(selectPanel);
		
		JPanel confirmPanel = new JPanel();
		confirmPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(confirmPanel);
		
		_roadCBm = new DefaultComboBoxModel<>();
		_roadCB = new JComboBox<Road>(_roadCBm);
		_weatherCBm = new DefaultComboBoxModel<>();
		_weatherCB = new JComboBox<Weather>(_weatherCBm);
		_tickSP = new JSpinner();

		selectPanel.add(new JLabel("Road: "));
		selectPanel.add(_roadCB);
		selectPanel.add(Box.createRigidArea(new Dimension(40, 0)));
		_roadCB.setPreferredSize(new Dimension(50, 20)); 
		
		selectPanel.add(new JLabel("Weather: "));
		selectPanel.add(_weatherCB);
		selectPanel.add(Box.createRigidArea(new Dimension(40, 0)));
		_weatherCB.setPreferredSize(new Dimension(75, 20));
		
		selectPanel.add(new JLabel("Ticks: "));
		selectPanel.add(_tickSP);
		_tickSP.setPreferredSize(new Dimension(50, 20));
		
		confirmPanel.add(Box.createRigidArea(new Dimension(40, 0)));
		JButton okButton = new JButton("OK");
		confirmPanel.add(okButton);
		okButton.addActionListener(new ActionListener () 
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if (_roadCB.getSelectedItem() != null) {
					ChangeWeatherClassDialog.this.setVisible(false);
					_status = 1;
				}					
			}
		});

		confirmPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		
		JButton cancelButton = new JButton("Cancel");
		confirmPanel.add(cancelButton);
		cancelButton.addActionListener(new ActionListener () 
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeWeatherClassDialog.this.setVisible(false);
				_status = 0;
			}
		});
		
		for(Weather w : Weather.values()) 
		{
			_weatherCBm.addElement(w);
		}
		
		setPreferredSize(new Dimension(650, 200));
		pack();
		setResizable(false);
		setVisible(false);
	}

	public int open(RoadMap map) {
		
		_roadCBm.removeAllElements();
		for(Road r : map.getRoads()) 
		{
			_roadCBm.addElement(r);
		} 
		
		setVisible(true);
		
		return _status;
	}
	
	public int getTicks()
	{	
		return (Integer) _tickSP.getValue();
	}
	
	public Road getRoad()
	{	
		return (Road) _roadCBm.getSelectedItem();
	}
	
	public Weather getWeather() 
	{
		return (Weather) _weatherCBm.getSelectedItem();
	}
}

