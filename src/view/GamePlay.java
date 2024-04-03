package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import model.Character;
import model.GameManager;

public class GamePlay implements KeyListener, Runnable {
	//private ViewMain _viewMain;
	private JPanel _gamePlayPanel;
	private GameManager _gameManager;
	
	public GamePlay(GameManager gameManager) {
		//_viewMain = viewMain;
		_gameManager = gameManager;
		_gamePlayPanel = new JPanel() {
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
				
				_gameManager.renderMap(g2d);
				_gameManager.renderItems(g2d);
				_gameManager.renderBooms(g2d);
				_gameManager.renderBoomEffects(g2d);
				_gameManager.renderAICharacter(g2d);
				_gameManager.renderCharacter(g2d);
			};
		};
		
		_gamePlayPanel.setSize(_gameManager.getMap().getMatrixMap().length * 40, _gameManager.getMap().getMatrixMap()[0].length * 40);
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public JPanel getGamePlayPanel() {
		return _gamePlayPanel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Character character = _gameManager.getCharacter();
		if (character.getHeart() <= 0) return;
		switch (e.getKeyChar()) {
		case 'w':
			character.setVy(-character.getA());
			break;
		case 'a':
			character.setVx(-character.getA());
			break;
		case 'd':
			character.setVx(character.getA());
			break;
		case 's':
			character.setVy(character.getA());
			break;
		case ' ':
			_gameManager.addBoom(character.getX(), character.getY());
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Character character = _gameManager.getCharacter();
		switch (e.getKeyChar()) {
		case 'w':
		case 's':
			character.setVy(0);
			break;
		case 'a':
		case 'd':
			character.setVx(0);
			break;
		case ' ':
			break;
		default:
			character.setVx(0);
			character.setVy(0);
			break;
		}
	}

	@Override
	public void run() {
		try {
			while(true) {
				_gamePlayPanel.repaint();
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
