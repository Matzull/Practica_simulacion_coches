package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.RoadMap;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.Weather;
import java.lang.Thread;

public class ControlPanel extends JPanel implements TrafficSimObserver{
	
	Controller _ctrl;
	RoadMap _roadMap;
	int _timeStep;
	boolean _stopped;
	
	JButton _fileImportB;
	JFileChooser _filec;
	File _file;
	
	JButton _changeCO2B;
	JButton _changeWeatherB;
	JButton _runB;
	JButton _stopB;
	JButton _exit;
	
	JSpinner _ticks;
	
	JToolBar _toolBar;
	
	ChangeCO2ClassDialog _changeCo2d;
	ChangeWeatherClassDialog _changeWeatherd;

	public ControlPanel(Controller ctrl) {
		this._ctrl = ctrl;
		this.setLayout(new BorderLayout());
		_changeCo2d = new ChangeCO2ClassDialog((JFrame) this.getTopLevelAncestor());
		_changeWeatherd = new ChangeWeatherClassDialog((JFrame) this.getTopLevelAncestor());
		_filec = new JFileChooser(new File("resources/examples2"));
		_ticks = new JSpinner(new SpinnerNumberModel());
		_stopped = false;
		_ctrl.addObserver(this);
		initGUI();
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_roadMap = map;
		_timeStep = time;
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_roadMap = map;
		_timeStep = time;		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_roadMap = map;
		_timeStep = time;		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

	private void initGUI()
	{
		_toolBar = new JToolBar(BorderLayout.PAGE_START);
		this.add(_toolBar);
		
		_fileImportB = new JButton();
		_toolBar.add(_fileImportB);
		_fileImportB.setToolTipText("Import file");
		_fileImportB.setIcon(new ImageIcon("resources/icons/open.png"));
		_fileImportB.addActionListener(new ActionListener () 
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				loadFile();				
			}
		});
		_toolBar.addSeparator();
		
		_changeCO2B = new JButton();
		_toolBar.add(_changeCO2B);
		_changeCO2B.setToolTipText("Change Co2 Class");
		_changeCO2B.setIcon(new ImageIcon("resources/icons/co2class.png"));
		_changeCO2B.addActionListener(new ActionListener () 
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				changeCo2Class();				
			}
		});
		
		_changeWeatherB = new JButton();
		_toolBar.add(_changeWeatherB);
		_changeWeatherB.setToolTipText("Change Wheather");
		_changeWeatherB.setIcon(new ImageIcon("resources/icons/weather.png"));
		_changeWeatherB.addActionListener(new ActionListener () 
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				changeWeather();				
			}
		});
		_toolBar.addSeparator();
		
		_runB = new JButton();
		_toolBar.add(_runB);
		_runB.setToolTipText("Run simulation");
		_runB.setIcon(new ImageIcon("resources/icons/run.png"));
		_runB.addActionListener(new ActionListener () 
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				start();				
			}
		});
		
		_stopB = new JButton();
		_toolBar.add(_stopB);
		_stopB.setToolTipText("Stop simulation");
		_stopB.setIcon(new ImageIcon("resources/icons/stop.png"));
		_stopB.addActionListener(new ActionListener () 
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				stop();				
			}
		});
		
		_toolBar.addSeparator();
		_toolBar.add((new JLabel("Ticks: ")));
		_toolBar.add(_ticks);
		_ticks.setToolTipText("Ticks");
		_ticks.setMaximumSize(new Dimension(80, 40));
		_ticks.setMinimumSize(new Dimension(80, 40));
		_ticks.setPreferredSize(new Dimension(80, 40));
		
		_toolBar.add(Box.createGlue());
		_toolBar.addSeparator();
		
		_exit = new JButton();
		_toolBar.add(_exit); 
		_exit.setToolTipText("Exit the program");
		_exit.setIcon(new ImageIcon("resources/icons/exit.png"));
		_exit.addActionListener(new ActionListener () 
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showOptionDialog(null, "Exit the program", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null) == 0) {
					System.exit(0);
				}				
			}
		});
		
}
	
	private void loadFile()
	{
		if(_filec.showOpenDialog(_filec) == JFileChooser.APPROVE_OPTION)
		{	
			_file = _filec.getSelectedFile();
			try {
				_ctrl.reset();
				_ctrl.loadEvents(new FileInputStream(_file));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Cannot load file", "error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void changeCo2Class()
	{
		int _status = _changeCo2d.open(_roadMap);
		if(_status == 1)
		{
			ArrayList<Pair<String, Integer>> _pairlist = new ArrayList<Pair<String, Integer>>();
			_pairlist.add(new Pair(_changeCo2d.getVehicle().getId(), _changeCo2d.getC02Class()));
			try {
				_ctrl.addEvent(new NewSetContClassEvent(_timeStep + _changeCo2d.getTicks(), _pairlist));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void changeWeather()
	{
		int _status = _changeWeatherd.open(_roadMap);
		if(_status == 1)
		{
			ArrayList<Pair<String, Weather>> _pairlist = new ArrayList<Pair<String, Weather>>();
			_pairlist.add(new Pair(_changeWeatherd.getRoad().getId(),_changeWeatherd.getWeather()));
			try {
				_ctrl.addEvent(new SetWeatherEvent(_timeStep + _changeWeatherd.getTicks(), _pairlist));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void start() 
	{
		_stopped = false;
		_fileImportB.setEnabled(false);
		_changeCO2B.setEnabled(false);
		_changeWeatherB.setEnabled(false);
		_runB.setEnabled(false);
		run_sim((Integer)_ticks.getValue());
	}
	
	private void stop() 
	{
		_stopped = true;
		_fileImportB.setEnabled(true);
		_changeCO2B.setEnabled(true);
		_changeWeatherB.setEnabled(true);
		_runB.setEnabled(true);
	}
	
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try 
			{
				_ctrl.run();
			} 
			catch (Exception e) {
				_stopped = true;
				JOptionPane.showMessageDialog(this, "Cannot run sim", "error", JOptionPane.ERROR_MESSAGE);
			}
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override	
				public void run() 
				{
					run_sim(n - 1);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} 
		else {
			enableToolBar(true);
			stop();
		}
	}
	
	private void enableToolBar(boolean bool) 
	{
		_toolBar.setEnabled(bool);
	}
	
}
