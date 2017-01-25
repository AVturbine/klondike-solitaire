import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class KlondikePanel extends JPanel {
	private Dimension dim = new Dimension(800, 600);
	private Color backgroundColor = new Color(125,125,125);
	public int count = 0;
	Deck deck;
	RegularPile[] pileArray = new RegularPile[8];
	
	public KlondikePanel() {
		this.setPreferredSize(dim);
		this.setBackground(backgroundColor);
		deck = new Deck();
		for (int i = 0; i < 8; i++) {
			pileArray[i] = new RegularPile(15, 15);
			for (int k = 0; k < i+1; k++) {
				pileArray[i].add(deck.deal());
			}
			pileArray[i].updateCardFaceStatus();
		}
		pileArray[3].updateCardFaceStatus();		
	}
	
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int x = 10;
		for (Pile p : pileArray) {
			p.draw(g, x, 50, 1);
			x+=80;
		}
		count++;
		System.out.println("paintComponent has executed " + count + " times");
	}
	
}
