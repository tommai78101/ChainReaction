package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Area {
	List<Circle> circles = new ArrayList<Circle>();
	Ship ship;
	
	public Area() {
		initialize();
	}
	
	public void initialize() {
		final Random r = new Random();
		final List<Circle> queue = new ArrayList<Circle>();
		while (circles.size() < 1) {
			circles.add(new Circle(r.nextFloat() * 600 + 20, r.nextFloat() * 440 + 20));
		}
		ship = new Ship();
	}
	
	public void deinitialize() {
		if (!circles.isEmpty()) {
			circles.clear();
		}
	}
	
	public void tick() {
		if (!circles.isEmpty()) {
			
			//			for (int i =0; i< circles.size() - 1; i++){
			//				Circle a = circles.get(i);
			//				for (int j = 1; j<circles.size(); j++){
			//					Circle b = circles.get(j);
			//					if (a.intersects(b)){
			//						a.setAX(a.getAX() * -1);
			//						a.setAY(a.getAX() * -1);
			//					}
			//					
			//				}
			//			}
			
			for (Circle c : circles) {
				c.tick(circles);
			}
		}
		ship.tick(circles);
	}
	
	public void render() {
		if (!circles.isEmpty()) {
			for (Circle c : circles) {
				c.render();
			}
		}
		ship.draw();
	}
}
