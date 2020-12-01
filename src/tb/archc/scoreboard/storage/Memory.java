package tb.archc.scoreboard.storage;

/**
 * Class Memory:
 * 
 * This class imitates memory, and contains all of the values of memory in an array.
 * It also has a set and get for the memory.
 */
public class Memory {
	
	// the array that holds all of the values at a memory index
	private static float memValues[] = new float[] {45, 12, 0, 0, 10, 135, 254, 127, 18, 4, 55, 8, 2, 98, 13, 5, 233, 158, 167};

	
	/**
	 * getMemValue()
	 * 
	 * gets a value from memory given an index. (Used in loads)
	 */
	public float getMemValue(int index) {
		if (index < 0 || index > 18) {
			return -1;
		}
		return memValues[index];
	}

	/**
	 * setMemValue()
	 * 
	 * Sets a value in the memory given an index and a value. (Used in stores)
	 */
	public void setMemValue(int index, float value) {
		if (index >= 0 && index <= 18) {
			memValues[index] = value;
		}
	}
}
