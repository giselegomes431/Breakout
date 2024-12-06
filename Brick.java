import java.awt.Color;
import java.awt.Rectangle;

public class Brick {

    private int x;
    private int y;
    private boolean destroyed;
    private final int BRICK_WIDTH = 75;
    private final int BRICK_HEIGHT = 20;
    private final int BRICK_SPACING = 10;
    private Color color;
    private int score;

    public Brick(int x, int y, Color color, int score) {
        this.x = x + BRICK_SPACING;
        this.y = y + BRICK_SPACING;
        this.color = color;
        this.score = score;
        destroyed = false;
        System.out.println("Brick created at (" + x + ", " + y + ")");
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
        System.out.println("Brick destroyed at (" + x + ", " + y + ")");
    }

    public Color getColor() {
        return color;
    }

    public int getScore() {
        return score;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return BRICK_WIDTH;
    }

    public int getHeight() {
        return BRICK_HEIGHT;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, BRICK_WIDTH, BRICK_HEIGHT);
    }
}
