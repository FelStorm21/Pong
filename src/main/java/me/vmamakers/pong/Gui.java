package me.vmamakers.pong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeSupport;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

// Potentially add fader to "Press space bar to play" JLabel
// MAKE CLASS BLOCK UNTIL START SCREEN IS GONE

public class Gui {
	
	private JFrame frame;
	private GamePanel gamePanel;
	private JLabel title, readyToPlay, pauseLabel, pauseInfo, endInfo, endLabel, score;
	private FancyTimer timer1, timer2, timer3;
	private PropertyChangeSupport pcs;
	
	public static int screenWidth, screenHeight;
	public static final int paddleSpeed = 3;
	public static final int ballSpeed = 5;
	public static final int frameRate = 1;

	public Gui() {
		screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		SwingUtilities.invokeLater(this::initGui);
	}
	
	public void initGui() {
		timer1 = new FancyTimer(frameRate, (e) -> {  //potentially lower the delay to make sure the fast movement doesn't happen
//			gamePanel.getBall().move(5);
			if (timer1.getId().equals("UP") && gamePanel.getRightPaddle().isCanMove() && gamePanel.getRightPaddle().getTransformedY() > gamePanel.getOffsetBorderThickness(5)) {
				gamePanel.getRightPaddle().move(paddleSpeed);
//				gamePanel.repaint();
			} else if (timer1.getId().equals("DOWN") && gamePanel.getRightPaddle().isCanMove() && gamePanel.getRightPaddle().getTransformedY() < gamePanel.getHeight() - gamePanel.getOffsetBorderThickness(5) - gamePanel.getRightPaddle().getHeight()) {
				gamePanel.getRightPaddle().move(-paddleSpeed);
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
		
		timer2 = new FancyTimer(frameRate, (e) -> {
			if (timer2.getId().equals("W") && gamePanel.getLeftPaddle().isCanMove() && gamePanel.getLeftPaddle().getTransformedY() > gamePanel.getOffsetBorderThickness(5)) {
				gamePanel.getLeftPaddle().move(paddleSpeed);
			} else if (timer2.getId().equals("S") && gamePanel.getLeftPaddle().isCanMove() && gamePanel.getLeftPaddle().getTransformedY() < gamePanel.getHeight() - gamePanel.getOffsetBorderThickness(5) - gamePanel.getLeftPaddle().getHeight()) {
				gamePanel.getLeftPaddle().move(-paddleSpeed);
//				gamePanel.repaint();
			}
			gamePanel.repaint();
		});
		
		timer3 = new FancyTimer(frameRate, (e) -> {
			gamePanel.getBall().move(ballSpeed);
//			System.out.println("Ball bottom: " + (gamePanel.getBall().getTransformedY() + 2 * gamePanel.getBall().getRadius()));
			gamePanel.repaint();
		});
		
//		timer3 = new FancyTimer(5, (e) -> {
//			if (timer3.getId().equals("START") && gamePanel.getBall().isAbleToMove() && gamePanel.getLeftPaddle().getTransformedY() > gamePanel.getOffsetBorderThickness(5)) {
//				gamePanel.getBall().move(1);  // starts to the right by default
//			} else if (timer2.getId().equals("S") && gamePanel.getLeftPaddle().isCanMove() && gamePanel.getLeftPaddle().getTransformedY() < gamePanel.getHeight() - gamePanel.getOffsetBorderThickness(5) - gamePanel.getLeftPaddle().getHeight()) {
//				gamePanel.getLeftPaddle().move(-1);
////				gamePanel.repaint();
//			}
//			gamePanel.repaint();
//		});

		// FOR THE BALL, WE SHOULD DO THE CHECKING BEFORE GamePanel.paintComponent() SO IT RUNS EVERY TIME repaint() is called externally
//		timer3 = new FancyTimer(5, (e) -> {
//			if (timer3.getId().equals("RIGHT")) {
//				if (gamePanel.getBall().getTransformedX() < gamePanel.getWidth() - gamePanel.getOffsetBorderThickness(5) - gamePanel.getRightPaddle().getWidth() || gamePanel.getBall().getTransformedX() < gamePanel.getWidth() - gamePanel.getOffsetBorderThickness(5)) {
//					gamePanel.getBall().move(1);
//				} else {
//					timer3.setId("LEFT");
//				}
//			} else if (timer3.getId().equals("LEFT")) {
//				if (gamePanel.getBall().getTransformedX() > gamePanel.getOffsetBorderThickness(5) + gamePanel.getLeftPaddle().getWidth() || gamePanel.getBall().getTransformedX() > gamePanel.getOffsetBorderThickness(5)) {
//					gamePanel.getBall().move(-1);
//				} else {
//					timer3.setId("RIGHT");
//				}
//			}
//			gamePanel.repaint();
//		});
		
		frame = new JFrame("Pong");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(screenWidth / 2, screenHeight / 2); 
		frame.setLocationRelativeTo(null);
		
		gamePanel = new GamePanel();
		pcs = gamePanel.getPcs();
		gamePanel.setBackground(Color.black);
		gamePanel.setLayout(new BorderLayout());
		gamePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black, 3), BorderFactory.createLineBorder(Color.white, 7)));
		
		readyToPlay = new JLabel("Press space bar to play");
		readyToPlay.setName("readyToPlay");
		readyToPlay.setForeground(Color.white);
		readyToPlay.setFont(new Font("Monospaced", Font.PLAIN, 18));
		readyToPlay.setHorizontalAlignment(JLabel.CENTER);
		
		title = new JLabel("WELCOME TO PONG!");
		title.setName("title");
		title.setForeground(Color.white);
		title.setFont(new Font("Monospaced", Font.PLAIN, 36));
		title.setHorizontalAlignment(JLabel.CENTER);
		
		pauseLabel = new JLabel("GAME PAUSED");
		pauseLabel.setForeground(Color.white);
		pauseLabel.setFont(new Font("Monospaced", Font.PLAIN, 36));
		pauseLabel.setHorizontalAlignment(JLabel.CENTER);
		
		pauseInfo = new JLabel("Press space bar to pause");
		pauseInfo.setForeground(Color.white);
		pauseInfo.setFont(new Font("Monospaced", Font.PLAIN, 16));
		pauseInfo.setHorizontalAlignment(JLabel.CENTER);
		
		endInfo = new JLabel("Press ENTER to play again.");
		endInfo.setForeground(Color.white);
		endInfo.setFont(new Font("Monospaced", Font.PLAIN, 16));
		endInfo.setHorizontalAlignment(JLabel.CENTER);
		
		endLabel = new JLabel("GAME OVER");
		endLabel.setForeground(Color.white);
		endLabel.setFont(new Font("Monospaced", Font.PLAIN, 36));
		endLabel.setHorizontalAlignment(JLabel.CENTER);
		
		score = new JLabel("0 | 0");
		score.setForeground(Color.WHITE);
		score.setFont(new Font("Monospaced", Font.PLAIN, 30));  //change to 36???
		score.setHorizontalAlignment(JLabel.CENTER);
		
//		gamePanel.add(endLabel, BorderLayout.CENTER);
//		gamePanel.add(endInfo, BorderLayout.NORTH);
//		gamePanel.add(pauseLabel, BorderLayout.CENTER);
//		gamePanel.add(pauseInfo, BorderLayout.NORTH);
		gamePanel.add(title, BorderLayout.CENTER);
		gamePanel.add(readyToPlay, BorderLayout.SOUTH);
//		gamePanel.validate();
		
		frame.add(gamePanel);
//		frame.validate();
		
		title.setVisible(true);
		readyToPlay.setVisible(true);
//		endLabel.setVisible(false);
//		endInfo.setVisible(false);
//		pauseLabel.setVisible(false);
//		pauseInfo.setVisible(false);
		
		setupListeners();
		prepareAndShow();
	}
	
	public void prepareAndShow() {
		frame.setVisible(true);
		gamePanel.initGameComponents(); // after so that gamePanel can correctly calculate its height and width
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
					gamePanel.add(pauseInfo, BorderLayout.NORTH);
					gamePanel.add(pauseLabel, BorderLayout.CENTER);
					gamePanel.add(score, BorderLayout.SOUTH);
					gamePanel.validate();
					gamePanel.setOnStart(false);
	//				gamePanel.add(pauseLabel, BorderLayout.CENTER);
	//				gamePanel.add(pauseInfo, BorderLayout.NORTH);
					
//					title.setVisible(false);
//					readyToPlay.setVisible(false);
					
					pauseLabel.setVisible(false);
					pauseInfo.setVisible(true);
					score.setVisible(true);
					
//					gamePanel.add(endLabel, BorderLayout.CENTER);
//					gamePanel.add(endInfo, BorderLayout.NORTH);
//					endLabel.setVisible(false);
//					endInfo.setVisible(true);
					
					// fire ball timer here
//					gamePanel.getBall().setSlope(0);	
					gamePanel.getBall().setSlope((int) (Math.random() * 100) /100.0);  //rounds to two decimal places
					timer3.start();
//					timer3.setId("RIGHT");
//					timer3.start();
					//
					
					gamePanel.repaint();
					break;
				case "beginPause":
					System.out.println("begin pause");
//					pauseLabel.setVisible(true);
					if (!gamePanel.isGameOver()) {
//						System.out.println("setting pauseLabel to visible");
						pauseLabel.setVisible(true);
					}
					gamePanel.getLeftPaddle().setCanMove(false);
					gamePanel.getRightPaddle().setCanMove(false);

					timer3.stop();  // could probably just use setAbleToMove like above or make all timer.stop()
	//				gamePanel.repaint();
					break;
				case "endPause":
					System.out.println("end pause");
					if (!gamePanel.isGameOver()) {
						pauseLabel.setVisible(false);
					}
					gamePanel.getLeftPaddle().setCanMove(true);
					gamePanel.getRightPaddle().setCanMove(true);
					
					timer3.restart();
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
		
		// START COLLISIONACTION
		
		CollisionAction collision = new CollisionAction(gamePanel);
		collision.addPropertyChangeListener((evt) -> {
			System.out.println("COLLISION DETECTED");
		});
		
		// START AWSD KEYS
		
		int[] wsKeys = {KeyEvent.VK_W, KeyEvent.VK_S};
		BlockingKeyAction[] wsActions = new BlockingKeyAction[2 * wsKeys.length];
		gamePanel.bindActionsToKeys(wsKeys, wsActions, BlockingKeyAction.class);
		
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
		BlockingKeyAction[] arrowActions = new BlockingKeyAction[2 * arrowKeys.length];
		gamePanel.bindActionsToKeys(arrowKeys, arrowActions, BlockingKeyAction.class);
		
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
		
		//Enter listener
		int[] enterKey = {KeyEvent.VK_ENTER};
		BlockingKeyAction[] enterActions = new BlockingKeyAction[2 * enterKey.length];
		gamePanel.bindActionsToKeys(enterKey, enterActions, BlockingKeyAction.class);
		
		enterActions[0].addPropertyChangeListener((evt) -> {  //enter pressed listener
			System.out.println("enter press listener works");
			if (gamePanel.isGameOver()) {
				gamePanel.setGameOver(false);
				gamePanel.getBall().setAbleToMove(true);
				gamePanel.getRightPaddle().setCanMove(true);
				gamePanel.getLeftPaddle().setCanMove(true);
				
				gamePanel.getBall().setX(gamePanel.getBall().getInitialX());
				gamePanel.getBall().setY(gamePanel.getBall().getInitialY());
				gamePanel.getBall().setSlope((int) (Math.random() * 100) /100.0);
				gamePanel.getRightPaddle().setY(gamePanel.getRightPaddle().getInitialY());
				gamePanel.getLeftPaddle().setY(gamePanel.getLeftPaddle().getInitialY());
				
				gamePanel.remove(endInfo);
				gamePanel.remove(endLabel);
				gamePanel.add(pauseInfo);
				gamePanel.add(pauseLabel);
				gamePanel.validate();
				
//				endInfo.setVisible(false);
				pauseInfo.setVisible(true);
				pauseLabel.setVisible(false);
//				endLabel.setVisible(false);
				
				timer1.setGameOver(false);
				timer2.setGameOver(false);
				timer3.setGameOver(false);
//				timer1.restart();
//				timer2.restart();
				timer3.restart();
				gamePanel.repaint();
			}
		});
		
		enterActions[1].addPropertyChangeListener((evt) -> {  //enter released listener
			System.out.println("enter release listener works");
			enterActions[0].setBlocking(false);
		});
		//End enter listener
		
		//WIN/LOSE LISTENER
		pcs.addPropertyChangeListener("Game ended", (e) -> {
			System.out.println("Game end property change registered");
			gamePanel.getBall().setAbleToMove(false);
			gamePanel.getRightPaddle().setCanMove(false);
			gamePanel.getLeftPaddle().setCanMove(false);
			
			timer1.setGameOver(true);
			timer2.setGameOver(true);
			timer3.setGameOver(true);
			timer1.stop();
			timer2.stop();
			timer3.stop();
			
			gamePanel.remove(pauseInfo);
			gamePanel.remove(pauseLabel);
			gamePanel.add(endInfo, BorderLayout.NORTH);
			gamePanel.add(endLabel, BorderLayout.CENTER);
			score.setText("" + gamePanel.getP1Score() + " | " + gamePanel.getP2Score());
			gamePanel.validate();
			endInfo.setVisible(true);
			endLabel.setVisible(true);
			
//			gamePanel.remove(pauseLabel);
//			gamePanel.remove(pauseInfo);
			
//			gamePanel.add(endLabel, BorderLayout.CENTER);
//			gamePanel.add(endInfo, BorderLayout.NORTH);
			
//			endLabel.setVisible(true);
//			endInfo.setVisible(true);
//			gamePanel.setEndInfo(endInfo);
//			gamePanel.setEndLabel(endLabel);
			
//			gamePanel.add(pauseLabel, BorderLayout.CENTER);
//			gamePanel.add(pauseInfo, BorderLayout.NORTH);
//			pauseLabel.setVisible(true);
//			pauseInfo.setVisible(true);
			
//			pauseLabel.setVisible(false);
//			pauseInfo.setVisible(false);
//			
//			endLabel.setVisible(true);
//			endInfo.setVisible(true);
			
//			endLabel.setVisible(true);
//			endInfo.setVisible(true);
			gamePanel.repaint();
		});
		
	}
	
}
