package program;

public class testMain {

	public testMain(){
		function();
		main(null);
	}
	
	public void main(String[] args){
		System.out.println("testMain Execute!");
	}
	
	public void function(){
		while(true){
			System.out.println("testMain function Execute!");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
