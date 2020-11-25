package tb.archc.scoreboard;

import tb.archc.scoreboard.storage.*;
import tb.archc.scoreboard.functionalUnits.*;
import java.util.StringTokenizer;

public class LineInfo {

	public LineInfo(String line) {
		this.line = line;
		StringTokenizer st = new StringTokenizer(line);
		if (st.hasMoreTokens()) {
			String token = st.nextToken();
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
	
	public void issue() {
		getFunctionalUnit().issue(getDestination(), getSourceLeft(), getSourceRight());
	}
	
	public void execute() {
		getFunctionalUnit().execute(getOperation());
	}
	
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
	
	
	private StorageLocation getStorageLocation(String token) {
		StorageLocation sl = null;
		String number;
		switch (token.charAt(0)) {
		case '$':
			if (token.length() > 2 && Character.isDigit(token.charAt(2))) {
				number = token.substring(1,3);
			}
			else {
				number = token.substring(1,2);
			}
			sl = Simulator.getHardware().getIntRegisters(Integer.parseInt(number));
			break;
		case 'F':
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
			if (indexOfOffset >= 0) {
				int offset = Integer.parseInt(token.substring(0, indexOfOffset));
				int index = Integer.parseInt(token.substring(indexOfOffset + 1, token.indexOf(')')));
				sl = Simulator.getHardware().getMemLocation(index + offset);
			}
			else {
				int immediate = Integer.parseInt(token);
				IntRegister temp = new IntRegister("immediate");
				temp.setValue(immediate);
				temp.setReadOK(true);
				temp.setWriteOK(false);
				sl = temp;
			}
			break;
		}
		
		return sl;
		
	}
	
	public boolean isLineFinished() {
		if (getWriteClockCycle() != 0) {
			return true;
		}
		return false;
	}
	
	/*
	public static void main(String [] args) {
		String line = "ADDI $11, $23, 20";
		LineInfo li = new LineInfo(line);
		System.out.println(li.getLine());
	}
	*/
}
