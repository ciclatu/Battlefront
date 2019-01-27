package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
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

public class Derrota implements IPantalla, Runnable {
	private DecimalFormat formatoDecimal;
	Color colorLetra = Color.YELLOW;
	public Thread sonido = null;
	PanelJuego panelJuego;
	Image fondo = null;
	Image fondoEscalado = null;

	public Derrota(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
		formatoDecimal = new DecimalFormat("##.##");
		inicializarPantalla();
		redimensionarPantalla();
	}

	@Override
	public void inicializarPantalla() {
		panelJuego.setCursor(null);
		try {
			fondo = ImageIO.read(new File("Imagenes/halconSuelo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		sonido = new Thread(this);
		sonido.start();
	}

	@Override
	public void renderizarPantalla(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);

		g.setColor(Color.BLACK);
		g.setFont(new Font("MiLetra", Font.BOLD, 40));
		g.drawString("Eres muy malo... ", panelJuego.getWidth() / 2 - 230, panelJuego.getHeight() / 2 - 250);
		g.setColor(Color.BLACK);
		g.setFont(new Font("MiLetra", Font.BOLD, 24));
		g.drawString("Aguantaste " + formatoDecimal.format((PantallaJugar.tiempoDeJuego) / 1000000000)
				+ " segundos nebulosos.", panelJuego.getWidth() / 2 - 80, panelJuego.getHeight() / 2 + 250);

		g.setColor(Color.BLACK);
		g.setFont(new Font("MiLetra", Font.BOLD, 35));
		g.drawString("Pulsa para volver a intentar.", panelJuego.getWidth() / 2 - 80, panelJuego.getHeight() / 2 + 300);

	}

	@Override
	public void ejecutarFrame() {
		panelJuego.repaint();
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

		panelJuego.setPantalla(new Intro(panelJuego));
		sonido.stop();
	}

	@Override
	public void redimensionarPantalla() {
		fondoEscalado = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);

	}

	@Override
	public void run() {
		try {

			FileInputStream fis;
			Player player;
			fis = new FileInputStream("Musica/cancionMala.mp3");
			BufferedInputStream bis = new BufferedInputStream(fis);

			player = new Player(bis);
			Thread.sleep(500);
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
}
