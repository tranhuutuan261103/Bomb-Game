package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

public class Area {
	private int _typeId;
	private boolean _canEnter;
	private boolean _canDestroy;
	private String _imageUrl;
	
	public Area(int typeId, boolean canEnter, boolean canDestroy, String imageUrl) {
		this._typeId = typeId;
		this._canEnter = canEnter;
		this._canDestroy = canDestroy;
		this._imageUrl = imageUrl;
	}
	
	public Area(Area area) {
		this._typeId = area._typeId;
		this._canEnter = area._canEnter;
		this._canDestroy = area._canDestroy;
		this._imageUrl = area._imageUrl;
	}
	
	public int getTypeId() {
		return _typeId;
	}

	public void setTypeId(int _typeId) {
		this._typeId = _typeId;
	}

	public boolean canEnter() {
		return _canEnter;
	}

	public void setCanEnter(boolean _canEnter) {
		this._canEnter = _canEnter;
	}
	
	public boolean canDestroy() {
		return _canDestroy;
	}
	
	public void setCanDetroy(boolean canDestroy) {
		this._canDestroy = canDestroy;
	}

	public String getImageUrl() {
		return _imageUrl;
	}

	public void setImageUrl(String _imageUrl) {
		this._imageUrl = _imageUrl;
	}

	public void renderUI(Graphics2D g2d,int x,int y) {
		Image backImg = Toolkit.getDefaultToolkit().getImage("src/images/areas/land.png");
		g2d.drawImage(backImg, x, y, 40, 40, null);
		
		if (_imageUrl.compareTo("src/images/areas/land.png") != 0) {
			Image image = Toolkit.getDefaultToolkit().getImage(_imageUrl);
			g2d.drawImage(image, x, y, 40, 40, null);
		}
	}
}
