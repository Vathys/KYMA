package Game.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import Enemies.BossMesh;
import Enemies.EnemyBoss1;
import Enemies.GameObject;
import Enemies.ID;
import Enemies.Spawn;

public class Player extends GameObject{

	Random r = new Random();
	Handler handler;
	private float startX;
	private float startY;
	private static float speed;
	private static float defense = 0;
	private static int coinFactor = 1;
	
	public Player(float x, float y, ID id, Handler handler){
		super(x, y, id);
		this.handler = handler;
		startX = x;
		startY = y;
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, 40, 40);
	}
	
	public void tick(){
		x += velX;
		y += velY;
		
		x = Game.clamp(x, 0, Game.WIDTH - 46);
		y = Game.clamp(y, 0, Game.HEIGHT - 68);
		
		vel[0] = velX;
		vel[1] = velY;
		
		
		collision();
	}
	
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){
			
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getID() == ID.Enemy){ //tempObject is now BasicEnemy
				if(getBounds().intersects(tempObject.getBounds())){
					 //Collision Code
					
					if(HUD.blueHEALTH > 0) HUD.blueHEALTH += HUD.HPLOSS + defense;
					else HUD.greenHEALTH += HUD.HPLOSS + defense;
				}
			}
			
			if(tempObject.getID() == ID.Coin){
				if(getBounds().intersects(tempObject.getBounds())){
					handler.removeObject(tempObject);
					Menu.increaseCurrence(coinFactor);
					Spawn.setCoinSpawned(Spawn.getCoinSpawned() - 1);
				}
			}
		}
	}
	
	public void render(Graphics g){
		g.setColor(Color.white);          /*if(id == ID.Player)*/ 
		g.fillRect((int)x, (int)y, 40, 40);
	}

	public void hide(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect((int)x, (int)y, 40, 40);
		
		velX = 0;
		velY = 0;
	}
	
	public void restoreVel(){
		velX = vel[0];
		velY = vel[1];
	}
	
	public void resetPos(){
		x = startX;
		y = startY;
	}
	
	public static void setSpeed(float s){
		speed = s;
	}
	
	public static void setDefense(float d){
		defense = d;
	}
	
	public static float getSpeed(){
		return speed;
	}

	public static float getDefense(){
		return defense;
	}
	
	public static int getCoinFactor(){
		return coinFactor;
	}
	
	public static void setCoinfactor(int change){
		coinFactor = change;
	}

}
