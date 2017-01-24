import java.awt.Graphics;
import java.util.ArrayList;

public class DrawPile extends Pile {

	public DrawPile(int x, int y) {
		cards = new ArrayList<Card>();
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int size) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getIndex(int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}

}
