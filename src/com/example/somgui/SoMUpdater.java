package com.example.somgui;

import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import com.example.statesofmatter.Attack;
import com.example.statesofmatter.LocalGameRunner;

public class SoMUpdater extends Thread {
	
	private LocalGameRunner runner;
	private Display display;
	private CCombo comboAttack;
	private CCombo comboState;
	private CCombo comboItem;
	private CCombo comboSwitch;
	private Text myStats;
	private Text oppStats;
	private Text info;
	private Button btnAttack;
	private Button btnState;
	private Button btnItem;
	private Button btnSwitch;
	
	public SoMUpdater(LocalGameRunner runner, Display display, 
			CCombo comboAttack, CCombo comboState, CCombo comboItem, 
			CCombo comboSwitch, Text myStats, Text oppStats, Text info, 
			Button btnAttack, Button btnState, Button btnItem, 
			Button btnSwitch) {
		this.runner = runner;
		this.display = display;
		this.comboAttack = comboAttack;
		this.comboState = comboState;
		this.comboItem = comboItem;
		this.comboSwitch = comboSwitch;
		this.myStats = myStats;
		this.oppStats = oppStats;
		this.info = info;
		this.btnAttack = btnAttack;
		this.btnState = btnState;
		this.btnItem = btnItem;
		this.btnSwitch = btnSwitch;
	}
	
	public void setParams(CCombo comboAttack, CCombo comboState, 
			CCombo comboItem, CCombo comboSwitch, Text myStats, Text oppStats, 
			Text info, Button btnAttack, Button btnState, Button btnItem, 
			Button btnSwitch) {
		this.comboAttack = comboAttack;
		this.comboState = comboState;
		this.comboItem = comboItem;
		this.comboSwitch = comboSwitch;
		this.myStats = myStats;
		this.oppStats = oppStats;
		this.info = info;
		this.btnAttack = btnAttack;
		this.btnState = btnState;
		this.btnItem = btnItem;
		this.btnSwitch = btnSwitch;
	}
	
	private void updateAttacks(LocalGameRunner runner, CCombo comboAttack) {
		this.comboAttack = comboAttack;
		
		comboAttack.remove(0, (comboAttack.getItemCount() - 1));
		comboAttack.setText("");
		for (Attack a : runner.getPlayer().getLead().getAttacks()) {
			if (a != null)
				comboAttack.add(a.getName());
		}
	}
	
	private void updateState(LocalGameRunner runner, CCombo comboState) {
		this.comboState = comboState;
		
		String currentState = runner.getPlayer().getLead().getState().toString();
		comboState.setText(currentState);
	}
	
	private void updateStats(LocalGameRunner runner, Text txtMe, Text txtOpp) {
		this.myStats = txtMe;
		this.oppStats = txtOpp;
		
		String myStats = String.format("%s%nHP: %s/%s%n" + "State: %s", 
  			 	 runner.getPlayer().getLead().getName(), 
				 	 runner.getPlayer().getLead().getHP(),
			 	 runner.getPlayer().getLead().getMaxHP(), 
			 	 runner.getPlayer().getLead().getState());
		String oppStats = String.format("%s%nHP: %s/%s%n" + "State: %s", 
				 runner.getOppLead().getName(), 
				 runner.getOppLead().getHP(),
				 runner.getOppLead().getMaxHP(), 
				 runner.getOppLead().getState());
		txtMe.setText(myStats);
		txtOpp.setText(oppStats);
	}
	
	public void run() {
		while (!runner.getGameOver()) {
			while (runner.getTurnWaiting()) {
				synchronized (runner.waitingLock){
					try {
						runner.waitingLock.wait();
					} catch (InterruptedException e) { }
				}
			}
			System.out.println("checkpoint 1");
			while (runner.getTurnReady()) {
				synchronized (runner.readyLock) {
					try {
						runner.readyLock.wait();
					} catch (InterruptedException e) {	}
				}
				System.out.println("trying to leave loop");
			}
			System.out.println("checkpoint 2");
			display.asyncExec(new Runnable() {
				public void run() {
					updateStats(runner, myStats, oppStats);
					updateAttacks(runner, comboAttack);
					updateState(runner, comboState);
					//updateItems
					//txtInfo.setText("Stuff that will get printed.");
					if (runner.getTurnReturn().getTurnFinished() == 0 || 
							runner.getTurnReturn().getTurnFinished() == 2 ||
							(runner.getTurnReturn().getTurnFinished() == 1 && 
							(runner.getTurnReturn().getFainted() == (runner.getPlayerNum() + 1) ||
							runner.getTurnReturn().getFainted() == 3))) {
						System.out.println("buttons set to true");
						btnAttack.setEnabled(true);
						btnState.setEnabled(true);
						btnItem.setEnabled(true);
						btnSwitch.setEnabled(true);
						comboSwitch.setText(runner.getPlayer().getLead().getName());
					}
				}
			});
			runner.setUpdateWaiting(false);
			synchronized (runner.waitingLock) {
				runner.waitingLock.notify();
			}
			System.out.println("checkpoint 3");
		}
		System.out.println("closing thread");
	}
}
