import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Date;

import javax.swing.JPanel;

public class KlondikePanel extends JPanel {
	private Dimension dim = new Dimension(800, 600);
	private Color backgroundColor = new Color(125,125,125);
	public int count = 0;
	
	protected static final int CARD_WIDTH = 73;
	protected static final int CARD_HEIGHT = 97;
	
	Pile[] pileArray = new Pile[12];
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
		for (int i = 0; i < 12; i++) {
			Pile p = pileArray[i];
			if (p.getIndex(x, y) != -1) 
				return p;
		}
		return null;
	}
	
	private boolean holdingCard() { return pileIndex != null; }
	
	private void grabPile(Pile p, int i) {
		moving = new MovePile(p.take(i));
		if(moving != null) pileIndex = p;
	}
	
	private void dropPile(Pile p) {
		p.add(moving);
		if(pileIndex.getClass() == pileArray[0].getClass()) ((RegularPile)pileIndex).updateCardFaceStatus();
		pileIndex = null;
	}
	
	private boolean deckClicked(int x, int y) {
		return deck.getIndex(x, y) == 0 || deck.getIndex(x, y) == -4;
	}
	
	//for stuff
	Date pressedTime;
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
		pileIndex = null;
		
		this.addMouseListener(new MouseListener () {

			@Override
			public void mouseClicked(MouseEvent m) {
				
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mousePressed(MouseEvent m) {
				pressedTime = new Date();
				if (m.getButton() == 3) {
					releaseMove();
					repaint();
					return;
				}
	
				if (deckClicked(m.getX(), m.getY())) {
					if(!deck.empty()) {
						pileArray[11].add(deck.getThree());
						repaint();
						return;
					}
					else { // if deck is empty and it is clicked again
						Pile l = ((DrawPile) pileArray[11]).take(0);
						deck.add(l);
					}
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
					repaint();
					return;
				}
				else if(holdingCard()){
					if (p.canStack(moving)) dropPile(p);
					repaint();
					//TODO return to spile
				}
			}

			@Override
			public void mouseReleased(MouseEvent m) {
				long timeClicked = new Date().getTime() - pressedTime.getTime();
	            if (timeClicked >= 300) {
	                // DO YOUR ACTION HERE
	            	if(holdingCard()){
	            		Pile p = pileAtPoint(m.getX(), m.getY());
						if (p != null && p.canStack(moving)) dropPile(p);
						else releaseMove();
					}
	            	releaseMove();
					repaint();
					return;
	            }
				
			}
				
		});
		this.addMouseMotionListener(new MouseMotionListener () {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				if(holdingCard()) {
					moving.setPosition(arg0.getX(), arg0.getY());
					repaint();
				}
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
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
		count++;
		Log.log("paintComponent has executed " + count + " times", Log.VERBOSE);
	}


	
}
