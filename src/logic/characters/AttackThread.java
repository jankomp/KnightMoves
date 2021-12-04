package logic.characters;

public class AttackThread extends Thread {
	Character c;

	
	public AttackThread (Character c) {
		this.c = c;
	}
	
	public void run() {
		int pics = 0;
		if(c instanceof Knight) {
			pics = 10;
		} else if (c instanceof Goblin) {
			pics = 6;
		} else if (c instanceof Archer) {
			pics = 5;
		}
			c.startAttack();
			for(int i = 0; i < pics; i++) {
				if(!c.hurting) {
					c.attack(i);
				}
			try {sleep(600/pics);}	catch(Exception e) {System.out.println(e);}
			}
			if (pics == 5) {
			try {sleep(1200);}	catch(Exception e) {System.out.println(e);}
			}
			c.endAttack();
			c.idle();
		} 
}
