package core;

import entities.Ball;
import entities.Entity;
import utils.CollisionEvent;
import utils.GeometryHelper;
import utils.Vector;

public class Collision {

	/**
	 * @param ball Ball entity
	 * @param e2   Object entity
	 * @return Evento de colisão em caso verdadeiro, null caso contrário
	 */
	public CollisionEvent isOverlapping(Ball ball, Entity e2) {

		CollisionEvent evt = null;

		Vector objPos = e2.getCom();
		Vector ballPos = ball.getCom();

		double objRadius = e2.getRadius();
		double ballRadius = ball.getRadius();

		/* se a distância do centro da bola até o centro de outro objeto maior que
		a soma dos raios dos dois, é impossível que âmbos estejam colidindo */
		if (GeometryHelper.distBetweenPoints(ballPos, objPos) <= ballRadius + objRadius) {
			Vector[] objPoints = e2.getPoints();

			for (int i = 0; i < objPoints.length - 1; i++) {
				Vector midpoint = GeometryHelper.getMidpoint(objPoints[i], objPoints[i + 1]);
				double distBallEdge = GeometryHelper.distBetweenPoints(ballPos, midpoint);
				double edgeRange = GeometryHelper.distBetweenPoints(midpoint, objPoints[i]);

				/* se a distância do centro da bola até o meio de uma reta for maior que a
				soma do raio da reta e da bola, é impossível que âmbos estejam colidindo */
				if (distBallEdge <= ballRadius + edgeRange) {
					Vector[] ballPoints = ball.getPoints();
					for (Vector ballPoint : ballPoints) {
						double d = GeometryHelper.calcDistanceEdgePoint(objPoints[i], objPoints[i + 1], ballPoint);

						// Colidiu!
						if (d <= ballRadius) {

							// Direção da parede
							Vector wallDirection = new Vector(objPoints[i + 1].x - objPoints[i].x, objPoints[i + 1].y - objPoints[i].y);
							double wallLength = GeometryHelper.distBetweenPoints(new Vector(0, 0), wallDirection);

							// Normalização da direção
							wallDirection.x /= wallLength;
							wallDirection.y /= wallLength;

							evt = new CollisionEvent(ball.direction, wallDirection);
							break;
						}
					}
					if (evt != null) break;
				}
			}
		}
		return evt;
	}
}
