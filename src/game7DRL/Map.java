package game7DRL;

import java.util.ArrayList;
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
	GeomUtil util;
	
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
	}
	
	
	public void update(int deltaTime, Player player){
		
	}
	
	public boolean checkMapRectCollision(Shape collisionShape){
		int collWidth;
		int collHeight;
		int rectX;
		int rectY;
		boolean testBoolean = false;
		//System.out.print("mapX: "+mapX+" MapY: "+mapY);
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
		//System.out.println("" + testBoolean);
		return testBoolean;
	}
	
	public Vector2f checkMapLineCollision(Line collisionShape){
		int collWidth;
		int collHeight;
		int rectX;
		int rectY;
		boolean testBoolean = false;
		//System.out.print("mapX: "+mapX+" MapY: "+mapY);
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
		//System.out.println("" + testBoolean);
		return null;
	}
	
	public void checkBulletCollision(Bullet addBullet){
		bulletList.add(addBullet);
	}
	
}
