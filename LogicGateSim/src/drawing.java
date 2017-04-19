/*
 * Title:			Logic Gate Simulator
 * Author: 			Shoaib Waseem
 * Student Code:	w13013878
 * University:		Northumbria University
 * Version:			Version7
 * Date:			18th April 2017 
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
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class drawing extends JFrame implements ActionListener {
	/***************************************************************************************/
	/************************************** DEFINITIONS ************************************/
	/***************************************************************************************/
	JPanel palette, paletteTT;

	JButton confirmNumberOfGates, checkAnswers, bHelpSheet;

	JTable truthTable;
	JScrollPane scrollPane;
	
	JComboBox<String> cbx_numberOfGates;
	
	MyCanvas drawarea;

	Color background = new Color(128, 128, 127);

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
	
	//Help Sheet
	HelpSheet HS;

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
		DefaultTableModel tTable = new DefaultTableModel(initialGates, ColumnHeadings);
		truthTable = new JTable(tTable);
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
			DefaultTableModel dtm = (DefaultTableModel) truthTable.getModel();
			Object[][] userAnswers = new Object[rowCount][colCount];
			boolean[] correctAnswers = new boolean[112];
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
			label.drawString("C", 565, 132);
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
				completeGates[j][outputColumn] = output;
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
}