package me.vmamakers.pong;

import lombok.Getter;
import lombok.Setter;

public class Ball {
	
	@Getter @Setter private int radius = 12;
	@Setter private int x, y; // these are for the center of the ball
	
	public Ball(int initialX, int initialY) {
		x = initialX; 
		y = initialY;
	}
	
	public void move(int dx, int dy) {
		x += dx;
		y -= dy;  // due to weird origin of graphics
	}
	
	public int getTransformedX() {
		return x - radius;
	}
	
	public int getTransformedY() {
		return y - radius;
	}
	
}
