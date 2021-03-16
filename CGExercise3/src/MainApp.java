import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;

@SuppressWarnings("serial")
public class MainApp extends JPanel {
	
	
   private static final int PREF_W = 600;
   private static final int PREF_H = PREF_W;
   
   private static final Color CURRENT_LIST_COLOR = new Color(190, 190, 255);
   private List<Color> colors = new ArrayList<Color>();
   
   private boolean drag = false;
   private boolean line = false;
   private boolean rect = false;
   private boolean circle = false;
   boolean firstTimeLine = true; 
   public String selectedColor;
   
   private List<Point> currentList = null;
   private BufferedImage bufferedImage = new BufferedImage(PREF_W, PREF_H, BufferedImage.TYPE_INT_ARGB);
   

   public MainApp() {

	  //some color ---- 
	  Color red = new Color(255,0,0);
	  Color yellow = new Color(255,255,0);
	  Color green = new Color(0,255,0);
	  Color blue = new Color(0,0,255);
      colors.add(red);
      colors.add(yellow);
      colors.add(green);
      colors.add(blue);
      
      // combobox --------
      String newColors[] = new String[]{"red", "yellow",
              "green", "blue"};
      JComboBox<String> colorList = new JComboBox<>(newColors);
      selectedColor = (String) colorList.getSelectedItem();
      
      
      // button --------
      JToggleButton jtb1 = new JToggleButton("Track Mouse");   
      JToggleButton jtb2 = new JToggleButton("Draw Line");
      JToggleButton jtb3 = new JToggleButton("Draw Rect");
      JToggleButton jtb4 = new JToggleButton("Draw Circle");
      JButton jb1 = new JButton("Clear Image");
      
      add(jtb1);      
      add(jtb2);    
      add(jtb3);
      add(jtb4);
      add(jb1);
      add(colorList);

      //Group the toggle buttons 
      ButtonGroup group = new ButtonGroup();
      group.add(jtb1 );
      group.add(jtb2);
      group.add(jtb3);
      group.add(jtb4);
      
      
      //Action Listener -----
      jtb1.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {

        	  line = jtb2.isSelected();
        	  drag = jtb1.isSelected();
        	  rect = jtb3.isSelected();
        	  circle = jtb4.isSelected();
        	  
          }
      });
      
      jtb2.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {

        	  line = jtb2.isSelected();
        	  drag = jtb1.isSelected();
        	  rect = jtb3.isSelected();
        	  circle = jtb4.isSelected();

          }
      });
      
      jtb3.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {

        	  line = jtb2.isSelected();
        	  drag = jtb1.isSelected();
        	  rect = jtb3.isSelected();
        	  circle = jtb4.isSelected();

          }
      });
      
      jtb4.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {

        	  line = jtb2.isSelected();
        	  drag = jtb1.isSelected();
        	  rect = jtb3.isSelected();
        	  circle = jtb4.isSelected();

          }
      });

      jb1.addActionListener(new ActionListener() {
		
    	  @Override
    	  public void actionPerformed(ActionEvent e) {
    		  bufferedImage = new BufferedImage(getWidth(), getHeight(),BufferedImage.TYPE_INT_ARGB);
			  repaint();			
    	  }
	  }); 
      
      colorList.addActionListener(new ActionListener() {
    	  
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	        JComboBox<String> combo = (JComboBox<String>) e.getSource();
    	        selectedColor = (String) combo.getSelectedItem();
    	    }
    	});
      
      //Mouse listener -----
      MyMouseAdapter myMA = new MyMouseAdapter();
      addMouseListener(myMA);
      addMouseMotionListener(myMA);
   }
   
   
   
   @Override
   public Dimension getPreferredSize() {
      return new Dimension(PREF_W, PREF_H);
   }


   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(bufferedImage, 0, 0, null);
      if (currentList != null) {
         drawList(g, currentList, CURRENT_LIST_COLOR, 1f);
      }
   }

   private void drawList(Graphics g, List<Point> ptList, Color color, float strokeWidth) {
      if (ptList.size() > 1) {
         Graphics2D g2 = (Graphics2D) g.create();
         g2.setColor(color);
         g2.setStroke(new BasicStroke(strokeWidth));
         for (int j = 0; j < ptList.size() - 1; j++) {
            int x1 = ptList.get(j).x;
            int y1 = ptList.get(j).y;
            int x2 = ptList.get(j + 1).x;
            int y2 = ptList.get(j + 1).y;
            g2.drawLine(x1, y1, x2, y2);
         }
         g2.dispose();         
         
      }
      
   }
   
   private void myDrawRect(Graphics g, Color c, Point p) {       
       g.setColor(c);       
       g.fillRect(p.x, p.y, 100, 100);
       g.setColor(Color.BLACK);
       g.drawRect(p.x, p.y, 100, 100);
   	
   }                                        

   private void myDrawCircle(Graphics g, Color c, Point p) {     
       g.setColor(c);
       g.fillOval(p.x, p.y, 100, 100);
       g.setColor(Color.BLACK);
       g.drawOval(p.x, p.y, 100, 100);
   }
   
   

   private class MyMouseAdapter extends MouseAdapter {
	   
      @Override
      public void mousePressed(MouseEvent e) {
         if (drag && e.getButton() == MouseEvent.BUTTON1) {
            currentList = new ArrayList<Point>();
            currentList.add(e.getPoint());
         }
         
         if (line && e.getButton() == MouseEvent.BUTTON1 ) {
        	       			 
        	 if (firstTimeLine) {
	        	 currentList = new ArrayList<Point>();
	             currentList.add(e.getPoint());
	             firstTimeLine = false;
        	 }else {
	             //if is the second click, print a line
            	 currentList.add(e.getPoint());
	             Graphics2D g3 = bufferedImage.createGraphics();
   
	             Color color;
	             
	             try {
	            	Field field = Class.forName("java.awt.Color").getField(selectedColor);
	            	color = (Color)field.get(null);
	             } catch (Exception ex) {
	            	color = null; // Not defined
	             }	
	             
	             drawList(g3, currentList, color, 3f);
	             currentList = null;
	             firstTimeLine = true;
	             repaint();
             }
             
         }
         
         if (rect && e.getButton() == MouseEvent.BUTTON1 ) {
   	         
        	 Point newPoint = e.getPoint();
       	     Graphics2D g3 = bufferedImage.createGraphics();
       	     
             Color color;
             
             try {
            	Field field = Class.forName("java.awt.Color").getField(selectedColor);
            	color = (Color)field.get(null);
             } catch (Exception ex) {
            	color = null; // Not defined
             }	
             
	         myDrawRect(g3, color, newPoint);
	    	 repaint();        	 

         }     
         
         if (circle && e.getButton() == MouseEvent.BUTTON1 ) {
   	         
        	 Point newPoint = e.getPoint();
       	     Graphics2D g3 = bufferedImage.createGraphics();
             Color color;
             
             try {
            	Field field = Class.forName("java.awt.Color").getField(selectedColor);
            	color = (Color)field.get(null);
             } catch (Exception ex) {
            	color = null; // Not defined
             }	
             
	         myDrawCircle(g3, color, newPoint);
	    	 repaint();        	 

         }   
         
      }

      @Override
      public void mouseReleased(MouseEvent e) {
         if (drag && e.getButton() == MouseEvent.BUTTON1) {
            currentList.add(e.getPoint());
            Graphics2D g2 = bufferedImage.createGraphics();
            Color color;
            
            try {
            	Field field = Class.forName("java.awt.Color").getField(selectedColor);
           		color = (Color)field.get(null);
            } catch (Exception ex) {
            	color = null; // Not defined
            }	
            
            drawList(g2, currentList, color, 3f);
            currentList = null;
            //The effect of calling repaint() is that Swing automatically clears the graphic on the panel 
            //and executes the paintComponent method to redraw the graphics.
            repaint();
         }
      }

      @Override
      public void mouseDragged(MouseEvent e) {
         if (drag && currentList != null) {
            currentList.add(e.getPoint());
            repaint();
         }
      }
   }

   private static void createAndShowGui() {
      MainApp mainPanel = new MainApp();
      JFrame frame = new JFrame("Draw Primitives");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(mainPanel);
      frame.setResizable(false);
      frame.pack();
      frame.setLocationByPlatform(true);
      frame.setVisible(true);
   }

   public static void main(String[] args) {
	   SwingUtilities.invokeLater(new Runnable() {
		   public void run() {
    		  createAndShowGui();
		   }
	   });
   }
}