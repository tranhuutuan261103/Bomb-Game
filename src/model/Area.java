package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

public class Area {
	private int _typeId;
	private boolean _canEnter;
	private String _imageUrl;
	public Area(int typeId, boolean canEnter, String imageUrl) {
		this._typeId = typeId;
		this._canEnter = canEnter;
		this._imageUrl = imageUrl;
	}
	
	public int getTypeId() {
		return _typeId;
	}

	public void setTypeId(int _typeId) {
		this._typeId = _typeId;
	}

	public boolean isCanEnter() {
		return _canEnter;
	}

	public void setCanEnter(boolean _canEnter) {
		this._canEnter = _canEnter;
	}

	public String getImageUrl() {
		return _imageUrl;
	}

	public void setImageUrl(String _imageUrl) {
		this._imageUrl = _imageUrl;
	}

	public void renderUI(Graphics2D g2d,int x,int y) {
		Image image = Toolkit.getDefaultToolkit().getImage(_imageUrl);
		g2d.drawImage(image, x, y, 40, 40, null);
	}
}
