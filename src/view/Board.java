package view;

import java.awt.*;
import java.applet.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import model.game.*;
import model.pieces.Piece;
import model.pieces.heroes.*;
import model.pieces.sidekicks.SideKick;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import exceptions.InvalidPowerUseException;
import exceptions.OccupiedCellException;
import exceptions.UnallowedMovementException;
import exceptions.WrongTurnException;

public class Board extends JFrame {
	private static Board gui;
	private static JButton button;
	private static JPanel panel;
	private static JPanel boardPanel;
	private static JButton pieces[][];
	private static JPanel deadCharacters;
	private static JPanel deadPanel;
	private static JPanel controls;
	private static JPanel controlsPanel;
	private static JButton controlsButton;
	private static JButton[][] cButtons;
	private static JButton deadButtons;
	private static JButton[] deadList;
	private static JPanel payloadPosP1;
	private static JPanel payloadPanelP1;
	private static JPanel cPlayer;
	private static JButton currentPlayer;
	private static JButton payloadP1;
	private static ArrayList<JButton> payload1;
	private static JPanel payloadPosP2;
	private static JPanel payloadPanelP2;
	private static JButton payloadP2;
	private static ArrayList<JButton> payload2;
	private static Piece clickedPiece;
	private static Game game;
	private static Piece deadPiece;
	private static Piece tech;
	public static boolean flag = true;
	public static ArrayList<Piece> pressedButtons = new ArrayList<>();
	private static int posI;
	private static int posJ;
	private static BufferedImage image;

	public Board() {
		pieces = new JButton[7][6];
		deadList = new JButton[9];
		payload1 = new ArrayList<JButton>();
		payload2 = new ArrayList<JButton>();
		panel = new JPanel();
		cButtons = new JButton[3][3];
		controls = new JPanel();
		controlsPanel = new JPanel();
		controls.setLayout(new GridLayout(3, 3));
		controlsPanel.setLayout(new GridLayout(0, 1));
		panel.setLayout(new GridLayout(0, 6));
		panel.setBackground(Color.ORANGE);
		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(0, 1));
		payloadPosP1 = new JPanel();
		// try {
		// image=ImageIO.read(new File("C:\\Users\\Mohamed A. Abu
		// Atala\\Desktop\\MET\\ladder.jpg"));
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// JLabel picLabel=new JLabel(new ImageIcon(image));
		// picLabel.setSize(150, 200);
		payloadPosP1.setLayout(new GridLayout(6, 1));
		payloadPanelP1 = new JPanel();
		payloadPanelP1.setLayout(new GridLayout(0, 1));
		// payloadPanelP1.add(picLabel);
		payloadPosP2 = new JPanel();
		payloadPosP2.setLayout(new GridLayout(6, 1));
		payloadPanelP2 = new JPanel();
		payloadPanelP2.setLayout(new GridLayout(0, 1));
		deadCharacters = new JPanel();
		deadCharacters.setLayout(new GridLayout(0, 9));
		deadPanel = new JPanel();
		deadPanel.setLayout(new GridLayout(0, 1));
		String p1 = JOptionPane.showInputDialog("Player 1:");
		String p2 = JOptionPane.showInputDialog("Player 2:");
		Player player1 = new Player(p1);
		Player player2 = new Player(p2);
		game = new Game(player1, player2);
		cPlayer = new JPanel();
		update(game);
		this.add(cPlayer, BorderLayout.NORTH);
		boardPanel.add(panel);
		boardPanel.setPreferredSize(new Dimension(700, 800));
		payloadPanelP1.add(payloadPosP1);
		payloadPanelP1.setPreferredSize(new Dimension(50, 100));
		payloadPanelP2.add(payloadPosP2);
		payloadPanelP2.setPreferredSize(new Dimension(50, 100));
		deadPanel.add(deadCharacters);
		deadPanel.add(controls);
		deadPanel.setPreferredSize(new Dimension(this.getWidth(), 150));
		controlsPanel.add(controls);
		controlsPanel.setPreferredSize(new Dimension(100, 100));
		this.setResizable(false);
		this.add(boardPanel, BorderLayout.CENTER);
		this.add(payloadPanelP1, BorderLayout.WEST);
		this.add(payloadPanelP2, BorderLayout.EAST);
		this.add(deadPanel, BorderLayout.SOUTH);
		this.repaint();
		this.revalidate();

	}

	public static void update(Game game) {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				button = new JButton();
				button.setBorderPainted(false);
				button.setFocusPainted(false);
				if ((i + j) % 2 == 0) {
					Color customColor = new Color(182, 225, 226);
					button.setBackground(customColor);
				} else {
					Color customColor = new Color(249, 249, 249);
					button.setBackground(customColor);
				}
				// button.setIcon(new ImageIcon("C:\\Users\\Mohamed A. Abu
				// Atala\\Desktop\\giphy.gif"));
				Piece piece = game.getCellAt(i, j).getPiece();
				if (piece instanceof Armored) {
					if (((Armored) piece).isArmorUp()) {
						ImageIcon img = new ImageIcon();
						if (game.getCellAt(i, j).getPiece().getOwner() == game.getPlayer1())
							img = new ImageIcon(
									"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\Armored\\with armor\\herowitha back view.png");
						else
							img = new ImageIcon(
									"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\Armored\\with armor\\herowitha.png");
						Image image = img.getImage();
						Image newImage = image.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
						// button.setText("Armored");
						ImageIcon x = new ImageIcon(newImage);
						button.setIcon(x);
						// button.setIcon(new ImageIcon("C:\\Users\\Mohamed A. Abu
						// Atala\\Desktop\\MET\\Final Characters MET\\Armored\\with
						// armor\\herowitha.png"));
						button.setToolTipText("<html>" + "<font color=aa0aea>" + "Owner: " + piece.getOwner().getName()
								+ "<br>" + "Armor up" + "<html>");
					} else {
						ImageIcon img = new ImageIcon();
						if (game.getCellAt(i, j).getPiece().getOwner() == game.getPlayer1())
							img = new ImageIcon(
									"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\Armored\\without armor\\herowitha back view.png");
						else
							img = new ImageIcon(
									"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\Armored\\without armor\\herowitha.png");
						Image image = img.getImage();
						Image newImage = image.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
						ImageIcon x = new ImageIcon(newImage);
						button.setIcon(x);
						// button.setText("Armor Down");
						// "<html>"+"Owner: " + piece.getOwner().getName() +"<br>" +"Armor
						// down"+"<html>"
						button.setToolTipText("<html>" + "<font color=aa0aea>" + "Owner: " + piece.getOwner().getName()
								+ "<br>" + "Armor down" + "<html>");
					}

				}
				if (piece instanceof Tech) {
					ImageIcon img = new ImageIcon(
							"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\Tech\\tech.png");
					Image image = img.getImage();
					Image newImage = image.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
					ImageIcon x = new ImageIcon(newImage);
					button.setIcon(x);
					String s = "";
					if (((Tech) piece).isPowerUsed())
						s = "Used";
					else
						s = "Not used";
					button.setToolTipText("<html>" + "<font color=aa0aea>" + "Owner: " + piece.getOwner().getName()
							+ "<br>" + "Power Status: " + s + "<html>");
					// button.setText("Tech");
				}
				if (piece instanceof Speedster) {
					ImageIcon img = new ImageIcon();
					if (game.getCellAt(i, j).getPiece().getOwner() == game.getPlayer1())
						img = new ImageIcon(
								"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\speedster\\back view.png");
					else
						img = new ImageIcon(
								"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\speedster\\front view.png");
					Image image = img.getImage();
					Image newImage = image.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
					ImageIcon x = new ImageIcon(newImage);
					button.setIcon(x);
					button.setToolTipText(
							"<html>" + "<font color=aa0aea>" + "Owner: " + piece.getOwner().getName() + "<html>");
					// button.setText("Speedster");
				}
				if (piece instanceof Super) {
					ImageIcon img = new ImageIcon();
					if (game.getCellAt(i, j).getPiece().getOwner() == game.getPlayer1())
						img = new ImageIcon(
								"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\super\\back view.png");
					else
						img = new ImageIcon(
								"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\super\\front view.png");
					Image image = img.getImage();
					Image newImage = image.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
					ImageIcon x = new ImageIcon(newImage);
					button.setIcon(x);
					String s = "";
					if (((Super) piece).isPowerUsed())
						s = "Used";
					else
						s = "Not used";
					button.setToolTipText("<html>" + "<font color=aa0aea>" + "Owner: " + piece.getOwner().getName()
							+ "<br>" + "Power Status: " + s + "<html>");
					// button.setText("Super");
				}
				if (piece instanceof Ranged) {
					ImageIcon img = new ImageIcon();
					if (game.getCellAt(i, j).getPiece().getOwner() == game.getPlayer1())
						img = new ImageIcon(
								"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\Ranged\\back view.png");
					else
						img = new ImageIcon(
								"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\Ranged\\front view.png");
					Image image = img.getImage();
					Image newImage = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
					ImageIcon x = new ImageIcon(newImage);
					button.setIcon(x);
					// ImageIcon x=new
					// ImageIcon(gui.getClass().getResource("/images/rangedfornow.png"));
					String s = "";
					if (((Ranged) piece).isPowerUsed())
						s = "Used";
					else
						s = "Not used";
					button.setToolTipText("<html>" + "<font color=aa0aea>" + "Owner: " + piece.getOwner().getName()
							+ "<br>" + "Power Status: " + s + "<html>");
					// button.setText("Ranged");
					// button.setIcon(x);

				}
				if (piece instanceof Medic) {
					ImageIcon img = new ImageIcon();
					if (game.getCellAt(i, j).getPiece().getOwner() == game.getPlayer1())
						img = new ImageIcon(
								"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\Medic\\back view.png");
					else
						img = new ImageIcon(
								"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\Medic\\front view.png");
					Image image = img.getImage();
					Image newImage = image.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
					ImageIcon x = new ImageIcon(newImage);
					button.setIcon(x);
					String s = "";
					if (((Medic) piece).isPowerUsed())
						s = "Used";
					else
						s = "Not used";
					button.setToolTipText("<html>" + "<font color=aa0aea>" + "Owner: " + piece.getOwner().getName()
							+ "<br>" + "Power Status: " + s + "<html>");
					// button.setText("Medic");
				}
				if (piece instanceof SideKick) {
					ImageIcon img = new ImageIcon();
					if (game.getCellAt(i, j).getPiece().getOwner() == game.getPlayer1())
						img = new ImageIcon(
								"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\sidekick\\back view.png");
					else
						img = new ImageIcon(
								"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\sidekick\\front view.png");
					Image image = img.getImage();
					Image newImage = image.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
					ImageIcon x = new ImageIcon(newImage);
					button.setIcon(x);
					button.setToolTipText(
							"<html>" + "<font color=aa0aea>" + "Owner: " + piece.getOwner().getName() + "<html>");
					// button.setText("SK");
				}
				// button.setContentAreaFilled(false);
				panel.add(button);
				pieces[i][j] = button;
				pieceListener pl = new pieceListener();
				keyListener kl = new keyListener();
				techListener tl = new techListener();
				pieces[i][j].addActionListener(pl);
				pieces[i][j].addKeyListener(kl);
				pieces[i][j].addActionListener(tl);
			}
		}
		int x = game.getPlayer1().getPayloadPos();
		int y = game.getPlayer2().getPayloadPos();
		// if(y==game.getPayloadPosTarget()) {
		// JFrame menu=new JFrame();
		// menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// menu.setSize(1080, 800);
		// menu.setLocation(0, 0);
		// menu.setResizable(false);
		// JPanel menuPanel=new JPanel();
		// JButton menuButton=new JButton();
		// menuPanel.add(menuButton);
		// menu.add(menuPanel);
		// menu.setVisible(true);
		// ImageIcon menuImg=new ImageIcon();
		// menuImg=new ImageIcon("C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final
		// Pages_Screens MET\\Untitled-10.png");
		// Image menuImage=menuImg.getImage();
		// Image newMenu=menuImage.getScaledInstance(1080, 800,
		// java.awt.Image.SCALE_SMOOTH);
		// ImageIcon imageformenu=new ImageIcon(newMenu);
		// menuButton.setIcon(imageformenu);
		// menu.repaint();
		// menu.revalidate();
		// try {
		// Thread.sleep(2000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// menu.setVisible(false);
		// }
		 if (x == game.getPayloadPosTarget()) {
		 JOptionPane.showMessageDialog(null, "Congrats " + game.getPlayer1().getName()
		 + " you win!!!", "Game Over!",
		 JOptionPane.INFORMATION_MESSAGE);
		 System.exit(2000);
		 }
		 if (y == game.getPayloadPosTarget()) {
		 JOptionPane.showMessageDialog(null, "Congrats " + game.getPlayer2().getName()
		 + " you win!!!", "Game Over!",
		 JOptionPane.INFORMATION_MESSAGE);
		 System.exit(2000);
		 }
		for (int i = 0; i < 6; i++) {
			if (x > 0) {
				x -= 1;
				payloadP1 = new JButton();
				payloadP1.setToolTipText(game.getPlayer1().getName());
				payloadP1.setBorderPainted(false);
				payloadP1.setFocusPainted(false);
				payloadP1.setBackground(Color.GREEN);
				payloadP1.setForeground(Color.GREEN);
				payloadP1.setEnabled(false);
				payload1.add(payloadP1);
				payloadPosP1.add(payloadP1);
			} else {
				payloadP1 = new JButton();
				payloadP1.setToolTipText(game.getPlayer1().getName());
				payloadP1.setBorderPainted(false);
				payloadP1.setFocusPainted(false);
				payloadP1.setBackground(Color.RED);
				payloadP1.setForeground(Color.RED);
				payloadP1.setEnabled(false);
				payload1.add(payloadP1);
				payloadPosP1.add(payloadP1);
			}
		}
		for (int i = 0; i < 6; i++) {
			if (y > 0) {
				y -= 1;
				payloadP2 = new JButton();
				payloadP2.setToolTipText(game.getPlayer2().getName());
				payloadP2.setBorderPainted(false);
				payloadP2.setFocusPainted(false);
				payloadP2.setBackground(Color.GREEN);
				payloadP2.setForeground(Color.GREEN);
				payloadP2.setEnabled(false);
				payload2.add(payloadP2);
				payloadPosP2.add(payloadP2);
			} else {
				payloadP2 = new JButton();
				payloadP2.setToolTipText(game.getPlayer2().getName());
				payloadP2.setBorderPainted(false);
				payloadP2.setFocusPainted(false);
				payloadP2.setBackground(Color.RED);
				payloadP2.setForeground(Color.RED);
				payloadP2.setEnabled(false);
				payload2.add(payloadP2);
				payloadPosP2.add(payloadP2);
			}
		}
		for (int i = 0; i < game.getCurrentPlayer().getDeadCharacters().size(); i++) {

			deadButtons = new JButton();
			deadButtons.setBorderPainted(false);
			deadButtons.setFocusPainted(false);
			if (i % 2 == 0) {
				Color customColor = new Color(182, 225, 226);
				deadButtons.setBackground(customColor);
			} else {
				Color customColor = new Color(249, 249, 249);
				deadButtons.setBackground(customColor);
			}
			Piece piece = game.getCurrentPlayer().getDeadCharacters().get(i);
			if (piece instanceof Armored) {
				ImageIcon img = new ImageIcon(
						"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\Armored\\with armor\\herowitha.png");
				Image image = img.getImage();
				Image newImage = image.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
				ImageIcon imgicon = new ImageIcon(newImage);
				deadButtons.setIcon(imgicon);
			}
			if (piece instanceof Tech) {
				ImageIcon img = new ImageIcon(
						"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\Tech\\tech.png");
				Image image = img.getImage();
				Image newImage = image.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
				ImageIcon imgicon = new ImageIcon(newImage);
				deadButtons.setIcon(imgicon);
			}
			if (piece instanceof Speedster) {
				ImageIcon img = new ImageIcon();
				img = new ImageIcon(
						"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\speedster\\front view.png");
				Image image = img.getImage();
				Image newImage = image.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
				ImageIcon imgicon = new ImageIcon(newImage);
				deadButtons.setIcon(imgicon);
			}
			if (piece instanceof Super) {
				ImageIcon img = new ImageIcon();
				img = new ImageIcon(
						"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\super\\front view.png");
				Image image = img.getImage();
				Image newImage = image.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
				ImageIcon imgicon = new ImageIcon(newImage);
				deadButtons.setIcon(imgicon);
			}
			if (piece instanceof Ranged) {
				ImageIcon img = new ImageIcon();
				img = new ImageIcon(
						"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\Ranged\\front view.png");
				Image image = img.getImage();
				Image newImage = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
				ImageIcon imgicon = new ImageIcon(newImage);
				deadButtons.setIcon(imgicon);
			}
			if (piece instanceof Medic) {
				ImageIcon img = new ImageIcon();
				img = new ImageIcon(
						"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\Medic\\front view.png");
				Image image = img.getImage();
				Image newImage = image.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
				ImageIcon imgicon = new ImageIcon(newImage);
				deadButtons.setIcon(imgicon);
			}
			if (piece instanceof SideKick) {
				ImageIcon img = new ImageIcon();
				img = new ImageIcon(
						"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Characters MET\\sidekick\\front view.png");
				Image image = img.getImage();
				Image newImage = image.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
				ImageIcon imgicon = new ImageIcon(newImage);
				deadButtons.setIcon(imgicon);
			}
			// deadButtons.setBorderPainted(false);
			// deadButtons.setFocusPainted(false);
			// deadButtons.setOpaque(false);
			// deadButtons.setContentAreaFilled(false);
			deadList[i] = deadButtons;
			deadListener dl = new deadListener();
			deadButtons.addActionListener(dl);
			deadCharacters.add(deadButtons);
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				controlsButton = new JButton();
				controlsButton.setBorderPainted(false);
				controlsButton.setFocusPainted(false);
				if (i == 0 && j == 0)
					controlsButton.setText("UPLEFT");
				if (i == 0 && j == 1)
					controlsButton.setText("UP");
				if (i == 0 && j == 2)
					controlsButton.setText("UPRIGHT");
				if (i == 1 && j == 0)
					controlsButton.setText("LEFT");
				if (i == 1 && j == 2)
					controlsButton.setText("RIGHT");
				if (i == 2 && j == 0)
					controlsButton.setText("DOWNLEFT");
				if (i == 2 && j == 1)
					controlsButton.setText("DOWN");
				if (i == 2 && j == 2)
					controlsButton.setText("DOWNRIGHT");
				controls.add(controlsButton);
				controlsListener cl = new controlsListener();
				cButtons[i][j] = controlsButton;
				cButtons[i][j].addActionListener(cl);
			}
		}
		String s = "<html>" + "<font color=aa0aea>" + "Current Player: " + "<html>";
		if (game.getCurrentPlayer() == game.getPlayer1()) {
			s += game.getPlayer1().getName();
			// <font color=aa0aea>
		}
		if (game.getCurrentPlayer() == game.getPlayer2()) {
			s += game.getPlayer2().getName();
		}
		// "<html>" + "<font color=aa0aea>" + "Owner: " + game.getPlayer2().getName() +
		// "<html>"
		currentPlayer = new JButton();
		currentPlayer.setText(s);
		currentPlayer.setEnabled(false);
		currentPlayer.setContentAreaFilled(false);
		cPlayer.add(currentPlayer);
		cPlayer.repaint();
		cPlayer.revalidate();
		panel.repaint();
		panel.revalidate();
		boardPanel.repaint();
		boardPanel.revalidate();
		deadPanel.repaint();
		deadPanel.revalidate();
	}

	public static class keyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent key) {
			try {
				if (key.getKeyCode() == KeyEvent.VK_W) { // UP
					clickedPiece.move(Direction.UP);
				}
				if (key.getKeyCode() == KeyEvent.VK_Q) { // UPLEFT
					clickedPiece.move(Direction.UPLEFT);
				}
				if (key.getKeyCode() == KeyEvent.VK_E) { // UPRIGHT
					clickedPiece.move(Direction.UPRIGHT);
				}
				if (key.getKeyCode() == KeyEvent.VK_A) { // LEFT
					clickedPiece.move(Direction.LEFT);
				}
				if (key.getKeyCode() == KeyEvent.VK_D) { // RIGHT
					clickedPiece.move(Direction.RIGHT);
				}
				if (key.getKeyCode() == KeyEvent.VK_Z) { // DOWNLEFT
					clickedPiece.move(Direction.DOWNLEFT);
				}
				if (key.getKeyCode() == KeyEvent.VK_S) { // DOWN
					clickedPiece.move(Direction.DOWN);
				}
				if (key.getKeyCode() == KeyEvent.VK_C) { // DOWNRIGHT
					clickedPiece.move(Direction.DOWNRIGHT);
				}
				if (key.getKeyCode() == KeyEvent.VK_I) { // POWER UP
					// System.out.println("control works");
					if (clickedPiece instanceof Super || clickedPiece instanceof Ranged)
						((ActivatablePowerHero) clickedPiece).usePower(Direction.UP, null, null);
					if (clickedPiece instanceof Medic)
						((Medic) clickedPiece).usePower(Direction.UP, deadPiece, null);
				}
				if (key.getKeyCode() == KeyEvent.VK_J) { // POWER LEFT
					if (clickedPiece instanceof Super || clickedPiece instanceof Ranged)
						((ActivatablePowerHero) clickedPiece).usePower(Direction.LEFT, null, null);
					if (clickedPiece instanceof Medic)
						((Medic) clickedPiece).usePower(Direction.LEFT, deadPiece, null);
				}
				if (key.getKeyCode() == KeyEvent.VK_K) { // POWER DOWN
					if (clickedPiece instanceof Super || clickedPiece instanceof Ranged)
						((ActivatablePowerHero) clickedPiece).usePower(Direction.DOWN, null, null);
					if (clickedPiece instanceof Medic)
						((Medic) clickedPiece).usePower(Direction.DOWN, deadPiece, null);
				}
				if (key.getKeyCode() == KeyEvent.VK_L) { // POWER RIGHT
					if (clickedPiece instanceof Super || clickedPiece instanceof Ranged)
						((ActivatablePowerHero) clickedPiece).usePower(Direction.RIGHT, null, null);
					if (clickedPiece instanceof Medic)
						((Medic) clickedPiece).usePower(Direction.RIGHT, deadPiece, null);
				}
				if (key.getKeyCode() == KeyEvent.VK_U) { // POWER UPLEFT
					if (clickedPiece instanceof Medic)
						((Medic) clickedPiece).usePower(Direction.UPLEFT, deadPiece, null);
				}
				if (key.getKeyCode() == KeyEvent.VK_O) { // POWER UPRIGHT
					if (clickedPiece instanceof Medic)
						((Medic) clickedPiece).usePower(Direction.UPRIGHT, deadPiece, null);
				}
				if (key.getKeyCode() == KeyEvent.VK_N) { // POWER DOWNLEFT
					if (clickedPiece instanceof Medic)
						((Medic) clickedPiece).usePower(Direction.DOWNLEFT, deadPiece, null);
				}
				if (key.getKeyCode() == KeyEvent.VK_M) { // POWER DOWNRIGHT
					if (clickedPiece instanceof Medic)
						((Medic) clickedPiece).usePower(Direction.DOWNRIGHT, deadPiece, null);
				}
				if (key.getKeyCode() == KeyEvent.VK_H) {
					clickedPiece = pressedButtons.get(pressedButtons.size() - 2);
					if (clickedPiece instanceof Tech)
						((Tech) clickedPiece).usePower(null, pressedButtons.get(pressedButtons.size() - 1), null);
				}
				if (key.getKeyCode() == KeyEvent.VK_T) {
					Piece piece1 = pressedButtons.get(pressedButtons.size() - 2);
					Piece piece2 = pressedButtons.get(pressedButtons.size() - 3);
					Point newPos = new Point(posI, posJ);
					if (piece2 instanceof Tech) {
						((Tech) piece2).usePower(null, piece1, newPos);
					}
				}
				clearRefresh();
				update(game);
				refreshUpdate();
			} catch (UnallowedMovementException | OccupiedCellException | InvalidPowerUseException
					| WrongTurnException error) {
				if (error instanceof UnallowedMovementException) {
					JOptionPane.showMessageDialog(null, "You cannot move in this direction", "Unallowed Movement!",
							JOptionPane.INFORMATION_MESSAGE);
				}
				if (error instanceof OccupiedCellException) {
					JOptionPane.showMessageDialog(null, "The cell you're moving to is occupied by a friendly piece",
							"Occupied Cell!", JOptionPane.INFORMATION_MESSAGE);
				}
				if (error instanceof WrongTurnException) {
					JOptionPane.showMessageDialog(null, "This isn't your turn", "Wrong Turn!",
							JOptionPane.INFORMATION_MESSAGE);
				}
				if (error instanceof InvalidPowerUseException) {
					JOptionPane.showMessageDialog(null, error.getMessage(), "Invalid Power Use!",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception err) {
				JOptionPane.showMessageDialog(null, "Error in movement please select a piece!!!", "Warning!",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {

		}

		@Override
		public void keyTyped(KeyEvent arg0) {

		}

	}

	public static class deadListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < game.getCurrentPlayer().getDeadCharacters().size(); i++) {
				if (e.getSource() == deadList[i])
					deadPiece = game.getCurrentPlayer().getDeadCharacters().get(i);
				// System.out.println(deadPiece);
			}

		}

	}

	public static class techListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 6; j++) {
					if (e.getSource() == pieces[i][j]) {
						if (clickedPiece != game.getCellAt(i, j).getPiece()) {
							tech = game.getCellAt(i, j).getPiece();
							// System.out.println(tech + " tech");
						}
					}
				}
			}
		}
	}

	public static class pieceListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			for (int i = 0; i < 7/* &&flag */; i++) {
				for (int j = 0; j < 6/* &&flag */; j++) {
					if (e.getSource() == pieces[i][j]) {
						clickedPiece = game.getCellAt(i, j).getPiece();
						posI = i;
						posJ = j;
						pressedButtons.add(clickedPiece);
						// System.out.println(clickedPiece + " clickedPiece");
					}
				}
			}
		}
	}

	public static class controlsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String s = e.getActionCommand();
			try {
				switch (s) {
				case "UP":
					clickedPiece.move(Direction.UP);
					break;
				case "DOWN":
					clickedPiece.move(Direction.DOWN);
					break;
				case "RIGHT":
					clickedPiece.move(Direction.RIGHT);
					break;
				case "LEFT":
					clickedPiece.move(Direction.LEFT);
					break;
				case "UPRIGHT":
					clickedPiece.move(Direction.UPRIGHT);
					break;
				case "UPLEFT":
					clickedPiece.move(Direction.UPLEFT);
					break;
				case "DOWNRIGHT":
					clickedPiece.move(Direction.DOWNRIGHT);
					break;
				case "DOWNLEFT":
					clickedPiece.move(Direction.DOWNLEFT);
					break;
				}
				flag = false;
				clearRefresh();
				update(game);
				refreshUpdate();
			} catch (UnallowedMovementException | OccupiedCellException | WrongTurnException error) {
				if (error instanceof UnallowedMovementException) {
					JOptionPane.showMessageDialog(null, error.getMessage(), "Unallowed Movement!",
							JOptionPane.INFORMATION_MESSAGE);
				}
				if (error instanceof OccupiedCellException) {
					JOptionPane.showMessageDialog(null, error.getMessage(), "Occupied Cell!",
							JOptionPane.INFORMATION_MESSAGE);
				}
				if (error instanceof WrongTurnException) {
					JOptionPane.showMessageDialog(null, error.getMessage(), "Wrong Turn!",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception error) {
				JOptionPane.showMessageDialog(null, error.getMessage(), "Warning!", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public static void clearRefresh() {
		cPlayer.removeAll();
		panel.removeAll();
		boardPanel.removeAll();
		payloadPosP1.removeAll();
		payloadPosP2.removeAll();
		payloadPanelP1.removeAll();
		payloadPanelP2.removeAll();
		deadCharacters.removeAll();
		deadPanel.removeAll();
		controls.removeAll();
		controlsPanel.removeAll();
	}

	public static void refreshUpdate() {
		boardPanel.add(panel);
		boardPanel.setPreferredSize(new Dimension(700, 800));
		payloadPanelP1.add(payloadPosP1);
		payloadPanelP1.setPreferredSize(new Dimension(50, 100));
		payloadPanelP2.add(payloadPosP2);
		payloadPanelP2.setPreferredSize(new Dimension(50, 100));
		deadPanel.add(deadCharacters);
		deadPanel.add(controls);
		deadPanel.setPreferredSize(new Dimension(gui.getWidth(), 150));
		controlsPanel.add(controls);
		controlsPanel.setPreferredSize(new Dimension(100, 100));
		gui.add(boardPanel, BorderLayout.CENTER);
		gui.add(payloadPanelP1, BorderLayout.WEST);
		gui.add(payloadPanelP2, BorderLayout.EAST);
		gui.add(deadPanel, BorderLayout.SOUTH);
		gui.add(cPlayer, BorderLayout.NORTH);
		gui.repaint();
		gui.revalidate();
	}

	public static void main(String[] args) {
		JFrame start = new JFrame();
		start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		start.setSize(1080, 800);
		start.setLocation(0, 0);
		start.setResizable(false);
		JPanel startPanel = new JPanel();
		JButton startButton = new JButton();
		// startButton.setContentAreaFilled(false);
		// startButton.setBorderPainted(false);
		// startButton.setEnabled(false);
		// startButton.setOpaque(false);
		// startButton.setFocusPainted(false);
		startPanel.add(startButton);
		start.add(startPanel);
		start.setVisible(true);
		ImageIcon img = new ImageIcon();
		img = new ImageIcon("C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Pages_Screens MET\\Untitled-8.png");
		Image image = img.getImage();
		Image newImage = image.getScaledInstance(1080, 800, java.awt.Image.SCALE_SMOOTH);
		ImageIcon imgicon = new ImageIcon(newImage);
		startButton.setIcon(imgicon);
		start.repaint();
		start.revalidate();
		try {
			Thread.sleep(3500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		start.setVisible(false);
		JFrame menu = new JFrame();
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setSize(1080, 800);
		menu.setLocation(0, 0);
		menu.setResizable(false);
		JPanel menuPanel = new JPanel();
		JButton menuButton = new JButton();
		menuPanel.add(menuButton);
		menu.add(menuPanel);
		menu.setVisible(true);
		ImageIcon menuImg = new ImageIcon();
		menuImg = new ImageIcon(
				"C:\\Users\\Mohamed A. Abu Atala\\Desktop\\MET\\Final Pages_Screens MET\\Untitled-6.png");
		Image menuImage = menuImg.getImage();
		Image newMenu = menuImage.getScaledInstance(1080, 800, java.awt.Image.SCALE_SMOOTH);
		ImageIcon imageformenu = new ImageIcon(newMenu);
		menuButton.setIcon(imageformenu);
		menu.repaint();
		menu.revalidate();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		menu.setVisible(false);
		gui = new Board();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setLocation(0, 0);
		gui.setSize(1080, 800);
		gui.setVisible(true);
		gui.setTitle("Super Hero Chess Board");
		play("res/music/music.wav", 120000);

	}

	public static void play(String path, int delay) {
		for (int i = 0; true; i++) {
			new Thread() {
				@Override
				public void run() {
					try {
						File file = new File(path);
						Clip clip = AudioSystem.getClip();
						clip.open(AudioSystem.getAudioInputStream(file));
						clip.start();
						Thread.sleep(clip.getMicrosecondLength());

					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}.start();
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
