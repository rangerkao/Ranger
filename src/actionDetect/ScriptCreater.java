package actionDetect;


import java.util.ArrayList;
import java.util.List;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class ScriptCreater implements NativeMouseInputListener,NativeKeyListener{

	public static List<String> actRecord = new ArrayList<String>();
	public static boolean enable = false;
	public static long stime ;
	
	public static void record(String txt){
		if(enable){
			actRecord.add(txt);
			
		}
	}
	
	public static void main(String[] args) {
		
		try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
		
		actRecord.add("record start!");
		
		
        ScriptCreater scc = new ScriptCreater();
        GlobalScreen.getInstance().addNativeKeyListener(scc);
        GlobalScreen.getInstance().addNativeMouseListener(scc);
        
    }

	//鍵盤偵測
	public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        //F8開始紀錄
        if (e.getKeyCode() == NativeKeyEvent.VC_F8) {
        	enable=true;
        	stime = System.currentTimeMillis();
        }else
        
        //F9中斷記錄
        if (e.getKeyCode() == NativeKeyEvent.VC_F9) {
        	enable=false;
        }else
        
        //F10 結束
        if (e.getKeyCode() == NativeKeyEvent.VC_F10) {
            try {
				GlobalScreen.unregisterNativeHook();
				System.out.println("END");
				//印出記錄內容
				for(String s:actRecord){
					System.out.println(s);
				}
			} catch (NativeHookException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }else{
        	record("wait\t"+(System.currentTimeMillis()-stime));
            stime=System.currentTimeMillis();
            record("type\t"+NativeKeyEvent.getKeyText(e.getKeyCode())+" CODE : "+e.getKeyCode());
            stime = System.currentTimeMillis();
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }


	public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode())+" CODE : "+e.getKeyCode());
    }
	
	
	//滑鼠偵測
    
    public void nativeMouseClicked(NativeMouseEvent e) {
        System.out.println("Mouse Clicked: " + e.getClickCount());
        record("wait\t"+(System.currentTimeMillis()-stime));
        stime=System.currentTimeMillis();
        record("move\t("+e.getX()+","+e.getY()+")");
        record("click");
        stime = System.currentTimeMillis();
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        System.out.println("Mouse Pressed: " + e.getButton());
    }


	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {/* ...unimplement*/}

	@Override
	public void nativeMouseDragged(NativeMouseEvent arg0) {/* ...unimplement*/}

	@Override
	public void nativeMouseMoved(NativeMouseEvent arg0) {/* ...unimplement*/}
}
