package tb.archc.scoreboard;

import tb.archc.scoreboard.storage.*;
import tb.archc.scoreboard.functionalUnits.*;

public class Hardware {

	public Hardware() {
		for (int i = 0; i < 32; i++) {
			intRegisters[i] = new IntRegister("$" + i);
			fpRegisters[i] = new FpRegister("F" + i);
		}
		
		for (int i = 0; i < 19; i++) {
			memLocation[i] = new MemoryLocation("(" + i + ")", i);
		}
	}
	
	private FpRegister fpRegisters[] = new FpRegister [32];
	private IntRegister intRegisters[] = new IntRegister [32];
	private MemoryLocation memLocation[] = new MemoryLocation[19];
	
	private IntegerFU integerFU = new IntegerFU();
	private FloatAddFU floatAddFU = new FloatAddFU();
	private FloatMultiplierFU floatMultiplierFU = new FloatMultiplierFU();
	private FloatDividerFU floatDividerFU = new FloatDividerFU();
	
	public FpRegister[] getAllFpRegisters() {
		return fpRegisters;
	}
	
	public IntRegister[] getAllIntRegisters() {
		return intRegisters;
	}
	
	public MemoryLocation[] getAllMemoryLocations() {
		return memLocation;
	}
	
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
