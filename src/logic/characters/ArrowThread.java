package logic.characters;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import graphic.MyLevelPanel;

public class ArrowThread extends Thread {
public ArrayList<Arrow> arrows = new ArrayList<Arrow>();

public void addArrow(Arrow a) {
	arrows.add(a);
}

public void run() {
	while(true) {
		for(Arrow a: arrows){
			if(a.visible) {
				a.fly();
			}
		}
		try {sleep(50);}
		catch(Exception e) {System.out.println(e);}
	}
}


}
