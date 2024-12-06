import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;

public class Ball {
    private int x, y, dx, dy;
    private final int BALL_SIZE = 32; // Tamanho do sprite
    private double speed;
    private BufferedImage[] frames; // Array para armazenar os frames do sprite
    private int currentFrame; // Controle do frame atual
    private int frameDelay = 5; // Controle de troca de frames
    private int frameCounter = 0; // Contador para controlar o delay

    public Ball() {
        x = 400;
        y = 300;
        dx = 1;
        dy = 1;
        speed = 1.0;
        loadFrames(); // Carregar os sprites
    }

    private void loadFrames() {
        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResource("/sprite.png"));
            frames = new BufferedImage[30]; // Número de frames no sprite
            int frameWidth = 32;
            int frameHeight = 32;
            int framesPerRow = 5; // Quantidade de frames por linha

            for (int i = 0; i < 30; i++) {
                int x = (i % framesPerRow) * frameWidth;
                int y = (i / framesPerRow) * frameHeight;
                frames[i] = spriteSheet.getSubimage(x, y, frameWidth, frameHeight);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar o sprite.");
        }
    }

    public void draw(Graphics2D g2d) {
        // Desenhar o frame atual
        g2d.drawImage(frames[currentFrame], x, y, BALL_SIZE, BALL_SIZE, null);

        // Atualizar o frame com base no delay
        frameCounter++;
        if (frameCounter >= frameDelay) {
            currentFrame = (currentFrame + 1) % frames.length; // Loop nos frames
            frameCounter = 0;
        }
    }

    public void move() {
        x += dx * speed;
        y += dy * speed;

        if (x <= 0) dx = 1;
        if (x >= 800 - BALL_SIZE) dx = -1;
        if (y <= 0) dy = 1;
    }

    public void reverseY() {
        dy = -dy;
    }

    public void increaseSpeed() {
        speed += 0.1;
    }

    public void setDX(int dx) {
        this.dx = dx;
    }

    public void setDY(int dy) {
        this.dy = dy;
    }

    public int getDX() {
        return dx;
    }

    public int getDY() {
        return dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return BALL_SIZE;
    }

    public int getHeight() {
        return BALL_SIZE;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, BALL_SIZE, BALL_SIZE);
    }
}
