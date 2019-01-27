package base;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import pantallas.IPantalla;
import pantallas.Intro;;

public class PanelJuego extends JPanel
		implements Runnable, MouseListener, MouseMotionListener, ComponentListener, KeyListener {

	private static final long serialVersionUID = 1L;

	IPantalla pantallaActual;
	Font fueteGrande = new Font("Arial", Font.PLAIN, 30);

	public PanelJuego() {

		pantallaActual = new Intro(this);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.addComponentListener(this);
		new Thread(this).start();
		this.addKeyListener(this);
		setFocusable(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		pantallaActual.renderizarPantalla(g);
	}

	@Override
	public void run() {
		while (true) {
			pantallaActual.ejecutarFrame();
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pantallaActual.pulsarRaton(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		pantallaActual.moverRaton(e);

	}

	@Override
	public void componentResized(ComponentEvent e) {
		pantallaActual.redimensionarPantalla();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	public Font getFuenteGrande() {
		return fueteGrande;
	}

	public void setPantalla(IPantalla pantalla) {
		this.pantallaActual = pantalla;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		pantallaActual.moverTeclas(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}