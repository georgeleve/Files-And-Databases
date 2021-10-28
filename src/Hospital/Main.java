package Hospital;

import java.sql.SQLException;

import Hospital.DatabaseManagement.DatabaseConnection;
import Hospital.ShiftManagement.ShiftManagement;
import Hospital.UI.LoginPanel;
import Hospital.UI.UserPanels.AdminPanel;
import Hospital.UI.UserPanels.DoctorPanel;
import Hospital.UI.UserPanels.UserPanel;
import Hospital.Users.Admin;
import Hospital.Users.Doctor;
import Hospital.Users.User;
import Hospital.Utilities.Utils;

public class Main {
	public static User user;
	public static UserPanel panel;
	public static DatabaseConnection con;
	public static ShiftManagement shift;
	
	/*
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Utils.setLookAndFeel();
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = new DatabaseConnection("jdbc:mysql://localhost", 3306, "root", "");
		if(!con.connect()) {
			Utils.sendError("Error!", "Could not connect to the database!");
			return;
		}
		shift = new ShiftManagement(con);
		Main.user = new Doctor(con);
		new DoctorPanel();
	}
	*/
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Utils.setLookAndFeel();
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = new DatabaseConnection("jdbc:mysql://localhost", 3306, "root", "");
		if(!con.connect()) {
			Utils.sendError("Error!", "Could not connect to the database!");
			return;
		}
		shift = new ShiftManagement(con);
		new LoginPanel();
	}
}
