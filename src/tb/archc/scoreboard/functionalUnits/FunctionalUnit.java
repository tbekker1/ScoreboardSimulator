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
		setBusy(true);
    }
    
	public boolean isBusy() {
		return busy;
	}

	public StorageLocation getDestination() {
		return destination;
	}

	public void setDestination(StorageLocation destination) {
		this.destination = destination;
		this.destination.setReadOK(false);
		this.destination.setUsedAsDestination(true);
	}

	public StorageLocation getSourceLeft() {
		return sourceLeft;
	}

	public void setSourceLeft(StorageLocation sourceLeft) {
		this.sourceLeft = sourceLeft;
		//if (this.sourceLeft != null) {
			//this.sourceLeft.setReadOK(true);
			//this.sourceLeft.setWriteOK(false);
		//}
	}

	public StorageLocation getSourceRight() {
		return sourceRight;
	}

	public void setSourceRight(StorageLocation sourceRight) {
		this.sourceRight = sourceRight;
		//if (this.sourceRight != null) {
			//this.sourceRight.setReadOK(true);
			//this.sourceRight.setWriteOK(false);
		//}
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
