package shadows;

import org.newdawn.slick.geom.Vector2f;

public class GameMath {

	public static double distance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) *(y2 - y1));
	}
	public static double distance(Vector2f location1, Vector2f location2) {
		return Math.sqrt((location1.x-location2.x)*(location1.x-location2.x)+
				(location1.y-location2.y)*(location1.y-location2.y));
	}
	
	public static float angleToPointDegrees(Vector2f location1, Vector2f location2){
		float radians = (float) Math.atan2(location1.y-location2.y, location1.x-location2.x);
		float degrees = 57.2957795f*radians;
		return degrees;
	}
	
	public static float angleToPointRadians(Vector2f location1, Vector2f location2){
		float radians = (float) Math.atan2(location1.y-location2.y, location1.x-location2.x);
		return radians;
	}
	
	public static float angleToPointRadians(float x, float y){
		float radians = (float) Math.atan2(0-y, 0-x);
		return radians;
	}
	
	/*	Returns a angle that measures the real angle from the origin to the
	 *  destination, positive for clockwise, negative for counter-clockwise.
	 * 
	 * @param	angle1	The origin angle
	 * @param	angle2	The destination angle
	 * @return		the real angle between the 2 angles
	 */
	public static float getRealAngle(Vector2f origin, Vector2f destination){
		float angle = (float) (Math.atan2( origin.x*destination.y - origin.y*destination.x,
									 	   origin.x*destination.x + origin.y*destination.y));
		
		return angle;
	}
	
	public static Vector2f pointFromDistanceAndAngle(float radius, float angle){
		float x = (float) (Math.cos(angle) * radius);
		float y = (float) (Math.sin(angle) * radius);
		return new Vector2f(x,y);
	}
	
	public static Vector2f pointFromDistanceAndAngleWithOffset(float radius, float angle, Vector2f origin){
		Vector2f num = pointFromDistanceAndAngle(radius,angle);
		return new Vector2f(num.x+origin.x,num.y+origin.y);
	}
	
}
