package tb.archc.scoreboard;

/**
 * enum InstructionState
 * 
 * Just an enumeration for what state an instruction currently is in.
 */
public enum InstructionState {
	QUEUED, ISSUED, READ, EXECUTING, EXECUTED, FINISHED
}
