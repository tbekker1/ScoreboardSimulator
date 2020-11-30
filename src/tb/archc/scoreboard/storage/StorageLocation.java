package tb.archc.scoreboard.storage;

public abstract class StorageLocation {
	public StorageLocation(String name) {
		super();
		this.name = name;
	}
	
	private String name;
	private boolean readOK;
	private boolean writeOK;
	private int requestedReadLock = -1;
	private int requestedWriteLock = -1;
	private boolean usedAsDestination;
	
	public void requestReadLock(boolean val) {
		if (val == false || this.requestedWriteLock != 1) {
			if (val == true) {
				this.requestedReadLock = 1;
			}
			else {
				this.requestedReadLock = 0;
			}
		}
	}
	
	public void requestWriteLock (boolean val) {
		//if (val == false || this.requestedReadLock != 1) {
			if (val == true) {
				this.requestedWriteLock = 1;
			}
			else {
				this.requestedWriteLock = 0;
			}
		//}
	}
	
	public void executeLocks() {
		if (this.requestedReadLock >= 0) {
			setReadOK(this.requestedReadLock == 0);
		}
		if (this.requestedWriteLock >= 0) {
			setWriteOK(this.requestedWriteLock == 0);
		}
		this.requestedReadLock = -1;
		this.requestedWriteLock = -1;
	}
	
	public boolean isUsedAsDestination() {
		return usedAsDestination;
	}

	public void setUsedAsDestination(boolean usedAsDestination) {
		this.usedAsDestination = usedAsDestination;
	}

	public String getName() {
		return name;
	}

	public boolean isReadOK() {
		return readOK;
	}
	public void setReadOK(boolean readOK) {
		this.readOK = readOK;
	}
	public boolean isWriteOK() {
		//return writeOK == 0;
		return writeOK;
	}
	public void setWriteOK(boolean writeOK) {
		/*
		if (writeOK == false) {
			this.writeOK++;
		}
		else {
			this.writeOK--;
			if (this.writeOK < 0) {
				this.writeOK = 0;
			}
		}
		*/
		this.writeOK = writeOK;
	}
	
	
}
