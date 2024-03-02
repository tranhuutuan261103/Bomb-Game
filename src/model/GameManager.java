package model;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import model.item.Item;
import model.item.ItemType;

public class GameManager {
	private Character _character;
	private Map _map;
	private ArrayList<Boom> _booms;
	private ArrayList<BoomEffect> _boomEffects;
	private ArrayList<Item> _items;
	
	public GameManager() {
		_map = new Map("src/db/map.txt");
		_character = new Character(0, 0, this);
		_booms = new ArrayList<>();
		_boomEffects = new ArrayList<>();
		_items = new ArrayList<>();
	}
	
	public Character getCharacter() {
		return _character;
	}
	
	public Map getMap() {
		return _map;
	}
	
	public ArrayList<Item> getItems() {
		return _items;
	}
	
	public ArrayList<BoomEffect> getBoomEffects() {
		return _boomEffects;
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

	public void renderItems(Graphics2D g2d) {
		for (Item item : _items) {
			item.renderUI(g2d);
		}
	}

	public void addBoom(int x, int y) {
		int row = (int)(x + 20) / 40;
		int col = (int)(y + 20) / 40;
		if (_map.getMatrixMap()[row][col].canEnter() == true && _character.getBombs() > 0){
			Boom boom = new Boom(row, col, 3, this);
			this._character.setBombs(_character.getBombs() - 1);
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
	    int lengthEffect = 0;
	    boolean canUp = true;
	    boolean canDown = true;
	    boolean canLeft = true;
	    boolean canRight = true;
	    while (lengthEffect < _character.getBombImpactLength()) {
	    	lengthEffect++;
	    	if (canRight && row + lengthEffect < 11 && _matrixMap[row + lengthEffect][col].canEnter() == true) {
			    BoomEffect boomEffect = new BoomEffect(row + lengthEffect, col, 1, this);
			    _boomEffects.add(boomEffect);
	    	} else if (canRight && row + lengthEffect < 11 
	    			&& _matrixMap[row + lengthEffect][col].canEnter() == false
	    			&& _matrixMap[row + lengthEffect][col].canDestroy() == true
	    		) {
	    		BoomEffect boomEffect = new BoomEffect(row + lengthEffect, col, 1, this);
			    _boomEffects.add(boomEffect);
			    _matrixMap[row + lengthEffect][col] = new Area(1, true, false, "src/images/areas/sango.jpg");
			    setRandomItemGift(row + lengthEffect, col);
			    
			    canRight = false;
	    	} else {
	    		canRight = false;
	    	}
	    	if (canLeft && row - lengthEffect >= 0 && _matrixMap[row - lengthEffect][col].canEnter() == true) {
	    		BoomEffect boomEffect = new BoomEffect(row - lengthEffect, col, 1, this);
			    _boomEffects.add(boomEffect);
	    	} else if (canLeft && row - lengthEffect >= 0 
	    			&& _matrixMap[row - lengthEffect][col].canEnter() == false
	    			&& _matrixMap[row - lengthEffect][col].canDestroy() == true
	    		) {
	    		BoomEffect boomEffect = new BoomEffect(row - lengthEffect, col, 1, this);
			    _boomEffects.add(boomEffect);
			    _matrixMap[row - lengthEffect][col] = new Area(1, true, false, "src/images/areas/sango.jpg");
			    setRandomItemGift(row - lengthEffect, col);
			    
			    canLeft = false;
	    	} else {
	    		canLeft = false;
	    	}
	    	if (canDown && col + lengthEffect < 11 && _matrixMap[row][col + lengthEffect].canEnter() == true) {
	    		BoomEffect boomEffect = new BoomEffect(row, col + lengthEffect, 1, this);
			    _boomEffects.add(boomEffect);
	    	} else if (canDown && col + lengthEffect < 11 
	    			&& _matrixMap[row][col + lengthEffect].canEnter() == false
	    			&& _matrixMap[row][col + lengthEffect].canDestroy() == true
	    		) {
	    		BoomEffect boomEffect = new BoomEffect(row, col + lengthEffect, 1, this);
			    _boomEffects.add(boomEffect);
			    _matrixMap[row][col + lengthEffect] = new Area(1, true, false, "src/images/areas/sango.jpg");
			    setRandomItemGift(row, col + lengthEffect);
			    
			    canDown = false;
	    	} else {
	    		canDown = false;
	    	}
	    	if (canUp && col - lengthEffect >= 0 && _matrixMap[row][col - lengthEffect].canEnter() == true) {
	    		BoomEffect boomEffect = new BoomEffect(row, col - lengthEffect, 1, this);
			    _boomEffects.add(boomEffect);
	    	} else if (canUp && col - lengthEffect >= 0 
	    			&& _matrixMap[row][col - lengthEffect].canEnter() == false
	    			&& _matrixMap[row][col - lengthEffect].canDestroy() == true
	    		) {
	    		BoomEffect boomEffect = new BoomEffect(row, col - lengthEffect, 1, this);
			    _boomEffects.add(boomEffect);
			    _matrixMap[row][col - lengthEffect] = new Area(1, true, false, "src/images/areas/sango.jpg");
			    setRandomItemGift(row, col - lengthEffect);
			    
			    canUp = false;
	    	} else {
	    		canUp = false;
	    	}
		}
	};

	public synchronized void removeBoom(Boom boom) {
		_booms.remove(boom);
	}

	public synchronized void removeBoomEffect(BoomEffect boomEffect) {
	    _boomEffects.remove(boomEffect);
	}
	
	public synchronized void setRandomItemGift(int row, int col) {
		Random random = new Random();
		float randomValue = random.nextFloat();
		if (randomValue >= 0 && randomValue < 0.1) {
			_items.add(new Item(row, col, ItemType.HEART));
		}
		else if (randomValue >= 0.1 && randomValue < 0.5) {
			_items.add(new Item(row, col, ItemType.BOMB));
		} else if (randomValue >= 0.5 && randomValue < 0.7) {
			_items.add(new Item(row, col, ItemType.ACCUARY));
		} else if (randomValue >= 0.7 && randomValue < 0.8) {
			_items.add(new Item(row, col, ItemType.SPEED));
		}
	}
}
