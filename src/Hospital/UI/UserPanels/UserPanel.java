package Hospital.UI.UserPanels;

import javax.swing.JFrame;

public abstract class UserPanel {
	protected JFrame frame;
	public void close() {
		if (frame != null) frame.dispose();
	}
}
