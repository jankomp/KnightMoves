package logic.characters;

public class DieThread extends Thread {
private Knight k;

public DieThread(Knight k) {
	this.k = k;
}

public void run() {
		
		for(int i = 0; i < 8; i++) {
			k.die(i);
			try {
				sleep(50);
			}catch (Exception e) {
				System.out.println(e);
			}
	}
}
}
