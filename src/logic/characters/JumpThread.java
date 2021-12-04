package logic.characters;

public class JumpThread extends Thread {
Knight k;

public JumpThread (Knight k) {
	this.k = k;
}

public void run() {
	k.startJump();
	for (int i = 0; i < 5; i++) {
		k.decreaseY(40);
		try {
			sleep(50);
			}
		catch(Exception e) {System.out.println(e);}
	}
	k.endJump();
}
}
