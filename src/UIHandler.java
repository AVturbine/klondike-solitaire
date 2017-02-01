import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class UIHandler {
	public void drawTime(Graphics g, int x, int y, int time) {
		g.setFont(new Font("Ubuntu", Font.BOLD, 24));
		g.setColor(new Color(200, 200, 200));
		int minutes = (time / 60);
		int seconds = (time - (60*minutes));
		String toDraw = "Time elapsed: " + minutes + ":";
		if (time - minutes*60 < 10) toDraw+="0";
		g.drawString (toDraw + seconds, x, y);
	}
	
	public void drawMoveCount(Graphics g, int x, int y, int score) {
		g.setFont(new Font("Ubuntu", Font.BOLD, 24));
		g.setColor(new Color(200, 200, 200));
		g.drawString("Moves: " + score, x, y);
	}
	
	public void drawScore(Graphics g, int x, int y, int score) {
		g.setFont(new Font("Ubuntu", Font.BOLD, 24));
		g.setColor(new Color(200, 200, 200));
		g.drawString("Score: " + score, x, y);
	}
	
	public int makeXCoord(Graphics g, String string) {
		return g.getFontMetrics().stringWidth(string);
	}
	
	
}
