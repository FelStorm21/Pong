package me.vmamakers.pong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

// Potentially add fader to "Press space bar to play" JLabel

public class Gui {
	
	private JFrame frame;
	private GamePanel gamePanel;
	private JLabel title, readyToPlay, pauseLabel, pauseInfo;
	
	public static final int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	public Gui() {
		SwingUtilities.invokeLater(this::initGui);
	}
	
	public void initGui() {
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
		SpacebarAction spacePressed = new SpacebarAction();
		gamePanel.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space bar pressed");
		gamePanel.getActionMap().put("space bar pressed", spacePressed);
		spacePressed.addPropertyChangeListener((evt) -> {
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
//					gamePanel.repaint();
					break;
				case "endPause":
					pauseLabel.setVisible(false);
//					gamePanel.repaint();
					break;
				default:
					System.out.println("ERROR IN PROPERTY NAME");
			}
		}); 
		
		// THESE ARE STILL ACCESSIBLE WHEN PAUSED
//		String[] arrowKeys = {"UP", "DOWN"};
		int[] arrowKeys = {KeyEvent.VK_UP, KeyEvent.VK_DOWN};
		String[] arrowKeysNames = new String[arrowKeys.length];
		arrowKeysNames = Arrays.stream(arrowKeys).mapToObj(KeyEvent::getKeyText).map(String::toUpperCase).toArray(String[]::new);
		ArrowAction[] arrowActions = new ArrowAction[2 * arrowKeys.length];
		
		for (int i = 0; i < arrowKeys.length; i++) {
			arrowActions[i] = new ArrowAction(arrowKeysNames[i], false);
			gamePanel.getInputMap().put(KeyStroke.getKeyStroke(arrowKeys[i], 0, false), arrowKeysNames[i] + " arrow pressed");
			gamePanel.getActionMap().put(arrowKeysNames[i] + " arrow pressed", arrowActions[i]);
			
			arrowActions[i + arrowKeys.length] = new ArrowAction(arrowKeysNames[i], true);
			gamePanel.getInputMap().put(KeyStroke.getKeyStroke(arrowKeys[i], 0, true), arrowKeysNames[i] + " arrow released");
			gamePanel.getActionMap().put(arrowKeysNames[i] + " arrow released", arrowActions[i + arrowKeys.length]);
		}
		
		arrowActions[2].addPropertyChangeListener((evt) -> {  // up release listener
			System.out.println("up release listener works");
			arrowActions[0].setBlocking(false);
//			switch (evt.getPropertyName()) {
//				case "UP":
//					gamePanel.getLeftPaddle().move(5);
//					gamePanel.repaint();
//					break;
//				case "DOWN":
//					gamePanel.getLeftPaddle().move(-5);
//					gamePanel.repaint();
//					break;
//				default:
//					System.out.println("ERROR");
//			}
		});
		
		arrowActions[3].addPropertyChangeListener((evt) -> {  // down release listener
			System.out.println("down release listener works");
			arrowActions[1].setBlocking(false);
//			switch (evt.getPropertyName()) {
//				case "UP":
//					gamePanel.getLeftPaddle().move(5);
//					gamePanel.repaint();
//					break;
//				case "DOWN":
//					gamePanel.getLeftPaddle().move(-5);
//					gamePanel.repaint();
//					break;
//				default:
//					System.out.println("ERROR");
//			}
		});
		
		arrowActions[0].addPropertyChangeListener((evt) -> {  // up pressed listener
			System.out.println("up pressed listener works");
			while (arrowActions[0].isBlocking()) {   // MULTI THREADING TO FIX WHILE BLOCKING ISSUE????
//				switch (evt.getPropertyName()) {
//					case "UP":
//						gamePanel.getLeftPaddle().move(5);
//						gamePanel.repaint();
//						break;
//					case "DOWN":
//						gamePanel.getLeftPaddle().move(-5);
//						gamePanel.repaint();
//						break;
//					default:
//						System.out.println("ERROR");
//				}
				System.out.println("moving up");
			}		
		});
		
		arrowActions[1].addPropertyChangeListener((evt) -> {  // down pressed listener
			System.out.println("down pressed listener works");
			while (!arrowActions[1].isBlocking()) {
//				switch (evt.getPropertyName()) {
//					case "UP":
//						gamePanel.getLeftPaddle().move(5);
//						gamePanel.repaint();
//						break;
//					case "DOWN":
//						gamePanel.getLeftPaddle().move(-5);
//						gamePanel.repaint();
//						break;
//					default:
//						System.out.println("ERROR");
//				}
				System.out.println("moving down");
			}		
		});
		
	}
	
}
