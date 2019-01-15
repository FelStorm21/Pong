package me.vmamakers.pong;

import java.awt.event.ActionEvent;

import lombok.Getter;
import lombok.Setter;

public class ArrowAction extends KeyAction {

	private static final long serialVersionUID = 2657249535618486846L;
	
	@Getter @Setter private String id;
	@Getter @Setter private boolean onRelease;
	@Getter @Setter private boolean isBlocking = false;
	@Setter private FancyTimer timer;
	
	public ArrowAction(String id, boolean onRelease) {
		super(id, onRelease);
		this.id = id;
		this.onRelease = onRelease;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
//		System.out.println("ArrowAction.actionPerformed called.");
		if (!onRelease && !isBlocking) {  // key pressed code
			isBlocking = true;
			firePropertyChange(id, false, true); 
			super.printKeyEvent(id, onRelease);
		} else if (onRelease) {  // key released code
			firePropertyChange(id, false, true); 
			super.printKeyEvent(id, onRelease);
		}
	}
	
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		String pressedBool = onRelease ? "released" : "pressed";
//		System.out.println(id + " key, " + pressedBool);
//		if (!onRelease && !isBlocking) {  // key pressed code
//			isBlocking = true;
//			timer.setId(id);
//			timer.start(); 
//		} else if (onRelease) {  // key released code
//			timer.stop();   // replace with timer.stop()
//			isBlocking = false;
//		} 
//	}
	
}
