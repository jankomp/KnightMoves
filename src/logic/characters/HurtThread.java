package logic.characters;

public class HurtThread extends Thread {
	Character c;

	public HurtThread (Character c) {
		this.c = c;
	}
	
	public void run() {
		if(c instanceof Goblin) {
		c.startHurting();
		}
	
		c.visualizeHurting();
		
		try {
			sleep(330);
			}
		catch(Exception e) {System.out.println(e);}
		
		
		
		c.endHurting();
		c.idle();
	}
}
