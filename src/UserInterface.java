import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import com.example.statesofmatter.*;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;


public class UserInterface {

	protected Shell shlStatesOfMatter;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UserInterface window = new UserInterface();
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
		shlStatesOfMatter.open();
		shlStatesOfMatter.layout();
		while (!shlStatesOfMatter.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlStatesOfMatter = new Shell();
		shlStatesOfMatter.setSize(400, 300);
		shlStatesOfMatter.setText("States of Matter");
		shlStatesOfMatter.setLayout(null);
		
		Button btnState = new Button(shlStatesOfMatter, SWT.NONE);
		btnState.setBounds(10, 229, 75, 25);
		btnState.setText("State");
		
		Button btnAttack = new Button(shlStatesOfMatter, SWT.NONE);
		btnAttack.setBounds(10, 198, 75, 25);
		btnAttack.setText("Attack");
		
		Button btnItem = new Button(shlStatesOfMatter, SWT.NONE);
		btnItem.setBounds(91, 198, 75, 25);
		btnItem.setText("Item");
		
		Button btnSwitch = new Button(shlStatesOfMatter, SWT.NONE);
		btnSwitch.setBounds(91, 229, 75, 25);
		btnSwitch.setText("Switch");

	}
}
