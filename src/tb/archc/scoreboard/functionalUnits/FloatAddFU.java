package tb.archc.scoreboard.functionalUnits;

import tb.archc.scoreboard.Operation;
import tb.archc.scoreboard.storage.FpRegister;
import tb.archc.scoreboard.storage.IntRegister;


/**
 * Class FloadAddFU:
 * 
 * This is a child class of FunctionalUnit that deals with the Floating Point Adder Functional Unit.
 * 
 * Sets the count to complete execution to 2, and executes the Float Adder functional unit operations.
 */
public class FloatAddFU extends FunctionalUnit {

	/**
	 * FloatAddFU():
	 * 
	 * The constructor for the specific functional unit.
	 * Instantiates the base class, and sets its specific count to complete.
	 */
	public FloatAddFU() {
		super();
		this.countToComplete = 2;
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
		case ADD:
			((FpRegister)getDestination()).setValue(((FpRegister)getSourceLeft()).getValue() + ((FpRegister)getSourceRight()).getValue());
			break;
		case SUBTRACT:
			((FpRegister)getDestination()).setValue(((FpRegister)getSourceLeft()).getValue() - ((FpRegister)getSourceRight()).getValue());
			break;
		default:
			break;
		}
	
	}
}
