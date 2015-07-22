package robot;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.SwingUtilities;

 
/**
 * A Java Robot example class.
 * 
 * Caution: Using the Java Robot class improperly can cause
 * a lot of system problems. I had to reboot my Mac ~10
 * times yesterday while trying to debug this code.
 * 
 * I created this class to demonstrate the Java Robot
 * class on a Mac OS X system, though it should run on Linux
 * or Windows as well.
 * 
 * On a Mac system, I place the TextEdit text editor in the 
 * upper-left corner of the screen, and put a bunch of blank lines 
 * in the editor. Then I run this Java Robot example from 
 * Eclipse or the Unix command line.
 * 
 * It types the three strings shown in the code below into
 * the text editor.
 *
 * Many thanks to the people on the Mac Java-dev mailing list
 * for your help. 
 * 
 * @author Alvin Alexander, <a href="http://devdaily.com" title="http://devdaily.com">http://devdaily.com</a>
 *
 */
public class robottest
{
  Robot robot = new Robot();
  String filename="robotScript.txt";
  String mobiletext="mobileList.txt";
  
  List<String> mobileList = new ArrayList<String>();
  List<String> actList = new ArrayList<String>();
  public static void main(String[] args) throws AWTException, InterruptedException
  {
    new robottest();
  }
  
  public robottest() throws AWTException, InterruptedException
  {
	  
	  readmobile(mobiletext);
	  
	  readAct(filename);
	  
	  robot.setAutoDelay(0);
	  robot.setAutoWaitForIdle(true);
	  
	  /*Scanner scanner = new Scanner(System.in);
	  String imput = scanner.next();*/
	  
	  Thread t = new Thread(
		  new Runnable() {
			  public void run() {
				  for(String mobile:mobileList){
						if(mobile.length()==0)
							continue;
						
						for(String acts:actList){

							String[] act = acts.split("\t");
							
							if(act.length==0)
								continue;
							
							if("wait".equals(act[0])){
								
								if(act.length>1 && act[1].matches("^\\d+$"))
									robot.delay(Integer.parseInt(act[1]));
								
							}else if("click".equals(act[0])){
								leftClick();
							}else if("move".equals(act[0])){
								
								if(act.length>1 && act[1].matches("^\\d+,\\d+$")){
									String[] coordinates =act[1].split(",");
									if(coordinates.length==2){
										robot.mouseMove(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
									}
								}
							}else if("type".equals(act[0])){
								if(act.length>1 && (act[1]!=null && !"".equals(act[1])))
									type(act[1]);
								else
									type(mobile);
							}else if("doubleClick".equals(act[0])){
								doubleClick();						
							}
							robot.delay(500);
						}
					}		  
			  }
		  });
	t.setDaemon(true);
	t.start();
	
	
	
	String imput="";
	while(true){
		
		if(!t.isAlive()){
			break;
		}
		if("^c".equals(imput)){
			break;
		}
	}
	
	  System.exit(0);
  }
   
  
  
  
  public void readmobile(String filePath) throws InterruptedException {
	  BufferedReader reader = null;
	  
	  try {
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
		
		String str = null;

		while ((str = reader.readLine()) != null) {
			String s=str.trim();
			if(s.matches("^\\d+$"))
				mobileList.add(s);		
		}
		
		
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  public void readAct(String filePath) throws InterruptedException {
		
		BufferedReader reader = null;
	
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8")); 
			// 指定讀取文件的編碼格式，以免出現中文亂碼
			
			
			String str = null;
					
			
					
			
			while ((str = reader.readLine()) != null) {
				
				System.out.println(str);
				String s=str.trim();			
				
				actList.add(s);
				
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(reader!=null){
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
  
  public void JavaRobotExample() throws AWTException
  {
    robot.setAutoDelay(40);
    robot.setAutoWaitForIdle(true);
    robot.delay(4000);
    robot.mouseMove(40, 130);
    robot.delay(500);
 
    leftClick();
    robot.delay(500);
    leftClick();
 
    robot.delay(500);
    type("Hello, world");
 
    robot.mouseMove(40, 160);
    robot.delay(500);
 
    leftClick();
    robot.delay(500);
    leftClick();
     
    robot.delay(500);
    type("This is a test of the Java Robot class");
     
    robot.delay(50);
    type(KeyEvent.VK_DOWN);
     
    robot.delay(250);
    type("Four score and seven years ago, our fathers ...");
 
    robot.delay(1000);
    System.exit(0);
    
  }
  
  private void leftClick()
  {
    robot.mousePress(InputEvent.BUTTON1_MASK);
    robot.delay(300);
    robot.mouseRelease(InputEvent.BUTTON1_MASK);
    robot.delay(200);
  }

  private void doubleClick()
  {
    robot.mousePress(InputEvent.BUTTON1_MASK);
    robot.delay(300);
    robot.mouseRelease(InputEvent.BUTTON1_MASK);
    robot.mousePress(InputEvent.BUTTON1_MASK);
    robot.delay(300);
    robot.mouseRelease(InputEvent.BUTTON1_MASK);

  }
   
  private void type(int i)
  {
    robot.delay(40);
    robot.keyPress(i);
    robot.keyRelease(i);
  }
 
  private void type(String s)
  {
    byte[] bytes = s.getBytes();
    for (byte b : bytes)
    {
      int code = b;
      // keycode only handles [A-Z] (which is ASCII decimal [65-90])
      if (code > 96 && code < 123) code = code - 32;
      robot.delay(40);
      robot.keyPress(code);
      robot.keyRelease(code);
    }
  }
  
}