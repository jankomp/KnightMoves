package logic.characters;

import java.awt.Graphics;
import java.util.ArrayList;

import graphic.MyLevelPanel;

public class GoblinThread extends Thread {
	public static boolean active = true;
	public static ArrayList<Goblin> goblins = new ArrayList<Goblin>();
	
	//Adding a new Goblin to the GoblinThread so it gets updated from this Thread (gets called in the Constructor of Goblin)
	public void addGoblin(Goblin g) {
		goblins.add(g);
	}
	
	
	public void run() {
		if(active) {
			while (true) {
	
				try {
					//Update all Goblins and then sleep
					for(Goblin g : goblins) {
						if(g.alive) {
							g.fall(40);
							g.runToKnight();
							if(g.inAttackDistance()&&!g.attacking) {
								new AttackThread(g).start();
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
