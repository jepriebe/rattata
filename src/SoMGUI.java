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


public class SoMGUI {

	protected Shell SoM;
	protected Database d;
	protected List attackList;
	protected List itemList;
	protected List monsterList;
	protected List teamList;
	private Text txtStats;
	protected Player player = new Player("Jim", "1");
	private Text txtLead;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SoMGUI window = new SoMGUI();
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
		SoM.setSize(738, 720);
		SoM.setText("States of Matter");
		SoM.setLayout(null);
		
		Group grpMonsterList = new Group(SoM, SWT.NONE);
		grpMonsterList.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		grpMonsterList.setText("Monster List");
		grpMonsterList.setBounds(302, 104, 140, 295);
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(grpMonsterList, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBounds(10, 22, 120, 232);
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
				String[] attackArray;
				String[] itemArray;
				String[] monsterArray;
				
				try {
					d.getData();
				} catch (FileNotFoundException fnfe) {
					MessageDialog.openError(SoM, null, "Could not acquire data");
					return;
				}
				
				attackArray = new String[d.AttackMap.size()];
				int count = 0;
				for (Attack nextAttack : d.AttackMap.values()) {
					attackArray[count] = nextAttack.getName();
					count++;
				}
				itemArray = new String[d.ItemMap.size()];
				count = 0;
				for (Item nextItem : d.ItemMap.values()) {
					itemArray[count] = nextItem.getName();
					count++;
				}
				monsterArray = new String[d.MonsterMap.size()];
				count = 0;
				for (Monster nextMonster : d.MonsterMap.values()) {
					monsterArray[count] = nextMonster.getName();
					count++;
				}
				attackList.setItems(attackArray);
				itemList.setItems(itemArray);
				monsterList.setItems(monsterArray);
			}
		});
		btnCollectData.setToolTipText("Activates getData method in Database class");
		btnCollectData.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		btnCollectData.setText("Collect Data");
		
		Group grpDisplayStats = new Group(SoM, SWT.NONE);
		grpDisplayStats.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		grpDisplayStats.setText("Display Stats");
		grpDisplayStats.setBounds(10, 405, 245, 267);
		
		txtStats = new Text(grpDisplayStats, SWT.BORDER | SWT.WRAP);
		txtStats.setEditable(false);
		txtStats.setBounds(10, 22, 225, 235);
		
		Group grpAttackList = new Group(SoM, SWT.NONE);
		grpAttackList.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		grpAttackList.setText("Attack List");
		grpAttackList.setBounds(10, 104, 140, 295);
		
		ScrolledComposite scrolledComposite_1 = new ScrolledComposite(grpAttackList, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_1.setBounds(10, 22, 120, 232);
		scrolledComposite_1.setExpandHorizontal(true);
		scrolledComposite_1.setExpandVertical(true);
		
		attackList = new List(scrolledComposite_1, SWT.BORDER);
		attackList.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		scrolledComposite_1.setContent(attackList);
		scrolledComposite_1.setMinSize(attackList.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Button btnViewStatsa = new Button(grpAttackList, SWT.NONE);
		btnViewStatsa.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (attackList.getSelectionIndex() >= 0 && attackList.getSelectionIndex() >= 0) {
					Attack statAttack = d.AttackMap.get(attackList.getItem(attackList.getSelectionIndex()));
					txtStats.setText(statAttack.toString());
				} else {
					MessageDialog.openError(SoM, "No Selection", "No attack selected");
				}
			}
		});
		btnViewStatsa.setText("View Stats");
		btnViewStatsa.setBounds(35, 260, 70, 25);
		
		Group grpItemList = new Group(SoM, SWT.NONE);
		grpItemList.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		grpItemList.setText("Item List");
		grpItemList.setBounds(156, 104, 140, 295);
		
		ScrolledComposite scrolledComposite_2 = new ScrolledComposite(grpItemList, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_2.setBounds(10, 22, 120, 232);
		scrolledComposite_2.setExpandHorizontal(true);
		scrolledComposite_2.setExpandVertical(true);
		
		itemList = new List(scrolledComposite_2, SWT.BORDER);
		scrolledComposite_2.setContent(itemList);
		scrolledComposite_2.setMinSize(itemList.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Button btnViewStatsi = new Button(grpItemList, SWT.NONE);
		btnViewStatsi.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (itemList.getSelectionIndex() >= 0 && itemList.getSelectionIndex() >= 0) {
					Item statItem = d.ItemMap.get(itemList.getItem(itemList.getSelectionIndex()));
					txtStats.setText(statItem.toString());
				} else {
					MessageDialog.openError(SoM, "No Selection", "No item selected");
				}
			}
		});
		btnViewStatsi.setText("View Stats");
		btnViewStatsi.setBounds(35, 260, 70, 25);
		
		Button btnViewStatsm = new Button(grpMonsterList, SWT.NONE);
		btnViewStatsm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (monsterList.getSelectionIndex() >= 0 && monsterList.getSelectionIndex() >= 0) {
					Monster statMonster = d.MonsterMap.get(monsterList.getItem(monsterList.getSelectionIndex()));
					txtStats.setText(statMonster.toString());
				} else {
					MessageDialog.openError(SoM, "No Selection", "No monster selected");
				}
			}
		});
		btnViewStatsm.setBounds(35, 260, 70, 25);
		btnViewStatsm.setText("View Stats");
		
		Button btnAddToTeam = new Button(SoM, SWT.NONE);
		btnAddToTeam.setBounds(448, 147, 34, 25);
		btnAddToTeam.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (teamList.getItemCount() < 6 && monsterList.getSelectionIndex() >= 0) {
					Monster newMonster = d.MonsterMap.get(monsterList.getItem(monsterList.getSelectionIndex()));
					Monster[] team = player.getTeam();
					boolean added = false;
					int indexToAdd = 0;
					
					teamList.add(monsterList.getItem(monsterList.getSelectionIndex()));
					try {
						while (added == false) {
							if (team[indexToAdd] == null) {
								player.addMonster(newMonster, indexToAdd);
								added = true;
							} else {
								indexToAdd++;
							}
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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
		btnRemoveFromTeam.setBounds(448, 178, 34, 25);
		btnRemoveFromTeam.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		btnRemoveFromTeam.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (teamList.getItemCount() > 0 && teamList.getSelectionIndex() >= 0) {
					Monster newMonster = d.MonsterMap.get(teamList.getItem(teamList.getSelectionIndex()));
					Monster[] updatedTeam = new Monster[6];
					int nextUpdated = 0;
					int indexToRemove = teamList.getSelectionIndex();
					try {
						player.removeMonster(newMonster, indexToRemove);
						for (Monster m : player.getTeam()) {
							if (m != null) {
								updatedTeam[nextUpdated] = m;
								nextUpdated ++;
							}
						player.setTeam(updatedTeam);
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					teamList.remove(teamList.getSelectionIndex());
				} else {
					MessageDialog.openError(SoM, "No Selection", "No monster selected");
				}
			}
		});
		btnRemoveFromTeam.setText("<-");
		
		Group grpTeam = new Group(SoM, SWT.NONE);
		grpTeam.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		grpTeam.setText("Team");
		grpTeam.setBounds(488, 104, 140, 218);
		
		teamList = new List(grpTeam, SWT.BORDER);
		teamList.setBounds(10, 49, 120, 97);
		
		txtLead = new Text(grpTeam, SWT.BORDER);
		txtLead.setToolTipText("Lead Monster");
		txtLead.setEditable(false);
		txtLead.setBounds(10, 22, 120, 21);
		
		Button btnViewStatst = new Button(grpTeam, SWT.NONE);
		btnViewStatst.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (teamList.getSelectionIndex() >= 0) {
					Monster statMonster = d.MonsterMap.get(teamList.getItem(teamList.getSelectionIndex()));
					txtStats.setText(statMonster.toString());
				} else {
					MessageDialog.openError(SoM, "No Selection", "No monster selected");
				}
			}
		});
		btnViewStatst.setText("View Stats");
		btnViewStatst.setBounds(35, 152, 70, 25);
		
		Button btnSetLead = new Button(grpTeam, SWT.NONE);
		btnSetLead.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (teamList.getSelectionIndex() >= 0) {
					Monster teamLead = d.MonsterMap.get(teamList.getItem(teamList.getSelectionIndex()));
					player.setLead(teamLead);
					String leadName = teamLead.getName();
					txtLead.setText(leadName);
				} else {
					MessageDialog.openError(SoM, "No Selection", "No monster selected");
				}
			}
		});
		btnSetLead.setBounds(35, 183, 70, 25);
		btnSetLead.setText("Set Lead");
	}
}
