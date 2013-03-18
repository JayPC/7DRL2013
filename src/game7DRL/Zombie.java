package game7DRL;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Zombie {
	Image sprite;
	Vector2f location;
	Vector2f currentDirection;
	Vector2f intendedDirection;
	int health = 1000;
	
	float turnSpeed = 1f;
	float speed = 10f;
	int sightDistance = 300;
	int escapeDistance = 400;
	int attackDistance = 16;
	boolean beenSeen = false;
	boolean attacking = false;
	int maxWaitTime = 2000;
	int waitTime = 0;
	boolean waiting = false;
	int maxWanderTime = 2000;
	int wanderTime = 0;
	boolean wandering = true;
	int wanderDistance = 100;
	float currentAngle = 0.0f;
	float spriteRotation = 0;

	int minDamage = 10;
	int damageScale = 100;

	Rectangle zombieRect;
	Rectangle xAxisRect;
	Rectangle yAxisRect;
	

	public Zombie(float x, float y, int health, Image zombieImage){
		sprite = zombieImage;
		
		currentDirection = new Vector2f(0,0);
		intendedDirection = new Vector2f(0,0);
		location = new Vector2f(x,y);
		
		zombieRect = new Rectangle(x,y,sprite.getWidth(), sprite.getHeight());
		xAxisRect = new Rectangle(x,y,sprite.getWidth()-5, sprite.getHeight()-5);
		yAxisRect = new Rectangle(x,y,sprite.getWidth()-5, sprite.getHeight()-5);
	}
	public void render(Graphics g){
		
		sprite.setRotation(spriteRotation);
		g.drawImage(sprite,location.x-16-Game.cam.cameraX, location.y-16-Game.cam.cameraY);
	}
	
	public void update(int deltaTime, Vector2f playerLocation){
		if(location.distance(playerLocation) <= sightDistance && !beenSeen){
			beenSeen = true;
			//System.out.println("You've Been Spotted!");
		}
		
		if(location.distance(playerLocation) <= attackDistance && !attacking){
			attacking = true;
			//System.out.println("You've Been Spotted!");
		}else if(attacking && location.distance(playerLocation) >= attackDistance){
			attacking = false;
			//System.out.println("You've Been Spotted!");
		}

		if(beenSeen && location.distance(playerLocation) <= escapeDistance && !attacking){
			//System.out.println("Run!");
			intendedDirection.x = playerLocation.x - location.x;
			intendedDirection.y = playerLocation.y - location.y;
			seekAI(deltaTime);
		}else if(beenSeen && location.distance(playerLocation) <= escapeDistance && attacking){
			//System.out.println("Run!");
			intendedDirection.x = playerLocation.x - location.x;
			intendedDirection.y = playerLocation.y - location.y;
			//dont Move if in attack distance
			//seekAI(deltaTime);
		}else if(beenSeen && location.distance(playerLocation) >= escapeDistance){
			//System.out.println("You Escaped!");
			beenSeen = false;
		}else{
			if(waitTime >= 0 && wanderTime >= 0){
				waiting = true;
				wandering = false;
				waitTime-=deltaTime;
			}else if(waitTime >= 0 && wanderTime <= 0){
				waiting = true;
				wandering = false;
				waitTime-=deltaTime;
			}else if(waitTime <= 0 && wanderTime >= 0){
				waiting = false;
				wandering = true;
				wanderTime-=deltaTime;
			}else if(waitTime <= 0 && wanderTime <= 0){
				intendedDirection.x = (float) (Math.random()*2-1);
				intendedDirection.y = (float) (Math.random()*2-1);
				//System.out.println(location.x);
				//System.out.println(location.y);
				//System.out.println(intendedDirection.x);
				//System.out.println(intendedDirection.y);
				//System.out.println();
				wanderTime= (int) (Math.random()*maxWanderTime);;
				waitTime= (int) (Math.random()*maxWaitTime);
			}
			
			if(waiting){
				
			}
			if(wandering){
				seekAI(deltaTime);
			}
		}
		spriteRotation = (int) Math.toDegrees(GameMath.angleToPointRadians(new Vector2f(0,0), currentDirection)-80);
	}
	
	public void seekAI(int deltaTime){
		intendedDirection.normalise();
		currentDirection = new Vector2f((float)Math.cos(currentAngle), (float)Math.sin(currentAngle));
		float rotationAngle = (float) Math.toDegrees(GameMath.getRealAngle(currentDirection, intendedDirection));
		if(Math.floor(rotationAngle) > 1.5){
			currentAngle += (turnSpeed/deltaTime);
		}else if(Math.floor(rotationAngle)<-1.5){
			currentAngle -= (turnSpeed/deltaTime);
		}else{
			
		}
		
		if(currentAngle >= Math.PI){
			currentAngle = (float) -Math.PI;
		}else if(currentAngle < -Math.PI){
			currentAngle = (float) Math.PI;
		}
		
		float xVel = currentDirection.x*(deltaTime/speed);
		float yVel = currentDirection.y*(deltaTime/speed);
		
		xAxisRect.setCenterX(location.x+xVel);
		xAxisRect.setCenterY(location.y);
		
		yAxisRect.setCenterX(location.x);
		yAxisRect.setCenterY(location.y+yVel);
		
		if(GameplayState.gameWorld.worldCollision(xAxisRect) != true){
			location.x+=xVel;
		}
		if(GameplayState.gameWorld.worldCollision(yAxisRect) != true){
			location.y+=yVel;
		}

		zombieRect.setCenterX(location.x);
		zombieRect.setCenterY(location.y);
	}
	
	public void rotation(){
		
	}
	
	public void damageZombie(int deltaTime,int minDamage, int damageScale){
		float xVel = currentDirection.x*(deltaTime/speed);
		float yVel = currentDirection.y*(deltaTime/speed);
		
		xAxisRect.setCenterX(location.x-xVel);
		xAxisRect.setCenterY(location.y);
		
		yAxisRect.setCenterX(location.x);
		yAxisRect.setCenterY(location.y-yVel);
		
		if(GameplayState.gameWorld.worldCollision(xAxisRect) != true){
			location.x-=xVel;
		}
		if(GameplayState.gameWorld.worldCollision(yAxisRect) != true){
			location.y-=yVel;
		}
		
		beenSeen = true;
		health -= (Math.random()*damageScale)+minDamage;
	}
	
	public boolean checkCollision(Shape collisionShape){
		if(collisionShape.intersects(zombieRect)){
			return true;
		}
		return false;
	}
}
