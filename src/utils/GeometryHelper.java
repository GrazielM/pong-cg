package utils;

public class GeometryHelper {


	/**
	 * @param p1 Borda 1 da reta
	 * @param p2 Borda 2 da reta
	 * @return Ângulo entre a reta formada pelos pontos e uma linha perfeitamente horizontal
	 */
	public static double calcEdgeAngleToGround(Vector p1, Vector p2) {
		return Math.toDegrees(Math.atan((p2.y - p1.y) / (p2.x - p1.x)));
	}

	/**
	 * @param e1 Borda 1 da reta
	 * @param e2 Borda 2 da reta
	 * @param p  Ponto em questão
	 * @return Menor distância entre o ponto e a reta
	 */
	public static double calcDistanceEdgePoint(Vector e1, Vector e2, Vector p) {
		double base = distBetweenPoints(e1, e2);
		double sideA = distBetweenPoints(e1, p);
		double sideB = distBetweenPoints(e2, p);

		double s = (base + sideA + sideB) / 2;

		double area = Math.sqrt(s * (s - sideA) * (s - sideB) * (s - base));

		return area / (base * 0.5);
	}

	public static double distBetweenPoints(Vector p1, Vector p2) {
		return Math.sqrt(Math.pow(Math.abs(p2.x - p1.x), 2) + Math.pow(Math.abs(p2.y - p1.y), 2));
	}

	public static Vector getMidpoint(Vector p1, Vector p2) {
		return new Vector((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
	}
}
