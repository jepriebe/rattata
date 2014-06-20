import java.io.*;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

import com.example.statesofmatter.*;

import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.List;


public class SoMUI {

	protected Shell SoM;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SoMUI window = new SoMUI();
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
		SoM.open();
		SoM.layout();
		while (!SoM.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		SoM = new Shell();
		SoM.setSize(450, 300);
		SoM.setText("States of Matter");
		SoM.setLayout(null);
		
		
		Button btnCollectData = new Button(SoM, SWT.NONE);
		btnCollectData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				FileDialog fd = new FileDialog(SoM, SWT.OPEN);
				String[] filterExt = { "*.txt" };
				String attackList;
				String itemList;
				String monsterList;
				fd.setFilterExtensions(filterExt);				
				attackList = fd.open();
				itemList = fd.open();
				monsterList = fd.open();
				Database d = new Database(attackList, itemList, monsterList);
				
				try {
					d.getData();
				} catch (FileNotFoundException fnfe) {
					MessageDialog.openError(SoM, null, "Could not acquire data");
					return;
				}
				
				for (Monster nextMonster : d.MonsterMap.values()) {
					Monster newMonster = nextMonster;
					newMonster.printStatus();
				}
			}
		});
		btnCollectData.setToolTipText("Activates getData method in Database class");
		btnCollectData.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		btnCollectData.setBounds(10, 41, 78, 25);
		btnCollectData.setText("Collect Data");
		
		Button btnCreateTeam = new Button(SoM, SWT.NONE);
		btnCreateTeam.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		btnCreateTeam.setBounds(10, 72, 78, 25);
		btnCreateTeam.setText("Create Team");
		
		Button btnConnect = new Button(SoM, SWT.NONE);
		btnConnect.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		btnConnect.setBounds(10, 10, 78, 25);
		btnConnect.setText("Connect");

	}
}
