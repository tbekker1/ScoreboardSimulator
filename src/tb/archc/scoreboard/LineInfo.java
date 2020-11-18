package tb.archc.scoreboard;

import tb.archc.scoreboard.storage.*;
import tb.archc.scoreboard.functionalUnits.*;
import java.util.StringTokenizer;

public class LineInfo {

	public LineInfo(String line) {
		this.line = line;
		StringTokenizer st = new StringTokenizer(line);
		if (st.hasMoreTokens()) {
			String operation = st.nextToken();
			parseOperation(operation);
			if (st.hasMoreTokens()) {
				String location1 = st.nextToken();
				if (st.hasMoreTokens()) {
					String location2 = st.nextToken();
					if (st.hasMoreTokens()) {
						String location3 = st.nextToken();
					}
				}
			}
	    }
	}

	private FunctionalUnit functionalUnit;
	private Operation operation;
	private StorageLocation destination;
	private StorageLocation sourceLeft;
	private StorageLocation sourceRight;
	private String line;
	
	
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
	
	private void parseOperation(String token){
		if ("L.D".equals(token.substring(3))){
			this.operation = Operation.LOAD;
			this.functionalUnit = Simulator.getHardware().getIntegerFU();
		}
		else if ("S.D".equals(token.substring(3))){
			this.operation = Operation.STORE;
			this.functionalUnit = Simulator.getHardware().getIntegerFU();
		}
		else if ("ADD".equals(token.substring(3))){
			this.operation = Operation.ADD;
			this.functionalUnit = Simulator.getHardware().getIntegerFU();
		}
		else if ("SUB".equals(token.substring(3))){
			this.operation = Operation.SUBTRACT;
			this.functionalUnit = Simulator.getHardware().getIntegerFU();
		}
		
	}
	
	
}
