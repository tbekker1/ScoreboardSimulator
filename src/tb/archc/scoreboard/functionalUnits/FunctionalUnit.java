package tb.archc.scoreboard.functionalUnits;

import tb.archc.scoreboard.storage.StorageLocation;

public abstract class FunctionalUnit {
	protected int currClockCount;
	protected int countToComplete;
	private boolean busy;
	private StorageLocation destination;
	private StorageLocation sourceLeft;
	private StorageLocation sourceRight;
	
	public boolean isBusy() {
		return busy;
	}

	public StorageLocation getDestination() {
		return destination;
	}

	public void setDestination(StorageLocation destination) {
		this.destination = destination;
		this.destination.setReadOK(false);
		this.destination.setWriteOK(false);
	}

	public StorageLocation getSourceLeft() {
		return sourceLeft;
	}

	public void setSourceLeft(StorageLocation sourceLeft) {
		this.sourceLeft = sourceLeft;
		if (this.sourceLeft != null) {
			this.sourceLeft.setReadOK(true);
			this.sourceLeft.setWriteOK(false);
		}
	}

	public StorageLocation getSourceRight() {
		return sourceRight;
	}

	public void setSourceRight(StorageLocation sourceRight) {
		this.sourceRight = sourceRight;
		if (this.sourceRight != null) {
			this.sourceRight.setReadOK(true);
			this.sourceRight.setWriteOK(false);
		}
	}

	public void setBusy(boolean busy) {
		this.busy = busy;
	}

	public void clockCycle() {
		if (!operationFinished()) {
			this.currClockCount++;
			if (operationFinished()) {
				this.destination.setReadOK(true);
				this.destination.setWriteOK(true);
				if (this.sourceLeft != null) {
					this.sourceLeft.setWriteOK(true);
				}
				if (this.sourceRight != null) {
					this.sourceRight.setWriteOK(true);
				}
			}
		}
	}
	
	
	public boolean operationFinished() {
		return currClockCount > countToComplete;
	}
	
	public void startOperation() {
		this.currClockCount = 1;
		setBusy(true);
	}
	
	
}
