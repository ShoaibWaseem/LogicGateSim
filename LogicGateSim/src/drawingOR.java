import java.awt.*; 
import javax.swing.*;

public class drawingOR extends JFrame
{
	public void paint(Graphics OR, int translatex, int translatey)
	{
		OR.setColor(Color.black); //black line colour
		
		OR.drawArc(35 + translatex, 50+translatey, 25, 50, 270, 180); 		//input arc
		OR.drawArc(0+translatex, 50+translatey, 100, 50, 270, 180); 	//output arc
		OR.drawLine(57+translatex, 62+translatey, 38, 62 + translatex + translatey); 			//input A
		OR.drawLine(57+translatex, 88+translatey, 38 + translatex, 88 + translatey); 			//input B
		OR.drawLine(100+translatex, 75+translatey, 112 + translatex, 75 + translatey);			//output OR
	}
	
	public void paint(Graphics OR)
	{
		OR.setColor(Color.black); //black line colour
		
		OR.drawArc(35, 50, 25, 50, 270, 180); 		//input arc
		OR.drawArc(0, 50, 100, 50, 270, 180); 	//output arc
		OR.drawLine(57, 62, 38, 62); 			//input A
		OR.drawLine(57, 88, 38, 88); 			//input B
		OR.drawLine(100, 75, 112, 75);			//output OR
	}
	
}