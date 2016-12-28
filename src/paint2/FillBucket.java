
package paint2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;


public class FillBucket extends PaintTool{

	int counter = 0;
	int baseColor;

	LinkedList<Dimension> queue;

	public FillBucket(BufferedImage image) {
		super(image);
	}

	public void fill(int x, int y){

		System.out.println("testingrgb" + image.getRGB(5,5));//test

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
		queue = new LinkedList<Dimension>();
		fill(endX, endY);
	}
	@Override
	void linePaint() {

	}
	@Override
	void changeWidth(int penWidth) {

	}


}
