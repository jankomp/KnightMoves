package graphic;

import javax.swing.JPanel;

public class RepaintThread extends Thread {
	private MyLevelPanel p;
	
	public RepaintThread(MyLevelPanel p) {
		this.p = p;
	}
	
	public void run() {
		do{
			
			p.repaint();
			
			try {
				sleep(50);
				}
			catch(Exception e){
				System.out.println(e);
			}
		}while(!p.gameOver);
		if(p.gameOver) {
			p.gameOverInfo();
			}
		
	}
}
