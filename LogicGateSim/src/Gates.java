import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;


/**
* Class Gates takes the randomly generated number between 1-5 to 
* select a gate from drawing.java when the user clicks the confirm button
* 
* Using awt.Graphics, the methods take x and y coordinates to position their
* lines to draw the logic gates.
* 
*
*/
@SuppressWarnings("serial")
public class Gates extends JFrame {
	//takes the randomly selected gates ID, applied in getGate
	int gateID;
	
	public Gates(int id)
	{
		gateID = id;
	}
	/**
	 * select the appropriate gate based on the gateID. 
	 * 
	 * @param	Gate		Graphics to draw - could be better named (future work)
	 * @param	translatex	Move gate with the x axis based on final gate or not
	 * @param	translatey	Move gate with the y axis for the 2nd gate
	 * 
	 */
	public void getGate(Graphics Gate ,int translatex, int translatey) {
		if(gateID == 1) {
			paintXOR(Gate ,translatex, translatey);
		}
		else if(gateID == 2){
			paintOR(Gate ,translatex, translatey);
		}
		else if(gateID == 3){
			paintNOR(Gate ,translatex, translatey);
		}
		else if(gateID == 4){
			paintAND(Gate ,translatex, translatey);
		}
		else if(gateID == 5){
			paintNAND(Gate ,translatex, translatey);
		}
	}
	
	/**
	 * Draws XOR on the Frame
	 * 
	 * @param	XOR		Graphics to draw - could be better named (future work)
	 * @param	translatex	Move gate with the x axis based on final gate or not
	 * @param	translatey	Move gate with the y axis for the 2nd gate
	 * 
	 */
		public void paintXOR(Graphics XOR, int translatex, int translatey)
		{
		XOR.setColor(Color.black); //black line colour
	
		XOR.drawArc(85 + translatex, 100 + translatey, 25, 50, 270, 180); 								//input arc
		XOR.drawArc(95 + translatex, 100 + translatey, 25, 50, 270, 180);								//secondary arc
		XOR.drawArc(60 + translatex, 100 + translatey, 90, 50, 270, 180); 								//output arc
		XOR.drawLine(107 + translatex, 112 + translatey, 88 + translatex, 112 + translatey); 			//input A
		XOR.drawLine(107 + translatex, 138 + translatey, 88 + translatex, 138 + translatey); 			//input B
		XOR.drawLine(150 + translatex, 125 + translatey, 162 + translatex, 125 + translatey);			//output XOR
		
		
		}
		/**
		 * Draws NOR on the Frame
		 * 
		 * @param	NOR		Graphics to draw - could be better named (future work)
		 * @param	translatex	Move gate with the x axis based on final gate or not
		 * @param	translatey	Move gate with the y axis for the 2nd gate
		 * 
		 */
		public void paintNOR(Graphics NOR, int translatex, int translatey)
		{
			NOR.setColor(Color.black); //black line colour
		
			NOR.drawArc(85 + translatex, 100 + translatey, 25, 50, 270, 180); 							//input arc
			NOR.drawArc(50 + translatex, 100 + translatey, 100, 50, 270, 180); 							//output arc
			NOR.drawLine(107 + translatex, 112 + translatey, 88 + translatex, 112 + translatey); 		//input A
			NOR.drawLine(107 + translatex, 138 + translatey, 88 + translatex, 138 + translatey); 		//input B
			NOR.drawLine(156 + translatex, 125 + translatey, 162 + translatex, 125 + translatey);		//output NOR
			
			NOR.drawOval(150 + translatex, 122 + translatey, 6, 6);										//NOT Output
			

		}
		/**
		 * Draws NAND on the Frame
		 * 
		 * @param	NAND		Graphics to draw - could be better named (future work)
		 * @param	translatex	Move gate with the x axis based on final gate or not
		 * @param	translatey	Move gate with the y axis for the 2nd gate
		 * 
		 */
		public void paintNAND(Graphics NAND, int translatex, int translatey)
		{
			NAND.setColor(Color.black); //black line colour
		
			NAND.drawLine(100 + translatex, 100 + translatey, 100 + translatex, 150 + translatey); 			//vertical input line
			NAND.drawLine(100 + translatex, 100 + translatey, 125 + translatex, 100 + translatey); 			//upper pre-arc line
			NAND.drawLine(100 + translatex, 150 + translatey, 125 + translatex, 150 + translatey); 			//lower pre-arc line
			NAND.drawArc(100 + translatex, 100 + translatey, 50, 50, 270, 180); 							//arc
			NAND.drawLine(100 + translatex, 112 + translatey, 88 + translatex, 112 + translatey); 			//input A
			NAND.drawLine(100 + translatex, 138 + translatey, 88 + translatex, 138 + translatey); 			//input B
			
			
			
			NAND.drawLine(156 + translatex, 125 + translatey, 162 + translatex, 125 + translatey);				//output 
			NAND.drawOval(150 + translatex, 122 + translatey, 6, 6);					//NOT Output
			

		}
		/**
		 * Draws AND on the Frame
		 * 
		 * @param	AND		Graphics to draw - could be better named (future work)
		 * @param	translatex	Move gate with the x axis based on final gate or not
		 * @param	translatey	Move gate with the y axis for the 2nd gate
		 * 
		 */
		public void paintAND(Graphics AND, int translatex, int translatey)
		{
			AND.setColor(Color.black); //black line colour
			
			AND.drawLine(100 + translatex, 100 + translatey, 100 + translatex, 150 + translatey); 			//vertical input line
			AND.drawLine(100 + translatex, 100 + translatey, 125 + translatex, 100 + translatey); 			//upper pre-arc line
			AND.drawLine(100 + translatex, 150 + translatey, 125 + translatex, 150 + translatey); 			//lower pre-arc line
			AND.drawArc(100 + translatex, 100 + translatey, 50, 50, 270, 180); 	//arc
			AND.drawLine(100 + translatex, 112 + translatey, 88 + translatex, 112 + translatey); 			//input A
			AND.drawLine(100 + translatex, 138 + translatey, 88 + translatex, 138 + translatey); 			//input B
			AND.drawLine(150 + translatex, 125 + translatey, 162 + translatex, 125 + translatey);			//output AND
			
		
		}
		/**
		 * Draws OR on the Frame
		 * 
		 * @param	OR		Graphics to draw - could be better named (future work)
		 * @param	translatex	Move gate with the x axis based on final gate or not
		 * @param	translatey	Move gate with the y axis for the 2nd gate
		 * 
		 */
		public void paintOR(Graphics OR, int translatex, int translatey)
		{
			OR.setColor(Color.black); //black line colour
			
			OR.drawArc(85 + translatex, 100+translatey, 25, 50, 270, 180); 		//input arc
			OR.drawArc(50+translatex, 100+translatey, 100, 50, 270, 180); 	//output arc
			OR.drawLine(107 + translatex, 112+translatey, 88 + translatex, 112 + translatey); 			//input A
			OR.drawLine(107 + translatex, 138+translatey, 88 + translatex, 138 + translatey); 			//input B
			OR.drawLine(150 +translatex, 125+translatey, 162 + translatex, 125 + translatey);			//output OR
		}
}
