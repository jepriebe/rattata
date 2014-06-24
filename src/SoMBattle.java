import com.example.statesofmatter.*;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.custom.CCombo;

public class SoMBattle {

	protected Shell SoMB;
	private Text txtMe;
	private Text txtOpp;
	private Group grpBattle;
	private Text text;
	protected Player player = SoMStart.player;
	protected Monster[] team = player.getTeam();
	protected Monster myLead = player.getLead();
	private CCombo comboSwitch;

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
		SoMB.setSize(362, 300);
		SoMB.setText("States of Matter Arena");
		
		Group grpMe = new Group(SoMB, SWT.NONE);
		grpMe.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		grpMe.setText("Me");
		grpMe.setBounds(10, 10, 160, 110);
		
		txtMe = new Text(grpMe, SWT.BORDER | SWT.WRAP);
		String leadStats = String.format("%s%nHP: %d/%d%n" + "State: %s", 
				   						 myLead.getName(), myLead.getHP(),
				   						 myLead.getmaxHP(), myLead.getState());
		txtMe.setText(leadStats);
		txtMe.setBounds(10, 22, 140, 78);
		txtMe.setEditable(false);
		
		Group grpOpp = new Group(SoMB, SWT.NONE);
		grpOpp.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		grpOpp.setText("Opponent");
		grpOpp.setBounds(176, 10, 160, 110);
		
		txtOpp = new Text(grpOpp, SWT.BORDER | SWT.WRAP);
		txtOpp.setText("Name: null\r\nHP: 0/0\r\nState: null");
		txtOpp.setEditable(false);
		txtOpp.setBounds(10, 22, 140, 78);
		
		grpBattle = new Group(SoMB, SWT.NONE);
		grpBattle.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		grpBattle.setText("Battle!");
		grpBattle.setBounds(10, 126, 326, 126);
		
		text = new Text(grpBattle, SWT.BORDER);
		text.setEditable(false);
		text.setBounds(122, 20, 194, 96);
		
		comboSwitch = new CCombo(grpBattle, SWT.BORDER);
		comboSwitch.setEditable(false);
		comboSwitch.setBounds(66, 95, 50, 21);
		for (Monster m : team) {
			comboSwitch.add(m.getName());
		}
		comboSwitch.setText(myLead.getName());
		comboSwitch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (comboSwitch.getSelection() != null) {
					myLead = team[comboSwitch.getSelectionIndex()];
					String leadStats = String.format("%s%nHP: %s/%s%n" + "State: %s", 
	   						 						 myLead.getName(), myLead.getHP(),
	   						 						 myLead.getmaxHP(), myLead.getState());
					txtMe.setText(leadStats);
				}
			}
		});
	}
}
