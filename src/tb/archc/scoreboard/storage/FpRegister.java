package tb.archc.scoreboard.storage;

/**
 * Class FpRegister
 * 
 * This is a child class of StorageLocation. This is the class for Floating Point Registers
 * ex. F4
 */
public class FpRegister extends StorageLocation {

	/**
	 * FpRegister()
	 * 
	 * The constructor.
	 * Takes in a name, and initializes the name. Also initializes the value to 0.
	 * Initializes the flags for that register.
	 */
	public FpRegister(String name) {
		super(name);
		this.value = 0;
		this.setWriteOK(true);
		this.setReadOK(true);
	}
	
	private float value;
	
	//setters and getters for the value of the register.
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	
	public void setValue(int value) {
		this.value = (float)value;
	}
}
