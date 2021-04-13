package control;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class GraphicsControl extends JPanel{
	
    public BufferedImage currentBufferedImage;
    public BufferedImage originalBufferedImage;
    double mask[][] = new double[3][3];
    int minRed = 255;
    int minGreen = 255;
    int minBlue = 255;
    int maxRed = 0;
    int maxGreen = 0;
    int maxBlue = 0;

    public GraphicsControl(int row, int col, int heigh, int width)
    {
        super();
        setLayout(new GridLayout(row,col));
        setSize(new Dimension(heigh,width));
    }
    

    public void uploadGraphicsFile(String path)
    {
        File graphicsFile = new File(path);
        try {
            originalBufferedImage = ImageIO.read(graphicsFile);
            currentBufferedImage = deepCopy(originalBufferedImage);
            Dimension size = new Dimension(originalBufferedImage.getWidth(),originalBufferedImage.getHeight());
            setPreferredSize(size);
            setMaximumSize(size);
            //setBorder(BorderFactory.createLineBorder(Color.BLACK));
            repaint();
        }
        catch(IOException ex)
        {
            JOptionPane.showMessageDialog(null, "Error read file: " + path);
            ex.printStackTrace();
        }
    }
    

    public void greyScale(BufferedImage input){
    	
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color color;
        int red, green, blue;

        for(int i=0; i<input.getWidth(); i++){
            for(int j=0; j<input.getHeight(); j++){
                color = new Color(input.getRGB(i,j));

                red = color.getRed();
                green = color.getGreen();
                blue = color.getBlue();
                int gray = (red+green+blue)/3;
                currentBufferedImage.setRGB(i,j, new Color(gray,gray,gray).getRGB());
            }
        }
        repaint();
    }
    
    public void lumaGreyScale(BufferedImage input){
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color color;
        int red, green, blue;

        for(int i=0; i<input.getWidth(); i++){
            for(int j=0; j<input.getHeight(); j++){
                color = new Color(input.getRGB(i,j));
                red = color.getRed();
                green = color.getGreen();
                blue = color.getBlue();
                int gray = (int)((0.299 * red) + (0.587 * green) + (0.114 * blue));
                currentBufferedImage.setRGB(i,j, new Color(gray,gray,gray).getRGB());
            }
        }
        repaint();
    }

    public void brightness(BufferedImage input, int k){
    	
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color color;
        int red, green, blue;

        for(int i=0; i<input.getWidth(); i++)
        {
            for(int j=0; j<input.getHeight(); j++){
                color = new Color(input.getRGB(i,j));
                red = color.getRed();
                green = color.getGreen();
                blue = color.getBlue();

                red+=k;
                green+=k;
                blue+=k;
                if(red>255){
                    red=255;
                }
                else if(red<0){
                    red=0;
                }
                if(green>255){
                    green=255;
                }
                else if(green<0){
                    green=0;
                }
                if(blue>255){
                    blue=255;
                }
                else if(blue<0){
                    blue=0;
                }
                currentBufferedImage.setRGB(i,j, new Color(red,green,blue).getRGB());
            }
        }
        repaint();
    }

    public void contrast(BufferedImage input, double k)
    {
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color color;
        double red, green, blue;

        for(int i=0; i<input.getWidth(); i++){
            for(int j=0; j<input.getHeight(); j++){
                color = new Color(input.getRGB(i, j));

                red = color.getRed();
                green = color.getGreen();
                blue = color.getBlue();

                red = (red * k);
                green = (green * k);
                blue = (blue * k);

                if(red>255){
                    red = 255;
                }
                else if(red<0){
                    red = 0;
                }
                if(green>255){
                    green = 255;
                }
                else if(green<0){
                    green = 0;
                }
                if(blue>255){
                    blue = 255;
                }
                else if(blue<0){
                    blue = 0;
                }

                currentBufferedImage.setRGB(i, j, new Color((int)red,(int)green,(int)blue).getRGB());
            }
        }
        repaint();
    }

    public void negation(BufferedImage input)
    {
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color color;
        int red, green, blue;
        maxMin(input);

        for(int i=0; i<input.getWidth(); i++){
            for(int j=0; j<input.getHeight(); j++){
                color = new Color(input.getRGB(i,j));
                red = maxRed - color.getRed();
                green = maxGreen - color.getGreen();
                blue = maxBlue - color.getBlue();

                currentBufferedImage.setRGB(i,j, new Color(red, green, blue).getRGB());
            }
        }
        repaint();
    }

    public BufferedImage deepCopy(BufferedImage bi) {
    	ColorModel cm = bi.getColorModel();
    	boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
    	WritableRaster raster = bi.copyData(null);
    	return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public void clear(BufferedImage input) {
       
	   	setSize(new Dimension(input.getWidth(),input.getHeight()));
	    Color color;
	    int red, green, blue;
	    maxMin(input);

	    for(int i=0; i<input.getWidth(); i++){
           for(int j=0; j<input.getHeight(); j++){
               color = new Color(input.getRGB(i,j));
               red = color.getRed();
               green = color.getGreen();
               blue = color.getBlue();

               currentBufferedImage.setRGB(i,j, new Color(red, green, blue).getRGB());
           }
	    }
	    repaint();
    }

    public void setSize(Dimension dimension){
       currentBufferedImage = new BufferedImage((int)dimension.getWidth(),(int)dimension.getHeight(),BufferedImage.TYPE_INT_RGB);
       setPreferredSize(dimension);
       setMaximumSize(dimension);
    }
    
    
    public void resetMaxMin(){
        minRed = 255;
        minGreen = 255;
        minBlue = 255;
        maxRed = 0;
        maxGreen = 0;
        maxBlue = 0;
    }

    public void maxMin(BufferedImage input)
    {
        resetMaxMin();
        Color color;
        int red, green, blue;

        for(int i=0; i<input.getWidth(); i++){
            for(int j=0; j<input.getHeight(); j++){
                color = new Color(input.getRGB(i,j));
                red = color.getRed();
                green = color.getGreen();
                blue = color.getBlue();

                if(red>maxRed){
                    maxRed = red;
                }
                if(green>maxGreen){
                    maxGreen = green;
                }
                if(blue>maxBlue){
                    maxBlue = blue;
                }
                if(red<minRed){
                    minRed = red;
                }
                if(green<minGreen){
                    minGreen = green;
                }
                if(blue<minBlue){
                    minBlue = blue;
                }
            }
        }
    }


    @Override
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(currentBufferedImage,0,0,this);
    }
}

