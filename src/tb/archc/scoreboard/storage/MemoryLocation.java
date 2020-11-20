package tb.archc.scoreboard.storage;

public class MemoryLocation extends StorageLocation{

	public MemoryLocation(String name, int index) {
		super(name);
		this.index = index;
		this.setWriteOK(true);
		this.setReadOK(true);
		this.memory = new Memory();
	}
	private int index;
	private Memory memory;
	
	public float read() {
		return this.memory.getMemValue(this.index);
	}
	
	public void write(float value) {
		this.memory.setMemValue(this.index, value);
	}
	
}
