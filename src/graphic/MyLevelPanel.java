package graphic;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Formatter;
import java.util.Scanner;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import logic.LifePoints;
import logic.Mondo;
import logic.characters.Archer;
import logic.characters.ArcherThread;
import logic.characters.Arrow;
import logic.characters.AttackThread;
import logic.characters.Goblin;
import logic.characters.GoblinThread;
import logic.characters.JumpThread;
import logic.characters.Knight;
import logic.characters.KnightMoveThread;
import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;


public class MyLevelPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private ArcherThread at = new ArcherThread();
	private GoblinThread gt = new GoblinThread();
	private MyLevelPanel p = this;

private int camerax = 0;
private Mondo m = new Mondo();
public boolean gameOver = false;

private LifePoints life = new LifePoints(this);
private Knight knight = new Knight(200, m, life,this);

private Goblin goblin4 = new Goblin(1400, m, this, gt);
private Goblin goblin2 = new Goblin(4800, m, this, gt);
private Goblin goblin3 = new Goblin(4600, m, this, gt);
private Goblin goblin1 = new Goblin(6000, m, this, gt);
private Goblin goblin5 = new Goblin(12000, m, this, gt);
private Goblin goblin6 = new Goblin(2800, m, this, gt);
private Archer archer1 = new Archer(10880, this, at, 250);
private Archer archer2 = new Archer(9400, this, at, 600);
private Archer archer3 = new Archer(8280, this, at, 250);
private Archer archer4 = new Archer(12700, this, at, 300);
private Archer archer5 = new Archer(13600, this, at, 150);
private Archer archer6 = new Archer(14320, this, at, 250);

private Goblin goblin7 = new Goblin(16000, m, this, gt);
private Goblin goblin8 = new Goblin(17400, m, this, gt);
private Goblin goblin9 = new Goblin(19000, m, this, gt);
private Archer archer7 = new Archer(16300, this, at, 400);
private Archer archer8 = new Archer(18200, this, at, 600);
private Archer archer9 = new Archer(19000, this, at, 250);

private KnightMoveThread kmt = new KnightMoveThread(knight);
private RepaintThread rp = new RepaintThread(this);

JButton highScoresButton = new JButton("Highscores");
JLabel noteText = new JLabel("You have to collect all 18 coins to be able to enter the Highscore-List");
private boolean displayingHighscore = false;

private int gravity = 7; 
private long startTime;
private long endTime;

protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		m.drawWorldFrom(camerax, g, this);
		knight.drawKnight(g, this);
		for(Goblin h: gt.goblins) {
			h.drawGoblin(g, this);
		}
		for(Archer a: at.archers) {
			a.drawArcher(g, this);
		}
		for(Arrow a: at.arrt.arrows) {
			a.paintArrow(g, this);
		}
		life.displayContainers(g);
		
		//Gravity
		if(!knight.jumps()) {
			if(!knight.isOnGround()) {
				knight.fall(gravity);
			}
		}
}


public MyLevelPanel() {
	super();
	initGui();
	initEH();
	life.setKnight(knight); //so the knight can die if the hearts are finished
	startTime = System.currentTimeMillis(); //startTime, to calculate the total time for the completion of the level later
	highScoresButton.setVisible(false);
	noteText.setVisible(false);
	
}

public void initGui(){
	//initialize the panel and start the threads
	this.setFocusable(true);
	this.setSize(1400,800);
	rp.start(); //thread that calls repaint() every 50ms
	gt.start(); //thread that updates goblins every 50ms
	at.start(); //thread that updates archers every 50ms
	kmt.start(); //thread that updates the knights movement every 50ms
	}

public void initEH() {
	//initialize all the interactions a player can make
	this.addKeyListener (new KeyAdapter() {
		
		
		@Override
		public synchronized void keyPressed(KeyEvent e) {	
				int c = e.getKeyCode();
				if(c == KeyEvent.VK_RIGHT) {
					//start moving right
					kmt.right();
					kmt.startMove();
					
				} else if (c == KeyEvent.VK_LEFT) {
					//start moving left
					kmt.left();
					kmt.startMove();
					
				} else if (c == KeyEvent.VK_F) {
					// if: only attack if the knight isn't already attacking
					if(!knight.attacks()) {
					AttackThread a = new AttackThread(knight);
					a.start();
					}
				}
				
				// First if: only if the knight is on the ground
				if(c == KeyEvent.VK_SPACE && knight.isOnGround()) {	
					// Second if: only jump if the knight isn't already jumping (redundant but i'll keep it)
					if(!knight.jumps() && !knight.dead) {
					JumpThread j = new JumpThread(knight);
					j.start();
					}
				}
				
		}
		
		public synchronized void keyReleased(KeyEvent e) {
			int c = e.getKeyCode();
			if(c == KeyEvent.VK_RIGHT) {
				//stop moving right
				kmt.endMove();
				knight.idle();
			} else if (c == KeyEvent.VK_LEFT) {
				//stop moving left
				kmt.endMove();
				knight.idle();
			}
		}
		
	});
	
	highScoresButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(!displayingHighscore) {
			//when the HighScoresButton is pressed
			
			//read all the highscores into the array highscores
			Scanner x;
			//Make an array where each unit consists in a String type of: xx.xxxs - name
			 String[] highscores = new String[3];
			 //read all the highscore entries from the file highscores, to diplay/manipulate them later
			try {
				x = new Scanner(new File("highscores.txt"));
				int u = 0;
				while(x.hasNext() && u < highscores.length) {
					highscores[u++] = x.nextLine();
				}
				x.close();
			} catch(Exception j) {System.out.println(j + "in the first trycatch-block");}
			
			
		if(knight.getCoins() == 18) {
			//if enough coins have been collected
			 try {
			 Formatter y;
			 int i = 2;
			 int position = 3;
			 double[] highscoreTimes = readDoubleArrayOutOfStringArray(highscores);
			 
			 
			 //detect the position in which the new highscore will be entered (to keep the order of the list)
			 while(i >= 0) {
				 if(highscoreTimes[i] >= getTime()) {
					 position = i;
				 }
				 i--;
			 }
			 
			 if(position < 3) {
				//PopUp to enter the name (but only if the Time is good enough)
				 String name = JOptionPane.showInputDialog(p,
	             "What is your name?", null);
				 
				  //If the players Time was good enough to enter the highscoreList we have to move the other scores down, for that we have to make a copy of the list
				 String[] moveHighScores = new String[3];
				 int b = 0;
				 for (i = position; i < 3; i++) {
					 moveHighScores[b++] = highscores[i];
				 }
				 //enter the new time into the list, with the name entered by the player
				 highscores[position] = getTime() + "s - " + name;
				 b = 0;
				 //move all the other entries of the List by 1 place
				 for (i = position + 1; i < 3; i++) {
					 highscores[i] = moveHighScores[b++];
				 }
				 //Write the new HighscoreList into the file highscores.txt
					 y = new Formatter("highscores.txt");
					 y.format("%s\n%s\n%s", highscores[0], highscores[1], highscores[2]);
					 y.close();
			 }//if(position < 3)
			 
			 
				JLabel highscore0 = new JLabel("HIGHSCORES:");
				JLabel highscore1 = new JLabel(highscores[0]);
				JLabel highscore2 = new JLabel(highscores[1]);
				JLabel highscore3 = new JLabel(highscores[2]);
				highscore0.setBounds(620, 180, 150, 20);
				highscore1.setBounds(600, 200, 200, 20);
				highscore2.setBounds(600, 220, 200, 20);
				highscore3.setBounds(600, 240, 200, 20);
				p.add(highscore0);
				p.add(highscore1);
				p.add(highscore2);
				p.add(highscore3);
				
			 } catch(Exception j)
			 {System.out.println(j);}
			 
		}else {
			//display the Highscores
			noteText.setVisible(true);
			JLabel highscore0 = new JLabel("HIGHSCORES:");
			JLabel highscore1 = new JLabel(highscores[0]);
			JLabel highscore2 = new JLabel(highscores[1]);
			JLabel highscore3 = new JLabel(highscores[2]);
			highscore0.setBounds(620, 180, 200, 20);
			highscore1.setBounds(600, 200, 200, 20);
			highscore2.setBounds(600, 220, 200, 20);
			highscore3.setBounds(600, 240, 200, 20);
			p.add(highscore0);
			p.add(highscore1);
			p.add(highscore2);
			p.add(highscore3);			
		}//if(knight.getCoins() == 18) {...} else {...}
		//Make shure the Button does nothing if pressed again
		displayingHighscore = true;
		}//if(!displayingHighscore)
	
	}//public void actionPerformed(ActionEvent e)
	});//highScoresButton.addActionListener(new ActionListener() {...});
}//initEH

public boolean increaseCameraX(int dx) {
	//used for the movement of the knight(move the position from which the world is being painted)
	if(camerax + dx >= 0 && camerax + dx <= 18900) {
		camerax += dx;
		return true;
	}
	else {
		return false;
	}
}

public int getCameraX() {
	return camerax;
}

public Knight getKnight() {return knight;}

public void gameOver() {
	//end the game and take the endtime
	if(!gameOver) {
	gameOver = true;
	endTime = System.currentTimeMillis();
	}
}

public double getTime() {
	//calculate the Time the current game took
	return (endTime - startTime)/1000.0;
}

public void gameOverInfo() {
	//gets called from RepaintThread when the player has reached the end
	
	//"GameOver"
	JLabel gameOverText = new JLabel("Game Over");
	gameOverText.setFont(new Font("Arial", Font.BOLD, 140));
	this.add(gameOverText);
	gameOverText.setBounds(275, 300, 1200, 140);
	gameOverText.setVisible(true);
	
	//inform the Player about his play
	if(gameOver) {
	JLabel timeText = new JLabel("Time: " + getTime() + " sec");
	JLabel coinText = new JLabel("Coins: " + knight.getCoins());
	timeText.setBounds(600, 430, 200, 20);
	timeText.setVisible(true);
	coinText.setBounds(600, 450, 200, 20);
	coinText.setVisible(true);
	noteText.setBounds(600, 470, 500, 20);
	this.add(timeText);
	this.add(coinText);
	this.add(noteText);
	highScoresButton.setVisible(true);
	highScoresButton.setBounds(590, 530, 160, 25);
	this.add(highScoresButton);	
	}
}

public void destroy() {
	//gets called from myframe in the ActionListener of the Button Restart, before reusing the Constructor
	gt.goblins.clear();
	at.archers.clear();
	gt.active = false;
	at.active = false;
}

private double[] readDoubleArrayOutOfStringArray(String[] a) {
	//Method that only reads the Time in Milliseconds out of the HighscoreList by only taking the part of the string up until the "s" which stands for seconds
	double[] returnArray = new double[a.length];
	for(int q = 0; q < a.length; q++) {
		 int positionOfS = 0;
			int p = 0;
			while((a[q].charAt(p) != 's')) {
					p++;
					positionOfS = p;
				} 
			String b = a[q].substring(0, positionOfS);
			returnArray[q] = Double.parseDouble(b);
	 }
	return returnArray;
}


}
