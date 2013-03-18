package game7DRL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Image;

public class MapLoader {
	ArrayList<Tile> allTiles = new ArrayList<Tile>();
	static ArrayList<MapPart> allMapParts = new ArrayList<MapPart>();
	public MapLoader() throws IOException{
		loadMasterTileList("res/tiles/","master.txt");
		loadMasterPartsList("res/maps/","master.txt");
	}
	
	public void loadMasterTileList(String directory, String fileName) throws IOException{
		String[] tileList = new String[1];
		String[] temp;
		
		FileReader fr = new FileReader(directory+fileName);
		BufferedReader br = new BufferedReader(fr);

		//init based on number of tiles in file
		int numOfTile = Integer.parseInt(br.readLine());
		for(int i = 0; i <= numOfTile+1;i++){
			allTiles.add(new Tile());
		}
		
		String currentString = br.readLine();
		tileList[0] = currentString;
		
		while((currentString = br.readLine())!=null){
			if(currentString != ""){
				temp = new String[tileList.length+1];
				for(int i = 0; i <= tileList.length-1; i++){
					temp[i] = tileList[i];
				}
				temp[temp.length-1] = currentString;
				tileList = temp;
			}
		}
		br.close();
		loadTiles(directory,tileList);
	}
	public void loadTiles(String directory, String[] tileList)throws IOException{
		for(int i = 0; i <= tileList.length-1; i++){
			FileReader fr = new FileReader(directory+tileList[i]);
			BufferedReader br = new BufferedReader(fr);
			
			int tileID = Integer.parseInt(br.readLine()); //this is the ID used in the loadMapParts to create the mapPart
			int floorImageID = Integer.parseInt(br.readLine());
			Image tempFloor;
			if(floorImageID != 0){
				tempFloor = Game.loadedResources.tileImages[floorImageID];
			}else{
				tempFloor = null;
			}
			int collisionImageID = Integer.parseInt(br.readLine());
			Image tempCollision;
			if(floorImageID != 0){
				tempCollision = Game.loadedResources.tileImages[collisionImageID];
			}else{
				tempCollision = null;
			}
			int overImageID = Integer.parseInt(br.readLine());
			Image tempOver;
			if(floorImageID != 0){
				tempOver = Game.loadedResources.tileImages[overImageID];
			}else{
				tempOver = null;
			}
			int numHasColl = Integer.parseInt(br.readLine());
			boolean hasCollision;
			if(numHasColl == 1){
				hasCollision = true;
			}else{
				hasCollision = false;
			}
			br.close();
			allTiles.set(tileID, new Tile(tileID,tempFloor,tempCollision,tempOver, hasCollision));
		}
		allTiles.set(0, null);
	}

	public void loadMasterPartsList(String directory, String fileName) throws IOException{
		String[] partNames = new String[1];
		String[] temp;
		
		FileReader fr = new FileReader(directory+fileName);
		BufferedReader br = new BufferedReader(fr);

		//init based on number of parts in file
		int numOfParts = Integer.parseInt(br.readLine());
		for(int i = 0; i <= numOfParts+1;i++){
			allMapParts.add(new MapPart());
		}
		
		String currentString = br.readLine();
		partNames[0] = currentString;
		
		while((currentString = br.readLine()) != null){
			if(currentString != ""){
				temp = new String[partNames.length+1];
				for(int i = 0; i <= partNames.length-1; i++){
					temp[i] = partNames[i];
				}
				temp[temp.length-1] = currentString;
				partNames = temp;
			}
		}
		br.close();
		loadMapParts(directory, partNames);
	}
	
	public void loadMapParts(String directory, String[] partNames) throws IOException{
		for(int i = 0; i <= partNames.length-1; i++){
			FileReader fr = new FileReader(directory+partNames[i]);
			BufferedReader br = new BufferedReader(fr);
			int mapPartID = Integer.parseInt(br.readLine()); 
			int mapWidth = Integer.parseInt(br.readLine());
			int mapHeight = Integer.parseInt(br.readLine());
			MapPart temp = new MapPart(mapWidth, mapHeight);
			String currentString = br.readLine();
			String[] currentStringArray = currentString.split(":");
			for(int c = 0; c <= temp.partTiles[0].length-1; c++){
				for(int z = 0; z <= temp.partTiles.length-1; z++){
					temp.partTiles[z][c] = allTiles.get(Integer.parseInt(currentStringArray[z]));
				}
				if((currentString = br.readLine()) != null){
					currentStringArray = currentString.split(":");
				}
			}
			br.close();
			allMapParts.set(mapPartID, temp);
		}
	}
	
	public static Map initMap(Map newPopMap){
		Map newMap = null;
		for(int i=0; i<=newPopMap.populationDensity; i++){
			 newMap = populateMap(newPopMap);
		}

		
		
		for(int i = 0; i<= newMap.tileMap[0].length-1; i++){
			for(int c = 0; c<= newMap.tileMap.length-1; c++){
				if(newMap.tileMap[c][i] == null){
				newMap.tileMap[c][i] = Game.loadedMapResources.allTiles.get(1);
				}
				//Fill in any un-populated holes with standard grass
			}	
		}
		return newMap;
	}
	
	public static Map populateMap(Map popMap){
			int locationX = (int) (Math.random()*popMap.tileMap.length);
			int locationY = (int) (Math.random()*popMap.tileMap[0].length);
			int partType = (int) (Math.random()*allMapParts.size());
			if(partType != 0){
				MapPart temp = allMapParts.get(partType);
				boolean preCheck = prePopulationCheck(popMap,temp,locationX,locationY);
				if(preCheck == false){
					for(int z = 0; z <= temp.partTiles[0].length-1; z++){
						for(int c = 0; c <= temp.partTiles.length-1; c++){
							popMap.tileMap[c+locationX][z+locationY] = temp.partTiles[c][z];
						
						}
					}
				}else{
				}
			}
			
			return popMap;
	}

	public static boolean prePopulationCheck(Map popMap, MapPart temp, int locationX, int locationY){
		int x = locationX;
		int y = locationY;
		int width = temp.partTiles.length;
		int height = temp.partTiles[0].length;

		int popX = 0;
		int popY = 0;
		int popWidth = popMap.tileMap.length;
		int popHeight = popMap.tileMap[0].length;
		
		if(x+width>popWidth){
			return true;
		}
		if(y+height>popHeight){
			return true;
		}
		if(x < popX){
			return true;
		}
		if(y < popY){
			return true;
		}
		
		for(int i = 0; i <= height-1; i++){
			for(int c = 0; c <= width-1; c++){
				if(popMap.tileMap[c+x][i+y] != null){
					return true;
				}
			}
		}	
		//System.out.println("Does the Part Overlap? " + isAlreadyPopulated);
		return false;
	}
}
