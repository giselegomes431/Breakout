import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Paddle {

    private int x;
    private int y;
    private int dx;
    private final int PADDLE_WIDTH = 100;
    private final int PADDLE_HEIGHT = 10;
    private final int PADDLE_SPEED = 4; // Velocidade ajustável
    final int SCREEN_WIDTH = 800;

    public Paddle() {
        x = 350;
        y = 550;
        System.out.println("Paddle created at (" + x + ", " + y + ")");
    }

    public void move() {
        x += dx;

        if (x <= 0) {
            x = 0;
        }

        if (x >= SCREEN_WIDTH - PADDLE_WIDTH) { // Ajuste baseado na largura da tela
            x = SCREEN_WIDTH - PADDLE_WIDTH;
        }


        System.out.println("Paddle moved to (" + x + ", " + y + ") with dx = " + dx);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -PADDLE_SPEED;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = PADDLE_SPEED;
        }

        System.out.println("Paddle key pressed: " + key);
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        System.out.println("Paddle key released: " + key);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return PADDLE_WIDTH;
    }

    public int getHeight() {
        return PADDLE_HEIGHT;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
    }
}
