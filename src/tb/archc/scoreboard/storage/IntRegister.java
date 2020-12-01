package tb.archc.scoreboard.storage;

/**
 * Class IntRegister
 * 
 * This is a child class of StorageLocation. This is the class for Integer Registers
 * ex. $3
 */
public class IntRegister extends StorageLocation {

	/**
	 * IntRegister()
	 * 
	 * The constructor.
	 * Takes in a name, and initializes the name. Also initializes the value to 0.
	 * Initializes the flags for that register.
	 */
	public IntRegister(String name) {
		super(name);
		this.value = 0;
		this.setWriteOK(true);
		this.setReadOK(true);
	}
	
	private int value;
	
	//setters and getters for the value of the register.
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
}
