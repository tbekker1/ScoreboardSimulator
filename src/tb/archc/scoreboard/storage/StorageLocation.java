package tb.archc.scoreboard.storage;


/**
 * Class StorageLocation:
 * The base class for all of the Storage Locations (Registers/Memory Locations).
 * 
 * Includes the name of the storage location, the flag booleans for whether it is 
 * ok to read or write on that location. It also has a requestedReadLock int for if those read or write
 * booleans should be changed on the next cycle.
 * 
 */
public abstract class StorageLocation {
	
	/**
	 * StorageLocation()
	 * 
	 * The constructor for the class. Takes in a name, and assigns the name of that storage location
	 */
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
	
	
	/**
	 * requestReadLock()
	 * 
	 * takes in a request for a read lock, and makes sure that there is no requested write lock
	 * for the next clock cycle.
	 */
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
	
	/**
	 * requestWriteLock()
	 * 
	 * takes in a request for a write lock, and sets the variable to set the lock on the next
	 * clock cycle.
	 */
	public void requestWriteLock (boolean val) {
			if (val == true) {
				this.requestedWriteLock = 1;
			}
			else {
				this.requestedWriteLock = 0;
			}
	}
	
	
	/**
	 * executeLocks()
	 * 
	 * Based on what the requested locks value is, it sets the actual read lock or write lock to 
	 * that value on the next clock cycle.
	 * 
	 * It then resets the values of the requested read and write locks.
	 */
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
	
	//All the functions below this point are setters and getters for the storage location variables.
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
