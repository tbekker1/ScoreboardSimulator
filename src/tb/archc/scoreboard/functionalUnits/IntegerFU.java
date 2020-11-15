package tb.archc.scoreboard.functionalUnits;

import tb.archc.scoreboard.storage.FpRegister;
import tb.archc.scoreboard.storage.IntRegister;
import tb.archc.scoreboard.storage.MemoryLocation;

public class IntegerFU extends FunctionalUnit {
	public IntegerFU() {
		super();
		this.countToComplete = 1;
	}

	public void load(FpRegister destination, MemoryLocation source) {
		this.setDestination(destination);
		this.setSourceLeft(source);
		this.setSourceRight(null);
		this.startOperation();
		destination.setValue(source.read());
	}
	
	public void store(MemoryLocation destination, FpRegister source) {
		this.setDestination(destination);
		this.setSourceLeft(source);
		this.setSourceRight(null);
		this.startOperation();
		destination.write(source.getValue());
	}
	
	public void add(IntRegister destination, IntRegister sourceLeft, IntRegister sourceRight) {
		this.setDestination(destination);
		this.setSourceLeft(sourceLeft);
		this.setSourceRight(sourceRight);
		this.startOperation();
		destination.setValue(sourceLeft.getValue() + sourceRight.getValue());
	}
	
	public void add(IntRegister destination, IntRegister sourceLeft, int immediate) {
		this.setDestination(destination);
		this.setSourceLeft(sourceLeft);
		this.setSourceRight(null);
		this.startOperation();
		destination.setValue(sourceLeft.getValue() + immediate);
	}
	
	public void subtract(IntRegister destination, IntRegister sourceLeft, IntRegister sourceRight) {
		this.setDestination(destination);
		this.setSourceLeft(sourceLeft);
		this.setSourceRight(sourceRight);
		this.startOperation();
		destination.setValue(sourceLeft.getValue() - sourceRight.getValue());
	}
	
	
}
