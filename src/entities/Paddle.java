package entities;

import com.jogamp.opengl.GL2;
import core.GameState;
import utils.Vector;

public class Paddle extends Entity {

	// <-  |  ->
	// -1  0  1
	public int direction = 0;
	public double speed = 0.007;
	public double wallT;
	private double length;

	public Paddle(double length, double width) {
		super(createPoints(length, width));
		this.length = length;
	}

	private static Vector[] createPoints(double length, double width) {
		double folga = 0.05;
		Vector[] points = new Vector[4];

		points[0] = new Vector(-length / 2, -1 + folga + width);
		points[1] = new Vector(length / 2, -1 + folga + width);
		points[2] = new Vector(length / 2, -1 + folga);
		points[3] = new Vector(-length / 2, -1 + folga);

		return points;
	}

	@Override
	public void draw(GL2 gl, double color[]) {
		double p = getCom().x;
		if (p - length / 2 >= -1 + wallT && direction == -1 || p + length / 2 < 1 - wallT && direction == 1)
			translate(direction * speed, 0);

		gl.glPushMatrix();
		gl.glColor3d(color[0], color[1], color[2]);
		gl.glBegin(GL2.GL_POLYGON);
		for (Vector point : getPoints()) {
			gl.glVertex2d(point.x, point.y);
		}
		gl.glEnd();
		gl.glPopMatrix();
	}

	@Override
	public void resetTransform() {
		translate(0 - getCom().x, 0);
	}

	@Override
	public void handleCollision(GameState gs) {
		gs.score += 50;
		if(gs.score == 200) {
			gs.vidas = 3;
			gs.nivel = 2;
			gs.state = GameState.LEVEL_START;
		} else if(gs.score == 400) {
			gs.vidas = 3;
			gs.nivel = 3;
			gs.state = GameState.LEVEL_START;
		}
	}
}