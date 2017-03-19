import java.awt.*; 
import javax.swing.*;

public class drawingAND extends JFrame
{
		
	public void paint(Graphics AND, int translatex, int translatey)
	{
		AND.setColor(Color.black); //black line colour
		
		
		AND.drawLine(100 + translatex, 100 + translatey, 100 + translatex, 150 + translatey); 			//vertical input line
		AND.drawLine(100 + translatex, 100 + translatey, 125 + translatex, 100 + translatey); 			//upper pre-arc line
		AND.drawLine(100 + translatex, 150 + translatey, 125 + translatex, 150 + translatey); 			//lower pre-arc line
		AND.drawArc(100 + translatex, 100, 50, 50, 270, 180); 	//arc
		AND.drawLine(100 + translatex, 112 + translatey, 88 + translatex, 112 + translatey); 			//input A
		AND.drawLine(100 + translatex, 138 + translatey, 88 + translatex, 138 + translatey); 			//input B
		AND.drawLine(150 + translatex, 125 + translatey, 162 + translatex, 125 + translatey);			//output AND
		
	
	}
	
	public void paint(Graphics AND)
	{
		AND.setColor(Color.black); //black line colour
		
		
		AND.drawLine(100, 100, 100, 150); 			//vertical input line
		AND.drawLine(100, 100, 125, 100); 			//upper pre-arc line
		AND.drawLine(100, 150, 125, 150); 			//lower pre-arc line
		AND.drawArc(100, 100, 50, 50, 270, 180); 	//arc
		AND.drawLine(100, 112, 88, 112); 			//input A
		AND.drawLine(100, 138, 88, 138); 			//input B
		AND.drawLine(150, 125, 162, 125);			//output AND
		
	
	}
	
}