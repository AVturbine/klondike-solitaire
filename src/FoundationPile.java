import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class FoundationPile extends Pile {
	
	public FoundationPile(int x, int y) {
		cards = new ArrayList<Card>();
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(Graphics g, int x, int y, int size) {
		g.setColor(new Color(255, 235, 0));
		g.drawRect(x - 5, y - 5, (int) boundingBox.getWidth()+5, (int)boundingBox.getHeight()+5);
		if (cards.size()==0) g.drawRect(x, y, (int)boundingBox.getWidth(), (int)boundingBox.getHeight());
		else {
			Card c = cards.get(cards.size()-1);
			c.draw(g, x, y, size);
		}
		
	}

	@Override
	public int getIndex(int x, int y) { //returns index of top card (the one that's going to be clicked) if click is in bound box
		if (!(x < 0 || x > boundingBox.getHeight() || y < 0 || y > boundingBox.getWidth())) return cards.size()-1;
		return -1;
	}

}
