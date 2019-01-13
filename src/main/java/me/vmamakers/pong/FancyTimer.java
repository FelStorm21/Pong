package me.vmamakers.pong;

import java.awt.event.ActionListener;

import javax.swing.Timer;

import lombok.Getter;
import lombok.Setter;

public class FancyTimer extends Timer {
	
	@Getter @Setter private String id;
	
	public FancyTimer(int delay, ActionListener listener) {
		super(delay, listener);
	}

}
