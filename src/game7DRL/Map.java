package game7DRL;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.GeomUtil;
import org.newdawn.slick.geom.GeomUtil.HitResult;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tests.GeomTest;
import org.newdawn.slick.tests.GeomUtilTest;

public class Map {
	int timeOfDay;
	int populationDensity = 500;
	public Tile[][] tileMap;
	int zombiePopulation = 0;
	int mapX;
	int mapY;
	GeomUtil util = new GeomUtil();

	Line testLine = new Line(0,0,0,0);
	
	public ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	
	public Map(int buildingPopulation, int zombiePopulation, int width, int height, int sMapX, int sMapY){
		this.zombiePopulation = zombiePopulation;
		this.populationDensity = buildingPopulation;
		tileMap = new Tile[width][height];
		mapX = sMapX;
		mapY = sMapY;
	}
	
	public void renderMap(Graphics g){
		for(int i = 0; i <= tileMap[0].length-1; i++){
			for(int c = 0; c <= tileMap.length-1; c++){
				if(tileMap[c][i] != null){
					tileMap[c][i].render(g, c*32-Game.cam.cameraX+(mapX*GameplayState.MAP_WIDTH), i*32-Game.cam.cameraY+(mapY*GameplayState.MAP_HEIGHT));
				}
			}	
		}
		g.draw(testLine);
	}
	
	
	public void update(int deltaTime, Player player){
		
	}
	
	public boolean checkMapRectCollision(Shape collisionShape){
		int collWidth;
		int collHeight;
		int rectX;
		int rectY;
		boolean testBoolean = false;
		
		for(int i = 0; i <= tileMap[0].length-1; i++){
			for(int c = 0; c <= tileMap.length-1; c++){
				if(tileMap[c][i] != null){
					if(tileMap[c][i].hasCollision){
						collWidth = tileMap[c][i].getWidth();
						collHeight = tileMap[c][i].getHeight();
						
						rectX = mapX*GameplayState.MAP_WIDTH+(c*collWidth);
						rectY = mapY*GameplayState.MAP_HEIGHT+(i*collHeight);
							
						Rectangle temp = new Rectangle(rectX, rectY, collWidth,collHeight);
						
						if(collisionShape.intersects(temp)){
							testBoolean = true;
						}
					}
				}
			}
		}
		return testBoolean;
	}
	
	public Vector2f checkMapLineCollision(Line collisionLine){
		int collWidth;
		int collHeight;
		int rectX;
		int rectY;
		Vector2f testPoint = null;
		List<Vector2f> intersectionList = new ArrayList<Vector2f>();
		for(int i = 0; i <= tileMap[0].length-1; i++){
			for(int c = 0; c <= tileMap.length-1; c++){
				if(tileMap[c][i] != null){
					if(tileMap[c][i].hasBulletCollision){
						collWidth = tileMap[c][i].getWidth();
						collHeight = tileMap[c][i].getHeight();
						rectX = mapX*GameplayState.MAP_WIDTH+(c*collWidth);
						rectY = mapY*GameplayState.MAP_HEIGHT+(i*collHeight);
						Rectangle temp = new Rectangle(rectX, rectY, collWidth,collHeight);
						
						if(util.intersect(temp,collisionLine) != null){
							testPoint = (util.intersect(temp,collisionLine).pt);
							intersectionList.add(testPoint);
						}
					}
				}
			}
		}
		Vector2f shortestPoint = null;
		Vector2f test = null;
		
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
