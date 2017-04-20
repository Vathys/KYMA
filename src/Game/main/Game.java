package Game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import Enemies.EnemyBoss1;
import Enemies.ID;
import Enemies.ParticleEffect;
import Enemies.Spawn;
public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -4201078827097160085L;
	
	public static final int WIDTH = 1000;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static int FPStrace = 20; // Modify to modify FPS
	public static boolean gameOver = false;
	public static boolean gameRun = false;
	private Thread thread;
	private boolean running = false;
	
	private Random r;
	private Handler handler;                                       //UPDATES AND RENDERS EVERY SINGLE GAME OBJECT
	
	private HUD hud;
	private Spawn spawner;
	
	public Menu menu;
	
	public enum STATE{
		NULL,
		MENU,
		INFO,
		EXTRA,
		SHOP,
		END,
		GAME,
		PAUSE,
		REVERSEHIDE
	};
	
	public STATE gameState;
	
	public Game(){
		AudioPlayer.load();
		AudioPlayer.getMusic("Menu_Music").loop();
		
		handler = new Handler();
		hud = new HUD(this, handler);
		spawner = new Spawn(handler, hud);
		menu = new Menu(this, handler, hud);
		r = new Random();
		
		this.addKeyListener(new KeyInput(this, handler));
		this.addMouseListener(menu);
		
		new Window(WIDTH, HEIGHT, "Kyma 3.0.0 (BETA)", this);
		gameState = STATE.MENU;
	}
	
	public void inGame(){
		gameOver = false;
		handler.clear();
		hud = hud.hudInit(handler);
		menu = menu.MenuInit(handler, hud);
		spawner = spawner.spawnInit(handler, hud);
	}

	public synchronized void start(){
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop(){
		try{
			thread.join();
			running = false;
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running){
			 long now = System.nanoTime();
			 delta += (now - lastTime) / ns;
			 lastTime = now;
			 while(delta >= 1){
				 tick();
				 delta--;
			 }
			 if(running)
				 render();
			 frames++;
			 
			 if(System.currentTimeMillis() - timer > 1000){
				 timer += 1000;
				 System.out.println("FPS: " + frames);
				 frames = 0;
			 }
			 try{
				 Thread.sleep(FPStrace);
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		 }
		 stop();
	}
	
	private void tick(){
		
		if(gameState == STATE.END){
			if(gameOver){
				inGame();
				gameState = STATE.MENU;
				gameOver = false;
			}
		}
		
		if(gameState == STATE.MENU || gameState == STATE.END || gameState == STATE.INFO || gameState == STATE.EXTRA || gameState == STATE.SHOP) {
			menu.particleHand.tick();
			menu.tick();
		}
		
		else if(gameState == STATE.GAME){
			if(!gameRun) handler.clear();
			gameRun = true;
			spawner.tick();
			handler.tick();
			if(hud.tick()){
				Menu.rendered = false;
				gameState = STATE.END;
			}
		}
		else if(gameState == STATE.PAUSE){
			menu.pauseTick();
		}
	}
	
	private void render(){                              //BACKGROUND
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if(gameState == STATE.MENU || gameState == STATE.INFO || gameState == STATE.EXTRA || gameState == STATE.SHOP){
			handler.render(g);
			menu.render(g);
		}
		
		if(gameState == STATE.END){
			handler.render(g);
			menu.render(g);
		}
		
		if(gameState == STATE.GAME){
			handler.render(g);
			hud.render(g);		
		}
		if(gameState == STATE.PAUSE){ 
			menu.render(g);
		}
		if(gameState == STATE.REVERSEHIDE){
				handler.restore(g);
				FPStrace = 1;
				gameState = STATE.GAME;
		}
		
		
		g.dispose();
		bs.show();
	}
	
	public static float clamp(float var, float min, float max){
		if(var >= max) return var = max;
		else if(var <= min) return var = min;
		else return var;
	}
	
	public static void main(String args[]){
		new Game();
	}
	
}
