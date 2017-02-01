import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Card implements Comparable<Card> {
	
	private String suit;
	private int rank;
	private boolean faceUp;
	private boolean color; //red is true, black is false
	private boolean selected;
	private BufferedImage front;
	private BufferedImage back;
	private BufferedImage frontSelected;
	
	private static int ORIGINAL_SIZE_X = 73;
	private static int ORIGINAL_SIZE_Y = 97;
	
	public Card(String suit, int rank, boolean faceUp) {
		this.suit = suit;
		this.rank = rank;
		this.faceUp = faceUp;
		this.selected = false;
		if (suit.equals("D") || suit.equals("H")) color = true; else color = false; // hearts and diamonds are usually red, other suits black
		this.obtainImages();
	}
	
	/*
	 * Getters
	 */	
	public int getRank() {return this.rank;}
	public String getSuit() {return this.suit;}
	public boolean getFaceUp() {return this.faceUp;}
	public boolean getColor() {return this.color;}
	public boolean getSelected() {return this.selected;}
	
	/*
	 * Setters
	 */
	public void setFaceUp(boolean faceUp) {this.faceUp = faceUp;}
	public void setSelected(boolean selected) {this.selected = selected;}
	
	public boolean equals(Card otherCard) {
		if (this.getSuit().equals(otherCard.getSuit()) && this.getRank()== otherCard.getRank()) return true;
		else return false;
	}
	
	public void draw(Graphics g, int x, int y, int size) {
		int width = ORIGINAL_SIZE_X*size;
		int height = ORIGINAL_SIZE_Y*size;
		if (faceUp) {
			if (!selected) {
				g.drawImage(front, x, y, width, height, null);
				Log.log("drew front", Log.VERBOSE);
			} else {
				g.drawImage(frontSelected, x, y, width, height, null);
				Log.log("drew frontS", Log.VERBOSE);
			}
		} else {
			g.drawImage(back, x, y, width, height, null);
			Log.log("drew back", Log.VERBOSE);
		}
	}
	/**
	 * Obtaitruens images for this card by constructing a filename and retrieving image from src/cards	
	 */
	private void obtainImages() {
		String fileName = "";
		if (rank == 1) fileName += "ace";
		else if (rank == 11) fileName += "jack";
		else if (rank == 12) fileName += "queen";
		else if (rank == 13) fileName += "king";
		else fileName += Integer.toString(rank);
		
		if (suit.equals("D")) fileName += "diamonds";
		else if (suit.equals("H")) fileName += "hearts";
		else if (suit.equals("S")) fileName += "spades";
		else if (suit.equals("C")) fileName += "clubs";
		
		try {
			URL url = getClass().getResource("cards/" + fileName + ".GIF");
			front = ImageIO.read(url);
			Log.log("Successfully loaded front img "+ fileName, Log.VERBOSE);
		} catch (IOException e) {
			Log.log("Problem opening the front resource: filename is " + fileName, Log.VERBOSE);
			e.printStackTrace();
		}
		
		fileName += "S"; // now we'll get the selected variant of the card
		
		try {
			URL url = getClass().getResource("cards/" + fileName + ".GIF");
			frontSelected = ImageIO.read(url);
			Log.log("Successfully loaded frontS img "+ fileName, Log.VERBOSE);

		} catch (IOException e) {
			Log.log("Probldiamondem opening the frontSelected resource: filename is " + fileName, Log.VERBOSE);
			e.printStackTrace();
		}
		
		try {
			URL url = getClass().getResource("cards/back1.GIF");
			back = ImageIO.read(url);
			Log.log("Successfully loaded back img back1.GIF", Log.VERBOSE);

		} catch (IOException e) {
			Log.log("Problem opening the back resource: filename is back1.GIF", Log.VERBOSE);
			e.printStackTrace();
		}
	}

	@Override
	public int compareTo(Card o) {
		return this.getRank()-o.getRank();
	}
}
