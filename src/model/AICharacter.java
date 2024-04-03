package model;

import utils.Direction;
import utils.DirectionPoint;
import model.item.Item;

import java.util.Collections;
import java.util.LinkedList;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

public class AICharacter extends CharacterBase implements Runnable {
	private LinkedList<Direction> _directions = new LinkedList<Direction>();
	private LinkedList<Point> _path = new LinkedList<Point>();
	private LinkedList<DirectionPoint> _directionPoints = 
		new LinkedList<DirectionPoint>() {
			{
				add(new DirectionPoint(Direction.UP, new Point(0, -1)));
				add(new DirectionPoint(Direction.DOWN, new Point(0, 1)));
				add(new DirectionPoint(Direction.LEFT, new Point(-1, 0)));
				add(new DirectionPoint(Direction.RIGHT, new Point(1, 0)));
			}
		};
	private Direction direction = Direction.UP;
	private boolean planted_bomb = false;

	private int numRow = 15;
	private int numCol = 13;

	public AICharacter(String characterName, int x, int y, GameManager gameManager, String characterImage) {
		super(characterName, x, y, gameManager, characterImage);

		numRow = gameManager.getMap().getMatrixMap().length;
		numCol = gameManager.getMap().getMatrixMap()[0].length;

		Thread thread = new Thread(this);
		thread.start();
	}

	private State[][] createGrid() {
		State[][] grid = new State[_gameManager.getMap().getMatrixMap().length][_gameManager.getMap().getMatrixMap()[0].length];
		for (int i = 0; i < _gameManager.getMap().getMatrixMap().length; i++) {
			for (int j = 0; j < _gameManager.getMap().getMatrixMap()[0].length; j++) {
				if (_gameManager.getMap().getMatrixMap()[i][j].canEnter() == true) {
					grid[i][j] = State.SAFE;
				} else if (_gameManager.getMap().getMatrixMap()[i][j].canDestroy() == true) {
					grid[i][j] = State.DESTROYABLE;
				} else {
					grid[i][j] = State.UNREACHABLE;
				}
			}
		}

		for (int i = 0; i < _gameManager.getBoomEffects().size(); i++) {
			BoomEffect boomEffect = _gameManager.getBoomEffects().get(i);
			int row = boomEffect.get_row();
			int col = boomEffect.get_col();
			if (_gameManager.getMap().getMatrixMap()[row][col].canDestroy() == true) {
				grid[row][col] = State.DESTROYABLE;
			} else if (grid[row][col] == State.SAFE) {
				if (boomEffect.get_isDestroyed() == false) {
					grid[row][col] = State.UNSAFE;
				} else {
					grid[row][col] = State.VERYDANGER;
				}
			}
		}

		for (int i = 0; i < _gameManager.getItems().size(); i++) {
			Item item = _gameManager.getItems().get(i);
			int row = item.getX();
			int col = item.getY();
			if (grid[row][col] == State.SAFE) {
				grid[row][col] = State.CANGETITEM;
			}
		}

		Character character = _gameManager.getCharacter();
		if (character.getHeart() > 0) {
			int row = (int) (character.getX() / 40);
			int col = (int) (character.getY() / 40);
			grid[row][col] = State.DESTROYABLE;
		}

		for (AICharacter aiCharacter : _gameManager.getAICharacters()) {
			int row = (int) (aiCharacter.getX() / 40);
			int col = (int) (aiCharacter.getY() / 40);
			if (this != aiCharacter) {
				grid[row][col] = State.DESTROYABLE;
			}
		}

		for (int i = 0; i < _gameManager.getBooms().size(); i++) {
			Boom boom = _gameManager.getBooms().get(i);
			int row = boom.get_row();
			int col = boom.get_col();
			grid[row][col] = State.UNREACHABLE;
		}

		return grid;
	}

	// private void showState(State[][] grid) {
	// 	for (int j = 0; j < grid[0].length; j++) {
	// 		for (int i = 0; i < grid.length; i++) {
	// 			if (grid[i][j] == State.SAFE) {
	// 				System.out.print("S ");
	// 			} else if (grid[i][j] == State.UNSAFE) {
	// 				System.out.print("U ");
	// 			} else if (grid[i][j] == State.DESTROYABLE) {
	// 				System.out.print("D ");
	// 			} else {
	// 				System.out.print("X ");
	// 			}
	// 		}
	// 		System.out.println();
	// 	}
	// 	System.out.println();
	// }

	private void move() {
		if (_directions.isEmpty()) {
			return;
		}

		if (direction == Direction.UP) {
			if (!isColliding(x, y - a)) {
				y -= a;
			}
		} else if (direction == Direction.DOWN) {
			if (!isColliding(x, y + a)) {
				y += a;
			}
		} else if (direction == Direction.LEFT) {
			if (!isColliding(x - a, y)) {
				x -= a;
			}
		} else if (direction == Direction.RIGHT) {
			if (!isColliding(x + a, y)) {
				x += a;
			}
		}

		if (x % 40 == 0 && y % 40 == 0) {
			_directions.poll();
			_path.poll();
			if (_path.size() > 1) {
				State grid[][] = createGrid();
				// get the next direction
				Point next = _path.get(1);
				if (grid[next.x][next.y] == State.DESTROYABLE 
				|| grid[next.x][next.y] == State.UNREACHABLE 
				|| grid[next.x][next.y] == State.FOUND 
				|| grid[next.x][next.y] == State.VERYDANGER) {
					_directions.clear();
					_path.clear();
					return;
				}
			}
		}
	}

	private void makeMove() {
		if (_directions.isEmpty()) {
			if (planted_bomb == true) {
				if (plantedBombCount < plantedBombLimit){
					Boom boom = new Boom((int) (x / 40), (int) (y / 40), 3, _gameManager, this);
					_gameManager.getBooms().add(boom);
					_gameManager.addBoomEffects(boom);
					bombs -= 1;
					plantedBombCount += 1;
					planted_bomb = false;
				}
			}
			dfs(createGrid());
		} else {
			direction = _directions.peek();
			move();
		}
	}

	private void dfs(State[][] grid) {
		LinkedList<Point> newPath = new LinkedList<Point>();
		newPath.add(new Point((int) (x / 40), (int) (y / 40)));
		int depth = 0;
		if (this.getBombs() == 0 || this.getPlantedBombCount() == this.getPlantedBombLimit()){
			if (canReachTo(State.CANGETITEM, createGrid(), (int) (x / 40), (int) (y / 40), 10)) {
				dfsHelper(State.CANGETITEM, grid, newPath, depth);
			} else {
				dfsHelper(State.SAFE, grid, newPath, depth);
			}
		} else {
			dfsHelper(State.DESTROYABLE, grid, newPath, depth);
		}

		_path = newPath;
	}

	private void dfsHelper(State endCondition, State[][] grid, LinkedList<Point> path, int depth) {
		Point last = path.getLast();
		if (depth > 200) {
			return;
		}

		if (endCondition == State.CANGETITEM && grid[last.x][last.y] == State.CANGETITEM) {
			return;
		} else if (endCondition == State.SAFE && grid[last.x][last.y] == State.SAFE) {
			return;
		} else if (endCondition == State.DESTROYABLE) {
			if ((last.x + 1 < numRow && grid[last.x + 1][last.y] == endCondition)
			|| (last.x > 0 && grid[last.x - 1][last.y] == endCondition)
			|| (last.y + 1 < numCol && grid[last.x][last.y + 1] == endCondition)
			|| (last.y > 0 && grid[last.x][last.y - 1] == endCondition)) {
				if (path.size() > 0 && endCondition == State.DESTROYABLE) {
					planted_bomb = true;
					return;
				}
			}
		}

		grid[last.x][last.y] = State.FOUND;
		Collections.shuffle(_directionPoints);

		boolean found = false;
		// Safe
		for (DirectionPoint directionPoint : _directionPoints) {
			int x = last.x + directionPoint.point.x;
			int y = last.y + directionPoint.point.y;
			if (x < 0 || x >= numRow || y < 0 || y >= numCol) {
				continue;
			}
			if (grid[x][y] == State.SAFE || grid[x][y] == State.CANGETITEM) {
				path.add(new Point(x, y));
				_directions.add(directionPoint.direction);
				found = true;
				break;
			}
		}

		if (!found) {
			// Unsafe
			for (DirectionPoint directionPoint : _directionPoints) {
				int x = last.x + directionPoint.point.x;
				int y = last.y + directionPoint.point.y;
				if (x < 0 || x >= numRow || y < 0 || y >= numCol) {
					continue;
				}
				if (grid[x][y] == State.UNSAFE) {
					path.add(new Point(x, y));
					_directions.add(directionPoint.direction);
					found = true;
					break;
				}
			}
		}

		if (!found) {
			if (!_directions.isEmpty()) {
				_directions.poll();
				path.poll();
			}
		}

		depth += 1;
		dfsHelper(endCondition, grid, path, depth);
	}

	private boolean canReachTo(State endState, State[][] grid, int x, int y, int maxDepth) {
		if (x < 0 || x >= numRow || y < 0 || y >= numCol || grid[x][y] == State.UNREACHABLE || grid[x][y] == State.VERYDANGER || grid[x][y] == State.FOUND || maxDepth == 0) {
			return false;
		}

		if (grid[x][y] == endState) {
			return true;
		}

		grid[x][y] = State.FOUND;
		for (DirectionPoint directionPoint : _directionPoints) {
			if (canReachTo(endState, grid, x + directionPoint.point.x, y + directionPoint.point.y, maxDepth - 1)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (heart > 0) {
					hitByBomb();
				}
				if (heart == 0) {
					System.out.println(_characterName + " is dead");
					break;
				}
				reduceSpeed();
				reduceShieldDuration();
				getItemGift();
				makeMove();
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void renderUI(Graphics2D g2d) {
		Image image = Toolkit.getDefaultToolkit().getImage(_characterImage);
		g2d.drawImage(image, getX(), getY(), 40, 40, null);
		
		if (getShieldDuration() > 0) {
			Image shieldImg = Toolkit.getDefaultToolkit().getImage("src/images/shield.png");
			g2d.drawImage(shieldImg, getX() + 20, getY() + 20, 20, 20, null);
		}
	}
}

enum State {
	UNREACHABLE, VERYDANGER, UNSAFE, SAFE, DESTROYABLE, FOUND, CANGETITEM
}