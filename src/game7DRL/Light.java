package game7DRL;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Light{
	/** The position of the light */
	float x, y;
	/** The RGB tint of the light, alpha is ignored */
	Color tint; 
	/** The alpha value of the light, default 1.0 (100%) */
	float alpha;
	/** The amount to scale the light (use 1.0 for default size). */
	float scale = 1f;
	//original scale
	//private float scaleOrig;
	Image overlayImage = Game.loadedResources.lightImage;

	public Light(float x, float y, float scale, Color tint) {
		this.x = x;
		this.y = y;
		//this.scale = scaleOrig = scale;
		this.alpha = 1f;
		this.tint = tint;
	}

	public Light(float x, float y, float scale) {
		this(x, y, scale, Color.white);
	}
	public void render(Graphics g){
		g.drawImage(overlayImage, x, y);
	}

	public void update(float time) {
		//effect: scale the light slowly using a sin func
		//scale = scaleOrig + 1f + .5f*(float)Math.sin(time);
	}
}