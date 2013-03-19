package game7DRL;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameplayState extends BasicGameState{
	Player player1;
	int baseMaps;
	final static int MAP_WIDTH = 640;
	final static int MAP_HEIGHT = 640;
	UltraBadLightingSystem lighting = new UltraBadLightingSystem();
	int currentMapX = 0;
	int currentMapY = 0;
	Minimap mip;
	int id;
	
	public static World gameWorld;
	
	//public static Map gameMap = new Map(500,50,20,20,0,0);
	
	public GameplayState(int sID){
		id = sID;
		gameWorld = new World(20,20);
		player1 = new Player(80, 80);
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		//gameMap.renderMap(g,0,0);
		gameWorld.renderWorld(g);
		player1.render(g);
		//lighting.render(g);
		player1.renderHUD(g);
		gameWorld.renderWorldHUD(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int deltaTime)
			throws SlickException {
		player1.update(gc, deltaTime);
		//gameMap.update(deltaTime, player1);
		
		currentMapX = (int) Math.floor(player1.location.x/MAP_WIDTH);
		currentMapY = (int) Math.floor(player1.location.y/MAP_HEIGHT);

		lighting.update(new Vector2f(player1.offsetLocation.x-(lighting.imageWidth/2),
				player1.offsetLocation.y-(lighting.imageHeight/2)));
		gameWorld.updateWorld(deltaTime, player1, currentMapX, currentMapY);
		
		if(player1.health <= 0){
			System.out.println("Entering State: "+ Game.state_gameover.getID());
			sbg.enterState(Game.state_gameover.getID());
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			gc.exit();
		}
	}

	@Override
	public int getID() {
		return id;
	}

}
