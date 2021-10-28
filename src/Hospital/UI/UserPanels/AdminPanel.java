package Hospital.UI.UserPanels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import Hospital.Main;
import Hospital.Users.Admin;
import Hospital.Utilities.Utils;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;

public class AdminPanel extends UserPanel{
	private final int Height = 600, Width = 1100;
	public AdminPanel() {
		initialize();
	}
	private void initialize() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setTitle("Hospital - Admin Panel");
		frame.setVisible(true);
		frame.setBounds((int) (dim.getWidth() - Width) / 2, (int) (dim.getHeight() - Height) / 2, Width, Height);
		frame.setResizable(false);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		frame.getContentPane().add(tabbedPane);
		tabbedPane.setFont(new Font("Rockwell", Font.PLAIN, 20));
		tabbedPane.setBounds(0, 0, Width, Height);
		tabbedPane.setFocusable(false);
		
		
		JPanel customQueryPanel = new JPanel();
		tabbedPane.addTab("Custom Query", customQueryPanel);
		initCustomQueryPanel(customQueryPanel);
		
		
		JPanel visitsTodayQueryPanel = new JPanel();
		tabbedPane.addTab("Visits Today", visitsTodayQueryPanel);
		initVisitsTodayQueryPanel(visitsTodayQueryPanel);
		
		JPanel shiftStatsQueryPanel = new JPanel();
		tabbedPane.addTab("Shift Statistics", shiftStatsQueryPanel);
		initshiftStatsQueryPanel(shiftStatsQueryPanel);
		
		JPanel covid19Panel = new JPanel();
		tabbedPane.addTab("Covid-19 Reports", covid19Panel);
		initCovidPanel(covid19Panel);
		
		JPanel staffShiftsPanel = new JPanel();
		tabbedPane.addTab("Staff Shifts", staffShiftsPanel);
		initStaffShiftsPanel(staffShiftsPanel);
		
		JPanel generateTomorrowPanel = new JPanel();
		tabbedPane.addTab("Next Day Shift", generateTomorrowPanel);
		initTomorrowPanel(generateTomorrowPanel);
		
	}
	
	public void initStaffShiftsPanel(JPanel panel) {
		panel.setLayout(null);
		
		JTextArea staffShiftsArea = new JTextArea();
		staffShiftsArea.setEditable(false);
		staffShiftsArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		JScrollPane scrollPane = new JScrollPane(staffShiftsArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(38, 179, 775, 340);
		panel.add(scrollPane);
		
		JCheckBox displayAll = new JCheckBox("Display all");
		displayAll.setFont(new Font("Rockwell", Font.PLAIN, 20));
		displayAll.setBounds(437, 115, 150, 25);
		panel.add(displayAll);

		String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		JTextField startDateField = new JTextField(timeStamp);
		startDateField.setFont(new Font("Rockwell", Font.PLAIN, 20));
		startDateField.setColumns(10);
		startDateField.setBounds(437, 39, 150, 25);
		panel.add(startDateField);
		
		JTextField endDateField = new JTextField(timeStamp);
		endDateField.setFont(new Font("Rockwell", Font.PLAIN, 20));
		endDateField.setColumns(10);
		endDateField.setBounds(437, 81, 150, 25);
		panel.add(endDateField);

		JLabel formLabel = new JLabel("Day in year-month-day form.");
		formLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
		formLabel.setBounds(418, 13, 207, 24);
		panel.add(formLabel);
		
		JLabel fromLabel = new JLabel("From:");
		fromLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
		fromLabel.setBounds(388, 41, 49, 24);
		panel.add(fromLabel);
		
		JLabel upToLabel = new JLabel("Up to:");
		upToLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
		upToLabel.setBounds(388, 83, 49, 24);
		panel.add(upToLabel);
		
		JButton staffShiftsButton = new JButton("View staff shifts");
		staffShiftsButton.setBounds(38, 39, 265, 67);
		staffShiftsButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		staffShiftsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String start = startDateField.getText(), end = endDateField.getText();
				if(!(Utils.isValidDate(start)&&Utils.isValidDate(end))) {
					Utils.sendError("Error!", "Invalid date format!");
					return;
				}
				((Admin) Main.user).displayStaffShiftsReports(staffShiftsArea, start, end, displayAll.isSelected());
			}
		});
		panel.add(staffShiftsButton);
		
		JLabel queryResultLabel = new JLabel("Query Result:");
		queryResultLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		queryResultLabel.setBounds(38, 142, 134, 24);
		panel.add(queryResultLabel);
		
		panel.repaint();
	}
	public void initCovidPanel(JPanel panel) {
		panel.setLayout(null);
		
		JTextArea covidArea = new JTextArea();
		covidArea.setEditable(false);
		covidArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		JScrollPane scrollPane = new JScrollPane(covidArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(38, 179, 775, 340);
		panel.add(scrollPane);
		
		JButton covidButton = new JButton("View covid diagnoses");
		covidButton.setBounds(38, 39, 265, 67);
		covidButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		covidButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((Admin) Main.user).dispayCovidReports(covidArea);
			}
		});
		panel.add(covidButton);

		JLabel queryResultLabel = new JLabel("Query Result:");
		queryResultLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		queryResultLabel.setBounds(38, 142, 134, 24);
		panel.add(queryResultLabel);
		
		panel.repaint();
	}
	public void initTomorrowPanel(JPanel panel) {
		panel.setLayout(null);
		JButton shiftStatsButton = new JButton("Generate Tomorrow's Shift");
		shiftStatsButton.setBounds(291, 239, 326, 67);
		shiftStatsButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		shiftStatsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Main.shift.registerTommorow()) Utils.sendNotification("Success!", "The shift for tomorrow was generated!");
				else Utils.sendError("Error!", "Tommorow's shift is already generated!");
			}
		});
		panel.add(shiftStatsButton);
	}
	
	public void initshiftStatsQueryPanel(JPanel panel) {
		panel.setLayout(null);
		
		
		JRadioButton daily = new JRadioButton("Daily Statistics");
		daily.setFont(new Font("Rockwell", Font.PLAIN, 20));
		daily.setSelected(true);
		daily.setBounds(288, 53, 207, 25);
		panel.add(daily);

		JRadioButton monthly = new JRadioButton("Monthly Statistics");
		monthly.setFont(new Font("Rockwell", Font.PLAIN, 20));
		monthly.setBounds(288, 83, 207, 25);
		panel.add(monthly);
		
		JLabel dayInfoLabel = new JLabel("Day in year-month-day form.");
		dayInfoLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
		dayInfoLabel.setBounds(474, 20, 207, 24);
		panel.add(dayInfoLabel);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(monthly);
		bg.add(daily);
		
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		JTextField dateField = new JTextField(timeStamp);
		dateField.setFont(new Font("Rockwell", Font.PLAIN, 20));
		dateField.setColumns(10);
		dateField.setBounds(503, 52, 150, 25);
		panel.add(dateField);
		
		JTextArea shiftsArea = new JTextArea("");
		shiftsArea.setEditable(false);
		shiftsArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		JScrollPane scrollPane = new JScrollPane(shiftsArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(38, 179, 775, 340);
		panel.add(scrollPane);
		
		JButton shiftStatsButton = new JButton("View visits today");
		shiftStatsButton.setBounds(38, 39, 230, 67);
		shiftStatsButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		shiftStatsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(daily.isSelected()) {
					String date = dateField.getText();
					if(!Utils.isValidDate(date)) {
						Utils.sendError("Error!", "Invalid date format!");
						return;
					}
					((Admin) Main.user).displayDailyStats(date, shiftsArea);
				}else ((Admin) Main.user).displayMonthlyStats(shiftsArea);
			}

		});
		panel.add(shiftStatsButton);
		
		JLabel queryResultLabel = new JLabel("Query Result:");
		queryResultLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		queryResultLabel.setBounds(38, 142, 134, 24);
		panel.add(queryResultLabel);
	
		panel.repaint();
	}
	
	public void initVisitsTodayQueryPanel(JPanel panel) {
		panel.setLayout(null);
		
		JTextArea visitsArea = new JTextArea();
		visitsArea.setEditable(false);
		visitsArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		JScrollPane scrollPane = new JScrollPane(visitsArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(38, 179, 775, 340);
		panel.add(scrollPane);
		
		JButton visitsTodayButton = new JButton("View visits today");
		visitsTodayButton.setBounds(38, 39, 230, 67);
		visitsTodayButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		visitsTodayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((Admin) Main.user).displayVisitsToday(visitsArea);
			}
		});
		panel.add(visitsTodayButton);
		
		JLabel queryResultLabel = new JLabel("Query Result:");
		queryResultLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		queryResultLabel.setBounds(38, 142, 134, 24);
		panel.add(queryResultLabel);
		
		panel.repaint();
	}
	
	public void initCustomQueryPanel(JPanel panel) {
		panel.setLayout(null);
		JLabel queryLabel = new JLabel("Insert Query:");
		queryLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		queryLabel.setBounds(38, 28, 134, 24);
		panel.add(queryLabel);
		
		JTextField queryField = new JTextField();
		queryField.setFont(new Font("Rockwell", Font.PLAIN, 20));
		queryField.setColumns(10);
		queryField.setBounds(38, 65, 576, 25);
		panel.add(queryField);
		
		JTextArea customArea = new JTextArea();
		customArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		customArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(customArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(38, 179, 775, 340);
		panel.add(scrollPane);
		
		JButton sendQueryButton = new JButton("Send Query");
		sendQueryButton.setBounds(35, 103, 230, 25);
		sendQueryButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		sendQueryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((Admin) Main.user).displayCustomQuery(queryField.getText(), customArea);
			}
		});
		panel.add(sendQueryButton);
		
		JLabel queryResultLabel = new JLabel("Query Result:");
		queryResultLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		queryResultLabel.setBounds(38, 142, 134, 24);
		panel.add(queryResultLabel);
		
		panel.repaint();
	}
}
