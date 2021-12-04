package logic.characters;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Graphics;

import graphic.MyLevelPanel;
import logic.GroundLevel;
import logic.LifePoints;
import logic.Mondo;
import logic.Walls;
import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class Knight extends Character {
	
	private int imageSize = 140;
	
	private Mondo m;
	
	private MyLevelPanel p;
	
	private static AudioPlayer AP = AudioPlayer.player;
	
	//sounds
/*private File hurtSoundFile = new File("hurt.wav");
private static Clip hurt = null;
private static Clip attack = null;
private static Clip jump = null;*/
	
	//Action-Images
private Image idleLeft = loadAssets("knight_idle_left.gif");
private Image idleRight = loadAssets("knight_idle_right.gif");
private Image runLeft = loadAssets("knight_run_left.gif");
private Image runRight = loadAssets("knight_run_right.gif");
private Image hurtLeft = loadAssets("knight_hurt_left.png");
private Image hurtRight = loadAssets("knight_hurt_right.png");
private Image [] attackLeft = new Image[10];
private Image [] attackRight = new Image[10];
private Image [] dieLeft = new Image[8];
private Image [] dieRight = new Image[8];

private ArrayList<Goblin> enemies1 = GoblinThread.goblins;
private ArrayList<Archer> enemies2 = ArcherThread.archers;

private Image currentImage = idleRight.getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT);
	
	//The Healthpoints of the Knight
private LifePoints life;

private int coins = 0;

	//The Position of the Knight on the frame (framex, framey) and in the World (worldx, worldy)
private int framex;
private int framey;
private int worldx;

	//is the knight jumping or not?
private boolean jumps = false;

//is the knight attacking
private boolean isAttacking = false;

 //which direction is the Knight facing?
private boolean facingRight = true;

//is the Knight dead?
public boolean dead = false;

public boolean hurting = false;

public int getLeftPoint() {return this.worldx;}
public int getRightPoint() {return worldx + imageSize;}
public int getHighestPoint() {return this.framey;}
public int getLowestPoint() {return this.framey + imageSize;}
public int getCoins() {return this.coins;}


	//Physical Stats for the Actions the Knight can perform
private int runningSpeed = 40;

	//Constructor which sets the Knight into some Point of the World
public Knight(int x, Mondo m, LifePoints l, MyLevelPanel p) {
	this.m = m;
	life = l;
	this.worldx = this.framex = x;
	this.framey = 460;
	this.p = p;
		
	//Resize Images
	idleLeft = idleLeft.getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT);
	idleRight = idleRight.getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT);
	runLeft = runLeft.getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT);
	runRight = runRight.getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT);
	hurtLeft = hurtLeft.getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT);
	hurtRight = hurtRight.getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT);
	
	//Load Single Attack Images
	for(int i = 0; i < 10; i++) {
		attackLeft[i] = loadAssets("knight_attack_left_" + i + ".png").getScaledInstance(168,  imageSize, Image.SCALE_DEFAULT);
		attackRight[i] = loadAssets("knight_attack_right_" + i + ".png").getScaledInstance(168,  imageSize, Image.SCALE_DEFAULT);
	}
	for(int i = 0; i < 8; i++) {
		//Load Single Death Images
		dieLeft[i] = loadAssets("knight_die_left_" + i + ".png").getScaledInstance(168,  imageSize, Image.SCALE_DEFAULT);
		dieRight[i] = loadAssets("knight_die_right_" + i + ".png").getScaledInstance(168,  imageSize, Image.SCALE_DEFAULT);
	}
}

public void drawKnight (Graphics g, MyLevelPanel p) {
	g.drawImage(currentImage, framex, framey, p);
	
	if(worldx == 19400) {
		dead = true;
		currentImage = idleRight;
		p.gameOver();
	}
}

//Method to run to the right
public void runRight() {
	if(!dead) {
	if(!Walls.collision(worldx + runningSpeed + imageSize, framey + imageSize)) {
		//first if: move the knight only to the right until the middle of the screen, else move the background to the left by increasing the x koordinate of the camera
		if(framex < 500){
			//second if: update position of the knight only as long as that doesn't mean a collision
			//if(!m.collisionDetector(worldx + runningSpeed, framey, imageSize, Direction.RIGHT)) {
				framex += runningSpeed;	
				worldx += runningSpeed;
			//}
		} else {
			framex = 500;
			//second if: update position of the knight only as long as that doesn't mean a collision
			//if(!m.collisionDetector(worldx + runningSpeed, framey, imageSize, Direction.RIGHT)) {
				//third if: just move the camera to the beginning of the world, not further, because there isn't painted anything
				if (p.increaseCameraX(runningSpeed)) {worldx += runningSpeed;}
			//}
		}
		
		
			currentImage = runRight;
			facingRight = true;
			
			//Pick up coins
			if (!enemies1.isEmpty()) {
				for(Enemy e : enemies1) {
					e.pickUpCoin();
				}
			}
			if (!enemies2.isEmpty()) {
				for(Enemy e : enemies2) {
					e.pickUpCoin();
				}
			}
		}
	}
}

//Method to run to the left
public void runLeft() {
	if(!dead) {
	if(!Walls.collision(worldx - runningSpeed, framey + imageSize)) {
		//first if: move the knight only to the left until some point of the screen, else move the background to the right by decreasing the x koordinate of the camera
		if(framex > 200){
			//second if: update position of the knight only as long as that doesn't mean a collision
			
				framex -= runningSpeed;
				worldx -= runningSpeed;
			
		} else {
			framex = 200;
			//second if: update position of the knight only as long as that doesn't mean a collision
			
				//third if: just move the camera to the beginning of the world, not further, because there isn't painted anything
				if(p.increaseCameraX(-runningSpeed)){worldx -= runningSpeed;}
			
		}
	
		
			currentImage = runLeft;
			facingRight = false;
			
			//Pick up coins
			if (!enemies1.isEmpty()) {
				for(Enemy e : enemies1) {
					e.pickUpCoin();
				}
			}
			if (!enemies2.isEmpty()) {
				for(Enemy e : enemies2) {
					e.pickUpCoin();
				}
			}
		}
	}
}

//Change Picture to idle
public void idle () {
	if(!dead) {
		if(facingRight) {
			currentImage = idleRight;
				}
		 else if(!facingRight){
			currentImage = idleLeft;
		}
	}
}



public void fall (int dy) {
	//fall (increase y) only if that doesn't mean a collision
	if(framey + imageSize + dy < GroundLevel.getGround(worldx + imageSize/2)) {
		framey = framey + dy;
		
	} else {
	//else increase y as much as possible without collision in one step
		while(framey + imageSize + 1 < GroundLevel.getGround(worldx + imageSize/2)) {
			framey++;
		}
		
	}
	
	if(framey>1200) {
		new DieThread(this).start();
	}
}

public synchronized void attack (int i) {
	if(!dead) {
		if (facingRight) {
				currentImage =  attackRight[i];
			if(i == 5) {
					if (!enemies1.isEmpty()) {
						for(Enemy e : enemies1) {
							if(e.getLeftPoint() < worldx +imageSize + 50 && e.getLeftPoint() > worldx + imageSize - 200) {
								if(e.getHighestPoint() > framey - 50 && e.getLowestPoint() < framey + imageSize + 50) {
									e.hurt();
								}
							}
						}
					}
					if (!enemies2.isEmpty()) {
						for(Enemy e : enemies2) {
							if(e.getLeftPoint() < worldx +imageSize + 50 && e.getLeftPoint() > worldx + imageSize - 200) {
								if(e.getHighestPoint() > framey - 50 && e.getLowestPoint() < framey + imageSize + 50) {
									e.hurt();
								}
							}
						}
					}
			}
		} else {
				currentImage = attackLeft[i];
				if(i == 5) {
					if (!enemies1.isEmpty()) {
						for(Enemy e : enemies1) {
							if(e.getLeftPoint() < worldx +imageSize + 50 && e.getRightPoint() > worldx + imageSize - 200) {
								if(e.getHighestPoint() > framey - 50 && e.getLowestPoint() < framey + imageSize + 50) {
									e.hurt();
								}
							}
						}
					}
					if (!enemies2.isEmpty()) {
						for(Enemy e : enemies2) {
							if(e.getLeftPoint() < worldx +imageSize + 50 && e.getRightPoint() > worldx + imageSize - 200) {
								if(e.getHighestPoint() > framey - 50 && e.getLowestPoint() < framey + imageSize + 50) {
									e.hurt();
								}
							}
						}
					}
			}
		}
		//Play the sound for the attack
		
	}
}


public boolean isOnGround() {
	//look if 1 pixel under the knight is a collision, ergo he's on the ground
	return !(framey + imageSize + 1 < GroundLevel.getGround(worldx));
}

public void decreaseY (int dy) {
	//decreases y-Coordinate (for Jump)
	framey = framey - dy;
	
}

//Interaction of a attack-thread and the knight
public void startAttack() {isAttacking = true;}
public void endAttack() {isAttacking = false;}
public boolean attacks() {return isAttacking;}


//Interaction of a jump-thread and the knight
public void startJump() {jumps = true;}
public void endJump() {jumps = false;}
public boolean jumps() {return jumps;}

//Interaction of a hurt-thread and the knight
public void startHurting() {hurting = true;}
public void endHurting() {hurting = false;}
public boolean hurting() {return hurting;}


public void visualizeHurting() {
	if(facingRight) {
	currentImage = hurtRight;
	}else {
	currentImage = hurtLeft;
	}
}

public void hurt() {
	if(!dead) {
		life.removeContainer();
	new HurtThread(this).start();
	//Play the Sound
	}
}

public void die(int i) {
	if(i == 0) {
	runningSpeed = 0;
	dead = true;
	}
	if(facingRight) {
			currentImage = dieRight[i];
			framex -= 10;
	}else {
			currentImage = dieLeft[i];
			framex += 10;
	}
}

public void addCoin() {
	//count the coins the knight collected
	coins++;
}

/*private void playSound(Clip clip){
	try {
	hurt = AudioSystem.getClip();
	hurt.open(AudioSystem.getAudioInputStream(hurtSoundFile));}
	catch (Exception e) {System.out.println(e);}
	clip.start();
}*/

}
