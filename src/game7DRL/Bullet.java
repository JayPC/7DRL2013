package game7DRL;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

public class Bullet {
	Line bulletT;
	Vector2f start;
	Vector2f end;
	float deltaMax = 100;
	float liveTime = 1000;
	float decayAmmount = 1000;
	float angle = 0.0f;
	
	public Bullet(Vector2f start, float distance, float angle){
		end = GameMath.pointFromDistanceAndAngle(distance, angle);
		end = new Vector2f(start.x+end.x,start.y+end.y);
		bulletT = new Line(start, end);
		this.start = start;
	}
	
	public void bulletCollision(int deltaTime,int minDamage, int damageScale, Zombie nearestZombie){
		nearestZombie.damageZombie(deltaTime, minDamage, damageScale);
	}
	
	public void render(Graphics g){
		g.drawLine(start.x-Game.cam.cameraX, start.y-Game.cam.cameraY, end.x-Game.cam.cameraX, end.y-Game.cam.cameraY);
	}
	
	public void update(float deltaTime){
		/*HighDeltaTime = LowDecayAmmount
		 *LowDeltaTime = HighDecayAmmount
		 *deltaTime = 20 LowTimePerFrame
		 *decayTime = 1000/20 = 50 decay per update
		 *deltaTime = 80 HighTimePerFrame
		 *decayTime = 1000/80 = 12.5 decay per update
		 */
		//System.out.println(deltaTime);
		deltaTime /= 100;
		if(deltaTime >= 5){
			liveTime-=(decayAmmount/5);
		}else{
			liveTime-=(decayAmmount/deltaTime);
		}
	}
}
