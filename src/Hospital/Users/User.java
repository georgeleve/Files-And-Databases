package Hospital.Users;

import java.sql.ResultSet;
import java.sql.SQLException;

import Hospital.DatabaseManagement.DatabaseConnection;

public abstract class User {
	protected boolean loggedIn;
	protected DatabaseConnection con;
	protected String username, password;
	
	public User(DatabaseConnection con) {
		this.con = con;
	}
	
	public boolean userExists(String username, String password, String type){
		try {
			String ask = "SELECT * FROM "+type+"s WHERE Username='"+username+"'"+" AND Password='"+password+"';";
			ResultSet rs = con.query(ask);
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isLoggedIn(){
		return loggedIn;
	}
}
