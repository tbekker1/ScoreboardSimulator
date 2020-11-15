package tb.archc.scoreboard.storage;

public class IntRegister extends StorageLocation {

	public IntRegister(String name) {
		super(name);
		this.value = 0;
	}
	
	private int value;
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
}
