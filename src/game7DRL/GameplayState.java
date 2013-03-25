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
	UltraBadLightingSystem lighting = new UltraBadLightingSystem();
	int currentMapX = 0;
	int currentMapY = 0;
	int id;
	
	public static World gameWorld;
	
	//public static Map gameMap = new Map(500,50,20,20,0,0);
	
	public GameplayState(int sID){
		id = sID;
		try {
			gameWorld = new World(20,20);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		player1 = new Player(80, 80);
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		gameWorld.renderWorld(g);
		player1.render(g);
		lighting.render(g);
		player1.renderHUD(g);
		gameWorld.renderWorldHUD(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int deltaTime)
			throws SlickException {
		player1.update(gc, deltaTime);
		
		currentMapX = (int) Math.floor(player1.location.x/Defaults.MAP_WIDTH);
		currentMapY = (int) Math.floor(player1.location.y/Defaults.MAP_HEIGHT);

		lighting.update(new Vector2f(player1.offsetLocation.x-(lighting.imageWidth/2),
				player1.offsetLocation.y-(lighting.imageHeight/2)));
		try {
			gameWorld.updateWorld(deltaTime, player1, currentMapX, currentMapY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
