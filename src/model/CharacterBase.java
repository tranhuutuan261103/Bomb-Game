package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Iterator;
import javax.swing.JPanel;

import model.item.Item;
import model.item.ItemType;

public class CharacterBase {
    protected GameManager _gameManager;
	protected String _characterName;
	protected String _characterImage;
	protected int x;
	protected int y;
	protected int vx;
	protected int vy;
	
	protected int heart;
	protected int bombs;
	protected int bombImpactLength = 1;
	protected int a = 2;
	protected int aDuration = 0; // lưu thời gian cho việc giảm tốc độ. Ví dụ: aDuration = 1000 thì sau 10 giây sẽ giảm tốc độ
	
	protected int shieldDuration = 300;
	
	protected int plantedBombLimit = 1;
	protected int plantedBombCount = 0;
	
	protected JPanel _characterPanel;
	
	public CharacterBase(String characterName, int x, int y, GameManager gameManager) {
		_gameManager = gameManager;
		this._characterName = characterName;
		this._characterImage = "src/images/klee-removebg-preview.png";
		this.x = x;
		this.y = y;
		this.vx = 0;
		this.vy = 0;
		this.heart = 1;
		this.bombs = 10;
	}

	public CharacterBase(String characterName, int x, int y, GameManager gameManager, String characterImage) {
		_gameManager = gameManager;
		this._characterName = characterName;
		this._characterImage = characterImage;
		this.x = x;
		this.y = y;
		this.vx = 0;
		this.vy = 0;
		this.heart = 1;
		this.bombs = 10;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getA() {
		return a;
	}
	
	public void setVx(int vx) {
		this.vx = vx;
	}
	
	public void setVy(int vy) {
		this.vy = vy;
	}
	
	public int getShieldDuration() {
		return shieldDuration;
	}
	
	public JPanel getCharacterPanel() {
		return _characterPanel;
	}
	
	protected boolean isOutOfBounds(int value,int min,int max) {
        return value < min || value > max;
    }

    protected boolean isColliding(int top,int left) {
        int row = (int)(top / 40);
        int col = (int)(left / 40);
        return _gameManager.getMap().getMatrixMap()[row][col].canEnter() == false;
    }
    
    protected synchronized void reduceSpeed() {
    	if (aDuration > 0)
    		aDuration--;
    	else if (aDuration == 0 && a > 2) {
    		a /= 2;
    	}
    }
    
    protected synchronized void reduceShieldDuration() {
    	if (shieldDuration > 0)
    		shieldDuration--;
    }
    
    protected void getItemGift() {
    	int row = (int)(x + 20) / 40;
        int col = (int)(y + 20) / 40;
        Item removedItem = null;
        synchronized(_gameManager.getItems()) {
	    	Iterator<Item> iterator = _gameManager.getItems().iterator();
        		while (iterator.hasNext()) {
					Item itemGift = iterator.next();
					if (itemGift.getX() == row && itemGift.getY() == col) {
						if (itemGift.getType() == ItemType.HEART) {
							if (heart < 3)
								heart += 1;
							removedItem = itemGift;
							break;
						} else if (itemGift.getType() == ItemType.BOMB) {
							bombs += 1;
							removedItem = itemGift;
							break;
						} else if (itemGift.getType() == ItemType.ACCUARY) {
							if (bombImpactLength < 3)
								bombImpactLength += 1;
							removedItem = itemGift;
							break;
						} else if (itemGift.getType() == ItemType.SPEED) {
							if (a < 4) {
								a *= 2;
								aDuration = 1000;
							}
							removedItem = itemGift;
							break;
						}
					}
	    		}
				if (removedItem != null) {
					iterator.remove(); // Use iterator to remove the item
				}
    		}
    }
    
    protected void hitByBomb() {
        int row = (int) (x + 20) / 40;
        int col = (int) (y + 20) / 40;
        synchronized (_gameManager.getBoomEffects()) {
        	Iterator<BoomEffect> iterator = _gameManager.getBoomEffects().iterator();
	        while (iterator.hasNext()) {
	            BoomEffect boomEffect = iterator.next();
	            if (boomEffect.get_row() == row && boomEffect.get_col() == col && boomEffect.get_isDestroyed() == true) {
	                if (shieldDuration == 0) {
	                    if (heart > 1) {
	                        heart -= 1;
	                        shieldDuration = 300;
	                    } else {
	                    	heart = 0;
	                    }
	                }
	                break;
	            }
			}
        }
    }

	public void renderUI(Graphics2D g2d) {
		Image image = Toolkit.getDefaultToolkit().getImage("src/images/characters/klee-removebg-preview.png");
		g2d.drawImage(image, x, y, 40, 40, null);
		
		if (shieldDuration > 0) {
			Image shieldImg = Toolkit.getDefaultToolkit().getImage("src/images/shield.png");
			g2d.drawImage(shieldImg, x + 20, y + 20, 20, 20, null);
		}
	}

	public int getBombImpactLength() {
		return bombImpactLength;
	}

	public void setBombImpactLength(int bombImpactLength) {
		this.bombImpactLength = bombImpactLength;
	}

	public int getHeart() {
		return heart;
	}

	public void setHeart(int heart) {
		this.heart = heart;
	}

	public int getBombs() {
		return bombs;
	}

	public void setBombs(int bombs) {
		this.bombs = bombs;
	}

	public int getPlantedBombLimit() {
		return plantedBombLimit;
	}

	public void setPlantedBombLimit(int plantedBombLimit) {
		this.plantedBombLimit = plantedBombLimit;
	}

	public int getPlantedBombCount() {
		return plantedBombCount;
	}

	public void setPlantedBombCount(int plantedBombCount, boolean needWait) {
		if (needWait) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(500);
						setPlantedBombCount(plantedBombCount, false);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});

			thread.start();
		} else {
			this.plantedBombCount = plantedBombCount;
		}
	}
}
