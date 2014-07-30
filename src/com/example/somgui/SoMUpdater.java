package com.example.somgui;

import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import com.example.statesofmatter.Attack;
import com.example.statesofmatter.LocalGameRunner;

public class SoMUpdater {
	//TODO need to reset state selection when monster gets switched to be on that monster's current state
	/*public Display getDisplay() {
		return Display.findDisplay(this);
	}*/
	
	public static Thread startBattle(LocalGameRunner currentRunner, 
			Display currentDisplay, Button battleButton) {
		final LocalGameRunner runner = currentRunner;
		final Display display = currentDisplay;
		final Button btnBattle = battleButton;
		
		return new Thread() {
			public void run() {
				while (!runner.getBattleStarted()) {
					synchronized (runner.lock) {
						try {
							runner.lock.wait();
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}	
				}
				SoMBattle somBattle = new SoMBattle(runner);
				openBattle(somBattle, somBattle.display.getDefault());
				display.asyncExec(new Runnable() {
					public void run() {
						btnBattle.setText("Battling");
					}
				});
			}
		};	
	}
	
	public static void openBattle(SoMBattle newBattle, Display battleDisplay) {
		final Display display = battleDisplay;
		final SoMBattle somBattle = newBattle;
		
		display.asyncExec(new Runnable() {
			public void run() {
				somBattle.open();
			}
		});
	}
	
	public static Thread doStuff(LocalGameRunner currentRunner, Display currentDisplay, 
			CCombo combo, Text myStats, Text oppStats, Text info,
			Button bAttack, Button bState, Button bItem, Button bSwitch) {
		final LocalGameRunner runner = currentRunner;
		final Display display = currentDisplay;
		final CCombo thisCombo = combo;
		final Text txtMe = myStats;
		final Text txtOpp = oppStats;
		final Text txtInfo = info;
		final Button btnAttack = bAttack;
		final Button btnState = bState;
		final Button btnItem = bItem;
		final Button btnSwitch = bSwitch;
		
		return new Thread() {
			public void run() {
				while (runner.getTurnReady()) {
					synchronized (runner.lock) {
						try {
							runner.lock.wait();
						} catch (InterruptedException e1) {	}
					}
				}
				
				display.asyncExec(new Runnable() {
					public void run() {
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
						//txtInfo.setText("Stuff that will get printed.");
						btnAttack.setEnabled(true);
						btnState.setEnabled(true);
						btnItem.setEnabled(true);
						btnSwitch.setEnabled(true);
						thisCombo.setText("");
					}
				});
			}
		};
	}
	
	public static Thread doStuff(LocalGameRunner currentRunner, Display currentDisplay, 
			CCombo combo, CCombo attackList, Text myStats, Text oppStats, Text info,
			Button bAttack, Button bState, Button bItem, Button bSwitch) {
		final LocalGameRunner runner = currentRunner;
		final Display display = currentDisplay;
		final CCombo thisCombo = combo;
		final CCombo attacks = attackList;
		final Text txtMe = myStats;
		final Text txtOpp = oppStats;
		final Text txtInfo = info;
		final Button btnAttack = bAttack;
		final Button btnState = bState;
		final Button btnItem = bItem;
		final Button btnSwitch = bSwitch;
		
		return new Thread() {
			public void run() {
				while (runner.getTurnReady()) {
					synchronized (runner.lock) {
						try {
							runner.lock.wait();
						} catch (InterruptedException e1) {	}
					}
				}
				
				display.asyncExec(new Runnable() {
					public void run() {
						updateStats(runner, txtMe, txtOpp);
						updateAttacks(runner, attacks);
						//txtInfo.setText("Stuff that will get printed.");
						btnAttack.setEnabled(true);
						btnState.setEnabled(true);
						btnItem.setEnabled(true);
						btnSwitch.setEnabled(true);
						thisCombo.setText("");
					}
				});
			}
		};
	}
	
	private static void updateAttacks(LocalGameRunner runner, CCombo attackList) {
		attackList.remove(0, (attackList.getItemCount() - 1));
		attackList.setText("");
		for (Attack a : runner.getPlayer().getLead().getAttacks()) {
			if (a != null)
				attackList.add(a.getName());
		}
	}
	
	private static void updateStats(LocalGameRunner runner, Text txtMe, Text txtOpp) {
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
}
