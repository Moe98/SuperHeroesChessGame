import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.game.Game;
import model.game.Player;

public class Version3View extends JFrame{

	static Player p1;
	static Player p2;
	static Game game;
	static JPanel panel;
	static JPanel viewPanel;
	static JButton button;
	static JButton [] buttons;
	static Version3View gui;
	public Version3View() {
		/**
		 * Initializing a game object, where the first Player corresponds to p1
		 * object and the second player corresponds to p2 object
		 */
		p1 = new Player("player 1");
		p2 = new Player("player 2");
		game = new Game(p1, p2);
		/**
		 * Assigning a random player to be the current player which the game would start with. 
		 */
		int random = ((int) (2 * Math.random()));
		try {
			Field f = Game.class.getDeclaredField("currentPlayer");
			f.setAccessible(true);
			f.set(game, (random == 0) ? game.getPlayer1() : game.getPlayer2());
		} catch (Exception e) {
			e.printStackTrace();
		}

		/**
		 * Initializing the payLoadPos of the current player to a random number.
		 */
		try {
			Field f = Player.class.getDeclaredField("payloadPos");
			f.setAccessible(true);
			f.set((random == 0) ? game.getPlayer1() : game.getPlayer2(), ((int) (4 * Math.random())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/**
		 * Your solution starts from here
		 */
		buttons=new JButton[3];
		panel=new JPanel();
		panel.setLayout(new GridLayout(3,0));
		viewPanel=new JPanel();
		viewPanel.setLayout(new GridLayout(1,0));
		update(game);
		viewPanel.add(panel);
		this.add(viewPanel);
		this.repaint();
		this.revalidate();
	}
	public static void update(Game game) {
		button=new JButton();
		button.setText(game.getCurrentPlayer().getName());
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setEnabled(false);
		panel.add(button);
		buttons[0]=(button);
		button=new JButton();
		int x=game.getCurrentPlayer().getPayloadPos();
		button.setText(""+x);
		button.setBorderPainted(false);
		button.setEnabled(false);
		button.setFocusPainted(false);
		panel.add(button);
		buttons[1]=(button);
		button=new JButton();
		button.setText("Increment PayLoadPos");
		buttons[2]=(button);
		listener l=new listener();
		buttons[2].addActionListener(l);
		panel.add(button);
		
	}
	public static void clearRefresh() {
		panel.removeAll();
		viewPanel.removeAll();
	}
	public static void updateRefresh() {
		viewPanel.add(panel);
		gui.add(viewPanel);
		gui.repaint();
		gui.revalidate();
	}
	public static class listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==buttons[2]) {
				int x=game.getCurrentPlayer().getPayloadPos();
				if(x!=6) {
					x+=1;
					game.getCurrentPlayer().setPayloadPos(x);
				}				
				clearRefresh();
				update(game);
				updateRefresh();
			}
			
		}
		
	}
	public static void main(String[] args) {
		gui=new Version3View();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setLocation(0, 0);
		gui.setSize(1080, 800);
		gui.setVisible(true);
	}
}
