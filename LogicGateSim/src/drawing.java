/*
 * Title:			Logic Gate Simulator
 * Author: 			Shoaib Waseem
 * Student Code:	w13013878
 * University:		Northumbria University
 * Version:			Version5
 * Date:			3rd April 2017 
 * 
 * 
 * 
 * 
 * 
 */

/***************************************************************************************/
/**************************************IMPORT MODULES***********************************/
/***************************************************************************************/
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class drawing extends JFrame implements ActionListener {
	/***************************************************************************************/
	/************************************** DEFINITIONS **************************************/
	/***************************************************************************************/
	JPanel palette, tools, paletteTT;

	JButton confirmNumberOfGates, checkAnswers;

	JTable truthTable;
	JScrollPane scrollPane;

	MyCanvas drawarea;

	Color background = new Color(128, 128, 127);

	JComboBox<String> cbx_numberOfGates;

	// Canvas Size
	int canvasSizeX = 1200;
	int canvasSizeY = 800;


	// coordinate translation for multiple gates
	int translationX;
	int translationY;

	int cbx_index = 0;
	int index = 0;

	Color currentColour = new Color(0, 0, 0); // black line for drawing

	// used for the combo box,
	String[] numberOfGates = { "Select No. of Gates", "1", "3" };

	int[] threeGatesList = new int[3];

	// co-ordinates used for 1 gate combo box selection - x, y
	int[] oneGateCoord = { 300, 400 };

	// rng for random gate
	Random randomGate = new Random();

	// AI
	AI AI;

	// List of printable Gates
	ArrayList<Gates> GatesList = new ArrayList<Gates>();
	Gates XOR = new Gates(1);
	Gates OR = new Gates(2);
	Gates NOR = new Gates(3);
	Gates AND = new Gates(4);
	Gates NAND = new Gates(5);

	// List of Printed Gates
	ArrayList<String> printedGates = new ArrayList<String>();

	// Column Names

	// test save
	String[] ColumnHeadings = { "A", "B", "C", "D", "E", "F", "G" };


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
	// 7 Gates Array
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
	
	
	
	
	static boolean inputA;
	static boolean inputB;
	static String currentGate;

	public static boolean output;

	public static int gateIndex;

	public drawing() {

		setSize(canvasSizeX, canvasSizeY);
		palette = new JPanel(); // Watch where this gets placed to
		paletteTT = new JPanel();
		palette.setLayout(new GridLayout(2, 3, 5, 5)); // grid layout
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

		// Gate buttons on top
		getContentPane().add(palette, "North");

		// Truth Tables

		DefaultTableModel tTable = new DefaultTableModel(initialGates, ColumnHeadings);
		truthTable = new JTable(tTable);
		// paletteTT.add(truthTable);
		paletteTT.add(new JScrollPane(truthTable));
		
		paletteTT.add(checkAnswers);
		checkAnswers.addActionListener(this);
		
		getContentPane().add(paletteTT, "South");

		// Draw area
		drawarea = new MyCanvas(); // MyCanvas class
		getContentPane().add(drawarea, "Center"); // Draw stuff in the centre
		setVisible(true); // visible true for all elements

		// AI
		AI = new AI();

	}

	public void actionPerformed(ActionEvent ev) {

		if (("Confirm".equals(ev.getActionCommand()))) {
			cbx_index = cbx_numberOfGates.getSelectedIndex();
			index = 0;
			// Random Gate Generation
			if (cbx_index != 0) {
				// if gate number is chosen
				gateIndex = 0;
				printedGates.clear();
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
			DefaultTableModel dtm = (DefaultTableModel) truthTable.getModel();
			Object[][] userAnswers = new Object[rowCount][colCount];
			boolean[] correctAnswers = new boolean[112];
			boolean sillycheck;
			
			for(int i = 0; i < rowCount; i++) {
				for(int j = 0; j < colCount; j++) {
					userAnswers[i][j] = dtm.getValueAt(i, j);
					System.out.println("Row: " + i + " Column: " + j + " Value: " + userAnswers[i][j]);
					
					sillycheck = userAnswers[i][j].equals(completeGates[i][j]);
					System.out.println(sillycheck);
					if(sillycheck) {
						correctAnswers[arrayindex] = true;
					} else {
						correctAnswers[arrayindex] = false;
					}
					arrayindex++;
				}
				
			}
			
			

			/*for (int col = 4; col < 7; col++) {
				System.out.println("Column: " + col);
				for (int x = 0; x < 16; x++) {
					blah = userAnswers[x][col].equals(completeGates[x][col]);
					if(userAnswers[x][col].equals(completeGates[x][col])) {
						correctAnswers[arrayindex] = true;
					} else {
						correctAnswers[arrayindex] = false;
					}
				}
			}		*/	
			System.out.println(Arrays.toString(correctAnswers));

			
			
			//was needed to work. Would rather not use a local variable for no reason
			boolean correctAnswer = Arrays.asList(correctAnswers).contains(false);
			
			if (correctAnswer == false) {
				JOptionPane.showMessageDialog(null, "WRONG!");
			}
			else if (correctAnswer == true){
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
	class MyCanvas extends JPanel implements MouseListener, MouseMotionListener {

		public MyCanvas() {
			addMouseListener(this);
			// addMouseMotionListener( this);

			setVisible(true); // Better to set visible than to not

		}

		public void updateGates(Graphics gfx) {
			index = randomGate.nextInt((GatesList.size() + 1) - 1);
			System.out.println(index);
			GatesList.get(index).getGate(gfx, translationX, translationY);
			addPrintedGate();
			completeTable();
			gateIndex++;
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
				System.out.println("Pass Number " + j);
				System.out.println(currentGate);

				inputA = (boolean) completeGates[j][columnA];
				System.out.println("input A: " + inputA);
				inputB = (boolean) completeGates[j][columnB];
				System.out.println("input B: " + inputB);

				output = AI.gateOutput(inputA, inputB, currentGate);
				completeGates[j][outputColumn] = output;
				
				truthTable.repaint();
			}
		}

		public void updateSingleGate(Graphics gfx) {
			translationX = translationX + 270;
			translationY = translationY + 75;
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

		// for testing array list printedGates
		public void getAll() {
			for (String printed : printedGates) {
				System.out.println(printed);
			}
		}

		public void paint(Graphics gfx) {
			gfx.setColor(currentColour);
			translationX = 400;
			translationY = 0;
			printedGates.clear();

			if (cbx_index == 1) {

				updateSingleGate(gfx);
			}
			else if (cbx_index == 2) {

				updateTwoGates(gfx);
				updateFinalGate(gfx);
			}
			getAll();
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
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mouseMoved(MouseEvent e) {
		}
	}
}