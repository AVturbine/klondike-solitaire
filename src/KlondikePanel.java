import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class KlondikePanel extends JPanel {
	private Dimension dim = new Dimension(800, 600);
	private Color backgroundColor = new Color(125,125,125);
	public int count = 0;
	
	protected static final int CARD_WIDTH = 73;
	protected static final int CARD_HEIGHT = 97;
	
	Pile[] pileArray = new Pile[13];
	
	public KlondikePanel() {
		
		this.setPreferredSize(dim);
		this.setBackground(backgroundColor);
		pileArray[0] = new Deck(50, 50);
		pileArray[1] = new DrawPile(150, 50);
		for (int i = 2; i<9; i++) {
			pileArray[i] = new RegularPile((i-2) * 100 + 50, 200);
		}
		pileArray[9] = new FoundationPile(350, 50, "H");
		pileArray[10] = new FoundationPile(450, 50, "S");
		pileArray[11] = new FoundationPile(550, 50, "D");
		pileArray[12] = new FoundationPile(650, 50, "C");
		this.addMouseListener(new MouseListener () {

			@Override
			public void mouseClicked(MouseEvent m) {
				for(Pile p: pileArray) {
					if (p.getIndex(m.getX(), m.getY()) != -1) {
						System.out.println(p.getIndex(m.getX(), m.getY()));
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}
				
		});
	}
	
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Pile p : pileArray) {
			p.draw(g, 1);
		}
		count++;
		Log.log("paintComponent has executed " + count + " times", Log.VERBOSE);
	}


	
}
