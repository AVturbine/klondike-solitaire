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
	
	Pile[] pileArray = new Pile[11];
	Deck deck;
	DrawPile drawPile;
	MovePile moving;
	Pile pileIndex;
	
	private void releaseMove() {

		if (!holdingCard()) return;
		pileIndex.add(moving);
		pileIndex = null;
		moving = null;
	}
	
	private Pile pileAtPoint(int x, int y) {
		for (int i = 0; i < 11; i++) {
			Pile p = pileArray[i];
			if (p.getIndex(x, y) != -1 && p.getIndex(x, y) != -2 ) 
				return p;
		}
		return null;
	}
	
	private boolean holdingCard() { return pileIndex != null; }
	
	private void grabPile(Pile p, int i) {
		moving = p.take(i);
		if(moving != null) pileIndex = p;
	}
	
	private void dropPile(Pile p) {
		p.add(moving);
		pileIndex = null;
	}
	
	public KlondikePanel() {
		
		this.setPreferredSize(dim);
		this.setBackground(backgroundColor);
		deck = new Deck(50, 50);
		drawPile = new DrawPile(150, 50);
		for (int i = 0; i<7; i++) {
			pileArray[i] = new RegularPile(i * 100 + 50, 200);
		}
		pileArray[7] = new FoundationPile(350, 50, "H");
		pileArray[8] = new FoundationPile(450, 50, "S");
		pileArray[9] = new FoundationPile(550, 50, "D");
		pileArray[10] = new FoundationPile(650, 50, "C");
		initializePiles();
		this.requestFocusInWindow();
		pileIndex = null;
		
		this.addMouseListener(new MouseListener () {

			@Override
			public void mouseClicked(MouseEvent m) {
				if (m.getButton() == 3) {
					releaseMove();
					repaint();
					return;
				}
	
				Pile p = pileAtPoint(m.getX(), m.getY());
				if(p == null) {
					releaseMove();
					repaint();
					return;
				}
				int takeIndex = p.getIndex(m.getX(), m.getY());
				if(!holdingCard() && p.canTake(takeIndex)) {
					grabPile(p, takeIndex);
					return;
				}
				else if(holdingCard()){
					if (p.canStack(moving)) dropPile(p);
					//TODO return to spile
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

			public void mousePressed(MouseEvent e) {
				/*if (e.getButton() == 3) {
					if (pileIndex == -1) return;
					pileArray[pileIndex].add(new MovePile(moving));
					pileIndex = -1;
				}
				for (int i = 0; i < 11; i++) {
					Pile p = pileArray[i];
					if (p.getIndex(e.getX(), e.getY()) != -1 && p.getIndex(e.getX(), e.getY()) != -2 ) {
						if(pileIndex == -1) {
							moving = p.take(p.getIndex(e.getX(), e.getY()));
							pileIndex = (moving == null) ? -1 : i;
						}
						repaint();
					}
				}*/
			}

			@Override
			public void mouseReleased(MouseEvent m) {
				/*if (pileIndex == -1) return;
				for (int i = 0; i < 11; i++) {
					Pile p = pileArray[i];
					if (p.getIndex(m.getX(), m.getY()) != -1 && p.getIndex(m.getX(), m.getY()) != -2 ) {

						if(pileIndex != -1 && p.canStack(moving)) {
							p.addCard(moving);
							((RegularPile) pileArray[pileIndex]).updateCardFaceStatus();
							pileIndex = -1;
							
						}
						repaint();
						return;
					}
				}
				
				pileArray[pileIndex].add(new MovePile(moving));
				pileIndex = -1;
				repaint();*/
			}
				
		});
		this.addMouseMotionListener(new MouseMotionListener () {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				/* if(pileIndex != -1) {
					moving.setPosition(arg0.getX(), arg0.getY());
					repaint();
				} */
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(holdingCard()) {
					moving.setPosition(arg0.getX(), arg0.getY());
					repaint();
				}
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
		if (holdingCard() && moving != null) moving.draw(g, 1);
		drawPile.draw(g, 1);
		count++;
		Log.log("paintComponent has executed " + count + " times", Log.VERBOSE);
	}


	
}
