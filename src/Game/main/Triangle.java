package Game.main;
import java.awt.Point;
import java.awt.Polygon;

public class Triangle extends Polygon{

	private static double RAD;
	private Point main;
	private Point minor1;
	private Point minor2;
	private double factorLong, factorShort;
	
	public Triangle(Point p, int size, int state){
		super();
		
		main = new Point(p.x, p.y);
		minor1 = new Point();
		minor2 = new Point();
		
		RAD = size;
		
		factorLong = (RAD * 3) / 2;
		factorShort = Math.sqrt(Math.pow(RAD, 2.0) - Math.pow(RAD / 2, 2.0));
		
		switch(state){
		 /*
		  * 1 - points right;
		  * 2 - points up;
		  * 3 - points down;
		  * 4 - points left;
		  * */
		case 1:
			minor1.setLocation(main.x - factorLong, main.y - factorShort);
			minor2.setLocation(main.x - factorLong, main.y + factorShort);
			break;
			
		case 2:
			minor1.setLocation(main.x - factorShort, main.y + factorLong);
			minor2.setLocation(main.x + factorShort, main.y + factorLong);
			break;
			
		case 3:
			minor1.setLocation(main.x + factorLong, main.y - factorShort);
			minor2.setLocation(main.x + factorLong, main.y + factorShort);
			break;
			
		case 4:
			minor1.setLocation(main.x - factorShort, main.y - factorLong);
			minor2.setLocation(main.x + factorShort, main.y - factorLong);
			break;
		}
		
		this.addPoint(main.x, main.y);
		this.addPoint(minor1.x, minor1.y);
		this.addPoint(minor2.x, minor2.y);
	}
}


