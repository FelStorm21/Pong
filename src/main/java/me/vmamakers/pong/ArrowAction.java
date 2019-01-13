package me.vmamakers.pong;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import lombok.Getter;
import lombok.Setter;

public class ArrowAction extends AbstractAction {

	private static final long serialVersionUID = 2657249535618486846L;
	
	@Getter @Setter private String id;
	@Getter @Setter private boolean onRelease;
	@Getter @Setter private boolean isBlocking = false;
	
	public ArrowAction(String id, boolean onRelease) {
		this.id = id;
		this.onRelease = onRelease;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		System.out.println("ArrowAction.actionPerformed called.");
		if (!onRelease && !isBlocking) {  // key pressed code
			isBlocking = true;
			firePropertyChange(id, false, true);
			
			switch (id) {
				case "DOWN":
					System.out.println("down pressed");
					break;
				case "UP":
					System.out.println("up pressed");
					break;
				case "LEFT":
					System.out.println("left pressed");
					break;
				case "RIGHT":
					System.out.println("right pressed");
					break;
			}
		} else if (onRelease) {  // key released code
			firePropertyChange(id, false, true);
			switch (id) {
				case "DOWN":
					System.out.println("down released");
					break;
				case "UP":
					System.out.println("up released");
					break;
				case "LEFT":
					System.out.println("left released");
					break;
				case "RIGHT":
					System.out.println("right released");
					break;
			}
		} 
	}

}
