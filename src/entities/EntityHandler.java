package entities;

import com.jogamp.opengl.GL2;
import core.Collision;
import core.CollisionEvent;
import utils.Vector;

import java.util.ArrayList;

public class EntityHandler {

	ArrayList<Entity> objs = new ArrayList<>();
	Ball ball;
	Collision globalCollider;
	GL2 context;

	public EntityHandler(GL2 context) {
		this.context = context;
		this.globalCollider = new Collision();
		init();
	}

	public void updateScene() {
		for (Entity it : objs) {
			CollisionEvent colTest = globalCollider.isOverlapping(ball, it);
			if (colTest != null) ball.handleCollision(colTest);
			it.draw(context);
			ball.draw(context);
		}
	}

	private void init() {
		walls();
		paddle();
		ball();
	}

	private void walls() {
		double t = 0.025;

		ArrayList<Vector> wall = new ArrayList<>();
		wall.add(new Vector(-1 + t, -1));
		wall.add(new Vector(-1, -1));
		wall.add(new Vector(-1, 1));
		wall.add(new Vector(-1 + t, 1 - t));
		objs.add(new Entity(wall.toArray(new Vector[0])));

		wall = new ArrayList<>();
		wall.add(new Vector(-1 + t, 1 - t));
		wall.add(new Vector(-1, 1));
		wall.add(new Vector(1, 1));
		wall.add(new Vector(1 - t, 1 - t));
		objs.add(new Entity(wall.toArray(new Vector[0])));

		wall = new ArrayList<>();
		wall.add(new Vector(1 - t, -1));
		wall.add(new Vector(1 - t, 1 - t));
		wall.add(new Vector(1, 1));
		wall.add(new Vector(1, -1));
		objs.add(new Entity(wall.toArray(new Vector[0])));
	}

	private void paddle() {
	}

	private void ball() {
		ball = new Ball(.025);
	}
}
