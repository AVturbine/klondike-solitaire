import javax.swing.JFrame;

public class KlondikeGame extends JFrame {
	
	private static final long serialVersionUID = 8008135L;

	public KlondikeGame() {
		super("Klondike Solitaire");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(new KlondikePanel());
		this.pack();
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new KlondikeGame();
	}
}
