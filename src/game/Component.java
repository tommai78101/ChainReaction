package game;

import java.awt.Canvas;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/*
 * 
 * This is an RTS.
 * Theme: Chemical Reaction.
 * Features: Pong (Simple, intuitive physics involving bouncing balls.)
 */

public class Component {
	private boolean isRunning = false;
	private Area area;
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	
	public Component() {
		try {
			
			JFrame frame = new JFrame("Test");
			Canvas c = new Canvas();
			c.setSize(WIDTH, HEIGHT);
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setParent(c);
			JPanel p = new JPanel();
			p.add(c);
			frame.add(p);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setAlwaysOnTop(false);
			frame.setVisible(true);
			
			Display.create();
		}
		catch (LWJGLException e) {
			e.printStackTrace();
		}
		catch (Exception ee) {
			ee.printStackTrace();
		}
	}
	
	public void start() {
		isRunning = true;
		area = new Area();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		while (isRunning) {
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Display.isCloseRequested()) {
				stop();
			}
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			
			area.tick();
			area.render();
			
			render();
			
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
		System.exit(0);
	}
	
	public void render() {
	}
	
	public void stop() {
		isRunning = false;
	}
	
	public static void main(String[] a) {
		new Component().start();
	}
}
