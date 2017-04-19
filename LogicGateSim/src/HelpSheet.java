import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class HelpSheet extends JFrame {

	JPanel paletteHS;
	
	Color currentColour = new Color(0, 0, 0); // black line for drawing

	ArrayList<Gates> GatesList = new ArrayList<Gates>();
	Gates XOR = new Gates(1);
	Gates OR = new Gates(2);
	Gates NOR = new Gates(3);
	Gates AND = new Gates(4);
	Gates NAND = new Gates(5);
	
	int labelx;
	int labely;
	int labelz;


	public HelpSheet() {
		
		JPanel paletteHS = new JPanel();
		setSize(800, 800);
		setTitle("Logic Gates - Help Sheet");
		
		if (GatesList.isEmpty()) {
			GatesList.add(XOR);
			GatesList.add(OR);
			GatesList.add(NOR);
			GatesList.add(AND);
			GatesList.add(NAND);
		}
		

		paletteHS.setLayout(new GridLayout(5, 2, 3, 5));

		paletteHS.setOpaque(true);
		setVisible(true);
		setResizable(false);
		getContentPane().add(paletteHS, "East");
		main();
	}

	public void main() {
		//addMouseListener(this);			
		setVisible(true);
		repaint(); // Better to set visible than to not
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
	
	public void paintLabels(Graphics label){
		label.drawString("A", 477, 112);
		label.drawString("B", 477, 143);
		label.drawString("Z", 477, 212);
	}

	public void paint(Graphics gfx) {
		gfx.setColor(currentColour);
		for (int HSGates = 0; HSGates < 5; HSGates++) {
			paintGate(HSGates, gfx);
			System.out.println(HSGates);
		}
		
	}
}