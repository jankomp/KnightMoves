package logic;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import graphic.MyLevelPanel;

public class Coin {
	private Image spinCoin = loadAssets("spinning_coin.gif").getScaledInstance(200, 200, Image.SCALE_DEFAULT);;
	
	public void drawCoin(Graphics g, MyLevelPanel p, int x, int y) {
		y -= 20;
		g.drawImage(spinCoin, x, y, p);
	}
	
	
	public Image loadAssets(String path) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		URL url = this.getClass().getResource(path);
		Image img = tk.getImage(url);
		
		return img;
	}
}
