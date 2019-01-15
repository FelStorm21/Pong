package me.vmamakers.pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
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
	
//	public void bindPractice(KeyAction[] actions, Class<? extends KeyAction> clazz) {
//		Constructor<? extends KeyAction> constructor = null;
//		try {
//			constructor = clazz.getConstructor(String.class, boolean.class);
//		} catch (NoSuchMethodException | SecurityException e1) {
//			e1.printStackTrace();
//		}
//		for (int i = 0; i < actions.length; i++) {
//			try {
//				actions[i] = constructor.newInstance("UP", false);
//			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//				e.printStackTrace();
//			}
//		}
//		
//	}
	
	public void bindActionsToKeys(int[] keyCodes, KeyAction[] actions, Class<? extends KeyAction> clazz) {
		Constructor<? extends KeyAction> constructor = null;
		try {
			constructor = clazz.getConstructor(String.class, boolean.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
		String[] keyNames = Arrays.stream(keyCodes).mapToObj(KeyEvent::getKeyText).map(String::toUpperCase).toArray(String[]::new);
		for (int i = 0; i < actions.length; i++) {
			boolean stateBool = (i > actions.length / 2 - 1) ? true : false;   //previously i > 1
			String stateStr = (i > actions.length / 2 - 1) ? "released" : "pressed";   //previously i > 1
			int j = (i > actions.length / 2 - 1) ? i - keyCodes.length : i;  //previously ? i - 2
			
			try {
				actions[i] = constructor.newInstance(keyNames[j], stateBool);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
			getInputMap().put(KeyStroke.getKeyStroke(keyCodes[j], 0, stateBool), keyNames[j] + " key " + stateStr);  // JUST USE keyNames INSTEAD OF getKeyStroke
			getActionMap().put(keyNames[j] + " key " + stateStr, actions[i]);
		}
	}
	
	public int getOffsetBorderThickness(int offset) {
		CompoundBorder border = (CompoundBorder) this.getBorder();
		LineBorder insideBorder = (LineBorder) border.getInsideBorder();
		LineBorder outsideBorder = (LineBorder) border.getOutsideBorder();
		return insideBorder.getThickness() + outsideBorder.getThickness() + offset;
	}

}
