package tb.archc.scoreboard.storage;

/**
 * Class MemoryLocation
 * 
 * This is a child class of StorageLocation. This is the class for Memory Locations
 * ex. 0(17)
 */
public class MemoryLocation extends StorageLocation{

	/**
	 * MemoryLocation()
	 * 
	 * The constructor.
	 * Takes in a name and its index, and initializes both of those values.
	 * Instantiates the actual memory.
	 */
	public MemoryLocation(String name, int index) {
		super(name);
		this.index = index;
		this.setWriteOK(true);
		this.setReadOK(true);
		this.memory = new Memory();
	}
	private int index;
	private Memory memory;
	
	/**
	 * read()
	 * 
	 * returns the value in the memory at the MemoryLocation's index (used in load)
	 */
	public float read() {
		return this.memory.getMemValue(this.index);
	}
	
	/**
	 * write()
	 * 
	 * Takes in a value
	 * sets the value in the memory at the MemoryLocation's index (used in store)
	 */
	public void write(float value) {
		this.memory.setMemValue(this.index, value);
	}
	
}
