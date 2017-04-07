package Game.main;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHelper {
	protected static String dir = "res/";
	protected static FileReader fRead;
	protected static BufferedReader bRead;
	protected static Scanner sc;
	protected static FileWriter fWrite;
	protected static BufferedWriter bWrite;
	private String currentData;
	
	public static void writeFile(ArrayList<String> n, String FileName) throws IOException{
		if(!FileExists(FileName)){
			File file = new File(dir+FileName);
			file.createNewFile();
		}
		fWrite = new FileWriter(new File(dir+FileName));
		bWrite = new BufferedWriter(fWrite);
		for(String val : n){
			bWrite.write(val);
			bWrite.newLine();
		}
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

	public void writeCurrentGameData(){
		//Highest Score
		//Highest Wave
		//Skin
		//Health
		//Cursor
		//Speed
		//Coin val
		//Defense
		
		
	}
}