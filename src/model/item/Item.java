package model.item;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

public class Item implements IItem {
	private int x;
	private int y;
	
	private int offset = 40;
	
	private ItemType type;

	public Item(int x, int y, ItemType type) {
		super();
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}

	@Override
	public void renderUI(Graphics2D g2d) {
		Image image = Toolkit.getDefaultToolkit().getImage("src/images/items/items.gif");
		if (this.type == ItemType.HEART) {
			g2d.drawImage(image, x * offset, y * offset, (x + 1) * offset, (y + 1) * offset, 
					90, 78, 195, 183, null);
		} else if (this.type == ItemType.BOMB) {
			g2d.drawImage(image, x * offset, y * offset, (x + 1) * offset, (y + 1) * offset, 
					90, 210, 195, 315, null);
		} else if (this.type == ItemType.ACCUARY) {
			g2d.drawImage(image, x * offset, y * offset, (x + 1) * offset, (y + 1) * offset, 
					90, 345, 195, 450, null);
		} else if (this.type == ItemType.SPEED) {
			g2d.drawImage(image, x * offset, y * offset, (x + 1) * offset, (y + 1) * offset, 
					90, 480, 195, 585, null);
		}
	}
	
	
}