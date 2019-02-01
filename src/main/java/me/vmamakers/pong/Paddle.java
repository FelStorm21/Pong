package me.vmamakers.pong;

import lombok.Getter;
import lombok.Setter;

public class Paddle {
	
	private int y;  // this is for the center of the paddle
	@Getter @Setter private int width = 10;
	@Getter @Setter private int height = 100;
	@Getter @Setter private boolean canMove = true;
	
	public Paddle(int initialY) {
		y = initialY;
	}
	
	public void move(int dy) {
		y -= dy;
	}
	
	public int getTransformedY() {
		return y - height / 2;
	}
	
}
