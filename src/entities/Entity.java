package entities;

import com.jogamp.opengl.GL2;
import core.GameState;
import utils.GeometryHelper;
import utils.Vector;

public class Entity {

	private final Vector com = new Vector(0, 0); // Center Of Mass
	private double radius = 0;
	private final Vector[] points;

	public Entity(double[][] points) {
		this.points = new Vector[points.length];
		for (int i = 0; i < points.length; i++) {
			this.points[i] = new Vector(points[i]);
		}
		init();
	}

	public Entity(Vector[] points) {
		this.points = points;
		init();
	}

	// Initialize Values of Interest
	private void init() {
		for (Vector point : points) {
			com.set(com.x + point.x, com.y + point.y);
		}
		com.set(com.x / points.length, com.y / points.length);

		for (Vector point : points) {
			radius = Math.max(GeometryHelper.distBetweenPoints(com, point), radius);
		}
	}

	public void translate(double x, double y) {
		com.set(com.x + x, com.y + y);
		for (Vector point : points) {
			point.set(point.x + x, point.y + y);
		}
	}

	public void resetTransform() {
		Vector vec = new Vector(0 - com.x, 0 - com.y);
		translate(vec.x, vec.y);
	}

	public void draw(GL2 gl, double[] color) {
		gl.glPushMatrix();
		gl.glColor3d(color[0], color[1], color[2]);
		gl.glBegin(GL2.GL_POLYGON);
		for (Vector point : points) {
			gl.glVertex2d(point.x, point.y);
		}
		gl.glEnd();
		gl.glPopMatrix();
	}

	public Vector getCom() {
		return com;
	}

	public double getRadius() {
		return radius;
	}

	public Vector[] getPoints() {
		return points;
	}

	public void handleCollision(GameState gs) {
	}
}
