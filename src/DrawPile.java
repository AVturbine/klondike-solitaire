import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class DrawPile extends Pile {

	public DrawPile(int x, int y) {
		cards = new ArrayList<Card>();
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void draw(Graphics g, int size) {
		int originalX = this.x;
		int xTemp = this.x;
		int yTemp = this.y;
		if (empty()) {
			g.setColor(new Color(200, 200, 200));
			g.drawRect(x, y, 73, 97);
		} else {
			int offset = getOffset();
			for (Card c : cards.subList(cards.size() - offset, cards.size())) {
				c.draw(g, xTemp, yTemp, size);
				if (!c.getFaceUp())
					xTemp+=10;
				else {
					xTemp+=20;
				}
			}
		}
		boundingBox.setSize(xTemp-originalX+CARD_WIDTH, boundingBox.getHeight());
		Log.log(this.getBoundingBox().toString(), Log.VERBOSE);
	}

	@Override
	public int getIndex(int x, int y) { //returns index of top card (the one that's going to be clicked) if click is in bound box
		x = x-this.x;
		y = y-this.y;
		if (!(x < 0 || x > boundingBox.getWidth() || y < 0 || y > boundingBox.getHeight())) return !empty() ? cards.size()-1 : -2; 
		return -1;
	}

	@Override
	public boolean canStack(Card c) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private int getOffset() {
		if (cards.size() >= 3) {
			return 3;
		} else {
			if (cards.size() == 2) return 2;
			else if (cards.size() == 1) return 1;
			else return 0;
		}
	}

	@Override
	public Point getCardLoc(int index) {
		int xTemp = this.x;
		int yTemp = this.y;
		if (empty()) {
			return null;
		}
		
		int offset = getOffset();
		List<Card> tempCards = cards.subList(cards.size() - offset, cards.size());
		
		for (int i = 0; i < index && i < tempCards.size(); i++) {
			if (!tempCards.get(i).getFaceUp())
				xTemp+=10;
			else {
				xTemp+=20;
			}
		}
		return new Point(xTemp, yTemp);
	}


}
