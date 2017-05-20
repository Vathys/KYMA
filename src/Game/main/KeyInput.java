package Game.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import Enemies.GameObject;
import Enemies.ID;
import Game.main.Game.STATE;

public class KeyInput extends KeyAdapter{
	
	private Game game;
	private Handler handler;
	private boolean[] keyDown = new boolean[4];
	boolean keyed;
	boolean cheatCodeActive = false;
	boolean unlimit = false;
	double exp = 0;
	int curChange = 0;
	boolean exit = false;
	public KeyInput(Game game, Handler handler){
		
		this.handler = handler;
		this.game = game;
		keyDown = memset(keyDown, 4, false);
		keyed = false;
		cheatCodeActive = false;
	}

	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(game.gameState == STATE.GAME){  //WHEN IN GAME
			for(int i = 0; i < handler.object.size(); i++){
				GameObject tempObject = handler.object.get(i);
				
				if(tempObject.getID() == ID.Player && game.gameState == STATE.GAME){                 //All Key Events For Player 1
					
					if(key == KeyEvent.VK_W){ 
						tempObject.setVelY((int)Player.getSpeed() * -1); keyDown[0] = true; 
						//System.out.println("UP");
					}
					if(key == KeyEvent.VK_S){ 
						tempObject.setVelY((int)Player.getSpeed()); keyDown[1] = true; 
						//System.out.println("DOWN");
					}
					if(key == KeyEvent.VK_D){ 
						tempObject.setVelX((int)Player.getSpeed()); keyDown[2] = true;
						//System.out.println("RIGHT");
					}
					if(key == KeyEvent.VK_A){ 
						tempObject.setVelX((int)Player.getSpeed() * -1); keyDown[3] = true;
						//System.out.println("LEFT");
					} 
					if(CheatCode.checkCode(key) && cheatCodeActive == false){
						HUD.HPLOSS *= -1;
						cheatCodeActive = true;
					}
					if(CheatCode.exit(key) && cheatCodeActive == true) {
						HUD.HPLOSS *= -1;
						cheatCodeActive = false;
					}
				}
			}
			if(key == KeyEvent.VK_P){
				if(keyed == false){
					keyed = true;
					game.gameState = STATE.PAUSE;
				} else if(keyed == true){
					keyed = false;
					game.gameState = STATE.REVERSEHIDE;
				}
				
			}
			if(key == KeyEvent.VK_E && game.gameState == STATE.GAME){
				Menu.rendered = false;
				game.gameState = STATE.END;
			}
		} else if(game.gameState == STATE.PAUSE){
			if(key == KeyEvent.VK_P){ 
				if(keyed == false){
					keyed = true;
					game.gameState = STATE.PAUSE;
				} else if(keyed == true){
					keyed = false;
					game.gameState = STATE.REVERSEHIDE;
				}
				
			}
		}else if(game.gameState == STATE.SHOP){
			if(CheatCode.checkCode(key) && cheatCodeActive == false){
				cheatCodeActive = true;
			}
			if(cheatCodeActive == true && !CheatCode.exit(key)){
				if(CheatCode.numbers(key) > 0){
					curChange += CheatCode.numbers(key) * Math.pow(10.0, exp);
				}else if(CheatCode.numbers(key) == 0){
					if(curChange == 0){
						curChange += 0;
					}else{
						curChange += Math.pow(10.0, exp);
					}
				}else{
					exit = true;
				}
				exp++;
			}
			if(CheatCode.exit(key)){
				exit = true;
			}
			if(exit && cheatCodeActive == true){
				Menu.increaseCurrence(curChange);
				curChange = 0;
				exp = 0;
				exit = false;
				cheatCodeActive = false;
			}
		}
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getID() == ID.Player){                       //All Key Events For Player 1
				
				if(key == KeyEvent.VK_W) keyDown[0] = false;           //tempObject.setVelY(0);
				if(key == KeyEvent.VK_S) keyDown[1] = false;           //tempObject.setVelY(0);
				if(key == KeyEvent.VK_D) keyDown[2] = false;           //tempObject.setVelX(0);
				if(key == KeyEvent.VK_A) keyDown[3] = false;           //tempObject.setVelX(0);
				
				if(!keyDown[0] && !keyDown[1]) tempObject.setVelY(0);  //Vertical Movement
				if(!keyDown[2] && !keyDown[3]) tempObject.setVelX(0);  //Horizontal Movement
			}
		}
	}
	
	public static boolean [] memset(boolean[] b, int bSIZE, boolean value){
		for(int i = 0; i < bSIZE; i++){
			b[i] = value;
		}
		return b;
	}
}
