/**
* Class AI uses an array of true statements depending on the gate chosen
* The class takes inputs from drawing.java and returns a boolean value
* which is used to solve the randomly generated logic gate system
* 
*
*/
public class AI {

	public static Boolean logicGates[][] = {

			// Input A Input B
			// XOR true Statements
			{ false, true }, 
			{ true, false },
			// OR True Statements
			{ false, true }, 
			{ true, false },
			{ true, true },
			// NOR true Statement
			{ false, false },
			// AND true Statement
			{ true, true },
			// NAND true Statements
			{ false, false },
			{ false, true },
			{ true, false } 
	};

	//input and output values
	boolean inputA;
	boolean inputB;
	boolean output;
	
	//kept throwing an error, had to add speech marks
	String Gate = "";

	//better here than not
	public AI() {}
	
	/**
	 * solves the AI based on the inputs when the logic gates are drawn 1 by 1.
	 * Uses the single global array as cases when the output is true.
	 * The rows are  predefined depending on the gate.
	 *
	 * @param	getinputA	takes input A from the logic gate
	 * @param	getinputB	takes input B from the logic gate
	 * @String	getGate		takes the gate so the AI knows how to solve it.
	 */

	public boolean gateOutput(boolean getinputA, boolean getinputB, String getGate) {
		inputA = getinputA;
		inputB = getinputB;
		Gate = getGate;

		int row;
		//first column in the array
		int column = 0;

		// output set to false less otherwise
		boolean output = false;

		//if the gate is a XOR, use rows 0 & 1, as there are 2 cases where the out for an XOR is true
		if (Gate == "XOR") {
			for (row = 0; row < 2; row++) {
				if (logicGates[row][column] == inputA && logicGates[row][column + 1] == inputB) {
					return output = true;
				}
			}
		}

		//if the gate is a OR, use rows 2 - 4, as there are 3 cases where the out for an OR is true
		if (Gate == "OR") {
			for (row = 2; row < 5; row++) {
				if (logicGates[row][column] == inputA && logicGates[row][column + 1] == inputB) {
					return output = true;
				}
			}
		}
		//if the gate is a NOR, use row 5 , as there is 1 case where the out for an NOR is true
		if (Gate == "NOR") {
			row = 5;
			if (logicGates[row][column] == inputA && logicGates[row][column + 1] == inputB) {
				return output = true;
			}
		}
		//if the gate is a AND, use row 6, as there is 1 case where the out for an AND is true
		if (Gate == "AND") {
			row = 6;
			if (logicGates[row][column] == inputA && logicGates[row][column + 1] == inputB) {
				return output = true;
			}
		}
		
		//if the gate is a NAND, use rows 7 - 9, as there are 3 cases where the out for an NAND is true
		if (Gate == "NAND") {
			for (row = 7; row < 10; row++) {
				if (logicGates[row][column] == inputA && logicGates[row][column + 1] == inputB) {
					return output = true;
				}
			}
		}
		return output;
	}

}
