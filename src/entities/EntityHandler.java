package entities;

import com.jogamp.opengl.GL2;
import core.Collision;
import core.GameState;
import graphics.GUI;
import graphics.Text;
import utils.CollisionEvent;
import utils.MovementBuffer;
import utils.Vector;

import java.util.ArrayList;

public class EntityHandler {

	GUI gui;
	Ball ball;
	GL2 context;
	GameState gs;
	Paddle paddle;
	Text textRender;
	Entity obstacle;
	Entity killPlane;
	Collision globalCollider;
	MovementBuffer globalMovBuff;
	ArrayList<Entity> objs = new ArrayList<>();

	double[] ballColor = new double[]{0.8, 0, 0};
	double[] wallColor = new double[]{0, 0.5, 1};
	double[] paddleColor = new double[]{1, 1, 1};

	public EntityHandler(GL2 context, GameState gs) {
		this.gs = gs;
		this.gui = new GUI(gs);
		this.context = context;
		this.textRender = new Text(gs);
		globalMovBuff = new MovementBuffer();
		this.globalCollider = new Collision();
		init();
	}

	public void updateScene(MovementBuffer movBuff) {

		switch (gs.state) {
			case GameState.ACTIVE:
				gui.draw();
				for (Entity it : objs) {

					if (gs.nivel < 2 && it == obstacle) continue;
					paddle.direction = movBuff.paddleDirection;
					paddle.wallT = globalMovBuff.wallThickness;
					CollisionEvent colTest = globalCollider.isOverlapping(ball, it);

					if (colTest != null) {
						ball.handleCollision(colTest);
						it.handleCollision(gs);
					}

					it.draw(context, wallColor);
				}

				ball.draw(context, ballColor);
				paddle.draw(context, paddleColor);
				break;

			case GameState.SUSPENDED:
				for (Entity it : objs) {
					if (gs.nivel < 2 && it == obstacle) continue;
					it.draw(context, wallColor);
				}
				ball.draw(context, ballColor);
				paddle.draw(context, paddleColor);
				textRender.desenhaTextos(Text.SUSPENDED);
				break;

			case GameState.SPLASH:
				textRender.desenhaTextos(Text.SPLASH);
				break;

			case GameState.LEVEL_START:
				ball.resetTransform();
				paddle.resetTransform();

				for (Entity it : objs) {
					if (gs.nivel < 2 && it == obstacle) continue;
					it.draw(context, wallColor);
				}

				ball.draw(context, ballColor);
				paddle.draw(context, paddleColor);
				textRender.desenhaTextos(Text.LEVEL_START);
				break;

			case GameState.GAME_OVER:
				textRender.desenhaTextos(Text.GAME_OVER);
				break;
		}
	}

	private void init() {
		walls();
		obstacle();
		paddle();
		ball();
	}

	private void obstacle() {
		double w = 1, h = 0.2;

		Vector[] wall = new Vector[4];
		wall[0] = new Vector(0 - w / 2, 0.3);
		wall[1] = new Vector(0, 0.3 + h / 2);
		wall[2] = new Vector(0 + w / 2, 0.3);
		wall[3] = new Vector(0, 0.3 - h / 2);
		obstacle = new Entity(wall);
		objs.add(obstacle);
	}

	private void walls() {
		double t = 0.025;
		globalMovBuff.wallThickness = t;

		Vector[] wall = new Vector[4];
		wall[0] = new Vector(-1 + t, -1);
		wall[1] = new Vector(-1, -1);
		wall[2] = new Vector(-1, 1);
		wall[3] = new Vector(-1 + t, 1 - t);
		objs.add(new Entity(wall));

		wall = new Vector[4];
		wall[0] = new Vector(-1 + t, 1 - t);
		wall[1] = new Vector(-1, 1);
		wall[2] = new Vector(1, 1);
		wall[3] = new Vector(1 - t, 1 - t);
		objs.add(new Entity(wall));

		wall = new Vector[4];
		wall[0] = new Vector(1 - t, -1);
		wall[1] = new Vector(1 - t, 1 - t);
		wall[2] = new Vector(1, 1);
		wall[3] = new Vector(1, -1);
		objs.add(new Entity(wall));

		wall = new Vector[4];
		wall[0] = new Vector(-1 + t, -1);
		wall[1] = new Vector(-1 + t, -1 + t);
		wall[2] = new Vector(1 - t, -1 + t);
		wall[3] = new Vector(1 - t, -1);
		killPlane = new Entity(wall) {
			@Override
			public void draw(GL2 gl, double[] color) {
			}

			@Override
			public void handleCollision(GameState gs) {
				gs.vidas -= 1;
				gs.state = gs.vidas == 0 ? GameState.GAME_OVER : GameState.LEVEL_START;
			}
		};
		objs.add(killPlane);
	}

	private void paddle() {
		paddle = new Paddle(0.5, 0.03);
		objs.add(paddle);
	}

	private void ball() {
		ball = new Ball(.025, gs);
	}
}
