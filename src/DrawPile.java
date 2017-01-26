import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class DrawPile extends Pile {

	public DrawPile(int x, int y) {
		cards = new ArrayList<Card>();
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void draw(Graphics g, int size) {
		int originalX = x;
		if (empty()) {
			g.setColor(new Color(200, 200, 200));
			g.drawRect(x, y, 73, 97);
		} else {
			for (Card c : cards) {
				if (!c.getFaceUp()) {
					c.draw(g, x, y, size);
					x+=10;
				} else {
					c.draw(g, x, y, size);
					x+=20;
				}
			}
		}
		boundingBox.setSize(boundingBox.getWidth(), x-originalX+CARD_HEIGHT);
		Log.log(this.getBoundingBox().toString(), Log.VERBOSE);
	}

	@Override
	public int getIndex(int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canStack(Card c) {
		// TODO Auto-generated method stub
		return false;
	}

}
