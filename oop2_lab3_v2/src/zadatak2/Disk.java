package zadatak2;

import java.awt.Color;
import java.awt.Graphics;

public class Disk extends Figura {

	public Disk(Vektor pol, Vektor pom, double r) {
		super(pol, pom, r);
		boja = Color.BLUE;
	}

	public Disk(Vektor pol, Vektor pom) {
		this(pol, pom, podrazumevaniRo);
	}

	@Override
	public void draw(Graphics g) {
		final int brTemena = 8;
		int[] temenaX = new int[brTemena];
		int[] temenaY = new int[brTemena];
		for (int i = 0; i < brTemena; i++) {
			double ugao = (2 * Math.PI / brTemena) * i;
			temenaX[i] = (int) (polozaj.getX() + ro * Math.cos(ugao));
			temenaY[i] = (int) (polozaj.getY() + ro * Math.sin(ugao));
		}
		Color staraBoja = g.getColor();
		g.setColor(boja);
		g.fillPolygon(temenaX, temenaY, brTemena);
		g.setColor(staraBoja);

	}

}
