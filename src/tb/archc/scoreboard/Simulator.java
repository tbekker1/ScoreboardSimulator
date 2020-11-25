package tb.archc.scoreboard;

import tb.archc.scoreboard.storage.*;
import tb.archc.scoreboard.functionalUnits.*;
import java.io.*;
import java.util.ArrayList;

public class Simulator {

	public Simulator() {
		
	}
	
	public static Hardware getHardware() {
		if (hardware == null) {
			hardware = new Hardware();
		}
		return hardware;
	}

	private static String filename;
	private static Hardware hardware = null;
	
	public static void main(String[] args) {
		if (args.length > 0) {
			filename = args[0];
			Simulator sim = new Simulator();
			sim.run();
		}
		else {
			System.out.println("Must provide file name as an argument");
		}
	
		
	}

	public void run() {
		ArrayList<LineInfo>instructions = parseFile();
		int clockCounter = 0;
		int numFinishedInstructions = 0;
		while(numFinishedInstructions < instructions.size()) {
			clockCounter++;
			//System.out.println("Clock counter " + clockCounter + "i finished " + numFinishedInstructions);
			numFinishedInstructions = 0;
			boolean issuedThisCycle = false;
			boolean stopCycle = false;
			for (int i = 0; stopCycle == false && i < instructions.size(); i++) {
				LineInfo instruction = instructions.get(i);
				switch (instruction.getState()) {
				case QUEUED:
					if (!issuedThisCycle && canIssue(instruction)) {
						instruction.setState(InstructionState.ISSUED);
						instruction.issue();
						instruction.setIssueClockCycle(clockCounter);
						issuedThisCycle = true;
						
					}
					stopCycle = true;
					break;
				
				case ISSUED:
					if (canRead(instruction)) {
						instruction.getSourceLeft().setWriteOK(false);
						if (instruction.getSourceRight() != null) {
							instruction.getSourceRight().setWriteOK(false);
						}
						instruction.setState(InstructionState.READ);
						instruction.setReadClockCycle(clockCounter);
					}
					break;
					
				case READ:
					instruction.setState(InstructionState.EXECUTING);
					instruction.getSourceLeft().setWriteOK(true);
					if (instruction.getSourceRight() != null) {
						instruction.getSourceRight().setWriteOK(true);
					}
					instruction.execute();
					if (instruction.getFunctionalUnit().isExecuting()) {
						break;
					}
				
				
				case EXECUTING:
					if (!instruction.getFunctionalUnit().isExecuting()) {
						instruction.setState(InstructionState.EXECUTED);
						instruction.setExecuteClockCycle(clockCounter);
					}
					break;
					
				case EXECUTED:
					if (canWrite(instruction)) {
						instruction.setState(InstructionState.FINISHED);
						instruction.setWriteClockCycle(clockCounter);
						instruction.getFunctionalUnit().setBusy(false);
						instruction.getDestination().setReadOK(true);
						instruction.getDestination().setWriteOK(true);
						instruction.getDestination().setUsedAsDestination(false);
						stopCycle = true;
						//output(instructions);
					}
					break;
					
				case FINISHED:
					numFinishedInstructions++;
					break;
				}
				
			}
			
			getHardware().getIntegerFU().clockCycle();
			getHardware().getFloatAddFU().clockCycle();
			getHardware().getFloatMultiplierFU().clockCycle();
			getHardware().getFloatDividerFU().clockCycle();	
			
		}
		
		output(instructions);
	}
	
	private void output(ArrayList<LineInfo> instructions) {
		for (int i = 0; i < instructions.size(); i++) {
			printLineInfo(instructions.get(i));
		}
		
		
		
	}
	
	private void printLineInfo(LineInfo line) {
		System.out.println(String.format("%-20s %5s %5s %10s %5d %5d %5d %5d",  
				line.getLine(),
				line.getDestination().getName(),
				line.getSourceLeft().getName(),
				line.getSourceRight()==null?"-" : line.getSourceRight().getName(),
				line.getIssueClockCycle(),
				line.getReadClockCycle(),
				line.getExecuteClockCycle(),
				line.getWriteClockCycle()));
						
	}
	
	private ArrayList<LineInfo> parseFile() {
		ArrayList<LineInfo> instructions = new ArrayList<LineInfo>(); 
		File mips = new File(filename);
		try {
			FileReader fr = new FileReader(mips);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line=br.readLine())!=null) {
				LineInfo lInfo = new LineInfo(line);
				instructions.add(lInfo);
			}
			br.close();
			fr.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return instructions;
	}
	
	
	/**
	 * check for structural hazard: is FU busy?
	 * check for WAW: do not issue if an 
	 *  active instruction has the same destination register
	 * @param instruction
	 * @return
	 */
	public boolean canIssue(LineInfo instruction) {
		boolean returnVal = true;
		if (instruction.getFunctionalUnit().isBusy()) {
			returnVal = false;
		}
		else if (instruction.getDestination().isUsedAsDestination()) {
			returnVal = false;
		}
		return returnVal;
	}
	
	/**
	 * Check for RAW: do not issue is
	 * any source register will be
	 * written by an active instruction
	 * @param instruction
	 * @return
	 */
	public boolean canRead(LineInfo instruction) {
		boolean returnVal = true;
		if (!instruction.getSourceLeft().isReadOK() && instruction.getSourceLeft() != instruction.getDestination() && instruction.getSourceRight() != instruction.getDestination()) {
			returnVal = false;
		}
		else if (instruction.getSourceRight() != null && !instruction.getSourceRight().isReadOK()) {
			returnVal = false;
		}
		return returnVal;
	}
	
	/**
	 * Check for WAR: do not write if read flag is busy
	 * @param instruction
	 * @return
	 */
	public boolean canWrite(LineInfo instruction) {
		boolean returnVal = true;
		if (!instruction.getDestination().isWriteOK()) {
			returnVal = false;
		}
		return returnVal;
	}
	
}
