import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

public abstract class Pile {
	protected List<Card> cards;
	
	int x; int y;
	
	protected static final int CARD_WIDTH = 73;
	protected static final int CARD_HEIGHT = 97;
	
	Dimension boundingBox = new Dimension (CARD_WIDTH, CARD_HEIGHT);
	public Dimension getBoundingBox() {return boundingBox;}
	
	public boolean empty() {return cards.size()==0?true:false;}
	
	public int size() {return cards.size();}

	public abstract void draw(Graphics g, int x, int y, int size);
	public abstract int getIndex(int x, int y);
	public abstract boolean canStack(Card c);
	
	
	public Card getCard(int i) { return cards.get(i); }
	public int getNumCards() { return cards.size(); }
	public Pile pickUp(int index) {
		List<Card> temp = cards.subList(index, cards.size());
		this.cards = cards.subList(0, index);
		return new MovePile(temp);
	}
	public boolean addCard(Pile incoming) {
		if(!canStack(incoming.getCard(0))) return false;
		for (int i = 0; i < incoming.getNumCards(); i++) {
			cards.add(incoming.getCard(i));
		}
		return true;
	}
	
}
