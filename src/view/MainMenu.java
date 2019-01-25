/*
 * package view; import javax.swing.*;
 * 
 * 
 * import java.awt.*; import java.awt.event.ActionEvent; import
 * java.awt.event.ActionListener;
 * 
 * public class MainMenu extends JPanel { private Board board; private JButton
 * button;
 * 
 * 
 * public MainMenu() { this.setSize(1080,800); button=new JButton(); } public
 * void decider() { button.addActionListener(new ActionListener() {
 * 
 * @Override public void actionPerformed(ActionEvent e) { //
 * board.switchView("Start");
 * 
 * } }); button.setText("Start"); button.setText("Start"); button.setFont(new
 * Font("Algerian", Font.BOLD, 20));
 * button.setHorizontalTextPosition(JButton.CENTER);
 * button.setVerticalTextPosition(JButton.CENTER); button.addMouseListener(new
 * java.awt.event.MouseAdapter() { public void
 * mouseEntered(java.awt.event.MouseEvent evt) { button.setFont(new
 * Font("Algerian", Font.BOLD | Font.ITALIC, 25)); }
 * 
 * public void mouseExited(java.awt.event.MouseEvent evt) { button.setFont(new
 * Font("Algerian", Font.BOLD, 20)); } }); add(button); repaint(); revalidate();
 * } public void setGameView(Board board) { this.board = board; decider(); }
 * public Board getBoard() { return board; } }
 */