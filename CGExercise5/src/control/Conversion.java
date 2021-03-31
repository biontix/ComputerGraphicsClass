package control;
import java.text.DecimalFormat;

import Model.CMYB;
import Model.RGB;

public class Conversion {
	
		
	public static CMYB RGBtoCMYB(RGB RGBcolor){
		
	    int red = RGBcolor.getRed();
	    int green = RGBcolor.getGreen();
	    int blue = RGBcolor.getBlue();  
	    
	    double cyan;
	    double magenta; 
	    double yellow;
	    double black;   
	    
	    CMYB CMYBcolor; 
	    
	    DecimalFormat df = new DecimalFormat("#.##");
	    	
        double R=red/255.0,G = green/255.0,B = blue/255.0;
        
		black = Math.min(Math.min(1-R, 1-G), 1-B);
		
	    try {
	    	cyan = (1.0-R-black) / (1.0-black); 

	    	if (cyan == Double.POSITIVE_INFINITY || cyan == Double.NEGATIVE_INFINITY || Double.isNaN(cyan))
	        	throw new ArithmeticException();
	    }catch (ArithmeticException ae) {
	    	cyan = 0.0;
	    }
	    
	    try {
	    	magenta = (1.0-G-black) / (1.0-black);
	    	if (magenta == Double.POSITIVE_INFINITY || cyan == Double.NEGATIVE_INFINITY || Double.isNaN(magenta))
	        	throw new ArithmeticException();
	    }catch (ArithmeticException ae) {
	    	magenta = 0.0;
	    }
	    
	    try {
	    	yellow = (1.0-B-black) / (1.0-black);
	    	if (yellow == Double.POSITIVE_INFINITY || cyan == Double.NEGATIVE_INFINITY || Double.isNaN(yellow))
	        	throw new ArithmeticException();
	    }catch (ArithmeticException ae) {
	    	yellow = 0.0;
	    }	

	    CMYBcolor = new CMYB(Double.parseDouble(df.format(cyan).replaceAll(",", ".")), 
	    					 Double.parseDouble(df.format(magenta).replaceAll(",", ".")), 
	    					 Double.parseDouble(df.format(yellow).replaceAll(",", ".")), 
	    					 Double.parseDouble(df.format(black).replaceAll(",", ".")));
	    
	    return CMYBcolor; 

	}
	
	
	public static RGB CMYBtoRGB(CMYB CMYBcolor){
		
	    int red;
	    int green;
	    int blue;
	    

	    double cyan = CMYBcolor.getCyan() / 100;
	    double magenta = CMYBcolor.getMagenta() / 100; 
	    double yellow = CMYBcolor.getYellow() / 100;
	    double black = CMYBcolor.getBlack() / 100;  
	    
	    RGB RGBcolor;  	    
	    
	    red = (int) ((1-Math.min(1,cyan*(1-black)+black))*255);
	    green = (int) ((1-Math.min(1,magenta*(1-black)+black))*255);
	    blue = (int) ((1-Math.min(1,yellow*(1-black)+black))*255);     
		    
	    RGBcolor = new RGB(red, blue, green);
	    return RGBcolor; 

	}
}
