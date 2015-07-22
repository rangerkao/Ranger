package actionDetect;


import java.awt.*;
import java.awt.event.*;
  
public class MouseAdapterDemo extends MouseAdapter {
    Frame frame;
    Panel panel;
    Label text;
     
    public static void main(String[] args) {
        new MouseAdapterDemo();
    }
      
    public MouseAdapterDemo() {
        frame = new Frame("AWTDemo");
        frame.addWindowListener(new AdapterDemo());
        frame.setSize(800, 600);
         
        panel = new Panel();
        panel.addMouseListener(this);
        panel.addMouseWheelListener(this);
        panel.addMouseMotionListener(this);
         
        text = new Label("something happened..");
         
        panel.add(text);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
     
    // from MouseListener
    public void mouseClicked(MouseEvent e) {
        text.setText("Clicked at ("+e.getXOnScreen()+","+e.getYOnScreen()+")");
        System.out.println("Clicked at ("+e.getX()+","+e.getY()+")");
    }
     
    public void mousePressed(MouseEvent e) {
        text.setText("mousePressed");
        System.out.println("mousePressed");
    }
     
    public void mouseReleased(MouseEvent e) {
        text.setText("mouseReleased");
        System.out.println("mouseReleased");
    }
     
    public void mouseEntered(MouseEvent e) {
        text.setText("mouseEntered");
        System.out.println("mouseEntered");
    }
     
    public void mouseExited(MouseEvent e) {
        text.setText("mouseExited");
        System.out.println("mouseExited");
    }
 
    // from MouseWheelListener
    public void mouseWheelMoved(MouseWheelEvent e) {
        text.setText("mouseWheelMoved");
        System.out.println("mouseWheelMoved");
    }
 
    // from MouseMotionListener
    public void mouseDragged(MouseEvent e) {
        text.setText("mouseDragged");
        System.out.println("mouseDragged");
    }
     
    public void mouseMoved(MouseEvent e) {
        text.setText("mouseMoved");
        System.out.println("mouseMoved");
    }
}
  
class AdapterDemo extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}
 
/* 《程式語言教學誌》的範例程式
    http://pydoing.blogspot.com/
    檔名：MouseAdapterDemo.java
    功能：示範 Java 程式 
    作者：張凱慶
    時間：西元 2012 年 3 月 */
