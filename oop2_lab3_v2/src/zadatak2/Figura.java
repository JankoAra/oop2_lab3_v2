package zadatak2;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Figura {
	protected Vektor polozaj;
	protected Vektor pomeraj;
	protected double ro;
	protected Color boja;
	protected static final int podrazumevaniRo = 20;

	public Figura(Vektor pol, Vektor pom, double r) {
		polozaj = pol;
		pomeraj = pom;
		ro = r;
	}

	public Figura(Vektor pol, Vektor pom) {
		this(pol, pom, podrazumevaniRo);
	}

	public boolean vektorUKruznici(Vektor v) {
		double x1 = this.polozaj.getX();
		double y1 = this.polozaj.getY();
		double x2 = v.getX();
		double y2 = v.getY();
		double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		return d <= this.ro;
	}

	public boolean kruzniceSePreklapaju(Figura fig2) {
		double x1 = this.polozaj.getX();
		double y1 = this.polozaj.getY();
		double x2 = fig2.polozaj.getX();
		double y2 = fig2.polozaj.getY();
		double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		return d <= (this.ro + fig2.ro);
	}

	public abstract void draw(Graphics g);

	public Vektor getPolozaj() {
		return polozaj;
	}

	public Vektor getPomeraj() {
		return pomeraj;
	}

	public double getRo() {
		return ro;
	}

	public Color getBoja() {
		return boja;
	}

}
