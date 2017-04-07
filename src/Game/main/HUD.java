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
	private int score = 0;
	private int wave = 1;
	private Font J;
	private Font INFO;
	private FontMetrics J_SIZE;
	
	
	public HUD(Game game, Handler handler){
		this.game = game;
		this.handler = handler;
		
		mouseDown[0] = true;
		mouseDown[1] = false;
		
		try{
			J = J.createFont(Font.TRUETYPE_FONT, new File("res/Fonts/Justo St.ttf"));
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
		
		INFO = J.deriveFont(Font.BOLD, 10);

		J_SIZE = g.getFontMetrics(J);
		
		g.setFont(INFO);
		
		g.drawString("Score: " + score, 18, 64);
		g.drawString("Wave: " + wave, 19, 80);
	}
	
	
	public void setScore(int score){                       //MUTATOR (SETTER)
		this.score = score;
	}
	
	public int getScore(){                                 //ACCESSOR (GETTER)
		return score;
	}
	
	public void setWave(int level){                       //MUTATOR (SETTER)
		this.wave = level;
	}
	
	public int getWave(){                                 //ACCESSOR (GETTER)
		return wave;
	}
	
	public void increaseWave(){
		this.wave += 1;
	}
}
