package Hospital.Users;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JTextArea;

import Hospital.Main;
import Hospital.DatabaseManagement.DatabaseConnection;
import Hospital.ShiftManagement.ShiftStats;
import Hospital.Utilities.Utils;

public class Admin extends User{
	int adminID;
	public Admin(DatabaseConnection con) {
		super(con);
	}
	
	public boolean loginAdmin(String username, String password){
		if(!userExists(username, password, "Admin")) return false;
		loggedIn = true;
		this.username = username;
		this.password = password;
		ResultSet rs = con.query("SELECT AdminID FROM Admins WHERE Username='"+username+"'"+" AND Password='"+password+"';");
		try {
			rs.next();
			this.adminID = rs.getInt("AdminID");
		}catch(Exception e) {}
		return true;
	}
	
	public void displayCustomQuery(String q, JTextArea area) {
		StringBuilder sb = new StringBuilder();
		try{
			Statement stmt = Main.con.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(q);
			ArrayList<String> values = new ArrayList<String>();
			int n = 0;
			while(rs.next()) {
				for(int i = 1; ; i++) {
					try {
						values.add(rs.getObject(i).toString());
					}catch(Exception ex2) {
						n = i-1;
						break;
					}
				}
			}
			int[] maxN = new int[n];
			for(int i = 0; i<values.size(); i++) maxN[i%n] = Math.max(maxN[i%n], values.get(i).length());
			for(int i = 0; i<values.size(); i++) {
				sb.append(Utils.addGaps(values.get(i), maxN[i%n]));
				if((i+1)%n==0) sb.append("  \n");
				else sb.append(" | ");
			}
		}catch(SQLException ex) {
			sb.append("Query error:\n");
			sb.append(ex.getLocalizedMessage());
		}
		String ans = sb.toString();
		if(ans.isEmpty()) ans = "No Results!";
		area.setText(ans);
	}
	
	public void displayVisitsToday(JTextArea area) {
		StringBuilder sb = new StringBuilder();
		int n = 0;
		try{
			Statement stmt = Main.con.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT AMKA,Symptoms, DoctorID from Visits Where Date=DATE(NOW())");
			while(rs.next()) {
				n++;
				int doc = rs.getInt("DoctorID");
				String amka = rs.getString("AMKA");
				String symptoms = rs.getString("Symptoms");
				String docName = Main.con.getDoctorNameByID(doc);
				String patientName = Main.con.getPatientNameByAMKA(amka);
				sb.append("-"+patientName+" visited the hospital with symptoms: "+symptoms+" and was assigned to dr."+docName+"\n");
			}
		}catch(SQLException ex) {
			sb.append("Query error:\n");
			sb.append(ex.getLocalizedMessage());
		}
		String ans = "Today there were "+n+" visit(s) in total:\n"+sb.toString();
		area.setText(ans);
	}

	public ShiftStats getShiftStatsDaily(String date) {
		String dateStr = "STR_TO_DATE('"+date+"', '%Y-%m-%d')";
		try{
			ShiftStats s = new ShiftStats();
			Statement stmt = Main.con.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Date from Visits Where Date="+dateStr);
			int n = 0;
			while(rs.next()) n++;
			s.visits = n;
			
			rs = stmt.executeQuery("SELECT DiseaseID from Diagnoses Where Date="+dateStr);
			while(rs.next()) {
				int d = rs.getInt("DiseaseID");
				s.diseases.put(d, s.diseases.getOrDefault(s, 0)+1);
			}
			
			rs = stmt.executeQuery("SELECT NurseID,AMKA from Reports Where Date="+dateStr);
			while(rs.next()) {
				String amka = rs.getString("AMKA");
				int nurse = rs.getInt("NurseID");
				s.examAmka.add(amka);
				s.examNurses.add(nurse);
			}
			
			rs = stmt.executeQuery("SELECT MedicineID from Prescriptions Where Date="+dateStr);
			while(rs.next()) {
				int medicineId = rs.getInt("MedicineID");
				s.medicines.put(medicineId, s.medicines.getOrDefault(s, 0)+1);
			}
			return s;
		}catch(SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public ShiftStats getShiftStatsMonthly() {
		String dateCmp = "Date(NOW() - INTERVAL 1 MONTH) <= Date AND Date <= DATE(NOW())";
		try{
			ShiftStats s = new ShiftStats();
			Statement stmt = Main.con.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Date from Visits Where "+dateCmp);
			int n = 0;
			while(rs.next()) n++;
			s.visits = n;
			
			rs = stmt.executeQuery("SELECT DiseaseID from Diagnoses Where "+dateCmp);
			while(rs.next()) {
				int d = rs.getInt("DiseaseID");
				s.diseases.put(d, s.diseases.getOrDefault(s, 0)+1);
			}
			
			rs = stmt.executeQuery("SELECT NurseID,AMKA from Reports Where "+dateCmp);
			while(rs.next()) {
				String amka = rs.getString("AMKA");
				int nurse = rs.getInt("NurseID");
				s.examAmka.add(amka);
				s.examNurses.add(nurse);
			}
			
			rs = stmt.executeQuery("SELECT MedicineID from Prescriptions Where "+dateCmp);
			while(rs.next()) {
				int medicineId = rs.getInt("MedicineID");
				s.medicines.put(medicineId, s.medicines.getOrDefault(s, 0)+1);
			}
			return s;
		}catch(SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public void displayMonthlyStats(JTextArea area) {
		ShiftStats daily = getShiftStatsMonthly();
		StringBuilder sb = new StringBuilder();
		sb.append("Shift report for last month:\n");
		sb.append("-Patient visits: "+daily.visits+"\n");
		sb.append("-The following diseases were diagnosed: \n");
		for(int id : daily.diseases.keySet()) {
			String d = Main.con.getDiseaseByID(id);
			sb.append("    -Disease "+d+" had a total of "+daily.diseases.get(id)+" cases.\n");
		}
		sb.append("-Nurse examinations: \n");
		for(int i = 0; i<daily.examNurses.size(); i++) {
			String p = Main.con.getPatientNameByAMKA(daily.examAmka.get(i));
			String n = Main.con.getNurseNameByID(daily.examNurses.get(i));
			sb.append("    -Patient "+p+" was examined by "+n+"\n");
		}
		sb.append("-The following amounts of medicines were given: \n");
		for(int id : daily.medicines.keySet()) {
			String d = Main.con.getMedicineByID(id);
			sb.append("    -Medicine "+d+" was given a total of "+daily.medicines.get(id)+" times. \n" );
		}
		area.setText(sb.toString());
	}

	public void displayDailyStats(String date, JTextArea area) {
		ShiftStats daily = getShiftStatsDaily(date);
		StringBuilder sb = new StringBuilder();
		sb.append("Shift report for ("+date+"):\n");
		sb.append("-Patient visits: "+daily.visits+"\n");
		sb.append("-The following diseases were diagnosed: \n");
		for(int id : daily.diseases.keySet()) {
			String d = Main.con.getDiseaseByID(id);
			sb.append("    -Disease "+d+" had a total of "+daily.diseases.get(id)+" cases.\n");
		}
		sb.append("-Nurse examinations: \n");
		for(int i = 0; i<daily.examNurses.size(); i++) {
			String p = Main.con.getPatientNameByAMKA(daily.examAmka.get(i));
			String n = Main.con.getNurseNameByID(daily.examNurses.get(i));
			sb.append("    -Patient "+p+" was examined by "+n+"\n");
		}
		sb.append("-The following amounts of medicines were given: \n");
		for(int id : daily.medicines.keySet()) {
			String d = Main.con.getMedicineByID(id);
			sb.append("    -Medicine "+d+" was given a total of "+daily.medicines.get(d)+" times. \n" );
		}
		area.setText(sb.toString());
	}

	public void dispayCovidReports(JTextArea area) {
		int covidID = Main.con.getDiseaseIDByName("COVID-19");
		StringBuilder sb = new StringBuilder();
		int n = 0;
		try{
			Statement stmt = Main.con.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Date,AMKA from Diagnoses Where DiseaseID="+covidID);
			while(rs.next()) {
				n++;
				String date = rs.getDate("Date").toString();
				String amka = rs.getString("AMKA");
				ArrayList<String> chronicDiseases = Main.con.getChronicDiseases(amka);
				Statement stmt2 = Main.con.getConnection().createStatement();
				ResultSet rs2 = stmt2.executeQuery("SELECT Name,Insurance,Address from Patients Where AMKA='"+amka+"'");
				rs2.next();
				String name = rs2.getString("Name");
				String insurance = rs2.getString("Insurance");
				String address = rs2.getString("Address");
				sb.append("Patient "+name+" was diagnosed with Covid-19 at "+date+". Personal Information: \n");
				sb.append("  -AMKA: "+amka+"\n");
				sb.append("  -Insurance: "+insurance+"\n");
				sb.append("  -Address: "+address+"\n");
				sb.append("  -Chronic Diseases: \n");
				for(String s : chronicDiseases) sb.append("    -"+s+" \n");
				sb.append("-------------------------------\n");
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
			return;
		}
		String ans = "Today there were "+n+" Covid-19 diagnoses in total:\n"+sb.toString();
		area.setText(ans);
	}

	public void displayStaffShiftsReports(JTextArea area, String start, String end, boolean showAll) {
		start = "STR_TO_DATE('"+start+"', '%Y-%m-%d')";
		end = "STR_TO_DATE('"+end+"', '%Y-%m-%d')";
		String dateInterval = start+"<= Date AND Date <="+end;
		StringBuilder sb = new StringBuilder();
		try{
			HashMap<Integer, ArrayList<String>> doctorShifts = new HashMap<>();
			HashMap<Integer, ArrayList<String>> nurseShifts = new HashMap<>();
			HashMap<Integer, ArrayList<String>> adminShifts = new HashMap<>();
			Statement stmt = Main.con.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from Shifts Where "+dateInterval);
			while(rs.next()) {
				String date = rs.getDate("Date").toString();
				String[] docSpecializations = new String[]{"Dermatologist","Pathologist","Psychiatrist","Otolaryngologist","Ophthalmologist"};
				for(int i = 0; i<docSpecializations.length; i++) {
					String lookFor = docSpecializations[i]+"ID";
					int id = rs.getInt(lookFor);
					if(!doctorShifts.containsKey(id)) doctorShifts.put(id, new ArrayList<>());
					doctorShifts.get(id).add(date);
				}
				int nurseID = rs.getInt("NurseID");
				int adminID = rs.getInt("AdminID");
				if(!nurseShifts.containsKey(nurseID)) nurseShifts.put(nurseID, new ArrayList<>());
				if(!adminShifts.containsKey(adminID)) adminShifts.put(adminID, new ArrayList<>());
				nurseShifts.get(nurseID).add(date);
				adminShifts.get(adminID).add(date);
			}
			

			sb.append("-Doctor Shifts:\n");
			Statement docStmt = Main.con.getConnection().createStatement();
			rs = docStmt.executeQuery("SELECT Name,DoctorID from Doctors");
			while(rs.next()) {
				int id = rs.getInt("DoctorID");
				String name = rs.getString("Name");
				if(!doctorShifts.containsKey(id)) {
					if(!showAll) continue;
					sb.append("  -Doctor "+name+" did not work on any shifts in that period.\n");
					continue;
				}
				ArrayList<String> dates = doctorShifts.get(id);
				sb.append("  -Doctor "+name+" worked a total of "+dates.size()+" shifts in that period: \n");
				for(String s : dates) sb.append("    -"+s+"\n");
			}
			
			sb.append("-Nurse Shifts:\n");
			Statement nurseStmt = Main.con.getConnection().createStatement();
			rs = nurseStmt.executeQuery("SELECT Name,NurseID from Nurses");
			while(rs.next()) {
				int id = rs.getInt("NurseID");
				String name = rs.getString("Name");
				if(!nurseShifts.containsKey(id)) {
					if(!showAll) continue;
					sb.append("  -Nurse "+name+" did not work on any shifts in that period.\n");
					continue;
				}
				ArrayList<String> dates = nurseShifts.get(id);
				sb.append("  -Nurse "+name+" worked a total of "+dates.size()+" shifts in that period: \n");
				for(String s : dates) sb.append("    -"+s+"\n");
			}
			
			sb.append("-Admin Shifts:\n");
			Statement adminStmt = Main.con.getConnection().createStatement();
			rs = adminStmt.executeQuery("SELECT Name,AdminID from Admins");
			while(rs.next()) {
				int id = rs.getInt("AdminID");
				String name = rs.getString("Name");
				if(!adminShifts.containsKey(id)) {
					if(!showAll) continue;
					sb.append("  -Admin "+name+" did not work on any shifts in that period.\n");
					continue;
				}
				ArrayList<String> dates = adminShifts.get(id);
				sb.append("  -Admin "+name+" worked a total of "+dates.size()+" shifts in that period: \n");
				for(String s : dates) sb.append("    -"+s+"\n");
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
			return;
		}
		area.setText(sb.toString());
	}
}