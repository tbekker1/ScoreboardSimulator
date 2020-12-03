package tb.archc.scoreboard;

import tb.archc.scoreboard.storage.*;
import tb.archc.scoreboard.functionalUnits.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Class Simulator:
 * The main class for the scoreboarding simulator. Acts as a CPU, and instantiates the program
 * Reads all of the lines in the file, and runs the actual scoreboard.
 */
public class Simulator {
	
	
	/**
	 * getHardware()
	 * Instantiates the hardware class (creates all of the registers, memory locations, and functional units)
	 */
	public static Hardware getHardware() {
		if (hardware == null) {
			hardware = new Hardware();
		}
		return hardware;
	}

	private static String filename;
	private static Hardware hardware = null;
	
	
	/**
	 * main()
	 * Reads the argument for the file name, and instantiates the simulator. Calls the run function 
	 */
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

	/**
	 * run()
	 * The crux of the program. Parses the lines in the file, 
	 * calls the LineInfo class to change the string line into objects.
	 * 
	 * Each LineInfo contains a single line's required functional unit, registers, operation... etc
	 * 
	 * It then runs through the entire logic to scoreboard the instructions
	 *  
	 */
	public void run() {
		//parses the file into an arraylist of instructions of LineInfo type. 
		ArrayList<LineInfo>instructions = parseFile();
		
		int clockCounter = 0;
		int numFinishedInstructions = 0;
		
		//continue looping until all of the instructions finish. Each loop is one clock cycle.
		while(numFinishedInstructions < instructions.size()) {
			//increment the clock cycle, and get a new StateManager.
			clockCounter++;
			StateManager stateManager = new StateManager(); 
			numFinishedInstructions = 0;
			boolean issuedThisCycle = false;
			boolean stopCycle = false;
			
			//loop through all of the instructions in the arraylist for one clock cycle.
			for (int i = 0; stopCycle == false && i < instructions.size(); i++) {
				LineInfo instruction = instructions.get(i);
				switch (instruction.getState()) {
				
				//If the instruction is yet to issue
				case QUEUED:
					
					//check if it can issue this clock cycle
					if (!issuedThisCycle && canIssue(instruction)) {
						//change the state of the instruction to issued, and actually issue the instruction
						instruction.setState(InstructionState.ISSUED);
						instruction.issue();
						instruction.setIssueClockCycle(clockCounter);
						
						//if the destination is ok to write, request a reading lock
						if (instruction.getDestination().isWriteOK()) {
							instruction.getDestination().requestReadLock(true);
						}
						
						//set the functional unit to busy and set the destination register to in use
						stateManager.setBusyTrue(instruction.getFunctionalUnit());
						stateManager.setUsedAsDestTrue(instruction.getDestination());
						issuedThisCycle = true;
						
					}
					issuedThisCycle = true;
					break;
				
				//If the instruction has yet to read
				case ISSUED:
					
					//check if the instruction can read on this clock cycle
					if (canRead(instruction)) {
						
						//make sure you aren't locking write within its own instruction,
						//and request a lock for the sources for writing while it it reading (for the next clock cycle)
						if (instruction.getSourceLeft() != instruction.getDestination()) {
							instruction.getSourceLeft().requestWriteLock(true);
						}
						
						if (instruction.getSourceRight() != null &&
								instruction.getDestination()!= instruction.getSourceRight()){
							instruction.getSourceRight().requestWriteLock(true);
						}
						
						//change the state of the instruction to read.
						instruction.setState(InstructionState.READ);
						instruction.setReadClockCycle(clockCounter);
						
						//after reading, request an unlock for the sources for writing (for the next clock cycle)
						instruction.getSourceLeft().requestWriteLock(false);
						if (instruction.getSourceRight() != null) {
							instruction.getSourceRight().requestWriteLock(false);
						}
					}
					//if you cannot read this cycle, make sure that the sources are locked for writing as well.
					else {
						if (instruction.getSourceLeft().isReadOK() == true &&
								instruction.getSourceLeft().isWriteOK() == true &&
								instruction.getSourceLeft() != instruction.getDestination()) {
							instruction.getSourceLeft().requestWriteLock(true);
						}
						
						if (instruction.getSourceRight() != null &&
								instruction.getSourceRight().isReadOK() == true &&
								instruction.getSourceRight().isWriteOK() == true &&
								instruction.getDestination()!= instruction.getSourceRight()) {
							instruction.getSourceRight().requestWriteLock(true);
						}
					}
					break;
				
				//If the instruction has been read, but is yet to execute
				case READ:
					
					//set the state to executing, execute the instruction, and break out.
					instruction.setState(InstructionState.EXECUTING);
					
					instruction.execute();
					if (instruction.getFunctionalUnit().isExecuting()) {
						break;
					}
				
				//For instructions that are executing
				case EXECUTING:
					//check if the instruction has finished executing this clock cycle.
					if (!instruction.getFunctionalUnit().isExecuting()) {
						
						//if it finished executing, request that to lock 
						//the destination for reading for the next clock cycle, 
						//and set the state to executed.
						if (instruction.getDestination().isReadOK() && instruction.getDestination().isWriteOK()) {
							instruction.getDestination().requestReadLock(true);
						}
						
						instruction.setState(InstructionState.EXECUTED);
						instruction.setExecuteClockCycle(clockCounter);
					}
					break;
				
				//if the instruction has already finished executing
				case EXECUTED:
					//check if the instruction can write this cycle
    				if (canWrite(instruction)) {
    					
    					//if it can, set the state to finished,
    					//request an unlock for the destination in both reading and writing.
    					//Set the functional unit to not busy, and used as destination to false
						instruction.setState(InstructionState.FINISHED);
						instruction.setWriteClockCycle(clockCounter);
				
						instruction.getDestination().requestReadLock(false);
						instruction.getDestination().requestWriteLock(false);
						
						stateManager.setBusyFalse(instruction.getFunctionalUnit());
						stateManager.setUsedAsDestFalse(instruction.getDestination());
					}
					break;
				
				//if the instruction has already completed everything
				case FINISHED:
					numFinishedInstructions++;
					break;
				}
				
				
			}
			
			//start the clock cycle for all of the hardware components (for each clock cycle)
			getHardware().getIntegerFU().clockCycle();
			getHardware().getFloatAddFU().clockCycle();
			getHardware().getFloatMultiplierFU().clockCycle();
			getHardware().getFloatDividerFU().clockCycle();	
			
			//manages the requests for a functional unit to be busy, and the usedAsDestination flag.
			//Also executes the locks for registers
			stateManager.clockCycle();
		}
		
		//at the end of all of the scoreboarding, output the results.
		output(instructions);
	}
	
	/**
	 * output()
	 * takes in the arraylist of instructions, and prints alll of the output
	 */
	private void output(ArrayList<LineInfo> instructions) {
		
		//print the header
		System.out.println(String.format("%-20s %5s %5s %5s %10s %5s %5s %5s",  
				"Instruction",
				"dest",
				"src1",
				"src2",
				"Issue",
				"Read",
				"Exec",
				"Write"));
		
		System.out.println("-------------------------------------------------------------------");
		
		for (int i = 0; i < instructions.size(); i++) {
			//calls print line info to get the information 
			//of when each instruction finished each phase. Prints the scoreboarding table
			printLineInfo(instructions.get(i));
		}
		
		//Prints all of the Float registers and the values in them
		System.out.println();
		System.out.println("Floating Point Registers");
		FpRegister [] fpRegisters = Simulator.getHardware().getAllFpRegisters();
		for (int i = 0; i < fpRegisters.length; i++) {
			System.out.print(String.format("%8s ", fpRegisters[i].getName()));
		}
		System.out.println();
		for (int i = 0; i < fpRegisters.length; i++) {
			System.out.print(String.format("%8.2f ", fpRegisters[i].getValue()));
		}
		System.out.println();
		
		//prints all of the integer registers, and the values in them
		System.out.println();
		System.out.println("Integer Registers");
		IntRegister [] intRegisters = Simulator.getHardware().getAllIntRegisters();
		for (int i = 0; i < intRegisters.length; i++) {
			System.out.print(String.format("%8s ", intRegisters[i].getName()));
		}
		System.out.println();
		for (int i = 0; i < intRegisters.length; i++) {
			System.out.print(String.format("%8d ", intRegisters[i].getValue()));
		}
		System.out.println();
		
		//prints all of the memory locations, and the values in them.
		System.out.println();
		System.out.println("Memory");
		MemoryLocation [] memoryLocations = Simulator.getHardware().getAllMemoryLocations();
		for (int i = 0; i < memoryLocations.length; i++) {
			System.out.print(String.format("%8s ", memoryLocations[i].getName()));
		}
		System.out.println();
		for (int i = 0; i < memoryLocations.length; i++) {
			System.out.print(String.format("%8.2f ", memoryLocations[i].read()));
		}
		System.out.println();
		
	}
	
	/**
	 * printLineInfo()
	 * takes in the line and prints the instruction, the registers it used, 
	 * and what cycle it finished each phase.
	 */
	private void printLineInfo(LineInfo line) {
		System.out.println(String.format("%-20s %5s %5s %5s %10d %5d %5d %5d",  
				line.getLine(),
				line.getDestination().getName(),
				line.getSourceLeft().getName(),
				line.getSourceRight()==null?"-" : line.getSourceRight().getName(),
				line.getIssueClockCycle(),
				line.getReadClockCycle(),
				line.getExecuteClockCycle(),
				line.getWriteClockCycle()));
						
	}
	
	
	/**
	 * parseFile()
	 * Opens the file, and parses the file line by line.
	 */
	private ArrayList<LineInfo> parseFile() {
		ArrayList<LineInfo> instructions = new ArrayList<LineInfo>(); 
		File mips = new File(filename);
		try {
			FileReader fr = new FileReader(mips);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line=br.readLine())!=null) {
				LineInfo lInfo = new LineInfo(line);
				if (lInfo.isValidInstruction()) {
					instructions.add(lInfo);
				}
			}
			br.close();
			fr.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return instructions;
	}
	
	
	/**
	 * canIssue()
	 * 
	 * check for structural hazard: is FU busy?
	 * check for WAW: do not issue if an 
	 *  active instruction has the same destination register
	 * @param instruction
	 * @return
	 */
	public boolean canIssue(LineInfo instruction) {
		boolean returnVal = true;
		//is the functional unit busy?
		if (instruction.getFunctionalUnit().isBusy()) {
			returnVal = false;
		}
		//is the destination being used somewhere else?
		else if (instruction.getDestination().isUsedAsDestination()) {
			returnVal = false;
		}
		return returnVal;
	}
	
	/**
	 * canRead()
	 * 
	 * Check for RAW: do not issue if
	 * any source register will be
	 * written by an active instruction
	 * @param instruction
	 * @return
	 */
	public boolean canRead(LineInfo instruction) {
		boolean returnVal = true;
		//can we read from the left source?
		if (!instruction.getSourceLeft().isReadOK() && 
				instruction.getSourceLeft() != instruction.getDestination()) {
			returnVal = false;
		}
		//can we read from the right source?
		else if (instruction.getSourceRight() != null && 
				!instruction.getSourceRight().isReadOK() &&
				instruction.getSourceRight() != instruction.getDestination()) {
			returnVal = false;
		}
		return returnVal;
	}
	
	/**
	 * canWrite()
	 * 
	 * Check for WAR: do not write if read flag is busy
	 * @param instruction
	 * @return
	 */
	public boolean canWrite(LineInfo instruction) {
		boolean returnVal = true;
		//can we write to the destination yet?
		if (!instruction.getDestination().isWriteOK()) {
			returnVal = false;
		}
		return returnVal;
	}
	
}
