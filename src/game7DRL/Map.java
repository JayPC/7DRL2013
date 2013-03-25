package game7DRL;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.GeomUtil;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Map {
	int timeOfDay;
	int populationDensity = 500;
	public Tile[][] tileMap;
	int zombiePopulation = 0;
	int mapX;
	int mapY;
	GeomUtil util = new GeomUtil();


	public ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	public ArrayList<Tile> collisionList = new ArrayList<Tile>();
	public ArrayList<Tile> bulletCollisionList = new ArrayList<Tile>();
	
	public Map(int buildingPopulation, int zombiePopulation, int width, int height, int sMapX, int sMapY){
		this.zombiePopulation = zombiePopulation;
		this.populationDensity = buildingPopulation;
		tileMap = new Tile[width][height];
		mapX = sMapX;
		mapY = sMapY;
	}
	
	public void initCollisionList(){
		for(int i = 0; i <= tileMap[0].length-1; i++){
			for(int c = 0; c <= tileMap.length-1; c++){
				if(tileMap[c][i] != null){
					if(tileMap[c][i].hasCollision){
						collisionList.add(tileMap[c][i]);
					
					}if(tileMap[c][i].hasBulletCollision){
						bulletCollisionList.add(tileMap[c][i]);
					}
				}
			}	
		}
	}
	
	public void initTileLocations(){
		for(int i = 0; i <= tileMap[0].length-1; i++){
			for(int c = 0; c <= tileMap.length-1; c++){
				if(tileMap[c][i] != null){
				tileMap[c][i].setNewLocation((c*Defaults.TILE_WIDTH)+(mapX*Defaults.MAP_WIDTH),
						(i*Defaults.TILE_HEIGHT)+(mapY*Defaults.MAP_HEIGHT));
				}
			}	
		}
	}
	
	
	public void renderMap(Graphics g){
		g.translate(-Game.cam.cameraX, -Game.cam.cameraY);
		for(int i = 0; i <= tileMap[0].length-1; i++){
			for(int c = 0; c <= tileMap.length-1; c++){
				if(tileMap[c][i] != null){
					tileMap[c][i].render(g);
				}
			}	
		}
		g.resetTransform();
	}
	
	
	public void update(int deltaTime, Player player){
		
	}
	
	public boolean checkMapRectCollision(Shape collisionShape){
		boolean testBoolean = false;

		for(int i = 0; i <= collisionList.size()-1; i++){
			if(collisionShape.intersects(collisionList.get(i).tileRect)){
				testBoolean = true;
			}
		}
		return testBoolean;
	}
	
	
	public Vector2f checkMapLineCollision(Line collisionLine){
		Vector2f shortestPoint = null;
		Vector2f test = null;
		Vector2f testPoint = null;
		List<Vector2f> intersectionList = new ArrayList<Vector2f>();

		for(int i = 0; i <= bulletCollisionList.size()-1; i++){
			if(util.intersect(bulletCollisionList.get(i).tileRect,collisionLine) != null){
					testPoint = (util.intersect(bulletCollisionList.get(i).tileRect,collisionLine).pt);
					intersectionList.add(testPoint);
			}
		}
		if(intersectionList.size() > 0){
			float shortestDistance = 100000;
			for(int i = 0; i <= intersectionList.size()-1; i++){
				test = intersectionList.get(i);
				if(collisionLine.getStart().distance(test) < shortestDistance){
					shortestDistance = collisionLine.getStart().distance(test);
					shortestPoint = test.copy();
				}
				
			}
			return shortestPoint;
		}
		return null;
	}
	
	public Line flipLine(Line inputLine){
		Line outputLine = new Line(0,0,0,0);
		outputLine.getStart().set(inputLine.getEnd());
		outputLine.getEnd().set(inputLine.getStart());
		return outputLine;
	}
	
	public void checkBulletCollision(Bullet addBullet){
		bulletList.add(addBullet);
	}
	
}
