package arkanoid.version02;

import java.awt.Color;
import java.awt.Graphics;

public class Pelota extends Objeto {

	protected int vx;
	protected int vy;
	

	public Pelota() {

	}
	
	public Pelota(int x, int y) {
		super(x,y);
	}



	public void act() {
		// Movimiento en el eje horizontal
		super.act();
				this.x += this.vx; // En cada iteraci�n del bucle principal, el monstruo cambiar� su posici�n en el eje X, sum�ndole a esta el valor de vx
				// �Qu� ocurre si la imagen del personaje sale de la pantalla en el eje horizontal?
				if (this.x < 0 || this.x > (Arkanoid.getInstance().getWidth() - 20)) {
					  vx = -vx; // En caso de salir fuera de la ventana, el actor cambiar el signo de su velocidad, tanto cuando choque
					  // con el l�mite derecho como cuando lo haga con el izquierdo
				}
				
				// Movimiento en el eje vertical
				this.y += this.vy; // En cada iteraci�n del bucle principal, el monstruo cambiar� su posici�n en el eje y, sum�ndole a esta el valor de vy
				// �Qu� ocurre si la imagen del personaje sale de la pantalla en el eje vertical?
				if (this.y < 0 || this.y > (Arkanoid.getInstance().getHeight() - 20)) {
					  vy = -vy; // En caso de salir fuera de la ventana, el actor cambiar el signo de su velocidad, tanto cuando choque
					  // con el l�mite superior como cuando lo haga con el inferior
				}
			}
	
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(this.getX(), this.getY(), 20, 20);
	}


	
	/**
	 * @return the vx
	 */
	public int getVx() {
		return vx;
	}

	/**
	 * @param vx the vx to set
	 */
	public void setVx(int vx) {
		this.vx = vx;
	}

	/**
	 * @return the vy
	 */
	public int getVy() {
		return vy;
	}

	/**
	 * @param vy the vy to set
	 */
	public void setVy(int vy) {
		this.vy = vy;
	}

	

}
