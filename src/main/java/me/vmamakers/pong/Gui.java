package me.vmamakers.pong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

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
		String[] arrowKeys = {"UP", "DOWN"};
		ArrowAction[] arrowActions = new ArrowAction[arrowKeys.length];
		for (int i = 0; i < arrowKeys.length; i++) {
			arrowActions[i] = new ArrowAction(arrowKeys[i]);
			gamePanel.getInputMap().put(KeyStroke.getKeyStroke(arrowKeys[i]), arrowKeys[i].toLowerCase() + " arrow");
			gamePanel.getActionMap().put(arrowKeys[i].toLowerCase() + " arrow", arrowActions[i]);
			
			arrowActions[i].addPropertyChangeListener((evt) -> {
				switch (evt.getPropertyName()) {
				case "UP":
					gamePanel.getLeftP().move(10);
					gamePanel.repaint();
					break;
				case "DOWN":
					gamePanel.getLeftP().move(-10);
					gamePanel.repaint();
					break;
				default:
					System.out.println("ERROR");
				}
			});
		}
		
	}
	
}
