package Enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;

import Game.main.Handler;

public class BossMesh extends GameObject{

	private Polygon collisionMesh;
	Point CENTER;
	int Points, Size;
	
	Handler Handler;
	
	public BossMesh(float x, float y, ID id, int Points, int Size, Handler Handler){
		super(x, y, id);
		this.Points = Points;
		this.Size = Size;
		this.Handler = Handler;
	}

	public void tick(){
	CENTER = new Point((int)x + (Size / 2), (int)y + (Size / 2));
		
		if(Points == 3){
			Point one = new Point(CENTER.x, (int)y);
			Point two = new Point((int)x, CENTER.y + (Size / 2));
			Point three = new Point(CENTER.x + (Size / 2), CENTER.y + (Size / 2));
			
			collisionMesh = new Polygon();
			collisionMesh.addPoint(one.x, one.y);
			collisionMesh.addPoint(two.x, two.y);
			collisionMesh.addPoint(three.x, three.y);
		}
		
		
		//collision();
	}
	
	public void collision(){
		
		for(int i = 0; i < Handler.object.size(); i++){
			GameObject tempObject = Handler.object.get(i);
			
			if(tempObject.getID() == ID.Player){
				
				for(int j = 0; j < this.getPoints().size(); j++){
					
					if(tempObject.getBounds().contains(this.getPoints().get(j))){
						System.out.println("I'M HITT");
					}
				}
			}
		}
	}

	public void render(Graphics g){
		
		g.drawPolygon(collisionMesh);
		
	}

	public void hide(Graphics g){
		
	}

	public void restoreVel(){
		
	}

	public Rectangle getBounds(){
		return null;
	}
	
	public ArrayList<Point> getPoints(){
		ArrayList<Point> n = new ArrayList<Point>();
		
		for(int i = 0; i < collisionMesh.xpoints.length; i++){
			n.add(new Point(collisionMesh.xpoints[i], collisionMesh.ypoints[i]));
		}
		return n;	
	}
}
