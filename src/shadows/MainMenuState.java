package shadows;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState{
	int id;
	Rectangle titleRect;
	Image titleImage;
	public MainMenuState(int sID){
		id = sID;
		titleImage = Game.loadedResources.titleImage;
		int centerX = titleImage.getWidth();
		int centerY = titleImage.getHeight();
		titleRect = new Rectangle(0,0,titleImage.getWidth(),titleImage.getHeight());
		titleRect.setCenterX(centerX);
		titleRect.setCenterY(centerY);
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.drawImage(titleImage,titleRect.getX(),titleRect.getY());
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int deltaTime)
			throws SlickException {
		Input input = gc.getInput();
		if(input.isKeyDown(Input.KEY_ESCAPE)){
				gc.exit();
		}
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			System.out.println("Entering State: " + Game.state_gameover.getID());
			sbg.enterState(Game.state_gameplay.getID());
		}
	}

	@Override
	public int getID() {
		return id;
	}

}
