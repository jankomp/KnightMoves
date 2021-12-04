package logic.characters;

import java.awt.Graphics;
import java.awt.Image;

import graphic.MyLevelPanel;
import logic.GroundLevel;
import logic.Mondo;
import logic.Walls;

public class Archer extends Character implements Enemy {
	public String name;
	private int imageSize = 140;
	
	private int startingPoint;
	private int worldx;
	private int framex;
	private int framey;
	private int watchrange;
	
	public boolean alive = true;
	public boolean hurting = false;
	public boolean attacking = false;
	public boolean facingRight = false;
	private boolean coinPickedUp = false;
	
	private Knight k;
	private ArrowThread arrt;
	
	private int life = 3;
	private int runningSpeed = 4;
	
	private Image currentImage = loadAssets("archer_idle_left.gif");
	
	private Image idleRight = loadAssets("archer_idle_right.gif");
	private Image idleLeft = loadAssets("archer_idle_left.gif");
	private Image runRight = loadAssets("archer_walk_right.gif");
	private Image runLeft = loadAssets("archer_walk_left.gif");
	private Image attackRight[] = new Image[5];
	private Image attackLeft[] = new Image[5];
	private Image hurtRight = loadAssets("archer_hurt_right.png");
	private Image hurtLeft = loadAssets("archer_hurt_left.png");

	
	public Archer(int x, MyLevelPanel p, ArcherThread arct, int watchrange) {
		this.worldx = this.startingPoint = x;
		framey = 50;
		k = p.getKnight();
		arct.addArcher(this);
		this.arrt = arct.arrt;
		this.watchrange = watchrange;
		
		name = "Archer spawned at: " + x;
		
		//Resize Images
		idleLeft = idleLeft.getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT);
		idleRight = idleRight.getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT);
		runLeft = runLeft.getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT);
		runRight = runRight.getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT);
		hurtLeft = hurtLeft.getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT);
		hurtRight = hurtRight.getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT);
		
		//Load Single Attack Images
			for(int i = 0; i < 5; i++) {
			attackLeft[i] = loadAssets("archer_attack_left_" + i + ".png").getScaledInstance(imageSize,  imageSize , Image.SCALE_DEFAULT);
			attackRight[i] = loadAssets("archer_attack_right_" + i + ".png").getScaledInstance(imageSize,  imageSize, Image.SCALE_DEFAULT);
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
	
	public void walk() {
	if(!attacking && ! hurting) {
		if (facingRight) {
			runRight();
			if((worldx >= startingPoint)) {
				facingRight = false;
				}
		} else {
			runLeft();
			if((worldx <= startingPoint - watchrange)) {
				facingRight = true;}
		}
	}
	}
	
	public boolean spotKnight(){
		return (facingRight && knightCloseOnRight(600))||(!facingRight && knightCloseOnLeft(600));
	}
	
	public void attack(int i) {
		if(!k.dead) {
			if(!hurting) {
				if(facingRight) {	
					currentImage = attackRight[i];
					} else {	
					currentImage = attackLeft[i];
					}
			
				//Hit the Knight
				if(i == 2) {
					shootArrow();
					}
			}
		}
	}
	
	private void shootArrow () {
		arrt.addArrow(new Arrow(worldx + imageSize/2, framey + 92, facingRight, k));
	}
	
	private boolean knightCloseOnLeft(int dmax) {
		int leftDistance = worldx - k.getRightPoint();
		return (!facingRight && leftDistance < dmax && leftDistance > 0);
	}
	
	private boolean knightCloseOnRight(int dmax) {
		int rightDistance = k.getLeftPoint() - worldx - imageSize;
		return (facingRight && rightDistance < dmax && rightDistance > 0);
	}
	
	private void runLeft() {
		if(!Walls.collision(worldx - runningSpeed, framey + imageSize)) {
			worldx -= runningSpeed;
			
			currentImage = runLeft;
			
			facingRight = false;
		}
	}
	
	private void runRight() {
		if(!Walls.collision(worldx + runningSpeed  + imageSize, framey + imageSize)) {
			worldx += runningSpeed;
			
			currentImage = runRight;
			
			facingRight = true;
		}
	}

	public int getLeftPoint() {return worldx;}
	public int getRightPoint() {return worldx + imageSize;}
	public int getHighestPoint() {return framey;}
	public int getLowestPoint() {return this.framey + imageSize;}

	@Override
	public void visualizeHurting() {
		if(facingRight) {
			currentImage = hurtRight;
			}else {
			currentImage = hurtLeft;
			}		
	}
	
	@Override
	public void hurt() {
		life--;
		new HurtThread(this).start();
		
		if(life <= 0) {
			die();
		}
	}
	
	public void die() {
		alive = false;
	}
	
	//Interaction of a attack-thread and the archer
		public void startAttack() {attacking = true;}
		public void endAttack() {attacking = false;}
		public boolean attacks() {return attacking;}
		
	//Interaction of a hurt-thread and the archer
	public void startHurting() {hurting = true;}
	public void endHurting() {hurting = false;}
	public boolean hurting() {return hurting;}
	
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

	public void drawArcher (Graphics g, MyLevelPanel p) {
		if(alive) {
			framex = worldx - p.getCameraX();
			g.drawImage(currentImage, framex, framey, p);
		}else if(!coinPickedUp){
			framex = worldx - p.getCameraX();
			coin.drawCoin(g, p, framex, framey);
		}
	}
	
	
}
