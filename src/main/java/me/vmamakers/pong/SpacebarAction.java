package me.vmamakers.pong;

import java.awt.event.ActionEvent;

import lombok.Getter;
import lombok.Setter;	

public class SpacebarAction extends KeyAction {

	private static final long serialVersionUID = 3315319539456505190L;
	
	@Getter @Setter private String id;
	@Getter @Setter private boolean onRelease;
	@Setter private boolean isBlocking = false; 
	private int counter;
	
	public SpacebarAction(String id, boolean onRelease) {
		super(id, onRelease);
		this.id = id;
		this.onRelease = onRelease;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		counter++;
		if (counter == 10) {
			counter = 2;
		}
		if (counter == 1) {
//			System.out.println("game started");
			firePropertyChange("startGame", false, true);
		} else if (!onRelease && !isBlocking && counter % 2 == 0){
//			System.out.println("pause begun");
			isBlocking = true;
			firePropertyChange("beginPause", false, true);
		} else if (onRelease) {
//			System.out.println("pause ended");
			firePropertyChange("endPause", false, true);
		}
		super.printKeyEvent(id, onRelease);
	}

}
