package model;

import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Map extends Thread {
	Area[][] matrixMap;
	int cellWidth = 40;
	
	public Map(String pathDB) {
		ArrayList<Area> areas = DataBase.getInstance().getAreas();
		File file = new File(pathDB);
		Scanner scanner;
		try {
			scanner = new Scanner(file);
		
			int rows = 11;  // Update with the actual number of rows in your file
	        int cols = 11;  // Update with the actual number of columns in your file
	        matrixMap = new Area[rows][cols];

	        // Read the file content into the matrix
	        for (int i = 0; i < rows; i++) {
	            if (scanner.hasNextLine()) {
	                String line = scanner.nextLine();
	                String[] elements = line.split(" ");

	                for (int j = 0; j < Math.min(cols, elements.length); j++) {
	                	int typeId = Integer.parseInt(elements[j]);
	                	for (Area area : areas) {
							if (area.getTypeId() == typeId) {
								matrixMap[i][j] = new Area(typeId, area.isCanEnter(), area.getImageUrl());
								break;
							}
						}
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
	
	public Area[][] getMatrixMap() {
		return matrixMap;
	}
	
	public void renderUI(Graphics2D g2d) {
		for (int i=0;i<11;i++) {
			for(int j=0;j<11;j++) {
				matrixMap[i][j].renderUI(g2d, i * cellWidth, j * cellWidth);
			}
		}
	}
}
