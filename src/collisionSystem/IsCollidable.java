package collisionSystem;

import org.newdawn.slick.geom.Shape;

public interface IsCollidable {
	public CollisionSource getCollisionType();
	public CollisionHostility getCollisionHostilityType();
	public Shape getCollisionShape();
	public void collisionReaction(CollisionSource cs, CollisionHostility ch, Shape sha);
}
