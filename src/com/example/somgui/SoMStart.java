package com.example.somgui;

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
import org.eclipse.swt.custom.CCombo;


public class SoMStart {

	protected Shell SoM;
	protected static LocalGameRunner runner = new LocalGameRunner();
	protected List attackList;
	protected List itemList;
	protected List monsterList;
	protected List teamList;
	private Text txtStats;
	private Text txtLead;
	private CCombo comboState;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SoMStart window = new SoMStart();
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
		SoM.setSize(738, 580);
		SoM.setText("States of Matter");
		SoM.setLayout(null);
		
		
		Group grpMonsterList = new Group(SoM, SWT.NONE);
		grpMonsterList.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		grpMonsterList.setText("Monster List");
		grpMonsterList.setBounds(302, 10, 140, 389);
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(grpMonsterList, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBounds(10, 22, 120, 326);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		monsterList = new List(scrolledComposite, SWT.BORDER);
		monsterList.setItems(new String[] {});
		scrolledComposite.setContent(monsterList);
		scrolledComposite.setMinSize(monsterList.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Group grpDisplayStats = new Group(SoM, SWT.NONE);
		grpDisplayStats.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		grpDisplayStats.setText("Display Stats");
		grpDisplayStats.setBounds(448, 264, 264, 267);
		
		txtStats = new Text(grpDisplayStats, SWT.BORDER | SWT.WRAP);
		txtStats.setEditable(false);
		txtStats.setBounds(10, 22, 244, 235);
		
		Group grpAttackList = new Group(SoM, SWT.NONE);
		grpAttackList.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		grpAttackList.setText("Attack List");
		grpAttackList.setBounds(10, 10, 140, 389);
		
		ScrolledComposite scrolledComposite_1 = new ScrolledComposite(grpAttackList, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_1.setBounds(10, 22, 120, 326);
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
					Attack statAttack = runner.getDbase().AttackMap.get(attackList.getItem(attackList.getSelectionIndex()));
					txtStats.setText(statAttack.toString());
				} else {
					MessageDialog.openError(SoM, "No Selection", "No attack selected");
				}
			}
		});
		btnViewStatsa.setText("View Stats");
		btnViewStatsa.setBounds(35, 354, 70, 25);
		
		Group grpItemList = new Group(SoM, SWT.NONE);
		grpItemList.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		grpItemList.setText("Item List");
		grpItemList.setBounds(156, 10, 140, 389);
		
		ScrolledComposite scrolledComposite_2 = new ScrolledComposite(grpItemList, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_2.setBounds(10, 22, 120, 326);
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
					Item statItem = runner.getDbase().ItemMap.get(itemList.getItem(itemList.getSelectionIndex()));
					txtStats.setText(statItem.toString());
				} else {
					MessageDialog.openError(SoM, "No Selection", "No item selected");
				}
			}
		});
		btnViewStatsi.setText("View Stats");
		btnViewStatsi.setBounds(35, 354, 70, 25);
		
		Button btnViewStatsm = new Button(grpMonsterList, SWT.NONE);
		btnViewStatsm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (monsterList.getSelectionIndex() >= 0 && monsterList.getSelectionIndex() >= 0) {
					Monster statMonster = runner.getDbase().MonsterMap.get(monsterList.getItem(monsterList.getSelectionIndex()));
					txtStats.setText(statMonster.toString());
				} else {
					MessageDialog.openError(SoM, "No Selection", "No monster selected");
				}
			}
		});
		btnViewStatsm.setBounds(35, 354, 70, 25);
		btnViewStatsm.setText("View Stats");
		
		Button btnAddToTeam = new Button(SoM, SWT.NONE);
		btnAddToTeam.setBounds(448, 79, 34, 25);
		btnAddToTeam.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (teamList.getItemCount() < 6 && monsterList.getSelectionIndex() >= 0) {
						runner.addToTeam(monsterList.getItem(monsterList.getSelectionIndex()), 
										 monsterList.getSelectionIndex());
						teamList.add(monsterList.getItem(monsterList.getSelectionIndex()));
					} else if (teamList.getItemCount() >= 6 && monsterList.getSelectionIndex() >= 0) {
						MessageDialog.openError(SoM, "List Full", "Your team is already full");
					} else {
						MessageDialog.openError(SoM, "No Selection", "No monster selected");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnAddToTeam.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		btnAddToTeam.setText("->");
		
		Button btnRemoveFromTeam = new Button(SoM, SWT.NONE);
		btnRemoveFromTeam.setBounds(448, 110, 34, 25);
		btnRemoveFromTeam.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		btnRemoveFromTeam.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (teamList.getItemCount() > 0 && teamList.getSelectionIndex() >= 0) {
						runner.removeFromTeam(teamList.getItem(teamList.getSelectionIndex()), 
											  teamList.getSelectionIndex());
						teamList.remove(teamList.getSelectionIndex());
					} else {
						MessageDialog.openError(SoM, "No Selection", "No monster selected");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnRemoveFromTeam.setText("<-");
		
		Group grpTeam = new Group(SoM, SWT.NONE);
		grpTeam.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		grpTeam.setText("Team");
		grpTeam.setBounds(488, 10, 140, 248);
		
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
					Monster statMonster = runner.getDbase().MonsterMap.get(teamList.getItem(teamList.getSelectionIndex()));
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
					Monster teamLead = runner.getDbase().MonsterMap.get(teamList.getItem(teamList.getSelectionIndex()));
					runner.getPlayer().setLead(teamLead);
					String leadName = teamLead.getName();
					txtLead.setText(leadName);
				} else {
					MessageDialog.openError(SoM, "No Selection", "No monster selected");
				}
			}
		});
		btnSetLead.setBounds(35, 183, 70, 25);
		btnSetLead.setText("Set Lead");
		
		comboState = new CCombo(grpTeam, SWT.BORDER);
		comboState.setEditable(false);
		comboState.setBounds(35, 214, 70, 21);
		comboState.add("SOLID");
		comboState.add("LIQUID");
		comboState.add("GAS");
		comboState.add("PLASMA");
		comboState.setText("SOLID");
		comboState.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (comboState.getSelection() != null) {
					State monState = State.valueOf(comboState.getText());
					runner.getPlayer().getTeam()[teamList.getSelectionIndex()].setState(monState);
				}
			}
		});
		
		Button btnBattle = new Button(SoM, SWT.NONE);
		btnBattle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (runner.getPlayer().getTeam()[5] != null && runner.getPlayer().getLead() != null
					&& runner.getIsReady() == false) {
					runner.setIsReady(true);
					synchronized (runner.lock) {
						runner.lock.notifyAll();
					}
					while (!runner.getBattleStarted()) {
						synchronized (runner.lock) {
							try {
								runner.lock.wait();
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}	
					}
					SoMBattle somBattle = new SoMBattle();
					somBattle.open();
				} else if (runner.getIsReady() == true) {
					MessageDialog.openError(SoM, "Error", "You have already readied yourself for battle!");
				} else {
					MessageDialog.openError(SoM, "Error", "Team must be full and have lead to battle!");
				}
			}
		});
		btnBattle.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
		btnBattle.setBounds(634, 10, 78, 66);
		btnBattle.setText("BATTLE!");
		
		
		FileDialog fd = new FileDialog(SoM, SWT.OPEN);		
		String[] filterExt = { "*.txt" };
		fd.setFilterExtensions(filterExt);				
		runner.setAttFile(fd.open());
		runner.setItemFile(fd.open());
		runner.setMonFile(fd.open());
		
		try {
			runner.startClient();
		} catch (Exception ex) {
			MessageDialog.openError(SoM, null, "Could not acquire data");
			return;
		}
		
		String [] attackArray = new String[runner.getDbase().AttackMap.size()];
		int count = 0;
		for (Attack nextAttack : runner.getDbase().AttackMap.values()) {
			attackArray[count] = nextAttack.getName();
			count++;
		}
		String[] itemArray = new String[runner.getDbase().ItemMap.size()];
		count = 0;
		for (Item nextItem : runner.getDbase().ItemMap.values()) {
			itemArray[count] = nextItem.getName();
			count++;
		}
		String[] monsterArray = new String[runner.getDbase().MonsterMap.size()];
		count = 0;
		for (Monster nextMonster : runner.getDbase().MonsterMap.values()) {
			monsterArray[count] = nextMonster.getName();
			count++;
		}
		attackList.setItems(attackArray);
		itemList.setItems(itemArray);
		monsterList.setItems(monsterArray);
	}
}
