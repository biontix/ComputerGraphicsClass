import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.imageio.ImageIO;
 


public class MainApp {
	
	    
	public static void main(String[] args) throws Exception{
		
		//read and write ppm P3
		imageP3 = readImageP3("sampleP3.ppm");
		writeImageP3(imageP3, "outputP3.ppm");
		
		//read and write ppm P6
		readImageP6("sampleP6.ppm");
		writeImageP6("outputP6.ppm", bytes, width, height);
		
		//read and write jpeg
		imageJpeg = readImageJpeg("sampleJpeg.jpg");
		writeImageJpeg(imageJpeg);
		
	}
	
	
	// This method read a ppm p3 Image and check also if there is a comment beetween the line
	public static RGBImage readImageP3(String filename) throws IOException{
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(filename)));
				
        bytes=null;
        char buffer;                   
        String p3 = new String();      // PPM magic number ("P3")
        String dim = new String();     // image dimension as a string
        int count = 0;

        
		
        // read char by char the header
        do {
            buffer = (char)reader.read();
            p3 = p3 + buffer;
            count ++;
        } while (buffer != '\n' && buffer != ' ');

        if (p3.charAt(0) == 'P') {

            buffer = (char)reader.read();
            count ++;
            if (buffer == '#') {
              do {
                buffer = (char)reader.read();
                count ++;
              } while (buffer != '\n');
              count ++;
              buffer = (char)reader.read();
              
            }
            
            // second header line is "width height"
            do {
                dim = dim + buffer;
                buffer = (char)reader.read();
                count ++;
            } while (buffer != ' ' && buffer != '\n');
            width = Integer.parseInt(dim.trim());
            
         
            dim = new String();
            buffer = (char)reader.read();
            count ++;
            
            do {
                dim = dim + buffer;
                buffer = (char)reader.read();
                count ++;
            } while (buffer != ' ' && buffer != '\n');
            height = Integer.parseInt(dim);
            // end width and height 

            
            do {                          
                buffer = (char)reader.read();
                count ++;
            } while (buffer != ' ' && buffer != '\n');
        }
		
        if (p3.charAt(1) == '3') {        	
		
			int numcolumns = width;
			int numrows = height;
			short[][] r = new short[numrows][numcolumns];
			short[][] g = new short[numrows][numcolumns];
			short[][] b = new short[numrows][numcolumns];
			String line;
			int loc = 0; // will range to rowdim*columndim;
			int row;
			int column;
			
			while((line = reader.readLine())!=null){
				String[] numbers = line.split("\\s+");
				for(int i=0;i<numbers.length;i++){
					
					//r g b index
					int rawloc = loc / 3;
					
					//row index
					row = rawloc / numcolumns;
					
					//column index
					column = rawloc % numcolumns;
					
					//r g b index for insert
					int color = loc % 3;
					 
					if(color == 0) {
						r[row][column] = Short.parseShort(numbers[i]);
					}else if( color ==1){
						g[row][column] =  Short.parseShort(numbers[i]);
					} else if (color ==2){
						 b[row][column] =  Short.parseShort(numbers[i]);		
					}
					loc += 1;
				}
				
			} 
			
			return new RGBImage(r, g, b);
			
		} else {
			
			throw new IOException("Error: can not read this file; maybe it's not a ppm P3?");
			
		}
	}
	
	public static void  writeImageP3(RGBImage image, String filename) throws IOException{
		
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
		
		// write header
		int rowdimension = image.numrows;
		int columndimension = image.numcolumns;
		writer.write("P3");
		writer.newLine();
		writer.write(image.numcolumns+" "+image.numrows);
		writer.newLine();
		writer.write("256");
		writer.newLine();
		
		// write body
		for(int row=0;row<rowdimension;row++){
			for(int column=0;column<columndimension;column++){
				writer.write(image.getRed()[row][column]+" ");
				writer.write(image.getGreen()[row][column]+" ");
				writer.write(image.getBlue()[row][column]+"");
				if(column < columndimension - 1)writer.write(" ");
			}
			writer.newLine();
		}
		
		writer.flush();
		writer.close();
	}

    private static byte bytes[]=null;        // bytes which make up binary PPM image for P6
    private static int height = 0;			 // height for P6
    private static int width = 0;            // width for P6
	private static RGBImage imageP3;
    private static BufferedImage imageJpeg;

    //Read the image from the specified file ppm P6 and check also if there is a comment beetween the line
    private static void readImageP6(String filename)  throws IOException, NumberFormatException {

      
        bytes=null;
        char buffer;                   // read char by char the header
        String p6 = new String();      // PPM magic number ("P6")
        String dim = new String();     // image dimension as a string
        int count = 0;
        
        // We have to utilize FileImputStream for read byte instead BufferedReader
        File f = new File(filename);
        FileInputStream reader = new FileInputStream(f);
        

        do {
            buffer = (char)reader.read();
            p6 = p6 + buffer;
            count ++;
        } while (buffer != '\n' && buffer != ' ');

        if (p6.charAt(0) == 'P') {

            buffer = (char)reader.read();
            count ++;
            if (buffer == '#') {
              do {
                buffer = (char)reader.read();
                count ++;
              } while (buffer != '\n');
              count ++;
              buffer = (char)reader.read();
              
            }
            
            // second header line is "width height\n"
            do {
                dim = dim + buffer;
                buffer = (char)reader.read();
                count ++;
            } while (buffer != ' ' && buffer != '\n');

            width = Integer.parseInt(dim.trim());
            dim = new String();
            buffer = (char)reader.read();
            count ++;
            
            do {
                dim = dim + buffer;
                buffer = (char)reader.read();
                count ++;
            } while (buffer != ' ' && buffer != '\n');
            height = Integer.parseInt(dim);
            // end width and height 

            
            do {                          
                buffer = (char)reader.read();
                count ++;
            } while (buffer != ' ' && buffer != '\n');


            // remainder: file is width*height*3 bytes (red/green/blue triples)
            bytes = new byte[height*width*3];
            
            if (p6.charAt(1) == '6') {
            	
            	reader.read(bytes,0,height*width*3);

            }

            reader.close();
            
    	}else {
            width = height = 0; 
            bytes= new byte[0];
            throw new IOException("Error: can not read this file; maybe it's not a ppm P6?");
        }
    }
    
    public static void writeImageP6(String filename, byte[] data, int width, int height) throws IOException {

        if (data != null) {

                FileOutputStream fos = new FileOutputStream(filename);
                fos.write(new String("P6\n").getBytes());
                fos.write(new String( width + " " + height + "\n").getBytes());
                fos.write(new String("255\n").getBytes());
                fos.write(data);
                fos.close();
          }
    }
    
    public static BufferedImage readImageJpeg (String filename) {
    	
    	BufferedImage img = null;
    	try {
    		
    	    img = ImageIO.read(new File(filename));
    	    return img;
    	    
    	} catch (IOException e) {
    		
    		System.out.println("Error: can not read this jpeg file");
    		return null;
    	}
    }
    
    public static void writeImageJpeg (BufferedImage img) {
    	
    	try {
    		
    	    File outputfile = new File("outputJPEG.png");
    	    ImageIO.write(img, "png", outputfile);
    	    
    	} catch (IOException e) {
    		System.out.println("Error: can not write output jpeg file");
    	}
    }
}
