
package paint2;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

//�����@�X�^���v�p�摜
//����炫���}�L�A�[�g�̃t���[�C���X�g http://ameblo.jp/petari/theme4-10006835505.html#main

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
//	BufferedImage loadImage(String name){//�R�s�y
//	    try{
//	      FileInputStream in=new FileInputStream(name);//FileInputStream�����
//	      BufferedImage rv=ImageIO.read(in);//�C���[�W����荞��
//	      in.close();//����
//	      imgWidth = rv.getWidth();
//	      imgHeight = rv.getHeight();
//	      stampWidth = imgWidth/5;
//	      stampHeight = imgHeight/5;
//	      
//	      return rv;//�߂�l�ɓǂݍ��񂾃C���[�W���Z�b�g
//	      
//	    }catch(IOException e){
//	      //�G���[���̏����i�G���[��\���j��null��Ԃ�
//	      System.out.println("Err e="+e);//�G���[��\��
//	      return null;//null�@��Ԃ�
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

