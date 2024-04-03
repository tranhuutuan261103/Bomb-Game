package model;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import model.item.Item;
import model.item.ItemType;
import utils.*;

public class GameManager {
	private Character _character;
	private ArrayList<AICharacter> _aiCharacters;
	private Map _map;
	private ArrayList<Boom> _booms;
	private ArrayList<BoomEffect> _boomEffects;
	private ArrayList<Item> _items;
	private ArrayList<DirectionPoint> _directionPoints = new ArrayList<>(){
		{
			add(new DirectionPoint(Direction.UP, new Point(0, -1)));
			add(new DirectionPoint(Direction.DOWN, new Point(0, 1)));
			add(new DirectionPoint(Direction.LEFT, new Point(-1, 0)));
			add(new DirectionPoint(Direction.RIGHT, new Point(1, 0)));
		}
	};
	
	public GameManager() {
		reset();
	}
	
	public void reset() {
		_map = new Map("src/db/map.txt");
		_booms = new ArrayList<>();
		_boomEffects = new ArrayList<>();
		_items = new ArrayList<>();
		_character = new Character("Klee", 0, 0, this);
		_aiCharacters = new ArrayList<>();
		_aiCharacters.add(new AICharacter("Slime Electro", 0, (_map.getMatrixMap()[0].length - 1) * 40, this, "src/images/characters/slime_electro-removebg.png"));
		_aiCharacters.add(new AICharacter("Slime Dendro", (_map.getMatrixMap().length - 1) * 40, 0, this, "src/images/characters/slime_dendro-removebg.png"));
		_aiCharacters.add(new AICharacter("Slime Hydro", (_map.getMatrixMap().length - 1) * 40, (_map.getMatrixMap()[0].length - 1) * 40, this, "src/images/characters/slime_hydro-removebg.png"));
	}
	
	public Character getCharacter() {
		return _character;
	}
	
	public ArrayList<AICharacter> getAICharacters() {
		return _aiCharacters;
	}
	
	public Map getMap() {
		return _map;
	}
	
	public ArrayList<Item> getItems() {
		return _items;
	}

	public ArrayList<Boom> getBooms() {
		return _booms;
	}
	
	public ArrayList<BoomEffect> getBoomEffects() {
		return _boomEffects;
	}
	
	public void renderAICharacter(Graphics2D g2d) {
		for (AICharacter aiCharacter : _aiCharacters) {
			aiCharacter.renderUI(g2d);
		}
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
		synchronized(_items) {
			for (Item item : _items) {
				item.renderUI(g2d);
			}
		}
	}

	public void addBoom(int x, int y) {
		int row = (int)(x + 20) / 40;
		int col = (int)(y + 20) / 40;
		if (_map.getMatrixMap()[row][col].canEnter() == true && _character.getBombs() > 0){
			Boom boom = new Boom(row, col, 3, this, _character);
			this._character.setBombs(_character.getBombs() - 1);
			_booms.add(boom);
			addBoomEffects(boom);
		}
	}

	private boolean checkCollision(int row, int col) {
		if (row < 0 || row >= _map.getMatrixMap().length || col < 0 || col >= _map.getMatrixMap()[0].length) {
			return true;
		}

		if (_map.getMatrixMap()[row][col].canEnter() == false && _map.getMatrixMap()[row][col].canDestroy() == false){
			return true;
		}
		return false;
	}

	public void addBoomEffects(Boom boom) {
		if (checkCollision(boom.get_row(), boom.get_col()) == false) {
			BoomEffect boomEffect = new BoomEffect(boom.get_row(), boom.get_col(), 3, this);
			_boomEffects.add(boomEffect);
		}

		for (DirectionPoint directionPoint : _directionPoints) {
			int row = boom.get_row();
			int col = boom.get_col();
			for (int i = 1; i <= _character.getBombImpactLength(); i++) {
				row += directionPoint.point.x;
				col += directionPoint.point.y;
				if (checkCollision(row, col) == true) {
					break;
				}
				BoomEffect boomEffect = new BoomEffect(row, col, 3, this);
				_boomEffects.add(boomEffect);
			}
		}
	}

	public void makeBombExplode(Boom boom) {
		CharacterBase character = null;
		if(boom.getCharacter() instanceof Character) {
			character = _character;
		} else {
			for(AICharacter aiCharacter : _aiCharacters) {
				if(aiCharacter == boom.getCharacter()) {
					character = aiCharacter;
					break;
				}
			}
		}
		character.setPlantedBombCount(boom.getCharacter().getPlantedBombCount() - 1, true);
		synchronized(_booms) {
			_booms.remove(boom);
		}
	}

	public void makeBoomEffectExplode(BoomEffect boomEffect) {
		if (_map.getMatrixMap()[boomEffect.get_row()][boomEffect.get_col()].canDestroy() == true){
			_map.getMatrixMap()[boomEffect.get_row()][boomEffect.get_col()] = new Area(1, true, false, "src/images/areas/land.png");
			setRandomItemGift(boomEffect.get_row(), boomEffect.get_col());
		}
	}

	public void removeBoomEffect(BoomEffect boomEffect) {
		synchronized(_boomEffects) {
			_boomEffects.remove(boomEffect);
		}
	}
	
	public synchronized void setRandomItemGift(int row, int col) {
		Random random = new Random();
		float randomValue = random.nextFloat();
		if (randomValue >= 0 && randomValue < 0.05) {
			_items.add(new Item(row, col, ItemType.HEART));
		}
		else if (randomValue >= 0.05 && randomValue < 0.7) {
			_items.add(new Item(row, col, ItemType.BOMB));
		} else if (randomValue >= 0.7 && randomValue < 0.8) {
			_items.add(new Item(row, col, ItemType.ACCUARY));
		} else if (randomValue >= 0.8 && randomValue < 0.9) {
			_items.add(new Item(row, col, ItemType.SPEED));
		}
	}
}
