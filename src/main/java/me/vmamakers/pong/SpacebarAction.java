package me.vmamakers.pong;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeSupport;

import javax.swing.AbstractAction;	

public class SpacebarAction extends AbstractAction 	{

	private static final long serialVersionUID = 3315319539456505190L;
	
	private int counter;
	private PropertyChangeSupport pcs;
	
	public SpacebarAction() {
		pcs = new PropertyChangeSupport(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		counter++;
		if (counter == 10) {
			counter = 2;
		}
		if (counter == 1) {
//			System.out.println("game started");
			pcs.firePropertyChange("startGame", false, true);
		} else if (counter % 2 == 0){
//			System.out.println("pause begun");
			pcs.firePropertyChange("beginPause", false, true);
		} else {
//			System.out.println("pause ended");
			pcs.firePropertyChange("endPause", false, true);
		}
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public PropertyChangeSupport getPcs() {
		return pcs;
	}

}
