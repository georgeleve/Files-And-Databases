package Hospital.UI.UserPanels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Hospital.Main;
import Hospital.Users.Doctor;
import Hospital.Users.Nurse;
import Hospital.Utilities.Utils;

public class NursePanel extends UserPanel{

	private final int Height = 300, Width = 400;
	public NursePanel() {
		initialize();
	}
	private void initialize() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setTitle("Hospital - Nurse Panel");
		frame.setVisible(true);
		frame.setBounds((int) (dim.getWidth() - Width) / 2, (int) (dim.getHeight() - Height) / 2, Width, Height);
		frame.setResizable(false);
		
		JLabel patientAmkaLabel = new JLabel("Patient's AMKA:");
		patientAmkaLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		patientAmkaLabel.setBounds(55, 20+40*0, 155, 24);
		frame.getContentPane().add(patientAmkaLabel);
		
		JTextField amkaField = new JTextField();
		amkaField.setFont(new Font("Rockwell", Font.PLAIN, 20));
		amkaField.setBounds(55, 20+40*1-10, 270, 25);
		amkaField.setColumns(10);
		frame.getContentPane().add(amkaField);
		
		JLabel reportLabel = new JLabel("Report Summary:");
		reportLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		reportLabel.setBounds(55, 20+40*2, 190, 24);
		frame.getContentPane().add(reportLabel);
		
		JTextField reportField = new JTextField();
		reportField.setFont(new Font("Rockwell", Font.PLAIN, 20));
		reportField.setColumns(10);
		reportField.setBounds(55, 20+40*3-10, 270, 25);
		frame.getContentPane().add(reportField);
		
		
		JButton submitReportButton = new JButton("Submit Diagnose");
		submitReportButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		submitReportButton.setBounds(55, 180, 270, 40);
		submitReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!Main.con.patientExists(amkaField.getText())) {
					Utils.sendError("Error!", "No Patient with such AMKA found.");
					return;
				}
				int reportID = ((Nurse) Main.user).report(reportField.getText(), amkaField.getText());
				if(reportID==-1) {
					Utils.sendError("Error!", "Error on submitting report.");
					return;
				}
				Utils.sendNotification("Report submited!", "The report id is: "+reportID);
			}
		});
		frame.getContentPane().add(submitReportButton);
		frame.repaint();
	}
}
