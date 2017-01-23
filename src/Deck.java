import java.util.ArrayList;
import java.util.List;

public class Deck {
	
	private List<Card> cards;
	private int size;
	
	public Deck() {
		String[] suits = {"D", "H", "C", "S"};
		cards = new ArrayList<Card>();
		
		for (String s: suits) {
			for (int i = 1; i<14; i++) {
				cards.add(new Card(s, i, false));
				size++;
			}
		}
		System.out.println(size);
		this.shuffle();
	}
	
	public boolean isEmpty() {
		return this.size == 0 ? true : false;
	}
	
	public Card[] deal() {
		size--;
		return new Card[] {cards.remove(size)};
	}
	
	public Card[] deal(int num) {
		int firstIndex = size - num;
		return (Card[])(cards.subList(firstIndex, size).toArray());
	}
	
	public int size() {return size;}
	
	public void shuffle() {
		for (int i = 0; i < size*4; i++) {
			int cardToShuffle = (int)(Math.random()*cards.size());
			int placeToInsert = (int)(Math.random()*cards.size());
			cards.add(placeToInsert, cards.remove(cardToShuffle));
		}
	}
	
}
