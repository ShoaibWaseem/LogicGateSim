import java.awt.*; 
import javax.swing.*;

public class drawingXOR extends JFrame
{
	public void paint(Graphics XOR)
	{
		XOR.setColor(Color.black); //black line colour
	
		XOR.drawArc(85, 100, 25, 50, 270, 180); 	//input arc
		XOR.drawArc(95, 100, 25, 50, 270, 180);		//secondary arc
		XOR.drawArc(60, 100, 90, 50, 270, 180); 	//output arc
		XOR.drawLine(107, 112, 88, 112); 			//input A
		XOR.drawLine(107, 138, 88, 138); 			//input B
		XOR.drawLine(150, 125, 162, 125);			//output OR
		
		

	}
	
	
	public void paint(Graphics XOR, int translatex, int translatey)
	{
		XOR.setColor(Color.black); //black line colour
	
		XOR.drawArc(85 + translatex, 100 + translatey, 25, 50, 270, 180); 	//input arc
		XOR.drawArc(95 + translatex, 100 + translatey, 25, 50, 270, 180);		//secondary arc
		XOR.drawArc(60 + translatex, 100 + translatey, 90, 50, 270, 180); 	//output arc
		XOR.drawLine(107 + translatex, 112 + translatey, 88 + translatex, 112 + translatey); 			//input A
		XOR.drawLine(107 + translatex, 138 + translatey, 88 + translatex, 138 + translatey); 			//input B
		XOR.drawLine(150 + translatex, 125 + translatey, 162 + translatex, 125 + translatey);			//output OR
		
		

	}
	
}