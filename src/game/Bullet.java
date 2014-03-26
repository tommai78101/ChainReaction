package game;

import org.lwjgl.opengl.GL11;

public class Bullet {
	
	private float x, y, ax, ay;
	private boolean outOfBounds;
	
	public Bullet(Ship ship) {
		this.ax = (float) Math.sin(ship.getDirection()) * 5f;
		this.ay = (float) Math.cos(ship.getDirection()) * 5f;
		this.x = ship.getX();
		this.y = ship.getY();
		this.outOfBounds = false;
	}
	
	public void tick() {
		x -= ax;
		y -= ay;
		
		if (x < 0 || x > Component.WIDTH || y < 0 || y > Component.HEIGHT)
			outOfBounds = true;
	}
	
	public void draw() {
		GL11.glPushMatrix();
		GL11.glColor3f(1f, 1f, 0f);
		GL11.glTranslatef(x, y, 0f);
		GL11.glPointSize(5f);
		GL11.glBegin(GL11.GL_POINTS);
		{
			GL11.glVertex2f(0, 0);
		}
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glColor3f(1f, 1f, 1f);
	}
	
	public boolean isOutOfBounds() {
		return outOfBounds;
	}
}
