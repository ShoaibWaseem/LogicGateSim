/***************************************************************************************/
/**************************************IMPORT MODULES***********************************/
/***************************************************************************************/
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;

/**
 * Class HelpSheet uses JFrame to create a new window with relevant information
 * which should aid the user in understanding how each gate looks, its truth
 * table and a text description of its function.
 * 
 * A inner class was created again for a draw area for the Graphics. This was
 * the best way to ensure that adding a JPanel did not overwrite the drawings.
 * 
 *
 */

@SuppressWarnings("serial")
public class HelpSheet extends JFrame {

	// 2 panels, one for the draw area, another to move the
	// truth tables and description for the gate on the right.
	JPanel paletteHS, paletteControl;

	// every gate present in the system has a truth table
	JTable tbl_OR, tbl_NOR, tbl_XOR, tbl_AND, tbl_NAND;

	// contains the description of every gate, respectively
	JTextPane txt_OR, txt_NOR, txt_XOR, txt_AND, txt_NAND;

	// Helpsheet draw area
	MyHSCanvas drawareaHS;

	// better to set than not, self explanatory variables
	Color currentColour = new Color(0, 0, 0); // black line
	Color background = new Color(128, 128, 127);

	// This had to be enabled for the text to appear, causes lag.
	Font paintFont;

	// array list of gates, identical to drawing.Java
	ArrayList<Gates> GatesList = new ArrayList<Gates>();
	Gates XOR = new Gates(1);
	Gates OR = new Gates(2);
	Gates NOR = new Gates(3);
	Gates AND = new Gates(4);
	Gates NAND = new Gates(5);

	/*
	 * Truth tables for every logic gate in the system. Format: (Input A,
	 * InputB, Output)
	 */
	Object[][] ORTT = { { false, false, false }, { false, true, true }, { true, false, true }, { true, true, true } };

	Object[][] NORTT = { { false, false, true }, { false, true, false }, { true, false, false },
			{ true, true, false } };

	Object[][] XORTT = { { false, false, false }, { false, true, true }, { true, false, true }, { true, true, false } };

	Object[][] ANDTT = { { false, false, false }, { false, true, false }, { true, false, false },
			{ true, true, true } };

	Object[][] NANDTT = { { false, false, false }, { false, true, true }, { true, false, true },
			{ true, true, false } };

	// as all tables will share the same column headings, only need 1 instance
	String columnHeadings[] = { "A", "B", "C" };

	public HelpSheet() {
		// creating the panels as panels, locally.
		JPanel paletteHS = new JPanel();
		JPanel paletteControl = new JPanel();

		// setting size of window (x,y)
		setSize(800, 800);

		// setting title
		setTitle("Logic Gates - Help Sheet");

		// add gates to the list if empty.
		if (GatesList.isEmpty()) {
			GatesList.add(XOR);
			GatesList.add(OR);
			GatesList.add(NOR);
			GatesList.add(AND);
			GatesList.add(NAND);
		}

		// 1 row, 2 column layout. 1st column for draw area
		paletteControl.setLayout(new GridLayout(1, 2));

		// 5 rows, 2 columns. 1 row per gate and columns for truth table and
		// text
		paletteHS.setLayout(new GridLayout(5, 2));

		// kept causing errors. I would like to remove.
		paletteHS.setSize(200, 800);

		// better set than not
		paletteHS.setOpaque(true);

		// creating the tables as a JTable
		tbl_OR = new JTable(ORTT, columnHeadings);
		tbl_NOR = new JTable(NORTT, columnHeadings);
		tbl_XOR = new JTable(XORTT, columnHeadings);
		tbl_AND = new JTable(ANDTT, columnHeadings);
		tbl_NAND = new JTable(NANDTT, columnHeadings);

		// making the columns smaller, makes it look more appropriate
		resizeColumns(tbl_OR);
		resizeColumns(tbl_NOR);
		resizeColumns(tbl_XOR);
		resizeColumns(tbl_AND);
		resizeColumns(tbl_NAND);

		// Text pane for the gates
		txt_OR = new JTextPane();
		txt_NOR = new JTextPane();
		txt_XOR = new JTextPane();
		txt_AND = new JTextPane();
		txt_NAND = new JTextPane();

		// Description of OR
		txt_OR.setText("An OR Gate will output true when" + " any output which enters is true");

		// Description of NOR
		txt_NOR.setText("The inverse of the OR Gate," + " noted by the cricle at the output.\n"
				+ " Will only be true when the inputs " + "are false.");

		// Description of AND
		txt_XOR.setText("An XOR Gate will output true " + "when there is only 1 input which\n "
				+ "is true. Also known as an exclusive" + " OR Gate.");

		// Description of AND
		txt_AND.setText("AND Gates only output true " + "when both inputs are true.");

		// Description of NAND
		txt_NAND.setText("The inverse of the AND " + "Gate noted by the circle at the\n"
				+ " output. Will output false only " + "when both inputs are true");

		// Row 1
		// Add truth tables with a scrollpane so it does not overlap
		// Add string into the 2nd column
		paletteHS.add(new JScrollPane(tbl_XOR));
		paletteHS.add(txt_XOR);

		// Row 2
		paletteHS.add(new JScrollPane(tbl_OR));
		paletteHS.add(txt_OR);

		// Row 3
		paletteHS.add(new JScrollPane(tbl_NOR));
		paletteHS.add(txt_NOR);

		// Row 4
		paletteHS.add(new JScrollPane(tbl_AND));
		paletteHS.add(txt_AND);

		// Row 5
		paletteHS.add(new JScrollPane(tbl_NAND));
		paletteHS.add(txt_NAND);

		// New Draw Area
		drawareaHS = new MyHSCanvas();

		// Better set than not.
		setVisible(true);

		// causes a redraw which messes the layout
		setResizable(false);

		// add the draw area in column 1
		paletteControl.add(drawareaHS);
		// add the Truth tables and description in column 2
		paletteControl.add(paletteHS);

		// add the completed Panel to the window
		getContentPane().add(paletteControl);

		// Makes the window visible.
		main();
	}

	/**
	 * main method. makes the entire window visible
	 * 
	 */
	public void main() {
		setVisible(true);
	}

	/**
	 * set an appropriate column size for every truth table. takes a table and
	 * resizes it. There are only 3 columns so it loops 3 times
	 * 
	 * @param table
	 *            Truth table
	 * 
	 */
	public void resizeColumns(JTable table) {
		for (int i = 0; i < 3; i++) {
			table.getColumnModel().getColumn(i).setMaxWidth(50);
		}
	}

	/**
	 * Class MyHSCanvas.java creates the draw area added to a palette for
	 * graphics to draw onto. The draw area is updated with new logic gates from
	 * Gates.java This is an innerclass for the class HelpSheet.java
	 *
	 */
	class MyHSCanvas extends JPanel {

		public MyHSCanvas() {
			setVisible(true); // Better to set visible than to not
		}

		/**
		 * paints every gate 1 by 1 based on the paint() method.
		 * 
		 * @param gatenumber
		 *            index number in the list
		 * @param gfx
		 *            graphics used to draw
		 */
		public void paintGate(int gatenumber, Graphics gfx) {

			if (gatenumber == 0) {
				GatesList.get(gatenumber).getGate(gfx, 10, 10);
			} else if (gatenumber == 1) {
				GatesList.get(gatenumber).getGate(gfx, 10, 150);
			} else if (gatenumber == 2) {
				GatesList.get(gatenumber).getGate(gfx, 10, 290);
			} else if (gatenumber == 3) {
				GatesList.get(gatenumber).getGate(gfx, 10, 430);
			} else if (gatenumber == 4) {
				GatesList.get(gatenumber).getGate(gfx, 10, 570);
			} else if (gatenumber == 5) {
				GatesList.get(gatenumber).getGate(gfx, 10, 710);
			}
		}

		/**
		 * draw/paint the labels for 1 logic gate at a time. Will paint when the
		 * drawing of the gates are completed.
		 * 
		 * @param label
		 *            graphics used to draw
		 * @param translatey
		 *            moving the labels down for the next gate
		 */

		public void paintLabels(Graphics label, int translatey) {

			int labelx = 89;
			int labely = 102;
			int labelyz = 120;

			label.drawString("A", labelx, labely + translatey);
			label.drawString("B", labelx, labely + 36 + translatey);
			label.drawString("Z", (labelx + 78), (labelyz + translatey));
		}

		/**
		 * when repaint is called, the canvas is cleared and paint() is called.
		 * This handles all drawing on the draw area. The parameter Graphics is
		 * taken from the awt.Graphics library.
		 * 
		 * @param gfx
		 *            graphics used to draw
		 */
		public void paint(Graphics gfx) {

			// paint it black
			gfx.setColor(currentColour);

			// base number
			int translatey = 10;

			// wouldnt print the text otherwise.
			paintFont = new Font("TimesRoman", Font.PLAIN, 12);
			setFont(paintFont);
			getFontMetrics(paintFont);

			// loops through every gate
			for (int HSGates = 0; HSGates < 5; HSGates++) {
				paintGate(HSGates, gfx);
				paintLabels(gfx, translatey);
				translatey = translatey + 140;
			}
		}
	}
}