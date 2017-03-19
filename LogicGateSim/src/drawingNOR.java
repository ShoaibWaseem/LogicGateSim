import java.awt.*; 
import javax.swing.*;

public class drawingNOR extends JFrame
{
	public void paint(Graphics NOR, int translatex, int translatey)
	{
		NOR.setColor(Color.black); //black line colour
	
		NOR.drawArc(85 + translatex, 100 + translatey, 25, 50, 270, 180); 		//input arc
		NOR.drawArc(50 + translatex, 100 + translatey, 100, 50, 270, 180); 	//output arc
		NOR.drawLine(107 + translatex, 112 + translatey, 88 + translatex, 112 + translatey); 			//input A
		NOR.drawLine(107 + translatex, 138 + translatey, 88 + translatex, 138 + translatey); 			//input B
		NOR.drawLine(156 + translatex, 125 + translatey, 162 + translatex, 125 + translatey);			//output OR
		
		NOR.drawOval(150 + translatex, 122 + translatey, 6, 6);					//NOT Output
		

	}
	
	public void paint(Graphics NOR)
	{
		NOR.setColor(Color.black); //black line colour
	
		NOR.drawArc(85, 100, 25, 50, 270, 180); 		//input arc
		NOR.drawArc(50, 100, 100, 50, 270, 180); 	//output arc
		NOR.drawLine(107, 112, 88, 112); 			//input A
		NOR.drawLine(107, 138, 88, 138); 			//input B
		NOR.drawLine(156, 125, 162, 125);			//output OR
		
		NOR.drawOval(150, 122, 6, 6);					//NOT Output
		

	}
	
}