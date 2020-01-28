package arkanoid.version02;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Arkanoid extends Canvas {
	
	
	// Instancia para patr�n Singleton
	private static Arkanoid instance = null;
	
	public static final int SPEED_FPS=60;
	
	private BufferStrategy strategy;
	private long usedTime;
	
	private List<Objeto> objetos = new ArrayList<Objeto>(); 


	
	public Arkanoid() {
		// Ventana principal del juego
		JFrame ventana = new JFrame("Arkanoid");
		JPanel panel = (JPanel) ventana.getContentPane();
		panel.setLayout(new BorderLayout());
		panel.add(this, BorderLayout.CENTER);
		ventana.setBounds(0,0,800,800);
		
		ventana.setVisible(true);
		ventana.setResizable(false);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ventana.setIgnoreRepaint(true);
		this.createBufferStrategy(2);
		strategy = getBufferStrategy();
		this.requestFocus();


	}
	
	public static Arkanoid getInstance () {
		if (instance == null) {
			instance = new Arkanoid();
		}
		return instance;
	}

	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		for (Objeto objeto : this.objetos) {
			objeto.paint(g);
		}
		
		
	}

	

	public void initWorld () {
		Pelota p = new Pelota();
		p.getX(); // Inicializo al azar la posici�n del eje horizontal del monstruo
		p.getY(); // Inicializo la posici�n en el eje vertical, escalonando los monstruos hacia abajo
		p.setVx(10);
		p.setVy(10);
		objetos.add(p);
		int coordIniX = 10;
		int coordIniY = 10;
		for (int fil = 0; fil < 4; fil++) {
			for (int col = 0; col < 12; col++) {
				Ladrillo l = new Ladrillo();
				if(fil == 0) {
					l.setColor(Color.RED);
				}
				if(fil == 1) {
					l.setColor(Color.BLUE);
				}
				if(fil == 2) {
					l.setColor(Color.YELLOW);
				}
				if(fil == 3) {
					l.setColor(Color.GREEN);
				}
				l.setX(coordIniX);
				l.setY(coordIniY);
				objetos.add(l);
				coordIniX += 65;
			}
			coordIniY += 35;
			coordIniX = 10;
			
		}
		Barra b = new Barra();
		b.getX();
		b.getY();
		objetos.add(b);
		
	}
	
	public void updateWorld() {
		// B�sicamente, se llama al m�todo "act" de cada actor, para que cada uno recalcule por si mismo sus valores.
		for (Objeto objeto : this.objetos) {
			objeto.act();
		}
	}
	
	public void paintWorld() {
		// Resuelve un problema de sincronizaci�n de memoria de v�deo en Linux
		Toolkit.getDefaultToolkit().sync();
		// Obtengo el objeto gr�fico que me permita pintar en el doble b�ffer
		Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
		// Pinto un rect�ngulo negro que ocupe toda la escena
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		// Para cada actor del juego, le pido que se pinte a s� mismo
		for (Objeto objeto : this.objetos) {
			objeto.paint(g);
		}
		strategy.show();
	}
	
	public void game() {
		// Inicializaci�n del juego
		initWorld();
		
		// El bucle se ejecutar� mientras el objeto Canvas sea visible
		while (isVisible()) {
			long startTime = System.currentTimeMillis(); // Tomo el tiempo, en millis, antes de crear el siguiente Frame del juego
			// actualizo y pinto la escena
			updateWorld(); 
			paintWorld();
			// Calculo el tiempo que se ha tardado en la ejecuci�n
			usedTime = System.currentTimeMillis()-startTime;
			// Hago que el bucle pare una serie de millis, antes de generar el siguiente FPS
			// El c�lculo hecho "duerme" el proceso para no generar m�s de los SPEED_FPS que se haya espec�ficado
			try { 
				int millisToSleep = (int) (1000/SPEED_FPS - usedTime);
				if (millisToSleep < 0) {
					millisToSleep = 0;
				}
				Thread.sleep(millisToSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String args[]) {
		Arkanoid.getInstance().game();

	}
}
