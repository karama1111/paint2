
package paint2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

abstract class PaintTool {
	protected Graphics2D g2;
	int WIDTH = MyCanvas.WIDTH;
	int HEIGHT = MyCanvas.HEIGHT;
	protected BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

	Color penColor = Color.black;
	
	PaintTool(BufferedImage image){
		g2 = image.createGraphics();
		this.image = image;
//		System.out.println("rgb" + image.getRGB(5,5));//test
	}
	
	abstract void dotPaint();
	abstract void linePaint();
	//public void change();//スタンプの種類を変えるとか？
	void changeColor(Color color){
		penColor = color;
		g2.setColor(penColor);
	}
	abstract void changeWidth(int penWidth);
	
	static int startX, startY, endX, endY;
	
	public void setX(int x){

		startX = endX;
		endX = x;
	}
	public void setY(int y){
		startY = endY;
		endY = y;
	}

	public void repaint() {
		MyCanvas myCanvas = Main.myCanvas;
		myCanvas.repaint();
	}
	
	public void setGraphics(Graphics2D g2){
		this.g2 = g2;
	}
}
