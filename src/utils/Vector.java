package utils;

public class Vector {
	public double x, y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector(double[] xy) {
		this.x = xy[0];
		this.y = xy[1];
	}

	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
}
