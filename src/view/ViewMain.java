package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import model.Character;
import model.GameManager;

public class ViewMain {
	
	/**
	 * 
	 */
	private JFrame _view;
	private static int widthHeader = 14;
	private static int heightHeader = 37;
	//private int offset = 100;
	Character _character;
	GameManager _gameManager;
	GamePlay gamePlayContainer;
	GameInfo gameInfoContainer;
	
	BufferedImage img;

	public ViewMain() {
		Image image = Toolkit.getDefaultToolkit().getImage("src/images/klee.jpeg");
		_view = new JFrame();
		_view.setTitle("Boom");
		_view.setIconImage(image);
		_view.setLayout(new BorderLayout());
		_view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_view.setResizable(false);
		
		_gameManager = new GameManager();
		
		gamePlayContainer = new GamePlay(_gameManager);
		_view.addKeyListener(gamePlayContainer);
		_view.add(gamePlayContainer.getGamePlayPanel(), BorderLayout.CENTER);
		
		gameInfoContainer = new GameInfo(this);
		_view.add(gameInfoContainer.getGameInfoPanel(), BorderLayout.EAST);
		
		Dimension gamePlayContainerSize = gamePlayContainer.getGamePlayPanel().getSize();
		Dimension gameInfoContainerSize = gameInfoContainer.getGameInfoPanel().getSize();
		_view.setSize(
				widthHeader + (int)gamePlayContainerSize.getWidth() + (int)gameInfoContainerSize.getWidth(), 
				heightHeader + (int)gamePlayContainerSize.getHeight()
			);
		_view.setLocationRelativeTo(null);
	}
	
	public JFrame getView() {
		return _view;
	}
	
	public void resetGame() {
		_gameManager = new GameManager();
	}
	
	public GameManager getGameManager() {
		return _gameManager;
	}
}
