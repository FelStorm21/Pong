package me.vmamakers.pong;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import lombok.Getter;
import lombok.Setter;

public class ArrowAction extends AbstractAction {

	private static final long serialVersionUID = 2657249535618486846L;
	
	@Getter @Setter private String id;
	
	public ArrowAction(String id) {
		this.id = id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		firePropertyChange(id, false, true);
//		switch (id) {
//			case "DOWN":
//				System.out.println("down pressed");
//			case "UP":
//				System.out.println("up pressed");
//			case "LEFT":
//				System.out.println("left pressed");
//			case "RIGHT":
//				System.out.println("right pressed");
//		}
	}

}
