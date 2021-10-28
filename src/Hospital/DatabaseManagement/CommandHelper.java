package Hospital.DatabaseManagement;
public class CommandHelper {
	
	final static String Doctors = "CREATE TABLE Doctors("
			+ "Name VARCHAR(30),"
			+ "Specialization ENUM('Dermatologist','Pathologist','Psychiatrist','Otolaryngologist','Ophthalmologist') NOT NULL,"
			+ "Username VARCHAR(30),"
			+ "Password VARCHAR(30),"
			+ "DoctorID INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY);";
	
	public static String insertDoctorCommand(String name, String specialization, String username, String password){
		String result = "INSERT INTO Doctors VALUE('"+name+"', '"+specialization+"', '"+username+"', '"+password+"', NULL);";
		return result;
	}
	
	final static String Nurses = "CREATE TABLE Nurses("
			+ "Name VARCHAR(30),"
			+ "Username VARCHAR(30),"
			+ "Password VARCHAR(30),"
			+ "NurseID INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY);";
	
	public static String insertNurseCommand(String name, String username, String password){
		String result = "INSERT INTO Nurses VALUE('"+name+"', '"+username+"', '"+password+"', NULL);";
		return result;
	}
	
	final static String Admins = "CREATE TABLE Admins("
			+ "Name VARCHAR(30),"
			+ "Username VARCHAR(30),"
			+ "Password VARCHAR(30),"
			+ "AdminID INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY);";
	
	public static String insertAdminCommand(String name, String username, String password){
		String result = "INSERT INTO Admins VALUE('"+name+"', '"+username+"', '"+password+"', NULL);";
		return result;
	}
	
	final static String Patients = "CREATE TABLE Patients("
			+ "Name VARCHAR(30),"
			+ "Username VARCHAR(30),"
			+ "Password VARCHAR(30),"
			+ "Address VARCHAR(30),"
			+ "Insurance VARCHAR(30),"
			+ "AMKA CHAR(11) NOT NULL PRIMARY KEY);";
	
	public static String insertPatientCommand(String name, String username, String password, String address, String insurance, String amka){
		String result = "INSERT INTO Patients VALUE('"+name+"', '"+username+"', '"+password+"', '"+address+"', '"+insurance+"', '"+amka+"');";
		return result;
	}
	
	final static String Visits = "CREATE TABLE Visits("
			+ "Date DATE,"
			+ "Symptoms VARCHAR(30),"
			+ "AMKA CHAR(11),"
			+ "DoctorID INT UNSIGNED,"
			+ "FOREIGN KEY (AMKA) REFERENCES Patients(AMKA),"
			+ "FOREIGN KEY (DoctorID) REFERENCES Doctors(DoctorID));";
	
	public static String insertVisitCommand(String symptoms, String AMKA, int doctorID){
		String result = "INSERT INTO Visits VALUE(NOW(), '"+symptoms+"', '"+AMKA+"', "+doctorID+");";
		return result;
	}
	
	final static String ChronicDiseases = "CREATE TABLE ChronicDiseases("
			+ "Name VARCHAR(30),"
			+ "AMKA CHAR(11),"
			+ "FOREIGN KEY (AMKA) REFERENCES Patients(AMKA));";
	
	public static String insertChronicDiseaseCommand(String chronicDiseaseName, String AMKA){
		String result = "INSERT INTO ChronicDiseases VALUE('"+chronicDiseaseName+"', '"+AMKA+"');";
		return result;
	}
	
	final static String Diseases = "CREATE TABLE Diseases("
			+ "Name VARCHAR(30),"
			+ "DiseaseID INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY);";
	
	public static String insertDiseaseCommand(String diseaseName){
		String result = "INSERT INTO Diseases VALUE('"+diseaseName+"', NULL);";
		return result;
	}
	
	final static String Medicines = "CREATE TABLE Medicines("
			+ "Name VARCHAR(30),"
			+ "Type VARCHAR(30),"
			+ "Content INT UNSIGNED,"
			+ "DiseaseID INT UNSIGNED,"
			+ "MedicineID INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,"
			+ "FOREIGN KEY (DiseaseID) REFERENCES Diseases(DiseaseID));";
	
	public static String insertMedicineCommand(String medicineName, String medicineType, int content, int diseaseID){
		String result = "INSERT INTO Medicines VALUE('"+medicineName+"', '"+medicineType+"', "+content+", "+diseaseID+", NULL);";
		return result;
	}
	
	final static String Diagnoses = "CREATE TABLE Diagnoses("
			+ "DiagnoseResult VARCHAR(30),"
			+ "Date DATE,"
			+ "AMKA CHAR(11),"
			+ "DoctorID INT UNSIGNED,"
			+ "DiseaseID INT UNSIGNED,"
			+ "FOREIGN KEY (AMKA) REFERENCES Patients(AMKA),"
			+ "FOREIGN KEY (DoctorID) REFERENCES Doctors(DoctorID),"
			+ "FOREIGN KEY (DiseaseID) REFERENCES Diseases(DiseaseID));";

	public static String insertDiagnoseCommand(String diagnoseResult, String AMKA, int doctorID, int diseaseID){
		String result = "INSERT INTO Diagnoses VALUE('"+diagnoseResult+"', NOW(), '"+AMKA+"', "+doctorID+", "+diseaseID+");";
		return result;
	}
	
	final static String Examination = "CREATE TABLE Examinations("
			+ "DoctorNotes VARCHAR(30),"
			+ "Treatment VARCHAR(30),"
			+ "Date DATE,"
			+ "AMKA CHAR(11),"
			+ "DoctorID INT UNSIGNED,"
			+ "ReportID INT UNSIGNED,"
			+ "FOREIGN KEY (AMKA) REFERENCES Patients(AMKA),"
			+ "FOREIGN KEY (DoctorID) REFERENCES Doctors(DoctorID),"
			+ "FOREIGN KEY (ReportID) REFERENCES Reports(ReportID));";

	public static String insertExaminationCommand(String doctorNotes, String treatment, String patientAmka, int doctorID, int reportID){
		String result = "INSERT INTO Examinations VALUE('"+doctorNotes+"', '"+treatment+"', NOW(), '"+patientAmka+"', "+doctorID+", "+reportID+");";
		return result;
	}
	
	final static String Prescriptions = "CREATE TABLE Prescriptions("
			+ "DoctorNotes VARCHAR(30),"
			+ "Date DATE,"
			+ "AMKA CHAR(11),"
			+ "DoctorID INT UNSIGNED,"
			+ "MedicineID INT UNSIGNED,"
			+ "FOREIGN KEY (AMKA) REFERENCES Patients(AMKA),"
			+ "FOREIGN KEY (DoctorID) REFERENCES Doctors(DoctorID),"
			+ "FOREIGN KEY (MedicineID) REFERENCES Medicines(MedicineID));";
	
	public static String insertPrescriptionCommand(String doctorNotes, String AMKA, int doctorID, int medicineID){
		String result = "INSERT INTO Prescriptions VALUE('"+doctorNotes+"', NOW(), '"+AMKA+"', "+doctorID+", "+medicineID+");";
		return result;
	}
	
	final static String Shifts = "CREATE TABLE Shifts("
			+ "Date DATE,"
			
			+ "DermatologistID INT UNSIGNED,"
			+ "PathologistID INT UNSIGNED,"
			+ "PsychiatristID INT UNSIGNED,"
			+ "OtolaryngologistID INT UNSIGNED,"
			+ "OphthalmologistID INT UNSIGNED,"
			+ "NurseID INT UNSIGNED,"
			+ "AdminID INT UNSIGNED,"
			
			+ "FOREIGN KEY (DermatologistID) REFERENCES Doctors(DoctorID),"
			+ "FOREIGN KEY (PathologistID) REFERENCES Doctors(DoctorID),"
			+ "FOREIGN KEY (PsychiatristID) REFERENCES Doctors(DoctorID),"
			+ "FOREIGN KEY (OtolaryngologistID) REFERENCES Doctors(DoctorID),"
			+ "FOREIGN KEY (OphthalmologistID) REFERENCES Doctors(DoctorID),"
			+ "FOREIGN KEY (NurseID) REFERENCES Nurses(NurseID),"
			+ "FOREIGN KEY (AdminID) REFERENCES Admins(AdminID),"
			
			+ "ShiftID INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY);";
	
	public static String insertShiftCommand(String date, int dermatologistID, int pathologistID,int psychiatristID, int otolaryngologistID, int ophthalmologistID, int nurseID, int adminID){
		String result = "INSERT INTO Shifts VALUE("+date+","+dermatologistID+","+pathologistID+","+psychiatristID+","+otolaryngologistID+","+ophthalmologistID+","+nurseID+","+adminID+",NULL);";
		return result;
	}
	
	final static String Report = "CREATE TABLE Reports("
			+ "ReportSummary VARCHAR(30),"
			+ "Date DATE,"
			+ "AMKA CHAR(11),"
			+ "NurseID INT UNSIGNED,"
			+ "ReportID INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,"
			+ "FOREIGN KEY (AMKA) REFERENCES Patients(AMKA),"
			+ "FOREIGN KEY (NurseID) REFERENCES Nurses(NurseID));";
	public static String insertReportCommand(String reportSummary, String AMKA, int nurseID){
		String result = "INSERT INTO Reports VALUE('"+reportSummary+"', NOW(), '"+AMKA+"', "+nurseID+", NULL);";
		return result;
	}
}