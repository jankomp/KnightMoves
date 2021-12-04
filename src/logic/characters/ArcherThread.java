package logic.characters;

import java.util.ArrayList;

public class ArcherThread extends Thread {
	public static boolean active = true;
public static ArrayList<Archer> archers = new ArrayList<Archer>();
public static ArrowThread arrt= new ArrowThread();
	
	//Adding a new Archer to the ArcherThread so it gets updated from this Thread (gets called in the Constructor of Archer)
	public void addArcher(Archer a) {
		archers.add(a);
	}
	
	
	
	public void run() {
		if(active) {
			arrt.start();
			while (true) {
	
				try {
					//Update all Goblins and then sleep
					for(Archer a : archers) {
						if(a.alive) {
							a.fall(40);
							a.walk();
							if(a.spotKnight()&&!a.attacking) {
								new AttackThread(a).start();
							}
						}
					}
					sleep(50);
				}catch(Exception e) {
					System.out.println(e);
				}
			}
		}
	}
}
