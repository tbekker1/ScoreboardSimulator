package tb.archc.scoreboard.functionalUnits;

import tb.archc.scoreboard.Operation;
import tb.archc.scoreboard.storage.FpRegister;

public class FloatDividerFU extends FunctionalUnit {

	public FloatDividerFU() {
		super();
		this.countToComplete = 40;
	}

	@Override
	public void execute(Operation operation) {
		this.startOperation();
		((FpRegister)getDestination()).setValue(((FpRegister)getSourceLeft()).getValue() / ((FpRegister)getSourceRight()).getValue());
	}
}

