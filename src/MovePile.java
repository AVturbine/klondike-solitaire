import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.util.ArrayList;
import java.util.List;


public class MovePile extends RegularPile {
	//TODO this class may not be necessary
	MovePile(List<Card> c) {
		super(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
		cards = c;
	}
	MovePile(Card c) {
		super(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
		cards = new ArrayList<Card>();
		cards.add(c);
	}
	MovePile(Pile c) {
		super(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
		cards = new ArrayList<Card>();
		for (int i = 0; i < c.getNumCards(); i++) {
			cards.add(c.getCard(i));
		}
	}
	void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	@Override
	public void draw(Graphics g, int size) {
		int originalY = y;
		int originalX = x;
		for (Card c : cards) {
			if (!c.getFaceUp()) {
				c.draw(g, x, y, size);
				y+=10;
			} else {
				c.draw(g, x, y, size);
				y+=20;
			}
		}
	

		boundingBox.setSize(boundingBox.getWidth(), y-originalY+CARD_HEIGHT);
		this.y = originalY;
		this.x = originalX;
		Log.log(this.getBoundingBox().toString(), Log.VERBOSE);
	}
	

}
