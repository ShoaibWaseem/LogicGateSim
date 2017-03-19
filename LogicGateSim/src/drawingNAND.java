import java.awt.*; 
import javax.swing.*;

public class drawingNAND extends JFrame
{
	public void paint(Graphics NAND, int translatex, int translatey)
	{
		NAND.setColor(Color.black); //black line colour
	
		NAND.drawLine(100, 100, 100, 150); 			//vertical input line
		NAND.drawLine(100, 100, 125, 100); 			//upper pre-arc line
		NAND.drawLine(100, 150, 125, 150); 			//lower pre-arc line
		NAND.drawArc(100, 100, 50, 50, 270, 180); 	//arc
		NAND.drawLine(100, 112, 88, 112); 			//input A
		NAND.drawLine(100, 138, 88, 138); 			//input B
		
		
		
		NAND.drawLine(156, 125, 162, 125);				//output 
		NAND.drawOval(150, 122, 6, 6);					//NOT Output
		

	}
	
	public void paint(Graphics NAND)
	{
		NAND.setColor(Color.black); //black line colour
	
		NAND.drawLine(100, 100, 100, 150); 			//vertical input line
		NAND.drawLine(100, 100, 125, 100); 			//upper pre-arc line
		NAND.drawLine(100, 150, 125, 150); 			//lower pre-arc line
		NAND.drawArc(100, 100, 50, 50, 270, 180); 	//arc
		NAND.drawLine(100, 112, 88, 112); 			//input A
		NAND.drawLine(100, 138, 88, 138); 			//input B
		
		
		
		NAND.drawLine(156, 125, 162, 125);				//output 
		NAND.drawOval(150, 122, 6, 6);					//NOT Output
		

	}
	
}