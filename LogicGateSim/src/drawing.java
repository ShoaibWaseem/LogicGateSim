/*
 * Title:			Logic Gate Simulator
 * Author: 			Shoaib Waseem
 * Student Code:	w13013878
 * University:		Northumbria University
 * Version:			Version9
 * Date:			28th April 2017 
 *
 * 
 * 
 */

/***************************************************************************************/
/**************************************IMPORT MODULES***********************************/
/***************************************************************************************/
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;


/**
* Class drawing creates the window and panels with the use of 
* the swing libraries.
* Contains 2 inner classes to handle drawing and the
* interactive truth table using data model. 
*
*/


@SuppressWarnings("serial")
public class drawing extends JFrame implements ActionListener {
	/***************************************************************************************/
	/************************************** DEFINITIONS ************************************/
	/***************************************************************************************/
	
	//2 panels used to store components to add to the frame
	JPanel palette, paletteTT;

	//3 buttons, appropriately named.
	JButton confirmNumberOfGates, checkAnswers, bHelpSheet;

	//table, appropriately named, with a scrollpane for the table
	JTable truthTable;
	JScrollPane scrollPane;
	
	//combobox used for storing user selections
	JComboBox<String> cbx_numberOfGates;
	
	
	//instance of innerclass "MyCanvas", used as a drawing area for the AET library
	MyCanvas drawarea;

	//background colour for the frame(window)
	Color background = new Color(128, 128, 127);
	// black line for drawing
	Color currentColour = new Color(0, 0, 0); 

	// Canvas Size, x & y dimensions respectively
	int canvasSizeX = 1200;
	int canvasSizeY = 800;

	// coordinate translation for multiple gates
	int translationX;
	int translationY;

	//used for declaring the starting index of the combo box
	int cbx_index = 0;
	
	//index number used for selecting the relevant gate
	int index = 0;
	
	//String array used to populate combobox
	String[] numberOfGates = { "Select No. of Gates", "1", "3" };

	//random number generator for random gate - from util library
	Random randomGate = new Random();

	//instance of separate class AI, named AI
	AI AI;
	
	//Help Sheet
	HelpSheet HS;

	//List of printable Gates, stored of type Gates (Separate class)
	ArrayList<Gates> GatesList = new ArrayList<Gates>();
	Gates XOR = new Gates(1);
	Gates OR = new Gates(2);
	Gates NOR = new Gates(3);
	Gates AND = new Gates(4);
	Gates NAND = new Gates(5);

	// List of Printed Gates
	ArrayList<String> printedGates = new ArrayList<String>();

	// 3 Gates Array, empty truth table for outputs
	Object[][] initialGates = { 
			{ false, false, false, false, false, false, false },
			{ false, false, false, true, false, false, false }, 
			{ false, false, true, false, false, false, false },
			{ false, false, true, true, false, false, false }, 
			{ false, true, false, false, false, false, false },
			{ false, true, false, true, false, false, false }, 
			{ false, true, true, false, false, false, false },
			{ false, true, true, true, false, false, false }, 
			{ true, false, false, false, false, false, false },
			{ true, false, false, true, false, false, false }, 
			{ true, false, true, false, false, false, false },
			{ true, false, true, true, false, false, false }, 
			{ true, true, false, false, false, false, false },
			{ true, true, false, true, false, false, false }, 
			{ true, true, true, false, false, false, false },
			{ true, true, true, true, false, false, false }
		};
	
	// uses AI to solve the logic gate system. Answers are stored here
	Object[][] completeGates = { 
			{ false, false, false, false, false, false, false },
			{ false, false, false, true, false, false, false },
			{ false, false, true, false, false, false, false },
			{ false, false, true, true, false, false, false },
			{ false, true, false, false, false, false, false },
			{ false, true, false, true, false, false, false },
			{ false, true, true, false, false, false, false },
			{ false, true, true, true, false, false, false }, 
			{ true, false, false, false, false, false, false },
			{ true, false, false, true, false, false, false },
			{ true, false, true, false, false, false, false },
			{ true, false, true, true, false, false, false },
			{ true, true, false, false, false, false, false },
			{ true, true, false, true, false, false, false },
			{ true, true, true, false, false, false, false },
			{ true, true, true, true, false, false, false }, 
		};
	
	
	//takes the inputs to solve the output respectively
	static boolean inputA;
	static boolean inputB;
	
	//records current gates to know how to solve
	static String currentGate;

	//output used in AI
	public boolean output;

	//index number used in rng to choose a gate
	public int gateIndex = 0;

	//Constructor
	public drawing() {

		//set Size of canvas
		setSize(canvasSizeX, canvasSizeY);
		
		//2 panels to draw on, one for buttons, one for truth table (TT)
		palette = new JPanel(); 
		paletteTT = new JPanel();
		
		//set layout to use y axis for adding new components
		palette.setLayout(new BoxLayout(palette, BoxLayout.Y_AXIS));
		//Title of window set to Logic Gates
		setTitle("Logic Gates");


		// add gates to arraylist GatesList, was repeating so used isEmpty
		if (GatesList.isEmpty()) {
			GatesList.add(XOR);
			GatesList.add(OR);
			GatesList.add(NOR);
			GatesList.add(AND);
			GatesList.add(NAND);
		}

		// create 2 buttons for checking answers and confirming combobox selection
		checkAnswers = new JButton("Check");
		confirmNumberOfGates = new JButton("Confirm");

		// used to determine how complex the logic gate system will be
		cbx_numberOfGates = new JComboBox<String>(numberOfGates);
		palette.add(cbx_numberOfGates);
		cbx_numberOfGates.setSelectedIndex(0);

		// Assign button to confirm number of logic gates
		palette.add(confirmNumberOfGates);
		confirmNumberOfGates.addActionListener(this);
		
		// Assign button to open helpsheet
		bHelpSheet = new JButton("Help Sheet");
		palette.add(bHelpSheet, "West");
		bHelpSheet.addActionListener(this);

		// Gate buttons on top
		getContentPane().add(palette, "North");

		drawarea = new MyCanvas();
				
		//create a table using the inner class truthTableModel
		truthTable = new JTable(new truthTableModel());
		
		// add a scrollpane so it is not cut off.
		paletteTT.add(new JScrollPane(truthTable));
		
		//add button to palette
		paletteTT.add(checkAnswers);
		checkAnswers.addActionListener(this);
		
		//add palettes to the frame
		getContentPane().add(paletteTT, "South");
		getContentPane().add((drawarea), "Center"); 
		setVisible(true); // visible true for all elements
		setResizable(false);

		// AI
		AI = new AI();
		
	}
	
	/**
	 * Receives an action set by the user and acts appropriately based
	 * on the button 'clicked'
	 * There are 3 buttons on the page and each button has a function which
	 * is completed by this method.
	 *
	 * @param	ev	Records an action executed by the user
	 */
	public void actionPerformed(ActionEvent ev) {
		
		/*If the user wishes to use a helpsheet, the helpsheet window is opened
		 * using the class HelpSheet.java
		 * This will occur when the user clicks on the help sheet button
		 */
		if(("Help Sheet".equals(ev.getActionCommand()))) {
			new HelpSheet();
		}
		
		/** When the user clicks confirm, the program will generate the 
		 * appropriate number of logic gates based on the selection.
		 * If nothing is selected, a message box appears asking the user to
		 * select a logic gate.
		 */
		if (("Confirm".equals(ev.getActionCommand()))) {
			
			//get the index number of the combo box
			cbx_index = cbx_numberOfGates.getSelectedIndex();
			index = 0;
			gateIndex = 0;

			// Random Gate Generation when a gate number is chosen
			if (cbx_index != 0) {
				
				// remove currently printed gates list.
				printedGates.clear();
				
				//paint onto the draw area
				repaint();
			} else { //Tell user to choose a valid number
				{
					JOptionPane.showMessageDialog(null, "Please select a logic gate first");
				}
			}

		}
		/** When the user clicks check, the user answers are compared with the AI
		 * answers and stored in an array. If the array contains a false, the user
		 * has not completed the truth table successfully.
		 */
		if (("Check".equals(ev.getActionCommand()))) {
			//start inserting boolean from position 0 onwards in array omdex
			int arrayindex = 0;
			
			//accurately get size of the table.
			int rowCount = truthTable.getRowCount();
			int colCount = truthTable.getColumnCount();
			
			//create a model of the truth table, which takes user answers.
			TableModel dtm = truthTable.getModel();
			
			//store users answers from dtm in a 2D object array (easier to work with)
			Object[][] userAnswers = new Object[rowCount][colCount];
			
			//an array of type ObjectBoolean. Regular boolean works differently (to be looked at why)
			Boolean[] correctAnswers = new Boolean[112];
			
			for(int i = 0; i < rowCount; i++) {
				for(int j = 0; j < colCount; j++) {
					
					//store the table value at (row,column) in the easier to use object array
					userAnswers[i][j] = dtm.getValueAt(i, j);
					
					//store the true or false from the compare to see if the user answers == AI answers
					correctAnswers[arrayindex]  = userAnswers[i][j].equals(completeGates[i][j]);
					
					//store value in the next instance of the array.
					arrayindex++;
				}
			}
			//if the correctAnswers array contains a false, user is wrong
			if (Arrays.asList(correctAnswers).contains(false)) {
				JOptionPane.showMessageDialog(null, "WRONG!");
			}
			else { //otherwise the user is correct
				JOptionPane.showMessageDialog(null, "Congratulations! You are correct!");
			}
		}
	}
	

	/***************************************************************************************/
	/****************************MAIN CLASS FOR DRAWING.JAVA *******************************/
	/***************************************************************************************/
	public static void main(String args[]) {
		new drawing(); // Calling main prog
	}

	
	/**
	* Class MyCanvas.java creates the draw area added to a palette for graphics to draw onto.
	* The draw area is updated with new logic gates from Gates.java
	* This also handles AI.java to solve the randomly generated logic gate System. 
	* This is an innerclass for the class drawing.java
	*
	*/

	class MyCanvas extends JPanel {

		
		public MyCanvas() {	
			setVisible(true); // Better to set visible than to not
		}
		
		/**
		 * updates draw area to draw a new set of gates which are randomly 
		 * generated
		 *
		 * @param	gfx	used to draw the gates.
		 */
		public void updateGates(Graphics gfx) {
			index = randomGate.nextInt((GatesList.size() + 1) - 1);
			//print statement
			GatesList.get(index).getGate(gfx, translationX, translationY);
			addPrintedGate();
			completeTable();
			
		}

		/**
		 * add a gate to the arraylist printedGates based on the index number set in
		 * updateGates(gfx)
		 * 
		 */
		public void addPrintedGate() {
			if (index == 0) {
				printedGates.add("XOR");
			} else if (index == 1) {
				printedGates.add("OR");
			} else if (index == 2) {
				printedGates.add("NOR");
			} else if (index == 3) {
				printedGates.add("AND");
			} else if (index == 4) {
				printedGates.add("NAND");
			}
		}
		
		/**
		 * based on the gate entered in the printGates arraylist,
		 * solve the logic. 
		 */
		public void completeTable() {
			if (printedGates.get(gateIndex) == "XOR") {
				currentGate = "XOR";
				logicSolver();
			} else if (printedGates.get(gateIndex) == "OR") {
				currentGate = "OR";
				logicSolver();
			} else if (printedGates.get(gateIndex) == "NOR") {
				currentGate = "NOR";
				logicSolver();
			} else if (printedGates.get(gateIndex) == "AND") {
				currentGate = "AND";
				logicSolver();
			} else if (printedGates.get(gateIndex) == "NAND") {
				currentGate = "NAND";
				logicSolver();
			}
		}
		
		/**
		 * draw/paint the labels for 3 logic gates. Will paint when
		 * the drawing of the gates are completed.
		 * 
		 * @param	label	graphics used to draw
		 */
		
		public void paintLabels(Graphics label){
			//Syntax
			//Graphics.drawString(String, x, y)
			label.drawString("A", 477, 112);
			label.drawString("B", 477, 143);
			label.drawString("C", 477, 212);
			label.drawString("D", 477, 243);
			label.drawString("E", 627, 162);
			label.drawString("F", 627, 193);
			label.drawString("G", 712, 175);
		}
		
		/**
		 * draw/paint the labels for 1 logic gate. Will paint when
		 * the drawing of the gates are completed.
		 * 
		 * @param	label	graphics used to draw
		 */
		
		public void printSingleLabels(Graphics label) {
			//Syntax
			//Graphics.drawString(String, x, y)
			label.drawString("A", 477, 112);
			label.drawString("B", 477, 143);
			label.drawString("E", 565, 132);
		}
		/**
		 * draw/paint the connections between each logic gate
		 * so the user can follow the inputs/outputs. Only needed
		 * for 3 gates.
		 * 
		 * @param	Lines	graphics used to draw
		 */
		
		public void drawConnections (Graphics lines) {
			//Syntax
			//Graphics.drawLine(x1, y1, x2, y2)
			lines.drawLine(562, 125, 637, 125);
			lines.drawLine(637, 125, 637, 162);
			lines.drawLine(562, 225, 637, 225);
			lines.drawLine(637, 225, 637, 188);
		}
		
		/**
		 * Uses AI.java (A simple rule based System) to 
		 * solve the randomly generated logic gate system for
		 * both 1 and 3 gates respectively. The columns to read
		 * the inputs from are chosen through the gate index 
		 * number. 
		 * 
		 * @param	Lines	graphics used to draw
		 */		
		
		public void logicSolver() {

			// if its the final index, default to the final
			//values
			int columnA = 4;
			int columnB = 5;
			int outputColumn = 6;

			//If its the first drawn gate, the input and out-
			//put columns are chosen
			if (gateIndex == 0) {
				columnA = 0;
				columnB = 1;
				outputColumn = 4;
				
			//Else If its the second drawn gate, the input and 
			//output columns are chosen	
			} else if (gateIndex == 1) {
				columnA = 2;
				columnB = 3;
				outputColumn = 5;
			}
			
			//for every row in the truth table,
			//use the defined column values to
			//solve the logic gate system			
			for (int j = 0; j < 16; j++) {
				inputA = (boolean) completeGates[j][columnA];
				inputB = (boolean) completeGates[j][columnB];
				output = AI.gateOutput(inputA, inputB, currentGate);				
				completeGates[j][outputColumn] = output;
			}
		}
		
		/**
		 * Draws a single gate
		 *  
		 * @param	gfx	graphics used to draw
		 */

		public void updateSingleGate(Graphics gfx) {
			updateGates(gfx);
		}
		
		/**
		 * Draws the final gate in a 3 logic gate
		 * System. Sets x & y coordinates to draw 
		 * the final gate accurately
		 *  
		 * @param	gfx	graphics used to draw
		 */

		public void updateFinalGate(Graphics gfx) {
			translationX = translationX + 150;
			translationY = translationY - 150;
			updateGates(gfx);
		}
		
		/**
		 * Draws the first 2 gates in a 3 gate system
		 * with the same initial x co-ordinate. The
		 * y-coordinate is translated by 100 for the
		 * second.
		 *  
		 * @param	gfx	graphics used to draw
		 */

		public void updateTwoGates(Graphics gfx) {

			for (int i = 0; i < 2; i++) {
				updateGates(gfx);
				translationY = translationY + 100;
				gateIndex++;
			}
		}
		
		/**
		 * when repaint is called, the canvas is cleared
		 * and paint() is called. This handles all drawing
		 * on the draw area. The parameter Graphics is taken
		 * from the awt.Graphics library.
		 *  
		 * @param	gfx	graphics used to draw
		 */

		public void paint(Graphics gfx) {
			gfx.setColor(currentColour);
			translationX = 400;
			translationY = 0;
			printedGates.clear();

			//if 1 gate is selected in the combo box
			if (cbx_index == 1) {
				//paint 1 gate
				updateSingleGate(gfx);
				//paint the gate labels
				printSingleLabels(gfx);
			}
			//if 3 gates are selected in the combo box
			else if (cbx_index == 2) {
				//paint 2 gates
				updateTwoGates(gfx);
				//paint the final gate
				updateFinalGate(gfx);
				//paint the gate labels
				paintLabels(gfx);
				//paint the lines to connect the gates
				drawConnections(gfx);
			}
			//This needs to be here for the next list of
			//randomly generated logic gates
			gateIndex = 0;
		}

		// All of this needs to be enabled
		public void mouseReleased(MouseEvent e) {}
		public void mouseDragged(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseMoved(MouseEvent e) {}
	}
	
	/**
	* Class truthTableModel.java creates the truth table drawn in the JPanel paletteTT
	* This is constructs the table based on the override methods from the java
	* library Swing.Table.AbstractTableModel.
	* This is an innerclass for the class drawing.java
	*
	*/
	class truthTableModel extends AbstractTableModel {
		
		//Column Headings are only used for this class. Used for the Truth Table
		String[] ColumnHeadings = { "Input A", "Input B", "Input C", "Input D", "Input E", "Input F", "Output G" };
		
		/**
		 * returns the number of columns. Used to determine the size of
		 * the truth table
		 */
		@Override
		public int getColumnCount() {
			return ColumnHeadings.length;
		}
		/**
		 * returns the number of rows. Used to determine the size of
		 * the truth table
		 */
		@Override
		public int getRowCount() {
			return initialGates.length;
		}
		
		/**
		 * returns the name of the columns at a specific index, based on the
		 * ColumnHeadings array
		 * 
		 * @param	col	declares the index column number being requested
		 * 
		 */
		@Override
		public String getColumnName(int col) {
			return ColumnHeadings[col];
		}
		
		/**
		 * When the user changes the value at a cell, this method returns the
		 * changed value
		 * 
		 * @param	row	the row number for where the user changed the data
		 * @param	col	the column number for where the user changed the data
		 * 
		 */
		@Override
		public Object getValueAt(int row, int col) {
			return initialGates[row][col];
		}
		
		/**
		 * Changes every cell of every column into of type Boolean.
		 * This in lead changes the cells into checkboxes. This avoids
		 * user error of typing true/false incorrectly
		 * 
		 * @param	columnIndex	the column index number used for changing the class
		 * 
		 */
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return Boolean.class;
		}
		
		/**
		 * The user will not be able to change the initial truth table values.
		 * This method will ensure that the user can only change the values
		 * for the gate outputs. The first 4 columns will be not be editable
		 * 
		 * @param	row		the row number for editing permissions. Not used but required.
		 * @param	col		the column number for editing permissions
		 */
		@Override
		public boolean isCellEditable(int row, int col) {
			if (col < 4) {
				return false;
			} else {
				return true;
			}
		}
		
		/**
		 * sets values of the truth table.
		 * 
		 * @param	value	value of the table
		 * @param	row		the row number for the value to be set
		 * @param	col		the column number for the value to be set
		 */
		@Override
		public void setValueAt(Object value, int row, int col) {
            initialGates[row][col] = value;
            fireTableCellUpdated(row, col);
        }
	}
}
