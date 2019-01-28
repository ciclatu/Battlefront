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

import javax.imageio.ImageIO;

import base.PanelJuego;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Victoria implements IPantalla, Runnable {
	Color colorLetra = Color.YELLOW;
	PanelJuego paneljuego;
	Image fondo = null;
	Image fondoescalado = null;
	private DecimalFormat formatoDecimal;
	private Thread sonido;

	public Victoria(PanelJuego paneljuego) {
		this.paneljuego = paneljuego;
		inicializarPantalla();
		redimensionarPantalla();
		formatoDecimal = new DecimalFormat("##.##");
	}

	@Override
	public void inicializarPantalla() {

		paneljuego.setCursor(null);
		sonido = new Thread(this);
		sonido.start();
		try {
			fondo = ImageIO.read(new File("Battlefront/Imagenes/nabooF.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void renderizarPantalla(Graphics g) {
		g.drawImage(fondoescalado, 0, 0, null);
		g.setColor(Color.BLACK);
		g.setFont(new Font("MiLetra", Font.BOLD, 50));
		g.drawString("Has salvado Naboo ", paneljuego.getWidth() / 2 - 270, paneljuego.getHeight() / 2 - 230);
		g.setColor(Color.WHITE);
		g.setFont(PantallaJugar.font);

		g.drawString(formatoDecimal.format((PantallaJugar.tiempoDeJuego) / 1000000) + " puntos.",
				paneljuego.getWidth() / 2 - 90, paneljuego.getHeight() / 2 - 70);
		g.setColor(Color.WHITE);
		g.setFont(new Font("MiLetra", Font.BOLD, 35));
		g.drawString("Volver a Jugar", paneljuego.getWidth() / 2 - 110, paneljuego.getHeight() / 2 + 100);
	}

	@Override
	public void ejecutarFrame() {
		paneljuego.repaint();
		try {
			Thread.sleep(25);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			colorLetra = colorLetra == Color.YELLOW ? Color.GREEN : Color.YELLOW;

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
		paneljuego.setPantalla(new Intro(paneljuego));
		sonido.stop();
	}

	@Override
	public void redimensionarPantalla() {
		fondoescalado = fondo.getScaledInstance(paneljuego.getWidth(), paneljuego.getHeight(), Image.SCALE_SMOOTH);
	}

	@Override
	public void run() {
		
		try {
			FileInputStream fis;
			Player player;
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream("Battlefront/Musica/naboo.mp3"));
			player = new Player(bis);
			Thread.sleep(1000);
			player.play();

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

	@Override
	public void moverTeclas(KeyEvent e) {
	
	}

}
