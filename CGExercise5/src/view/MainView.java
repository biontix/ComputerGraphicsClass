package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Model.CMYB;
import Model.RGB;
import control.Conversion;

public class MainView {
	
	public int red;
	public int blue;
	public int green;
	
	public double cyan;
	public double magenta;
	public double yellow;
	public double black;
	
    public JPanel panel1;    
    public JPanel panel2;
    public JLabel title1;
    public JLabel title2;
    public JLabel statusRed;
    public JLabel statusBlue;
    public JLabel statusGreen;
    public JLabel statusCyan;
    public JLabel statusMagenta;
    public JLabel statusYellow;
    public JLabel statusBlack;
    public JSlider sliderRed; 
    public JSlider sliderBlue; 
    public JSlider sliderGreen;   
    public JSlider sliderCyan; 
    public JSlider sliderMagenta; 
    public JSlider sliderYellow;  
    public JSlider sliderBlack;
    public JButton buttonRGB;
    public JButton buttonCMYK;    
    
    Conversion c = new Conversion();
    
	
    /**
     *  A Constructor for objects of class View. Initializes 
     *  GUI components.
     */
    public MainView(){
    	
        // Create and set up a frame window
        JFrame frame = new JFrame("RGB CMYK Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setSize(300, 300);
        
        //Panels title
        title1 = new JLabel("RGB");
        title2 = new JLabel("CMYK");
         
        // Set the panel to add buttons
        panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(80,120));
        GridLayout gl1 = new GridLayout(4,2); //3 rows, 4 columns
        panel1.setLayout(gl1);
        TitledBorder titleRGB;
        titleRGB = BorderFactory.createTitledBorder("RGB");
        panel1.setBorder(titleRGB);

        panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(80,120));
        GridLayout gl2 = new GridLayout(5,2); //3 rows, 4 columns
        panel2.setLayout(gl2);
        TitledBorder titleCMYK;
        titleCMYK = BorderFactory.createTitledBorder("CMYK");
        panel2.setBorder(titleCMYK);
        
        // Add status label to show the status of the slider
        statusRed = new JLabel("Red: 0");
        statusBlue = new JLabel("Blue: 0");
        statusGreen = new JLabel("Green: 0");
        
        statusCyan = new JLabel("Cyan: 0");
        statusMagenta = new JLabel("Magenta: 0");
        statusYellow = new JLabel("Yellow: 0");
        statusBlack = new JLabel("Black: 0");
        
         
        // Set the slider
        sliderRed = new JSlider(0, 255, 0); 
        sliderBlue = new JSlider(0, 255, 0); 
        sliderGreen = new JSlider(0, 255, 0);   
        
        sliderCyan = new JSlider(0, 100, 0); 
        sliderMagenta = new JSlider(0, 100, 0); 
        sliderYellow = new JSlider(0, 100, 0);  
        sliderBlack = new JSlider(0, 100, 0);
        
        // Add Button Select RGB, CMYK
        buttonRGB = new JButton("Select RGB");
        buttonCMYK = new JButton("Select CMYK");         

         
        // Add change listener to the slider
        sliderRed.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                statusRed.setText("Red: " + ((JSlider)e.getSource()).getValue());
                red = ((JSlider)e.getSource()).getValue();
            }
        });
        
        sliderGreen.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                statusGreen.setText("Green: " + ((JSlider)e.getSource()).getValue());
                green = ((JSlider)e.getSource()).getValue();
            }
        });        

        sliderBlue.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                statusBlue.setText("Blue: " + ((JSlider)e.getSource()).getValue());
                blue = ((JSlider)e.getSource()).getValue();
            }
        });       

        
        sliderCyan.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                statusCyan.setText("Cyan: " + ((JSlider)e.getSource()).getValue());
                cyan = ((JSlider)e.getSource()).getValue();
            }
        });
        

        sliderMagenta.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                statusMagenta.setText("Magenta: " + ((JSlider)e.getSource()).getValue());
                magenta = ((JSlider)e.getSource()).getValue();
            }
        });
        

        sliderYellow.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                statusYellow.setText("Yellow: " + ((JSlider)e.getSource()).getValue());
                yellow = ((JSlider)e.getSource()).getValue();
            }
        });
        
        sliderBlack.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                statusBlack.setText("Black: " + ((JSlider)e.getSource()).getValue());
                black = ((JSlider)e.getSource()).getValue();
            }
        });
        
        // Add change listener to the Button
        buttonRGB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	//convert RGB to CMYK
            	RGB RGBcolor1 = new RGB(red, green, blue);		
        		CMYB CMYBcolor1 = c.RGBtoCMYB(RGBcolor1);
        		
        		//SET the  CMYK  value
        		sliderCyan.setValue((int)(CMYBcolor1.getCyan()*100));
        		statusCyan.setText("Cyan: " + String.valueOf(CMYBcolor1.getCyan()*100));
        		sliderMagenta.setValue((int)(CMYBcolor1.getMagenta()*100));
        		statusMagenta.setText("Magenta: " + String.valueOf(CMYBcolor1.getMagenta()*100));
        		sliderYellow.setValue((int)(CMYBcolor1.getYellow()*100));
        		statusYellow.setText("Yellow: " + String.valueOf(CMYBcolor1.getYellow()*100));
        		sliderBlack.setValue((int)(CMYBcolor1.getBlack()*100));
        		statusBlack.setText("Black: " + String.valueOf(CMYBcolor1.getBlack()*100));
          	  
             }
        });
        
        buttonCMYK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	//convert CMYK to RGB
            	CMYB CMYBcolor2 = new CMYB(cyan, magenta, yellow, black);		
        		RGB RGBcolor2 = c.CMYBtoRGB(CMYBcolor2);
        		
        		//SET the  CMYK  value
        		sliderRed.setValue(RGBcolor2.getRed());
        		statusRed.setText("Red: " + String.valueOf(RGBcolor2.getRed()));
        		sliderGreen.setValue(RGBcolor2.getGreen());
        		statusGreen.setText("Green: " + String.valueOf(RGBcolor2.getGreen()));
        		sliderBlue.setValue(RGBcolor2.getBlue());
        		statusBlue.setText("Blue: " + String.valueOf(RGBcolor2.getBlue()));
        		
            }
        });
        
         
        // Add the slider and label to the panel
        
        panel1.add(sliderRed);
        panel1.add(statusRed);
        panel1.add(sliderBlue);
        panel1.add(statusBlue);
        panel1.add(sliderGreen);
        panel1.add(statusGreen);
        panel1.add(buttonRGB);

        

        panel2.add(sliderCyan);
        panel2.add(statusCyan);
        panel2.add(sliderMagenta);
        panel2.add(statusMagenta);
        panel2.add(sliderYellow);
        panel2.add(statusYellow);
        panel2.add(sliderBlack);
        panel2.add(statusBlack);
        panel2.add(buttonCMYK);

                 
        // Set the window to be visible as the default to be false
        frame.add(panel1, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
        f.add(menuPanel, BorderLayout.EAST);
        f.add(dateSelectPanel, BorderLayout.NORTH);
        f.add(zoomPanel,  BorderLayout.SOUTH);
        */

    }
    
}
