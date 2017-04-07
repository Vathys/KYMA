package Enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;

import Game.main.Game;
import Game.main.Handler;

public class Coins extends GameObject {
	
	private Handler handler;
	ArrayList<Image> Coin = new ArrayList<Image>();
	
	public Coins(float x, float y, ID id, Handler handler){
		super(x, y, id);
		x = Game.clamp(x, 0, Game.WIDTH - 20);
		y = Game.clamp(y, 0, Game.HEIGHT - 20);
		this.handler = handler;

		Toolkit toolKit = Toolkit.getDefaultToolkit();
		for(int i = 1; i <= 6; i++){
			String temp = "res/Coin Layer/Layer " + i + ".png";
			Coin.add(toolKit.getImage(temp));
		}
		
		counter = new int[2];
		counter[0] = 1;
		counter[1] = 0;
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if(counter[1] == 0)
			counter[0]++;
		
		if(counter[0] > 6){
			counter[0] = 1;
		}
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(Coin.get(counter[0] - 1), (int)x, (int)y, 40, 40, null, null);
	}

	@Override
	public void hide(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.BLACK);
		g.fillRect((int)x, (int)y, 40, 40);
	}

	@Override
	public void restoreVel() {
		// TODO Auto-generated method stub
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle((int)x, (int)y, 40, 40);
	}

}
