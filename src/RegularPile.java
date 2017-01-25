import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class RegularPile extends Pile {	
	
	public RegularPile(int x, int y) {
		cards = new ArrayList<Card>();
		this.x = x;
		this.y = y;
	}
	
	/*
	 * Flips top card if all faceup cards are taken off the pile
	 */
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
		Log.log(this.getBoundingBox().toString(), Log.VERBOSE);
	}
	
	/*
	 * Returns index of card at click location (relative to bounding box), -1 if click is outside the bounding box
	 */
	public int getIndex(int x, int y) { 
		if (!(x < this.x || x > boundingBox.getHeight() + this.x || y < this.y || y > boundingBox.getWidth() + this.y)) { // determines if click is valid
			int yMod = 0;
			int previousYMod = 0;
			for (int i = 0; i < cards.size(); i++) {
				Card c = cards.get(i);
				if (c.getFaceUp()) yMod+=20;
				else yMod+=10;
				if (i != cards.size()-1) { // if we got to the last card and y is valid but still not found, just go with the last index
					if(previousYMod <= y && yMod >= y) {
						return i;
					}
				} else {
					return i;
				}
				previousYMod = yMod;
			}
		}
		return -1;
	} 
	public boolean canStack(Card c) {
		if(cards.size() == 0) return c.getRank() == 13;
		Card lastCard = cards.get(cards.size() - 1);
		return (c.getRank() + 1 == lastCard.getRank() && c.getColor() != lastCard.getColor());
	}

}
