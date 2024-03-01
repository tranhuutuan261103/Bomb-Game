package main;

import controller.MainController;
import view.ViewMain;

public class GameMain {
	public static void main(String[] args) {
        ViewMain viewMain = new ViewMain();
        MainController mainController = new MainController(viewMain);
        mainController.displayUI();
        viewMain.getView().setVisible(true);
	}
}
