package Hospital.Users;

import java.sql.ResultSet;
import java.util.ArrayList;

import Hospital.DatabaseManagement.CommandHelper;
import Hospital.DatabaseManagement.DatabaseConnection;

public class Patient extends User{
	private DatabaseConnection con;
	private String name, AMKA, address, insurance;
	public Patient(DatabaseConnection con){
		super(con);
		this.con = con;
		this.loggedIn = false;
	}
	
	public boolean registerPatient(String name, String username, String password, String address, String insurance, String AMKA){
		if(userExists(username, password, "Patient")) return false;
		if(con.getPatientNameByAMKA(AMKA)!=null) return false;
		String genRequest = CommandHelper.insertPatientCommand(name, username, password, address, insurance, AMKA);
		con.update(genRequest);
		loginPatient(username, password);
		return true;
	}
	
	public boolean loginPatient(String username, String password){
		if(!userExists(username, password, "Patient")) return false;
		loggedIn = true;
		this.username = username;
		this.password = password;
		ResultSet rs = con.query("SELECT Name,AMKA,Address,Insurance FROM Patients WHERE Username='"+username+"'"+" AND Password='"+password+"';");
		try {
			rs.next();

			this.name = rs.getString("Name");
			this.AMKA = rs.getString("AMKA");
			this.address = rs.getString("Address");
			this.insurance = rs.getString("Insurance");
		}catch(Exception e) {}
		return true;
	}
	
	public void addCronicDisease(String chronicDeseaseName) {
		con.update(CommandHelper.insertChronicDiseaseCommand(chronicDeseaseName,AMKA));
	}
	
	public ArrayList<String> getChronicDiseases(){
		ResultSet rs = con.query("SELECT Name FROM ChronicDiseases WHERE AMKA='"+AMKA+"';");
		ArrayList<String> result = new ArrayList<String>();
		try {
			while(rs.next()) result.add(rs.getString("Name"));
		}catch(Exception e) {}
		return result;
	}
	
	public boolean visit(String symptoms, int doctorID) {
		if(!isLoggedIn()) return false;
		con.update(CommandHelper.insertVisitCommand(symptoms, AMKA, doctorID));
		return true;
	}
	public String getAMKA() {
		return AMKA;
	}
	public void setAMKA(String amka) {
		this.AMKA = amka;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getInsurance() {
		return insurance;
	}
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
}
