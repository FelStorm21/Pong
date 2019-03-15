package me.vmamakers.pong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import lombok.Getter;
import lombok.Setter;

public class GamePanel extends JPanel {
	
	//get width of rectangle from Paddle class and size of ball from Ball class
	// WIN OR LOSE LISTENER
	
	private static final long serialVersionUID = -3185492084353029707L;
	@Getter @Setter private boolean onStart = true;
	private boolean reversingSlope = false;
	@Getter @Setter private boolean gameOver = false;
	@Getter @Setter private JLabel endInfo, endLabel;
	
	@Getter @Setter private int p1Score, p2Score;  //p1 is left, p2 is right
	
	@Getter private Paddle leftPaddle, rightPaddle;
	@Getter private Ball ball;
	private int borderThickness;
	@Getter @Setter private PropertyChangeSupport pcs;
	
	public GamePanel() {
		pcs = new PropertyChangeSupport(this);
	}
	
	public void initGameComponents() {
		leftPaddle = new Paddle(this.getHeight() / 2);
		rightPaddle = new Paddle(this.getHeight() / 2);
		ball = new Ball(this.getWidth() / 2, this.getHeight() / 2);
		
		borderThickness = getOffsetBorderThickness(5);
	}
	
	@Override
	public void paintComponent(Graphics g) {  // THE SPOT WHERE THE BALL STARTS IS GREYED OUT
		super.paintComponent(g);
//		boolean reversingSlope = false;
//		if (isOnStart /*&& Arrays.asList(this.getComponents()).stream().filter(x -> x.getName() == "title" || x.getName() == "readyToPause").count() == 0*/) {
//			isOnStart = false;
//		}
	//	if (!onStart) {
			g.setColor(Color.white);
			g.fillRect(borderThickness, leftPaddle.getTransformedY(), leftPaddle.getWidth(), leftPaddle.getHeight());
			g.fillRect(this.getWidth() - borderThickness - rightPaddle.getWidth(), rightPaddle.getTransformedY(), rightPaddle.getWidth(), rightPaddle.getHeight());
			// put ball stuff here
//			if ( (isOutOfBoundsNoOffset() && !isTouchingPaddle()) || (!isOutOfBoundsNoOffset() && isTouchingPaddle()) ) {
			if (gameOver) {
				g.setColor(Color.red);
			}
			if (!onStart) {
				g.fillRect(ball.getTransformedX(), ball.getTransformedY(), 2 * ball.getRadius(), 2 * ball.getRadius());
			}
			if (isTouchingPaddle())
//					if (ball.getSlope() < 0.01) {
						ball.setDirection(-ball.getDirection());
//					} else {
	//					System.out.println("incoming slope: " + ball.getSlope());
		//				ball.setSlope(-ball.getSlope());     // it might work just to leave it as is and just change direction
	//					System.out.println("outgoing slopoe: " + ball.getSlope());
//					}
//					ball.move(2);
		//	} 
			if (isLegalOutOfBounds()) {
				ball.setSlope(-ball.getSlope());
			}
	//		if (!gameOver) {
			if (!gameOver && isIllegalOutOfBounds()) {
//					ball.setAbleToMove(false);
					gameOver = true;
					pcs.firePropertyChange("Game ended", false, true);
//					g.setColor(Color.red);
//					add(endLabel, BorderLayout.CENTER);
////					endLabel.setVisible(true);
//					add(endInfo, BorderLayout.NORTH);
////					endInfo.setVisible(true);
					System.out.println("at end of !gameOver section");
				}
	//		}
			
//				g.setColor(Color.black);
	}
	
	public void repaintSuper() {
		super.repaint();
	}
	
	private boolean isTouchingPaddle() {  //FIX THIS SO IT'S FALSE WHEN isOutOfBoundsNoOffset() is TRUE
		if ( (ball.getTransformedX() + 2 * ball.getRadius() > getWidth() - getOffsetBorderThickness(5) - rightPaddle.getWidth() &&
				ball.getTransformedY() + 2 * ball.getRadius() > rightPaddle.getTransformedY() &&
				ball.getTransformedY() < rightPaddle.getTransformedY() + rightPaddle.getHeight() ) ||
				(ball.getTransformedX() < getOffsetBorderThickness(5) + leftPaddle.getWidth() &&
				ball.getTransformedY() + 2 * ball.getRadius() > leftPaddle.getTransformedY() &&
				ball.getTransformedY() < leftPaddle.getTransformedY() + leftPaddle.getHeight()) ) 
				{
			System.out.println("Touching paddle");
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isLegalOutOfBounds() {  // EVENTUALLY FIX SO THAT ONLY TOP AND BOTTOM WALLS ARE ALLOWED
		if ( 	ball.getTransformedY() < getBorderThickness() ||
				ball.getTransformedY() + 2 * ball.getRadius() > getHeight() - getBorderThickness() ) 
		{
			System.out.println("LEGAL - Out of bounds");
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isIllegalOutOfBounds() {
		if ( ball.getTransformedX() + 2 * ball.getRadius() > getWidth() - getBorderThickness() ) {
			System.out.println("ILLEGAL - Out of bounds");
			System.out.println("p2 lost");
			p1Score++;
			return true;
		} 
		else if ( ball.getTransformedX() < getBorderThickness() ) {
			System.out.println("ILLEGAL - Out of bounds");
			System.out.println("p1 lost");
			p2Score++;
			return true;
		}
		else {
			return false;
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
		// simplify using instanceof keyword and type casting ????
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
	
	public int getBorderThickness() {
		CompoundBorder border = (CompoundBorder) this.getBorder();
		LineBorder insideBorder = (LineBorder) border.getInsideBorder();
		LineBorder outsideBorder = (LineBorder) border.getOutsideBorder();
		return insideBorder.getThickness() + outsideBorder.getThickness();
	}
	
	private Component findComponentByName(String name) {
		return Arrays.asList(this.getComponents()).stream().filter(x -> x.getName().equals(name)).collect(Collectors.toList()).get(0);
	}

}
