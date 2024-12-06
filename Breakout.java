import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Breakout extends JFrame {

    public Breakout() {
        initUI();
    }

    private void initUI() {
        add(new GameBoard());
        setTitle("Breakout");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(815, 600);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Breakout breakout = new Breakout();
            breakout.setVisible(true);
        });
    }
}
