package pantallas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import base.PanelJuego;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Intro implements IPantalla, Runnable {
	Image fondo = null;
	Image fondoEscalado = null;
	PanelJuego panelJuego;
	Thread sonido = null;
	Color colorLetra = Color.BLACK;

	public Intro(PanelJuego panelJuego) {
		super();
		this.panelJuego = panelJuego;
		try {
			fondo = ImageIO.read(new File("Imagenes/fondoStar.jpg"));
			fondoEscalado = ImageIO.read(new File("Imagenes/fondoStar.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		panelJuego.setCursor(null);
		inicializarPantalla();
		redimensionarPantalla();
	}

	@Override
	public void inicializarPantalla() {
		sonido = new Thread(this);
		sonido.start();

	}

	@Override
	public void renderizarPantalla(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, panelJuego.getWidth(), panelJuego.getHeight());
		g.drawImage(fondoEscalado, 0, 0, null);
		g.setColor(colorLetra);
		g.setFont(panelJuego.getFuenteGrande());

	}

	@Override
	public void ejecutarFrame() {
		panelJuego.repaint();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void moverRaton(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		cambiarPantalla();
		sonido.stop();

	}

	public void cambiarPantalla() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		panelJuego.setPantalla(new PantallaJugar(panelJuego));
		sonido.stop();

	}

	@Override
	public void redimensionarPantalla() {
		if (panelJuego.getWidth() == 0) {
			fondoEscalado = fondo.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
		} else {
			fondoEscalado = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
		}

	}

	@Override
	public void run() {
		try {
			FileInputStream fis;
			Player player;
			fis = new FileInputStream("Musica/intro.mp3");
			BufferedInputStream bis = new BufferedInputStream(fis);
			player = new Player(bis);
			player.play();

		} catch (JavaLayerException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void moverTeclas(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
