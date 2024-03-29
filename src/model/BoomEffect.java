package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

public class BoomEffect implements Runnable {
	private GameManager _gameManager;
	private int _row;
	private int _col;
	private int _timeCountDown;
	private int _timeCountDownEffect = 400;
	private boolean _isDestroyed = false;
	
	public BoomEffect(int row, int col, int timeCountDown, GameManager gameManager) {
		_gameManager = gameManager;
		_row = row;
		_col = col;
		_timeCountDown = timeCountDown * 1000;
		
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		try {
			while(true) {
				if (_isDestroyed == true) {
					if (_timeCountDownEffect == 0) {
						break;
					} else {
						_timeCountDownEffect-=10;
					}
				} else {
					if (_timeCountDown == 0) {
						_gameManager.makeBoomEffectExplode(this);
						_isDestroyed = true;
					} else {
						_timeCountDown-=10;
					}
				}
				Thread.sleep(10);
			}
			_gameManager.removeBoomEffect(this);
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

	public void set_isDestroyed(boolean isDestroyed) {
		_isDestroyed = isDestroyed;
	}

	public boolean get_isDestroyed() {
		return _isDestroyed;
	}

	public void renderUI(Graphics2D g2d) {
		Image image = Toolkit.getDefaultToolkit().getImage("src/images/fire.png");
		if (_isDestroyed == true){
			g2d.drawImage(image, _row * 40, _col * 40, 40, 40, null);
		}
	}
}
