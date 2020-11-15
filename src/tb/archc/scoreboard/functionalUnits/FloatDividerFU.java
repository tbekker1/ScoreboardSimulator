package tb.archc.scoreboard.functionalUnits;

import tb.archc.scoreboard.storage.FpRegister;

public class FloatDividerFU extends FunctionalUnit {

	public FloatDividerFU() {
		super();
		this.countToComplete = 40;
	}

	public void divide(FpRegister destination, FpRegister sourceLeft, FpRegister sourceRight) {
		this.setDestination(destination);
		this.setSourceLeft(sourceLeft);
		this.setSourceRight(sourceRight);
		this.startOperation();
		destination.setValue(sourceLeft.getValue() / sourceRight.getValue());
	}
}

