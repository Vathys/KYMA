package Enemies;

import java.awt.Color;
import java.util.Random;

import Enemies.*;
import Game.main.Game;
import Game.main.HUD;
import Game.main.Handler;
import Game.main.Player;
import Game.main.CheatCode;

public class Spawn {

	private Handler handler;
	private HUD hud;
	
	private int scoreKeep;
	private Random r = new Random();
	private static int coinSpawned;
	
	private int looper;
	
	public Spawn(Handler handler, HUD hud){
		this.handler = handler;
		this.hud = hud;
		coinSpawned = 0;
		scoreKeep = 0;
	}
	
	public Spawn spawnInit(Handler handler, HUD hud){
		this.handler = handler;
		this.hud = hud;
		
		coinSpawned = 0;
		
		scoreKeep = 0;
		return this;
	}
	
	public void tick(){
		scoreKeep++;
			if(CheatCode.UpGame){
				hud.setScore(hud.getScore()+500);
				scoreKeep += 500;
				CheatCode.UpGame = false;
			}
			if(scoreKeep == 1){
				handler.addObject(new Player(Game.WIDTH/2-30, Game.HEIGHT/2-30, ID.Player, handler));
				handler.addObject(new BasicEnemy(Game.WIDTH / 2 + 40, Game.HEIGHT / 2 - 30, ID.Enemy, Color.red, handler));
			}
			if(scoreKeep == 500){
				hud.setWave(2);
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 20), r.nextInt(Game.HEIGHT - 46), ID.Enemy, Color.yellow, handler));                                  //BasicEnemy
			}
			
			if(scoreKeep == 1000){
				hud.setWave(3);
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 20), r.nextInt(Game.HEIGHT - 50), ID.Enemy, Color.magenta, handler));                                 //BasicEnemy
			}
			
			if(scoreKeep == 1500){
				hud.setWave(4);
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 20), r.nextInt(Game.HEIGHT - 55), ID.Enemy, Color.pink, handler));                                    //BasicEnemy
			}
			
			if(scoreKeep == 2000){
				hud.setWave(5);
				handler.addObject(new LeftRigthEnemy(r.nextInt(Game.WIDTH - 22), Game.HEIGHT - 44, ID.Enemy, Color.orange, handler));                                     //LeftRightEnemy (BOTTOM)
				handler.addObject(new LeftRigthEnemy(r.nextInt(Game.WIDTH - 22), 0, ID.Enemy, Color.orange, handler));                                                    //LeftRightEnemy (TOP)
			}
			
			if(scoreKeep == 2500){
				hud.setWave(6);
				handler.addObject(new UpDownEnemy(0, r.nextInt(Game.HEIGHT - 45), ID.Enemy, Color.orange, handler));                                                         //UpDownEnemy (LEFT)
				handler.addObject(new UpDownEnemy(Game.WIDTH - 22, r.nextInt(Game.HEIGHT - 45), ID.Enemy, Color.orange,  handler));                                          //UpDownEnemy (Right)
			}
			
			if(scoreKeep == 3000){
				hud.setWave(7); 
				handler.addObject(new VerticallyFastEnemy(r.nextInt(Game.WIDTH - 20), r.nextInt(Game.HEIGHT - 42), ID.Enemy, Color.cyan, handler));                  //VerticallyFastEnemy
			}
				
			if(scoreKeep == 3500){
				hud.setWave(8);  
				handler.addObject(new HorizontallyFastEnemy(r.nextInt(Game.WIDTH - 20), r.nextInt(Game.HEIGHT - 42), ID.Enemy, Color.green, handler));             //HorizontallyFastEnemy
			}
				
			if(scoreKeep == 4000){
				hud.setWave(9);   
				handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 20), r.nextInt(Game.HEIGHT - 42), ID.Enemy, Color.lightGray, handler));                               //SmartEnemy
			}
			
			if(scoreKeep == 4500){
				hud.setWave(10);  
				handler.clearEnemies();
				handler.addObject(new EnemyBoss1(Game.WIDTH / 2 + 38, -155, ID.Enemy, handler));                                                                              //EnemyBoss1
			}
			
			if(scoreKeep == 6000){
				hud.setWave(11);
				handler.clearEnemies();
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH + 20), r.nextInt(Game.HEIGHT + 46), ID.Enemy, Color.red, handler));
				for(int i = 0; i < handler.object.size(); i++){
					GameObject tempObject = handler.object.get(i);
					if(tempObject.id == ID.Player){
						Player player = (Player)tempObject;
						player.resetPos();
					}
				}
			}
			
			if(scoreKeep >= 7000 && scoreKeep % 1000 == 0){
				wave();
			}
			
			if(randomSpawn(20) && randomSpawn(10) && coinSpawned < 10){
				handler.addObject(new Coins(r.nextInt(Game.WIDTH - 1), r.nextInt(Game.HEIGHT - 1),ID.Coin, handler));
				coinSpawned++;
			}
			
			//
	}
	
	public void wave(){
		hud.increaseWave();
		switch(r.nextInt(5)){
		
		case 0:
			handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - (r.nextInt(45))), r.nextInt(Game.HEIGHT - (r.nextInt(42))), ID.Enemy, rand0mColor(), handler));
		case 1:
			handler.addObject(new UpDownEnemy(r.nextInt(Game.WIDTH - (r.nextInt(45))), r.nextInt(Game.HEIGHT - (r.nextInt(42))), ID.Enemy, rand0mColor(), handler));
		case 2: 
			handler.addObject(new LeftRigthEnemy(r.nextInt(Game.WIDTH - (r.nextInt(45))), r.nextInt(Game.HEIGHT - (r.nextInt(42))), ID.Enemy, rand0mColor(), handler));
		case 3: 
			handler.addObject(new HorizontallyFastEnemy(r.nextInt(Game.WIDTH - (r.nextInt(45))), r.nextInt(Game.HEIGHT - (r.nextInt(42))), ID.Enemy, rand0mColor(), handler));
		case 4: 
			handler.addObject(new VerticallyFastEnemy(r.nextInt(Game.WIDTH - (r.nextInt(45))), r.nextInt(Game.HEIGHT - (r.nextInt(42))), ID.Enemy, rand0mColor(), handler));
		case 5:
			handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - (r.nextInt(45))), r.nextInt(Game.HEIGHT - (r.nextInt(42))), ID.Enemy, rand0mColor(), handler));
		}
	}
	public Color rand0mColor(){
		switch(r.nextInt(8)){
		case 0 : return Color.green;
		case 1: return Color.blue;
		case 2: return Color.cyan;
		case 3: return Color.orange;
		case 4: return Color.yellow;
		case 5: return Color.lightGray;
		case 6: return Color.magenta;
		case 7: return Color.red;
		}
		return Color.white;
	}
	
	public boolean randomSpawn(int n){
		return (r.nextInt() % n == 0);		
	}
	
	public static void setCoinSpawned(int s){
		coinSpawned = s;
	}
	
	public static int getCoinSpawned(){
		return coinSpawned;
	}
}
