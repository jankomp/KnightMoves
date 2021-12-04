package logic;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import graphic.MyLevelPanel;
import logic.characters.Knight;

public class Mondo {
	private int x;
	private int y;
	private int dimY = 100;
	private int dimX = 10000;
	private Image background_outside = loadAssets("sky_background.png");
	private Image floor_1 = loadAssets("grass_floor.png");
	private Image under_floor_1 = loadAssets("earth_floor.png");
	private Image[] background_inside = new Image[10];
	private Image floor_2 = loadAssets("castle_floor.png");
	private Image floor_2_high = loadAssets("castle_floor_high.png");
	private Image under_floor_2 = loadAssets("rock_floor.png");
	private Image door_0 = loadAssets("door0.png");
	private Image door_1 = loadAssets("door1.png");
	
	
	//private Set <PhysicalObject> physicalObjects1 = new HashSet<PhysicalObject>();
	private Knight knight;
	
	public void setKnight (Knight k) {
		this.knight = k;
	}
	
	public Knight getKnight () {
		return this.knight;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getdimX() {
		return this.dimX;
	}
	
	public int getdimY() {
		return this.dimY;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public Image getBackground() {
		return this.background_outside;
	}
	
	public Image getFloor() {
		return this.floor_1;
	}
	
	public void drawWorldFrom(int x, Graphics g, MyLevelPanel p) {
		int loop = 1400;
		int loopAt = 0;	
		
		
		g.drawImage(background_outside, 5000 - x, 200, 1400, 700, p);
		for(int i = 0; i < 4; i++){
			g.drawImage(background_outside, loopAt-x, 0, 1400, 700, p);
		if(i == 3) {
			g.drawImage(under_floor_1, loopAt - x, 600, 1050, 200, p);
			g.drawImage(floor_1, loopAt-x, 500, 1050, 200, p);
		} else {
			
			g.drawImage(floor_1, loopAt-x, 600, 1400, 200, p);
		}
		loopAt += loop;
		}	
		int underloop = 5600;
		for(int i = 0; i < 9; i++) {
			g.drawImage(background_inside[8], underloop-x, 220, 1000, 600, p);
			underloop += 1000;
		}
			//draw the castle backgrounds
			g.drawImage(background_outside, 5600 - x, 0, 1400, 700, p);	
			g.drawImage(background_inside[4], 6600-x, 0, 1000, 600, p);
			g.drawImage(background_inside[6], 7600-x, 0, 1000, 600, p);
			g.drawImage(background_inside[3], 8600-x, 0, 1000, 600, p);
			g.drawImage(background_inside[2], 9600-x, 0, 1000, 600, p);
			g.drawImage(background_inside[2], 10600-x, 0, 1000, 600, p);
			g.drawImage(background_inside[8], 11600-x, 0, 1000, 600, p);
			g.drawImage(background_inside[9], 12600-x, 0, 1000, 600, p);
			g.drawImage(background_inside[8], 13600-x, 0, 1000, 600, p);
			
			g.drawImage(door_1, 13600 - x, 330, 120, 170, p);
			g.drawImage(door_0, 10050 - x, 230, 120, 170, p);
			
			g.drawImage(background_outside, 14600 - x, 0, 1400, 700, p);
			g.drawImage(background_outside, 14600 - x, 200, 1400, 700, p);
			
			//draw all the castle floors
			
			g.drawImage(floor_2, 5600-x, 600, 1000, 200, p);
			
			
			g.drawImage(floor_2, 6600-x, 600, 1000, 200, p);
			
			
			g.drawImage(floor_2_high, 7600-x, 500, 400, 400, p);
			g.drawImage(floor_2_high, 8000-x, 400, 400, 400, p);
			
			loopAt = 8600;
			for(int i = 0; i < 3; i++) {
				g.drawImage(floor_2_high, loopAt-x, 400, 400, 400, p);
				loopAt += 400;
			}
			g.drawImage(floor_2_high, 10000-x, 400, 400, 400, p);
			g.drawImage(floor_2_high, 10600-x, 400, 400, 400, p);
			
			loopAt = 11000;
			for(int i = 0; i < 3; i++) {
				g.drawImage(floor_2_high,loopAt-x, 600, 400, 400, p);
				loopAt += 400;
			}
			
			g.drawImage(floor_2_high, 12400-x, 600, 400, 400, p);
			
			g.drawImage(floor_2_high, 13000-x, 600, 400, 400, p);
			g.drawImage(floor_2_high, 13400-x, 500, 400, 400, p);
			g.drawImage(floor_2_high, 14000-x, 500, 400, 400, p);
			g.drawImage(floor_2_high, 14400-x, 600, 400, 400, p);
			
			
			loopAt = 15000;
			for(int i = 0; i < 4; i++){
					g.drawImage(background_outside, loopAt-x, 0, 1400, 700, p);
				if(i == 3) {
					
					g.drawImage(under_floor_1, loopAt - x, 600, 1050, 200, p);
					g.drawImage(floor_1, loopAt-x, 500, 1050, 200, p);
					
				} else {
					
					g.drawImage(floor_1, loopAt-x, 600, 1400, 200, p);
				}
				loopAt += loop;
			}
			
			
	}
	
	/*public boolean collisionDetector(int x, int y, int imgsize, Direction d) {
		for(PhysicalObject po: physicalObjects1) {
			try {
			if(po.collision(x, x + imgsize, y, y + imgsize, d)) {
				return true;
			}
			} catch(ConcurrentModificationException e) {
				
			}
		}
		return false;
	}*/
	
	public Mondo() {
		
		for(int i = 1; i < 10; i++) {
			background_inside[i] = loadAssets("castle_background_0" + i + ".png");
		}
	}

	public Image loadAssets(String path) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		URL url = this.getClass().getResource(path);
		Image img = tk.getImage(url);
		
		return img;
	}
	
	

}