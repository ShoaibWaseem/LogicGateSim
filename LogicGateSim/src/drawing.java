/*
 * Title:			Logic Gate Simulator
 * Author: 			Shoaib Waseem
 * Student Code:	w13013878
 * University:		Northumbria University
 * Version:			Version8
 * Date:			26th April 2017 
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

	public static boolean output;

	public static int gateIndex = 0;

	public drawing() {

		setSize(canvasSizeX, canvasSizeY);
		palette = new JPanel(); // Watch where this gets placed to
		paletteTT = new JPanel();
		
		palette.setLayout(new BoxLayout(palette, BoxLayout.Y_AXIS));
		setTitle("Logic Gates");

		// drawTruthTable();

		// add gates to arraylist GatesList
		if (GatesList.isEmpty()) {
			GatesList.add(XOR);
			GatesList.add(OR);
			GatesList.add(NOR);
			GatesList.add(AND);
			GatesList.add(NAND);
		}

		// Assign components with variables
		checkAnswers = new JButton("Check");
		confirmNumberOfGates = new JButton("Confirm");

		// used to determine how complex the logic gate system will be
		cbx_numberOfGates = new JComboBox<String>(numberOfGates);
		palette.add(cbx_numberOfGates);
		cbx_numberOfGates.setSelectedIndex(0);
		cbx_numberOfGates.addActionListener(this);

		// Assign button to confirm number of logic gates
		palette.add(confirmNumberOfGates);
		confirmNumberOfGates.addActionListener(this);
		
		bHelpSheet = new JButton("Help Sheet");
		palette.add(bHelpSheet, "West");
		bHelpSheet.addActionListener(this);

		// Gate buttons on top
		getContentPane().add(palette, "North");

		drawarea = new MyCanvas();
		// Truth Tables
		//DefaultTableModel tTable = new DefaultTableModel(initialGates, ColumnHeadings);
		
		
		truthTable = new JTable(new truthTableModel());
		paletteTT.add(new JScrollPane(truthTable));
		
		paletteTT.add(checkAnswers);
		checkAnswers.addActionListener(this);
		getContentPane().add(paletteTT, "South");
		getContentPane().add((drawarea), "Center"); 
		setVisible(true); // visible true for all elements
		setResizable(false);

		// AI
		AI = new AI();
		
	}
	
	



	public void actionPerformed(ActionEvent ev) {
		
		if(("Help Sheet".equals(ev.getActionCommand()))) {
			new HelpSheet();
		}
		
		
		if (("Confirm".equals(ev.getActionCommand()))) {
			cbx_index = cbx_numberOfGates.getSelectedIndex();
			index = 0;
			gateIndex = 0;
			printedGates.clear();
			// Random Gate Generation
			if (cbx_index != 0) {
				// if gate number is chosen
				
				repaint();
			} else {
				{
					JOptionPane.showMessageDialog(null, "Please select a logic gate first");
				}
			}

		}
		
		if (("Check".equals(ev.getActionCommand()))) {
			int arrayindex = 0;
			int rowCount = truthTable.getRowCount();
			int colCount = truthTable.getColumnCount();
			TableModel dtm = truthTable.getModel();
			Object[][] userAnswers = new Object[rowCount][colCount];
			Boolean[] correctAnswers = new Boolean[112];
			boolean correct;
			
			for(int i = 0; i < rowCount; i++) {
				for(int j = 0; j < colCount; j++) {
					userAnswers[i][j] = dtm.getValueAt(i, j);
					
					correct = userAnswers[i][j].equals(completeGates[i][j]);
					if(correct) {
						correctAnswers[arrayindex] = true;
					} else {
						correctAnswers[arrayindex] = false;
					}
					arrayindex++;
				}
			}
			System.out.println(Arrays.deepToString(completeGates));
			System.out.println(Arrays.toString(correctAnswers));
			
			System.out.println(Arrays.asList(correctAnswers).contains(false));
			
			if (Arrays.asList(correctAnswers).contains(false)) {
				JOptionPane.showMessageDialog(null, "WRONG!");
			}
			else{
				JOptionPane.showMessageDialog(null, "Congratulations! You are correct!");
			}
		}
	}
	

	/***************************************************************************************/
	/**************************************MAIN CLASS***************************************/
	/***************************************************************************************/
	public static void main(String args[]) {
		new drawing(); // Calling main prog
	}

	/***************************************************************************************/
	/**************************************
	 * CANVAS CLASS
	 *************************************/
	/**************************************************************************************/
	class MyCanvas extends JPanel {

		public MyCanvas() {	
				
			setVisible(true); // Better to set visible than to not
		}
		
		public void updateGates(Graphics gfx) {
			index = randomGate.nextInt((GatesList.size() + 1) - 1);
			//print statement
			GatesList.get(index).getGate(gfx, translationX, translationY);
			addPrintedGate();
			completeTable();
			
		}

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
		
		public void paintLabels(Graphics label){
			label.drawString("A", 477, 112);
			label.drawString("B", 477, 143);
			label.drawString("C", 477, 212);
			label.drawString("D", 477, 243);
			label.drawString("E", 627, 162);
			label.drawString("F", 627, 193);
			label.drawString("G", 712, 175);
		}
		
		public void printSingleLabels(Graphics label) {
			label.drawString("A", 477, 112);
			label.drawString("B", 477, 143);
			label.drawString("E", 565, 132);
		}
		
		public void drawConnections (Graphics lines) {			
			lines.drawLine(562, 125, 637, 125);
			lines.drawLine(637, 125, 637, 162);
			lines.drawLine(562, 225, 637, 225);
			lines.drawLine(637, 225, 637, 188);
		}
		
		public void logicSolver() {

			int ttRows = 16;

			// if its the final index
			int columnA = 4;
			int columnB = 5;
			int outputColumn = 6;

			if (gateIndex == 0) {
				columnA = 0;
				columnB = 1;
				outputColumn = 4;
			} else if (gateIndex == 1) {
				columnA = 2;
				columnB = 3;
				outputColumn = 5;
			}
			for (int j = 0; j < ttRows; j++) {
				inputA = (boolean) completeGates[j][columnA];
				inputB = (boolean) completeGates[j][columnB];
				output = AI.gateOutput(inputA, inputB, currentGate);
				System.out.println(output);
				
				completeGates[j][outputColumn] = output;
				
				/*if(output == true) {
					completeGates[j][outputColumn] = true;
				} else {
					completeGates[j][outputColumn] = false;
				}*/
				truthTable.repaint();
			}
		}

		public void updateSingleGate(Graphics gfx) {
			//translationX = translationX + 270;
			//translationY = translationY + 75;
			updateGates(gfx);
		}

		public void updateFinalGate(Graphics gfx) {

			translationX = translationX + 150;
			translationY = translationY - 150;
			updateGates(gfx);
			

		}

		public void updateTwoGates(Graphics gfx) {

			for (int i = 0; i < 2; i++) {
				updateGates(gfx);
				translationY = translationY + 100;
				gateIndex++;
			}
		}

		public void updateFourGates(Graphics gfx) {

			for (int i = 0; i < 4; i++) {
				updateGates(gfx);
				translationY = translationY + 100;
			}

			translationX = translationX + 150;
			translationY = translationY - (100 * 3);
		}
		


		public void paint(Graphics gfx) {
			gfx.setColor(currentColour);
			translationX = 400;
			translationY = 0;
			printedGates.clear();

			if (cbx_index == 1) {
				updateSingleGate(gfx);
				printSingleLabels(gfx);
			}
			else if (cbx_index == 2) {
				updateTwoGates(gfx);
				updateFinalGate(gfx);
				paintLabels(gfx);
				drawConnections(gfx);
			}
			gateIndex = 0;
		}

		public void update(Graphics gfx) {
			super.paint(gfx);
		}
		// All of this needs to be enabled
		public void mouseReleased(MouseEvent e) {
		}

		public void mouseDragged(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseClicked(MouseEvent e) {
			gateIndex = 0;
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mouseMoved(MouseEvent e) {
		}
	}
	
	/***************************************************************************************/
	/**************************************
	 * Table CLASS
	 *************************************/
	/**************************************************************************************/
	class truthTableModel extends AbstractTableModel {
		String[] ColumnHeadings = { "Input A", "Input B", "Input C", "Input D", "Input E", "Input F", "Output G" };
		@Override
		public int getColumnCount() {
			return ColumnHeadings.length;
		}
		@Override
		public int getRowCount() {
			return initialGates.length;
		}
		@Override
		public String getColumnName(int col) {
			return ColumnHeadings[col];
		}
		@Override
		public Object getValueAt(int row, int col) {
			return initialGates[row][col];
		}
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return Boolean.class;
		}
		
		@Override
		public boolean isCellEditable(int row, int col) {
			if (col < 4) {
				return false;
			} else {
				return true;
			}
		}
		@Override
		public void setValueAt(Object value, int row, int col) {
            initialGates[row][col] = value;
            fireTableCellUpdated(row, col);
        }
	}
}
