package tb.archc.scoreboard;

import tb.archc.scoreboard.storage.*;
import tb.archc.scoreboard.functionalUnits.*;

public class Simulator {

	public Simulator() {
		for (int i = 0; i < 32; i++) {
			intRegisters[i] = new IntRegister("$" + i);
			fpRegisters[i] = new FpRegister("F" + i);
		}
		
		for (int i = 0; i < 19; i++) {
			memLocation[i] = new MemoryLocation("(" + i + ")", i);
		}
	}
	
	private static String filename;
	
	private FpRegister fpRegisters[] = new FpRegister [32];
	private IntRegister intRegisters[] = new IntRegister [32];
	private MemoryLocation memLocation[] = new MemoryLocation[19];
	
	private IntegerFU integerFU = new IntegerFU();
	private FloatAddFU floatAddFU = new FloatAddFU();
	private FloatMultiplierFU floatMultiplierFU = new FloatMultiplierFU();
	private FloatDividerFU floatDividerFU = new FloatDividerFU();
	
	
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
		
	}
	
}
