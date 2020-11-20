package tb.archc.scoreboard.storage;

public class FpRegister extends StorageLocation {

	public FpRegister(String name) {
		super(name);
		this.value = 0;
		this.setWriteOK(true);
		this.setReadOK(false);
	}
	
	private float value;
	
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
