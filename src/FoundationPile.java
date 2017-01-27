import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class FoundationPile extends Pile {
	
	String suit;
	BufferedImage suitImage;
	int SUIT_IMAGE_HEIGHT = 30;
	
	public FoundationPile(int x, int y, String suit) {
		cards = new ArrayList<Card>();
		this.suit = suit;
		this.x = x;
		this.y = y;
		loadImages();
	}

	@Override
	public void draw(Graphics g,  int size) {
		g.setColor(new Color(255, 235, 0));
		if (cards.size()==0) {
			g.drawRect(x, y, 73, 97);
			g.drawRoundRect(x + 5, y + 5, 63, 87, 10, 20);
			BufferedImage resized = new BufferedImage(SUIT_IMAGE_HEIGHT, SUIT_IMAGE_HEIGHT, suitImage.getType());
			Graphics2D v = resized.createGraphics();
			v.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			v.drawImage(suitImage, 0, 0, 30, 30, 0, 0, suitImage.getWidth(),
			    suitImage.getHeight(), null);
			v.dispose();
			int centerX = boundingBox.width/2+x; int centerY = boundingBox.height/2+y;
			int imageCenterX = resized.getHeight()/2; int imageCenterY = resized.getWidth()/2;
			g.drawImage(resized, centerX-imageCenterX, centerY-imageCenterY, null);
		}
		else {
			Card c = cards.get(cards.size()-1);
			c.draw(g, x, y, size);
		}
		
	}

	@Override
	public int getIndex(int x, int y) { //returns index of top card (the one that's going to be clicked) if click is in bound box
		x = x-this.x;
		y = y-this.y;
		if (!(x < 0 || x > boundingBox.getHeight() || y < 0 || y > boundingBox.getWidth())) return !empty() ? cards.size()-1 : -2; 
		return -1;
	}
	
	public void loadImages() {
		switch(suit) {
		case "H":
			try {
				URL url = getClass().getResource("cards/hearts.png");
				suitImage = ImageIO.read(url);
				Log.log("Successfully loaded heart graphic", Log.VERBOSE);
			} catch (IOException e) {
				Log.log("Problem opening foundation pile hearts graphic", Log.VERBOSE);
				e.printStackTrace();
			}
			break;
		case "S":
			try {
				URL url = getClass().getResource("cards/spades.png");
				suitImage = ImageIO.read(url);
				Log.log("Successfully loaded spades graphic", Log.VERBOSE);
			} catch (IOException e) {
				Log.log("Problem opening foundation pile spades graphic", Log.VERBOSE);
				e.printStackTrace();
			}
			break;
		case "D":
			try {
				URL url = getClass().getResource("cards/diamonds.png");
				suitImage = ImageIO.read(url);
				Log.log("Successfully loaded diamond graphic", Log.VERBOSE);
			} catch (IOException e) {
				Log.log("Problem opening foundation pile diamond graphic", Log.VERBOSE);
				e.printStackTrace();
			}
			break;
		case "C":
			try {
				URL url = getClass().getResource("cards/clubs.png");
				suitImage = ImageIO.read(url);
				Log.log("Successfully loaded club graphic", Log.VERBOSE);
			} catch (IOException e) {
				Log.log("Problem opening foundation pile club graphic", Log.VERBOSE);
				e.printStackTrace();
			}
			break;
		}
		
	}

	@Override
	public boolean canStack(Card c) {
		if (!(cards.size() == 0)) {
			return cards.get(cards.size()-1).getRank()+1 == c.getRank() && c.getSuit() == this.suit ? true : false;
			
		} else {
			if (c.getRank() == 1 && c.getSuit() == this.suit) return true;
		}
		return false;
		
	}

}


