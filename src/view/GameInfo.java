package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.CharacterBase;
import model.GameManager;

public class GameInfo implements Runnable {
	private JPanel _gameInfoPanel;
	private ViewMain _viewMain;
	
	public GameInfo(ViewMain viewMain) {
		super();
		this._viewMain = viewMain;
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
				
//				drawCharacterInfo(g2d);
//				Image matchImg = Toolkit.getDefaultToolkit().getImage("src/images/match.png");
//				g2d.drawImage(matchImg, 72, 175, 50, 50,this);
//				drawAICharacterInfo(g2d);
			}
		};

		JPanel panel = new JPanel();
		panel.setBackground(Color.white); // 245/ 206/ 231
		panel.setLayout(new GridLayout(4, 1, 0, 20));
		panel.add(_viewMain.getGameManager().getCharacter().getProfile());
		for (CharacterBase aiChar : _viewMain.getGameManager().getAICharacters()) {
			panel.add(aiChar.getProfile());
		}
		
		this._gameInfoPanel.setSize(200, 400);
		this._gameInfoPanel.setPreferredSize(new Dimension(200, 400));
		//this._gameInfoPanel.setBackground(new Color(95, 210, 252)); // 245/ 206/ 231
		
		this._gameInfoPanel.setLayout(new BorderLayout());
		this._gameInfoPanel.add(panel, BorderLayout.CENTER);
		Button playAgainBtn = new Button("Play again!");
		playAgainBtn.setFocusable(false);
		playAgainBtn.setPreferredSize(new Dimension(100, 50));
		playAgainBtn.setBackground(new Color(253, 132, 16));
		playAgainBtn.setForeground(Color.WHITE);
		playAgainBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		playAgainBtn.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				String[] buttons = { "Yes", "No" };

			    int rc = JOptionPane.showOptionDialog(null, "Are you sure play again ?", "Confirmation",
			        JOptionPane.WARNING_MESSAGE, 1, null, buttons, buttons[1]);

			    if (rc == 0) {
			    	JOptionPane.showMessageDialog(null, "Error... Let close app and reopen to play again");
			    	System.exit(0);
			    }
			}
		});
		this._gameInfoPanel.add(playAgainBtn, BorderLayout.SOUTH);
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public JPanel getGameInfoPanel() {
		return _gameInfoPanel;
	}
	
//	private void drawCharacterInfo(Graphics2D g2d) {
//		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		
//		Image image = Toolkit.getDefaultToolkit().getImage("src/images/klee_profile.png");
//		g2d.drawImage(image, 10, 60, 180, 108, null);
//		
//		Font customFont = new Font(Font.SANS_SERIF, Font.BOLD, 16);
//		g2d.setFont(customFont);
//		
//		g2d.drawString(_gameManager.getCharacter().getHeart() + "", 140, 88);
//		g2d.drawString(_gameManager.getCharacter().getBombs() + "", 140, 110);
//		g2d.drawString(_gameManager.getCharacter().getBombImpactLength() + "", 140, 131);
//		g2d.drawString(_gameManager.getCharacter().getA() + "", 140, 153);
//	}
//	
//	private void drawAICharacterInfo(Graphics2D g2d) {
//		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		
//		Image image = Toolkit.getDefaultToolkit().getImage("src/images/slime_profile.png");
//		int margin = 70;
//		g2d.drawImage(image, 10, margin + 162, 10 + 180, margin + 162 + 108, 3, 10, 620, 390, null);
//		
//		Font customFont = new Font(Font.SANS_SERIF, Font.BOLD, 16);
//		g2d.setFont(customFont);
//		
//		g2d.drawString(_gameManager.getAICharacters().get(0).getHeart() + "", 140, margin + 188);
//		g2d.drawString(_gameManager.getAICharacters().get(0).getBombs() + "", 140, margin + 210);
//		g2d.drawString(_gameManager.getAICharacters().get(0).getBombImpactLength() + "", 140, margin + 231);
//		g2d.drawString(_gameManager.getAICharacters().get(0).getA() + "", 140, margin + 253);
//	}
	
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
