package Hospital.Utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;

public class DataGenerator {
	private static String[] docSpecializations = new String[]{"Dermatologist","Pathologist","Psychiatrist","Otolaryngologist","Ophthalmologist"};
	private static int idx = 0, idx2 = 1, idx3 = 1;
	
	public static String[][] generateDoctors(int n){
		String[][] ans = new String[4][n];
		String[] names = getRandomNames(n);
		for(int i = 0; i<n; i++){
			ans[0][i] = names[i];
			ans[1][i] = (docSpecializations[(idx++)%5]);
			ans[2][i] = names[i].split(" ")[0];
			ans[3][i] = names[i].split(" ")[1];
		}
		return ans;
	}
	
	public static String[][] generateNursesOrAdmins(int n){
		String[][] ans = new String[3][n];
		String[] names = getRandomNames(n);
		for(int i = 0; i<n; i++){
			ans[0][i] = names[i];
			ans[1][i] = names[i].split(" ")[0];
			ans[2][i] = names[i].split(" ")[1];
		}
		return ans;
	}
	
	public static String[][] generatePatients(int n){
		String[][] ans = new String[6][n];
		String[] names = getRandomNames(n);
		for(int i = 0; i<n; i++){
			ans[0][i] = names[i];
			ans[1][i] = names[i].split(" ")[0];
			ans[2][i] = names[i].split(" ")[1];
			ans[3][i] = getAddress();
			ans[4][i] = "INSURANCE INC.";
			ans[5][i] = generateRandomAmka();
		}
		return ans;
	}
	
	public static String[] getRandomNames(int n){
		try {
			URL url = new URL("http://names.drycodes.com/"+n+"?nameOptions=boy_names");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String data = in.readLine();
			data = data.replace("[\"", "");
			data = data.replace("\"]", "");
			data = data.replace("_", " ");
			String[] arr = data.split("\",\"");
			return arr;
		} catch (Exception e) {
			String[] arr = new String[n];
			for(int i = 0; i<n; i++)  {
				arr[i] = "Name"+(idx3)+" Surname"+(idx3);
				idx3++;
			}
			return arr;
		}
	}
	
	public static String getAddress(){
		return "Adress"+(idx2++);
	}
	
	public static String generateRandomAmka(){
		Random r = new Random();
		String ans = "";
		for(int i = 0; i<11; i++){
			int v = Math.abs(r.nextInt())%8+1;
			ans += (char)(v+'0');
		}
		return ans;
	}
}
