
package paint2;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.ImageIcon;


//TODO
//jarファイル作ると画像読み込めない　下記参照
//http://nowloading.blog.jp/archives/22904384.htm

public class MyCanvas extends Canvas implements ChangeListener, ActionListener{
	static final int WIDTH = 800;
	static final int HEIGHT = 600;
	int startX = 0;
	int startY = 0;
	int endX = 0;
	int endY = 0;
	Color penColor;
	private BufferedImage img;
	Graphics2D bufferedg2;
	MyMouseAdapter myMouseAdapter;
	JColorChooser colorChooser;
	JButton thinButton;
	JButton mediumButton;
	JButton thickButton;
	JButton eraserButton;
	JButton initButton;
	JButton saveButton;
	JButton undoButton;
	JButton redoButton;
	JButton fillButton;
	JButton stampButton;
	
	BasicStroke wideStroke;
	boolean isEraser = false;
	boolean initFlag = false;
	boolean isUndo = false;
	boolean isRedo = false;
	JLabel colorLabel;
	ArrayList<BufferedImage> undoStack = new ArrayList<BufferedImage>();
	ArrayList<BufferedImage> redoStack = new ArrayList<BufferedImage>();
	BufferedImage lastImage;
	
	PaintTool tool;
	Pen pen;
	Eraser eraser;
	
	JFrame stampFrame;
	JPanel stampPanel;
	JScrollPane scrollStampPane;
	int stampFrameWidth;
    int stampFrameHeight;
    ArrayList<JButton> stampButtons = new ArrayList<JButton>();
	ArrayList<ImageIcon> stamps = new ArrayList<ImageIcon>();
	
    String[] fileNames;
	
	public MyCanvas(){
		JFrame frame = new JFrame();
		frame.setTitle("ペイントソフト");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();

		//上部パネル
		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new FlowLayout());
		frame.getContentPane().add(upperPanel, BorderLayout.NORTH);
		
		makeColorChooser(upperPanel);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		makeButtons(upperPanel);
		
		setBackground(Color.white);
		repaint();
		panel.add(this);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		frame.pack();
		frame.setVisible(true);
		
		//マウスアダプタ
		myMouseAdapter = new MyMouseAdapter();
		addMouseListener(myMouseAdapter);
		addMouseMotionListener(myMouseAdapter);
		
		
		
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
		bufferedg2 = img.createGraphics();
		bufferedg2.setBackground(Color.white);
		bufferedg2.clearRect(0, 0, WIDTH, HEIGHT);
		undoStack.add(copyImage(img));
		
		penColor = colorChooser.getColor();
	    colorLabel.setForeground(penColor);
	    System.out.println(penColor);
	    
	    new Timer(200, taskPerformer).start();//画面サイズ変更などの際に画面が真っ白にならないように
	    
	    tool = new Pen(img);
	    
	    //スタンプ用サブフレーム
	    //スタンプアイコンのボタンを載せる
	    stampFrame = new JFrame("スタンプ");
	    stampFrameWidth = 320;
	    stampFrameHeight = 600;
	    stampFrame.setSize(stampFrameWidth, stampFrameHeight);
	    stampFrame.setLocation(WIDTH + 200, 100);
	    stampFrame.addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) { stampFrame.setVisible(false); };
	    });
	    stampPanel = new JPanel();
	    stampPanel.setLayout(new GridLayout(0, 2, 3, 3));
	    scrollStampPane = new JScrollPane(stampPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    stampFrame.add(scrollStampPane);

	    
	   //test
	    readStamps();
	    
 	}
	
	private void makeButtons(JPanel upperPanel){
		JPanel buttonsPanel = new JPanel();
		
		ImageIcon thinPenIcon = new ImageIcon(".\\icons\\thinPen.png");
		ImageIcon mediumPenIcon = new ImageIcon(".\\icons\\mediumPen.png");
		ImageIcon thickPenIcon = new ImageIcon(".\\icons\\thickPen.png");
		thinButton = new JButton(thinPenIcon);
		mediumButton = new JButton(mediumPenIcon);
		thickButton = new JButton(thickPenIcon);
		eraserButton = new JButton("けしごむ");
		initButton = new JButton("ぜんぶ消す");
		saveButton = new JButton("保存する");
		undoButton = new JButton("←");
		redoButton = new JButton("→");
		fillButton = new JButton("塗りつぶし");
		stampButton = new JButton("スタンプ");
		buttonsPanel.setLayout(new GridLayout(2, 3, 5, 10));//あとでレイアウト整える、ボタンも絵に差し替え
		buttonsPanel.add(thinButton);
		buttonsPanel.add(mediumButton);
		buttonsPanel.add(thickButton);
		buttonsPanel.add(eraserButton);
		buttonsPanel.add(initButton);
		
		buttonsPanel.add(undoButton);
		buttonsPanel.add(redoButton);
		buttonsPanel.add(fillButton);
		buttonsPanel.add(stampButton);
		buttonsPanel.add(saveButton);
		upperPanel.add(buttonsPanel);
		thinButton.addActionListener(this);
		mediumButton.addActionListener(this);
		thickButton.addActionListener(this);
		eraserButton.addActionListener(this);
		initButton.addActionListener(this);
		saveButton.addActionListener(this);
		undoButton.addActionListener(this);
		redoButton.addActionListener(this);
		fillButton.addActionListener(this);
		stampButton.addActionListener(this);
		
		saveButton.setVisible(false);
		
		// ここからテスト
		/*
		thinPenIcon=null;
		mediumPenIcon = null; 
		thickPenIcon = null;
		Image im = null;
		URL url=this.getClass().getResource("thinPen.png");
		
		System.out.println("url is " + url);
		try {
			im=this.createImage((ImageProducer) url.getContent());
			if (im!=null) thinPenIcon=new ImageIcon(im);
		}catch(Exception ex){
			System.out.println("Resource Error");
		}
		//mediumButton.setText("" + url);
		 
		 */
		//　ここまでテスト
		
	}
	
	private void makeColorChooser(JPanel upperPanel){
		//カラーチューザー
		colorChooser = new JColorChooser(Color.black);
		colorChooser.getSelectionModel().addChangeListener(this);
		JPanel colorPreviewPanel = new JPanel();
		colorLabel = new JLabel("この色が選択されています");
		colorPreviewPanel.add(colorLabel);
		colorChooser.setPreviewPanel(colorPreviewPanel);
		AbstractColorChooserPanel[] panels = colorChooser.getChooserPanels();
		AbstractColorChooserPanel[] newaccp = new AbstractColorChooserPanel[1];
		for(AbstractColorChooserPanel accp : panels){
			if(accp.getDisplayName().equals("サンプル(S)")){
				newaccp[0] = accp;
				colorChooser.setChooserPanels(newaccp);
			}
		}
		upperPanel.add(colorChooser);
	}
	
	ActionListener taskPerformer = new ActionListener() {
	      public void actionPerformed(ActionEvent evt) {
	          repaint();
	      }
	};
	
	public void stateChanged(ChangeEvent e) {
	    	colorLabel.setText("この色が選択されています");
	    	System.out.println(tool);
	    if(tool.getClass().getSimpleName().equals("Eraser")){
	    	tool = changeTool("Pen");
	    }
	    penColor = colorChooser.getColor();
	    tool.changeColor(penColor);
	    colorLabel.setForeground(penColor);
	    System.out.println(penColor);
	  }
	
	
	public void update(Graphics g){
		if(isUndo){
			img = copyImage(lastImage);
			bufferedg2 = img.createGraphics();
			isUndo = false;
			tool.setGraphics(bufferedg2);
		}
		if(isRedo){
			img = copyImage(lastImage);
			bufferedg2 = img.createGraphics();
			isRedo = false;
			tool.setGraphics(bufferedg2);
		}
		if(myMouseAdapter.getIsg2Copy()){
			undoStack.add(copyImage(img));
			
		}
		if(initFlag){
			bufferedg2.setBackground(Color.white);
			bufferedg2.clearRect(0, 0, WIDTH, HEIGHT);
			initFlag = false;
			undoStack.add(copyImage(img));
		}
		
		g.drawImage(img, 0, 0, this);
	}
	
	private BufferedImage copyImage(BufferedImage img) {
	    BufferedImage copyOfImage = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = copyOfImage.createGraphics();
	    g2.drawImage(img, 0, 0, getWidth(), getHeight(), null);
	    return copyOfImage;
	}
	
	public void actionPerformed(ActionEvent e){
		
		  if(e.getSource() == thinButton){
			  tool = changeTool("Pen");
			  tool.changeWidth(3);
			  tool.changeColor(penColor);
		  }else if(e.getSource() == mediumButton){
			  tool = changeTool("Pen");
			  tool.changeWidth(6);
			  tool.changeColor(penColor);
		  }else if(e.getSource() == thickButton){
			  tool = changeTool("Pen");
			  tool.changeWidth(12);
			  tool.changeColor(penColor);
		  }else if(e.getSource() == eraserButton){
			  tool = changeTool("Eraser");
			  colorLabel.setText("けしゴムが選択されています");
			  System.out.println(tool.getClass().getSimpleName());
		  }else if(e.getSource() == initButton){
			  initFlag = true;
			  repaint();
		  }else if(e.getSource() == saveButton){
			  try {
	                boolean result = ImageIO.write( img, "jpeg", new File( "mytest.jpg" ) );
	                System.out.println( "画像をファイルに保存しました！  " + result);
	            } catch ( Exception ex ) {
	                ex.printStackTrace();
	                System.out.println(ex);
	                
	            }
		  }else if(e.getSource() == undoButton && undoStack.size() > 1){
			  redoStack.add(undoStack.remove(undoStack.size()-1));
			  lastImage = undoStack.get(undoStack.size()-1);
			  isUndo = true;
		  }else if(e.getSource() == redoButton && redoStack.size() > 0){
			  undoStack.add(redoStack.get(redoStack.size()-1));
			  lastImage = redoStack.remove(redoStack.size()-1);
			  isRedo = true;
		  }else if(e.getSource() == fillButton){
			  tool = changeTool("FillBucket");
			  tool.changeColor(penColor);
		  }else if(e.getSource() == stampButton){
			  //tool = changeTool("Stamp");
			  stampFrame.setVisible(true);
		  }
//		  else if(e.getSource() == stampButtons.get(0)){
//			  System.out.println(e.getSource());
//		  }
		  for(int i=0; i<fileNames.length-1; i++){
			  if(e.getSource() == stampButtons.get(i)){
				  tool = changeTool("Stamp");
				  ((Stamp) tool).setStamp(stamps.get(i));
				  System.out.println(i);
			  }
		  }
	}
	
	PaintTool changeTool(String newToolName){
		String currentToolName = tool.getClass().getSimpleName();
		if(newToolName.equals(currentToolName)){
			return tool;
		}else{
			return convertToolNameToTool(newToolName);
		}
		
	}

	PaintTool convertToolNameToTool(String toolName){
		if(toolName.equals("Pen")){
			return new Pen(img);
		}else if(toolName.equals("Eraser")){
			return new Eraser(img);
		}else if(toolName.equals("FillBucket")){
			return new FillBucket(img);
		}else if(toolName.equals("Stamp")){
			return new Stamp(img);
		}else{
			System.out.println("error");
			return new Pen(img);
		}
	}
	
	void readStamps(){
		File file = new File(".\\stamps");
		fileNames = file.list();
		for(int i = 0; i < fileNames.length-1; i++){
			addStampsToPanel(fileNames[i]);
			System.out.println(i);
		}
	}
	
	void addStampsToPanel(String fileName){
			 ImageIcon icon = new ImageIcon(".\\stamps\\" + fileName);
			 Image image = icon.getImage().getScaledInstance(stampFrameWidth/2-50, -1,Image.SCALE_SMOOTH);
			 ImageIcon scaledIcon = new ImageIcon(image);
			 JButton button = new JButton(scaledIcon);
			 button.addActionListener(this);
			 stampPanel.add(button);
			 stamps.add(icon);
			 stampButtons.add(button);
		      
	}
}
