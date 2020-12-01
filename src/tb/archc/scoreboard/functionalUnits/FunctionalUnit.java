package tb.archc.scoreboard.functionalUnits;

import tb.archc.scoreboard.Operation;
import tb.archc.scoreboard.storage.StorageLocation;

/**
 * Class FunctionalUnit:
 * The base class for all of the functional units. Contains the destination and sources StorageLocations
 * 
 * Has a busy state, and an executing state.
 * 
 * Also contains the current execution cycle count, and the number of clock cycles
 * it takes to finish executing that specific functional unit.
 */
public abstract class FunctionalUnit {
	protected int currClockCount;
	protected int countToComplete;
	private boolean busy = false;
	private boolean executing = false;
	private StorageLocation destination;
	private StorageLocation sourceLeft;
	private StorageLocation sourceRight;
	
	/**
	 * execute()
	 * executes the instruction given the operation.
	 */
    public abstract void execute(Operation operation);
	
    
    /**
	 * issue()
	 * sets the functional units destination and sources, given the storage locations.
	 */
    public void issue(StorageLocation destination, StorageLocation sourceLeft, StorageLocation sourceRight) {
    	setDestination(destination);
		setSourceLeft(sourceLeft);
		setSourceRight(sourceRight);
    }
    
    //lines 41 to 75 are getters and setters for a functional units private variables.
	public boolean isBusy() {
		return busy;
	}

	public StorageLocation getDestination() {
		return destination;
	}

	public void setDestination(StorageLocation destination) {
		this.destination = destination;
	}

	public StorageLocation getSourceLeft() {
		return sourceLeft;
	}

	public void setSourceLeft(StorageLocation sourceLeft) {
		this.sourceLeft = sourceLeft;
	}

	public StorageLocation getSourceRight() {
		return sourceRight;
	}

	public void setSourceRight(StorageLocation sourceRight) {
		this.sourceRight = sourceRight;
	}

	public void setBusy(boolean busy) {
		this.busy = busy;
	}

	public boolean isExecuting(){
		return this.executing;
	}
	
	
	/**
	 * clockCycle()
	 * if the functional unit is still executing, the clock cycle count increases.
	 */
	public void clockCycle() {
		if (isExecuting()) {
			this.currClockCount++;
			if (operationFinished()) {
				this.executing = false;
			}
		}
	}
	
	/**
	 * operationFinished()
	 * returns a boolean of whether the functional unit has finished executing or not
	 */
	public boolean operationFinished() {
		return currClockCount >= countToComplete;
	}
	
	/**
	 * startOperation()
	 * Begins the count for the number of clock cycles it takes to execute that specific
	 * functional unit.
	 */
	public void startOperation() {
		this.currClockCount = 1;
		if (!operationFinished()) {
			this.executing = true;
		}
		else {
			this.executing = false;
		}
	}
	
}
