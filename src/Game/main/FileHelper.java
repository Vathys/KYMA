package Game.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHelper {
	protected static String dir = "res/";
	protected static FileReader fRead;
	protected static BufferedReader bRead;
	protected static Scanner sc;
	protected static FileWriter fWrite;
	protected static BufferedWriter bWrite;
	private int highestScore = 0;
	private int highestWave = 0;
	
	
	public static void writeFile(String n, File file) throws IOException{
		
		fWrite = new FileWriter(file);
		bWrite = new BufferedWriter(fWrite);
		bWrite.write(n);
		bWrite.newLine();
		
		bWrite.close();
		fWrite.close();
	}
	
	public static boolean FileExists(String FileName){
		File tempDir = new File(dir+FileName);
		return tempDir.exists();
	}

	public ArrayList<String> readFile(String FileName){
		if(!FileExists(FileName)){
			return null;
		}
		ArrayList<String> create = new ArrayList <String>();
		try{
			fRead = new FileReader(dir + FileName);
			bRead = new BufferedReader(fRead);
			sc = new Scanner(fRead);
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				create.add(line);
			}
			sc.close();
			bRead.close();
			fRead.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
		return create;
	}

	public void writeCurrentGameData(File file){
		//Highest Score
		if(HUD.getScore() > highestScore){
			highestScore = HUD.getScore();
		}
		//Highest Wave
		if(HUD.getWave() > highestWave){
			highestWave = HUD.getWave();
		}
		//Health
		
		//Speed
		//Coin val
		//Defense
		

		//Skin
		//Cursor
		
	}
}