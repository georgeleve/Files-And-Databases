package Hospital.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Hospital.DatabaseManagement.CommandHelper;
import Hospital.DatabaseManagement.DatabaseConnection;

public class Nurse extends User{
	int nurseID;
	public Nurse(DatabaseConnection con) {
		super(con);
	}
	
	public boolean loginNurse(String username, String password){
		if(!userExists(username, password, "Nurse")) return false;
		loggedIn = true;
		this.username = username;
		this.password = password;
		ResultSet rs = con.query("SELECT NurseID FROM Nurses WHERE Username='"+username+"'"+" AND Password='"+password+"';");
		try {
			rs.next();
			this.nurseID = rs.getInt("NurseID");
		}catch(Exception e) {}
		return true;
	}
	public int report(String reportResult, String AMKA) { //Returns reportID
		if(!isLoggedIn()) return -1;
		try {
			PreparedStatement stmt = con.getConnection().prepareStatement(CommandHelper.insertReportCommand(reportResult, AMKA, nurseID), new String[]{"ReportID"});
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
}