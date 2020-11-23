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
	public void execute(Operation operation) {
		
		this.startOperation();
		switch (operation) {
		case LOAD:
			((FpRegister)getDestination()).setValue(((MemoryLocation)getSourceLeft()).read());
			break;
		case STORE:
			((MemoryLocation)getDestination()).write(((FpRegister)getSourceLeft()).getValue());
			break;
		case ADD:
			((IntRegister)getDestination()).setValue(((IntRegister)getSourceLeft()).getValue() + ((IntRegister)getSourceRight()).getValue());
			break;
		case SUBTRACT:
			((IntRegister)getDestination()).setValue(((IntRegister)getSourceLeft()).getValue() - ((IntRegister)getSourceRight()).getValue());
			break;
		default:
			break;
		}
			
	}
	
	
}
