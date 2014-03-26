package game;

import java.util.List;
import java.util.Random;
import org.lwjgl.opengl.GL11;

public class Circle {
	
	private static final int DRAW_CONSTANT = 20;
	private int count = 0;
	private float angle = 0;
	private boolean hasReflected = false;
	
	private float x, y, radius, ax, ay;
	
	public Circle(float x, float y) {
		this.x = x;
		this.y = y;
		this.radius = 30f;
		Random random = new Random();
		/*this.ax = random.nextFloat() * 10 - 5;
		this.ay = random.nextFloat() * 10 - 5;*/
		this.ax = this.ay = 0f;
		this.count = random.nextInt(10) + 3;
		this.angle = random.nextFloat() * (float) (Math.PI * 2 / 180);
	}
	
	public void setAX(float ax) {
		this.ax = ax;
	}
	
	public void setAY(float ay) {
		this.ay = ay;
	}
	
	public float getAX() {
		return ax;
	}
	
	public float getAY() {
		return ay;
	}
	
	public void tick(List<Circle> circles) {
		
		Circle target = null;
		for (Circle c : circles) {
			if (c == this)
				continue;
			if (intersects(c)) {
				target = c;
				break;
			}
		}
		
		if (target == null) {
			x += ax;
			y += ay;
		}
		else {
			reflect(target);
		}
		if (x > Component.WIDTH + 2 * radius)
			x = -2 * radius;
		if (y > Component.HEIGHT + 2 * radius)
			y = -2 * radius;
		if (x < -2 * radius)
			x = Component.WIDTH + 2 * radius;
		if (y < -2 * radius)
			y = Component.HEIGHT + 2 * radius;
	}
	
	private void reflect(Circle other) {
		float vx = this.x - other.x;
		float vy = this.y - other.y;
		float nx = vy;
		float ny = vx;
		float nd = (float) Math.hypot(nx, ny);
		if (nd == 0)
			nd = 1;
		nx /= nd;
		ny /= nd;
		float dot = vx * nx + vy * ny;
		float rx = -2 * dot * nx + vx;
		float ry = -2 * dot * ny + vy;
		
		this.ax = rx * 0.02f;
		this.ay = ry * 0.02f;
		x += ax;
		y += ay;
	}
	
	public void draw() {
		angle += (float) (Math.PI * 2 / (180 - count));
		if (angle > Math.PI * 2)
			angle = 0;
		
		float theta = (float) (Math.PI * 2) / (count);
		float tangential_factor = (float) Math.tan(theta);
		float radial_factor = (float) Math.cos(theta);
		
		//Starting at 0 degrees angle.
		float x = radius * (float) Math.sin(angle);
		float y = radius * (float) Math.cos(angle);
		
		GL11.glBegin(GL11.GL_LINE_LOOP);
		for (int i = 0; i < (count); i++) {
			GL11.glVertex2f(x + this.x, y + this.y);
			
			//Calculate the tangential vector.
			//Radial vector: The vector (x, y).
			//Tangential vector: Flip the radial vector coordinates, and negate one of them.
			float tx = -y;
			float ty = x;
			
			//Add the tangential vector.
			x += tx * tangential_factor;
			y += ty * tangential_factor;
			
			//Correct the vertex to the circle.
			
			x *= radial_factor;
			y *= radial_factor;
		}
		GL11.glEnd();
		
	}
	
	public void render() {
		draw();
	}
	
	//TODO: Work on the intersecting area.
	public boolean intersects(Circle other) {
		float twoRadii = this.radius + other.radius;
		double test = Math.hypot((this.x - other.x), (this.y - other.y));
		if (test < twoRadii) { return true; }
		return false;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
}
