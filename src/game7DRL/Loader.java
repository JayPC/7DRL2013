package game7DRL;

import java.io.IOException;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Loader {
	public Image titleImage;
	public Image GameOverImage;
	public Image peopleSprites;
	public Image floorSprites;
	public Image collisionSprites;
	public Image overSprites;
	public Image bulletLine;
	public Image healthBar;
	public Image healthBack;
	public Image deadImage;
	
	public Image player;
	public Image zombie_one;
	public Image lightImage;
	public Image[] zombies;
	public Image[] tileImages;
	
	
	public Loader() throws SlickException, IOException{
		loadResources();
	}
	
	public void loadResources() throws SlickException{
		 peopleSprites =  new Image("res/images/PeopleSprites.png");
		 floorSprites =  new Image("res/images/FloorSprites.png");
		 collisionSprites =  new Image("res/images/CollisionSprites.png");
		 overSprites =  new Image("res/images/PeopleSprites.png");
		 bulletLine =  new Image("res/images/FloorSprites.png");
		 lightImage =  new Image("res/images/InvertedLight.png");
		 titleImage =   new Image("res/images/Title.png");
		 healthBar =   new Image("res/images/HealthBar.png");
		 healthBack =   new Image("res/images/HealthBackground.png");
		 deadImage =   new Image("res/images/Dead.png");
		 
		 player = peopleSprites.getSubImage(0, 32, 32, 32);
		 zombies = new Image[4];
		 zombie_one = peopleSprites.getSubImage(0, 0, 32, 32);
		 zombies[0] = peopleSprites.getSubImage(0, 0, 32, 32);
		 zombies[1] = peopleSprites.getSubImage(32, 0, 32, 32);
		 zombies[2] = peopleSprites.getSubImage(64, 0, 32, 32);
		 zombies[3] = peopleSprites.getSubImage(96, 0, 32, 32);
		 
		 tileImages = new Image[50];
		 tileImages[0] = null;
		 tileImages[1] = floorSprites.getSubImage(0, 0, 32, 32);//grass
		 tileImages[2] = collisionSprites.getSubImage(0, 0, 32, 32);//UR Corner
		 tileImages[3] = collisionSprites.getSubImage(64, 0, 32, 32);//UL Corner
		 tileImages[4] = collisionSprites.getSubImage(0, 64, 32, 32);//BL Corner
		 tileImages[5] = collisionSprites.getSubImage(64, 64, 32, 32);//BR Corner
		 tileImages[6] = collisionSprites.getSubImage(32, 0, 32, 32);//HW Wall
		 tileImages[7] = collisionSprites.getSubImage(0, 32, 32, 32);//VW Corner
		 
		 tileImages[8] = floorSprites.getSubImage(32, 0, 32, 32);//TileFloor
		 
		 tileImages[9] = collisionSprites.getSubImage(128, 0, 32, 32);//U+
		 tileImages[10] = collisionSprites.getSubImage(128, 64, 32, 32);//D+
		 tileImages[11] = collisionSprites.getSubImage(96, 32, 32, 32);//L+
		 tileImages[12] = collisionSprites.getSubImage(160, 32, 32, 32);//R+
		 tileImages[13] = collisionSprites.getSubImage(128, 32, 32, 32);//C+

		 tileImages[14] = collisionSprites.getSubImage(0, 128, 32, 32);//Chair
		 tileImages[15] = collisionSprites.getSubImage(32, 128, 32, 32);//TV
		 tileImages[17] = collisionSprites.getSubImage(64, 128, 32, 32);//Table
		 tileImages[16] = collisionSprites.getSubImage(64, 160, 32, 32);//Lamp
		 
		 tileImages[18] = collisionSprites.getSubImage(64, 96, 32, 32);//Crate1
		 tileImages[19] = collisionSprites.getSubImage(0, 160, 32, 32);//Crate2
		 tileImages[20] = collisionSprites.getSubImage(32, 160, 32, 32);//Crate3

		 tileImages[21] = collisionSprites.getSubImage(0, 96, 32, 32);//Tombstone 1
		 tileImages[22] = collisionSprites.getSubImage(32, 96, 32, 32);//Tombstone 2
		 tileImages[23] = collisionSprites.getSubImage(32, 160, 32, 32);//chest
	}
}
