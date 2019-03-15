package me.vmamakers.pong;

import java.awt.event.ActionListener;

import javax.swing.Timer;

import lombok.Getter;
import lombok.Setter;

public class FancyTimer extends Timer {
	
	@Getter @Setter private String id;
	@Getter @Setter private boolean gameOver = false;
	
	public FancyTimer(int delay, ActionListener listener) {
		super(delay, listener);
	}
	
	@Override public void start() {
		if (!gameOver) super.start();
	}
	
//	@Override public void stop() {
//		if (!gameOver) super.stop();
//	}
	
	@Override public void restart() {
		if (!gameOver) super.restart();
	}
	
}
