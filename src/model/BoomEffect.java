package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

public class BoomEffect implements Runnable {
	private GameManager _gameManager;
	private int _row;
	private int _col;
	private double _timeCountDown;
	
	public BoomEffect(int row, int col, double timeCountDown, GameManager gameManager) {
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
					_gameManager.fire(this);
					break;
				} else {
					_timeCountDown-=_timeCountDown;
				}
				Thread.sleep(400);
			}
			
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

	public void renderUI(Graphics2D g2d) {
		Image image = Toolkit.getDefaultToolkit().getImage("src/images/fire.png");
		g2d.drawImage(image, _row * 40, _col * 40, 40, 40, null);
	}
}
