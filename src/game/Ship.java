package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Ship {
	
	private float x, y, angle, ax, ay;
	private List<Bullet> bullets = new ArrayList<Bullet>();
	
	public Ship() {
		initialize();
	}
	
	public void initialize() {
		x = Component.WIDTH / 2;
		y = Component.HEIGHT / 2;
		angle = 0f;
	}
	
	public void tick(List<Circle> circles) {
		if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			ax += (float) Math.sin(angle);
			ay += (float) Math.cos(angle);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			angle += (float) Math.PI * 2 / 180;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			angle -= (float) Math.PI * 2 / 180;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			bullets.add(new Bullet(this));
		}
		
		x -= ax * 0.4;
		y -= ay * 0.4;
		ax *= 0.9;
		ay *= 0.9;
		
		if (x < 0)
			x = Component.WIDTH;
		if (y < 0)
			y = Component.HEIGHT;
		if (x > Component.WIDTH)
			x = 0;
		if (y > Component.HEIGHT)
			y = 0;
		
		for (Iterator<Bullet> i = bullets.iterator(); i.hasNext();) {
			Bullet b = i.next();
			b.tick();
			if (b.isOutOfBounds())
				i.remove();
		}
		
		System.out.println(intersects(circles.get(0)));
		
	}
	
	public void draw() {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0f);
		GL11.glRotatef((float) -(Math.toDegrees(angle)), 0f, 0f, 1f);
		GL11.glColor3f(0f, 1f, 1f);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		{
			GL11.glVertex2i(0, -10);
			GL11.glVertex2i(8, 10);
			GL11.glVertex2i(0, 5);
			GL11.glVertex2i(-8, 10);
			GL11.glVertex2i(0, -10);
		}
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glColor3f(1f, 1f, 1f);
		
		for (Bullet b : bullets)
			b.draw();
	}
	
	public float getDirection() {
		return angle;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public boolean intersects(Circle circle) {
		
		float vx1 = -8f;
		float vy1 = -20f;
		
		float vx2 = 8f;
		float vy2 = 5f;
		
		float vx3 = 8f;
		float vy3 = -5f;
		
		float vx4 = -8f;
		float vy4 = 0f;
		
		float cx = circle.getX();
		float cy = circle.getY();
		
		float px1 = cx - (x + 0);
		float py1 = cy - (y - 10);
		float px2 = cx - (x + 8);
		float py2 = cy - (y + 10);
		float px3 = cx - (x + 0);
		float py3 = cy - (y + 5);
		float px4 = cx - (x - 8);
		float py4 = cy - (y + 10);
		
		float radius = circle.getRadius();
		
		float V1dotN = dotProduct(px1, py1, vy1, vx1);
		float V2dotN = dotProduct(px2, py2, vy2, vx2);
		float V3dotN = dotProduct(px3, py3, vy3, vx3);
		float V4dotN = dotProduct(px4, py4, vy4, vx4);
		
		if (V1dotN <= 1f || V2dotN <= 1f || V3dotN <= 1f || V4dotN <= 1f) { return true; }
		return false;
	}
	
	private float dotProduct(float x, float y, float nx, float ny) {
		float nd = (float) Math.hypot(nx, ny);
		if (nd == 0)
			nd = 1;
		nx /= nd;
		ny /= nd;
		
		return Math.abs(x * nx + y * ny);
	}
}
