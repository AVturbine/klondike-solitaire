import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.util.ArrayList;
import java.util.List;


public class MovePile extends RegularPile {
	//TODO this class may not be necessary
	int yOffset;
	int xOffset;
	MovePile(List<Card> c) {
		super(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
		cards = c;
		yOffset = 0;
		xOffset = 0;
	}
	MovePile(Card c) {
		super(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
		cards = new ArrayList<Card>();
		cards.add(c);
		yOffset = 0;
		xOffset = 0;
	}
	MovePile(Pile c) {
		super(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
		cards = new ArrayList<Card>();
		for (int i = 0; i < c.getNumCards(); i++) {
			cards.add(c.getCard(i));
		}
		yOffset = 0;
		xOffset = 0;
	}
	MovePile(Pile c, int x, int y) {
		super(MouseInfo.getPointerInfo().getLocation().x + x, MouseInfo.getPointerInfo().getLocation().y + y);
		cards = new ArrayList<Card>();
		for (int i = 0; i < c.getNumCards(); i++) {
			cards.add(c.getCard(i));
		}
		yOffset = y;
		xOffset = x;
	}
	void setPosition(int x, int y) {
		this.x = x + xOffset;
		this.y = y + yOffset;
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
