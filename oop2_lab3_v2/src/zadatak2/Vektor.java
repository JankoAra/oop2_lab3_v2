package zadatak2;

import java.util.Random;

public class Vektor {
	private double x, y;

	public Vektor(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Vektor ortVektor() {
		double magnituda = Math.sqrt(x * x + y * y);
		if (magnituda == 0)
			return new Vektor(0, 0);
		return new Vektor(x / magnituda, y / magnituda);
	}

	static public Vektor slucajanVektor() {
		double x, y;
		do {
			x = new Random().nextDouble() * 2 - 1;
			y = new Random().nextDouble() * 2 - 1;
		} while (x == 0 && y == 0);
		return new Vektor(x, y);
	}
}
