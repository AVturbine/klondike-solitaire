import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Date;

import javax.swing.Timer;
import javax.swing.JPanel;

public class KlondikePanel extends JPanel {
	private Dimension dim = new Dimension(800, 600);
	private Color backgroundColor = new Color(125,125,125);
	public int count = 0;
	
	protected static final int CARD_WIDTH = 73;
	protected static final int CARD_HEIGHT = 97;
	
	Pile[] pileArray = new Pile[12];
	Deck deck;

	Timer timer;
	UIHandler ui = new UIHandler();
	Scorekeeper sc = new Scorekeeper();
	int elapsedSeconds = 0;
	int movesMade = 0;
	int score = 0;
	int fromPile; 
	int cardIndexInPile;
	DrawPile drawPile;
	MovePile moving;
	Pile pileIndex;
	
	private void releaseMove() {

		if (!holdingCard()) return;
		pileIndex.add(moving);
		pileIndex = null;
		moving = null;
		repaint();
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
	
	private void grabPile(Pile p, int i, int x, int y) {
		Point og = p.getCardLoc(i);
		moving = new MovePile(p.take(i), og.x - x, og.y - y);
		if(moving != null) {
			pileIndex = p;
			moving.setPosition(x, y);
			repaint();
		}
		
	}
	
	int getIndex(Pile p) {
		for(int i = 0; i < 12; i++) {
			if(pileArray[i] == p) return i;
		}
		return -1;
	}
	
	private void dropPile(Pile p) {
		p.add(moving);
		boolean flipped = false;
		if(pileIndex.getClass() == pileArray[0].getClass()) flipped = ((RegularPile)pileIndex).updateCardFaceStatus();
		pileIndex = null;
		score = sc.keepScore(getIndex(pileIndex), getIndex(p), score, (flipped) ? 1 : 0);
		movesMade++;
		repaint();
	}
	
	private boolean deckClicked(int x, int y) {
		return deck.getIndex(x, y) == 0 || deck.getIndex(x, y) == -4;
	}
	
	//for stuff
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
		setUpTimer(1000);
		timer.start();
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
				if (m.getButton() == 3) {
					releaseMove();
					return;
				}
	
				if (deckClicked(m.getX(), m.getY())) {
					movesMade++;
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
					return;
				}
				int takeIndex = p.getIndex(m.getX(), m.getY());
				if(!holdingCard() && p.canTake(takeIndex)) {
					grabPile(p, takeIndex, m.getX(), m.getY());
					return;
				}
				else if(holdingCard()){
					if (p.canStack(moving)) dropPile(p);
					//TODO return to spile
				}
			}
			
			
			
			@Override
			public void mouseReleased(MouseEvent m) {
				if(!holdingCard()) return;
	            if (pileIndex.getIndex(m.getX(), m.getY()) == -1) {
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
	
	private int maintainCardFlipStatus() {
		int count = 0;
		for (int i = 0; i < 7; i++) {
			count += ((RegularPile)pileArray[i]).updateCardFaceStatus() ? 1 : 0;
		}
		return count;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Pile p : pileArray) {
			p.draw(g, 1);
		}
		deck.draw(g, 1);
		ui.drawTime(g, 15, this.getHeight()-15, elapsedSeconds);
		ui.drawMoveCount(g, this.getWidth() - g.getFontMetrics().stringWidth("Moves: "+ movesMade) - 15, this.getHeight()-15, movesMade);
		ui.drawScore(g, 15, this.getHeight() - 15 - g.getFontMetrics().getHeight() - 15, score);
		if (holdingCard() && moving != null) moving.draw(g, 1);
		count++;
		Log.log("paintComponent has executed " + count + " times", Log.VERBOSE);
	}

	private void setUpTimer(int tickSpeed) {
		timer = new Timer(tickSpeed, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				elapsedSeconds++;
				repaint();
			}
			
		});
	}

	
}
