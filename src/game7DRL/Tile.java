package game7DRL;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Tile {
	int tileID;
	int height;
	int width;
	Image floorTile = null;
	Image collisionTile = null;
	Image aboveTile = null;
	boolean hasCollision = false;
	boolean showAboveTile = true;
	
	public Tile(int TileID, Image floor, Image collision, Image above, boolean hasColl){
		tileID = TileID;
		floorTile = floor;
		collisionTile = collision;
		aboveTile = above;
		hasCollision = hasColl;
		width = floorTile.getWidth();;
		height = floorTile.getHeight();
		
	}
	public Tile(){
		
	}
	public int getWidth(){
		return width;
		
	}public int getHeight(){
		return height;
		
	}
	
	public void setTile(int TileID, Image floor, Image collision, Image above, boolean hasColl){
		tileID = TileID;
		floorTile = floor;
		collisionTile = collision;
		aboveTile = above;
		hasCollision = hasColl;
	}
	
	
	public void render(Graphics g, int xPos, int yPos){
		if(floorTile != null){
			g.drawImage(floorTile, xPos, yPos);
		}
		if(collisionTile != null){
			g.drawImage(collisionTile, xPos, yPos);
		}
		if(aboveTile != null){
			g.drawImage(aboveTile, xPos, yPos);
		}
	}
}
