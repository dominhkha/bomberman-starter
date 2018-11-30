package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Swing Panel hiển thị thông tin th�?i gian, điểm mà ngư�?i chơi đạt được
 */
public class InfoPanel extends JPanel{
	
	private JLabel timeLabel;
	private JLabel pointsLabel;
        private JLabel FlameItem;
	public InfoPanel(Game game) {
		setLayout(new GridLayout());
		
		timeLabel = new JLabel("Time: " + game.getBoard().getTime());
		timeLabel.setForeground(Color.white);
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		
		pointsLabel = new JLabel("Points: " + game.getBoard().getPoints());
		pointsLabel.setForeground(Color.white);
		pointsLabel.setHorizontalAlignment(JLabel.CENTER);
		FlameItem = new JLabel("BombRadius: "+Game.getBombRadius());
                
                FlameItem.setForeground(Color.white);
		FlameItem.setHorizontalAlignment(JLabel.CENTER);
		add(timeLabel);
		add(pointsLabel);
		//add(FlameItem);
		setBackground(Color.black);
		setPreferredSize(new Dimension(0, 40));
                
	}
	
	public void setTime(int t) {
		timeLabel.setText("Time: " + t);
	}

	public void setPoints(int t) {
		pointsLabel.setText("Score: " + t);
	}

   
	
}
