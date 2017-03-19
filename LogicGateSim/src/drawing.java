	/***************************************************************************************/
	/**************************************IMPORT MODULES***********************************/
	/***************************************************************************************/
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;


public class drawing extends JFrame implements ActionListener
{
	/***************************************************************************************/
	/**************************************DEFINITIONS**************************************/
	/***************************************************************************************/
	JPanel palette,tools;
    JButton orGate, andGate, xorGate, norGate, nandGate, confirmNumberOfGates, randomTest;
    

    MyCanvas drawarea;
    
    Color background = new Color(128,128,127);	// colour 'hack'
    
    drawingOR ORGate; 		// OR gate class
    drawingAND ANDGate; 	// AND gate class
    drawingXOR XORGate; 	// XOR gate class
    drawingNOR NORGate;		// NOR gate class
    drawingNAND NANDGate;	// NAND gate class
    JComboBox cbx_numberOfGates;
    
    //Canvas Size
    int canvasSizeX = 800;
    int canvasSizeY = 800;
    
    // Coordinate integers
    int gate; // 1=OR, 2=NAND, 3=XOR, 4=NOR, 5=NAND
    
    //coordinate translation for multiple gates
	int translationX;
	int translationY;
	
	int cbx_index = 0;
	int index = 0;
	
	Color currentColour = new Color(0,0,0); // black line for drawing
	
	//used for the combo box, 
	String[] numberOfGates  = { "Select No. of Gates","1","3","9" };
	
	int[] threeGatesList = new int[3];
	
	//co-ordinates used for 1 gate combo box selection - x, y
	int[] oneGateCoord = {300, 400};
	
	//rng for random gate
	Random randomGate = new Random();
    
	ArrayList<Gates> GatesList = new ArrayList<Gates>();
	Gates XOR = new Gates(1);
	Gates OR = new Gates(2);
	Gates NOR = new Gates(3);
	Gates AND = new Gates(4);
	Gates NAND = new Gates(5);
	
	

	ArrayList<Gates> threeGates = new ArrayList<Gates>();
	
	

    public drawing()
    {
	setSize(canvasSizeX,canvasSizeY); 
	palette = new JPanel(); // Watch where this gets placed to
	palette.setLayout(new GridLayout(2,3,5,5)); // grid layout
     
    setTitle("Logic Gates"); 
    
    
    GatesList.add(XOR);
    GatesList.add(OR);
    GatesList.add(NOR);
    GatesList.add(AND);
    GatesList.add(NAND);

    
    // Instance of classes
	ORGate = new drawingOR(); 			// drawingOR.java
	ANDGate = new drawingAND(); 		// drawingAND.java
	XORGate = new drawingXOR(); 		// drawingXOR.java
	NORGate = new drawingNOR(); 		// drawingNOR.java
	NANDGate = new drawingNAND(); 		// drawingNAND.java
	

	
	
	// Assign components with variables
	orGate = new JButton("OR Gate"); 
	andGate = new JButton("AND Gate");
	xorGate = new JButton("XOR Gate");
	norGate = new JButton("NOR Gate");
	nandGate = new JButton("NAND Gate");
	randomTest = new JButton("Random");
	confirmNumberOfGates = new JButton("Confirm");
	
	cbx_numberOfGates = new JComboBox(numberOfGates);
	
	palette.add(cbx_numberOfGates);
	cbx_numberOfGates.setSelectedIndex(0);
	cbx_numberOfGates.addActionListener(this);
	
	palette.add(confirmNumberOfGates);
	confirmNumberOfGates.addActionListener(this);
	
	palette.add(orGate);
	orGate.addActionListener(this);
	palette.add(andGate);
	andGate.addActionListener(this);
	palette.add(xorGate);
	xorGate.addActionListener(this);
	palette.add(norGate);
	norGate.addActionListener(this);
	palette.add(nandGate);
	nandGate.addActionListener(this);
	palette.add(randomTest);
	randomTest.addActionListener(this);
	getContentPane().add(palette,"North"); // Gate buttons on top

	
	
	// Drawing area
	drawarea = new MyCanvas(); // MyCanvas class
	getContentPane().add(drawarea,"Center"); // Draw stuff in the centre
	setVisible(true); // visible true for all elements
	} 
	       

    public void actionPerformed(ActionEvent ev)
    {
		if ("OR Gate".equals(ev.getActionCommand())) // if button with "OR Gate" text
		{ 
			gate = 1; // assign it a int value
			repaint(); // so gates won't overlap
		}
		else if ("AND Gate".equals(ev.getActionCommand()))
		{
			gate = 2;
			repaint();
		}
		else if ("XOR Gate".equals(ev.getActionCommand())){
			gate = 3;
			repaint();
		}
		else if ("NOR Gate".equals(ev.getActionCommand())){
			gate = 4;
			repaint();
		}
		else if ("NAND Gate".equals(ev.getActionCommand())){
			gate = 5;
			repaint();
		}
		else if ("NAND Gate".equals(ev.getActionCommand())){
			gate = 5;
			repaint();
		}
		else if ("Random".equals(ev.getActionCommand())){
			gate = randomGate.nextInt(5-1 + 1) + 1;
			repaint();
		}
		else if(("Confirm".equals(ev.getActionCommand()))){
			cbx_index = cbx_numberOfGates.getSelectedIndex();
			repaint();
			
			if(cbx_index == 0){
				{JOptionPane.showMessageDialog(null, "Please select a logic gate first");}
			}
			else if (cbx_index == 1){
				//1 gate goes here
			}
			else if (cbx_index == 2){
				//3 gates
				for(int i = 0; i < 3; i++){
					repaint();
				}
				
			}
			else if (cbx_index == 3){
				//7 gates
			}
							
					
			}
				
		}
				
			
			

			
		
		
    

		
    
    
	/***************************************************************************************/
	/**************************************MAIN CLASS***************************************/
	/***************************************************************************************/
    public static void main(String args[])
    {
		new drawing(); // Calling main prog
    }



	/***************************************************************************************/
	/**************************************CANVAS CLASS*************************************/
	/**************************************************************************************/
	class MyCanvas extends JPanel implements MouseListener,  MouseMotionListener
	{
		public MyCanvas()
		{
			addMouseListener(this); 
			addMouseMotionListener( this);

			setVisible(true); // Better to set visible than to not
		}
		
		
    
		public void updateGates(Graphics gfx) 
		{	

			index = randomGate.nextInt(GatesList.size());
			GatesList.get(randomGate.nextInt(GatesList.size())).getGate(gfx, translationX, translationY);
		}
			
			

		
		
		public void paint(Graphics gfx) 
		{	
		gfx.setColor(currentColour);
		translationX = 0;
		translationY = 0;
		
		if(cbx_index == 2) {
			
		
			for (int i = 0; i < 2; i++)
			{
				updateGates(gfx);
				translationY = translationY + 100;
			}
		
			translationX = translationX + 150;
			translationY = translationY - 150;
		
			}
		}
		/*public void paint(Graphics gfx) 
			{	
			gfx.setColor(currentColour);
			translationX = 0;
			translationY = 0;
			
			
			for (Gates g : GatesList) {
			
			if(cbx_index == 1) {	
			for (int i = 0; i < 2; i++){
								
				if (threeGatesList[i] == 1) // If user selected or gate...
				{ 
					GatesList.get(threeGatesList[i]).getGate(gfx, translationX, translationY);

					
				}
				else if (threeGatesList[i] == 2) // If user selected and gate
				{
					GatesList.get(threeGatesList[i]).getGate(gfx, translationX, translationY);


				} 
				else if (threeGatesList[i] == 3) // If user selected XOR gate
				{
					GatesList.get(threeGatesList[i]).getGate(gfx, translationX, translationY);
					
			
				} 
				else if (threeGatesList[i] == 4) // If user selected NOR gate
				{
					GatesList.get(threeGatesList[i]).getGate(gfx, translationX, translationY);
					

				}
				else if (threeGatesList[i] == 5) // If user selected NAND gate
				{
					GatesList.get(threeGatesList[i]).getGate(gfx, translationX, translationY);
					

				} 
				
				translationY = translationY + 100;
							
			}
			translationX = translationX + 150;
			translationY = translationY - 150;
			
			if (threeGatesList[2] == 1) // If user selected or gate...
			{ 
				GatesList.get(threeGatesList[2]).getGate(gfx, translationX, translationY);
				
			}
			else if (threeGatesList[2] == 2) // If user selected and gate
			{
				GatesList.get(threeGatesList[2]).getGate(gfx, translationX, translationY);
				

			} 
			else if (threeGatesList[2] == 3) // If user selected XOR gate
			{
				GatesList.get(threeGatesList[2]).getGate(gfx, translationX, translationY);
				
		
			} 
			else if (threeGatesList[2] == 4) // If user selected NOR gate
			{
				GatesList.get(threeGatesList[2]).getGate(gfx, translationX, translationY);
				

			}
			else if (threeGatesList[2] == 5) // If user selected NAND gate
			{
				GatesList.get(threeGatesList[2]).getGate(gfx, translationX, translationY);
				

			} 
			
			}
			
			
			}
		
	}
		*/
		
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
	}
}
	
