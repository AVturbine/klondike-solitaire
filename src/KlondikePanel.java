import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class KlondikePanel extends JPanel {
	private Dimension dim = new Dimension(800, 600);
	private Color backgroundColor = new Color(125,125,125);
	public int count = 0;
	Pile pile = new RegularPile(50, 50);
	Pile pile2 = new FoundationPile(150, 300);
	Deck deck;
	
	public KlondikePanel() {
		this.setPreferredSize(dim);
		this.setBackground(backgroundColor);
		deck = new Deck(400, 400);
				
	}
	
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		pile.draw(g, 1);
		pile2.draw(g, 1);
		deck.draw(g, 1);
		count++;
		Log.log("paintComponent has executed " + count + " times", Log.VERBOSE);
	}
	
}
