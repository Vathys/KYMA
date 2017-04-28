package Game.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHelper {
	
	protected static FileReader fRead;
	protected static BufferedReader bRead;
	protected static Scanner sc;
	protected static FileWriter fWrite;
	protected static BufferedWriter bWrite;
	private static int highestScore = 0;
	private static int highestWave = 0;
	
	
	public static void writeFile(ArrayList<String> n, File file) throws IOException{
		
		fWrite = new FileWriter(file);
		bWrite = new BufferedWriter(fWrite);
		
		for(int i = 0; i < n.size(); i++){
			bWrite.write(n.get(i));
			bWrite.newLine();
		}
		
		bWrite.close();
		fWrite.close();
	}
	
	public static String getAttribute(String section, char separator, File file) throws FileNotFoundException{
		fRead = new FileReader(file);
		sc = new Scanner(fRead);
		
		while(sc.hasNextLine()){
			String line = sc.nextLine();
			if(line.substring(0, line.indexOf(separator)).equals(section)){
				return line.substring(line.indexOf(separator) + 1);
			}
		}
		return "Section not found";
	}
	
	public static boolean FileExists(String pathName){
		File tempDir = new File(pathName);
		return tempDir.exists();
	}

	public static ArrayList<String> readFile(String pathname){
		
		ArrayList<String> create = new ArrayList <String>();
		try{
			fRead = new FileReader(new File(pathname));
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

	public static void createGameData(File file, String name){
		if(!FileExists(file.getPath())){
			return;
		}
		
		ArrayList<String> gameData = new ArrayList<String>();
		
		gameData.add("Name:" + name);
		
		//Highest Score
		if(HUD.getScore() > highestScore){
			highestScore = HUD.getScore();
		}
		gameData.add("Highest Score:" + highestScore);
		//Highest Wave
		if(HUD.getWave() > highestWave){
			highestWave = HUD.getWave();
		}
		gameData.add("Highest Wave:" + highestWave);
		//Health
		gameData.add("Health Upgrade:" + HUD.blueHEALTH);
		//Speed
		gameData.add("Speed Upgrade:" + Player.getSpeed());
		//Coin val
		gameData.add("Coin Upgrade:" + Player.getCoinFactor());
		//Defense
		gameData.add("Defense Upgrade:" + Player.getDefense());
		
		gameData.add("Coins:" + Menu.getCurrency());
		//Skin
		//Cursor
		
		if(file.canWrite()){
			
			try {
				FileHelper.writeFile(gameData, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static void updateGameData(File file) throws IOException{
		final char separator = ':';
		if(!FileExists(file.getPath())){
			return;
		}
		ArrayList<String> gameData = new ArrayList<String>();
		
		gameData.add("Name:" + getAttribute("Name", ':', file));
		
		//Highest Score
		if(HUD.getScore() > highestScore){
			highestScore = HUD.getScore();
		}
		gameData.add("Highest Score:" + highestScore);
		//Highest Wave
		if(HUD.getWave() > highestWave){
			highestWave = HUD.getWave();
		}
		gameData.add("Highest Wave:" + highestWave);
		//Health
		gameData.add("Health Upgrade:" + HUD.blueHEALTH);
		//Speed
		gameData.add("Speed Upgrade:" + Player.getSpeed());
		//Coin val
		gameData.add("Coin Upgrade:" + Player.getCoinFactor());
		//Defense
		gameData.add("Defense Upgrade:" + Player.getDefense());
		
		gameData.add("Coins:" + Menu.getCurrency());
		//Skin
		//Cursor
		
		if(file.canWrite()){
			
			try {
				FileHelper.writeFile(gameData, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}