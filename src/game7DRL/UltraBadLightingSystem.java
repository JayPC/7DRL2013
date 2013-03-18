package game7DRL;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class UltraBadLightingSystem {
	int width = 800;
	int height = 800;
	final int imageWidth = 800;
	final int imageHeight = 800;
	int lightx = 0;
	int lighty = 0;
	
	
	public void render(Graphics g){
		g.setColor(Color.black);
		if(lightx >= 0){
			g.fillRect(0, 0, lightx, Game.cam.height);
		}
		if(lighty >= 0){
			g.fillRect(lightx, 0, imageWidth, lighty+5);
		}
		
		if(lighty+imageHeight <= Game.cam.height){
			g.fillRect(lightx, lighty+imageHeight-5, imageWidth, Game.cam.height-lighty+imageHeight);
		}
		
		if(lightx+imageWidth <= Game.cam.width){
			g.fillRect(lightx+imageWidth, 0, Game.cam.width-(lightx+imageWidth), Game.cam.height);
		}
		
		
		g.drawImage(Game.loadedResources.lightImage, lightx, lighty, width, height,0,0,200,200);
	}
	public void update(Vector2f lightLocation){
		lightx = (int) lightLocation.x;
		lighty = (int) lightLocation.y;
		width = lightx+800;
		height = lighty+800;
		
	}
}
