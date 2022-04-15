package simulator.view;

import javax.swing.JPanel;
import javax.swing.JSpinner;

import simulator.model.RoadMap;
import simulator.model.Vehicle;

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

public class ChangeCO2ClassDialog extends JDialog{

	int _status;
	
	private JComboBox <Vehicle>_vehicleCB;
	private DefaultComboBoxModel<Vehicle> _vehicleCBm;
	private JComboBox <Integer>_integerCB;
	private DefaultComboBoxModel<Integer> _integerCBm;
	private JSpinner _tickSP;
	private RoadMap _rm;
	
	JLabel _label;
	
	public ChangeCO2ClassDialog(JFrame parent)
	{
		super(parent, true);
		initGUI();
	}
	
	private void initGUI()
	{		
		this.setTitle("Change Co2 Class");		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		//TODO
		mainPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		JLabel label = new JLabel(" Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now");
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
		
		_vehicleCBm = new DefaultComboBoxModel<>();
		_vehicleCB = new JComboBox<Vehicle>(_vehicleCBm);
		_integerCBm = new DefaultComboBoxModel<>();
		_integerCB = new JComboBox<Integer>(_integerCBm);
		_tickSP = new JSpinner();

		selectPanel.add(new JLabel("Vehicle: "));
		selectPanel.add(_vehicleCB);
		selectPanel.add(Box.createRigidArea(new Dimension(40, 0)));
		_vehicleCB.setPreferredSize(new Dimension(50, 20)); 
		
		selectPanel.add(new JLabel("CO2 Class: "));
		selectPanel.add(_integerCB);
		selectPanel.add(Box.createRigidArea(new Dimension(40, 0)));
		_integerCB.setPreferredSize(new Dimension(50, 20));
		
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
				if (_vehicleCB.getSelectedItem() != null) {
					ChangeCO2ClassDialog.this.setVisible(false);
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
				ChangeCO2ClassDialog.this.setVisible(false);
				_status = 0;
			}
		});
		
		for(int i = 0; i < 11; i++) 
		{
			_integerCBm.addElement(i);
		}
		
		
		setPreferredSize(new Dimension(650, 200));
		pack();
		setResizable(false);
		setVisible(false);
	}

	public int open(RoadMap map) {
		
		_vehicleCBm.removeAllElements();
		for(Vehicle v : map.getVehicles()) 
		{
			_vehicleCBm.addElement(v);
		} 
		
		setVisible(true);
		
		return _status;
	}
	
	public int getTicks()
	{	
		return (Integer) _tickSP.getValue();
	}
	
	public Vehicle getVehicle()
	{	
		return (Vehicle) _vehicleCBm.getSelectedItem();
	}
	
	public int getC02Class() 
	{
		return (Integer) _integerCBm.getSelectedItem();
	}
}

