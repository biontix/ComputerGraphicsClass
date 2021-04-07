package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import control.GraphicsControl;

public class MainView extends JFrame{
	
    GraphicsControl imagePanel = new GraphicsControl(1,1,500,500);
    JPanel controlPanel = new JPanel();
    JPanel brightnessPanel = new JPanel();
    JPanel contrastPanel = new JPanel();
    
    String pathToFile;
    String pathToFileTxt;
    JTextField txtValue = new JTextField(30);
    JTextField normalization = new JTextField(3);
    JTextField prog = new JTextField(3);
	JButton grayscale = new JButton("Gray scale");
	JButton negative = new JButton("Negative");
	JButton okBrightness = new JButton("Set Brightness");
	JButton okContrast = new JButton("Set Contrast");
	JButton clear = new JButton("Clear");
    double choose = 1;
    
    public MainView() throws HeadlessException
    {
        super("Image Trasformation");
        
        controlPanel.setLayout(new GridLayout(5,1));
        controlPanel.setSize(new Dimension(300,500));
        
        brightnessPanel.setLayout(new GridLayout(1,2));
        brightnessPanel.setSize(new Dimension(100,100));
        
        contrastPanel.setLayout(new GridLayout(1,2));
        contrastPanel.setSize(new Dimension(100,100));
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(2,2));
        add(imagePanel,BorderLayout.WEST);
        add(controlPanel,BorderLayout.EAST);
        
        initializeButton();
        matchToContent();
        setVisible(true);
        
        openFile();
        setListenerEvents();
      
        
    }

    private void setListenerEvents(){
    	
        //add all action listener
    	grayscale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

          	  //do something          	  
            }
        });
    	
    	negative.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int w = imagePanel.currentBufferedImage.getWidth();
                int h = imagePanel.currentBufferedImage.getHeight();
                imagePanel.negation(imagePanel.currentBufferedImage);
                matchSize(imagePanel, w, h);         	  
                
            }
        });
    	
    	okBrightness.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

          	  //do something          	  
            }
        });
    	
    	okContrast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

          	  //do something          	  
            }
        });    	
    	
    	clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
              int w = imagePanel.currentBufferedImage.getWidth();
              int h = imagePanel.currentBufferedImage.getHeight();
          	  imagePanel.clear(imagePanel.originalBufferedImage);  
          	  matchSize(imagePanel, w, h);
          	  
            }
        });
    	
    }

 
    private void openFile()
    {
        JFileChooser open = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & BMP & PNG Images","jpg","bmp","png");
        open.setFileFilter(filter);
        int score = open.showOpenDialog(this);
        if(score == JFileChooser.APPROVE_OPTION)
        {
            pathToFile = open.getSelectedFile().getPath();
            imagePanel.uploadGraphicsFile(pathToFile);
            int width = imagePanel.originalBufferedImage.getWidth();
            int height = imagePanel.originalBufferedImage.getHeight();
            if(width != imagePanel.originalBufferedImage.getWidth() || height != imagePanel.originalBufferedImage.getHeight())
                matchToContent();
        }
    }

    private void initializeButton() {
    	
  	
    	SpinnerModel model1 = new SpinnerNumberModel(0, -255, 255, 1);
    	SpinnerModel model2 = new SpinnerNumberModel(0, -255, 255, 1);
    	JSpinner brightnessSpin = new JSpinner(model1);
    	JSpinner contrastSpin = new JSpinner(model2);
    	
    	brightnessPanel.add(brightnessSpin);  
    	brightnessPanel.add(okBrightness);
    	contrastPanel.add(contrastSpin);
    	contrastPanel.add(okContrast);
    	
    	controlPanel.add(grayscale);
    	controlPanel.add(negative);
    	controlPanel.add(brightnessPanel);
    	controlPanel.add(contrastPanel);
    	controlPanel.add(clear);
    	
    	
    }

    public void matchSize(GraphicsControl input, int w, int h){
        if(w != input.originalBufferedImage.getWidth() || h != input.originalBufferedImage.getHeight()){
            if(input.originalBufferedImage.getWidth()>=600 || input.originalBufferedImage.getHeight()>=600){
                input.setPreferredSize(new Dimension(600, 600));
                input.setMaximumSize(new Dimension(600, 600));
            }
            matchToContent();
        }
    }

    private void matchToContent()
    {
        pack();
        setLocationRelativeTo(null);
    }
}
