package logic.characters;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import logic.GroundLevel;

public abstract class Character {
 private boolean attacking;
 public boolean hurting;
 private Image currentImage = null;
 private int imageSize;
 
 private int worldx;
 private int framey;

	public void idle() {};
	public void hurt() {};
	public void visualizeHurting() {};
	
	public void startAttack() {	attacking = true;};
	public void endAttack() {attacking = false;};
	public boolean attacking() {return attacking;}
	
	//Interaction of a hurt-thread and the character
	public void startHurting() {hurting = true;}
	public void endHurting() {hurting = false;}
	public boolean hurting() {return hurting;}
	
	public void attack(int i) {
		currentImage = null;
	}
	
	public void fall(int dy) {
		if(framey + imageSize + dy < GroundLevel.getGround(worldx + imageSize/2)) {
			framey += dy; 
		} else {
			while(framey + imageSize + 1 < GroundLevel.getGround(worldx + imageSize/2)) {
				framey++;
			}
		}
	}
	
	public Image loadAssets(String path) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		URL url = this.getClass().getResource(path);
		Image img = tk.getImage(url);
		
		return img;
	}

}
