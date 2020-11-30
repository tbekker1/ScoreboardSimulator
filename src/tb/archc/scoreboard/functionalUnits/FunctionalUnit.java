package tb.archc.scoreboard.functionalUnits;

import tb.archc.scoreboard.Operation;
import tb.archc.scoreboard.storage.StorageLocation;

public abstract class FunctionalUnit {
	protected int currClockCount;
	protected int countToComplete;
	private boolean busy = false;
	private boolean executing = false;
	private StorageLocation destination;
	private StorageLocation sourceLeft;
	private StorageLocation sourceRight;
	
    public abstract void execute(Operation operation);
	
    public void issue(StorageLocation destination, StorageLocation sourceLeft, StorageLocation sourceRight) {
    	setDestination(destination);
		setSourceLeft(sourceLeft);
		setSourceRight(sourceRight);
    }
    
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

	public void clockCycle() {
		if (isExecuting()) {
			this.currClockCount++;
			if (operationFinished()) {
				this.executing = false;
			}
		}
	}
	
	
	public boolean operationFinished() {
		return currClockCount >= countToComplete;
	}
	
	public void startOperation() {
		this.currClockCount = 1;
		if (!operationFinished()) {
			this.executing = true;
		}
		else {
			this.executing = false;
		}
	}
	
	public boolean isExecuting(){
		return this.executing;
	}
	
}
