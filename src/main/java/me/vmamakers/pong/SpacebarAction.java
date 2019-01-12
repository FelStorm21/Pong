package me.vmamakers.pong;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;	

public class SpacebarAction extends AbstractAction 	{

	private static final long serialVersionUID = 3315319539456505190L;
	
	private int counter;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		counter++;
		if (counter == 10) {
			counter = 2;
		}
		if (counter == 1) {
//			System.out.println("game started");
			firePropertyChange("startGame", false, true);
		} else if (counter % 2 == 0){
//			System.out.println("pause begun");
			firePropertyChange("beginPause", false, true);
		} else {
//			System.out.println("pause ended");
			firePropertyChange("endPause", false, true);
		}
	}

}
