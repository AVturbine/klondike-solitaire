import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Deck extends Pile{
	
	BufferedImage arrowIcon;
	
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
		this.loadImages();
	}
	
	public boolean isEmpty() {
		return this.cards.size() == 0 ? true : false;
	}
	
	public Pile deal(boolean state) {
		Card temp = cards.remove(cards.size()-1);
		temp.setFaceUp(state);
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
		int yTemp = y;
		int xTemp = x;
		int count = 0;
		if (empty()) {
			g.setColor(new Color(200, 200, 200));
			g.drawRect(x, y, 73, 97);
			g.drawRoundRect(x + 5, y+5, 63, 87, 10, 20);
			int centerX = boundingBox.width/2+x; int centerY = boundingBox.height/2+y;
			int imageCenterX = arrowIcon.getHeight()/2; int imageCenterY = arrowIcon.getWidth()/2;
			g.drawImage(arrowIcon, centerX-imageCenterX, centerY-imageCenterY, null);
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
	}

	@Override
	public int getIndex(int x, int y) { // special code that triggers dealing three cards to draw deck
		x = x-this.x;
		y = y-this.y;
		if (!(x < 0 || x > boundingBox.getWidth() || y < 0 || y > boundingBox.getHeight())) if (cards.isEmpty()) return -4; else return 0;
		else return -1;
	}

	@Override
	public boolean canStack(Card c) {
		return false;
	}
	
	private void loadImages() {
		try {
			URL url = getClass().getResource("cards/arrow.png");
			arrowIcon = ImageIO.read(url);
			Log.log("Successfully loaded arrow graphic", Log.VERBOSE);
		} catch (IOException e) {
			Log.log("Problem opening arrow graphic", Log.VERBOSE);
			e.printStackTrace();
		}
		BufferedImage resized = new BufferedImage(30, 30, arrowIcon.getType());
		Graphics2D v = resized.createGraphics();
		v.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		v.drawImage(arrowIcon, 0, 0, 30, 30, 0, 0, arrowIcon.getWidth(),
		    arrowIcon.getHeight(), null);
		v.dispose();
		arrowIcon = resized;
	}
	
	
}
