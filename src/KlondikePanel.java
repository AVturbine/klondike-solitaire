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
	
	Pile[] pileArray = new Pile[12];
	Deck deck;
	MovePile cardBuffer;
	int pileIndextoPickFrom; int cardIndexInPile;
	
	public KlondikePanel() {
		
		this.setPreferredSize(dim);
		this.setBackground(backgroundColor);
		deck = new Deck(50, 50);
		for (int i = 0; i<7; i++) {
			pileArray[i] = new RegularPile(i * 100 + 50, 200);
		}
		pileArray[7] = new FoundationPile(350, 50, "H");
		pileArray[8] = new FoundationPile(450, 50, "S");
		pileArray[9] = new FoundationPile(550, 50, "D");
		pileArray[10] = new FoundationPile(650, 50, "C");
		pileArray[11] = new DrawPile(150, 50);
		initializePiles();
		this.requestFocusInWindow();
		this.addMouseListener(new MouseListener () {

			@Override
			public void mouseClicked(MouseEvent m) {
				for(int i = 0; i < 12; i++) {
					Pile p = pileArray[i];
					if (p.getIndex(m.getX(), m.getY()) != -1) {
						System.out.println(p.getIndex(m.getX(), m.getY()) + "by  pile at " + p.x + " "+ p.y);
					}
				}
				
				if (deck.getIndex(m.getX(), m.getY()) == 0) {
					int counter = 3;
					if (!deck.empty()) {
						do {
							Pile temp = deck.deal(true);
							pileArray[11].add(temp);
							counter--;
						} while(!deck.empty() && counter > 0);
					}
				}
				repaint();
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
				int xC = e.getX();
				int yC = e.getY();
				for (int i = 0; i < 12; i++) {
					Pile p = pileArray[i];
					if (p.getIndex(e.getX(), e.getY()) != -1 && p.getIndex(e.getX(), e.getY()) != -2 ) {
						 pileIndextoPickFrom = i;
						 cardIndexInPile = p.getIndex(e.getX(), e.getY());
						 p.markAsSelected(cardIndexInPile, true);
						 repaint();
					}
				} 
				
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				int xC = e.getX();
				int yC = e.getY();
				for (int i = 0; i < 12; i++) {
					Pile p = pileArray[i];
					if (p.getIndex(e.getX(), e.getY()) != -1 && i != pileIndextoPickFrom) {
						pileArray[pileIndextoPickFrom].markAsSelected(cardIndexInPile, false);
						if (p.canStack(pileArray[pileIndextoPickFrom].queryFirstCard(cardIndexInPile))) {
							p.addCard(pileArray[pileIndextoPickFrom].take(cardIndexInPile));
						}
						maintainCardFlipStatus();
					}
				}
				pileArray[pileIndextoPickFrom].markAsSelected(cardIndexInPile, false);
				repaint();
				
			}
				
		});
	}
	
	private void initializePiles() {
		for (int i = 0; i < 7; i++) {
			for (int j = 7-i; j < 8; j++) {
				pileArray[i].add(deck.deal(false));
			}
		}
		maintainCardFlipStatus();
	}
	
	private void maintainCardFlipStatus() {
		for (int i = 0; i < 7; i++) {
			((RegularPile)pileArray[i]).updateCardFaceStatus();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Pile p : pileArray) {
			p.draw(g, 1);
		}
		deck.draw(g, 1);
		count++;
		Log.log("paintComponent has executed " + count + " times", Log.VERBOSE);
	}


	
}
