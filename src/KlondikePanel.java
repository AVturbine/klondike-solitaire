import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

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
	MovePile cardBuffer;
	Timer timer;
	UIHandler ui = new UIHandler();
	Scorekeeper sc = new Scorekeeper();
	int elapsedSeconds = 0;
	int movesMade = 0;
	int score = 0;
	int fromPile; int cardIndexInPile;
	
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
		this.addMouseListener(new MouseListener () {

			@Override
			public void mouseClicked(MouseEvent m) {
				if (deck.getIndex(m.getX(), m.getY()) == 0) {
					int counter = 3;
					if (!deck.empty()) {
						do {
							Pile temp = deck.deal(true);
							pileArray[11].add(temp);
							counter--;
						} while(!deck.empty() && counter > 0);
						movesMade++;
					}
				}
				else if (deck.getIndex(m.getX(), m.getY()) == -4) { // if deck is empty and it is clicked again
					Pile p = ((DrawPile) pileArray[11]).take(0);	
					deck.add(p);
					movesMade++;
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
				for (int i = 0; i < 12; i++) {
					Pile p = pileArray[i];
					if (p.getIndex(e.getX(), e.getY()) != -1 && p.getIndex(e.getX(), e.getY()) != -2 ) {
						 fromPile = i;
						 cardIndexInPile = p.getIndex(e.getX(), e.getY());
						 p.markAsSelected(cardIndexInPile, true);
						 repaint();
					}
				} 
			}
			
			
			
			@Override
			public void mouseReleased(MouseEvent e) {
				for (int i = 0; i < 12; i++) {
					Pile p = pileArray[i];
					if (p.getIndex(e.getX(), e.getY()) != -1 && i != fromPile) {
						pileArray[fromPile].markAsSelected(cardIndexInPile, false);
						if (p.canStack(pileArray[fromPile].queryFirstCard(cardIndexInPile))) {
							p.addCard(pileArray[fromPile].take(cardIndexInPile));
							int cardsFlipped = maintainCardFlipStatus();
							score = sc.keepScore(fromPile, i, score, cardsFlipped);
							movesMade++;
						}
						
					}
				}
				pileArray[fromPile].markAsSelected(cardIndexInPile, false);
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
