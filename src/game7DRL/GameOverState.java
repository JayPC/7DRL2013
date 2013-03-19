package game7DRL;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOverState extends BasicGameState{
	int id;
	Rectangle deadRect;
	Image deadImage;
	public GameOverState(int sID){
		this.id = sID;
		deadImage = Game.loadedResources.deadImage;
		int centerX = deadImage.getWidth();
		int centerY = deadImage.getHeight();
		deadRect = new Rectangle(0,0,deadImage.getWidth(),deadImage.getHeight());
		deadRect.setCenterX(centerX);
		deadRect.setCenterY(centerY);
	}
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		g.drawImage(deadImage,deadRect.getX(),deadRect.getY());
		g.drawString("Points: " + GameplayState.gameWorld.points,deadRect.getX()+deadRect.getHeight()/2,deadRect.getY()+150);
		g.drawString("Spacebar to Restart the game",deadRect.getX()+deadRect.getHeight()/2,deadRect.getY()+170);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int deltaTime)
			throws SlickException {
		Input input = gc.getInput();
		
		if(input.isKeyPressed(Input.KEY_SPACE)){
			sbg.addState(new GameplayState(Game.state_gameplay.getID()));
			Game.state_gameplay.init(gc, sbg);
			sbg.enterState(Game.state_menu.getID());
		}
		// TODO Auto-generated method stub
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			gc.exit();
		}
		
	}
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}
}
