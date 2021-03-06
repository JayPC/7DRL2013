package game7DRL;

import java.util.ArrayList;
import java.util.Hashtable;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class World {
	//the Key is X_Y_ where the blanks are filled with the mapCoordinates
	public static Hashtable<String,Map> gameMaps= new Hashtable<String,Map>();
	int points = 0;
	int MapTileWidth;
	int MapTileHeight;
	public ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	int currentMapX = 0;
	int currentMapY = 0;
	int zombiePopulation = 30;
	int zombieOffScreenDistance = 800;
	int zombieCreationWindow = 200;
	int zombieListCullingDistance = 600;
	
	public ArrayList<Zombie> zombieList = new ArrayList<Zombie>();
	
	public World(int MapTileWidth, int MapTileHeight){
		 this.MapTileWidth = MapTileWidth;
		 this.MapTileHeight = MapTileHeight;
		 initWorld();
		 bulletList = new ArrayList<Bullet>();
	}
	
	public void initWorld(){
		
		for(int i = -2; i<= 2; i++){
			for(int c = -2; c <= 2; c++){
				gameMaps.put("X"+(c)+"Y"+(i),MapLoader.initMap(new Map(20,0,MapTileWidth,MapTileHeight,c,i)));
			}
		}
	}

	public void renderWorld(Graphics g){
		
		g.setDrawMode(Graphics.MODE_NORMAL);
		for(int i = currentMapY-2; i<= currentMapY+2; i++){
			for(int c = currentMapX-2; c <= currentMapX+2; c++){
				gameMaps.get("X"+(c)+"Y"+(i)).renderMap(g);
			}
		}
		
		renderZombies(g);
	}

	public void renderWorldHUD(Graphics g){
		g.drawString("Points! - " + points,0,0);
		
	}
	
	public void updateWorld(int deltaTime, Player player, int sCurrentMapX, int sCurrentMapY){
		currentMapX = sCurrentMapX;
		currentMapY = sCurrentMapY;
		
		for(int i = currentMapY-2; i<= currentMapY+2; i++){
			for(int c = currentMapX-2; c <= currentMapX+2; c++){
				if(gameMaps.get("X"+(c)+"Y"+(i)) != null){
					gameMaps.get("X"+(c)+"Y"+(i)).update(deltaTime, player);
				}else{
					gameMaps.put("X"+(c)+"Y"+(i),MapLoader.initMap(new Map(20,5,MapTileWidth,MapTileHeight,c,i)));
				}
			}
		}
		addZombies(player);
		updateZombies(deltaTime,player);
		updateBullets(deltaTime,player);
		checkZombiePlayerCollision(deltaTime,player);
		checkZombieBulletCollision(deltaTime,player);
	}
	
	public boolean worldCollision(Shape collisionShape){
		boolean test = false;
		for(int i = currentMapY-2; i<= currentMapY+2; i++){
			for(int c = currentMapX-2; c <= currentMapX+2; c++){
				if(gameMaps.get("X"+(c)+"Y"+(i)) != null && test == false){
					test = gameMaps.get("X"+(c)+"Y"+(i)).checkMapRectCollision(collisionShape);
				}else{
					
				}
			}
		}
		return test;
	}
	public Vector2f worldBulletCollision(Line collisionShape){
		Vector2f test = null;
		
		for(int i = currentMapY-2; i<= currentMapY+2; i++){
			for(int c = currentMapX-2; c <= currentMapX+2; c++){
				if(gameMaps.get("X"+(c)+"Y"+(i)) != null && test == null){
					test = gameMaps.get("X"+(c)+"Y"+(i)).checkMapLineCollision(collisionShape);
				}else{
					
				}
			}
		}
		return test;
	}
	
	public void playerFireBullet(int deltaTime, Player playerNum){
		Vector2f start = playerNum.location.copy();
		Vector2f end;
		end = GameMath.pointFromDistanceAndAngle(playerNum.gunRange, (float) (Math.toRadians(playerNum.rotation)-Math.PI));
		end = new Vector2f(start.x+end.x,start.y+end.y);
		Line bulletT = new Line(start, end);
		Vector2f test = null;
		
		if((test = worldBulletCollision(bulletT)) !=null){
			end = test.copy();
			bulletT.getEnd().set(end);
		}else{
			
		}
		bulletList.add(new Bullet(bulletT));
	}
	
	public void renderZombies(Graphics g){
		for(int i=0; i<= zombieList.size()-1; i++){
			zombieList.get(i).render(g);
		}
		for(int i=0; i <= bulletList.size()-1; i++){
			g.setColor(Color.white);
			bulletList.get(i).render(g);
		}
	}
	public void addZombies(Player player){
		if(zombieList.size() <= zombiePopulation){
			float num1 = (float) ((Math.random()*(zombieCreationWindow + zombieOffScreenDistance)+player.location.x) - ((zombieCreationWindow + zombieOffScreenDistance)/2));
			float num2 =  (float) ((Math.random()*(zombieCreationWindow + zombieOffScreenDistance)+player.location.y) - ((zombieCreationWindow + zombieOffScreenDistance)/2));
			Vector2f newZombieLocation = new Vector2f(num1,num2);
			while(newZombieLocation.distance(player.location) <= (zombieOffScreenDistance/2)){
				num1 = (float) ((Math.random()*(zombieCreationWindow + zombieOffScreenDistance)+player.location.x) - ((zombieCreationWindow + zombieOffScreenDistance)/2));
				num2 =  (float) ((Math.random()*(zombieCreationWindow + zombieOffScreenDistance)+player.location.y) - ((zombieCreationWindow + zombieOffScreenDistance)/2));
				newZombieLocation.set(num1, num2);
			}
			zombieList.add(new Zombie(num1, num2, 100, Game.loadedResources.zombies[(int) Math.floor(Math.random()*4)]));
		}
	}
	
	public void updateZombies(int deltaTime, Player player){
		for(int i=0; i<= zombieList.size()-1; i++){
			Zombie temp = zombieList.get(i);
			temp.update(deltaTime, player.location);
			
			if(temp.health <=0){
				//System.out.println("Cull those Zombies");
				zombieList.remove(i);
				points+=10;
			} 
			
			if(player.location.distance(temp.location) >= zombieListCullingDistance){
				//System.out.println("Cull those Zombies");
				zombieList.remove(i);
			} 
		}
	}public void updateBullets(int deltaTime, Player player){
		for(int c=0; c <= bulletList.size()-1; c++){
			Bullet tempBullet = bulletList.get(c);
			
			if(tempBullet.liveTime <= 0){
				//System.out.println("DIE BULLET!");
				bulletList.remove(c);
			}else{
				tempBullet.update(deltaTime);
			}
		}
	}
	public void checkZombieBulletCollision(int deltaTime, Player player){
		for(int i=0; i<= zombieList.size()-1; i++){
			Zombie temp = zombieList.get(i);
			for(int c=0; c <= bulletList.size()-1; c++){
				Bullet tempBullet = bulletList.get(c);
				if(temp.checkCollision(tempBullet.bulletT)){
					temp.damageZombie(deltaTime, player.currentMinDamage, player.currentScaleDamage);
				}
			}
		}
	}
	public void checkZombiePlayerCollision(int deltaTime, Player player){
		for(int i=0; i<= zombieList.size()-1; i++){
			Zombie temp = zombieList.get(i);
			if(temp.checkCollision(player.playerRect)){
				player.damagePlayer(deltaTime,temp.minDamage, temp.damageScale);
			}
		}
	}
	
	
	
}










