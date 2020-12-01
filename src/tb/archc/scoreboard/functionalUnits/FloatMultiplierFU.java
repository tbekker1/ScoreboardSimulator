package tb.archc.scoreboard.functionalUnits;

import tb.archc.scoreboard.Operation;
import tb.archc.scoreboard.storage.FpRegister;

/**
 * Class FloatMultiplierFU:
 * 
 * This is a child class of FunctionalUnit that deals with the Floating Point Multiplier Functional Unit
 * 
 * Sets the count to complete execution to 10, and executes the multiply functional unit operations.
 */
public class FloatMultiplierFU extends FunctionalUnit {

	/**
	 * FloatMultiplierFU():
	 * 
	 * The constructor for the specific functional unit.
	 * Instantiates the base class, and sets its specific count to complete.
	 */
	public FloatMultiplierFU() {
		super();
		this.countToComplete = 10;
	}

	
	/**
	 * execute():
	 * 
	 * Takes in the operation of the functional unit, and multiplies the registers
	 */
	@Override
	public void execute(Operation operation) {
		this.startOperation();
		((FpRegister)getDestination()).setValue(((FpRegister)getSourceLeft()).getValue() * ((FpRegister)getSourceRight()).getValue());
	}
}
