package me.vmamakers.pong;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	//get width of rectangle from Paddle class and size of ball from Ball class
	
	private static final long serialVersionUID = -3185492084353029707L;
	private boolean isOnStart = true;
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (isOnStart && Arrays.asList(this.getComponents()).stream().filter(x -> x.getName() == "title" || x.getName() == "readyToPause").count() == 0) {
			isOnStart = false;
		}
		if (!isOnStart) {
			g.setColor(Color.white);
			g.fillRect(15, this.getHeight() / 2 - 50, 10, 100);
			g.fillRect(this.getWidth() - 25, this.getHeight() / 2 - 50, 10, 100);
//			g.setColor(Color.black);
		}
		
		
	}

}
