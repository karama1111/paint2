
package paint2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Eraser extends PaintTool{

	int penWidth = 25;
	Eraser(BufferedImage image) {
		super(image);
		BasicStroke wideStroke = new BasicStroke(penWidth);
		g2.setColor(Color.white);
		g2.setStroke(wideStroke);
	}


	@Override
	public void changeColor(Color color) {
		
	}

	@Override
	public void changeWidth(int penWidth) {
		
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
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(penWidth));
		
	}
}