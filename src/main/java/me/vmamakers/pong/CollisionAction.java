package me.vmamakers.pong;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class CollisionAction extends AbstractAction {

	private GamePanel gamePanel;
	
	public CollisionAction(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {  // 2 * radius for getHeight() equiv???
		System.out.println("collisionAction performed");
		if (gamePanel.getBall().getTransformedY() == gamePanel.getOffsetBorderThickness(5) || 
			gamePanel.getBall().getTransformedY() == gamePanel.getHeight() - gamePanel.getOffsetBorderThickness(5) - 2 * gamePanel.getBall().getRadius() ||
			gamePanel.getBall().getTransformedX() == gamePanel.getOffsetBorderThickness(5) + gamePanel.getLeftPaddle().getWidth() ||
			gamePanel.getBall().getTransformedX() == gamePanel.getWidth() - gamePanel.getOffsetBorderThickness(5) - gamePanel.getRightPaddle().getWidth() - 2 * gamePanel.getBall().getRadius()) {
		
			firePropertyChange("hasCollided", false, true);
		}
	}
	
}
