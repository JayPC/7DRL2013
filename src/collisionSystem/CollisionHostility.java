package collisionSystem;

public enum CollisionHostility {
	FRIENDLY, //affects everything but player
	NEUTRAL, //affects both
	HOSTILE, //affects everything but Zombies
	PASSIVE; //affects everything but usually with no effect(Walls)
}
