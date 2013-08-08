package shadows;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame{
	public static MapLoader loadedMapResources;
	public static Loader loadedResources;
	public static Camera cam;
	public static GameplayState state_gameplay;
	public static GameOverState state_gameover;
	public static MainMenuState state_menu;
	public static int currentControler1 = 0;
	public static boolean hasControler = false;
	Input input;
	public Game(String name) throws SlickException, IOException {
		super(name);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		input = gc.getInput();
		try {
			loadedResources = new Loader();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			loadedMapResources = new MapLoader();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i <= input.getControllerCount()-1; i++){
			if(input.getAxisCount(i) == 5){
				currentControler1 = i;
				hasControler = true;
			}
		}
		
		
		cam = new Camera(0, 0, gc.getWidth(), gc.getHeight());
		state_gameover = new GameOverState(2);
		state_gameplay = new GameplayState(1);
		state_menu = new MainMenuState(0);
		this.addState(state_menu);
		this.addState(state_gameover);
		this.addState(state_gameplay);
	}

}
