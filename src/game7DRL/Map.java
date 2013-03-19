package game7DRL;

import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Map {
	int timeOfDay;
	int populationDensity = 0;
	public Tile[][] tileMap;
	int zombiePopulation = 0;
	int mapX;
	int mapY;
	
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
	
	public boolean checkCollision(Rectangle oneAxisRect){
		boolean testBoolean = false;
		for(int i = 0; i <= tileMap[0].length-1; i++){
			for(int c = 0; c <= tileMap.length-1; c++){
				if(tileMap[c][i] != null){
					if(tileMap[c][i].collisionTile !=null){
						if(tileMap[c][i].hasCollision){
							int collWidth = tileMap[c][i].collisionTile.getWidth();
							int collHeight = tileMap[c][i].collisionTile.getHeight();
							int mapOffsetX = mapX*GameplayState.MAP_WIDTH;
							int mapOffsetY = mapY*GameplayState.MAP_HEIGHT;
							Rectangle temp = new Rectangle((c*collWidth)+mapOffsetX,
									(i*collHeight)+mapOffsetY,
									collWidth,
									collHeight);
							
							if(oneAxisRect.intersects(temp)){
								testBoolean = true;
								return testBoolean;
							}
						}
					}
				}
			}
		}
		//System.out.println("" + testBoolean);
		return testBoolean;
	}
	public void checkBulletCollision(Bullet addBullet){
		bulletList.add(addBullet);
	}
	
}
