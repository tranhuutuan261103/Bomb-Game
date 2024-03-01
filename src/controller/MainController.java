package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import model.Boom;
import model.BoomEffect;
import model.Character;
import view.ViewMain;

public class MainController implements IMainController, KeyListener {
	ViewMain _viewMain;
	ArrayList<Boom> booms = new ArrayList<>();
	ArrayList<BoomEffect> boomEffects = new ArrayList<>();
	Character character;
	
	public MainController(ViewMain viewMain) {
		_viewMain = viewMain;
	}

	@Override
	public void displayUI() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
