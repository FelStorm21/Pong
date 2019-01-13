package me.vmamakers.pong;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import lombok.Getter;

public class GamePanel extends JPanel {
	
	//get width of rectangle from Paddle class and size of ball from Ball class
	
	private static final long serialVersionUID = -3185492084353029707L;
	private boolean isOnStart = true;
	
	@Getter private Paddle leftPaddle, rightPaddle;
	@Getter private Ball ball;
	private int borderThickness;
	
	public void initGameComponents() {
		leftPaddle = new Paddle(this.getHeight() / 2);
		rightPaddle = new Paddle(this.getHeight() / 2);
		ball = new Ball(this.getWidth() / 2, this.getHeight() / 2);
		
		borderThickness = getOffsetBorderThickness(5);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (isOnStart && Arrays.asList(this.getComponents()).stream().filter(x -> x.getName() == "title" || x.getName() == "readyToPause").count() == 0) {
			isOnStart = false;
		}
		if (!isOnStart) {
			g.setColor(Color.white);
			g.fillRect(borderThickness, leftPaddle.getTransformedY(), leftPaddle.getWidth(), leftPaddle.getHeight());
			g.fillRect(this.getWidth() - borderThickness - rightPaddle.getWidth(), rightPaddle.getTransformedY(), rightPaddle.getWidth(), rightPaddle.getHeight());
			g.fillRect(ball.getTransformedX(), ball.getTransformedY(), 2 * ball.getRadius(), 2 * ball.getRadius());
//			g.setColor(Color.black);
		}
	}
	
	public int getOffsetBorderThickness(int offset) {
		CompoundBorder border = (CompoundBorder) this.getBorder();
		LineBorder insideBorder = (LineBorder) border.getInsideBorder();
		LineBorder outsideBorder = (LineBorder) border.getOutsideBorder();
		return insideBorder.getThickness() + outsideBorder.getThickness() + offset;
	}

}
