package tb.archc.scoreboard.functionalUnits;

import tb.archc.scoreboard.Operation;
import tb.archc.scoreboard.storage.FpRegister;

public class FloatMultiplierFU extends FunctionalUnit {

	public FloatMultiplierFU() {
		super();
		this.countToComplete = 10;
	}

	@Override
	public void execute(Operation operation) {
		this.startOperation();
		((FpRegister)getDestination()).setValue(((FpRegister)getSourceLeft()).getValue() * ((FpRegister)getSourceRight()).getValue());
	}
}
