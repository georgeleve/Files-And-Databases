package Hospital.DatabaseManagement;

import Hospital.Main;
import Hospital.Utilities.DataGenerator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseConnection {
	private final String databaseName = "Hospital";
	private Connection con;
	private String url, username ,password;
	private int port;
	public DatabaseConnection(String url, int port, String username, String password){
		this.url = url;
		this.port = port;
		this.username = username;
		this.password = password;
	}
	
	public ResultSet query(String request){
		try {
			Statement stmt = con.createStatement(); 
			return stmt.executeQuery(request);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void update(String request){
		try {
			Statement stmt = con.createStatement(); 
			stmt.executeUpdate(request);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean connect(){
		try {
			//clearDatabase(url, port, username, password); //Clear the database to recreate it
			
			boolean exists = false;
			con = DriverManager.getConnection(url + ":" + port , username, password);
			ResultSet resultSet = con.getMetaData().getCatalogs();
			while (resultSet.next()) if(resultSet.getString(1).equalsIgnoreCase(databaseName)) {
				exists = true;
				break;
			}
		    resultSet.close();
		    if(!exists) con.createStatement().executeUpdate("CREATE DATABASE "+databaseName);
		    con = DriverManager.getConnection(url + ":" + port+ "/" + databaseName , username, password);
		    if(exists) return true; //In this case we already have initialized the tables, so we can just return.
		    
		    /* Table initialization */
		    Statement stmt = con.createStatement(); 
		    
	    	/* People - Doctors, Nurses, Admins, Patients */
	    	stmt.executeUpdate(CommandHelper.Doctors);
	    	stmt.executeUpdate(CommandHelper.Nurses);
	    	stmt.executeUpdate(CommandHelper.Admins);
	    	stmt.executeUpdate(CommandHelper.Patients);
	    	
	    	
	    	/* Objects - Medicines, */
	    	stmt.executeUpdate(CommandHelper.ChronicDiseases);
	    	stmt.executeUpdate(CommandHelper.Diseases);
	    	stmt.executeUpdate(CommandHelper.Medicines);
	    	
	    	
	    	/* Actions - Visits, Examinations, Prescriptions */
	    	stmt.executeUpdate(CommandHelper.Visits);
	    	stmt.executeUpdate(CommandHelper.Diagnoses);
	    	stmt.executeUpdate(CommandHelper.Prescriptions);
	    	stmt.executeUpdate(CommandHelper.Shifts);
	    	stmt.executeUpdate(CommandHelper.Report);
	    	stmt.executeUpdate(CommandHelper.Examination);
	    	
	    	initializeTableItems();
	    	return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	private void initializeTableItems(){
		try {
			Statement stmt = con.createStatement();
			//'Dermatologist','Anesthesiologist','Psychiatrist','Otolaryngologist','Ophthalmologist
			DataGenerator dg = new DataGenerator();
			int n = 10;
			String[][] docData = dg.generateDoctors(n);
			for(int i = 0; i<n; i++){
				stmt.executeUpdate(CommandHelper.insertDoctorCommand(docData[0][i], docData[1][i], docData[2][i], docData[3][i]));
			}
			
			String[][] nurseData = dg.generateNursesOrAdmins(n);
			for(int i = 0; i<n; i++){
				stmt.executeUpdate(CommandHelper.insertNurseCommand(nurseData[0][i], nurseData[1][i], nurseData[2][i]));
			}
			
			String[][] adminData = dg.generateNursesOrAdmins(n);
			for(int i = 0; i<n; i++){
				stmt.executeUpdate(CommandHelper.insertAdminCommand(adminData[0][i], adminData[1][i], adminData[2][i]));
			}
			String[][] patientData = dg.generatePatients(n);
			for(int i = 0; i<n; i++){
				stmt.executeUpdate(CommandHelper.insertPatientCommand(patientData[0][i], patientData[1][i], patientData[2][i], patientData[3][i], patientData[4][i], patientData[5][i]));
			}
			
			
			
			stmt.executeUpdate(CommandHelper.insertDiseaseCommand("COVID-19"));
			stmt.executeUpdate(CommandHelper.insertMedicineCommand("ANTI-COVID", "Vaccine", 100, 1));
			
			stmt.executeUpdate(CommandHelper.insertDiseaseCommand("Gastroenteritis"));
			stmt.executeUpdate(CommandHelper.insertMedicineCommand("Antibiotics", "Pill", 100, 2));
			
			stmt.executeUpdate(CommandHelper.insertDiseaseCommand("Flu"));
			stmt.executeUpdate(CommandHelper.insertMedicineCommand("Aspirin", "Pill", 100, 3));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public String getPatientNameByAMKA(String amka) {
		ResultSet rs = query("SELECT Name FROM Patients WHERE AMKA='"+amka+"';");
		try {
			rs.next();
			return rs.getString("Name");
		}catch(Exception e) {}
		return null;
	}
	public String getDoctorNameByID(int id) {
		ResultSet rs = query("SELECT Name FROM Doctors WHERE DoctorID="+id+";");
		try {
			rs.next();
			return rs.getString("Name");
		}catch(Exception e) {}
		return null;
	}
	public String getDiseaseByID(int id) {
		ResultSet rs = query("SELECT Name FROM Diseases WHERE DiseaseID="+id+";");
		try {
			rs.next();
			return rs.getString("Name");
		}catch(Exception e) {}
		return null;
	}
	public int getDiseaseIDByName(String name) {
		ResultSet rs = query("SELECT DiseaseID FROM Diseases WHERE Name='"+name+"';");
		try {
			rs.next();
			return rs.getInt("DiseaseID");
		}catch(Exception e) {}
		return -1;
	}
	public ArrayList<String> getChronicDiseases(String amka){
		ResultSet rs = Main.con.query("SELECT Name FROM ChronicDiseases WHERE AMKA='"+amka+"';");
		ArrayList<String> result = new ArrayList<String>();
		try {
			while(rs.next()) result.add(rs.getString("Name"));
		}catch(Exception ex) {}
		if(result.isEmpty()) result.add("No chronic diseases found.");
		return result;
	}
	public String getMedicineByID(int id) {
		ResultSet rs = query("SELECT Name FROM Medicines WHERE MedicineID="+id+";");
		try {
			rs.next();
			return rs.getString("Name");
		}catch(Exception e) {}
		return null;
	}
	public String getNurseNameByID(int id) {
		ResultSet rs = query("SELECT Name FROM Nurses WHERE NurseID="+id+";");
		try {
			rs.next();
			return rs.getString("Name");
		}catch(Exception e) {}
		return null;
	}
	public ArrayList<String> getDiseases() {
		ArrayList<String> ans = new ArrayList<String>();
		ResultSet rs = query("SELECT Name FROM Diseases;");
		try {
			while(rs.next()) ans.add(rs.getString("Name"));
		}catch(Exception e) {}
		return ans;
	}

	public ArrayList<String> getMedicines() {
		ArrayList<String> ans = new ArrayList<String>();
		ResultSet rs = query("SELECT Name FROM Medicines;");
		try {
			while(rs.next()) ans.add(rs.getString("Name"));
		}catch(Exception e) {}
		return ans;
	}
	
	public String[] getReportData(String id) {
		try {
			int idd = Integer.parseInt(id);
			ResultSet rs = query("SELECT AMKA,ReportSummary FROM Reports WHERE ReportID="+idd);
			String[] ans = new String[2];
			rs.next();
			ans[0] = rs.getString("AMKA");
			ans[1] = rs.getString("ReportSummary");
			return ans;
		}catch(Exception e) {
			return null;
		}
	}
	
	public boolean patientExists(String amka) {
		try {
			String ask = "SELECT * FROM Patients WHERE AMKA='"+amka+"';";
			ResultSet rs = query(ask);
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	private void clearDatabase(String url, int port, String username, String password){
		try {
			con = DriverManager.getConnection(url + ":" + port , username, password);
			boolean exists = false;
			ResultSet resultSet = con.getMetaData().getCatalogs();
			while (resultSet.next()) if(resultSet.getString(1).equalsIgnoreCase(databaseName)) exists = true;
		    resultSet.close();
		    if(!exists) return;
			Statement stmt = con.createStatement(); 
			stmt.executeUpdate("DROP DATABASE "+databaseName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Connection getConnection() {
		return this.con;
	}
}
