package zadatak2;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Label;
import java.util.ArrayList;

public class AktivnaScena extends Canvas implements Runnable {
	public enum StatusScene {
		AKTIVNA, PAUZIRANA, ZAVRSENA
	};

	private static final int sleepTime = 100;

	private Simulacija glavniProzor;
	private ArrayList<Figura> figure = new ArrayList<>();
	private int multiplikator = 3;
	private StatusScene status;
	private Thread mojaNit;

	public AktivnaScena(Simulacija glProzor) {
		glavniProzor = glProzor;
		setBackground(Color.GRAY);
		status = StatusScene.PAUZIRANA;
		new Thread(this).start();
	}

	public int getMultiplikator() {
		return multiplikator;
	}

	public void setMultiplikator(int multiplikator) {
		this.multiplikator = multiplikator;
	}

	public void dodajFiguru(Figura fig) {
		if (udaraZid(fig))
			return;
		for (Figura f : figure) {
			if (fig.kruzniceSePreklapaju(f))
				return;
		}
		figure.add(fig);
	}

	public void zavrsiScenu() {
		status = StatusScene.ZAVRSENA;
	}

	public void aktivirajScenu() {
		status = StatusScene.AKTIVNA;
	}

	public void pauzirajScenu() {
		status = StatusScene.PAUZIRANA;
	}

	public boolean scenaAktivna() {
		return status == StatusScene.AKTIVNA;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int canvasWidth = getWidth();
		int canvasHeight = getHeight();
		for (Figura f : figure) {
			f.draw(g);
		}
		if (status == StatusScene.PAUZIRANA) {
			Font oldFont = g.getFont();
			Color oldColor = g.getColor();
			double fontScale = 0.1;
			g.setFont(new Font("Comic Sans MS", Font.BOLD, (int) (Math.min(canvasWidth, canvasHeight) * fontScale)));
			g.setColor(Color.BLACK);
			FontMetrics fm = g.getFontMetrics();
			int stringHeight = fm.getHeight();
			int stringWidth = fm.stringWidth("PAUZA");
			int x = (canvasWidth - stringWidth) / 2;
			int y = (canvasHeight - stringHeight) / 2 + fm.getAscent();
			g.drawString("PAUZA", x, y);
			g.setFont(oldFont);
			g.setColor(oldColor);
		}
	}

	@Override
	public void run() {
		while (status != StatusScene.ZAVRSENA) {
			try {
				glavniProzor.requestFocus();

				Thread.sleep(sleepTime);
				if (status == StatusScene.AKTIVNA) {
					izracunajPomeraje();
					repaint();
				}
			} catch (InterruptedException e) {
			}
		}

	}

	private void izracunajPomeraje() {
		for (Figura f : figure) {
			Vektor ort = f.pomeraj.ortVektor();
			ort.setX(ort.getX() * multiplikator);
			ort.setY(ort.getY() * multiplikator);
			f.polozaj.setX(f.polozaj.getX() + ort.getX());
			f.polozaj.setY(f.polozaj.getY() + ort.getY());
			odbijanjeOZid(f);
			for (Figura f2 : figure) {
				if (f != f2 && f.kruzniceSePreklapaju(f2)) {
					odbijanjeOFiguru(f, f2);
				}
			}
		}
	}

	private boolean udaraZid(Figura f) {
		int r = (int) f.getRo();
		int x = (int) f.polozaj.getX();
		int y = (int) f.polozaj.getY();
		int sirina = getWidth();
		int visina = getHeight();
		return x - r < 0 || x + r >= sirina || y - r < 0 || y + r >= visina;
	}

	private void odbijanjeOZid(Figura f) {
		int r = (int) f.getRo();
		int x = (int) f.polozaj.getX();
		int y = (int) f.polozaj.getY();
		int sirina = getWidth();
		int visina = getHeight();
		if (x - r < 0 || x + r >= sirina) {
			f.pomeraj.setX(-f.pomeraj.getX());
			if (x - r < 0) {
				f.polozaj.setX(r);
			} else {
				f.polozaj.setX(sirina - r - 1);
			}
		}
		if (y - r < 0 || y + r >= visina) {
			f.pomeraj.setY(-f.pomeraj.getY());
			if (y - r < 0) {
				f.polozaj.setY(r);
			} else {
				f.polozaj.setY(visina - r - 1);
			}
		}
	}

	private void odbijanjeOFiguru(Figura f1, Figura f2) {
		double f1x = f1.pomeraj.getX();
		double f1y = f1.pomeraj.getY();
		double f2x = f2.pomeraj.getX();
		double f2y = f2.pomeraj.getY();
		f1.pomeraj.setX(f2x);
		f1.pomeraj.setY(f2y);
		f2.pomeraj.setX(f1x);
		f2.pomeraj.setY(f1y);
	}

}
