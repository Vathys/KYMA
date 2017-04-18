package Game.main;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

import org.newdawn.slick.Image;

public class Window extends Canvas{

	private static final long serialVersionUID = -1864215488270890922L;
	
	private JFrame frame;
	
	public Window(int width, int height, String title, Game game){
		frame = new JFrame(title);                            // Create New JFrame frame
		
		Toolkit toolKit = Toolkit.getDefaultToolkit();
	    java.awt.Image img = toolKit.getImage("res/Cursors/Cursor_White.png");
	    Cursor cursor = toolKit.createCustomCursor(img, new Point(0, 0), "Pencil");

		frame.setPreferredSize(new Dimension(width, height));        // Set Frame Dimensions
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        // Makes Sure That Top Right X Works
		frame.setResizable(false);                                   // Not Resizable
		frame.setLocationRelativeTo(null);                           // Frame Positioned in Middle of Screen
		frame.add(game);                                             // Adding game Class Into Frame
		
		//frame.setCursor(cursor);
		frame.setVisible(true);                                      // Can Actually See The Frame
		game.start();                                                // Runs Game Start Method
	}
	
	public JFrame getFrame(){
		return frame;
	}
}