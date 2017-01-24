import javax.swing.JFrame;

public class KlondikeGame extends JFrame {
	
	private static final long serialVersionUID = 8008135L;
	public KlondikePanel k;
	public static KlondikeGame kg;
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
