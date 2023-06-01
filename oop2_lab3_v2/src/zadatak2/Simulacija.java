package zadatak2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Simulacija extends Frame {

	private AktivnaScena scena;
	
	private void populateWindow() {
		add(scena, BorderLayout.CENTER);
	}

	public Simulacija() {
		setSize(new Dimension(500, 500));
		setTitle("Simulacija");
		scena = new AktivnaScena(this);
		populateWindow();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				scena.zavrsiScenu();
				dispose();
			}
		});
		scena.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Simulacija.this.requestFocus();
				if (scena.scenaAktivna())
					return;
				int x = e.getX();
				int y = e.getY();
				Vektor polozaj = new Vektor(x, y);
				Vektor pomeraj = Vektor.slucajanVektor();

				// moze biti neka druga figura, ne mora disk
				Figura f = new Disk(polozaj, pomeraj);


				scena.dodajFiguru(f);
				scena.repaint();
			}
		});

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int akcija = e.getKeyCode();
				switch (akcija) {
				case KeyEvent.VK_SPACE:
					if (scena.scenaAktivna()) {
						scena.pauzirajScenu();
						scena.repaint();
						setResizable(true);
					} else {
						scena.aktivirajScenu();
						setResizable(false);
					}
					break;
				case KeyEvent.VK_ESCAPE:
					scena.zavrsiScenu();
					dispose();
					break;
				}
			}
		});
		setResizable(true);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new Simulacija();
	}

}
