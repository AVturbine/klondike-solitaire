import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
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
	public boolean updateCardFaceStatus() {
		if (!empty()) {
			boolean flipped = false;
			Card temp = cards.get(cards.size()-1);
			if (!temp.getFaceUp()) flipped = true;
			temp.setFaceUp(true);
			cards.set(cards.size()-1, temp);
			return flipped;
		}
		return false;
	}
	
	@Override
	public void draw(Graphics g, int size) {
		int originalY = y;
		int originalX = x;
		if (empty()) {
			if(highlight) g.setColor(new Color(255, 255, 0));
			g.setColor(new Color(200, 200, 200));
			g.drawRect(x, y, 73, 97);
		}
		else {
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
		this.y = originalY;
		this.x = originalX;
		Log.log(this.getBoundingBox().toString(), Log.VERBOSE);
	}
	
	/*
	 * Returns index of card at click location, -1 if click is outside the bounding box
	 */
	public int getIndex(int x, int y) { 
		x = x-this.x;
		y = y-this.y;
		if (!(x < 0 || x > boundingBox.getWidth() || y < 0 || y > boundingBox.getHeight())) { // determines if click is valid
			int yMod = 0;
			int previousYMod = 0;
			if (!empty()) {
				for (int i = 0; i < cards.size(); i++) {
					Card c = cards.get(i);
					if (c.getFaceUp()) yMod+=20;
					else yMod+=10;
					if (i != cards.size()-1) { // if we got to the last card and y is valid but still not found, just go with the last index
						if(previousYMod <=y && yMod >= y) {
							return i;
						}
					} else {
						return i;
					}
					previousYMod = yMod;
				}
			} else {
				return -2;
			}
		}
		return -1;
	} 
	public boolean canStack(Card c) {
		if(cards.size() == 0) return c.getRank() == 13;
		Card lastCard = cards.get(cards.size() - 1);
		return (c.getRank() + 1 == lastCard.getRank() && c.getColor() != lastCard.getColor());
	}

	@Override
	public Point getCardLoc(int index) {
		int originalY = y;
		int originalX = x;
		if (empty()) {
			return null;
		}
		for (int i = 0; i < index; i++) {
			if (!cards.get(i).getFaceUp()) {
				originalY+=10;
			} else {
				originalY+=20;
			}
		}
		return new Point(originalX, originalY);
	}


}
