

package paint2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Pen extends PaintTool{

	
	private int penWidth = 3;//‚Æ‚è‚ ‚¦‚¸
//	Color penColor = Color.black;
	
	Pen(BufferedImage image) {
		super(image);
		BasicStroke wideStroke = new BasicStroke(penWidth);
		g2.setColor(penColor);
		g2.setStroke(wideStroke);
	}


//	@Override
//	public void changeColor(Color color) {
//		penColor = color;
//		g2.setColor(penColor);
//	}

	@Override
	public void changeWidth(int penWidth) {
		this.penWidth = penWidth;
		BasicStroke wideStroke = new BasicStroke(penWidth);
		g2.setStroke(wideStroke);
	}


	@Override
	void dotPaint() {
		g2.fillOval(endX-penWidth/2, endY-penWidth/2, penWidth, penWidth);
	}


	@Override
	void linePaint() {
		g2.drawLine(startX, startY, endX, endY);
	}
	
	public void setGraphics(Graphics2D g2){
		super.setGraphics(g2);
		BasicStroke wideStroke = new BasicStroke(penWidth);
		g2.setColor(penColor);
		g2.setStroke(wideStroke);
		
	}

}
