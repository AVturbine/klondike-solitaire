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

}
