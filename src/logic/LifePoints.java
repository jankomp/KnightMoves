package logic;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import graphic.MyLevelPanel;
import logic.characters.DieThread;
import logic.characters.Knight;

public class LifePoints {
	private Image Heart = loadAssets("pixel-art-heart.png").getScaledInstance(50,  50, Image.SCALE_DEFAULT);;
	private int actualContainers = 8;
	private MyLevelPanel p;
	private Knight k;
	
	public LifePoints(MyLevelPanel p) {
		this.p = p;
	}
	
	public void setKnight(Knight k) {
		this.k = k;
	}
	
	public void displayContainers(Graphics g) {
		for(int i = 0; i < actualContainers; i++) {
			g.drawImage(Heart, 50 + i*70, 50, p);
			}
	}
	
	public void removeContainer() {
		actualContainers--;
		if(actualContainers == 0){new DieThread(k).start();}
		
	}
	
	public void addContainer() {
		actualContainers++;
	}
	
	public Image loadAssets(String path) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		URL url = this.getClass().getResource(path);
		Image img = tk.getImage(url);
		
		return img;
	}
}
