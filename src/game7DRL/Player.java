package game7DRL;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Player {
	Input input;
	float x, y;
	Vector2f location = new Vector2f(0,0);
	Vector2f offsetLocation = new Vector2f(0,0);
	Vector2f movementVector = new Vector2f(0,0);
	Vector2f currentDirection = new Vector2f(0,0);

	int currentMinDamage;
	int currentScaleDamage;
	int gunRange;
	
	int directionAmount;
	float magnitude;
	float speed;
	float rotation;
	boolean up,down,left,right;
	Rectangle playerRect;
	Rectangle xAxisRect;
	Rectangle yAxisRect;
	Line moveLine;
	Polygon playerCross;
	
	Image playerImage = Game.loadedResources.player;
	
	int acuracy;

	int hunger;
	int health;
	int battery;
	
	public Player(int x, int y){

		this.x = x;
		this.y = y;
		
		location = new Vector2f(x,y);
		offsetLocation = new Vector2f(x-Game.cam.cameraX,y-Game.cam.cameraY);
		movementVector = new Vector2f(0,0);
		currentDirection = new Vector2f(0,0);

		currentMinDamage = 150;
		currentScaleDamage = 300;
		gunRange = 400;
		
		directionAmount = 1;
		magnitude = 0f;
		speed = 5;
		rotation = 0;
		up = false;
		down = false;
		left = false;
		right = false;
		
		playerRect = new Rectangle(x,y,playerImage.getWidth(), playerImage.getHeight());
		xAxisRect = new Rectangle(x,y,playerImage.getWidth()-5, playerImage.getHeight()-5);
		yAxisRect = new Rectangle(x,y,playerImage.getWidth()-5, playerImage.getHeight()-5);
		moveLine = new Line(x-playerImage.getWidth(),y-playerImage.getHeight(),x-playerImage.getWidth(),y-playerImage.getHeight());
		playerCross = new Polygon();
		playerCross.setAllowDuplicatePoints(true);
		
		playerCross.addPoint(x, y);
		playerCross.addPoint(x+playerImage.getWidth(), y+playerImage.getHeight());
		playerCross.addPoint(x+playerImage.getWidth(), y);
		playerCross.addPoint(x, y+playerImage.getHeight());
		playerCross.addPoint(x, y);
		
		playerImage = Game.loadedResources.player;
		
		acuracy = 0;

		hunger = 10000;
		health = 5000;
		System.out.println("Setting Health at Max = " + health);
		battery = 0;
		
		boolean xOk = false;
		boolean yOk = false;
		while(xOk == false && yOk == false){
			System.out.println("TestingAgain");
			if(GameplayState.gameWorld.worldCollision(moveLine) && GameplayState.gameWorld.worldCollision(playerCross)){
				if(!xOk){
					x += (Math.floor(Math.random()*10))*32;
					xOk = !GameplayState.gameWorld.worldCollision(xAxisRect);
				}
				if(!yOk){
					y += (Math.floor(Math.random()*10))*32;
					yOk = !GameplayState.gameWorld.worldCollision(yAxisRect);
				}
			}else{
				xOk = true;
				yOk = true;
			}
			xAxisRect.setCenterX(x);
			xAxisRect.setCenterY(y);
			yAxisRect.setCenterX(x);
			yAxisRect.setCenterY(y);
			playerCross.setCenterX(x);
			playerCross.setCenterY(y);
			location.set(x, y);
		}
	}	
	public void update(GameContainer gc ,int deltaTime){
		input = gc.getInput();
		movement(deltaTime);
		shoot(deltaTime);
		rotation();
	}
	
	public void render(Graphics g){
		playerImage.setRotation(rotation+90);
		g.drawImage(playerImage, x-16-Game.cam.cameraX, y-16-Game.cam.cameraY);
		g.translate(-Game.cam.cameraX, -Game.cam.cameraY);
		g.draw(xAxisRect);
		g.draw(yAxisRect);
		g.draw(moveLine);
		g.draw(playerCross);
		g.resetTransform();
	}
	
	public void renderHUD(Graphics g){
		g.setColor(Color.white);
		//g.drawString("" + health, 0, Game.cam.height-50);
		g.drawString("" + x/32, 100, Game.cam.height-70);
		g.drawString("" + y/32, 200, Game.cam.height-70);
		g.drawString("" + x, 100, Game.cam.height-50);
		g.drawString("" + y, 200, Game.cam.height-50);
		g.drawString("" + health, Game.cam.width-150, 50);
		g.drawImage(Game.loadedResources.healthBack,Game.cam.width-205,15,Game.cam.width-95,40,0,0,120,24);
		for(int i = 0; i <= (health/50);i++){
			g.drawImage(Game.loadedResources.healthBar,i+Game.cam.width-200,20,i+Game.cam.width-199,35,0,0,1,50);
		}
	}

	public void shoot(int deltaTime){
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			GameplayState.gameWorld.playerFireBullet(deltaTime, this);
		}else{
		}
	}
	
	public void movement(int deltaTime){
		playerRect.setCenterX(x);
		playerRect.setCenterY(y);
		
		if(input.isKeyDown(Input.KEY_W)){
			movementVector.y = -directionAmount;
		}else{
		}
		if(input.isKeyDown(Input.KEY_A)){
			movementVector.x = -directionAmount;
		}else{
		}
		
		if(input.isKeyDown(Input.KEY_S)){
			movementVector.y = directionAmount;
		}else{
		}
			
		if(input.isKeyDown(Input.KEY_D)){
			movementVector.x = directionAmount;
		}else{
		}
			
		magnitude  = (float) Math.sqrt(Math.pow(movementVector.x, 2) +
				Math.pow(movementVector.y, 2));
		if(magnitude != 0){
			float xVel = (Math.abs(movementVector.x) * movementVector.x/magnitude)*(deltaTime/speed);
			float yVel = (Math.abs(movementVector.y) * movementVector.y/magnitude)*(deltaTime/speed);
			xAxisRect.setCenterX(x+xVel);
			xAxisRect.setCenterY(y);
			
			yAxisRect.setCenterX(x);
			yAxisRect.setCenterY(y+yVel);

			if(GameplayState.gameWorld.worldCollision(xAxisRect) != true){
				x+=xVel;
			}else{
				
			}
			if(GameplayState.gameWorld.worldCollision(yAxisRect) != true){
				y+=yVel;
			}
		}
		location.set(x,y);
		playerCross.setCenterX(x);
		playerCross.setCenterY(y);
		offsetLocation.set(x-Game.cam.cameraX,y-Game.cam.cameraY);
		Game.cam.updateCameraFromPlayer(location);
		movementVector.x = 0;
		movementVector.y = 0;
	}
	
	public void rotation(){
		float mX = input.getMouseX()+Game.cam.cameraX;
		float mY = input.getMouseY()+Game.cam.cameraY;
		
		float radiansToMouse = (float) Math.atan2(y-mY, x-mX);
		float degreesToMouse = 57.2957795f*radiansToMouse;

		currentDirection = new Vector2f((float)Math.cos(degreesToMouse), (float)Math.sin(degreesToMouse));
		
		rotation = degreesToMouse;

		//System.out.println(rotation);
	}
	
	public void damagePlayer(int deltaTime,int minDamage, int damageScale){
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

		//System.out.println("Still Damaging Player");
		health -= (Math.random()*damageScale)+minDamage;
	}
}
