package logic.characters;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import graphic.MyLevelPanel;

public class Arrow {
	private Image leftArrow = loadAssets("arrow_left.png").getScaledInstance(40, 7, Image.SCALE_DEFAULT);
	private Image rightArrow = loadAssets("arrow_right.png").getScaledInstance(40, 7, Image.SCALE_DEFAULT);
	private Image arrow;
	
	private int positionx;
	private int positiony;
	private int startx;
	
	private int arrowSpeed = 30;
	private int range = 1000;
	
	public boolean visible = false;
	private boolean right = false;
	
	private Knight k;
	
	public Arrow(int startx, int starty, boolean right, Knight k) {
		this.startx = positionx = startx;
		positiony = starty;
		this.k = k;
		this.right = right;
		visible = true;
		
		
		if(right) {
			arrow = rightArrow;
		} else {
			arrow = leftArrow;
		}
	}
	
	public void fly() {
		if(right) {
			positionx += arrowSpeed;
		} else if (!right) {
			positionx -= arrowSpeed;
		}
		
		if(Math.abs(positionx - startx) > range) {
			visible = false;
		}
		
		if(k.getLeftPoint() <= positionx && k.getRightPoint() >= positionx) {
			if(k.getHighestPoint() <= positiony && k.getLowestPoint() >= positiony) {
				hitKnight();
			}
		}
	}
	
	private void hitKnight(){
		visible = false;
		k.hurt();
	}
	
	
	public void paintArrow(Graphics g, MyLevelPanel p) {
		if(visible) {
			g.drawImage(arrow, positionx - p.getCameraX(), positiony, p);
		}
	}
	
	private Image loadAssets(String path) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		URL url = this.getClass().getResource(path);
		Image img = tk.getImage(url);
		
		return img;
	}
}
