import java.awt.Graphics;
import java.util.List;

public abstract class Pile {
	public abstract void draw(Graphics g, int x, int y, int size);
	public abstract Card[] pickUp(int index);
	public abstract void addCard(Card[] cards);
}
