package Hospital.UI.UserPanels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import Hospital.Main;
import Hospital.Users.Doctor;
import Hospital.Utilities.Utils;

import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;

public class DoctorPanel extends UserPanel{

	private final int Height = 500, Width = 900;
	public DoctorPanel() {
		initialize();
	}
	private void initialize() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setTitle("Hospital - Doctor Panel");
		frame.setVisible(true);
		frame.setBounds((int) (dim.getWidth() - Width) / 2, (int) (dim.getHeight() - Height) / 2, Width, Height);
		frame.setResizable(false);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		frame.getContentPane().add(tabbedPane);
		tabbedPane.setFont(new Font("Rockwell", Font.PLAIN, 20));
		tabbedPane.setBounds(0, 0, Width-15, Height-45);
		tabbedPane.setFocusable(false);
		
		
		JPanel diagnosePanel = new JPanel();
		tabbedPane.addTab("Diagnose Patient", diagnosePanel);
		initDiagnosePanel(diagnosePanel);
		
		
		JPanel prescriptionPanel = new JPanel();
		tabbedPane.addTab("Prescription", prescriptionPanel);
		initPrescriptionPanel(prescriptionPanel);
		
		JPanel examinationPanel = new JPanel();
		tabbedPane.addTab("Re-examination", examinationPanel);
		initExaminationPanel(examinationPanel);
		
		
	}
	
	public void initExaminationPanel(JPanel panel) {
		panel.setLayout(null);
		
		//Report Id
		JLabel reportIDLabel = new JLabel("Report ID:");
		reportIDLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		reportIDLabel.setBounds(15, 20+40*0, 160, 24);
		panel.add(reportIDLabel);
		
		JTextField reportIDField = new JTextField();
		reportIDField.setFont(new Font("Rockwell", Font.PLAIN, 20));
		reportIDField.setBounds(15, 20+40*1-10, 270, 28);
		reportIDField.setColumns(10);
		panel.add(reportIDField);
		
		//Amka
		JLabel patientAmkaLabel = new JLabel("Patient AMKA:");
		patientAmkaLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		patientAmkaLabel.setBounds(15, 20+40*2, 190, 24);
		panel.add(patientAmkaLabel);
		
		JTextField patientAmkaField = new JTextField("Insert report id to load.");
		patientAmkaField.setFont(new Font("Rockwell", Font.PLAIN, 20));
		patientAmkaField.setColumns(10);
		patientAmkaField.setBounds(15, 20+40*3-10, 270, 28);
		patientAmkaField.setEditable(false);
		panel.add(patientAmkaField);
		
		//Doctor notes
		JLabel doctorNotesLabel = new JLabel("Notes:");
		doctorNotesLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		doctorNotesLabel.setBounds(15, 20+40*4, 236, 24);
		panel.add(doctorNotesLabel);
		
		JTextField doctorNotesField = new JTextField();
		doctorNotesField.setFont(new Font("Rockwell", Font.PLAIN, 20));
		doctorNotesField.setColumns(10);
		doctorNotesField.setBounds(15, 20+40*5-10, 270, 28);
		panel.add(doctorNotesField);
		
		//treatment
		JLabel treatmentLabel = new JLabel("Treatment (if required):");
		treatmentLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		treatmentLabel.setBounds(15, 20+40*6, 236, 24);
		panel.add(treatmentLabel);
		
		JTextField treatmentField = new JTextField();
		treatmentField.setFont(new Font("Rockwell", Font.PLAIN, 20));
		treatmentField.setColumns(10);
		treatmentField.setBounds(15, 20+40*7-10, 270, 28);
		panel.add(treatmentField);
		
		//Nurse report
		JLabel reportSummaryLabel = new JLabel("Nurse report summary:");
		reportSummaryLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		reportSummaryLabel.setBounds(349, 100, 236, 24);
		panel.add(reportSummaryLabel);
		JTextField reportSummaryField = new JTextField("Insert report id to load.");
		reportSummaryField.setFont(new Font("Rockwell", Font.PLAIN, 20));
		reportSummaryField.setEditable(false);
		reportSummaryField.setColumns(10);
		reportSummaryField.setBounds(349, 130, 270, 28);
		panel.add(reportSummaryField);
		
		
		//submit examination
		JButton submitExaminationButton = new JButton("Submit Examination");
		submitExaminationButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		submitExaminationButton.setBounds(15, 350, 271, 46);
		submitExaminationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] a = Main.con.getReportData(reportIDField.getText());
				if(a==null) {
					Utils.sendError("Error!", "Invalid report id!");
					return;
				}
				if(!((Doctor)Main.user).examination(doctorNotesField.getText(), treatmentField.getText(), a[0], Integer.parseInt(reportIDField.getText()))) {
					Utils.sendError("Error!", "Could not store examination!");
					return;
				}
				Utils.sendNotification("Success!", "Examination submited!");
			}
		});
		panel.add(submitExaminationButton);
		
		JButton loadReportButton = new JButton("Load Report.");
		loadReportButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		loadReportButton.setBounds(335, 51, 363, 28);
		loadReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] a = Main.con.getReportData(reportIDField.getText());
				if(a==null) {
					Utils.sendError("Error!", "Invalid report id!");
					return;
				}
				patientAmkaField.setText(a[0]);
				reportSummaryField.setText(a[1]);
			}
		});

		panel.add(loadReportButton);
		
		panel.repaint();
	}
	public void initDiagnosePanel(JPanel panel) {
		panel.setLayout(null);
		
		JLabel patientAmkaLabel = new JLabel("Patient's AMKA:");
		patientAmkaLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		patientAmkaLabel.setBounds(15, 20+40*0, 160, 24);
		panel.add(patientAmkaLabel);
		
		JTextField amkaField = new JTextField();
		amkaField.setFont(new Font("Rockwell", Font.PLAIN, 20));
		amkaField.setBounds(15, 20+40*1-10, 270, 28);
		amkaField.setColumns(10);
		panel.add(amkaField);
		
		JLabel diagnoseLabel = new JLabel("Diagnose Summary:");
		diagnoseLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		diagnoseLabel.setBounds(15, 20+40*2, 190, 24);
		panel.add(diagnoseLabel);
		
		JTextField diagnoseField = new JTextField();
		diagnoseField.setFont(new Font("Rockwell", Font.PLAIN, 20));
		diagnoseField.setColumns(10);
		diagnoseField.setBounds(15, 20+40*3-10, 270, 28);
		panel.add(diagnoseField);
		
		JLabel diseaseLabel = new JLabel("Disease:");
		diseaseLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		diseaseLabel.setBounds(15, 20+40*4, 190, 24);
		panel.add(diseaseLabel);
		
		JComboBox<String> comboBox = new JComboBox<String>(loadDiseases());
		comboBox.setFont(new Font("Rockwell", Font.PLAIN, 20));
		comboBox.setBounds(15, 20+40*5-10, 270, 22);
		panel.add(comboBox);
		
		
		JButton submitDiagnoseButton = new JButton("Submit Diagnose");
		submitDiagnoseButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		submitDiagnoseButton.setBounds(15, 271, 271, 46);
		submitDiagnoseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!Main.con.patientExists(amkaField.getText())) {
					Utils.sendError("Error!", "No Patient with such AMKA found.");
					return;
				}
				
				if(!((Doctor) Main.user).diagnose(diagnoseField.getText(), amkaField.getText(), String.valueOf(comboBox.getSelectedItem()))) {
					Utils.sendError("Error!", "Error on submitting diagnose.");
					return;
				}
				Utils.sendNotification("Success!", "Diagnose submited.");
			}
		});
		panel.add(submitDiagnoseButton);
		
		 DefaultListModel<String> model = new DefaultListModel<>();
		JList<String> list = new JList<String>(model);
		list.setFont(new Font("Rockwell", Font.PLAIN, 20));
		list.setBounds(343, 56, 300, 333);
		panel.add(list);
		
		
		JButton loadChronicDiseasesButton = new JButton("Load Patients Chronic Diseases ");
		loadChronicDiseasesButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		loadChronicDiseasesButton.setBounds(316, 15, 363, 28);
		loadChronicDiseasesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!Main.con.patientExists(amkaField.getText())) {
					Utils.sendError("Error!", "No Patient with such AMKA found.");
					return;
				}
				ArrayList<String> result = Main.con.getChronicDiseases(amkaField.getText());
				model.clear();
				for(String s : result) model.addElement(s);
			}
		});
		panel.add(loadChronicDiseasesButton);
		
		JButton sendForExaminationButton = new JButton("Send for examination");
		sendForExaminationButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		sendForExaminationButton.setBounds(15, 343, 271, 46);
		sendForExaminationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!Main.con.patientExists(amkaField.getText())) {
					Utils.sendError("Error!", "No Patient with such AMKA found.");
					return;
				}
				String nurse = Main.con.getNurseNameByID(Main.shift.getNurseOnDuty());
				Utils.sendNotification("Success!", "Nurse "+nurse+" is availiable to examinate the patient!");
			}
		});
		panel.add(sendForExaminationButton);
		
		panel.repaint();
	}
	
	public void initPrescriptionPanel(JPanel panel) {
		panel.setLayout(null);
		
		JLabel patientAmkaLabel = new JLabel("Patient's AMKA:");
		patientAmkaLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		patientAmkaLabel.setBounds(15, 20+40*0, 160, 24);
		panel.add(patientAmkaLabel);
		
		JTextField amkaFieldP = new JTextField();
		amkaFieldP.setFont(new Font("Rockwell", Font.PLAIN, 20));
		amkaFieldP.setBounds(15, 20+40*1-10, 270, 28);
		amkaFieldP.setColumns(10);
		panel.add(amkaFieldP);
		
		JLabel notesLabel = new JLabel("Doctor Notes:");
		notesLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		notesLabel.setBounds(15, 20+40*2, 190, 24);
		panel.add(notesLabel);
		
		JTextField doctorNotesField = new JTextField();
		doctorNotesField.setFont(new Font("Rockwell", Font.PLAIN, 20));
		doctorNotesField.setColumns(10);
		doctorNotesField.setBounds(15, 20+40*3-10, 270, 28);
		panel.add(doctorNotesField);
		
		JLabel medicineLabel = new JLabel("Medicine:");
		medicineLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		medicineLabel.setBounds(15, 20+40*4, 190, 24);
		panel.add(medicineLabel);
		
		JComboBox<String> comboBox = new JComboBox<String>(loadMedicines());
		comboBox.setFont(new Font("Rockwell", Font.PLAIN, 20));
		comboBox.setBounds(15, 20+40*5-10, 270, 22);
		panel.add(comboBox);
		
		
		JButton submitPrescriptionButton = new JButton("Submit Prescription");
		submitPrescriptionButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		submitPrescriptionButton.setBounds(15, 298, 271, 46);
		submitPrescriptionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!Main.con.patientExists(amkaFieldP.getText())) {
					Utils.sendError("Error!", "No Patient with such AMKA found.");
					return;
				}
				
				if(!((Doctor) Main.user).prescription(doctorNotesField.getText(), amkaFieldP.getText(), String.valueOf(comboBox.getSelectedItem()))) {
					Utils.sendError("Error!", "Error on submitting prescription.");
					return;
				}
				Utils.sendNotification("Success!", "Prescription submited.");
			}
		});
		panel.add(submitPrescriptionButton);
		
		 DefaultListModel<String> model = new DefaultListModel<>();
		JList<String> list = new JList<String>(model);
		list.setFont(new Font("Rockwell", Font.PLAIN, 20));
		list.setBounds(343, 56, 300, 333);
		panel.add(list);
		
		
		JButton loadChronicDiseasesButton = new JButton("Load Patients Chronic Diseases ");
		loadChronicDiseasesButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		loadChronicDiseasesButton.setBounds(316, 15, 363, 28);
		loadChronicDiseasesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!Main.con.patientExists(amkaFieldP.getText())) {
					Utils.sendError("Error!", "No Patient with such AMKA found.");
					return;
				}
				ArrayList<String> result = Main.con.getChronicDiseases(amkaFieldP.getText());
				model.clear();
				for(String s : result) model.addElement(s);
			}
		});
		panel.add(loadChronicDiseasesButton);
		
		
		panel.repaint();
	}
	
	public String[] loadDiseases(){
		ArrayList<String> d = Main.con.getDiseases();
		String[] a = new String[d.size()];
		for(int i = 0; i<d.size(); i++) a[i] = d.get(i);
		return a;
	}
	public String[] loadMedicines(){
		ArrayList<String> d = Main.con.getMedicines();
		String[] a = new String[d.size()];
		for(int i = 0; i<d.size(); i++) a[i] = d.get(i);
		return a;
	}
}
