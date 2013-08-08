package shadows;

import org.newdawn.slick.geom.Vector2f;

public class Camera {
	public int cameraX, cameraY;
	public int width, height;
	public int edgeThresh = 500;
	public Camera(int sx, int sy, int swidth, int sheight){
		cameraX = sx;
		cameraY = sy;
		width = swidth;
		height = sheight;
		if(width > height){
			edgeThresh = height/2;
		}else{
			edgeThresh = width/2;
		}
		
		
	}
	
	public void updateCameraFromPlayer(Vector2f playerLocation){
		
		if(playerLocation.y <= cameraY + edgeThresh){
			cameraY = (int) (playerLocation.y - edgeThresh);
		}else if(playerLocation.y >= cameraY + height - edgeThresh){
			cameraY = (int)(playerLocation.y - height + edgeThresh);
		}
		
		
		if(playerLocation.x <= cameraX + edgeThresh){
			cameraX = (int) (playerLocation.x - edgeThresh);
		}else if(playerLocation.x >= cameraX + width - edgeThresh){
			cameraX = (int)(playerLocation.x - width + edgeThresh);
		}
	}
	
}
