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
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;


public class SoMUI {

	protected Shell SoM;
	protected Database d;
	protected List monsterList;
	protected List teamList;
	private Text txtStats;
	protected Player player = new Player("Jim", "1");
	int nextTeamIndex = 0;

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
		SoM.setSize(700, 545);
		SoM.setText("States of Matter");
		SoM.setLayout(null);
		
		Group grpMonsterList = new Group(SoM, SWT.NONE);
		grpMonsterList.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		grpMonsterList.setText("Monster List");
		grpMonsterList.setBounds(113, 10, 133, 300);
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(grpMonsterList, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBounds(10, 22, 113, 237);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		monsterList = new List(scrolledComposite, SWT.BORDER);
		monsterList.setItems(new String[] {});
		scrolledComposite.setContent(monsterList);
		scrolledComposite.setMinSize(monsterList.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Group grpStartup = new Group(SoM, SWT.NONE);
		grpStartup.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		grpStartup.setText("On Startup");
		grpStartup.setBounds(10, 10, 97, 90);
		
		Button btnConnect = new Button(grpStartup, SWT.NONE);
		btnConnect.setBounds(10, 24, 78, 25);
		btnConnect.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		btnConnect.setText("Connect");
		
		
		Button btnCollectData = new Button(grpStartup, SWT.NONE);
		btnCollectData.setBounds(10, 55, 78, 25);
		btnCollectData.addSelectionListener(new SelectionAdapter() {
			//@Override
			public void widgetSelected(SelectionEvent e) {
				
				FileDialog fd = new FileDialog(SoM, SWT.OPEN);
				String[] filterExt = { "*.txt" };
				String attacks;
				String items;
				String monsters;
				fd.setFilterExtensions(filterExt);				
				attacks = fd.open();
				items = fd.open();
				monsters = fd.open();
				d = new Database(attacks, items, monsters);
				String[] monsterArray;
				
				try {
					d.getData();
				} catch (FileNotFoundException fnfe) {
					MessageDialog.openError(SoM, null, "Could not acquire data");
					return;
				}
				
				monsterArray = new String[d.MonsterMap.size()];
				int count = 0;
				for (Monster nextMonster : d.MonsterMap.values()) {
					Monster newMonster = nextMonster;
					monsterArray[count] = nextMonster.getName();
					newMonster.printStatus();
					count++;
				}
				
				monsterList.setItems(monsterArray);
			}
		});
		btnCollectData.setToolTipText("Activates getData method in Database class");
		btnCollectData.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		btnCollectData.setText("Collect Data");
		
		Group grpMonsterStats = new Group(SoM, SWT.NONE);
		grpMonsterStats.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		grpMonsterStats.setText("Monster Stats");
		grpMonsterStats.setBounds(429, 10, 245, 300);
		
		txtStats = new Text(grpMonsterStats, SWT.BORDER | SWT.WRAP);
		txtStats.setBounds(10, 22, 225, 268);
		txtStats.setEditable(false);
		
		Button btnViewStatsm = new Button(grpMonsterList, SWT.NONE);
		btnViewStatsm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (monsterList.getItemCount() == 0) {
					MessageDialog.openError(SoM, "List Empty", 
											"Use Collect Data first to get monster data");
				} else if (monsterList.getSelectionIndex() >= 0 && monsterList.getSelectionIndex() >= 0) {
					Monster statMonster = d.MonsterMap.get(monsterList.getItem(monsterList.getSelectionIndex()));
					txtStats.setText(statMonster.toString());
				} else {
					MessageDialog.openError(SoM, "No Selection", "No monster selected");
				}
			}
		});
		btnViewStatsm.setBounds(29, 265, 75, 25);
		btnViewStatsm.setText("View Stats");
		
		Group grpTeam = new Group(SoM, SWT.NONE);
		grpTeam.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		grpTeam.setText("Team");
		grpTeam.setBounds(290, 10, 133, 198);
		
		teamList = new List(grpTeam, SWT.BORDER);
		teamList.setBounds(10, 22, 113, 97);
		
		Button btnViewStatst = new Button(grpTeam, SWT.NONE);
		btnViewStatst.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (teamList.getItemCount() == 0) {
					MessageDialog.openError(SoM, "List Empty", 
											"Add a monster to the team first");
				} else if (teamList.getSelectionIndex() >= 0 && teamList.getSelectionIndex() >= 0) {
					Monster statMonster = d.MonsterMap.get(teamList.getItem(teamList.getSelectionIndex()));
					txtStats.setText(statMonster.toString());
				} else {
					MessageDialog.openError(SoM, "No Selection", "No monster selected");
				}
			}
		});
		btnViewStatst.setText("View Stats");
		btnViewStatst.setBounds(29, 125, 75, 25);
		
		Button btnTeamSize = new Button(grpTeam, SWT.NONE);
		btnTeamSize.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Monster[] team = player.getTeam();
				int size = 0;
				for (Monster monster : team) {
					if (monster != null)
						size++;
				}
				txtStats.setText(Integer.toString(size));
			}
		});
		btnTeamSize.setBounds(29, 156, 75, 25);
		btnTeamSize.setText("Team Size");
		
		Button btnAddToTeam = new Button(SoM, SWT.NONE);
		btnAddToTeam.setBounds(252, 32, 34, 25);
		btnAddToTeam.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (monsterList.getItemCount() == 0) {
					MessageDialog.openError(SoM, "List Empty", 
											"Use Collect Data first to get monster data");
				} else if (teamList.getItemCount() < 6 && monsterList.getSelectionIndex() >= 0) {
					Monster newMonster = d.MonsterMap.get(monsterList.getItem(monsterList.getSelectionIndex()));
					try {
						player.addMonster(newMonster, nextTeamIndex);
						nextTeamIndex++;
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					teamList.add(monsterList.getItem(monsterList.getSelectionIndex()));
				} else if (teamList.getItemCount() >= 6 && monsterList.getSelectionIndex() >= 0) {
					MessageDialog.openError(SoM, "List Full", "Your team is already full");
				} else {
					MessageDialog.openError(SoM, "No Selection", "No monster selected");
				}
			}
		});
		btnAddToTeam.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		btnAddToTeam.setText("->");
		
		Button btnRemoveFromTeam = new Button(SoM, SWT.NONE);
		btnRemoveFromTeam.setBounds(252, 63, 34, 25);
		btnRemoveFromTeam.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		btnRemoveFromTeam.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (teamList.getItemCount() > 0 && teamList.getSelectionIndex() >= 0) {
					Monster newMonster = d.MonsterMap.get(teamList.getItem(teamList.getSelectionIndex()));
					try {
						nextTeamIndex--;
						player.removeMonster(newMonster, nextTeamIndex);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					teamList.remove(teamList.getItem(teamList.getSelectionIndex()));
				} else if (teamList.getItemCount() == 0) {
					MessageDialog.openError(SoM, "List Empty", "Your team is empty");
				} else {
					MessageDialog.openError(SoM, "No Selection", "No monster selected");
				}
			}
		});
		btnRemoveFromTeam.setText("<-");
	}
}
