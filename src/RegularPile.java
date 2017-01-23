import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class RegularPile extends Pile {	
	
	public RegularPile() {
		cards = new ArrayList<Card>();
	}
	
	public void updateCardFaceStatus() {
		if (!empty()) {
			if(!cards.get(cards.size()-1).getFaceUp()) {
				Card temp = cards.remove(cards.size()-1);
				temp.setFaceUp(true);
				cards.add(temp);
			}
		}
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int size) {
		int originalY = y;
		if (empty()) {
			g.setColor(new Color(200, 200, 200));
			g.drawRect(x, y, 73, 97);
		} else {
			for (Card c : cards) {
				if (!c.getFaceUp()) {
					c.draw(g, x, y, size);
					y+=10;
				} else {
					c.draw(g, x, y, size);
					y+=20;
				}
			}
		}
		boundingBox.setSize(boundingBox.getWidth(), y-originalY+CARD_HEIGHT);
		System.out.println(this.getBoundingBox());
	}

	@Override
	public int getIndex(int x, int y) {
		if ()
		return 0;
	}

}
