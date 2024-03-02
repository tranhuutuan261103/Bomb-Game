package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JPanel;

import model.GameManager;

public class GameInfo implements Runnable {
	private JPanel _gameInfoPanel;
	private GameManager _gameManager;
	
	public GameInfo(GameManager gameManager) {
		super();
		this._gameManager = gameManager;
		this._gameInfoPanel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setStroke(new java.awt.BasicStroke(2));
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				Image image = Toolkit.getDefaultToolkit().getImage("src/images/klee_profile.png");
				g2d.drawImage(image, 10, 60, 180, 108, this);
				
				Font customFont = new Font(Font.SANS_SERIF, Font.BOLD, 16);
				g2d.setFont(customFont);
				
				g2d.drawString(_gameManager.getCharacter().getHeart() + "", 140, 88);
				g2d.drawString(_gameManager.getCharacter().getBombs() + "", 140, 110);
				g2d.drawString(_gameManager.getCharacter().getBombImpactLength() + "", 140, 131);
				g2d.drawString(_gameManager.getCharacter().getA() + "", 140, 153);
			}
		};
		
		this._gameInfoPanel.setSize(200, 400);
		this._gameInfoPanel.setPreferredSize(new Dimension(200, 400));
		this._gameInfoPanel.setBackground(new Color(95, 210, 252)); // 245/ 206/ 231
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public JPanel getGameInfoPanel() {
		return _gameInfoPanel;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				_gameInfoPanel.repaint();
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
