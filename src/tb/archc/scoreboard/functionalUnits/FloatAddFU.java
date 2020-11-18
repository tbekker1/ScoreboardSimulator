package tb.archc.scoreboard.functionalUnits;

import tb.archc.scoreboard.Operation;
import tb.archc.scoreboard.storage.FpRegister;
import tb.archc.scoreboard.storage.IntRegister;
import tb.archc.scoreboard.storage.StorageLocation;

public class FloatAddFU extends FunctionalUnit {

	public FloatAddFU() {
		super();
		this.countToComplete = 2;
	}


	@Override
	public void execute(Operation operation, StorageLocation destination, StorageLocation sourceLeft,
			StorageLocation sourceRight) {
		
		this.setDestination(destination);
		this.setSourceLeft(sourceLeft);
		this.setSourceRight(sourceRight);
		this.startOperation();
		switch (operation) {
		case ADD:
			((FpRegister)destination).setValue(((FpRegister)sourceLeft).getValue() + ((FpRegister)sourceRight).getValue());
			break;
		case SUBTRACT:
			((FpRegister)destination).setValue(((FpRegister)sourceLeft).getValue() - ((FpRegister)sourceRight).getValue());
			break;
		default:
			break;
		}
	
	}
}
