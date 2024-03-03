package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

public class AICharacter extends Character {

	public AICharacter(String characterName, int x, int y, GameManager gameManager) {
		super(characterName, x, y, gameManager);
	}

	@Override
	public void renderUI(Graphics2D g2d) {
		Image image = Toolkit.getDefaultToolkit().getImage("src/images/slime-removebg-preview.png");
		g2d.drawImage(image, getX(), getY(), 40, 40, null);
		
		if (getShieldDuration() > 0) {
			Image shieldImg = Toolkit.getDefaultToolkit().getImage("src/images/shield.png");
			g2d.drawImage(shieldImg, getX() + 20, getY() + 20, 20, 20, null);
		}
	}
}
