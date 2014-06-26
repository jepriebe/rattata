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
	protected static LocalGameRunner runner = SoMStart.runner;
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
	private static boolean ready = true; //should be set to false once move selected and set back to true once turn is resolved

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
		while (!SoMB.isDisposed() && true) {
			if (ready) {
				this.btnAttack.setEnabled(true);
				this.btnState.setEnabled(true);
				this.btnItem.setEnabled(true);
				this.btnSwitch.setEnabled(true);
			} else {
				this.btnAttack.setEnabled(false);
				this.btnState.setEnabled(false);
				this.btnItem.setEnabled(false);
				this.btnSwitch.setEnabled(false);
			}
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
		SoMB.setSize(410, 305);
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
		grpOpp.setBounds(224, 10, 160, 110);
		
		txtOpp = new Text(grpOpp, SWT.BORDER | SWT.WRAP);
		txtOpp.setText("Name: null\r\nHP: 0/0\r\nState: null");
		txtOpp.setEditable(false);
		txtOpp.setBounds(10, 22, 140, 78);
		
		grpBattle = new Group(SoMB, SWT.NONE);
		grpBattle.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		grpBattle.setText("Battle!");
		grpBattle.setBounds(10, 126, 374, 132);
		
		txtInfo = new Text(grpBattle, SWT.BORDER | SWT.WRAP);
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
		for (Attack a : attacks) {
			if (a != null)
			comboAttack.add(a.getName());
		}
		comboAttack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (comboAttack.getSelection() != null) {
					Attack myAttack = myLead.getAttacks()[comboAttack.getSelectionIndex()];
					comboAttack.setEnabled(false);
					btnAttack.setEnabled(false);
					btnState.setEnabled(false);
					btnItem.setEnabled(false);
					btnSwitch.setEnabled(false);
					ready = false;
					txtInfo.setText(myLead.getName() + " uses " + myAttack.getName() + "!");
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
		for (Monster m : team) {
			if (m != null)
			comboSwitch.add(m.getName());
		}
		comboSwitch.setText(myLead.getName());
		comboSwitch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (comboSwitch.getSelection() != null) {
					action = PlayerAction.SWITCH;
					argument = comboSwitch.getSelectionIndex();
					myTurn = new Turn(action, argument);
					String oldLead = myLead.getName();
					myTurn.executeTurn(player);
					myLead = player.getLead();
					String leadStats = String.format("%s%nHP: %s/%s%n" + "State: %s", 
	   						 						 myLead.getName(), myLead.getHP(),
	   						 						 myLead.getmaxHP(), myLead.getState());
					txtMe.setText(leadStats);
					comboSwitch.setEnabled(false);
					ready = false;
					txtInfo.setText(oldLead + " is called back and " + myLead.getName() + " is switched in!");
				}
			}
		});
	}
}
