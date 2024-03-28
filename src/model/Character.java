package model;

import java.util.ConcurrentModificationException;

public class Character extends CharacterBase implements Runnable{
	
	public Character(String characterName, int x, int y, GameManager gameManager) {
		super(characterName, x, y, gameManager);

        Thread thread = new Thread(this);
        thread.start();
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
				if (isOutOfBounds(x + vx, 0, (_gameManager.getMap().getMatrixMap().length - 1) * 40))
					continue;

                if (isOutOfBounds(y + vy, 0, (_gameManager.getMap().getMatrixMap()[0].length - 1) * 40)) {
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
}
