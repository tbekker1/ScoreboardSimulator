package tb.archc.scoreboard;

import tb.archc.scoreboard.storage.*;
import tb.archc.scoreboard.functionalUnits.*;
import java.io.*;
import java.util.ArrayList;

public class Simulator {

	public Simulator() {
		
	}
	
	public static Hardware getHardware() {
		if (hardware == null) {
			hardware = new Hardware();
		}
		return hardware;
	}

	private static String filename;
	private static Hardware hardware = null;
	
	public static void main(String[] args) {
		if (args.length > 0) {
			filename = args[0];
			Simulator sim = new Simulator();
			sim.run();
		}
		else {
			System.out.println("Must provide file name as an argument");
		}
	
		
	}

	public void run() {
		ArrayList<String>instructions = parseFile();
		
		
	}
	
	public ArrayList<String> parseFile() {
		ArrayList<String> instructions = new ArrayList<String>(); 
		File mips = new File(filename);
		try {
			FileReader fr = new FileReader(mips);
			BufferedReader br = new BufferedReader(fr);
			//StringBuffer sb = new StringBuffer();
			String line;
			while((line=br.readLine())!=null) {
				instructions.add(line);
			}
			br.close();
			fr.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return instructions;
	}
	
	
}
