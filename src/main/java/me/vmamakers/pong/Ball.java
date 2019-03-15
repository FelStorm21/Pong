package me.vmamakers.pong;

import lombok.Getter;
import lombok.Setter;

public class Ball {
	
	@Getter @Setter private int radius = 12;
	@Getter @Setter private int x, y; // these are for the center of the ball
	@Getter @Setter private int initialX, initialY;
	@Getter @Setter private boolean isColliding = false;
	@Getter @Setter private boolean ableToMove = true;
	@Getter @Setter private double slope; 
	@Getter @Setter private double direction;
	
	public Ball(int initialX, int initialY) {
		this.initialX = initialX;
		this.initialY = initialY;
		x = initialX; 
		y = initialY;
		
		slope = 0;
		direction = 1;
	}
	
	public void move(int dx) {
		if (ableToMove) {
			x += direction * dx;
			y -= (int) (slope * dx);  // due to weird origin of graphics
		}
	}
	
	/**
	 * @return x-coordinate of left edge of ball
	 */
	public int getTransformedX() { 
		return x - radius;
	}
	
	/**
	 * @return y-coordinate of top edge of ball
	 */
	public int getTransformedY() {
		return y - radius;
	}
	
//	public void setSlope(double slope) {
//		if (slope < 0.15) {
//			slope += 0.15;
//		}
//		this.slope = slope;
//	}
	
}
