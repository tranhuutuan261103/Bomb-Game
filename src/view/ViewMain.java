package view;

import java.awt.BorderLayout;
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
	private static int WIDTH = 800;
	private static int HEIGHT = 600;
	//private int offset = 100;
	Character _character;
	GamePlay gamePlayContainer;
	
	BufferedImage img;

	public ViewMain() {
		//_matrixMap = matrixMap;
		
		_view = new JFrame();
		_view.setSize(WIDTH, HEIGHT);
		_view.setTitle("Boom");
		_view.setLocationRelativeTo(null);
		_view.setLayout(new BorderLayout());
		_view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gamePlayContainer = new GamePlay(this);
		_view.add(gamePlayContainer.getGamePlayPanel(), BorderLayout.CENTER);
		_view.addKeyListener(gamePlayContainer);
		
		Image image = Toolkit.getDefaultToolkit().getImage("src/images/klee.jpeg");
		_view.setIconImage(image);
	}
	
	public JFrame getView() {
		return _view;
	}
//	
//	public void boomBoomBoom(Boom boom) {
//		Iterator<Boom> iterator = booms.iterator();
//	    while (iterator.hasNext()) {
//	        Boom item = iterator.next();
//	        if (item == boom) {
//	            if (item.getTimeCountDown() == 0) {
//	                iterator.remove();
//	                handleBoomEffect(boom);
//	            }
//	        }
//	    }
//	}
	
//	public void happyHappyHappy(BoomEffect boom) {
//		for (BoomEffect item : boomEffects) {
//			if (item == boom) {
//				boomEffects.remove(boom);
//			}
//		}
//	}
//	
//	public void handleBoomEffect(Boom boom) {
//		int row = boom.get_row();
//		int col = boom.get_col();
//	    int totalCells = 2;
//	    int lengthEffect = 0;
//	    boolean canUp = true;
//	    boolean canDown = true;
//	    boolean canLeft = true;
//	    boolean canRight = true;
//	    while (totalCells > 0) {
//	    	lengthEffect++;
//	    	if (canDown && row + lengthEffect < 11 && _matrixMap[row + lengthEffect][col] == 1 && totalCells > 0) {
//			    totalCells--;
//			    //_matrixMap[row + lengthEffect][col] = 3;
//			    BoomEffect boomEffect = new BoomEffect(row + lengthEffect, col, 1, null);
//			    Thread thread = new Thread(boomEffect);
//			    thread.start();
//			    boomEffects.add(boomEffect);
//	    	} else {
//	    		canDown = false;
//	    	}
//	    	if (canUp && row - lengthEffect >=0 && _matrixMap[row - lengthEffect][col] == 1 && totalCells > 0) {
//	    		totalCells--;
//	    		//_matrixMap[row - lengthEffect][col] = 3;
//	    		BoomEffect boomEffect = new BoomEffect(row - lengthEffect, col, 1, null);
//	    		Thread thread = new Thread(boomEffect);
//			    thread.start();
//			    boomEffects.add(boomEffect);
//	    	} else {
//	    		canUp = false;
//	    	}
//	    	if (canRight && col + lengthEffect < 11 && _matrixMap[row][col + lengthEffect] == 1 && totalCells > 0) {
//	    		totalCells--;
//	    		//_matrixMap[row][col + lengthEffect] = 3;
//	    		BoomEffect boomEffect = new BoomEffect(row, col + lengthEffect, 1, null);
//	    		Thread thread = new Thread(boomEffect);
//			    thread.start();
//			    boomEffects.add(boomEffect);
//	    	} else {
//	    		canRight = false;
//	    	}
//	    	if (canLeft && col - lengthEffect >=0 && _matrixMap[row][col - lengthEffect] == 1 && totalCells > 0) {
//	    		totalCells--;
//	    		//_matrixMap[row][col - lengthEffect] = 3;
//	    		BoomEffect boomEffect = new BoomEffect(row, col - lengthEffect, 1, null);
//	    		Thread thread = new Thread(boomEffect);
//			    thread.start();
//			    boomEffects.add(boomEffect);
//	    	} else {
//	    		canLeft = false;
//	    	}
//		}
//	};
}
