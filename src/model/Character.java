package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import javax.swing.JPanel;

import model.item.Item;
import model.item.ItemType;

public class Character implements Runnable {
	private GameManager _gameManager;
	private int x;
	private int y;
	private int vx;
	private int vy;
	
	private int heart;
	private int bombs;
	private int bombImpactLength = 1;
	private int a = 2;
	private int aDuration = 0; // lưu thời gian cho việc giảm tốc độ. Ví dụ: aDuration = 1000 thì sau 10 giây sẽ giảm tốc độ
	
	private int shieldDuration = 300;
	
	
	private JPanel _characterPanel;
	
	public Character(int x, int y, GameManager gameManager) {
		_gameManager = gameManager;
		this.x = x;
		this.y = y;
		this.vx = 0;
		this.vy = 0;
		this.setHeart(1);
		this.setBombs(10);
		
		Thread thread = new Thread(this);
		thread.start();
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
	
	public JPanel getCharacterPanel() {
		return _characterPanel;
	}
	
	//	private boolean isOutOfBounds(int value,int min,int max) {
	//        return value < min || value > max;
	//    }

    private boolean isColliding(double top,double left) {
        int row = (int)top / 40;
        int col = (int)left / 40;
        return _gameManager.getMap().getMatrixMap()[row][col].canEnter() == false;
    }
    
    private synchronized void reduceSpeed() {
    	if (aDuration > 0)
    		aDuration--;
    	else if (aDuration == 0 && a > 2) {
    		a /= 2;
    	}
    }
    
    private synchronized void reduceShieldDuration() {
    	if (shieldDuration > 0)
    		shieldDuration--;
    }
    
    private synchronized void getItemGift() {
    	int row = (int)(x + 20) / 40;
        int col = (int)(y + 20) / 40;
        Item removedItem = null;
    	for (Item itemGift : _gameManager.getItems()) {
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
    		_gameManager.getItems().remove(removedItem);
    	}
    }
    
    private synchronized void checkDie() {
        int row = (int) (x + 20) / 40;
        int col = (int) (y + 20) / 40;
        Iterator<BoomEffect> iterator = _gameManager.getBoomEffects().iterator();
        while (iterator.hasNext()) {
            BoomEffect boomEffect = iterator.next();
            if (boomEffect.get_row() == row && boomEffect.get_col() == col) {
                if (shieldDuration == 0) {
                    if (heart > 0) {
                        heart -= 1;
                        shieldDuration = 300;
                    } else {
                        System.out.println("You die!");
                    }
                }
                iterator.remove(); // Use iterator to safely remove the element
            }
        }
    }

	@Override
	public void run() {
		while(true) {
			try {
				reduceSpeed();
				reduceShieldDuration();
				getItemGift();
				checkDie();
				Thread.sleep(10);
				if (x + vx > 400 || x + vx < 0) {
                    continue;
                }

                if (y + vy > 400 || y + vy <0) {
                    continue;
                }

                if (isColliding(x + vx, y + vy)) {
                	//System.out.println("Error 1");
                    if (vx < 0 && vy == 0) {
                        if (y % 40 >= 25) {
                        	if (y % 40 >= 38) {
                        		y+=2;
                        		continue;
                        	}
                            y += a;
                            continue;
                        }
                    }
                    if (vy < 0 && vx == 0) {
                        if (x % 40 >= 25) {
                        	if (x % 40 >= 38) {
                        		x+=2;
                        		continue;
                        	}
                           x += a;
                           continue;
                        }
                    }
                    continue;
                }

                if (isColliding(x + 39 + vx, y + vy)) {
                	//System.out.println("Error 2");
                    if (vx > 0 && vy == 0) {
                        if (y % 40 >= 25) {
                        	if (y % 40 >= 38) {
                        		y+=2;
                        		continue;
                        	}
                            y += a;
                            continue;
                        }
                    }
                    if (vy < 0 && vx == 0) {
                        if (x % 40 <= 15) {
                        	if (x % 40 <=2) {
                        		x-=2;
                        		continue;
                        	}
                            x -= a;
                            continue;
                        }
                    }
                    continue;
                }

                if (isColliding(x + vx, y + 39 + vy)) {
                	//System.out.println("Error 3");
                    if (vx < 0 && vy == 0) {
                        if (y % 40 <= 15) {
                        	if (y % 40 <= 2) {
                        		y-=2;
                        		continue;
                        	}
                            y -= a;
                            continue;
                        }
                    }
                    if (vy > 0 && vx == 0) {
                        if (x % 40 >= 25) {
                        	if (x % 40 >= 38) {
                        		x+=2;
                        		continue;
                        	}
                            x += a;
                            continue;
                        }
                    }
                    continue;
                }

                if (isColliding(x + 39 + vx, y + 39 + vy)) {
                	//System.out.println("Error 4");
                    if (vx > 0 && vy == 0) {
                        if (y % 40 <= 15) {
                        	if (y % 40 <= 2) {
                        		y-=2;
                        		continue;
                        	}
                            y -= a;
                            continue;
                        }
                    }
                    if (vy > 0 && vx == 0) {
                        if (x % 40 <= 15) {
                        	if (x % 40 <= 2) {
                        		x-=2;
                        		continue;
                        	}
                            x -= a;
                            continue;
                        }
                    }
                    continue;
                }
                x += vx;
                y += vy;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConcurrentModificationException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	public void renderUI(Graphics2D g2d) {
		Image image = Toolkit.getDefaultToolkit().getImage("src/images/klee.jpeg");
		g2d.drawImage(image, x, y, 40, 40, null);
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
}
