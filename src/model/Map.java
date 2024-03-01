package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map extends Thread {
	int[][] matrixMap;
	int cellWidth = 40;
	
	public Map(String pathDB) {
		File file = new File(pathDB);
		Scanner scanner;
		try {
			scanner = new Scanner(file);
		
			int rows = 11;  // Update with the actual number of rows in your file
	        int cols = 11;  // Update with the actual number of columns in your file
	        matrixMap = new int[rows][cols];

	        // Read the file content into the matrix
	        for (int i = 0; i < rows; i++) {
	            if (scanner.hasNextLine()) {
	                String line = scanner.nextLine();
	                String[] elements = line.split(" ");

	                for (int j = 0; j < Math.min(cols, elements.length); j++) {
	                    matrixMap[i][j] = Integer.parseInt(elements[j]);
	                }
	            }
	        }
	        
	        // Close the scanner
	        scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int[][] getMatrixMap() {
		return matrixMap;
	}
	
	public void renderUI(Graphics2D g2d) {
		Image image = Toolkit.getDefaultToolkit().getImage("src/images/boxgo.png");
		for (int i=0;i<11;i++) {
			for(int j=0;j<11;j++) {
				if (matrixMap[i][j] == 0) {
					//g.drawImage(image, i * cellWidth, j * cellWidth, viewMain);
					g2d.setColor(Color.CYAN);
					//g2d.fillRect(i * cellWidth, j * cellWidth, cellWidth, cellWidth);
					g2d.drawImage(image, i * cellWidth, j * cellWidth, cellWidth, cellWidth, null);
				} else if (matrixMap[i][j] == 1) {
					g2d.setColor(Color.BLACK);
					g2d.fillRect(i * cellWidth, j * cellWidth, cellWidth, cellWidth);
				} else if (matrixMap[i][j] == 2) {
					g2d.setColor(Color.BLACK);
					g2d.fillRect(i * cellWidth, j * cellWidth, cellWidth, cellWidth);
					Image imageBoom = Toolkit.getDefaultToolkit().getImage("src/map/bomb.gif");
					g2d.drawImage(imageBoom, i * cellWidth, j * cellWidth, cellWidth, cellWidth, null);
				} else if (matrixMap[i][j] == 3) {
					g2d.setColor(Color.RED);
					g2d.fillRect(i * cellWidth, j * cellWidth, cellWidth, cellWidth);
				}
			}
		}
	}
}
