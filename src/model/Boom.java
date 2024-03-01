package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

public class Boom implements Runnable {
	private GameManager _gameManager;
	private int _row;
	private int _col;
	private int _timeCountDown;
	
	public Boom(int row, int col, int timeCountDown, GameManager gameManager) {
		_gameManager = gameManager;
		_row = row;
		_col = col;
		_timeCountDown = timeCountDown;
		
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		try {
			while(true) {
				if (_timeCountDown == 0) {
					_gameManager.boomBoomBoom(this);
					break;
				} else {
					_timeCountDown--;
				}
				Thread.sleep(1000);
			}
			_gameManager.removeBoom(this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int get_row() {
		return _row;
	}

	public void set_row(int _row) {
		this._row = _row;
	}

	public int get_col() {
		return _col;
	}

	public void set_col(int _col) {
		this._col = _col;
	}

	public int getTimeCountDown() {
		return _timeCountDown;
	}

	public void renderUI(Graphics2D g2d) {
		Image image = Toolkit.getDefaultToolkit().getImage("src/images/bomb.gif");
		g2d.drawImage(image, _row * 40, _col * 40, 40, 40, null);
	}
}
