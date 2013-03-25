package game7DRL;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;


public class Tile{
	int tileID;
	int x = 0;
	int y = 0;
	
	Image floorTile = null;
	Image collisionTile = null;
	Image aboveTile = null;
	
	boolean hasCollision = false;
	boolean hasBulletCollision = false;
	
	Rectangle tileRect;
	
	public Tile(int TileID, Image floor, Image collision, Image above, boolean hasColl, boolean hasBuColl){
		tileID = TileID;
		floorTile = floor;
		collisionTile = collision;
		aboveTile = above;
		hasCollision = hasColl;
		hasBulletCollision = hasBuColl;
		tileRect = new Rectangle(0,0,Defaults.TILE_WIDTH, Defaults.TILE_HEIGHT);
		
	}
	public Tile(Tile copyTile){
		this.tileID = copyTile.tileID;
		
		this.x = copyTile.x;
		this.y = copyTile.y;
		
		this.aboveTile = copyTile.aboveTile;
		this.collisionTile = copyTile.collisionTile;
		this.floorTile = copyTile.floorTile;
		
		this.hasBulletCollision = copyTile.hasBulletCollision;
		this.hasCollision = copyTile.hasCollision;
		
		tileRect = new Rectangle(0,0,Defaults.TILE_WIDTH, Defaults.TILE_HEIGHT);
	}
	
	public Tile(){
		
	}
	
    public void setNewLocation(int sX, int sY){
        this.x = sX;
        this.y = sY;
        tileRect.setX(sX);
        tileRect.setY(sY);
   }
	
	
	public void render(Graphics g){
		if(floorTile != null){
			g.drawImage(floorTile, x, y);
		}
		if(collisionTile != null){
			g.drawImage(collisionTile, x, y);
		}
		if(aboveTile != null){
			g.drawImage(aboveTile, x, y);
		}
	}
}
