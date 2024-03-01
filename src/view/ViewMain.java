package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import model.Character;

public class ViewMain {
	
	/**
	 * 
	 */
	private JFrame _view;
	private static int widthHeader = 14;
	private static int heightHeader = 37;
	//private int offset = 100;
	Character _character;
	GamePlay gamePlayContainer;
	
	BufferedImage img;

	public ViewMain() {
		Image image = Toolkit.getDefaultToolkit().getImage("src/images/klee.jpeg");
		_view = new JFrame();
		_view.setTitle("Boom");
		_view.setIconImage(image);
		_view.setLayout(new BorderLayout());
		_view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gamePlayContainer = new GamePlay(this);
		_view.add(gamePlayContainer.getGamePlayPanel(), BorderLayout.CENTER);
		_view.addKeyListener(gamePlayContainer);
		
		Dimension screen = gamePlayContainer.getGamePlayPanel().getSize();
		_view.setSize((int)screen.getWidth() + widthHeader, (int)screen.getHeight() + heightHeader);
		_view.setLocationRelativeTo(null);
	}
	
	public JFrame getView() {
		return _view;
	}
}
