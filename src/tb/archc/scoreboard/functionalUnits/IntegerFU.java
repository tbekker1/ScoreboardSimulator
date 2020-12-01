package tb.archc.scoreboard.functionalUnits;

import tb.archc.scoreboard.Operation;
import tb.archc.scoreboard.storage.FpRegister;
import tb.archc.scoreboard.storage.IntRegister;
import tb.archc.scoreboard.storage.MemoryLocation;
import tb.archc.scoreboard.storage.StorageLocation;

/**
 * Class IntegerFU:
 * 
 * This is a child class of FunctionalUnit that deals with the Integer Functional Unit
 * 
 * Sets the count to complete execution to 1, and executes the integer functional unit operations.
 */
public class IntegerFU extends FunctionalUnit {
	
	/**
	 * IntegerFU():
	 * 
	 * The constructor for the specific functional unit.
	 * Instantiates the base class, and sets its specific count to complete.
	 */
	public IntegerFU() {
		super();
		this.countToComplete = 1;
	}

	/**
	 * execute():
	 * 
	 * Takes in the operation of the functional unit, and executes the specific instruction
	 * based on the operation.
	 */
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
