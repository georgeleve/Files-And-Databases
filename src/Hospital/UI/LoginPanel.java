package Hospital.UI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Hospital.Main;
import Hospital.UI.UserPanels.AdminPanel;
import Hospital.UI.UserPanels.DoctorPanel;
import Hospital.UI.UserPanels.NursePanel;
import Hospital.UI.UserPanels.PatientPanel;
import Hospital.Users.Admin;
import Hospital.Users.Doctor;
import Hospital.Users.Nurse;
import Hospital.Users.Patient;
import Hospital.Utilities.Utils;

public class LoginPanel {
	private boolean loginMode;
	private JFrame frame;
	private final int Height1 = 300, Width = 500, Height2 = 400;
	private JTextField usernameField;
	private JTextField passwordField;
	private JLabel usernameLabel, passwordLabel;
	private JRadioButton patientRegisterButton, patientLoginButton, doctorLoginButton, nurseLoginButton, adminLoginButton;
	private JTextField nameField;
	private JButton connectButton;
	private JLabel nameLabel,amkaLabel;
	private JTextField amkaField;
	private JTextField addressField;
	private JLabel addressLabel;
	private JTextField insuranceField;
	private JLabel insuranceLabel;
	public LoginPanel() {
		initialize();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if(patientRegisterButton.isSelected() && loginMode) setRegisterView();
				if((!patientRegisterButton.isSelected()) && (!loginMode)) setLoginView();
			}
		}, 0, 10);
	}

	private void initialize() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new JFrame();
		frame.setTitle("Hospital - Login");
		frame.setBounds((int) (dim.getWidth() - Width) / 2, (int) (dim.getHeight() - Height1) / 2, Width, Height1);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setContentPane(new JLayeredPane());
		
		
		usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
		frame.getContentPane().add(usernameLabel);

		passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
		frame.getContentPane().add(passwordLabel);

		usernameField = new JTextField();
		usernameField.setFont(new Font("Rockwell", Font.PLAIN, 15));
		frame.getContentPane().add(usernameField);
		usernameField.setColumns(10);

		passwordField = new JTextField();
		passwordField.setFont(new Font("Rockwell", Font.PLAIN, 15));
		frame.getContentPane().add(passwordField);
		passwordField.setColumns(10);

		patientRegisterButton = new JRadioButton("Register Patient");
		patientRegisterButton.setFont(new Font("Rockwell", Font.PLAIN, 15));
		patientRegisterButton.setBounds(288, 53, 163, 25);
		frame.getContentPane().add(patientRegisterButton);

		patientLoginButton = new JRadioButton("Patient Login");
		patientLoginButton.setFont(new Font("Rockwell", Font.PLAIN, 15));
		patientLoginButton.setSelected(true);
		patientLoginButton.setBounds(288, 83, 163, 25);
		frame.getContentPane().add(patientLoginButton);

		doctorLoginButton = new JRadioButton("Doctor Login");
		doctorLoginButton.setFont(new Font("Rockwell", Font.PLAIN, 15));
		doctorLoginButton.setBounds(288, 113, 163, 25);
		frame.getContentPane().add(doctorLoginButton);

		nurseLoginButton = new JRadioButton("Nurse Login");
		nurseLoginButton.setFont(new Font("Rockwell", Font.PLAIN, 15));
		nurseLoginButton.setBounds(288, 143, 163, 25);
		frame.getContentPane().add(nurseLoginButton);

		adminLoginButton = new JRadioButton("Admin Login");
		adminLoginButton.setFont(new Font("Rockwell", Font.PLAIN, 15));
		adminLoginButton.setBounds(288, 173, 163, 25);
		frame.getContentPane().add(adminLoginButton);

		//registration labels and fields
		nameField = new JTextField();
		nameField.setFont(new Font("Rockwell", Font.PLAIN, 15));
		nameField.setColumns(10);
		frame.getContentPane().add(nameField);
		
		nameLabel = new JLabel("Name:");
		nameLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
		frame.getContentPane().add(nameLabel);
		
		amkaLabel = new JLabel("AMKA:");
		amkaLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
		frame.getContentPane().add(amkaLabel);
		
		amkaField = new JTextField();
		amkaField.setFont(new Font("Rockwell", Font.PLAIN, 15));
		amkaField.setColumns(10);
		frame.getContentPane().add(amkaField);
		
		addressField = new JTextField();
		addressField.setFont(new Font("Rockwell", Font.PLAIN, 15));
		addressField.setColumns(10);
		frame.getContentPane().add(addressField);
		
		addressLabel = new JLabel("Address:");
		addressLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
		frame.getContentPane().add(addressLabel);
		
		insuranceField = new JTextField();
		insuranceField.setFont(new Font("Rockwell", Font.PLAIN, 15));
		insuranceField.setColumns(10);
		frame.getContentPane().add(insuranceField);
		
		insuranceLabel = new JLabel("Insurance:");
		insuranceLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
		frame.getContentPane().add(insuranceLabel);
		frame.setVisible(true);
		
		//button
		ButtonGroup bg = new ButtonGroup();
		bg.add(patientRegisterButton);
		bg.add(patientLoginButton);
		bg.add(doctorLoginButton);
		bg.add(nurseLoginButton);
		bg.add(adminLoginButton);

		connectButton = new JButton("Login");
		connectButton.setFont(new Font("Rockwell", Font.PLAIN, 15));
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int connection = connectType(bg);
				if(connection==0) {
					Main.user = new Patient(Main.con);
					Patient p = (Patient) Main.user;
					String name = nameField.getText(), username = usernameField.getText(), password = passwordField.getText(), address = addressField.getText(), insurance = insuranceField.getText(), amka = amkaField.getText();
					if(!verifyData(name, username, password, address, insurance, amka)) return;
					if(p.registerPatient(name, username, password, address, insurance, amka)) {
						Utils.sendNotification("Success", "Registration Complete!");
					}else Utils.sendError("Error!", "This user is already registered (or the AMKA is already in the database)!");
				}else if(connection==1) {
					Main.user = new Patient(Main.con);
					Patient p = (Patient) Main.user;
					String username = usernameField.getText(), password = passwordField.getText();
					if(p.loginPatient(username, password)) {
						Main.panel = new PatientPanel();
						close();
					}else Utils.sendError("Error!", "Invalid credentials");
				}else if(connection==2) {
					Main.user = new Doctor(Main.con);
					Doctor d = (Doctor) Main.user;
					String username = usernameField.getText(), password = passwordField.getText();
					if(d.loginDoctor(username, password)) {
						Main.panel = new DoctorPanel();
						close();
					}else Utils.sendError("Error!", "Invalid credentials");
				}else if(connection==3) {
					Main.user = new Nurse(Main.con);
					Nurse n = (Nurse) Main.user;
					String username = usernameField.getText(), password = passwordField.getText();
					if(n.loginNurse(username, password)) {
						Main.panel = new NursePanel();
						close();
					}else Utils.sendError("Error!", "Invalid credentials");
				}else if(connection==4) {
					Main.user = new Nurse(Main.con);
					Admin n = (Admin) Main.user;
					String username = usernameField.getText(), password = passwordField.getText();
					if(n.loginAdmin(username, password)) {
						Main.panel = new AdminPanel();
						close();
					}else Utils.sendError("Error!", "Invalid credentials");
				}
			}
		});
		frame.getContentPane().add(connectButton);
		
		setLoginView();
		frame.repaint();
	}

	public void setLoginView() {
		loginMode = true;
		frame.setSize(Width, Height1);
		usernameLabel.setBounds(25, 20*2, 80, 16);
		usernameField.setBounds(25, 20*3, 175, 20);
		passwordLabel.setBounds(25, 20*4, 80, 16);
		passwordField.setBounds(25, 20*5, 175, 20);
		
        
		nameLabel.setVisible(false);
		nameField.setVisible(false);
		amkaLabel.setVisible(false);
		amkaField.setVisible(false);
		addressLabel.setVisible(false);
		addressField.setVisible(false);
		insuranceField.setVisible(false);
		insuranceLabel.setVisible(false);
		connectButton.setBounds(25, 165, 175, 30);
		connectButton.setText("Login");
	}

	public void setRegisterView() {
		loginMode = false;
		frame.setSize(Width, Height2);
		nameLabel.setBounds(25, 25+20*0, 80, 16);
		nameField.setBounds(25, 25+20*1, 175, 20);
		usernameLabel.setBounds(25, 25+20*2, 80, 16);
		usernameField.setBounds(25, 25+20*3, 175, 20);
		passwordLabel.setBounds(25, 25+20*4, 80, 16);
		passwordField.setBounds(25, 25+20*5, 175, 20);
		amkaLabel.setBounds(25, 25+20*6, 80, 16);
		amkaField.setBounds(25, 25+20*7, 175, 20);
		addressLabel.setBounds(25, 25+20*8, 80, 16);
		addressField.setBounds(25, 25+20*9, 175, 20);
		insuranceLabel.setBounds(25, 25+20*10, 94, 16);
		insuranceField.setBounds(25, 25+20*11, 175, 20);
		
		nameLabel.setVisible(true);
		nameField.setVisible(true);
		amkaLabel.setVisible(true);
		amkaField.setVisible(true);
		addressLabel.setVisible(true);
		addressField.setVisible(true);
		insuranceField.setVisible(true);
		insuranceLabel.setVisible(true);
		connectButton.setText("Register");
		connectButton.setBounds(25, 290, 175, 30);
	}
	public boolean verifyData(String name, String username, String password, String address, String insurance, String amka) {
		if(name.length()<=1) {
			Utils.sendError("Registration Error!", "Name must not be empty");
			return false;
		}
		if(username.length()<=1) {
			Utils.sendError("Registration Error!", "Username must not be empty");
			return false;
		}
		if(password.length()<3) {
			Utils.sendError("Registration Error!", "Password must contain at least 3 characters!");
			return false;
		}
		if(address.length()<=1) {
			Utils.sendError("Registration Error!", "Address must not be empty");
			return false;
		}
		if(insurance.length()<=1) {
			Utils.sendError("Registration Error!", "Insurance must not be empty");
			return false;
		}
		if(amka.length()!=11) {
			Utils.sendError("Registration Error!", "AMKA must be of size 11");
			return false;
		}
		return true;
	}
	public int connectType(ButtonGroup bg) {
		if(patientRegisterButton.isSelected()) return 0;
		if(patientLoginButton.isSelected()) return 1;
		if(doctorLoginButton.isSelected()) return 2;
		if(nurseLoginButton.isSelected()) return 3;
		return 4;
	}

	public int center(int sz) {
		return (Width-sz+1)/2;
	}
	public void close() {
		if (frame != null)
			frame.dispose();
	}
}
