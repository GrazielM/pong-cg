package entities;

import com.jogamp.opengl.GL2;
import core.GameState;
import utils.CollisionEvent;
import utils.Vector;

public class Ball extends Entity {

	public Vector direction = new Vector(1, 0.5);
	public double speed = 0.02;
	private static final int RESOLUTION = 16;
	private GameState gs;

	public Ball(double radius, GameState gs) {
		super(createPoints(radius));
		this.gs = gs;
	}

	private static Vector[] createPoints(double radius) {
		Vector[] points = new Vector[RESOLUTION];

		for (int i = 0; i < RESOLUTION; i++) {
			points[i] = new Vector(
					radius * Math.cos(Math.toRadians(360.0 / RESOLUTION * i)),
					radius * Math.sin(Math.toRadians(360.0 / RESOLUTION * i))
			);
		}
		return points;
	}

	@Override
	public void draw(GL2 gl, double[] color) {
		if (gs.nivel == 2) speed = .025;
		if (gs.nivel == 3) speed = .03;

		if (gs.state == GameState.ACTIVE) {
			direction.x += (0.5 - Math.random()) * .003;
			direction.y += (0.5 - Math.random()) * .003;
			translate(direction.x * speed, direction.y * speed);
		}

		gl.glPushMatrix();
		gl.glColor3d(0.8, 0, 0);
		gl.glBegin(GL2.GL_POLYGON);
		for (Vector point : getPoints()) {
			gl.glVertex2d(point.x, point.y);
		}
		gl.glEnd();
		gl.glPopMatrix();
	}

	public void handleCollision(CollisionEvent colEvt) {
		Vector ballDirection = colEvt.getOriginalDirection();
		Vector wallDirection = colEvt.getObstacleAngle();

		direction.x = ballDirection.x - 2 * (ballDirection.x * wallDirection.y + ballDirection.y * wallDirection.x) * wallDirection.y;
		direction.y = ballDirection.y - 2 * (ballDirection.x * wallDirection.y + ballDirection.y * wallDirection.x) * wallDirection.x;
	}
}
