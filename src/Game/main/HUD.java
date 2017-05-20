package Game.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import Game.main.Game.STATE;

public class HUD{

	private Game game;
	private Handler handler;
	private float saved;
	public static float greenHEALTH = 1000;
	public static float blueHEALTH = 0;      //When At Max = 1000
	private float greenValue = 255;
	private float blueValue = 255;
	public static int HPLOSS = -115;
	
	private boolean[] mouseDown = new boolean[2];
	private static int score = 0;
	private static int wave = 1;
	private Font J, MU;
	private Font HUD, BOSS, COUNTDOWN;
	private FontMetrics J_SIZE, MU_SIZE;
	
	static boolean timer = false;
	int sleepTime_1 = 300; 
	int sleepTime_2 = 300;
	int repeater = 3;
	
	public static int BossStage = 0;
	
	int countdownTime_1 = 1800;
	
	public HUD(Game game, Handler handler){
		this.game = game;
		this.handler = handler;
		
		mouseDown[0] = true;
		mouseDown[1] = false;
		
		try{
			J = J.createFont(Font.TRUETYPE_FONT, new File("res/Fonts/Justo St.ttf"));
		}
		catch(Exception e){}
		
		try{
			MU = MU.createFont(Font.TRUETYPE_FONT, new File("res/Fonts/Mestizos Unidos.otf"));
		}
		catch(Exception e){}
	}
	
	public HUD hudInit(Handler handler){
		this.handler = handler;
		
		greenHEALTH = 1000;
		saved = blueHEALTH;
		greenValue = 225;
		blueValue = 225;
		return this;
	}
	
	public boolean tick(){
		greenHEALTH = Game.clamp(greenHEALTH, 0, 1000);
		blueHEALTH = Game.clamp(blueHEALTH, 0, 1000);
		
		greenValue = Game.clamp(greenValue, 0, 255);
		greenValue = greenHEALTH / 5;
		blueValue = Game.clamp(blueValue, 0, 255);
		blueValue = blueHEALTH / 5;
	
		if(BossStage == 0)
			score++;
		if(greenHEALTH == 0){
			blueHEALTH = saved;
			greenHEALTH = 1000;
			return true;
		}
		return false;
	}
	
	public void render(Graphics g){
		
		g.setColor(Color.gray);
		g.fillRect(15, 15, 300, 32);
		
		g.setColor(new Color(75, (int)greenValue, 0));
		g.fillRect(15,  15,  (int)(greenHEALTH / 10) * 3, 32);
		
		g.setColor(new Color(0, 160, (int)blueValue));
		g.fillRect(15, 15, (int)(blueHEALTH / 10) * 3, 32);
		
		g.setColor(Color.white);
		g.drawRect(15, 15, 300, 32);
		
		HUD = J.deriveFont(Font.BOLD, 12);
		BOSS = MU.deriveFont(Font.BOLD, 100);
		COUNTDOWN = MU.deriveFont(Font.BOLD, 60);
		
		J_SIZE = g.getFontMetrics(HUD);
		MU_SIZE = g.getFontMetrics(BOSS);
		
		g.setFont(HUD);

		g.drawString("Score: " + score, 18, 64);
		g.drawString("Wave: " + wave, 19, 80);
		
		g.setFont(COUNTDOWN);  //COUNTDOWN
		g.setColor(Color.red);
		
		if(score >= 4400 && score <= 4500){
			if(score >= 4400 && score <= 4433){
				g.drawString("3", Game.WIDTH - 70, Game.HEIGHT - 680);
			}
			if(score > 4433 && score <= 4466){
				g.drawString("2", Game.WIDTH - 70, Game.HEIGHT - 680);
			}
			if(score > 4466  && score < 4500){
				g.drawString("1", Game.WIDTH - 70, Game.HEIGHT - 680);
			}
		}
		
		g.setFont(BOSS);  //BOSS STAGE
		if(repeater > 0 && wave == 10){
			BossStage = 1;
		
			g.drawString("BOSS STAGE", (Game.WIDTH / 2) - MU_SIZE.stringWidth("BOSS STAGE") / 2, Game.HEIGHT / 2);
			
			if(sleepTime_1 > 0){
				timer = false;
				sleepTime_1--;
			}
		
			if(sleepTime_1 <= 0 ){
				timer = true;
				sleepTime_2--;
			}
		
			if(sleepTime_2 <= 0){
				timer = false;
				sleepTime_1 = 300;
				sleepTime_2 = 300;
				repeater--;
			}
		
			if(timer == true){
				g.setColor(Color.BLACK);
				g.fillRect((Game.WIDTH / 2) - MU_SIZE.stringWidth("BOSS STAGE") / 2 - 20, Game.HEIGHT / 2 - 95, 685, 110);
			}
		} else{
			if(repeater <= 0) BossStage = 0;
		}
	}
	
	public static void setScore(int sc){                       //MUTATOR (SETTER)
		score = sc;
	}
	
	public static int getScore(){                                 //ACCESSOR (GETTER)
		return score;
	}
	
	public static void setWave(int level){                       //MUTATOR (SETTER)
		wave = level;
	}
	
	public static int getWave(){                                 //ACCESSOR (GETTER)
		return wave;
	}
	
	public void increaseWave(){
		wave += 1;
	}
}
