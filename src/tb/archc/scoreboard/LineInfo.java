package tb.archc.scoreboard;

import tb.archc.scoreboard.storage.*;
import tb.archc.scoreboard.functionalUnits.*;
import java.util.StringTokenizer;

/**
 * Class LineInfo:
 * Takes one line from the file, and breaks it into its required elements
 * 
 * Will create objects for the 
 *  lines operation, functional unit, and registers/storage locations.
 */
public class LineInfo {

	/**
	 * LineInfo()
	 * 
	 * the constructor. Takes in one line that was parsed
	 * in Simulator.
	 * 
	 * Separates the line into individual elements, and
	 * assigns the correct objects based on those elements  
	 */
	public LineInfo(String line) {
		this.line = line;
		StringTokenizer st = new StringTokenizer(line);
		if (st.hasMoreTokens()) {
			String token = st.nextToken();
			
			//check what the operation is on the line. Assign the line its operation, 
			//the functional unit it will use, and call getStorageLocation to assign that
			//line the registers/memory locations it needs.
			if ("L.D".equals(token)){
				this.operation = Operation.LOAD;
				this.functionalUnit = Simulator.getHardware().getIntegerFU();
				this.destination = getStorageLocation(st.nextToken());
				this.sourceLeft = getStorageLocation(st.nextToken());
			}
			else if ("S.D".equals(token)){
				this.operation = Operation.STORE;
				this.functionalUnit = Simulator.getHardware().getIntegerFU();
				this.sourceLeft = getStorageLocation(st.nextToken());
				this.destination = getStorageLocation(st.nextToken());
			}
			else if ("ADD.D".equals(token)){
				this.operation = Operation.ADD;
				this.functionalUnit = Simulator.getHardware().getFloatAddFU();
				this.destination = getStorageLocation(st.nextToken());
				this.sourceLeft = getStorageLocation(st.nextToken());
				this.sourceRight = getStorageLocation(st.nextToken());
			}
			
			else if ("ADD".equals(token.substring(0,3))){
				this.operation = Operation.ADD;
				this.functionalUnit = Simulator.getHardware().getIntegerFU();
				this.destination = getStorageLocation(st.nextToken());
				this.sourceLeft = getStorageLocation(st.nextToken());
				this.sourceRight = getStorageLocation(st.nextToken());
			}
			else if ("SUB.D".equals(token)){
				this.operation = Operation.SUBTRACT;
				this.functionalUnit = Simulator.getHardware().getFloatAddFU();
				this.destination = getStorageLocation(st.nextToken());
				this.sourceLeft = getStorageLocation(st.nextToken());
				this.sourceRight = getStorageLocation(st.nextToken());
			}
			else if ("SUB".equals(token)){
				this.operation = Operation.SUBTRACT;
				this.functionalUnit = Simulator.getHardware().getIntegerFU();
				this.destination = getStorageLocation(st.nextToken());
				this.sourceLeft = getStorageLocation(st.nextToken());
				this.sourceRight = getStorageLocation(st.nextToken());
			}
			else if ("MUL.D".equals(token)){
				this.operation = Operation.MULTIPLY;
				this.functionalUnit = Simulator.getHardware().getFloatMultiplierFU();
				this.destination = getStorageLocation(st.nextToken());
				this.sourceLeft = getStorageLocation(st.nextToken());
				this.sourceRight = getStorageLocation(st.nextToken());
			}
			else if ("DIV.D".equals(token)){
				this.operation = Operation.DIVIDE;
				this.functionalUnit = Simulator.getHardware().getFloatDividerFU();
				this.destination = getStorageLocation(st.nextToken());
				this.sourceLeft = getStorageLocation(st.nextToken());
				this.sourceRight = getStorageLocation(st.nextToken());
			}
			
	    }
	}
	
	private FunctionalUnit functionalUnit = null;
	private Operation operation = null;
	private StorageLocation destination = null;
	private StorageLocation sourceLeft = null;
	private StorageLocation sourceRight = null;
	private int issueClockCycle = 0;
	private int readClockCycle = 0;
	private int executeClockCycle = 0;
	private int writeClockCycle = 0;
	private String line = "";
	private InstructionState state = InstructionState.QUEUED;
	
	
	/**
	 * issue()
	 *   
	 * Issue the line whet it is ready to be issued on its functional unit.  
	 */
	public void issue() {
		getFunctionalUnit().issue(getDestination(), getSourceLeft(), getSourceRight());
	}
	
	/**
	 * execute()
	 *   
	 * Execute the line when it is ready to be executed on its functional unit  
	 */
	public void execute() {
		getFunctionalUnit().execute(getOperation());
	}
	
	
	//LINES 124 - 177 are just setters and getters for a lines attributes
	public InstructionState getState() {
		return state;
	}
	public void setState(InstructionState state) {
		this.state = state;
	}
	
	public int getIssueClockCycle() {
		return issueClockCycle;
	}
	public void setIssueClockCycle(int issueClockCycle) {
		this.issueClockCycle = issueClockCycle;
	}
	
	public int getReadClockCycle() {
		return readClockCycle;
	}
	public void setReadClockCycle(int readClockCycle) {
		this.readClockCycle = readClockCycle;
	}
	
	public int getExecuteClockCycle() {
		return executeClockCycle;
	}
	public void setExecuteClockCycle(int executeClockCycle) {
		this.executeClockCycle = executeClockCycle;
	}
	public int getWriteClockCycle() {
		return writeClockCycle;
	}
	public void setWriteClockCycle(int writeClockCycle) {
		this.writeClockCycle = writeClockCycle;
	}
	
	public FunctionalUnit getFunctionalUnit() {
		return functionalUnit;
	}
	public StorageLocation getDestination() {
		return destination;
	}
	public StorageLocation getSourceLeft() {
		return sourceLeft;
	}
	public StorageLocation getSourceRight() {
		return sourceRight;
	}
	public Operation getOperation() {
		return operation;
	}

	public String getLine() {
		return this.line;
	}
	
	/**
	 * getStorageLocation()
	 * 
	 * Takes in the string of the register/memory location, and assigns it to its
	 * required registers/memory location objects.
	 */
	private StorageLocation getStorageLocation(String token) {
		StorageLocation sl = null;
		String number;
		switch (token.charAt(0)) {
		//if the token is an integer register
		case '$':
			
			//parse the number of the register, and assign it that specific register.
			if (token.length() > 2 && Character.isDigit(token.charAt(2))) {
				number = token.substring(1,3);
			}
			else {
				number = token.substring(1,2);
			}
			sl = Simulator.getHardware().getIntRegisters(Integer.parseInt(number));
			break;
			
		//if the token is a floating point register.
		case 'F':
			//parse the number of the register, and assign it that specific register.
			if (token.length() > 2 && Character.isDigit(token.charAt(2))) {
				number = token.substring(1,3);
			}
			else {
				number = token.substring(1,2);
			}
			sl = Simulator.getHardware().getFpRegisters(Integer.parseInt(number));
			break;
		
		default:
			int indexOfOffset = token.indexOf('(');
			
			//if the token a memory location. 
			//
			//Parse the memory location, and assign it 
			//its memory location object.
			if (indexOfOffset >= 0) {
				int offset = Integer.parseInt(token.substring(0, indexOfOffset));
				int index = Integer.parseInt(token.substring(indexOfOffset + 1, token.indexOf(')')));
				sl = Simulator.getHardware().getMemLocation(index + offset);
			}
			
			//if the token an immediate value. 
			//
			//Parse the value, assign a temporary register to the immediate value.		
			else {
				int immediate = Integer.parseInt(token);
				IntRegister temp = new IntRegister(token);
				temp.setValue(immediate);
				temp.setReadOK(true);
				temp.setWriteOK(false);
				sl = temp;
			}
			break;
		}
		
		return sl;
		
	}
	
}
