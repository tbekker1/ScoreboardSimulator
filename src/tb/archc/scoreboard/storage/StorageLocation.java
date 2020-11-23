package tb.archc.scoreboard.storage;

public abstract class StorageLocation {
	public StorageLocation(String name) {
		super();
		this.name = name;
	}
	
	private String name;
	private boolean readOK;
	private boolean writeOK;
	private boolean usedAsDestination;
	
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
		return writeOK;
	}
	public void setWriteOK(boolean writeOK) {
		this.writeOK = writeOK;
	}
	
}
