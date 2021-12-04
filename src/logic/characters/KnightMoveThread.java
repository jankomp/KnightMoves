package logic.characters;

public class KnightMoveThread extends Thread {
private boolean move = false;
private boolean right = true;
private Knight k;

public void startMove() {
	move = true;
}

public void endMove() {
	move = false;
}

public void right() {
	right = true;
}

public void left() {
	right = false;
}

public KnightMoveThread(Knight k) {
	this.k = k;
}

public void run() {
	while(true) {
		if(move) {
			if(right) {
				k.runRight();
			}else {
				k.runLeft();
			}
		}
		try {sleep(50);}catch(Exception e) {System.out.println(e);}
	}
}
}
