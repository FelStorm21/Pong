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
	private FancyTimer timer;
	
	public static final int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	public Gui() {
		SwingUtilities.invokeLater(this::initGui);
	}
	
	public void initGui() {
		timer = new FancyTimer(5, (e) -> {
			if (timer.getId().equals("UP") && gamePanel.getLeftPaddle().getTransformedY() > gamePanel.getOffsetBorderThickness(5)) {
				gamePanel.getLeftPaddle().move(1);
//				gamePanel.repaint();
			} else if (timer.getId().equals("DOWN") && gamePanel.getLeftPaddle().getTransformedY() < gamePanel.getHeight() - gamePanel.getOffsetBorderThickness(5) - gamePanel.getLeftPaddle().getHeight()) {
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
		
		spaceActions[0].addPropertyChangeListener((evt) -> {  // space pressed
			System.out.println("space pressed listener works");
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
					pauseLabel.setVisible(true);
	//				gamePanel.repaint();
					break;
				case "endPause":
					pauseLabel.setVisible(false);
	//				gamePanel.repaint();
					break;
				default:
					System.out.println("ERROR IN PROPERTY NAME");
			}
		});
		
		spaceActions[1].addPropertyChangeListener((evt) -> {  // space released
			System.out.println("space released listener works");
			spaceActions[0].setBlocking(false);
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
				timer.setId(id);
				timer.start();	
			});
		}
		
		arrowActions[2].addPropertyChangeListener((evt) -> {  // up release listener
			System.out.println("up release listener works");
			timer.stop();
			arrowActions[0].setBlocking(false);
		});
		
		arrowActions[3].addPropertyChangeListener((evt) -> {  // down release listener
			System.out.println("down release listener works");
			timer.stop();
			arrowActions[1].setBlocking(false);
		});
		
	}
	
}
