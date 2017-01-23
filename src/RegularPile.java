import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class RegularPile extends Pile {
	
	private List<Card> cards;
	private static final int CARD_WIDTH = 73;
	private static final int CARD_HEIGHT = 97;
	private Dimension boundingBox = new Dimension (CARD_WIDTH, CARD_HEIGHT);
	
	
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
	
	public int size() {return cards.size();}
	public boolean empty() {return cards.size()==0?true:false;}
	public Dimension getBoundingBox() {return boundingBox;}
	
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
	}

	@Override
	public Card[] pickUp(int index) {
		List<Card> temp = cards.subList(index, cards.size());
		this.cards = cards.subList(0, index);
		return temp.toArray(new Card[temp.size()]);
	}
	
	@Override
	public void addCard(Card[] incoming) {
		for (int i = 0; i < incoming.length; i++) {
			cards.add(incoming[i]);
		}
	}


}
