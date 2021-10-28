package Hospital.ShiftManagement;

public class Shift {
	private int dermatologistID, pathologistID, psychiatristID, otolarynologistID, opthalmologistID, nurseID, adminID;
	public Shift(int dermatologistID, int pathologistID, int psychiatristID,int otolarynologistID, int opthalmologistID, int nurseID, int adminID) {
		this.dermatologistID = dermatologistID;
		this.pathologistID = pathologistID;
		this.psychiatristID = psychiatristID;
		this.otolarynologistID = otolarynologistID;
		this.opthalmologistID = opthalmologistID;
		this.nurseID = nurseID;
		this.adminID = adminID;
	}
	public int getDermatologistID() {
		return dermatologistID;
	}
	public void setDermatologistID(int dermatologistID) {
		this.dermatologistID = dermatologistID;
	}
	public int getPathologistID() {
		return pathologistID;
	}
	public void setPathologistID(int pathologistID) {
		this.pathologistID = pathologistID;
	}
	public int getPsychiatristID() {
		return psychiatristID;
	}
	public void setPsychiatristID(int psychiatristID) {
		this.psychiatristID = psychiatristID;
	}
	public int getOtolarynologistID() {
		return otolarynologistID;
	}
	public void setOtolarynologistID(int otolarynologistID) {
		this.otolarynologistID = otolarynologistID;
	}
	public int getOpthalmologistID() {
		return opthalmologistID;
	}
	public void setOpthalmologistID(int opthalmologistID) {
		this.opthalmologistID = opthalmologistID;
	}
	public int getNurseID() {
		return nurseID;
	}
	public void setNurseID(int nurseID) {
		this.nurseID = nurseID;
	}
	public int getAdminID() {
		return adminID;
	}
	public void setAdminID(int adminID) {
		this.adminID = adminID;
	}
}
