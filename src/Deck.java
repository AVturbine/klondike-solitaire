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
	
	public Pile deal(boolean state) {
		Card temp = cards.remove(cards.size()-1);
		temp.setSelected(state);
		return new MovePile(temp);
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
		int yTemp = y;
		int xTemp = x;
		int count = 0;
		if (empty()) {
			g.setColor(new Color(200, 200, 200));
			g.drawRect(x, y, 73, 97);
			g.drawRoundRect(x + 5, y+5, 63, 87, 10, 20);
		} else {
			for (Card c : cards) {
				c.draw(g, xTemp, yTemp, size);
				count++;
				if (count % 2 == 0) {
					yTemp-=1;
					xTemp-=1;
				}
			}
		}
		boundingBox.setSize(x-originalX+CARD_WIDTH, y-originalY+CARD_HEIGHT);
	}

	@Override
	public int getIndex(int x, int y) { // special code that triggers dealing three cards to draw deck
		return -3;
	}

	@Override
	public boolean canStack(Card c) {
		return false;
	}
	
}
