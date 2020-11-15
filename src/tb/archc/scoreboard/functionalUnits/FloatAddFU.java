package tb.archc.scoreboard.functionalUnits;

import tb.archc.scoreboard.storage.FpRegister;

public class FloatAddFU extends FunctionalUnit {

	public FloatAddFU() {
		super();
		this.countToComplete = 2;
	}

	public void add(FpRegister destination, FpRegister sourceLeft, FpRegister sourceRight) {
		this.setDestination(destination);
		this.setSourceLeft(sourceLeft);
		this.setSourceRight(sourceRight);
		this.startOperation();
		destination.setValue(sourceLeft.getValue() + sourceRight.getValue());
	}
	
	public void subtract(FpRegister destination, FpRegister sourceLeft, FpRegister sourceRight) {
		this.setDestination(destination);
		this.setSourceLeft(sourceLeft);
		this.setSourceRight(sourceRight);
		this.startOperation();
		destination.setValue(sourceLeft.getValue() - sourceRight.getValue());
	}
}
