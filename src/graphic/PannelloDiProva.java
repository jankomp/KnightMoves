package graphic;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class PannelloDiProva extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField tf = new JTextField(20);
	private JButton b = new JButton("OK");
	private JLabel l = new JLabel("");
	
	private Image quadrato;
	private int x=50,y=50;
	
	public PannelloDiProva() {
		super();
		initGui();
		initEH();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(quadrato, x%1400, y%900, this);
		
	}
	
	public void initGui() {
		this.setFocusable(true);
		this.setLayout(new BorderLayout());
		JPanel pb = new JPanel();
		pb.add(b);
		this.add(pb,BorderLayout.SOUTH);
		b.setEnabled(false);
		
		JPanel pp = new JPanel();
		pp.add(tf);
		pp.add(l);
		this.add(pp, BorderLayout.NORTH);
		
		quadrato = loadAssets("qua.png"); 		
	}
	
	public void initEH() {
		
		tf.addCaretListener(new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) {
				if(!tf.getText().equals(""))
					b.setEnabled(true);
				else
					b.setEnabled(false);
				
			}
		});
		
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				l.setText(tf.getText());
				
			}
		});
		
		this.addKeyListener (new KeyAdapter() {

			private Set<Character> pressed= new HashSet<Character>();
			
			@Override
			public synchronized void keyPressed(KeyEvent e) {	
				pressed.add(e.getKeyChar());
				if (pressed.size() > 1) {
					/*if(pressed.contains()) {
						//TODO: implement
					}*/
				}else {
					super.keyPressed(e);
					switch (e.getKeyCode()) {
					case KeyEvent.VK_RIGHT : x+=10 ; break ;
					case KeyEvent.VK_LEFT : x-=10 ; break ;
					case KeyEvent.VK_UP : y-=10 ; break ;
					case KeyEvent.VK_DOWN : y+=10 ; break ; 
					}
				}
			/**
				repaint();**/
			}
			
			public synchronized void keyReleased (KeyEvent e) {
				pressed.remove(e.getKeyChar());
			}
			
		});
	}

	public Image loadAssets(String path) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		URL url = this.getClass().getResource(path);
		Image img = tk.getImage(url);
		
		return img;
	}
	
	
}
