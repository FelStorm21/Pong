package me.vmamakers.pong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.beans.PropertyChangeSupport;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

// Potentially add fader to "Press space bar to play" JLabel

public class Gui {
	
	private JFrame frame;
	private JPanel gamePanel;
	private JLabel title, readyToPlay, pauseLabel, pauseInfo;
	private PropertyChangeSupport pcs;
	
	public Gui() {
		SwingUtilities.invokeLater(this::initGui);
	}
	
	public void initGui() {
		frame = new JFrame("Pong");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2); 
		frame.setLocationRelativeTo(null);
		
		gamePanel = new JPanel();
		gamePanel.setBackground(Color.black);
		gamePanel.setLayout(new BorderLayout());
		gamePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black, 2), BorderFactory.createLineBorder(Color.white, 7)));
		
		readyToPlay = new JLabel("Press space bar to play");
		readyToPlay.setForeground(Color.white);
		readyToPlay.setFont(new Font("Monospaced", Font.PLAIN, 18));
		readyToPlay.setHorizontalAlignment(JLabel.CENTER);
		gamePanel.add(readyToPlay, BorderLayout.SOUTH);
		
		title = new JLabel("WELCOME TO PONG!");
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
		pauseInfo.setFont(new Font("Monospaced", Font.PLAIN, 18));
		pauseInfo.setHorizontalAlignment(JLabel.CENTER);
		
		frame.add(gamePanel);
		setupListeners();
		prepareAndShow();
	}
	
	public void prepareAndShow() {
		frame.setVisible(true);
	}
	
	public void setupListeners() {
		SpacebarAction spacePressed = new SpacebarAction();
		pcs = spacePressed.getPcs();
		gamePanel.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space bar pressed");
		gamePanel.getActionMap().put("space bar pressed", spacePressed);
		
		pcs.addPropertyChangeListener("startGame", (evt) -> {
			gamePanel.remove(title);
			gamePanel.remove(readyToPlay);
			gamePanel.add(pauseLabel, BorderLayout.CENTER);
			gamePanel.add(pauseInfo, BorderLayout.NORTH);
			pauseLabel.setVisible(false);
			pauseInfo.setVisible(true);
			gamePanel.repaint();
		});
		
		pcs.addPropertyChangeListener("beginPause", (evt) -> {
			System.out.println("BEGIN PAUSE listener code reached");
			pauseLabel.setVisible(true);
		});
		
		pcs.addPropertyChangeListener("endPause", (evt) -> {
			System.out.println("END PAUSE listener code reached");
			pauseLabel.setVisible(false);
		});
	}
	
}
