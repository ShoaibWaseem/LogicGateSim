/*
 * Title:			Logic Gate Simulator
 * Author: 			Shoaib Waseem
 * Student Code:	w13013878
 * University:		Northumbria University
 * Version:			Version 2
 * Date:			20th March 2017 
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
    
    Color background = new Color(128,128,127);
    
    
    
    //Drawing Classes through buttons
    
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
	String[] numberOfGates  = { "Select No. of Gates","1","3","7" };
	
	int[] threeGatesList = new int[3];
	
	//co-ordinates used for 1 gate combo box selection - x, y
	int[] oneGateCoord = {300, 400};
	
	//rng for random gate
	Random randomGate = new Random();
    
	//List of printable Gates
	ArrayList<Gates> GatesList = new ArrayList<Gates>();
	Gates XOR = new Gates(1);
	Gates OR = new Gates(2);
	Gates NOR = new Gates(3);
	Gates AND = new Gates(4);
	Gates NAND = new Gates(5);
	
	//List of Printed Gates
	ArrayList<String> printedGates = new ArrayList<String>();
	

    public drawing()
    {
	setSize(canvasSizeX,canvasSizeY); 
	palette = new JPanel(); // Watch where this gets placed to
	palette.setLayout(new GridLayout(2,3,5,5)); // grid layout
     
    setTitle("Logic Gates"); 
    
    //add gates to arraylist GatesList
    if(GatesList.isEmpty()){
        GatesList.add(XOR);
        GatesList.add(OR);
        GatesList.add(NOR);
        GatesList.add(AND);
        GatesList.add(NAND);
    }


    
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
	
	//used to determine how complex the logic gate system will be
	cbx_numberOfGates = new JComboBox(numberOfGates);
	palette.add(cbx_numberOfGates);
	cbx_numberOfGates.setSelectedIndex(0);
	cbx_numberOfGates.addActionListener(this);
	
	// Assign button to confirm number of logic gates
	palette.add(confirmNumberOfGates);
	confirmNumberOfGates.addActionListener(this);
	
	// Assign button to draw a single logic gate - for testing
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
	
	// Gate buttons on top
	getContentPane().add(palette,"North"); 

	// Draw area
	drawarea = new MyCanvas(); // MyCanvas class
	getContentPane().add(drawarea,"Center"); // Draw stuff in the centre
	setVisible(true); // visible true for all elements
	} 
	       

    public void actionPerformed(ActionEvent ev)
    {
    	
		if(("Confirm".equals(ev.getActionCommand())))
		{
			cbx_index = cbx_numberOfGates.getSelectedIndex();

						
			//Random Gate Generation	
			if(cbx_index != 0){
				//if gate number is chosen
				printedGates.clear();
				repaint();
			
			} else {
				{JOptionPane.showMessageDialog(null, "Please select a logic gate first");}
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
			//addMouseMotionListener( this);

			setVisible(true); // Better to set visible than to not
		}
		
		
    
		public void updateGates(Graphics gfx) 
		{	

			index = randomGate.nextInt((GatesList.size() + 1) - 1);
			System.out.println(index);
			GatesList.get(index).getGate(gfx, translationX, translationY);
			addPrintedGate();
		}
		
		public void addPrintedGate() {
			if(index == 0) 		{	printedGates.add("XOR");	}
			else if(index == 1)	{	printedGates.add("OR");		}
			else if(index == 2)	{	printedGates.add("NOR");	}
			else if(index == 3)	{	printedGates.add("AND");	}
			else if(index == 4)	{	printedGates.add("NAND");	}
		}
		
		public void updateSingleGate(Graphics gfx) {
			translationX = translationX + 270;
			translationY = translationY + 75;
			updateGates(gfx);
		}
			
		
		public void updateFinalGate(Graphics gfx){
			
			translationX = translationX + 150;
			translationY = translationY - 150;
			updateGates(gfx);
		}
		
		public void updateTwoGates(Graphics gfx) {	
			
			for (int i = 0; i < 2; i++)
			{
				updateGates(gfx);
				translationY = translationY + 100;
			}
		}
		
		public void updateFourGates(Graphics gfx){
			
			for (int i = 0; i < 4; i++)
			{
				updateGates(gfx);
				translationY = translationY + 100;
			}
		
			translationX = translationX + 150;
			translationY = translationY - (100 * 3);
		}
		
		//for testing array list printedGates
		public void getAll(){
			for(String printed : printedGates) {
				System.out.println(printed);
			}
		}

		
		
		public void paint(Graphics gfx) 
		{	
		gfx.setColor(currentColour);
		translationX = 0;
		translationY = 0;
		printedGates.clear();
		
		if(cbx_index == 1){
			
			updateSingleGate(gfx);
		}
		
		else if(cbx_index == 2) {
			
			updateTwoGates(gfx);
			updateFinalGate(gfx);
				
			}
		
		else if(cbx_index == 3) {
			
			updateFourGates(gfx);
			updateTwoGates(gfx);
			updateFinalGate(gfx);
			
			}
		getAll();
		}
		
		
		public void update(Graphics gfx)
		{
			super.paint(gfx);
		}
				
		// All of this needs to be enabled
		public void mouseReleased(MouseEvent e)	{}
		public void mouseDragged(MouseEvent e)	{}
		public void mousePressed(MouseEvent e)	{}
		public void mouseClicked(MouseEvent e)	{}
		public void mouseEntered(MouseEvent e)	{}
		public void mouseExited(MouseEvent e)	{}
		public void mouseMoved(MouseEvent e)	{}
	}
}
	
