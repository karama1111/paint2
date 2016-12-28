
package paint2;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

//メモ　スタンプ用画像
//きゃらきゃらマキアートのフリーイラスト http://ameblo.jp/petari/theme4-10006835505.html#main

public class Stamp extends PaintTool{

	int imgWidth;
	int imgHeight;
	BufferedImage bimg;
	int stampWidth = 120;
	int stampHeight;
	Image img;
	
	Stamp(BufferedImage image) {
		super(image);
		//bimg = loadImage(".\\stamps\\colorDog.png");
		System.out.println("stamp");
	}

	@Override
	void dotPaint() {
//		pressStamp(endX-imgWidth/2, endY-imgHeight/2);
		pressStamp(endX-stampWidth/2, endY-stampHeight/2);
	}

	@Override
	void linePaint() {}

	@Override
	void changeWidth(int penWidth) {}

	void pressStamp(int x, int y) {
		if (img != null){
//		      g2.drawImage(img, null, x, y);
		      g2.drawImage(img, x, y, stampWidth, stampHeight,  null);
		}
	}
	
//	
//	BufferedImage loadImage(String name){//コピペ
//	    try{
//	      FileInputStream in=new FileInputStream(name);//FileInputStreamを作る
//	      BufferedImage rv=ImageIO.read(in);//イメージを取り込む
//	      in.close();//閉じる
//	      imgWidth = rv.getWidth();
//	      imgHeight = rv.getHeight();
//	      stampWidth = imgWidth/5;
//	      stampHeight = imgHeight/5;
//	      
//	      return rv;//戻り値に読み込んだイメージをセット
//	      
//	    }catch(IOException e){
//	      //エラー時の処理（エラーを表示）しnullを返す
//	      System.out.println("Err e="+e);//エラーを表示
//	      return null;//null　を返す
//	    }
//	  }
	public void setStamp(ImageIcon icon){
		img = icon.getImage();
		
		//BufferedImage bimg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		//Graphics tempg = bimg.getGraphics();

		//test
		 //g2.drawImage(img, null, null);
		
		
		//tempg.drawImage(img, 0, 0, null);
//		tempg.dispose();
		
		imgWidth = img.getWidth(null);
	    imgHeight = img.getHeight(null);
	    stampHeight = stampWidth * imgHeight/imgWidth;
	}
}

