import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Deck extends Pile{
	
	public Deck(int x, int y){
		this.x = x;
		this.y = y;
		String[] suits = {"D", "H", "C", "S"};
		cards = new ArrayList<Card>();

		for (String s: suits) {
		for (int i = 1; i<14; i++) {
			cards.add(new Card(s, i, false));
		}
		}
		Log.log(Integer.toString(cards.size()), Log.VERBOSE);
		this.shuffle();
	}
	
	public boolean isEmpty() {
		return this.cards.size() == 0 ? true : false;
	}
	
	public Pile deal() {
		return new MovePile(cards.remove(cards.size()-1));
	}
	
	public Pile deal(int num) {
		int firstIndex = cards.size() - num;
		return new MovePile(cards.subList(firstIndex, cards.size()));
	}
	
	public void shuffle() {
		for (int i = 0; i < cards.size()*4; i++) {
			int cardToShuffle = (int)(Math.random()*cards.size());
			int placeToInsert = (int)(Math.random()*cards.size());
			cards.add(placeToInsert, cards.remove(cardToShuffle));
		}
	}

	@Override
	public void draw(Graphics g, int size) {
		int originalY = y;
		int originalX = x;
		if (empty()) {
			g.setColor(new Color(200, 200, 200));
			g.drawRect(x, y, 73, 97);
			g.drawRoundRect(x + 5, y+5, 63, 87, 10, 20);
		} else {
			for (Card c : cards) {
				if (!c.getFaceUp()) {
					c.draw(g, x, y, size);
					y+=1;
					x+=1;
				} else {
					c.draw(g, x, y, size);
					y+=1;
					x+=1;
				}
			}
		}
		boundingBox.setSize(x-originalX+CARD_WIDTH, y-originalY+CARD_HEIGHT);
		System.out.println(this.getBoundingBox());
	}

	@Override
	public int getIndex(int x, int y) { // deck is not clickable per se?
		return -1;
	}

	@Override
	public boolean canStack(Card c) {
		return false;
	}
	
}
