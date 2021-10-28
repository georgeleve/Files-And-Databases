package Hospital.Utilities;

import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import Hospital.Main;
public class Utils {
	public static void sendNotification(String title, String msg) {
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
	}
	public static void sendError(String title, String msg) {
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
	}
	public static String request(String question) {
		return JOptionPane.showInputDialog(null, question);
	}
	public static ImageIcon loadImageIcon(String name) {
		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("../resources/"+name)));
	}
	public static String addGaps(String s, int sz) {
		s = s.trim();
		boolean front = false;
		while(s.length()<sz) {
			if(front) s= " "+s;
			else s = s+" ";
			front = !front;
		}
		return s;
	}
	public static boolean isValidDate(String date) {
		try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
	}
	public static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            try {
						UIManager.setLookAndFeel(info.getClassName());
		            } catch (Exception e2) { }
		            break;
		        }
		    }
		}
	}
	public static boolean isInt(String v) {
		try {
			Integer.parseInt(v);
			return true;
		}catch (Exception e){
			return false;
		}
	}
}
