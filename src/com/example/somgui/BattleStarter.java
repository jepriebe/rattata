package com.example.somgui;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import com.example.statesofmatter.LocalGameRunner;

public class BattleStarter {

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
}
