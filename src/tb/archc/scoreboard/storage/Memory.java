package tb.archc.scoreboard.storage;

public class Memory {
	
	private static float memValues[] = new float[] {45, 12, 0, 0, 10, 135, 254, 127, 18, 4, 55, 8, 2, 98, 13, 5, 233, 158, 167};

	public float getMemValue(int index) {
		if (index < 0 || index > 18) {
			return -1;
		}
		return memValues[index];
	}

	public void setMemValue(int index, float value) {
		if (index >= 0 && index <= 18) {
			memValues[index] = value;
		}
	}
}
