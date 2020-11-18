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
	
	
}
