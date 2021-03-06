package com.example.somgui;

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
import org.eclipse.swt.widgets.Button;

public class SoMBattle {

	protected Shell SoMB;
	protected Display display;
	
	protected LocalGameRunner runner;
	
	private SoMUpdater updater;
	
	private boolean stateChanged = false;
	
	private Text txtMe;
	private Text txtOpp;
	private Group grpBattle;
	private Text txtInfo;
	private Button btnAttack;
	private Button btnState;
	private Button btnItem;
	private Button btnSwitch;
	private CCombo comboAttack;
	private CCombo comboState;
	private CCombo comboItem;
	private CCombo comboSwitch;

	/**
	 * Launch the application.
	 * @param args
	 */
	/*public static void main(String[] args) {
		try {
			SoMBattle window = new SoMBattle(runner);
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		SoMB.open();
		SoMB.layout();
		updater = new SoMUpdater(runner, display, comboAttack, comboState,
				comboItem, comboSwitch, txtMe, txtOpp, txtInfo, btnAttack,
				btnState, btnItem, btnSwitch);
		updater.start();
		while (!SoMB.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	public SoMBattle(LocalGameRunner runner) {
		this.runner = runner;
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		SoMB = new Shell();
		SoMB.setSize(410, 305);
		SoMB.setText("States of Matter Arena - Player " + (runner.getPlayerNum() + 1));
		
		Group grpMe = new Group(SoMB, SWT.NONE);
		grpMe.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		grpMe.setText("Me");
		grpMe.setBounds(10, 10, 160, 110);
		
		txtMe = new Text(grpMe, SWT.BORDER | SWT.WRAP);
		String myStats = String.format("%s%nHP: %d/%d%n" + "State: %s", 
				   						 runner.getPlayer().getLead().getName(), 
				   						 runner.getPlayer().getLead().getHP(),
				   						 runner.getPlayer().getLead().getMaxHP(), 
				   						 runner.getPlayer().getLead().getState());
		txtMe.setText(myStats);
		txtMe.setBounds(10, 22, 140, 78);
		txtMe.setEditable(false);
		
		Group grpOpp = new Group(SoMB, SWT.NONE);
		grpOpp.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		grpOpp.setText("Opponent");
		grpOpp.setBounds(224, 10, 160, 110);
		
		txtOpp = new Text(grpOpp, SWT.BORDER | SWT.WRAP);
		String oppStats = String.format("%s%nHP: %d/%d%n" + "State: %s",
										runner.getOppLead().getName(),
										runner.getOppLead().getHP(),
										runner.getOppLead().getMaxHP(),
										runner.getOppLead().getState());
		txtOpp.setText(oppStats);
		txtOpp.setEditable(false);
		txtOpp.setBounds(10, 22, 140, 78);
		
		grpBattle = new Group(SoMB, SWT.NONE);
		grpBattle.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		grpBattle.setText("Battle!");
		grpBattle.setBounds(10, 126, 374, 132);
		
		txtInfo = new Text(grpBattle, SWT.BORDER | SWT.WRAP);
		txtInfo.setText("Select a move!");
		txtInfo.setEditable(false);
		txtInfo.setBounds(154, 20, 210, 102);
		
		btnAttack = new Button(grpBattle, SWT.NONE);
		btnAttack.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		btnAttack.setBounds(10, 20, 48, 21);
		btnAttack.setText("Attack");
		btnAttack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (comboState.isEnabled() || comboItem.isEnabled() || comboSwitch.isEnabled()) {
					comboState.setEnabled(false);
					comboItem.setEnabled(false);
					comboSwitch.setEnabled(false);
				}
				comboAttack.setEnabled(true);
			}
		});
		
		comboAttack = new CCombo(grpBattle, SWT.BORDER);
		comboAttack.setEnabled(false);
		comboAttack.setEditable(false);
		comboAttack.setBounds(64, 20, 84, 21);
		for (Attack a : runner.getPlayer().getLead().getAttacks()) {
			if (a != null)
				comboAttack.add(a.getName());
		}
		comboAttack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int currentIndex;
				if ((currentIndex = comboAttack.getSelectionIndex()) >= 0 &&
						currentIndex < 4) {
					runner.setAction(PlayerAction.ATTACK);
					runner.setArgument(currentIndex);
					if (!stateChanged)
						runner.setTurnState(runner.getPlayer().getLead().getState());
					btnAttack.setEnabled(false);
					btnState.setEnabled(false);
					btnItem.setEnabled(false);
					btnSwitch.setEnabled(false);
					comboAttack.setEnabled(false);
					stateChanged = false;
					runner.setTurnReady(true);
					updater.setParams(comboAttack, comboState, 
							comboItem, comboSwitch, txtMe, txtOpp, txtInfo, 
							btnAttack, btnState, btnItem, btnSwitch);
					synchronized (runner.lock) {
						runner.lock.notify();
					}
				} else {
					System.err.println("Index out of bounds.");
				}
			}
		});
		
		btnState = new Button(grpBattle, SWT.NONE);
		btnState.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		btnState.setBounds(10, 47, 48, 21);
		btnState.setText("State");
		btnState.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (comboAttack.isEnabled() || comboItem.isEnabled() || comboSwitch.isEnabled()) {
					comboAttack.setEnabled(false);
					comboItem.setEnabled(false);
					comboSwitch.setEnabled(false);
				}
				comboState.setEnabled(true);
			}
		});
		
		comboState = new CCombo(grpBattle, SWT.BORDER);
		comboState.setEnabled(false);
		comboState.setEditable(false);
		comboState.setBounds(64, 47, 84, 21);
		for (int i = 0; i < 4; i++) {
			comboState.add(State.values()[i].toString(), i);
		}
		comboState.setText(runner.getPlayer().getLead().getState().toString());
		comboState.addSelectionListener(new SelectionAdapter() {
			int currentIndex = comboState.getSelectionIndex();
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (comboState.getSelectionIndex() != currentIndex) {
					currentIndex = comboState.getSelectionIndex();
					runner.setTurnState(State.values()[currentIndex]);
					stateChanged = true;
					System.out.println(runner.getTurnState());
					btnSwitch.setEnabled(false);
				}
			}
		});
		
		btnItem = new Button(grpBattle, SWT.NONE);
		btnItem.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		btnItem.setBounds(10, 74, 48, 21);
		btnItem.setText("Item");
		btnItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (comboAttack.isEnabled() || comboState.isEnabled() || comboSwitch.isEnabled()) {
					comboAttack.setEnabled(false);
					comboState.setEnabled(false);
					comboSwitch.setEnabled(false);
				}
				comboItem.setEnabled(true);
			}
		});
		
		comboItem = new CCombo(grpBattle, SWT.BORDER);
		comboItem.setEditable(false);
		comboItem.setEnabled(false);
		comboItem.setBounds(64, 74, 84, 21);
		
		btnSwitch = new Button(grpBattle, SWT.NONE);
		btnSwitch.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		btnSwitch.setBounds(10, 101, 48, 21);
		btnSwitch.setText("Switch");
		btnSwitch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (comboAttack.isEnabled() || comboState.isEnabled() || comboItem.isEnabled()) {
					comboAttack.setEnabled(false);
					comboState.setEnabled(false);
					comboItem.setEnabled(false);
				}
				comboSwitch.setEnabled(true);
			}
		});
		
		comboSwitch = new CCombo(grpBattle, SWT.BORDER);
		comboSwitch.setEnabled(false);
		comboSwitch.setEditable(false);
		comboSwitch.setBounds(64, 101, 84, 21);
		for (Monster m : runner.getPlayer().getTeam()) {
			if (m != null)
			comboSwitch.add(m.getName());
		}
		comboSwitch.setText(runner.getPlayer().getLead().getName());
		comboSwitch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int currentIndex;
				if ((currentIndex = comboSwitch.getSelectionIndex()) >= 0 &&
						currentIndex < 6) {
					runner.setAction(PlayerAction.SWITCH);
					runner.setArgument(currentIndex);
					runner.setTurnState(runner.getPlayer().getLead().getState());
					btnAttack.setEnabled(false);
					btnState.setEnabled(false);
					btnItem.setEnabled(false);
					btnSwitch.setEnabled(false);
					comboSwitch.setEnabled(false);
					runner.setTurnReady(true);
					updater.setParams(comboAttack, comboState, 
							comboItem, comboSwitch, txtMe, txtOpp, txtInfo, 
							btnAttack, btnState, btnItem, btnSwitch);
					synchronized (runner.lock) {
						runner.lock.notify();
					}
				} else {
					System.err.println("Index out of bounds.");
				}
			}
		});
	}
}