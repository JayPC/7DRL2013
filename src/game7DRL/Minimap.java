package game7DRL;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Minimap {
	public static void minimap(Map mip,Graphics g){
		for(int i = 0; i <= mip.tileMap[0].length-1; i++){
			for(int c = 0; c <= mip.tileMap.length-1; c++){
				if(mip.tileMap[c][i] != null){
					switch(mip.tileMap[c][i].tileID){
					case 0:
						break;
					case 1:
						g.setColor(Color.green);
						g.fill(new Rectangle(c,i,1,1));
						break;
					case 2:
						g.setColor(Color.orange);
						g.fill(new Rectangle(c,i,1,1));
						break;
					case 3:
						g.setColor(Color.orange);
						g.fill(new Rectangle(c,i,1,1));
						break;
					case 4:
						g.setColor(Color.orange);
						g.fill(new Rectangle(c,i,1,1));
						break;
					case 5:
						g.setColor(Color.orange);
						g.fill(new Rectangle(c,i,1,1));
						break;
					case 6:
						g.setColor(Color.orange);
						g.fill(new Rectangle(c,i,1,1));
						break;
					case 7:
						g.setColor(Color.orange);
						g.fill(new Rectangle(c,i,1,1));
						break;
					case 8:
						g.setColor(Color.red);
						g.fill(new Rectangle(c,i,1,1));
						break;
					}
				}
			}	
		}
	}
}
