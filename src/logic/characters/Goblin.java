package logic.characters;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import graphic.MyLevelPanel;
import logic.GroundLevel;
import logic.Mondo;
import logic.Walls;

public class Goblin extends Character implements Enemy{
	String name;
	
	public int life = 3;
	public boolean alive;
	public boolean facingRight = false;
	public boolean attacking = false;
	public boolean hurting = false;
	private boolean coinPickedUp = false;
	
	int worldx;
	int framex;
	int framey;
	
	int imageSize = 110;
	
	int runningSpeed = 8;
	
	MyLevelPanel p;
	Mondo m;
	Knight k;
	
	// Pictures for the Goblin
	private Image runLeft = loadAssets("goblin_run_left.gif");
	private Image runRight = loadAssets("goblin_run_right.gif");
	private Image[] attackLeft = new Image[6];
	private Image[] attackRight = new Image[6];
	private Image idleLeft = loadAssets("goblin_idle_left.gif");
	private Image idleRight = loadAssets("goblin_idle_right.gif");
	private Image hurtLeft = loadAssets("goblin_hurt_left.png");
	private Image hurtRight = loadAssets("goblin_hurt_right.png");
	
	private Image currentImage = idleLeft.getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT);
	
	
	public int getLeftPoint() {return this.worldx;}
	public int getRightPoint() {return worldx + imageSize;}	
	public int getHighestPoint() {return this.framey;}
	public int getLowestPoint() {return this.framey + imageSize;}
	
	public Goblin(int x, Mondo m, MyLevelPanel p, GoblinThread gt) {
		worldx = x;
		framex = worldx;
		framey = 150;
		this.p = p;
		this.m = m;
		this.k = p.getKnight();
		alive = true;
		name = "Goblin spawned at: " + x;
		
		gt.addGoblin(this);
		
		//Resize Images
		idleLeft = idleLeft.getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT);
		idleRight = idleRight.getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT);
		runLeft = runLeft.getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT);
		runRight = runRight.getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT);
		hurtLeft = loadAssets("goblin_hurt_left.png").getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT);
		hurtRight = loadAssets("goblin_hurt_right.png").getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT);
		
		//Load Single Attack Images
		for(int i = 0; i < 6; i++) {
			attackLeft[i] = loadAssets("goblin_attack_left_" + i + ".png").getScaledInstance(imageSize,  imageSize , Image.SCALE_DEFAULT);
			attackRight[i] = loadAssets("goblin_attack_right_" + i + ".png").getScaledInstance(imageSize,  imageSize, Image.SCALE_DEFAULT);
			}
	}
	
	public void runToKnight() {
		if(knightCloseOnLeft(1000, -50) && !attacking) {
			runLeft();
		} else {
			idle();
		}
		if(knightCloseOnRight(1000, -50) && !attacking) {
			runRight();
		}
	}
	
	public boolean knightCloseOnLeft(int dmax, int dmin) {
		int leftDistance = worldx - k.getRightPoint();
		return (leftDistance > dmin && leftDistance < dmax);
	}
	
	public boolean knightCloseOnRight(int dmax, int dmin) {
		int rightDistance = k.getLeftPoint() - worldx - imageSize;
		return (rightDistance > dmin && rightDistance < dmax);
	}
	
	public void idle() {
		if(!attacking && !hurting) {
			if(facingRight) {
				currentImage = idleRight;
			} else {
				currentImage = idleLeft;
			}
		}
	}
	
	public boolean inAttackDistance() {
		return knightCloseOnRight(-50, -80) || knightCloseOnLeft(-50, -80);
	}
	
	public void attack(int i) {
		if(!k.dead) {
			if(!hurting) {
				if(facingRight) {	
					currentImage = attackRight[i];
					//Hit the Knight
					if(i == 3) {
						if(k.getLeftPoint() < worldx + imageSize - 50 && k.getLeftPoint() > worldx + imageSize - 80) {
							if(k.getHighestPoint() > framey - 50 && k.getHighestPoint() < framey + imageSize) {
								k.hurt();
							}
						}
					}
				} else {	
					currentImage = attackLeft[i];
					//Hit the Knight
					if(i == 3) {
						if(k.getRightPoint() < worldx + 80 && k.getRightPoint() > worldx + 50) {
							if(k.getHighestPoint() > framey - 50 && k.getHighestPoint() < framey + imageSize) {
								k.hurt();
							}
						}
					}
				}
			}	
		}
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
	
	public void hurt() {
		life--;
		new HurtThread(this).start();
		if(life <= 0) {
			die();
		}
	}
	
	@Override
	public void visualizeHurting() {
		if(facingRight) {
			currentImage = hurtRight;
			}else {
			currentImage = hurtLeft;
			}		
	}
	
	public void die() {
		alive = false;
	}
	
	private void runLeft() {
		if(!Walls.collision(worldx - runningSpeed, framey + imageSize)) {
			worldx -= runningSpeed;
			
			currentImage = runLeft;
			
			facingRight = false;
		}
	}
	
	private void runRight() {
		if(!Walls.collision(worldx + runningSpeed + imageSize, framey + imageSize)) {
			worldx += runningSpeed;
			
			currentImage = runRight;
			
			facingRight = true;
		}
	}
	
	//Interaction of a attack-thread and the goblin
	public void startAttack() {attacking = true;}
	public void endAttack() {attacking = false;}
	public boolean attacks() {return attacking;}
	
	//Interaction of a hurt-thread and the goblin
	public void startHurting() {hurting = true;}
	public void endHurting() {hurting = false;}
	public boolean hurting() {return hurting;}
	
	public void drawGoblin (Graphics g, MyLevelPanel p) {
		if(alive) {
		framex = worldx - p.getCameraX();
		g.drawImage(currentImage, framex, framey, p);
		} else if(!coinPickedUp){
			framex = worldx - p.getCameraX();
			coin.drawCoin(g, p, framex, framey);
		}
	}
	
	public void pickUpCoin() {
		if(!alive && !coinPickedUp) {
			if((k.getHighestPoint() >= framey && k.getHighestPoint() <= framey + imageSize) ||
				(k.getLowestPoint() >= framey && k.getLowestPoint() <= framey + imageSize) ||
				(k.getHighestPoint() <= framey && k.getLowestPoint() >= framey + imageSize)) {
				if((k.getLeftPoint() >= worldx && k.getLeftPoint() <= worldx + imageSize)	||
					(k.getRightPoint() >= worldx && k.getRightPoint() <= worldx + imageSize)) {
					coinPickedUp = true;
					k.addCoin();
				}	
			}
		}
	}
	
	public String toString() {
		return name;
	}
}
