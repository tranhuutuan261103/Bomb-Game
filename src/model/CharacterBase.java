package model;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
	protected String _avatar;
	protected Color _backColorProfile;
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

	private static final Color _backColorDeath = new Color(88, 90, 96);
	
	public CharacterBase(String characterName, int x, int y, GameManager gameManager) {
		_gameManager = gameManager;
		this._characterName = characterName;
		this._characterImage = "src/images/klee-removebg-preview.png";
		this._avatar = "src/images/characters/klee.jpeg";
		this._backColorProfile = new Color(234, 161, 154);
		this.x = x;
		this.y = y;
		this.vx = 0;
		this.vy = 0;
		this.heart = 1;
		this.bombs = 10;
	}

	public CharacterBase(String characterName, int x, int y, GameManager gameManager, String characterImage, String avatar, Color backColorProfile) {
		_gameManager = gameManager;
		this._characterName = characterName;
		this._characterImage = characterImage;
		this._avatar = avatar;
		this._backColorProfile = backColorProfile;
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
					_gameManager.getItemSoundRequire(this);
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

	public JPanel getProfile() {
		Image image = Toolkit.getDefaultToolkit().getImage(_avatar);
		_characterPanel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (heart <= 0) {
					g.setColor(_backColorDeath);
				} else {
					g.setColor(_backColorProfile);
				}
				g.fillRect(0, 0, 1000, 1000);

				g.drawImage(image, 20, 40, 40, 40, null);
				g.setColor(Color.BLACK);
				g.setFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 16));

				g.drawLine(70, 60, 100, 60);

				g.drawLine(100, 60, 140, 22);
				g.drawLine(140, 22, 180, 22);
				Image imageHeart = Toolkit.getDefaultToolkit().getImage("src/images/items/heart.png");
				g.drawImage(imageHeart, 140, 5, 15, 15, null);
				g.drawString(heart + "", 165, 20);

				g.drawLine(100, 60, 140, 47);
				g.drawLine(140, 47, 180, 47);
				Image imageBomb = Toolkit.getDefaultToolkit().getImage("src/images/items/bomb.png");
				g.drawImage(imageBomb, 140, 30, 15, 15, null);
				g.drawString(bombs + "", 165, 45);

				g.drawLine(100, 60, 140, 72);
				g.drawLine(140, 72, 180, 72);
				Image imageBombImpact = Toolkit.getDefaultToolkit().getImage("src/images/items/target.png");
				g.drawImage(imageBombImpact, 140, 55, 15, 15, null);
				g.drawString(bombImpactLength + "", 165, 70);

				g.drawLine(100, 60, 140, 97);
				g.drawLine(140, 97, 180, 97);
				Image imageSpeed = Toolkit.getDefaultToolkit().getImage("src/images/items/running-shoe.png");
				g.drawImage(imageSpeed, 140, 80, 15, 15, null);
				g.drawString(a + "", 165, 95);
			}
		};

		_characterPanel.setSize(200, 200);
		_characterPanel.setPreferredSize(new Dimension(200, 200));

		return _characterPanel;
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
