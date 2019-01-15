package me.vmamakers.pong;

import java.awt.event.ActionEvent;

import lombok.Getter;
import lombok.Setter;	

public class SpacebarAction extends KeyAction {

	private static final long serialVersionUID = 3315319539456505190L;
	
	@Getter @Setter private String id;
	@Getter @Setter private boolean onRelease;
	@Setter private boolean isBlocking = false; 
	private boolean onStart = true;
	private int counter;
	
	public SpacebarAction(String id, boolean onRelease) {
		super(id, onRelease);
		this.id = id;
		this.onRelease = onRelease;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {  // depends on which action it is, i.e. depends on onRelease
//		if (!onRelease) {  // on pressed spacebar
//			
//		} else {
//			
//		}
		
		if (onRelease) {   // THIS IS FOR THE RELEASED OBJECT
//			System.out.println("This is the RELEASED action");
			firePropertyChange(id, false, true);
		} else if (!isBlocking) {   // THIS IS FOR THE PRESSED OBJECT
			counter++;
//			System.out.println(counter);
			isBlocking = true;
//			System.out.println("This the PRESSED action");
			if (onStart) {
				onStart = false;
				counter = 0;
				firePropertyChange("startGame", false, true);
			}
			else {
				if (counter % 2 != 0) {
					firePropertyChange("beginPause", false, true);
				} else {
					firePropertyChange("endPause", false, true);
				}	
			}
		}
		
		
//		if (onStart && !onRelease) {
//			onStart = false;
//			firePropertyChange("startGame", false, true);
//			super.printKeyEvent(id, onRelease); 
//		} else if (!onStart) {
//			if (!onRelease && !isBlocking) {
//				counter++;
//				isBlocking = true;
//				if (counter % 2 != 0) {
//					firePropertyChange("beginPause", false, true);
//					super.printKeyEvent(id, onRelease);
//				} else {
//					firePropertyChange("endPause", false, true);
//					super.printKeyEvent(id, onRelease);
//				}
//				
//			} 
//			if (onRelease) {  // key released code
//				System.out.println("space released actionPerformed");
//				firePropertyChange("released", false, true); 
//				super.printKeyEvent(id, onRelease);
//			}
//		} 
		
//		counter++;
//		if (counter == 10) {
//			counter = 2;
//		}
//		if (counter == 1) {
////			System.out.println("game started");
//			firePropertyChange("startGame", false, true);
//		} else if (!onRelease && !isBlocking && counter % 2 == 0){
////			System.out.println("pause begun");
//			isBlocking = true;
//			firePropertyChange("beginPause", false, true);
//		} else if (onRelease) {
////			System.out.println("pause ended");
//			firePropertyChange("endPause", false, true);
//		}
//		super.printKeyEvent(id, onRelease);
	}

}
