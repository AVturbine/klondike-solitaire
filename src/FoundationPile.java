import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class FoundationPile extends Pile {
	
	String suit;
	
	public FoundationPile(int x, int y, String suit) {
		cards = new ArrayList<Card>();
		this.suit = suit;
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(Graphics g,  int size) {
		g.setColor(new Color(255, 235, 0));
		if (cards.size()==0) {
			g.drawRect(x, y, 73, 97);
			g.drawRoundRect(x + 5, y + 5, 63, 87, 10, 20);
			//TODO Add suit labels
		}
		else {
			Card c = cards.get(cards.size()-1);
			c.draw(g, x, y, size);
		}
		
	}

	@Override
	public int getIndex(int x, int y) { //returns index of top card (the one that's going to be clicked) if click is in bound box
		x = x-this.x;
		y = y-this.y;
		if (!(x < 0 || x > boundingBox.getHeight() || y < 0 || y > boundingBox.getWidth())) return cards.size()-1;
		return -1;
	}
	
	public java.awt.Polygon getPolygonForSuit() {
		return null;
		
	}

}
