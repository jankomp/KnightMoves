package graphic;

import java.awt.BorderLayout;
import sun.audio.*;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class MyFrame extends JFrame 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) 
	{
		JFrame f = new MyFrame();
		f.setVisible(true);
	}

	static MyLevelPanel p = new MyLevelPanel();
	private static AudioPlayer AP = AudioPlayer.player;
	
	JButton optionButton = new JButton("Options");
	
	JButton resumeButton = new JButton("Resume");
	JButton restartButton = new JButton("Restart");
	JButton quitButton = new JButton("Quit");
	
	
	
	//Class implementing ActionListener when restart is pressed
	class RestartAction implements ActionListener{
		private MyFrame f;
		
		public RestartAction(MyFrame f) {
		this.f = f;
		}
		
		public void actionPerformed(ActionEvent e) {
			p.destroy();
			p = new MyLevelPanel();
			f.setContentPane(p);
			p.add(optionButton);
			p.add(resumeButton);
			p.add(restartButton);
			p.add(quitButton);
			optionButton.setVisible(true);
			resumeButton.setVisible(false);
			restartButton.setVisible(false);
			quitButton.setVisible(false);
			}
	}
	
	public MyFrame() 
	{	
		this.setSize(1400,800);
		this.setLocation(0,0);
		this.setTitle("Jans_Level");
		this.setContentPane(p);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		p.setLayout(null);
		startMusic();
		
		p.add(optionButton);
		optionButton.setBounds(610, 10, 80, 25);
		p.add(resumeButton);
		resumeButton.setBounds(510, 10, 80, 25);
		p.add(restartButton);
		restartButton.setBounds(610, 10, 80, 25);
		p.add(quitButton);
		quitButton.setBounds(710, 10, 80, 25);
		resumeButton.setVisible(false);
		restartButton.setVisible(false);
		quitButton.setVisible(false);
	
		optionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			optionButton.setVisible(false);
			resumeButton.setVisible(true);
			restartButton.setVisible(true);
			quitButton.setVisible(true);
			}
		});
		
		resumeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			optionButton.setVisible(true);
			resumeButton.setVisible(false);
			restartButton.setVisible(false);
			quitButton.setVisible(false);
			}
		});
		
		
		
		restartButton.addActionListener(new RestartAction(this));
		
		//quitButton.addActionListener(arg0);
	}
	
	

public void startMusic() {
	try {
	URL url = this.getClass().getResource("HaloTheme8Bit.wav");
	Clip clip = AudioSystem.getClip();
	AudioInputStream ais = AudioSystem.
	getAudioInputStream( url );
	clip.open(ais);
	clip.loop(Clip.LOOP_CONTINUOUSLY);
	} catch(Exception e) {e.printStackTrace();}
}
		
	
}
