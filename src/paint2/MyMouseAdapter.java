
package paint2;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseAdapter extends MouseAdapter{
	boolean isg2Copy = false;

	public void mousePressed(MouseEvent e){
		PaintTool tool = Main.myCanvas.tool;
		System.out.println(e.getX() + "," + e.getY());
		tool.setX(e.getX());
		tool.setY(e.getY());
		tool.dotPaint();
		tool.repaint();
	}

	public void mouseReleased(MouseEvent e){
		isg2Copy = true;
	}

	public void mouseDragged(MouseEvent e){
		PaintTool tool = Main.myCanvas.tool;
		tool.setX(e.getX());
		tool.setY(e.getY());
		tool.linePaint();
		tool.repaint();
	}

	public boolean getIsg2Copy(){
		if(isg2Copy){
			isg2Copy = false;
			return true;
		}else{
			return isg2Copy;
		}
	}
}


