package tb.archc.scoreboard.functionalUnits;

import tb.archc.scoreboard.Operation;
import tb.archc.scoreboard.storage.FpRegister;
import tb.archc.scoreboard.storage.IntRegister;
import tb.archc.scoreboard.storage.MemoryLocation;
import tb.archc.scoreboard.storage.StorageLocation;

public class IntegerFU extends FunctionalUnit {
	public IntegerFU() {
		super();
		this.countToComplete = 1;
	}

	@Override
	public void execute(Operation operation, StorageLocation destination, StorageLocation sourceLeft,
			StorageLocation sourceRight) {
		
		this.setDestination(destination);
		this.setSourceLeft(sourceLeft);
		this.setSourceRight(sourceRight);
		this.startOperation();
		switch (operation) {
		case LOAD:
			((FpRegister)destination).setValue(((MemoryLocation)sourceLeft).read());
			break;
		case STORE:
			((MemoryLocation)destination).write(((FpRegister)sourceLeft).getValue());
			break;
		case ADD:
			((IntRegister)destination).setValue(((IntRegister)sourceLeft).getValue() + ((IntRegister)sourceRight).getValue());
			break;
		case SUBTRACT:
			((IntRegister)destination).setValue(((IntRegister)sourceLeft).getValue() - ((IntRegister)sourceRight).getValue());
			break;
		default:
			break;
		}
			
	}
	
	
}
