/*import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

	/***************************************************************************************/
	/**************************************CANVAS CLASS*************************************/
	/***************************************************************************************/
	/*public class canvas extends JPanel implements MouseListener,  MouseMotionListener
	{
		
		int gate;
		
	    drawingOR ORGate; 		// OR gate class
	    drawingAND ANDGate; 	// AND gate class
	    drawingXOR XORGate; 	// XOR gate class
	    drawingNOR NORGate;		// NOR gate class
	    drawingNAND NANDGate;	// NAND gate class
		
	    // Instance of classes
		ORGate = new drawingOR(); 		// drawingOR.java
		ANDGate = new drawingAND(); 	// drawingAND.java
		XORGate = new drawingXOR(); 	// drawingXOR.java
		NORGate = new drawingNOR(); 	// drawingNOR.java
		NANDGate = new drawingNAND(); 	// drawingNAND.java
		
		Color currentColour = new Color(0,0,0); // black line for drawing
		
		
		public canvas(int btnGate)
		{
			addMouseListener(this); 
			addMouseMotionListener( this);
			setVisible(true); // Better to set visible than to not
			
			gate = btnGate;
		}
		
    
		public void paint(Graphics gfx) 
		{	

			
			//gfx.setColor(ovalColour); // grey or yellow
			gfx.setColor(currentColour);
			if (gate == 1) // If user selected or gate...
			{ 
				ORGate.paint(gfx); // see the other class/file

			}
			else if (gate == 2) // If user selected and gate
			{
				ANDGate.paint(gfx);	

			} 
			else if (gate == 3) // If user selected XOR gate
			{
				XORGate.paint(gfx);	
		
			} 
			else if (gate == 4) // If user selected NOR gate
			{
				NORGate.paint(gfx);	

			}
			else if (gate == 5) // If user selected NOR gate
			{
				NANDGate.paint(gfx);	

			} 

		
	}
		
		
		public void update(Graphics gfx)
		{
			super.paint(gfx);
		}
				
		// Loads of mouse stuff, not all used, but needed
		public void mouseReleased(MouseEvent e)
		{

			repaint();
		}

		public void mouseDragged(MouseEvent e)
		{

			repaint();
		}

		public void mousePressed(MouseEvent e)
		{
			if (gate == 0)
			{
				{JOptionPane.showMessageDialog(null, "Please select a logic gate first");}
			}

			repaint();
		}
		
		// All of this needs to be enabled
		public void mouseClicked(MouseEvent e)	{}
		public void mouseEntered(MouseEvent e)	{}
		public void mouseExited(MouseEvent e)	{}
		public void mouseMoved(MouseEvent e)	{}
	}*/
