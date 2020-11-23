package tb.archc.scoreboard.functionalUnits;

import tb.archc.scoreboard.Operation;
import tb.archc.scoreboard.storage.FpRegister;
import tb.archc.scoreboard.storage.IntRegister;

public class FloatAddFU extends FunctionalUnit {

	public FloatAddFU() {
		super();
		this.countToComplete = 2;
	}


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
