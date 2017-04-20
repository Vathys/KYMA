package Game.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.image.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import Game.main.Game.STATE;
import Enemies.BasicEnemy;
import Enemies.Coins;
import Enemies.GameObject;
import Enemies.ParticleEffect;

public class Menu extends MouseAdapter{

	private Game game;
	private Handler handler;
	private Random r = new Random();
	private boolean[] mouseDown = new boolean[6];
	private Font MU;
	private Font AA;
	private Font JU;
	private FontMetrics MU_SIZE;
	private FontMetrics JU_SIZE;
	private Font title;
	private Font mainMenu;
	private STATE pre;
	private HashMap<String, Triangle> tList;
	private static int currency;
	private int change;
	public static boolean rendered;
	public Handler particleHand;
	private ParticleEffect p;
	private HUD hud;
	private int HP_CHANGE = 0, SPEED_CHANGE = 0, DEFENSE_CHANGE = 0, COIN_CHANGE = 0;
	private int HP_COST = 0, SPEED_COST = 0, DEFENSE_COST = 0, COIN_COST = 0;
	public FileHelper f = new FileHelper();
	
	public Menu(Game game, Handler handler, HUD hud){
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		
		mouseDown[0] = true; // When in Menu
		mouseDown[1] = false; // When in End State
		mouseDown[2] = false; // When in Extra
		mouseDown[3] = false; // When in Info
		mouseDown[4] = false; // When in Shop
		mouseDown[5] = false; // When in Save
		 
		try{
			MU = MU.createFont(Font.TRUETYPE_FONT, new File("res/Fonts/Mestizos Unidos.otf"));
		}
		catch(Exception e){}
		try{
			AA = AA.createFont(Font.TRUETYPE_FONT, new File("res/Fonts/aaron.ttf"));
		}
		catch(Exception e){}
		try{
			JU = JU.createFont(Font.TRUETYPE_FONT, new File("res/Fonts/Justo St.ttf"));
		}
		catch(Exception e){}
		
		
		title = MU.deriveFont(Font.BOLD, 100);
		mainMenu = MU.deriveFont(Font.PLAIN, 75);
		pre = STATE.NULL;
		tList = new HashMap<String, Triangle>();
		rendered = false;
		Player.setSpeed(5);
		Player.setDefense(0);
		particleHand = new Handler();
		p = new ParticleEffect(game, particleHand);
		p.render();
	}
	
	public Menu MenuInit(Handler handler, HUD hud){
		this.handler = handler;
		this.hud = hud;
		
		mouseDown[0] = true; // When in Menu
		mouseDown[1] = false; // When in End State
		mouseDown[2] = false; // When in Extra
		mouseDown[3] = false; // When in Info
		mouseDown[4] = false; // When in Shop
		mouseDown[5] = false; // When in Save
		pre = STATE.NULL;
		return this;
	}
	
	public void mousePressed(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		
		if(game.gameState != STATE.GAME && game.gameState != STATE.PAUSE){
			
			if(mouseDown[0] == true){ // when in Menu
				//PLAY BUTTON
				if(mouseOver(mx, my, Game.WIDTH/4, Game.HEIGHT * 2/6 - (MU_SIZE.getHeight() / 2) - 40, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20)){
					
					handler.clear(); // INSTA DEATH
					HUD.setScore(0);
					HUD.setWave(1);
					Game.FPStrace = 0;
					
					game.gameState = STATE.GAME;
					mouseDown[0] = false;
					mouseDown[1] = true;
					mouseDown[2] = false;
					mouseDown[3] = false;
					mouseDown[4] = false;
					mouseDown[5] = false;
				}	
				
				//INFO BUTTON
				if(mouseOver(mx, my, Game.WIDTH/4, Game.HEIGHT * 3/6 - (MU_SIZE.getHeight() / 2) - 40, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20)){
					game.gameState = STATE.INFO;
					rendered = false;
					mouseDown[0] = false;
					mouseDown[1] = false;
					mouseDown[2] = false;
					mouseDown[3] = true;
					mouseDown[4] = false;
					mouseDown[5] = false;
				}
				
				//EXTRA BUTTON
				if(mouseOver(mx, my, Game.WIDTH/4, Game.HEIGHT * 4/6 - (MU_SIZE.getHeight() / 2) - 40, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20)){
					game.gameState = STATE.EXTRA;
					rendered = false;
					mouseDown[0] = false;
					mouseDown[1] = false;
					mouseDown[2] = true;
					mouseDown[3] = false;
					mouseDown[4] = false;
					mouseDown[5] = false;
				}
				
				//EXIT BUTTON
				if(mouseOver(mx, my, Game.WIDTH/4, Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2) - 40, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20)){
					System.exit(1);
				}
			} else if(mouseDown[1] == true){ // when in End state
				//SHOP BUTTON
				if(mouseOver(mx, my, Game.WIDTH/4, Game.HEIGHT * 3/6 - (MU_SIZE.getHeight() / 2), Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20)){
					game.gameState = STATE.SHOP;
					pre = STATE.END;
					rendered = false;
					mouseDown[0] = false;
					mouseDown[1] = false;
					mouseDown[2] = false;
					mouseDown[3] = false;
					mouseDown[4] = true;
					mouseDown[5] = false;
				}
				//RETRY BUTTON
				if(mouseOver(mx, my, Game.WIDTH/4, Game.HEIGHT * 4/6 - (MU_SIZE.getHeight() / 2), Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20)){
					handler.clear();
					
					HUD.setScore(0);
					HUD.setWave(1);
					Game.FPStrace = 0;
					
					game.inGame();
					game.gameState = STATE.GAME;
					
					mouseDown[0] = false;
					mouseDown[1] = true;
					mouseDown[2] = false;
					mouseDown[3] = false;
					mouseDown[4] = false;
					mouseDown[5] = false;
				}
				//BACK BUTTON
				if(mouseOver(mx, my, Game.WIDTH/4, Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2), Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20)){
					Game.gameOver = true;
					rendered = false;
					mouseDown[0] = true;
					mouseDown[1] = false;
					mouseDown[2] = false;
					mouseDown[3] = false;
					mouseDown[4] = false;
					mouseDown[5] = false;
				}
				
				
			} else if(mouseDown[2] == true){ // when in Extra
				//SAVE BUTTON
				if(mouseOver(mx, my, Game.WIDTH/4, Game.HEIGHT * 3/6 - (MU_SIZE.getHeight() / 2) - 40, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20)){
					game.gameState = STATE.SAVE;
					
					mouseDown[0] = false;
					mouseDown[1] = false;
					mouseDown[2] = false;
					mouseDown[3] = false;
					mouseDown[4] = false;
					mouseDown[5] = true;
				}
				
				//SHOP BUTTON
				if(mouseOver(mx, my, Game.WIDTH/4, Game.HEIGHT * 4/6 - (MU_SIZE.getHeight() / 2) - 40, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20)){
					game.gameState = STATE.SHOP;
					pre = STATE.EXTRA;
					rendered = false;
					mouseDown[0] = false;
					mouseDown[1] = false;
					mouseDown[2] = false;
					mouseDown[3] = false;
					mouseDown[4] = true;
					mouseDown[5] = false;
				}
				//BACK BUTTON
				if(mouseOver(mx, my, Game.WIDTH/4, Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2) - 40, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20)){
					game.gameState = STATE.MENU;
					rendered = false;
					mouseDown[0] = true;
					mouseDown[1] = false;
					mouseDown[2] = false;
					mouseDown[3] = false;
					mouseDown[4] = false;
					mouseDown[5] = false;
				}
			} else if(mouseDown[3] == true){ // when in Info
				//BACK BUTTON
				if(mouseOver(mx, my, Game.WIDTH/4, Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2) - 40, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20)){
					game.gameState = STATE.MENU;
					rendered = false;
					mouseDown[0] = true;
					mouseDown[1] = false;
					mouseDown[2] = false;
					mouseDown[3] = false;
					mouseDown[4] = false;
					mouseDown[5] = false;
				}
			} else if(mouseDown[4] == true){ // when in Shop
				//BACK BUTTON
				if(mouseOver(mx, my, Game.WIDTH/4, Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2), Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20)){
					if(pre == STATE.END){
						game.gameState = STATE.END;
						rendered = false;
						mouseDown[0] = false;
						mouseDown[1] = true;
						mouseDown[2] = false;
						mouseDown[3] = false;
						mouseDown[4] = false;
						mouseDown[5] = false;

						pre = STATE.NULL;
					} else if(pre == STATE.EXTRA){
						game.gameState = STATE.EXTRA;
						rendered = false;
						mouseDown[0] = false;
						mouseDown[1] = false;
						mouseDown[2] = true;
						mouseDown[3] = false;
						mouseDown[4] = false;
						mouseDown[5] = false;

						pre = STATE.NULL;
					} 
				}
				for(String itt : tList.keySet()){
					if(tList.get(itt).contains(mx,  my)){
						if(itt.substring(0, 3).equals("inc")){
							change = 1;
						} else if(itt.substring(0, 3).equals("dec")){
							change = -1;
						}
						
						int i = itt.lastIndexOf('_');
						int current;
						if(itt.charAt(0) != '1' && itt.charAt(0) != '2'){
							if(itt.substring(i + 1, itt.length()).equals("Health")){
								
								HP_CHANGE = (int)Game.clamp(HP_CHANGE + (10 * change), 0, 1000 - HUD.blueHEALTH);
								current = ((int) (HUD.blueHEALTH)) + HP_CHANGE;
								HP_COST = getCost(current, "res/Costs/Health_Cost.txt");
							
								
							} else if(itt.substring(i + 1, itt.length()).equals("Speed")){
								
								SPEED_CHANGE = (int)Game.clamp(SPEED_CHANGE + change, 0, 20 - Player.getSpeed());
								current = ((int) (Player.getSpeed() - 5)) + SPEED_CHANGE;
								SPEED_COST = getCost(current, "res/Costs/Speed_Cost.txt");
								
							} else if(itt.substring(i + 1, itt.length()).equals("Defense")){
								
								DEFENSE_CHANGE = (int)Game.clamp(DEFENSE_CHANGE + change, 0, 100 - Player.getDefense());
								current = ((int) (Player.getDefense())) + DEFENSE_CHANGE;
								DEFENSE_COST = getCost(current, "res/Costs/Defense_Cost.txt");
								
							} else if(itt.substring(i + 1, itt.length()).equals("Coin")){
								
								COIN_CHANGE = (int)Game.clamp(COIN_CHANGE + change, 0, 5 - Player.getCoinFactor());
								current = COIN_CHANGE;
								COIN_COST = getCost(current, "res/Costs/Coin_Cost.txt");
								
							}
						}
					}
				}
				
				//DEEP
				if(mouseOver(mx, my, 715, 172 - 35, 215, 70)){  //HEALTH APPLY
					if(currency >= HP_COST){
						currency -= HP_COST;
						HUD.blueHEALTH += HP_CHANGE;
						HP_CHANGE = 0;
						HP_COST = 0;
					}
					//COSTS THE LEAST
					
				}
				
				if(mouseOver(mx, my, 715, 279 - 35, 215, 70)){  //SPEED APPLY
					if(currency >= SPEED_COST){
						currency -= SPEED_COST;
						Player.setSpeed(Player.getSpeed() + SPEED_CHANGE);
						SPEED_CHANGE = 0;
						SPEED_COST = 0;
					} 
				}
				
				if(mouseOver(mx, my, 715, 386 - 35, 215, 70)){  //DEFENSE APPLY
					if(currency >= DEFENSE_COST){
						currency -= DEFENSE_COST;
						Player.setDefense(Player.getDefense() + DEFENSE_CHANGE);
						DEFENSE_CHANGE = 0;
						DEFENSE_COST = 0;
					}
					//MORE EXPENSIVE THAN HEALTH AND LESS EXPENSIVE THAN SPEED
					
				}
				
				if(mouseOver(mx, my, 715, 493 - 35, 215, 70)){  //COINS APPLY
					if(currency >= COIN_COST){
						currency -= COIN_COST;
						Player.setCoinfactor(Player.getCoinFactor() + COIN_CHANGE);
						COIN_CHANGE = 0;
						COIN_COST = 0;
					}
				}
			}
			else if(mouseDown[5] == true){ // When in Save
				if(mouseOver(mx, my, Game.WIDTH/4, Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2) - 40, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20)){
					game.gameState = STATE.EXTRA;
					mouseDown[0] = false;
					mouseDown[1] = false;
					mouseDown[2] = true;
					mouseDown[3] = false;
					mouseDown[4] = false;
					mouseDown[5] = false;
				}
			}
		}
	}
	
	public void mouseReleased(MouseEvent e){
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
		if(mx >= x && mx <= x + width){
			if(my >= y && my <= y + height){
				return true;
			} else return false; 
		} else return false;
	}
	
	public void tick(){
	}
	
	public void pauseTick(){
	}
	
	public void render(Graphics g){
		
		if(game.gameState == STATE.MENU){
			particleHand.render(g);
			
			g.setColor(Color.white);
			
			MU_SIZE = g.getFontMetrics(title);
			
			g.setFont(title);
			g.drawString("KYMA", Game.WIDTH/2 - MU_SIZE.stringWidth("KYMA") / 2, Game.HEIGHT/7);      //TITLE
			
			g.setFont(mainMenu);
			MU_SIZE = g.getFontMetrics(mainMenu); 
			
			//COSME
			g.drawRect(Game.WIDTH/4, Game.HEIGHT * 2/6 - (MU_SIZE.getHeight() / 2) - 40, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);       //PLAY
			g.drawRect(Game.WIDTH/4 -1, Game.HEIGHT * 2/6 - (MU_SIZE.getHeight() / 2) - 41, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);    //PLAY
			g.drawRect(Game.WIDTH/4 -2, Game.HEIGHT * 2/6 - (MU_SIZE.getHeight() / 2) - 42, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);    //PLAY
			g.drawString("PLAY", Game.WIDTH/2 - MU_SIZE.stringWidth("PLAY") / 2,  Game.HEIGHT * 2/6);
			
			g.drawRect(Game.WIDTH/4, Game.HEIGHT * 3/6 - (MU_SIZE.getHeight() / 2) - 40, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);       //INFO
			g.drawRect(Game.WIDTH/4 -1, Game.HEIGHT * 3/6 - (MU_SIZE.getHeight() / 2) - 41, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);    //INFO
			g.drawRect(Game.WIDTH/4 -2, Game.HEIGHT * 3/6 - (MU_SIZE.getHeight() / 2) - 42, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);    //INFO
			g.drawString("INFO", Game.WIDTH/2 - MU_SIZE.stringWidth("INFO") / 2,  Game.HEIGHT * 3/6);
			
			g.drawRect(Game.WIDTH/4, Game.HEIGHT * 4/6 - (MU_SIZE.getHeight() / 2) - 40, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);       //EXTRA
			g.drawRect(Game.WIDTH/4 -1, Game.HEIGHT * 4/6 - (MU_SIZE.getHeight() / 2) - 41, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);    //EXTRA
			g.drawRect(Game.WIDTH/4 -2, Game.HEIGHT * 4/6 - (MU_SIZE.getHeight() / 2) - 42, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);    //EXTRA
			g.drawString("EXTRA", Game.WIDTH/2 - MU_SIZE.stringWidth("EXTRA") / 2,  Game.HEIGHT * 4/6);
			
			g.drawRect(Game.WIDTH/4, Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2) - 40, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);       //EXIT
			g.drawRect(Game.WIDTH/4 -1, Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2) - 41, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);    //EXIT
			g.drawRect(Game.WIDTH/4 -2, Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2) - 42, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);    //EXIT
			g.drawString("EXIT", Game.WIDTH/2 - MU_SIZE.stringWidth("EXIT") / 2,  Game.HEIGHT * 5/6);
			
		
		}
		
		if(game.gameState == STATE.INFO){
			particleHand.render(g);
			Font fnt = MU.deriveFont(Font.PLAIN, 85);
			Font fnt2 = new Font("Papyrus", 1, 25);
			
			g.setColor(Color.white);
			
			MU_SIZE = g.getFontMetrics(fnt);
			g.setFont(fnt);
			g.drawString("INFO", Game.WIDTH/2 - MU_SIZE.stringWidth("INFO") / 2, Game.HEIGHT/7);
			
			//COSME
			MU_SIZE = g.getFontMetrics(mainMenu);      //BACK
			g.setFont(mainMenu);
			g.drawRect(Game.WIDTH/4, Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2) - 40, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);   //BACK
			g.drawRect(Game.WIDTH/4 -1, Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2) - 41, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);   //BACK
			g.drawRect(Game.WIDTH/4 -2, Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2) - 42, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);   //BACK
			g.drawString("BACK", Game.WIDTH/2 - MU_SIZE.stringWidth("BACK") / 2,  Game.HEIGHT * 5/6);
			
			//INSTRUCTIONS & INFO TEXT
			g.setFont(fnt2);
			g.drawString("Use the WASD keys to move the player and evade all enemies", 60, 175);
			g.drawString("Each bullet trail does no damage, only the bullet point", 60, 205);
			g.drawString("Every 10th wave has a unique STAGE BOSS", 60, 235);
			g.drawString("Once you defeat a boss, a checkpoint will appear", 60, 265);
			g.drawString("The final wave is wave 100", 60, 295);
			g.drawString("Have Fun!", 340, 495);
			
			//Font fnt4 = new Font("aaronfaces", 1, 60);
			Font fnt3 = AA.deriveFont(Font.PLAIN, 60);
			
			//EMOJI
			g.setColor(Color.yellow);
			g.setFont(fnt3);
			g.drawString("flx", 480, 515);
		}
		
		if(game.gameState == STATE.EXTRA){

			particleHand.render(g);
			
			g.setColor(Color.white);
			
			Font fnt = MU.deriveFont(Font.PLAIN, 85);
			MU_SIZE = g.getFontMetrics(fnt);
			g.setFont(fnt);
			
			g.drawString("EXTRA", Game.WIDTH/2 - MU_SIZE.stringWidth("EXTRA") / 2, Game.HEIGHT/7);  //EXTRA
			
			g.setFont(mainMenu);
			MU_SIZE = g.getFontMetrics(mainMenu);
			
			g.drawRect(Game.WIDTH/4, Game.HEIGHT * 3/6 - (MU_SIZE.getHeight() / 2) - 40, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);   //SAVE
			g.drawRect(Game.WIDTH/4 - 1, Game.HEIGHT * 3/6 - (MU_SIZE.getHeight() / 2) - 41, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);   //SAVE
			g.drawRect(Game.WIDTH/4 - 2, Game.HEIGHT * 3/6 - (MU_SIZE.getHeight() / 2) - 42, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);   //SAVE
			g.drawString("SAVE", Game.WIDTH/2 - MU_SIZE.stringWidth("SAVE") / 2,  Game.HEIGHT * 3/6);
			//COSME
			g.drawRect(Game.WIDTH/4, Game.HEIGHT * 4/6 - (MU_SIZE.getHeight() / 2) - 40, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);     // SHOP
			g.drawRect(Game.WIDTH/4 -1, Game.HEIGHT * 4/6 - (MU_SIZE.getHeight() / 2) - 41, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);  // SHOP
			g.drawRect(Game.WIDTH/4 -2, Game.HEIGHT * 4/6 - (MU_SIZE.getHeight() / 2) - 42, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);  // SHOP
			g.drawString("SHOP", Game.WIDTH/2 - MU_SIZE.stringWidth("SHOP") / 2,  Game.HEIGHT * 4/6);
			
			g.drawRect(Game.WIDTH/4, Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2) - 40, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);     // BACK
			g.drawRect(Game.WIDTH/4 -1, Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2) - 41, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);     // BACK
			g.drawRect(Game.WIDTH/4 -2, Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2) - 42, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);     // BACK
			g.drawString("BACK", Game.WIDTH/2 - MU_SIZE.stringWidth("BACK") / 2,  Game.HEIGHT * 5/6);
			
		}
		
		if(game.gameState == STATE.SHOP){

			particleHand.render(g);
			g.setColor(Color.white);
			
			Font fnt = MU.deriveFont(Font.PLAIN, 85);
			MU_SIZE = g.getFontMetrics(fnt);
			g.setFont(fnt);
			
			g.drawString("SHOP", Game.WIDTH/2 - MU_SIZE.stringWidth("SHOP") / 2, Game.HEIGHT/8);
			
			Font fnt2 = MU.deriveFont(Font.PLAIN, 50);
			MU_SIZE = g.getFontMetrics(fnt2);
			g.setFont(fnt2);
			
			g.drawString("HEALTH", Game.WIDTH / 35 + 10, Game.HEIGHT * 2/7 - MU_SIZE.getHeight() / 35 - 20);
			g.drawString("SPEED", Game.WIDTH / 35 + 10, Game.HEIGHT * 3/7 - MU_SIZE.getHeight() / 35 - 20);
			g.drawString("DEFENSE", Game.WIDTH / 35 + 10, Game.HEIGHT * 4/7 - MU_SIZE.getHeight() / 35 - 20);
			g.drawString("COIN", Game.WIDTH / 35 + 10, Game.HEIGHT * 5/7 - MU_SIZE.getHeight() / 35 - 20);
			
			Font stats = JU.deriveFont(Font.PLAIN, 35);
			MU_SIZE = g.getFontMetrics(stats);
			g.setFont(stats);
		
			g.drawString("" + ((int)HUD.greenHEALTH + (int)HUD.blueHEALTH) + "  PLUS  " + HP_CHANGE, Game.WIDTH / 2 - MU_SIZE.stringWidth("" + (int)HUD.greenHEALTH + "  PLUS  " + HP_CHANGE) / 2 - 5, Game.HEIGHT * 2/7 - MU_SIZE.getHeight() / 35 - 25);
			g.drawString("" + (int)Player.getSpeed() + "  PLUS  " + SPEED_CHANGE, Game.WIDTH / 2 - MU_SIZE.stringWidth("" + (int)Player.getSpeed() + "  PLUS  " + SPEED_CHANGE) / 2 - 5, Game.HEIGHT * 3/7 - MU_SIZE.getHeight() / 35 - 25);
			g.drawString("" + (int)Player.getDefense() + "  PLUS  " + DEFENSE_CHANGE, Game.WIDTH / 2 - MU_SIZE.stringWidth("" + (int)Player.getDefense() + "  PLUS  " + DEFENSE_CHANGE) / 2 - 5, Game.HEIGHT * 4/7 - MU_SIZE.getHeight() / 35 - 25);
			g.drawString("" + (int)Player.getCoinFactor()+ "  PLUS  " + COIN_CHANGE, Game.WIDTH / 2 - MU_SIZE.stringWidth("" + (int)Player.getCoinFactor() + "  PLUS  " + COIN_CHANGE) / 2 - 5, Game.HEIGHT * 5/7 - MU_SIZE.getHeight() / 35 - 25);
			
			
			Coins coin = new Coins((Game.WIDTH * 5) / 7, (Game.WIDTH) / 20, null, handler);
			coin.render(g);
			
			g.drawString("" + Menu.getCurrency(), ((Game.WIDTH * 7) / 9), (Game.WIDTH / 20) + (MU_SIZE.getHeight() * 5)/ 7);
			//inc for increase
			//dec for decrease
			
			//Health, Speed, Defense, Coin
			//new Triangle(Point, size = 15, state (either 1-points right, or 3-points left)); 
			
			tList.put("dec_Health", new Triangle(new Point(320, 172), 15, 3));
			tList.put("1dec_Health", new Triangle(new Point(321, 172), 15, 3));
			tList.put("2dec_Health", new Triangle(new Point(321, 172), 15, 3));
			tList.put("inc_Health", new Triangle(new Point(670, 172), 15, 1));
			tList.put("1inc_Health", new Triangle(new Point(671, 172), 15, 1));
			tList.put("2inc_Health", new Triangle(new Point(672, 172), 15, 1));
			tList.put("dec_Speed", new Triangle(new Point(320, 279), 15, 3));
			tList.put("1dec_Speed", new Triangle(new Point(321, 279), 15, 3));
			tList.put("2dec_Speed", new Triangle(new Point(322, 279), 15, 3));
			tList.put("inc_Speed", new Triangle(new Point(670, 279), 15, 1));
			tList.put("1inc_Speed", new Triangle(new Point(671, 279), 15, 1));
			tList.put("2inc_Speed", new Triangle(new Point(672, 279), 15, 1));
			tList.put("dec_Defense", new Triangle(new Point(320, 386), 15, 3));
			tList.put("1dec_Defense", new Triangle(new Point(321, 386), 15, 3));
			tList.put("2dec_Defense", new Triangle(new Point(322, 386), 15, 3));
			tList.put("inc_Defense", new Triangle(new Point(670, 386), 15, 1));
			tList.put("1inc_Defense", new Triangle(new Point(671, 386), 15, 1));
			tList.put("2inc_Defense", new Triangle(new Point(672, 386), 15, 1));
			tList.put("dec_Coin", new Triangle(new Point(320, 493), 15, 3));
			tList.put("1dec_Coin", new Triangle(new Point(321, 493), 15, 3));
			tList.put("2dec_Coin", new Triangle(new Point(322, 493), 15, 3));
			tList.put("inc_Coin", new Triangle(new Point(670, 493), 15, 1));
			tList.put("1inc_Coin", new Triangle(new Point(671, 493), 15, 1));
			tList.put("2inc_Coin", new Triangle(new Point(672, 493), 15, 1));
			
			for(String itt : tList.keySet()){
				g.drawPolygon(tList.get(itt));
			}
			
			g.setFont(mainMenu);
			MU_SIZE = g.getFontMetrics(mainMenu);
			
			g.drawRect(Game.WIDTH/4, Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2), Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);               // BACK
			g.drawRect(Game.WIDTH/4 -1, (Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2)) -1, (Game.WIDTH * 3 / 4 - Game.WIDTH/4), MU_SIZE.getHeight() + 20);     // BACK
			g.drawRect(Game.WIDTH/4 -2, (Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2)) -2, (Game.WIDTH * 3 / 4 - Game.WIDTH/4), MU_SIZE.getHeight() + 20);     // BACK
			g.drawString("BACK", Game.WIDTH/2 - MU_SIZE.stringWidth("BACK") / 2,  Game.HEIGHT * 5/6 + 40);
			
			Font fnt3 = MU.deriveFont(Font.PLAIN, 30);
			g.setFont(fnt3);
			
			g.drawString("SKINS", 103, 643);
			g.drawString("CURSORS", 781, 643);
			
			g.drawRect(80, 606, 150, 50);    //SKINS
			g.drawRect(79, 605, 150, 50);
			g.drawRect(78, 604, 150, 50);
			
			g.drawRect(770, 606, 150, 50);   //CURSORS
			g.drawRect(769, 605, 150, 50);
			g.drawRect(768, 604, 150, 50);
			
			g.drawPolygon(new int[] {80, 80, 23}, new int[] {574, 688, 631}, 3);      //SKINS: LEFT ARROW
			g.drawPolygon(new int[] {79, 79, 22}, new int[] {574, 688, 631}, 3);
			g.drawPolygon(new int[] {78, 78, 21}, new int[] {574, 688, 631}, 3);
			
			g.drawPolygon(new int[] {920, 920, 977}, new int[] {574, 688, 631}, 3);   //CURSORS: RIGHT ARROW
			g.drawPolygon(new int[] {919, 919, 976}, new int[] {574, 688, 631}, 3);
			g.drawPolygon(new int[] {918, 918, 975}, new int[] {574, 688, 631}, 3);
			
			g.setColor(Color.black);
			g.drawLine(80, 607, 80, 653);     //SKINS
			g.drawLine(79, 607, 79, 653);
			g.drawLine(78, 607, 78, 653);
			
			g.drawLine(920, 607, 920, 653);   //CUROSRS
			g.drawLine(919, 607, 919, 653);
			g.drawLine(918, 607, 918, 653);
			
			Font fnt4 = MU.deriveFont(Font.PLAIN, 45);
			MU_SIZE = g.getFontMetrics(fnt);
			g.setFont(fnt4);
			
			
			//COSME
			g.setColor(Color.white);
			g.drawRect(715, 172 - 35, 215, 70);  //HEALTH APPLY
			g.drawRect(714, 172 - 36, 215, 70);
			g.drawRect(713, 172 - 37, 215, 70);
			
			g.drawRect(715, 279 - 35, 215, 70);  //SPEED APPLY
			g.drawRect(714, 279 - 36, 215, 70);
			g.drawRect(713, 279 - 37, 215, 70);
			
			g.drawRect(715, 386 - 35, 215, 70);  //DEFENSE APPLY
			g.drawRect(714, 386 - 36, 215, 70);
			g.drawRect(713, 386 - 37, 215, 70);
			
			g.drawRect(715, 493 - 35, 215, 70);  //COIN APPLY
			g.drawRect(714, 493 - 36, 215, 70);
			g.drawRect(713, 493 - 37, 215, 70);
			
			g.drawString("" + HP_COST, 758, 190);
			g.drawString("" + SPEED_COST, 758, 297);
			g.drawString("" + DEFENSE_COST, 758, 404);
			g.drawString("" + COIN_COST, 758, 511);
			
			
		}
		
		if(game.gameState == STATE.PAUSE){
			handler.hide(g);
			Game.FPStrace = 20;	
			
			Font fnt = MU.deriveFont(Font.PLAIN, 85);
			MU_SIZE = g.getFontMetrics(fnt);
			g.setColor(Color.WHITE);
			g.setFont(fnt);
			g.drawString("PAUSE", Game.WIDTH/2 - MU_SIZE.stringWidth("PAUSE") / 2, /*Game.HEIGHT/7*/ Game.HEIGHT/2);
		}
		
		if(game.gameState == STATE.END){
			Game.gameRun = false;
			handler.clear();
			particleHand.render(g);
			
			g.setColor(Color.WHITE);
			Game.FPStrace = 20;
			
			MU_SIZE = g.getFontMetrics(title);
			g.setFont(title);
			
			g.drawString("GAME OVER", Game.WIDTH/2 - MU_SIZE.stringWidth("GAME OVER") / 2, Game.HEIGHT/8 + 5);
			
			Font score = JU.deriveFont(Font.PLAIN, 40);
			g.setFont(score);
			
			String tempStr = "Score: " + hud.getScore();
			g.drawString(tempStr, Game.WIDTH / 2 - 100 , Game.HEIGHT * 2/8 + 15);
			
			tempStr = "Wave: " + hud.getWave();
			g.drawString(tempStr, Game.WIDTH / 2 - 100, Game.HEIGHT * 3/8 - 5);
			
			MU_SIZE = g.getFontMetrics(mainMenu);
			g.setFont(mainMenu);
			
			//COSME
			g.drawRect(Game.WIDTH/4, Game.HEIGHT * 3/6 - (MU_SIZE.getHeight() / 2), Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);        //SHOP
			g.drawRect(Game.WIDTH/4 -1, Game.HEIGHT * 3/6 - (MU_SIZE.getHeight() / 2) +1, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);  //SHOP
			g.drawRect(Game.WIDTH/4 -2, Game.HEIGHT * 3/6 - (MU_SIZE.getHeight() / 2) +2, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);  //SHOP
			
			
			g.drawString("SHOP", Game.WIDTH/2 - MU_SIZE.stringWidth("SHOP") / 2,  Game.HEIGHT * 3/6 + 40);
			
			g.drawRect(Game.WIDTH/4, Game.HEIGHT * 4/6 - (MU_SIZE.getHeight() / 2), Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);        //RETRY
			g.drawRect(Game.WIDTH/4 -1, Game.HEIGHT * 4/6 - (MU_SIZE.getHeight() / 2) +1, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);  //RETRY
			g.drawRect(Game.WIDTH/4 -2, Game.HEIGHT * 4/6 - (MU_SIZE.getHeight() / 2) +2, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);  //RETRY
			g.drawString("RETRY", Game.WIDTH/2 - MU_SIZE.stringWidth("RETRY") / 2,  Game.HEIGHT * 4/6 + 40);
			
			g.drawRect(Game.WIDTH/4, Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2), Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);  //BACK
			g.drawRect(Game.WIDTH/4 -1, Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2) +1, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);  //BACK
			g.drawRect(Game.WIDTH/4 -2, Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2) +2, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);  //BACK
			g.drawString("BACK", Game.WIDTH/2 - MU_SIZE.stringWidth("BACK") / 2,  Game.HEIGHT * 5/6 + 40);
		}
		
		if(game.gameState == STATE.SAVE){
			g.setColor(Color.white);
			
			Font fnt = MU.deriveFont(Font.PLAIN, 85);
			MU_SIZE = g.getFontMetrics(fnt);
			g.setFont(fnt);
			
			g.drawString("SAVE", Game.WIDTH/2 - MU_SIZE.stringWidth("SAVE") / 2, Game.HEIGHT/7);
			
			g.setFont(mainMenu);
			MU_SIZE = g.getFontMetrics(mainMenu);
			
			g.drawRect(Game.WIDTH/4, Game.HEIGHT * 5/6 - (MU_SIZE.getHeight() / 2) - 40, Game.WIDTH * 3 / 4 - Game.WIDTH/4 , MU_SIZE.getHeight() + 20);
			g.drawString("BACK", Game.WIDTH/2 - MU_SIZE.stringWidth("BACK") / 2, Game.HEIGHT * 5/6);
		}
	}

	public static int getCurrency() {
		return currency;
	}

	public static void increaseCurrence(int factor) {
		currency = (int) Game.clamp(currency + factor, 0, 999999);
	}

	public Integer getCost(int id, String filename){
		ArrayList <String> s  = new ArrayList <String>();
		s = f.readFile(filename);
		Integer e = new Integer(0);
		String now;
		for(int i = 0; i < s.size(); i++){
			now = s.get(i);
			e = Integer.valueOf(now.substring(0, now.indexOf('$')));
			if(e.equals(Integer.valueOf(id))){
				return Integer.valueOf(now.substring(now.indexOf('$') + 1).trim());
			}
		}
		return Integer.MIN_VALUE;
	}
}