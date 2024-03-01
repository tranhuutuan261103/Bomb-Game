package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class Character implements Runnable {
	private GameManager _gameManager;
	private int x;
	private int y;
	private int vx;
	private int vy;
	
	private int a = 2;
	
	private int bombImpactLength = 1;
	
	private JPanel _characterPanel;
	
	public Character(int x, int y, GameManager gameManager) {
		_gameManager = gameManager;
		this.x = x;
		this.y = y;
		this.vx = 0;
		this.vy = 0;
		
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
        return _gameManager.getMap().getMatrixMap()[row][col].isCanEnter() == false;
    }

	@Override
	public void run() {
		try {
			while(true) {
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
                            y += a;
                            continue;
                        }
                    }
                    if (vy < 0 && vx == 0) {
                        if (x % 40 >= 25) {
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
                            y += a;
                            continue;
                        }
                    }
                    if (vy < 0 && vx == 0) {
                        if (x % 40 <= 15) {
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
                            y -= a;
                            continue;
                        }
                    }
                    if (vy > 0 && vx == 0) {
                        if (x % 40 >= 25) {
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
                            y -= a;
                            continue;
                        }
                    }
                    if (vy > 0 && vx == 0) {
                        if (x % 40 <= 15) {
                            x -= a;
                            continue;
                        }
                    }
                    continue;
                }
                x += vx;
                y += vy;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
}
