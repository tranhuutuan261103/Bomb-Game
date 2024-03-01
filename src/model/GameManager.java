package model;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameManager {
	private Character _character;
	private Map _map;
	private ArrayList<Boom> _booms;
	private ArrayList<BoomEffect> _boomEffects;
	
	public GameManager() {
		_map = new Map("src/db/map.txt");
		_character = new Character(0, 0, this);
		_booms = new ArrayList<>();
		_boomEffects = new ArrayList<>();
	}
	
	public Character getCharacter() {
		return _character;
	}
	
	public Map getMap() {
		return _map;
	}

	public void renderCharacter(Graphics2D g2d) {
		_character.renderUI(g2d);
	}
	
	public void renderMap(Graphics2D g2d) {
		_map.renderUI(g2d);
	}
	
	public void renderBooms(Graphics2D g2d) {
		for (Boom boom : _booms) {
			boom.renderUI(g2d);
		}
	}
	
	public void renderBoomEffects(Graphics2D g2d) {
		for (BoomEffect boomEffect : _boomEffects) {
			boomEffect.renderUI(g2d);
		}
	}

	public void addBoom(int x, int y) {
		int row = (int)(x + 20) / 40;
		int col = (int)(y + 20) / 40;
		if (_map.getMatrixMap()[row][col].isCanEnter() == true){
			Boom boom = new Boom(row, col, 3, this);
			_booms.add(boom);
		}
	}

	public void boomBoomBoom(Boom boom) {
		BoomEffect boomEffect = new BoomEffect(boom.get_row(), boom.get_col(), 1, this);
		_boomEffects.add(boomEffect);
		handleBoomEffect(boom);
	}
	
	public void handleBoomEffect(Boom boom) {
		Area[][] _matrixMap = _map.getMatrixMap();
		int row = boom.get_row();
		int col = boom.get_col();
	    int totalCells = 4 * _character.getBombImpactLength();
	    int lengthEffect = 0;
	    boolean canUp = true;
	    boolean canDown = true;
	    boolean canLeft = true;
	    boolean canRight = true;
	    while (totalCells > 0) {
	    	lengthEffect++;
	    	if (canDown && row + lengthEffect < 11 && _matrixMap[row + lengthEffect][col].isCanEnter() == true && totalCells > 0) {
			    totalCells--;
			    BoomEffect boomEffect = new BoomEffect(row + lengthEffect, col, 1, this);
			    _boomEffects.add(boomEffect);
	    	} else {
	    		canDown = false;
	    	}
	    	if (canUp && row - lengthEffect >=0 && _matrixMap[row - lengthEffect][col].isCanEnter() == true && totalCells > 0) {
	    		totalCells--;
	    		BoomEffect boomEffect = new BoomEffect(row - lengthEffect, col, 1, this);
			    _boomEffects.add(boomEffect);
	    	} else {
	    		canUp = false;
	    	}
	    	if (canRight && col + lengthEffect < 11 && _matrixMap[row][col + lengthEffect].isCanEnter() == true && totalCells > 0) {
	    		totalCells--;
	    		BoomEffect boomEffect = new BoomEffect(row, col + lengthEffect, 1, this);
			    _boomEffects.add(boomEffect);
	    	} else {
	    		canRight = false;
	    	}
	    	if (canLeft && col - lengthEffect >=0 && _matrixMap[row][col - lengthEffect].isCanEnter() == true && totalCells > 0) {
	    		totalCells--;
	    		BoomEffect boomEffect = new BoomEffect(row, col - lengthEffect, 1, this);
			    _boomEffects.add(boomEffect);
	    	} else {
	    		canLeft = false;
	    	}
		}
	};

	public synchronized void removeBoom(Boom boom) {
		_booms.remove(boom);
	}

	public synchronized void removeBoomEffect(BoomEffect boomEffect) {
	    _boomEffects.remove(boomEffect);
	}
}
