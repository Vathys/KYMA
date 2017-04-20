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
	
	public static void replaceString(String n, char separator, String section, File file) throws IOException{
		fWrite = new FileWriter(file);
		bWrite = new BufferedWriter(fWrite);
		fRead = new FileReader(file);
		sc = new Scanner(fRead);
		
		if(file.canWrite()){
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				if(line.substring(0, line.indexOf(separator)) == section){
					bWrite.write(line.substring(0, line.indexOf(separator) + 1) + n);
					bWrite.newLine();
				}
				else{
					bWrite.write(sc.nextLine());
					bWrite.newLine();
				}
				bWrite.close();
				fWrite.close();
				sc.close();
				fRead.close();
			}
		}
	}
	
	public static boolean FileExists(String FileName){
		File tempDir = new File(dir+FileName);
		return tempDir.exists();
	}

	public ArrayList<String> readFile(String filename){
		
		ArrayList<String> create = new ArrayList <String>();
		try{
			fRead = new FileReader(new File(filename));
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

	public void createGameData(File file){
		if(!FileExists(file.getName())){
			return;
		}
		if(file.canWrite()){
			
			//Highest Score
			if(HUD.getScore() > highestScore){
				highestScore = HUD.getScore();
				try {
					FileHelper.writeFile("Highest Score:" + highestScore, file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//Highest Wave
			if(HUD.getWave() > highestWave){
				highestWave = HUD.getWave();
				try {
					FileHelper.writeFile("Highest Wave:" + highestWave, file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//Health
			try {
				FileHelper.writeFile("Health Upgrade:" + HUD.blueHEALTH, file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Speed
			try {
				FileHelper.writeFile("Speed Upgrade:" + Player.getSpeed(), file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Coin val
			try {
				FileHelper.writeFile("Coin Upgrade:" + Player.getCoinFactor(), file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Defense
			try {
				FileHelper.writeFile("Defense Upgrade:" + Player.getDefense(), file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			//Skin
			//Cursor
			
		}
	}
	
	public void updateGameData(File file) throws IOException{
		final char separator = ':';
		if(!FileExists(file.getName())){
			return;
		}
		if(file.canWrite()){
			
			//Highest Score
			if(HUD.getScore() > highestScore){
				highestScore = HUD.getScore();
				FileHelper.replaceString("" + highestScore, separator, "Highest Score", file);
			}
			//Highest Wave
			if(HUD.getWave() > highestWave){
				highestWave = HUD.getWave();
				FileHelper.replaceString("" + highestWave, separator, "Highest Wave", file);
			}
			//Health
			FileHelper.replaceString("" + HUD.blueHEALTH, separator, "Health Upgrade", file);
			//Speed
			FileHelper.replaceString("" + Player.getSpeed(), separator, "Speed Upgrade", file);
			//Coin val
			FileHelper.replaceString("" + Player.getCoinFactor(), separator, "Coin Upgrade", file);
			//Defense
			FileHelper.replaceString("" + Player.getDefense(), separator, "Defense Upgrade", file);
	
			//Skin
			//Cursor
			
		}
		
		
	}
}