import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.List;

public abstract class Pile {
	List<Card> cards;
	
	int x; int y;
	
	protected static final int CARD_WIDTH = 73;
	protected static final int CARD_HEIGHT = 97;
	
	Dimension boundingBox = new Dimension (CARD_WIDTH, CARD_HEIGHT);
	public Dimension getBoundingBox() {return boundingBox;}
	
	public boolean empty() {return cards.size()==0?true:false;}
	
	public int size() {return cards.size();}

	public abstract void draw(Graphics g, int size);
	public abstract int getIndex(int x, int y);
	
	public Card[] pickUp(int index) {
		List<Card> temp = cards.subList(index, cards.size());
		this.cards = cards.subList(0, index);
		return temp.toArray(new Card[temp.size()]);
	}
	public void addCard(Card[] incoming) {
		for (int i = 0; i < incoming.length; i++) {
			cards.add(incoming[i]);
		}
	}
	
}
