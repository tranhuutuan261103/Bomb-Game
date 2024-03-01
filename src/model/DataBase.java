package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
	private static DataBase instance;
	
	private DataBase() {
		
    }
	
	public static synchronized DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }
	
	public ArrayList<Area> getAreas() {
		List<Area> areas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/db/areas.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    int typeId = Integer.parseInt(parts[0]);
                    boolean canEnter = Boolean.parseBoolean(parts[1]);
                    String imageUrl = parts[2];

                    Area area = new Area(typeId, canEnter, imageUrl);
                    areas.add(area);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (ArrayList<Area>) areas;
	}
}
