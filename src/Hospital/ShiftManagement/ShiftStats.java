package Hospital.ShiftManagement;

import java.util.ArrayList;
import java.util.HashMap;

public class ShiftStats{
	public int visits = 0;
	public HashMap<Integer, Integer> diseases = new HashMap<>();
	public ArrayList<String> examAmka = new ArrayList<>();
	public ArrayList<Integer> examNurses = new ArrayList<>();
	public HashMap<Integer, Integer> medicines = new HashMap<>();
}