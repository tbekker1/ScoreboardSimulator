package tb.archc.scoreboard.functionalUnits;

import tb.archc.scoreboard.storage.FpRegister;

public class FloatMultiplierFU extends FunctionalUnit {

	public FloatMultiplierFU() {
		super();
		this.countToComplete = 10;
	}

	public void multiply(FpRegister destination, FpRegister sourceLeft, FpRegister sourceRight) {
		this.setDestination(destination);
		this.setSourceLeft(sourceLeft);
		this.setSourceRight(sourceRight);
		this.startOperation();
		destination.setValue(sourceLeft.getValue() * sourceRight.getValue());
	}
}
