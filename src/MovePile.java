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

}
