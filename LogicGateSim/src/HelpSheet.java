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

@SuppressWarnings("serial")
public class HelpSheet extends JFrame {

	JPanel paletteHS, paletteControl;
	JTable tbl_OR, tbl_NOR, tbl_XOR, tbl_AND, tbl_NAND; 
	JTextPane txt_OR, txt_NOR, txt_XOR, txt_AND, txt_NAND;
	
	MyHSCanvas drawareaHS;
	
	Color currentColour = new Color(0, 0, 0); // black line for drawing
	Color background = new Color(128, 128, 127);
	
	Font paintFont;

	ArrayList<Gates> GatesList = new ArrayList<Gates>();
	Gates XOR = new Gates(1);
	Gates OR = new Gates(2);
	Gates NOR = new Gates(3);
	Gates AND = new Gates(4);
	Gates NAND = new Gates(5);
	
	Object[][] ORTT = {
			{false, false, false},
			{false, true, true},
			{true, false, true},
			{true, true, true}
	};
	
	Object[][] NORTT = {
			{false, false, true},
			{false, true, false},
			{true, false, false},
			{true, true, false}
	};
	
	Object[][] XORTT = {
			{false, false, false},
			{false, true, true},
			{true, false, true},
			{true, true, false}
	};
	
	Object[][] ANDTT = {
			{false, false, false},
			{false, true, false},
			{true, false, false},
			{true, true, true}
	};
	
	Object[][] NANDTT = {
			{false, false, false},
			{false, true, true},
			{true, false, true},
			{true, true, false}
	};
	
	String columnHeadings[] = {"A", "B", "C"};


	public HelpSheet() {
		
		JPanel paletteHS = new JPanel();
		JPanel paletteControl = new JPanel();
		setSize(800,800);
		setTitle("Logic Gates - Help Sheet");
		
		if (GatesList.isEmpty()) {
			GatesList.add(XOR);
			GatesList.add(OR);
			GatesList.add(NOR);
			GatesList.add(AND);
			GatesList.add(NAND);
		}
		
		paletteControl.setLayout(new GridLayout(1,2));

		paletteHS.setLayout(new GridLayout(5, 2));
		paletteHS.setSize(200, 800);
		paletteHS.setOpaque(true);
		
		tbl_OR = new JTable(ORTT, columnHeadings);
		tbl_NOR = new JTable(NORTT, columnHeadings);
		tbl_XOR = new JTable(XORTT, columnHeadings);
		tbl_AND = new JTable(ANDTT, columnHeadings);
		tbl_NAND = new JTable(NANDTT, columnHeadings);
		
		resizeColumns(tbl_OR);
		resizeColumns(tbl_NOR);
		resizeColumns(tbl_XOR);
		resizeColumns(tbl_AND);
		resizeColumns(tbl_NAND);
		
		
		txt_OR = new JTextPane();
		txt_NOR = new JTextPane();
		txt_XOR = new JTextPane();
		txt_AND = new JTextPane();
		txt_NAND = new JTextPane();
		
		
		
		txt_OR.setText("An OR Gate will output true when"
				+ " any output which enters is true");
				
		txt_NOR.setText("The inverse of the OR Gate,"
				+ " noted by the cricle at the output.\n"
				+ " Will only be true when the inputs "
				+ "are false.");
			
		txt_XOR.setText("An XOR Gate will output true "
				+ "when there is only 1 input which\n "
				+ "is true. Also known as an exclusive"
				+ " OR Gate.");
		
		txt_AND.setText("AND Gates only output true "
				+ "when both inputs are true.");
		
		
		txt_NAND.setText("The inverse of the AND "
				+ "Gate noted by the circle at the\n"
				+ " output. Will output false only "
				+ "when both inputs are true");
		
		paletteHS.add(new JScrollPane(tbl_XOR));
		paletteHS.add(txt_XOR);
		
		paletteHS.add(new JScrollPane(tbl_OR));
		paletteHS.add(txt_OR);
		
		paletteHS.add(new JScrollPane(tbl_NOR));
		paletteHS.add(txt_NOR);
		
		paletteHS.add(new JScrollPane(tbl_AND));
		paletteHS.add(txt_AND);
		
		paletteHS.add(new JScrollPane(tbl_NAND));
		paletteHS.add(txt_NAND);
		
		
		
		drawareaHS = new MyHSCanvas();
		
		setVisible(true);
		setResizable(false);
		
		paletteControl.add(drawareaHS);
		paletteControl.add(paletteHS);
		
		getContentPane().add(paletteControl);
		
		//getContentPane().add(paletteHS, "East");
		//getContentPane().add(drawareaHS, "West"); 
		main();
	}

	public void main() {
		//addMouseListener(this);			
		setVisible(true);
		//repaint(); // Better to set visible than to not
	}
	
	public void resizeColumns(JTable table) {
		
		
		for(int i = 0; i < 3; i++) {
			table.getColumnModel().getColumn(i).setMaxWidth(50);
		}
	}

	
	
	class MyHSCanvas extends JPanel {

		public MyHSCanvas() {	
				
			setVisible(true); // Better to set visible than to not
		}
		
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
		
		public void paintLabels(Graphics label, int translatey){

			int labelx = 89;
			int labely = 102;
			int labelyz = 120;
			
			label.drawString("A", labelx, labely + translatey);
			label.drawString("B", labelx, labely + 36 + translatey);
			label.drawString("Z", (labelx + 78), (labelyz + translatey));
		}

		public void paint(Graphics gfx) {
			gfx.setColor(currentColour);
			int translatey = 10;

			paintFont = new Font("TimesRoman", Font.PLAIN, 12);
	        setFont(paintFont);
	        getFontMetrics(paintFont);
			
			for (int HSGates = 0; HSGates < 5; HSGates++) {
				paintGate(HSGates, gfx);
				paintLabels(gfx, translatey);
				translatey = translatey + 140;
			}
		}
	}
}