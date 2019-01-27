package pantallas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import base.PanelJuego;
import base.Sprite;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class PantallaJugar implements IPantalla, Runnable {
	private static final int ASTEROIDES = 5;
	private static final int ALTO_ASTEROIDE = 150;

	private static final int ANCHO_ASTEROIDE = 80;
	private static final int ANCHO_NAVE = 350;
	private static final int ALTO_NAVE = 250;
	private static final int ANCHO_DISPARO = 30;
	private static final int ALTO_DISPARO = 50;
	private int posicionNaveX, posicionNaveY;
	private Thread sonido;
	private boolean asteroidesBoolean;
	float tiempoActual;
	private static int colisiones;

	ArrayList<Sprite> navesEnemigas;
	Image fondo = null;
	Image fondoEscalado = null;
	Sprite nave;
	Sprite disparo = null;

	double tiempoInicial;
	static double tiempoDeJuego;
	static Font font;
	private DecimalFormat formatoDecimal;

	PanelJuego panelJuego;
	int vidas;

	public PantallaJugar(PanelJuego paneljuego) {
		BufferedImage cursorImg = new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB);
		// Cursor blankCursor =
		// Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0),
		// "blank cursor");
		// paneljuego.setCursor(blankCursor);

		Image im = Toolkit.getDefaultToolkit().createImage("Imagenes/mira.png");
		Cursor cur = Toolkit.getDefaultToolkit().createCustomCursor(im, new Point(1, 1), "will");
		paneljuego.setCursor(cur);

		panelJuego = paneljuego;
		inicializarPantalla();
		redimensionarPantalla();
		colisiones = 0;
		tiempoInicial = System.nanoTime();
		moverRaton(null);
		asteroidesBoolean = false;
		moverTeclas(null);
	}

	String rutaImagen = "Imagenes/starWars.png";

	@Override
	public void inicializarPantalla() {
		sonido = new Thread(this);
		sonido.start();
		navesEnemigas = new ArrayList<Sprite>();
		try {
			fondo = ImageIO.read(new File("Imagenes/fondoPantalla.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		nave = new Sprite(ANCHO_NAVE, ALTO_NAVE, 500, 500, rutaImagen);
		tiempoDeJuego = 0;
		font = new Font("MiLetra", Font.BOLD, 20);
		formatoDecimal = new DecimalFormat("##.##");

	}

	@Override
	public void renderizarPantalla(Graphics g) {
		rellenarFondo(g);

		for (Sprite cuadrado : navesEnemigas) {
			cuadrado.pintarSpriteEnMundo(g);
		}
		if (disparo != null) {
			disparo.pintarSpriteEnMundo(g);
		}
		nave.pintarSpriteEnMundo(g);

		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(formatoDecimal.format(tiempoDeJuego / 1000000000), 25, 25);

	}

	@Override
	public void ejecutarFrame() {
		actualizarTiempo();
		panelJuego.repaint();
		try {
			Thread.sleep(25);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		colisiones();
		moverSprites();
		if (asteroidesBoolean) {
			actualizarTiempo();
		}
		if (ASTEROIDES == colisiones) {
			try {
				fondo = ImageIO.read(new File("Imagenes/starWars.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cambiarPantalla();
		}

	}

	@Override
	public void moverRaton(MouseEvent e) {

		if (e != null) {
			posicionNaveX = e.getX();
			posicionNaveY = 50;

			if (e.getX() > panelJuego.getWidth() / 2) {

			}
			nave.setPosX(500);
			nave.setPosY(500);

		}

	}

	@Override
	public void moverTeclas(KeyEvent e) {

		if (e != null) {

			int x = nave.getPosX();
			int y = nave.getPosY();
			if ((e.getKeyCode() == KeyEvent.VK_RIGHT) && (e.getKeyCode() == KeyEvent.VK_UP)) {
				x = x + 5;
				y = y - 5;
				nave.setPosY(y);
				nave.setPosX(x);

			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				x = x + 5;
				nave.setPosX(x);
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				x = x - 5;
				nave.setPosX(x);
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				y = y - 5;
				nave.setPosY(y);
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				y = y + 5;
				nave.setPosY(y);
			}
		}
	}

	boolean disparoB = false;

	@Override
	public void pulsarRaton(MouseEvent e) {
		String enemigos[] = { "Imagenes/ana.png", "Imagenes/boba.png", "Imagenes/bossk.png", "Imagenes/jabba.png",
				"Imagenes/yoda.png", "Imagenes/storm.png" };
		if (!asteroidesBoolean) {

			Random rd = new Random();

			for (int i = 0; i < ASTEROIDES; i++) {
				Sprite creador;
				int posX = rd.nextInt(panelJuego.getWidth() + 1);
				int poxY = rd.nextInt(panelJuego.getHeight() + 1);
				int ene = rd.nextInt(enemigos.length) + 0;

				// int velocidadX = rd.nextInt(panelJuego.getWidth() / 2);
				// int velocidadY = rd.nextInt(panelJuego.getHeight() / 2);

				int velocidadX = rd.nextInt(20) - 10;
				int velocidadY = rd.nextInt(10) + 1;
				creador = new Sprite(ANCHO_ASTEROIDE, ALTO_ASTEROIDE, 500, 500, 0, 0, enemigos[ene]);

				navesEnemigas.add(creador);
			}

			asteroidesBoolean = true;
		} else {

			if (SwingUtilities.isLeftMouseButton(e)) {
				if (disparo == null) { // No tengo disparo, lo creo:

					disparo = new Sprite(ANCHO_DISPARO, ALTO_DISPARO, (e.getX() - ANCHO_DISPARO / 2) - 30, e.getY(), 0,
							0, "Imagenes/mira2.png");

				}

				try {
					Player disparo = new Player(new FileInputStream("Musica/disparo.mp3"));
					// disparo.play();
				} catch (FileNotFoundException | JavaLayerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		}
	}

	@Override
	public void redimensionarPantalla() {
		fondoEscalado = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
	}

	private void rellenarFondo(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
	}

	private void moverSprites() {
		for (int i = 0; i < navesEnemigas.size(); i++) {
			Sprite aux = navesEnemigas.get(i);
			aux.moverSprite(panelJuego.getWidth(), panelJuego.getHeight());
		}
		if (disparo != null) {
			disparo.moverSprite();
			if (disparo.getPosY() + disparo.getAlto() < 0) {
				disparo = null;
			}
		}
	}

	public void actualizarTiempo() {
		float tiempoActual = System.nanoTime();
		tiempoDeJuego = tiempoActual - tiempoInicial;

	}

	public void colisiones() {
		boolean naveMuerta = false;
		for (int i = 0; i < navesEnemigas.size(); i++) {
			if (navesEnemigas.get(i).colisionan(nave)) {
				// naveMuerta = true;
				// OtraPantalla.vidas--;
			}
		}
		if (vidas > 0 && naveMuerta) {
			sonido.stop();
			panelJuego.setPantalla(new PantallaJugar(panelJuego));
		} else if (naveMuerta) {
			sonido.stop();
			panelJuego.setPantalla(new Derrota(panelJuego));
		}

		for (int i = 0; i < navesEnemigas.size() && disparo != null; i++) {
			if (navesEnemigas.get(i).colisionan(disparo)) {
				navesEnemigas.remove(i);
				colisiones++;
				disparo = null;
			}

		}
		// para que cuando se encuentren se destruyan
		// for (int i = 0; i < navesEnemigas.size() - 1; i++) {
		// for (int j = i + 1; j < navesEnemigas.size(); j++) {
		// if (navesEnemigas.get(i).colisionan(navesEnemigas.get(j))) {
		// navesEnemigas.remove(j);
		// navesEnemigas.remove(i);
		// System.out.println("hola");
		// }
		// }
		// }

	}

	public void cambiarPantalla() {
		sonido.stop();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		panelJuego.setPantalla(new Victoria(panelJuego));
	}

	@Override
	public void run() {
		try {
			FileInputStream fis;
			Player player;
			fis = new FileInputStream("Musica/espacio.mp3");
			BufferedInputStream bis = new BufferedInputStream(fis);
			player = new Player(bis);
			Thread.sleep(1000);
			player.play();

			moverSprites();

			// Llamada al método play
		} catch (JavaLayerException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
