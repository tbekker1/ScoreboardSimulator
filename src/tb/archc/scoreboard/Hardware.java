package tb.archc.scoreboard;

import tb.archc.scoreboard.storage.*;
import tb.archc.scoreboard.functionalUnits.*;

/**
 * Class Hardware:
 * Creates and initializes all the hardware that the system uses.
 * 
 * Creates and initializes all of the registers/memory locations, in an array
 * Creates and initializes all of the functional units that will be used.
 */
public class Hardware {

	/**
	 * Hardware()
	 * 
	 * the constructor.
	 * 
	 * Goes through all of the arrays of registers and memory locations, creates instances
	 * of the registers and memory locations at that index of the array. 
	 */
	public Hardware() {
		for (int i = 0; i < 32; i++) {
			//create all of the int registers and float registers in their arrays
			intRegisters[i] = new IntRegister("$" + i);
			fpRegisters[i] = new FpRegister("F" + i);
		}
		
		for (int i = 0; i < 19; i++) {
			//create all of the memory locations in their arrays.
			memLocation[i] = new MemoryLocation("(" + i + ")", i);
		}
	}
	
	private FpRegister fpRegisters[] = new FpRegister [32];
	private IntRegister intRegisters[] = new IntRegister [32];
	private MemoryLocation memLocation[] = new MemoryLocation[19];
	
	//initialize all of the functional units in the hardware
	private IntegerFU integerFU = new IntegerFU();
	private FloatAddFU floatAddFU = new FloatAddFU();
	private FloatMultiplierFU floatMultiplierFU = new FloatMultiplierFU();
	private FloatDividerFU floatDividerFU = new FloatDividerFU();
	
	
	//getters for the arrays of storage locations
	public FpRegister[] getAllFpRegisters() {
		return fpRegisters;
	}
	
	public IntRegister[] getAllIntRegisters() {
		return intRegisters;
	}
	
	public MemoryLocation[] getAllMemoryLocations() {
		return memLocation;
	}
	
	
	//getters for the functional units and individual storage locations
	public FpRegister getFpRegisters(int index) {
		if (index >= 0 && index < 32) {
			return fpRegisters[index];
		}
		return null;
	}
	public IntRegister getIntRegisters(int index) {
		if (index >= 0 && index < 32) {
			return intRegisters[index];
		}
		return null;
	}
	public MemoryLocation getMemLocation(int index) {
		if (index >= 0 && index < 19) {
			return memLocation[index];
		}
		return null;
	}
	public IntegerFU getIntegerFU() {
		return integerFU;
	}
	public FloatAddFU getFloatAddFU() {
		return floatAddFU;
	}
	public FloatMultiplierFU getFloatMultiplierFU() {
		return floatMultiplierFU;
	}
	public FloatDividerFU getFloatDividerFU() {
		return floatDividerFU;
	}
	

}
