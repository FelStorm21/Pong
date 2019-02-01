package me.vmamakers.pong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

// Potentially add fader to "Press space bar to play" JLabel
// MAKE CLASS BLOCK UNTIL START SCREEN IS GONE

public class Gui {
	
	private JFrame frame;
	private GamePanel gamePanel;
	private JLabel title, readyToPlay, pauseLabel, pauseInfo;
	private FancyTimer timer1, timer2;
	
	public static final int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	public Gui() {
		SwingUtilities.invokeLater(this::initGui);
	}
	
	public void initGui() {
		timer1 = new FancyTimer(5, (e) -> {
			if (timer1.getId().equals("UP") && gamePanel.getRightPaddle().isCanMove() && gamePanel.getRightPaddle().getTransformedY() > gamePanel.getOffsetBorderThickness(5)) {
				gamePanel.getRightPaddle().move(1);
//				gamePanel.repaint();
			} else if (timer1.getId().equals("DOWN") && gamePanel.getRightPaddle().isCanMove() && gamePanel.getRightPaddle().getTransformedY() < gamePanel.getHeight() - gamePanel.getOffsetBorderThickness(5) - gamePanel.getRightPaddle().getHeight()) {
				gamePanel.getRightPaddle().move(-1);
//				gamePanel.repaint();
			} 
//			else if (timer1.getId().equals("W") && gamePanel.getLeftPaddle().getTransformedY() > gamePanel.getOffsetBorderThickness(5)) {
//				gamePanel.getLeftPaddle().move(1);
//			} else if (timer1.getId().equals("S") && gamePanel.getLeftPaddle().getTransformedY() < gamePanel.getHeight() - gamePanel.getOffsetBorderThickness(5) - gamePanel.getLeftPaddle().getHeight()) {
//				gamePanel.getLeftPaddle().move(-1);
////				gamePanel.repaint();
//			}
			gamePanel.repaint();
		});
		
		timer2 = new FancyTimer(5, (e) -> {
			if (timer2.getId().equals("W") && gamePanel.getLeftPaddle().isCanMove() && gamePanel.getLeftPaddle().getTransformedY() > gamePanel.getOffsetBorderThickness(5)) {
				gamePanel.getLeftPaddle().move(1);
			} else if (timer2.getId().equals("S") && gamePanel.getLeftPaddle().isCanMove() && gamePanel.getLeftPaddle().getTransformedY() < gamePanel.getHeight() - gamePanel.getOffsetBorderThickness(5) - gamePanel.getLeftPaddle().getHeight()) {
				gamePanel.getLeftPaddle().move(-1);
//				gamePanel.repaint();
			}
			gamePanel.repaint();
		});
		
		frame = new JFrame("Pong");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(screenWidth / 2, screenHeight / 2); 
		frame.setLocationRelativeTo(null);
		
		gamePanel = new GamePanel();
		gamePanel.setBackground(Color.black);
		gamePanel.setLayout(new BorderLayout());
		gamePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black, 3), BorderFactory.createLineBorder(Color.white, 7)));
		
		readyToPlay = new JLabel("Press space bar to play");
		readyToPlay.setName("readyToPlay");
		readyToPlay.setForeground(Color.white);
		readyToPlay.setFont(new Font("Monospaced", Font.PLAIN, 18));
		readyToPlay.setHorizontalAlignment(JLabel.CENTER);
		gamePanel.add(readyToPlay, BorderLayout.SOUTH);
		
		title = new JLabel("WELCOME TO PONG!");
		title.setName("title");
		title.setForeground(Color.white);
		title.setFont(new Font("Monospaced", Font.PLAIN, 36));
		title.setHorizontalAlignment(JLabel.CENTER);
		gamePanel.add(title, BorderLayout.CENTER);
		
		pauseLabel = new JLabel("GAME PAUSED");
		pauseLabel.setForeground(Color.white);
		pauseLabel.setFont(new Font("Monospaced", Font.PLAIN, 36));
		pauseLabel.setHorizontalAlignment(JLabel.CENTER);
		
		pauseInfo = new JLabel("Press space bar to pause");
		pauseInfo.setForeground(Color.white);
		pauseInfo.setFont(new Font("Monospaced", Font.PLAIN, 16));
		pauseInfo.setHorizontalAlignment(JLabel.CENTER);
		
		frame.add(gamePanel);
		setupListeners();
		prepareAndShow();
	}
	
	public void prepareAndShow() {
		frame.setVisible(true);
		gamePanel.initGameComponents();
	}
	
	public void setupListeners() {
		// THIS IS WEIRD IF YOU HOLD DOWN THE SPACE BAR
//		SpacebarAction spacePressed = new SpacebarAction();
//		gamePanel.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space bar pressed");
//		gamePanel.getActionMap().put("space bar pressed", spacePressed);
//		spacePressed.addPropertyChangeListener((evt) -> {
//			switch (evt.getPropertyName()) {
//				case "startGame":
//					gamePanel.remove(title);
//					gamePanel.remove(readyToPlay);
//					gamePanel.add(pauseLabel, BorderLayout.CENTER);
//					gamePanel.add(pauseInfo, BorderLayout.NORTH);
//					pauseLabel.setVisible(false);
//					pauseInfo.setVisible(true);
//					gamePanel.repaint();
//					break;
//				case "beginPause":
//					pauseLabel.setVisible(true);
////					gamePanel.repaint();
//					break;
//				case "endPause":
//					pauseLabel.setVisible(false);
////					gamePanel.repaint();
//					break;
//				default:
//					System.out.println("ERROR IN PROPERTY NAME");
//			}
//		}); 
		
		int[] spaceKeys = {KeyEvent.VK_SPACE};
		SpacebarAction[] spaceActions = new SpacebarAction[2 * spaceKeys.length];
		gamePanel.bindActionsToKeys(spaceKeys, spaceActions, SpacebarAction.class);
//		System.out.println(spaceActions[0].getId() + " " + spaceActions[0].isOnRelease() + ", " + spaceActions[1].getId() + " " + spaceActions[1].isOnRelease());
		
		spaceActions[0].addPropertyChangeListener((evt) -> {  // space pressed
			System.out.println("space pressed listener works");
//			if (evt.getPropertyName().equals("startGame")) {
//				System.out.println("game started");
//			} else if (evt.getPropertyName().equals("beginPause")) {
//				System.out.println("pause started");
//			} else if (evt.getPropertyName().equals("endPause")) {
//				System.out.println("pause ended");
//			}
			switch (evt.getPropertyName()) {
				case "startGame":
					gamePanel.remove(title);
					gamePanel.remove(readyToPlay);
					gamePanel.add(pauseLabel, BorderLayout.CENTER);
					gamePanel.add(pauseInfo, BorderLayout.NORTH);
					pauseLabel.setVisible(false);
					pauseInfo.setVisible(true);
					gamePanel.repaint();
					break;
				case "beginPause":
					System.out.println("begin pause");
					pauseLabel.setVisible(true);
					gamePanel.getLeftPaddle().setCanMove(false);
					gamePanel.getRightPaddle().setCanMove(false);
	//				gamePanel.repaint();
					break;
				case "endPause":
					System.out.println("end pause");
					pauseLabel.setVisible(false);
					gamePanel.getLeftPaddle().setCanMove(true);
					gamePanel.getRightPaddle().setCanMove(true);
	//				gamePanel.repaint();
					break;
				default:
					System.out.println("ERROR IN PROPERTY NAME");
			}
		});
		
		spaceActions[1].addPropertyChangeListener((evt) -> {  // space released
			System.out.println("space released listener works");
			//if you switch pauseLabel.setVisible(true) to here, then the pause comes on as you hold space with the right prints
			spaceActions[0].setBlocking(false);
		});
		
		// START AWSD KEYS
		
		int[] wsKeys = {KeyEvent.VK_W, KeyEvent.VK_S};
		ArrowAction[] wsActions = new ArrowAction[2 * wsKeys.length];
		gamePanel.bindActionsToKeys(wsKeys, wsActions, ArrowAction.class);
		
		for (int i = 0; i <= 1; i++) {
			String id = (i == 0) ? "W" : "S";
			wsActions[i].addPropertyChangeListener((evt) -> {  // PRESSED LISTENERS
				System.out.println(id.toLowerCase() + " pressed listener works");
				timer2.setId(id);
				timer2.start();	
			});
		}
		// MAKE THE RELEASE LISTENERS CONCISE USING HASHMAP OF PRESS INDEX TO RELEASE INDEX
		wsActions[2].addPropertyChangeListener((evt) -> {  // up release listener
			System.out.println("W release listener works");
			timer2.stop();
			wsActions[0].setBlocking(false);
		});
		
		wsActions[3].addPropertyChangeListener((evt) -> {  // down release listener
			System.out.println("S release listener works");
			timer2.stop();
			wsActions[1].setBlocking(false);
		});
		
		// THESE ARE STILL ACCESSIBLE WHEN PAUSED
//		String[] arrowKeys = {"UP", "DOWN"};
		int[] arrowKeys = {KeyEvent.VK_UP, KeyEvent.VK_DOWN};
		ArrowAction[] arrowActions = new ArrowAction[2 * arrowKeys.length];
		gamePanel.bindActionsToKeys(arrowKeys, arrowActions, ArrowAction.class);
		
		for (int i = 0; i <= 1; i++) {
			String id = (i == 0) ? "UP" : "DOWN";
			arrowActions[i].addPropertyChangeListener((evt) -> {  // PRESSED LISTENERS
				System.out.println(id.toLowerCase() + " pressed listener works");
				timer1.setId(id);
				timer1.start();	
			});
		}
		
		arrowActions[2].addPropertyChangeListener((evt) -> {  // up release listener
			System.out.println("up release listener works");
			timer1.stop();
			arrowActions[0].setBlocking(false);
		});
		
		arrowActions[3].addPropertyChangeListener((evt) -> {  // down release listener
			System.out.println("down release listener works");
			timer1.stop();
			arrowActions[1].setBlocking(false);
		});
		
	}
	
}
