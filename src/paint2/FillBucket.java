
package paint2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;


public class FillBucket extends PaintTool{

	int counter = 0;
//	int rgb;
	int baseColor;
//	boolean isFilling = false;
//	int WIDTH = MyCanvas.WIDTH;
//	int HEIGHT = MyCanvas.HEIGHT;
	
	LinkedList<Dimension> queue;
	
	public FillBucket(BufferedImage image) {
		super(image);
//		System.out.println("rgb" + image.getRGB(5,5));//test
//		myRepaint();//test
	}
	
/*	public void myRepaint() {
		g2.setColor(Color.red);
		g2.fillRect(0, 0, 800, 800);
		g2.setColor(Color.yellow);
		g2.drawOval(190, 190, 100, 100);
		g2.setColor(Color.blue);
		int x2 = 200;
		int y2 = 200;
		baseColor = image.getRGB(x2, y2);

		fill(x2, y2);
	}*/
	
	public void fill(int x, int y){
		
		System.out.println("testingrgb" + image.getRGB(5,5));//test
//		g2.setColor(penColor);
//		penColor = Color.blue;
//		g2.setColor(penColor);//後で色変えられるようにする
		
		baseColor = image.getRGB(x, y);
		Color c = new Color(baseColor);
		if(penColor.equals(c)){
			System.out.println("return");
			return;
		}
		System.out.println("baseColor = " + baseColor);
		System.out.println("c = " + c);
		int xr;
		int xl;
		queue.offer(new Dimension(x,y));
		while(queue.size()>0){
			
			x = queue.element().width;
			y = queue.element().height;
			queue.remove();
			
//			System.out.println(queue);
			xr = x;
			xl = x;
			while(image.getRGB(xr+1, y) == baseColor && xr < WIDTH-3){
				xr++;
			}
			while(image.getRGB(xl-1, y) == baseColor && xl > 1){
				xl--;
			}
			g2.drawLine(xl, y, xr, y);
			if(y<HEIGHT-3){
				scanLine(xl, xr, y+1);
			}
			if(y>2){
				scanLine(xl, xr, y-1);
			}
			counter++;
//			if(counter>100)break;
		}
	}
	
	public void scanLine(int xl, int xr, int y){

		
		int scanxl = xl;
		int scanxr = xr;
		while(scanxl<scanxr){
			if(image.getRGB(scanxl, y) == baseColor){
				if(image.getRGB(scanxl+1, y) != baseColor){
					queue.offer(new Dimension(scanxl, y));
				}
			}
			scanxl++;
		}
		if(image.getRGB(scanxl, y) == baseColor){
			queue.offer(new Dimension(scanxl, y));
		}
		
	}
	@Override
	void dotPaint() {
//		System.out.println("testingrgbdotpaint" + image.getRGB(5,5));//test
//		System.out.println("fill");
		queue = new LinkedList<Dimension>();
		fill(endX, endY);
//		System.out.println("endx" + endX + "endY" + endY);
		
	}
	@Override
	void linePaint() {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	@Override
	void changeWidth(int penWidth) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
	
}
