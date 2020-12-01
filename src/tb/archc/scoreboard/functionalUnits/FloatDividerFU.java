package tb.archc.scoreboard.functionalUnits;

import tb.archc.scoreboard.Operation;
import tb.archc.scoreboard.storage.FpRegister;

/**
 * Class FloatDividerFU:
 * 
 * This is a child class of FunctionalUnit that deals with the Floating Point Divider Functional Unit
 * 
 * Sets the count to complete execution to 40, and executes the divide functional unit operations.
 */
public class FloatDividerFU extends FunctionalUnit {

	/**
	 * FloatDividerFU():
	 * 
	 * The constructor for the specific functional unit.
	 * Instantiates the base class, and sets its specific count to complete.
	 */
	public FloatDividerFU() {
		super();
		this.countToComplete = 40;
	}

	
	/**
	 * execute():
	 * 
	 * Takes in the operation of the functional unit, and divides the registers
	 */
	@Override
	public void execute(Operation operation) {
		this.startOperation();
		((FpRegister)getDestination()).setValue(((FpRegister)getSourceLeft()).getValue() / ((FpRegister)getSourceRight()).getValue());
	}
}

