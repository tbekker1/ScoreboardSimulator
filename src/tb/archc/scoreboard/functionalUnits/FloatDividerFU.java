package tb.archc.scoreboard.functionalUnits;

import tb.archc.scoreboard.Operation;
import tb.archc.scoreboard.storage.FpRegister;
import tb.archc.scoreboard.storage.StorageLocation;

public class FloatDividerFU extends FunctionalUnit {

	public FloatDividerFU() {
		super();
		this.countToComplete = 40;
	}

	@Override
	public void execute(Operation operation, StorageLocation destination, StorageLocation sourceLeft,
			StorageLocation sourceRight) {
	
		this.setDestination(destination);
		this.setSourceLeft(sourceLeft);
		this.setSourceRight(sourceRight);
		this.startOperation();
		((FpRegister)destination).setValue(((FpRegister)sourceLeft).getValue() / ((FpRegister)sourceRight).getValue());
		
	}
}

