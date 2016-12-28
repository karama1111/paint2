
package paint2;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

//スタンプ用画像
//http://ameblo.jp/petari/theme4-10006835505.html#main

public class Stamp extends PaintTool{

	int imgWidth;
	int imgHeight;
	BufferedImage bimg;
	int stampWidth = 120;
	int stampHeight;
	Image img;

	Stamp(BufferedImage image) {
		super(image);
		System.out.println("stamp");
	}

	@Override
	void dotPaint() {
		pressStamp(endX-stampWidth/2, endY-stampHeight/2);
	}

	@Override
	void linePaint() {}

	@Override
	void changeWidth(int penWidth) {}

	void pressStamp(int x, int y) {
		if (img != null){
		      g2.drawImage(img, x, y, stampWidth, stampHeight,  null);
		}
	}

	public void setStamp(ImageIcon icon){
		img = icon.getImage();
		imgWidth = img.getWidth(null);
	    imgHeight = img.getHeight(null);
	    stampHeight = stampWidth * imgHeight/imgWidth;
	}
}

