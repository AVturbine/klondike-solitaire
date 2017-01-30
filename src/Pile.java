import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.List;

public abstract class Pile {
	protected List<Card> cards;
	
	int x; int y;
	
	protected static final int CARD_WIDTH = 73;
	protected static final int CARD_HEIGHT = 97;
	
	Dimension boundingBox = new Dimension (CARD_WIDTH, CARD_HEIGHT);
	public Dimension getBoundingBox() {return boundingBox;}
	
	public boolean empty() {return cards.size()==0;}
	
	public int size() {return cards.size();}

	public abstract void draw(Graphics g, int size);
	public abstract int getIndex(int x, int y);
	public abstract boolean canStack(Card c);
	
	public boolean canStack(Pile p) {
		return canStack(p.getCard(0));
	}
	public Card getCard(int i) { 
		if (i < cards.size()) return cards.get(i); 
		Log.error("Out of pile bounds");
		return null;
	}
	public int getNumCards() { return cards.size(); }
	
	public boolean canTake(int index) {
		return cards.get(index).getFaceUp();
	}
	public MovePile take(int index) {
		if(!cards.get(index).getFaceUp()) return null;
		List<Card> temp = cards.subList(index, cards.size());
		this.cards = cards.subList(0, index);
		
		return new MovePile(temp);
	}
	public Card queryFirstCard(int index) {
		Card temp = cards.get(index);
		return temp;
	}
	
	public boolean add(Pile incoming) {
		for (int i = 0; i < incoming.getNumCards(); i++) {
			cards.add(incoming.getCard(i));
		}
		return true;
	}
	public boolean addCard(Pile incoming) {
		if(incoming.getNumCards() < 1) return false;
		if(!canStack(incoming.getCard(0))) return false;
		for (int i = 0; i < incoming.getNumCards(); i++) {
			cards.add(incoming.getCard(i));
		}
		return true;
	}
	
	public void markAsSelected(int index, boolean status) {
		for (int i = index; i < cards.size(); i++) {
			Card temp = cards.get(i);
			temp.setSelected(status);
			cards.set(i, temp);
		}
	}
	
}
