package base;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

import pantallas.PantallaJugar2;

/**
 * @author jesusredondogarcia Clase Sprite. Representa un elemento pintable y
 *         colisionable del juego.
 */
public class Sprite {

	private BufferedImage buffer;
	private Color color = Color.RED;

	private int ancho;
	private int alto;

	private int posX;
	private int posY;

	private int velocidadX;
	private int velocidadY;

	public Sprite(int ancho, int alto, int posX, int posY, int velocidadX, int velocidadY, String ruta) {
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.velocidadX = velocidadX;
		this.velocidadY = velocidadY;
		actualizarBuffer(ruta);
	}

	public Sprite(int ancho, int alto, int posX, int posY, String ruta) {
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		actualizarBuffer(ruta);
	}

	public void actualizarBuffer(String ruta) {

		buffer = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();

		Image imagenAuxiliar = null;
		try {
			imagenAuxiliar = ImageIO.read(new File(ruta));
			imagenAuxiliar = imagenAuxiliar.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
			g.drawImage(imagenAuxiliar, 0, 0, null);
			return;
		} catch (Exception e) {
			// si no escribe lo otro pone esto
			g.setColor(color);
			g.fillRect(0, 0, ancho, alto);
			g.dispose();
		}

	}

	// colisiones

	public boolean colisionan(Sprite sprite) {
		// Checkeamos si comparten algún espacio a lo ancho:
		boolean colisionX = false;
		if (posX < sprite.getPosX()) {
			colisionX = ancho + posX > sprite.getPosX();
		} else {
			colisionX = sprite.getAncho() + sprite.getPosX() > posX;
		}

		boolean colisionY = false;
		if (posY < sprite.getPosY()) {
			colisionY = alto > sprite.getPosY() - posY;
		} else {
			colisionY = sprite.getAlto() > posY - sprite.getPosY();
		}

		return colisionX && colisionY;
	}

	public void moverSprite(int anchoFrame, int altoFrame) {

		if (posX >= anchoFrame - ancho) { // por la derecha

			velocidadX = -1 * Math.abs(velocidadX);

		}
		if (posX <= 0) {// por la izquierda

			velocidadX = Math.abs(velocidadX);

		}
		if (posY >= altoFrame - alto) {// por abajo

			velocidadY = -1 * Math.abs(velocidadY);
		}

		if (posY <= 0) { // Por arriba

			velocidadY = Math.abs(velocidadY);
		}
		moverSprite();
	}

	public void moverSprite() {

		if (PantallaJugar2.vidasEnemigos == 2) {
			posX = posX + velocidadX * 3;
			posY = posY + velocidadY * 3;

		}

		if (PantallaJugar2.vidasEnemigos == 1) {
			posX = posX + velocidadX * 8;
			posY = posY + velocidadY * 6;

		} else {
			posX = posX + velocidadX;
			posY = posY + velocidadY;
		}

	}

	public void pintarSpriteEnMundo(Graphics g) {
		g.drawImage(buffer, posX, posY, null);
	}

	// Métodos para obtener:
	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public BufferedImage getBuffer() {
		return buffer;
	}

	public int getVelocidadX() {
		return velocidadX;
	}

	public int getVelocidadY() {
		return velocidadY;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public void setBuffer(BufferedImage buffer) {
		this.buffer = buffer;
	}

	public void setVelocidadX(int velocidadX) {
		this.velocidadX = velocidadX;
	}

	public void setAceleracionY(int velocidadY) {
		this.velocidadY = velocidadY;
	}

	public void setColor(Color color) {
		this.color = color;
		actualizarBuffer(null);
	}

}
