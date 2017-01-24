import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;


public class MovePile extends RegularPile implements MouseListener, MouseMotionListener{

	@Override
	public void draw(Graphics g, int x, int y, int size) {
		
	}

	@Override
	public int getIndex(int x, int y) {
		return 0;
	}

	@Override
	public boolean canStack(Card c) {
		return false;
	}
	
	MovePile(List<Card> c) {
		super(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
		cards = c;
	}
	MovePile(Card c) {
		super(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
		cards = new ArrayList<Card>();
		cards.add(c);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Handle click here
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		x = arg0.getX();
		y = arg0.getY();
		KlondikeGame.kg.k.repaint();
	}
}
