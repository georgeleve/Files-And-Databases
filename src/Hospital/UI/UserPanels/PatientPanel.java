package Hospital.UI.UserPanels;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Hospital.Main;
import Hospital.Users.Patient;
import Hospital.Utilities.Utils;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

public class PatientPanel extends UserPanel{
	private DefaultListModel<String> model = new DefaultListModel<>();
	private JRadioButton doctorButtons[];
	private final int Height = 500, Width = 900;
	private JTextField symptomsField;
	public PatientPanel() {
		initialize();
	}
	private void initialize() {

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setTitle("Hospital - Patient Panel");
		frame.setVisible(true);
		frame.setBounds((int) (dim.getWidth() - Width) / 2, (int) (dim.getHeight() - Height) / 2, Width, Height);
		frame.setResizable(false);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		frame.getContentPane().add(tabbedPane);
		tabbedPane.setFont(new Font("Rockwell", Font.PLAIN, 20));
		tabbedPane.setBounds(0, 0, Width-15, Height-45);
		tabbedPane.setFocusable(false);
		
		// Chronic Diseases
		JPanel informationPanel = new JPanel();
		tabbedPane.addTab("Information", informationPanel);
		initInformationPanel(informationPanel);
		
		
		
		
		// Visit
		JPanel visitPanel = new JPanel();
		tabbedPane.addTab("Visit", visitPanel);
		initVisitPanel(visitPanel);
		visitPanel.setLayout(null);
		
	
		updateChronicDiseases();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void initVisitPanel(JPanel panel) {
		JLabel symptomsLabel = new JLabel("Describe your symptoms:");
		symptomsLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		symptomsLabel.setBounds(12, 55, 251, 24);
		
		
		JLabel chooseDoctorLabel = new JLabel("Choose doctor specialization:");
		chooseDoctorLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		chooseDoctorLabel.setBounds(12, 130, 303, 24);
		
		
		symptomsField = new JTextField();
		symptomsField.setFont(new Font("Rockwell", Font.PLAIN, 20));
		symptomsField.setBounds(300, 55, 403, 25);
		symptomsField.setColumns(10);
		
		String[] docSpecializations = new String[]{"Dermatologist","Pathologist","Psychiatrist","Otolaryngologist","Ophthalmologist"};
		doctorButtons = new JRadioButton[5];
		ButtonGroup bg = new ButtonGroup();
		for(int i = 0; i<5; i++) {
			doctorButtons[i] = new JRadioButton(docSpecializations[i]);
			doctorButtons[i].setFont(new Font("Rockwell", Font.PLAIN, 20));
			doctorButtons[i].setBounds(300, 130+30*i, 200, 25);
			bg.add(doctorButtons[i]);
			panel.add(doctorButtons[i]);
		}
		doctorButtons[0].setSelected(true);
		
		JButton findDoctorButton = new JButton("Find doctor on duty.");
		findDoctorButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		findDoctorButton.setBounds(226, 365, 314, 39);
		findDoctorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int docId = -1;
				for(int i = 0; i<5; i++) if(doctorButtons[i].isSelected()) docId = Main.shift.getDoctorIdOnDuty(i);
				((Patient)Main.user).visit(symptomsField.getText(), docId);
				Utils.sendNotification("Success!", "You were assigned to dc. "+Main.con.getDoctorNameByID(docId));
			}
		});
		panel.add(findDoctorButton);
		
		
		
		panel.add(symptomsField);
		panel.add(symptomsLabel);
		panel.add(chooseDoctorLabel);
	}
	
	public void initInformationPanel(JPanel panel) {
		Patient loggedIn = ((Patient)Main.user);
		String name = loggedIn.getName(), amka= loggedIn.getAMKA(), address = loggedIn.getAddress(), insurance=loggedIn.getInsurance();
		panel.setLayout(null);
		
		JLabel informationLabel = new JLabel("<html>Personal Information:<br/><br/>Name: "+name+"<br/>AMKA: "+amka+"<br/>Address: "+address+"<br/>Insurance: "+insurance+"<html/>");
		informationLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		informationLabel.setBounds(446, 98, 193, 265);
		
		JLabel chronicLabel = new JLabel("Chronic Diseases:");
		chronicLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		chronicLabel.setBounds(95, 25, 180, 16);
		
		JList<String> chronicList = new JList<String>(model);
		chronicList.setFont(new Font("Rockwell", Font.PLAIN, 20));
		chronicList.setBounds(30, 55, 300, 333);
		panel.add(chronicList);
		
		JButton addDiseaseButton = new JButton("Add Chronic Disease");
		addDiseaseButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		addDiseaseButton.setBounds(30, 401, 300, 25);
		addDiseaseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String disease = Utils.request("Chronic Disease Name:");
				if(disease==null) return;
				((Patient)Main.user).addCronicDisease(disease);
				updateChronicDiseases();
				Utils.sendNotification("Success!", "Chronic disease added to your history");
			}
		});
		panel.add(addDiseaseButton);
		
		panel.add(chronicList);
		panel.add(chronicLabel);
		panel.add(informationLabel);
	}
	
	public void updateChronicDiseases() {
		ArrayList<String> diseases = ((Patient)Main.user).getChronicDiseases();
		if(diseases.isEmpty()) diseases.add("No chronic diseases found.");
		model.clear();
		for(String s : diseases) model.addElement(s);
	}
}
