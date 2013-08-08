package shadows;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import collisionSystem.CollisionHostility;
import collisionSystem.CollisionSource;
import collisionSystem.IsCollidable;

public class Player implements IsCollidable{
	Input input;
	float x, y;
	Vector2f location = new Vector2f(0,0);
	Vector2f movementVector = new Vector2f(0,0);
	Vector2f currentDirection = new Vector2f(0,0);

	boolean up,down,left,right;
	boolean triggerDown = false;
	boolean xCollision = false;
	boolean yCollision = false;
	
	float magnitude;
	float rotation;
	
	Rectangle playerRect;
	Rectangle xAxisRect;
	Rectangle yAxisRect;
	Line moveLine;
	
	Image playerImage = Game.loadedResources.player;
	
	int acuracy;

	int hunger;
	int health;
	int battery;
	
	public Player(int x, int y){

		this.x = x;
		this.y = y;
		
		location = new Vector2f(x,y);
		movementVector = new Vector2f(0,0);
		currentDirection = new Vector2f(0,0);

		rotation = 0;
		magnitude = 0;
		up = false;
		down = false;
		left = false;
		right = false;
		triggerDown = false;
		
		
		playerRect = new Rectangle(x,y,playerImage.getWidth(), playerImage.getHeight());
		xAxisRect = new Rectangle(x,y,playerImage.getWidth()-5, playerImage.getHeight()-5);
		yAxisRect = new Rectangle(x,y,playerImage.getWidth()-5, playerImage.getHeight()-5);
		moveLine = new Line(x-playerImage.getWidth(),y-playerImage.getHeight(),x-playerImage.getWidth(),y-playerImage.getHeight());

		
		playerImage = Game.loadedResources.player;
		
		acuracy = 0;

		hunger = 10000;
		health = 5000;
		battery = 0;
	}	
	public void update(GameContainer gc ,int deltaTime){
		input = gc.getInput();
		movement(deltaTime);
		rotation();
	}
	
	public void render(Graphics g){
		playerImage.setRotation(rotation+90);
		g.drawImage(playerImage, x-16-Game.cam.cameraX, y-16-Game.cam.cameraY);
		g.translate(-Game.cam.cameraX, -Game.cam.cameraY);
		g.draw(xAxisRect);
		g.draw(yAxisRect);
		g.draw(moveLine);
		g.resetTransform();
	}
	
	public void renderHUD(Graphics g){
		g.drawImage(Game.loadedResources.healthBack,Game.cam.width-205,15,Game.cam.width-95,40,0,0,120,24);
		for(int i = 0; i <= (health/50);i++){
			g.drawImage(Game.loadedResources.healthBar,i+Game.cam.width-200,20,i+Game.cam.width-199,35,0,0,1,50);
		}
	}
	
	public void movement(int deltaTime){
		playerRect.setCenterX(x);
		playerRect.setCenterY(y);
		
		if(input.isKeyDown(Input.KEY_W)){
			movementVector.y = -1;
		}else{
		}
		if(input.isKeyDown(Input.KEY_A)){
			movementVector.x = -1;
		}else{
		}
		
		if(input.isKeyDown(Input.KEY_S)){
			movementVector.y = 1;
		}else{
		}
		if(input.isKeyDown(Input.KEY_D)){
			movementVector.x = 1;
		}else{
		}
		magnitude  = (float) Math.sqrt(Math.pow(movementVector.x, 2) +
				Math.pow(movementVector.y, 2));
		if(magnitude != 0){
			float xVel = (movementVector.x/magnitude)*(Defaults.PLAYER_SPEED);
			float yVel = (movementVector.y/magnitude)*(Defaults.PLAYER_SPEED);
			xAxisRect.setCenterX(x+xVel);
			xAxisRect.setCenterY(y);
			
			yAxisRect.setCenterX(x);
			yAxisRect.setCenterY(y+yVel);

			if(xCollision == false){
				x+=xVel;
			}
			if(yCollision == false){
				y+=yVel;
			}
		}
		
		location.set(x,y);
		
		playerRect.setCenterX(x);
		playerRect.setCenterY(y);
		
		Game.cam.updateCameraFromPlayer(location);
		movementVector.x = 0;
		movementVector.y = 0;
	}
	
	public void rotation(){
		float mX = input.getMouseX()+Game.cam.cameraX;
		float mY = input.getMouseY()+Game.cam.cameraY;
		float degrees = 0;
		float radiansToMouse = (float) Math.atan2(y-mY, x-mX);
		degrees = 57.2957795f*radiansToMouse;
		
		currentDirection = new Vector2f((float)Math.cos(degrees), (float)Math.sin(degrees));
		
		rotation = degrees;

		//System.out.println(rotation);
	}
	@Override
	public CollisionSource getCollisionType() {
		return CollisionSource.PLAYER;
	}
	@Override
	public CollisionHostility getCollisionHostilityType() {
		return CollisionHostility.FRIENDLY;
	}
	@Override
	public Shape getCollisionShape() {
		return this.playerRect;
	}
	@Override
	public void collisionReaction(CollisionSource cs, CollisionHostility ch, Shape sha) {
		if(cs == CollisionSource.WALL){
			xCollision = sha.intersects(xAxisRect);
			yCollision = sha.intersects(yAxisRect);
		}
	}
}
