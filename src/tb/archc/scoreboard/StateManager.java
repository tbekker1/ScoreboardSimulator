package tb.archc.scoreboard;

import java.util.ArrayList;

import tb.archc.scoreboard.functionalUnits.FunctionalUnit;
import tb.archc.scoreboard.storage.FpRegister;
import tb.archc.scoreboard.storage.IntRegister;
import tb.archc.scoreboard.storage.MemoryLocation;
import tb.archc.scoreboard.storage.StorageLocation;

public class StateManager {


	private ArrayList<FunctionalUnit> setBusyTrue = new ArrayList<FunctionalUnit>();
	private ArrayList<FunctionalUnit> setBusyFalse = new ArrayList<FunctionalUnit>();
	
	private ArrayList<StorageLocation> setUsedAsDestFalse = new ArrayList<StorageLocation>();
	private ArrayList<StorageLocation> setUsedAsDestTrue = new ArrayList<StorageLocation>();
	
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
	
	public void clockCycle() {

		for (int i = this.setBusyTrue.size(); i > 0; i--) {
			this.setBusyTrue.remove(i-1).setBusy(true);		
		}
		
		for (int i = this.setBusyFalse.size(); i > 0; i--) {
			this.setBusyFalse.remove(i-1).setBusy(false);		
		}
		
		
		for (int i = this.setUsedAsDestFalse.size(); i > 0; i--) {
			this.setUsedAsDestFalse.remove(i-1).setUsedAsDestination(false);		
		}
		
		for (int i = this.setUsedAsDestTrue.size(); i > 0; i--) {
			this.setUsedAsDestTrue.remove(i-1).setUsedAsDestination(true);		
		}
		
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
