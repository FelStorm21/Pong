package me.vmamakers.pong;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class KeyAction extends AbstractAction {
	
//	@Getter @Setter private String id;
//	@Getter @Setter private boolean onRelease;
	
	private static final long serialVersionUID = 2737910913802457127L;
	
	public KeyAction(String id, boolean onRelease) {
//		this.id = id;
//		this.onRelease = onRelease;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {}
	
	public void printKeyEvent(String id, boolean onRelease) {
		String pressedBool = onRelease ? "released" : "pressed";
		String newLine = onRelease ? "\n" : "";
		System.out.println(id.toLowerCase() + " " + pressedBool + newLine);
	}

}
