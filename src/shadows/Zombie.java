package shadows;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Zombie {
	Image sprite;
	Vector2f location;
	Vector2f currentDirection;
	Vector2f intendedDirection;
	int health = 1000;
	
	float turnSpeed = 0.7f;
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

	Line moveLine;
	Polygon zombieCross;
	
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
		
		moveLine = new Line(x-sprite.getWidth(),y-sprite.getHeight(),x-sprite.getWidth(),y-sprite.getHeight());
		zombieCross = new Polygon();
		zombieCross.setAllowDuplicatePoints(true);
		
		zombieCross.addPoint(x-1, y-1);
		zombieCross.addPoint(x+sprite.getWidth()+1, y+sprite.getHeight()+1);
		zombieCross.addPoint(x+sprite.getWidth()+1, y-1);
		zombieCross.addPoint(x-1, y+sprite.getHeight()+1);
		zombieCross.addPoint(x, y);
	}	
	
	public void render(Graphics g){
		
		sprite.setRotation(spriteRotation);
		g.drawImage(sprite,location.x-16-Game.cam.cameraX, location.y-16-Game.cam.cameraY);
	}
	
	public void update(int deltaTime, Vector2f playerLocation){
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
		zombieCross.setCenterX(location.x);
		zombieCross.setCenterY(location.y);
	}
	
	public void rotation(){
		
	}
}
