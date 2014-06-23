import com.example.statesofmatter.*;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;

public class SoMBattle {

	protected Shell SoMB;
	private Text txtYou;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SoMBattle window = new SoMBattle();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		SoMB.open();
		SoMB.layout();
		while (!SoMB.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		SoMB = new Shell();
		SoMB.setSize(450, 300);
		SoMB.setText("States of Matter Arena");
		
		Group grpYou = new Group(SoMB, SWT.NONE);
		grpYou.setText("You");
		grpYou.setBounds(10, 10, 160, 107);
		
		txtYou = new Text(grpYou, SWT.BORDER | SWT.WRAP);
		Monster initLead = SoMStart.player.getLead();
		String leadStats = String.format("HP: %s/%s%n" + "State: %s", 
				   						 initLead.getHP(), initLead.getmaxHP(), 
				   						 initLead.getState());
		txtYou.setText(leadStats);
		txtYou.setBounds(10, 22, 140, 75);
		txtYou.setEditable(false);

	}
}
