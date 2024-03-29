package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

public class Boom implements Runnable {
	private GameManager _gameManager;
	private CharacterBase _character;
	private int _row;
	private int _col;
	private int _timeCountDown;
	private boolean _isDestroyed = false;
	
	public Boom(int row, int col, int timeCountDown, GameManager gameManager, CharacterBase character) {
		_gameManager = gameManager;
		_character = character;
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
				if (_timeCountDown == 0 || _isDestroyed == true) {
					_isDestroyed = true;
					break;
				} else {
					_timeCountDown-=10;
				}
				Thread.sleep(10);
			}
			_gameManager.makeBombExplode(this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public CharacterBase getCharacter(){
		return _character;
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

	public void set_isDestroyed(boolean isDestroyed) {
		_isDestroyed = isDestroyed;
	}

	public boolean get_isDestroyed() {
		return _isDestroyed;
	}

	public void renderUI(Graphics2D g2d) {
		Image image = Toolkit.getDefaultToolkit().getImage("src/images/bomb.gif");
		g2d.drawImage(image, _row * 40, _col * 40, 40, 40, null);
	}
}
