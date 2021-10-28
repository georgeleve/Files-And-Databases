package Hospital.ShiftManagement;

import java.sql.ResultSet;
import java.util.ArrayList;

import Hospital.DatabaseManagement.CommandHelper;
import Hospital.DatabaseManagement.DatabaseConnection;

public class ShiftManagement {
	private DatabaseConnection con;
	
	public ShiftManagement(DatabaseConnection con) {
		this.con = con;
	}
	
	/* Returns the todays Shift. If it doesn't exist it gets auto-generated */
	public Shift getCurrentShift() {
		int id = getTodaysShiftID();
		if(id==-1) forceRegisterTodaysShift();
		id = getTodaysShiftID();
		try {																													
			ResultSet rs = con.query("SELECT DermatologistID,PathologistID,PsychiatristID,OtolaryngologistID,OphthalmologistID,NurseID,AdminID FROM Shifts Where ShiftID="+id);
			rs.next();
			int dermatologistID = rs.getInt("DermatologistID");
			int pathologistID = rs.getInt("PathologistID");
			int psychiatristID = rs.getInt("PsychiatristID");
			int otolaryngologistID = rs.getInt("OtolaryngologistID");
			int ophthalmologistID = rs.getInt("OphthalmologistID");
			int nurseID = rs.getInt("NurseID");
			int adminID = rs.getInt("AdminID");
			return new Shift(dermatologistID, pathologistID, psychiatristID, otolaryngologistID, ophthalmologistID, nurseID, adminID);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/* Returns the shiftID of the shift that has todays day, -1 otherwise */
	private int getTodaysShiftID() {
		try {																													
			ResultSet rs = con.query("SELECT ShiftID from Shifts Where Date=Date(NOW())");
			rs.next();
			return rs.getInt("ShiftID");
		}catch (Exception e) {
			return -1;
		}
	}
	
	/* Returns the id of the doctor of specialization spec on duty */
	public int getDoctorIdOnDuty(int spec) {
		Shift s = getCurrentShift();
		if(spec==0) return s.getDermatologistID();
		if(spec==1) return s.getPathologistID();
		if(spec==2) return s.getPsychiatristID();
		if(spec==3) return s.getOtolarynologistID();
		return s.getOpthalmologistID();
	}
	
	/* Returns the  id of the nurse on duty */
	public int getNurseOnDuty() {
		return getCurrentShift().getNurseID();
	}
	
	/* Generates tomorrows shift. Returns false if it is already registered, true on success. */
	public boolean registerTommorow() {
		if(isTomorrowRegistered()) return false;
		int idx = getLastShiftID();
		try {
			int[] docs = new int[5];
			String[] docSpecializations = new String[]{"Dermatologist","Pathologist","Psychiatrist","Otolaryngologist","Ophthalmologist"};
			for(int i = 0; i<5; i++) {
				ResultSet rs = con.query("SELECT DoctorID FROM Doctors WHERE Specialization='"+docSpecializations[i]+"';");
				ArrayList<Integer> ids = new ArrayList<Integer>();
				while(rs.next()) ids.add(rs.getInt("DoctorID"));
				docs[i] = ids.get(idx%ids.size());
			}
			int nurseID, adminID;
			{
				ResultSet rs = con.query("SELECT NurseID FROM Nurses");
				ArrayList<Integer> ids = new ArrayList<Integer>();
				while(rs.next()) ids.add(rs.getInt("NurseID"));
				nurseID = ids.get(idx%ids.size());
			}
			{
				ResultSet rs = con.query("SELECT AdminID FROM Admins");
				ArrayList<Integer> ids = new ArrayList<Integer>();
				while(rs.next()) ids.add(rs.getInt("AdminID"));
				adminID = ids.get(idx%ids.size());
			}
			con.update(CommandHelper.insertShiftCommand("Date(NOW() + INTERVAL 1 DAY)",docs[0], docs[1], docs[2], docs[3], docs[4], nurseID, adminID));
			return true;
		}catch (Exception e){}
		return false;
	}
	
	/* Checks if tomorrows Shift is registered */
	private boolean isTomorrowRegistered() {
		try {																													
			ResultSet rs = con.query("SELECT ShiftID from Shifts Where Date=Date(NOW() + INTERVAL 1 DAY)");
			return rs.next();
		}catch (Exception e) {
			return false;
		}
	}
	
	/* Force register today. Makes sure that todays shift always exists before it gets used. */
	private void forceRegisterTodaysShift() {
		int idx = getLastShiftID();
		try {
			int[] docs = new int[5];
			String[] docSpecializations = new String[]{"Dermatologist","Pathologist","Psychiatrist","Otolaryngologist","Ophthalmologist"};
			for(int i = 0; i<5; i++) {
				ResultSet rs = con.query("SELECT DoctorID FROM Doctors WHERE Specialization='"+docSpecializations[i]+"';");
				ArrayList<Integer> ids = new ArrayList<Integer>();
				while(rs.next()) ids.add(rs.getInt("DoctorID"));
				docs[i] = ids.get(idx%ids.size());
			}
			int nurseID, adminID;
			{
				ResultSet rs = con.query("SELECT NurseID FROM Nurses");
				ArrayList<Integer> ids = new ArrayList<Integer>();
				while(rs.next()) ids.add(rs.getInt("NurseID"));
				nurseID = ids.get(idx%ids.size());
			}
			{
				ResultSet rs = con.query("SELECT AdminID FROM Admins");
				ArrayList<Integer> ids = new ArrayList<Integer>();
				while(rs.next()) ids.add(rs.getInt("AdminID"));
				adminID = ids.get(idx%ids.size());
			}
			con.update(CommandHelper.insertShiftCommand("NOW()",docs[0], docs[1], docs[2], docs[3], docs[4], nurseID, adminID));
		}catch (Exception e){}
	}
	
	/* Returns the ID  of the last shift or zero if there are no shifts registered on the system. Used to generate shifts fairly. */
	public int getLastShiftID() {
		try {
			ResultSet rs = con.query("SELECT MAX(ShiftID) as last_shift_id FROM Shifts");
			rs.next();
			return rs.getInt("last_shift_id");
		}catch (Exception e) {}
		return 0;
	}
}
