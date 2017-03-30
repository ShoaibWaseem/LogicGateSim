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

	boolean inputA;
	boolean inputB;
	boolean output;
	String Gate = "";

	public AI() {

	}

	public boolean getOutput() {
		return output;
	}

	public boolean gateOutput(boolean getinputA, boolean getinputB, String getGate) {
		inputA = getinputA;
		inputB = getinputB;
		Gate = getGate;

		int row;
		int column = 0;

		// output set to false less otherwise
		boolean output = false;

		if (Gate == "XOR") {
			for (row = 0; row < 2; row++) {
				if (logicGates[row][column] == inputA && logicGates[row][column + 1] == inputB) {
					return output = true;
				}
			}
		}

		if (Gate == "OR") {
			for (row = 2; row < 5; row++) {
				if (logicGates[row][column] == inputA && logicGates[row][column + 1] == inputB) {
					return output = true;
				}
			}
		}

		if (Gate == "NOR") {
			row = 5;
			if (logicGates[row][column] == inputA && logicGates[row][column + 1] == inputB) {
				return output = true;
			}
		}

		if (Gate == "AND") {
			row = 6;
			if (logicGates[row][column] == inputA && logicGates[row][column + 1] == inputB) {
				return output = true;
			}
		}

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
