package utils;

import utils.Vector;

public class CollisionEvent {

	private final Vector originalDirection;
	private final Vector obstacleAngle;

	public CollisionEvent(Vector originalDirection, Vector obstacleAngle) {
		this.originalDirection = originalDirection;
		this.obstacleAngle = obstacleAngle;
	}

	public Vector getOriginalDirection() {
		return originalDirection;
	}

	public Vector getObstacleAngle() {
		return obstacleAngle;
	}
}
