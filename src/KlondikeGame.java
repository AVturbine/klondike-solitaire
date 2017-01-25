import javax.swing.JFrame;

public class KlondikeGame extends JFrame {
	
	private static final long serialVersionUID = 8008135L;
	public static KlondikePanel k;
	public static KlondikeGame kg;
	public void repaint() {
		if (kg != null && k != null) k.repaint();
	}
	public KlondikeGame() {
		super("Klondike Solitaire");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		k = (new KlondikePanel());
		this.add(k);
		this.pack();
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		kg = new KlondikeGame();
	}
}
