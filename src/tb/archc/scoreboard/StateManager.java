package tb.archc.scoreboard;

import java.util.ArrayList;

import tb.archc.scoreboard.functionalUnits.FunctionalUnit;
import tb.archc.scoreboard.storage.FpRegister;
import tb.archc.scoreboard.storage.IntRegister;
import tb.archc.scoreboard.storage.MemoryLocation;
import tb.archc.scoreboard.storage.StorageLocation;

/**
 * Class StateManager:
 * 
 * Manages the functional unit states, and also deals with the locking requests
 * that were requested in the Simulator.
 */
public class StateManager {

	//create arraylists of all functional units that are busy, and not busy.
	private ArrayList<FunctionalUnit> setBusyTrue = new ArrayList<FunctionalUnit>();
	private ArrayList<FunctionalUnit> setBusyFalse = new ArrayList<FunctionalUnit>();
	
	//create arraylists of all destination registers that already used as a destination or not.
	private ArrayList<StorageLocation> setUsedAsDestFalse = new ArrayList<StorageLocation>();
	private ArrayList<StorageLocation> setUsedAsDestTrue = new ArrayList<StorageLocation>();
	
	//setters for the arraylists above
	public void setUsedAsDestFalse(StorageLocation sl) {
		if (sl != null) 
			this.setUsedAsDestFalse.add(sl);
	}
	
	public void setUsedAsDestTrue(StorageLocation sl) {
		if (sl != null) 
			this.setUsedAsDestTrue.add(sl);
	}
	
	
	public void setBusyTrue(FunctionalUnit fu) { 
		this.setBusyTrue.add(fu);
	}
	public void setBusyFalse(FunctionalUnit fu) { 
		this.setBusyFalse.add(fu);
	}
	
	
	/**
	 * clockCycle()
	 * 
	 *Looks at all of the requests in all of the arrays, and executes those requests at the end of each
	 *clock cycle. (Called in simulator)
	 *
	 *Also gets the arrays of all the registers and memory locations from the hardware class,
	 *and executes the locks that were requested in Simulator.
	 */
	public void clockCycle() {

		//Go through the busy functional units and the non busy functional units, and actually set
		//their states to those requested
		for (int i = this.setBusyTrue.size(); i > 0; i--) {
			this.setBusyTrue.remove(i-1).setBusy(true);		
		}
		
		for (int i = this.setBusyFalse.size(); i > 0; i--) {
			this.setBusyFalse.remove(i-1).setBusy(false);		
		}
		
		//Go through the array list of destination flags, and set
		//their states to those requested
		for (int i = this.setUsedAsDestFalse.size(); i > 0; i--) {
			this.setUsedAsDestFalse.remove(i-1).setUsedAsDestination(false);		
		}
		
		for (int i = this.setUsedAsDestTrue.size(); i > 0; i--) {
			this.setUsedAsDestTrue.remove(i-1).setUsedAsDestination(true);		
		}
		
		//Get the arrays of all the registers from the hardware, and set their lock
		//states to the locks that were requested
		FpRegister [] fpRegisters = Simulator.getHardware().getAllFpRegisters();
		for (int i = 0; i < fpRegisters.length; i++) {
			fpRegisters[i].executeLocks();
		}
		
		IntRegister [] intRegisters = Simulator.getHardware().getAllIntRegisters();
		for (int i = 0; i < intRegisters.length; i++) {
			intRegisters[i].executeLocks();
		}

		MemoryLocation [] memLocations = Simulator.getHardware().getAllMemoryLocations();
		for (int i = 0; i < memLocations.length; i++) {
			memLocations[i].executeLocks();
		}
		
	}
}
