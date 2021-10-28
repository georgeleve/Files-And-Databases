package Hospital.Users;
import java.sql.ResultSet;

import Hospital.Main;
import Hospital.DatabaseManagement.CommandHelper;
import Hospital.DatabaseManagement.DatabaseConnection;
public class Doctor extends User{
	int doctorID;
	public Doctor(DatabaseConnection con) {
		super(con);
	}
	
	public boolean loginDoctor(String username, String password){
		if(!userExists(username, password, "Doctor")) return false;
		loggedIn = true;
		this.username = username;
		this.password = password;
		ResultSet rs = con.query("SELECT DoctorID FROM Doctors WHERE Username='"+username+"'"+" AND Password='"+password+"';");
		try {
			rs.next();
			this.doctorID = rs.getInt("DoctorID");
		}catch(Exception e) {}
		return true;
	}
	
	public boolean diagnose(String diagnoseResult, String patientAMKA, String diseaseName) {
		if(!isLoggedIn()) return false;
		int diseaseID = -1;
		ResultSet rs = Main.con.query("SELECT DiseaseID FROM Diseases WHERE Name='"+diseaseName+"';");
		try {
			rs.next();
			diseaseID = rs.getInt("DiseaseID");
		}catch(Exception ex) {}
		if(diseaseID==-1) return false;
		con.update(CommandHelper.insertDiagnoseCommand(diagnoseResult, patientAMKA, diseaseID, diseaseID));
		return true;
	}
	
	public boolean prescription(String doctorNotes, String patientAMKA, String medicineName) {
		if(!isLoggedIn()) return false;
		int medicineID = -1;
		ResultSet rs = Main.con.query("SELECT MedicineID FROM Medicines WHERE Name='"+medicineName+"';");
		try {
			rs.next();
			medicineID = rs.getInt("MedicineID");
		}catch(Exception ex) {}
		if(medicineID==-1) return false;
		con.update(CommandHelper.insertPrescriptionCommand(doctorNotes, patientAMKA, doctorID, medicineID));
		return true;
	}
	public boolean examination(String doctorNotes, String treatment, String patientAmka, int reportID) {
		if(!isLoggedIn()) return false;
		con.update(CommandHelper.insertExaminationCommand(doctorNotes, treatment, patientAmka, doctorID, reportID));
		return true;
	}
}