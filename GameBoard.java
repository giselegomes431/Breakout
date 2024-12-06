import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class GameBoard extends JPanel implements ActionListener {

    private Timer timer;
    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    private int score;
    private int lives;
    private boolean gameOver;
    private boolean gameWon;
    private boolean gameStarted;
    private JButton startButton;
    private JButton restartButton;

    final int SCREEN_WIDTH = 789; // Largura da tela
    final int COLS = 10; // Número de colunas
    final int ROWS = 5; // Número de linhas
    final int BRICK_WIDTH = SCREEN_WIDTH / COLS - 5; // Largura do bloco ajustada
    final int BRICK_HEIGHT = 20; // Altura fixa do bloco
    final int BRICK_SPACING = 5; // Espaçamento entre blocos
    private final Color[] BRICK_COLORS = { Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.CYAN };
    private final int[] BRICK_SCORES = { 50, 40, 30, 20, 10 };

    public GameBoard() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);

        score = 0;
        lives = 3;
        gameOver = false;
        gameWon = false;
        gameStarted = false;

        ball = new Ball();
        paddle = new Paddle();
        bricks = new Brick[ROWS * COLS];

        int k = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                bricks[k] = new Brick(j * (BRICK_WIDTH + BRICK_SPACING), 
                                      i * (BRICK_HEIGHT + BRICK_SPACING),
                                      BRICK_COLORS[i], 
                                      BRICK_SCORES[i]);
                k++;
            }
        }


        timer = new Timer(10, this);

        startButton = new JButton("Start");
        startButton.setVisible(true);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start button clicked");
                startGame();
            }
        });
        add(startButton);

        restartButton = new JButton("Restart");
        restartButton.setVisible(false);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Restart button clicked");
                restartGame();
            }
        });
        add(restartButton);
    }

    private void startGame() {
        gameStarted = true;
        startButton.setVisible(false);
        timer.start();
        System.out.println("Game started");
    }

    private void restartGame() {
        score = 0;
        lives = 3;
        gameOver = false;
        gameWon = false;
        gameStarted = false;
        ball = new Ball();
        paddle = new Paddle();

        int k = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                bricks[k] = new Brick(j * (BRICK_WIDTH + 5), i * (BRICK_HEIGHT + 5), BRICK_COLORS[i], BRICK_SCORES[i]);
                k++;
            }
        }

        startButton.setVisible(true);
        restartButton.setVisible(false);
        timer.stop();
        System.out.println("Game restarted");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawObjects(g);

        if (gameOver) {
            showGameOver(g);
        }

        if (gameWon) {
            showGameWon(g);
        }
    }

    private void drawObjects(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Desenhar a bola com animação
        ball.draw(g2d);

        // Desenhar o paddle
        g2d.setColor(Color.WHITE);
        g2d.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());

        // Desenhar os blocos restantes
        for (int i = 0; i < bricks.length; i++) {
            if (!bricks[i].isDestroyed()) {
                g2d.setColor(bricks[i].getColor());
                g2d.fillRect(bricks[i].getX(), bricks[i].getY(), bricks[i].getWidth(), bricks[i].getHeight());
            }
        }

        // Desenhar pontuação e vidas
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Pontuação: " + score, 10, 20);
        g2d.drawString("Vidas: " + lives, 720, 20);
    }


    private void showGameOver(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        String msg = "Game Over";
        Font font = new Font("Arial", Font.BOLD, 30);
        FontMetrics fm = getFontMetrics(font);

        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        g2d.drawString(msg, (getWidth() - fm.stringWidth(msg)) / 2, getHeight() / 2);

        restartButton.setVisible(true);
        System.out.println("Game Over");
    }

    private void showGameWon(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        String msg = "Parabéns! Você Ganhou!";
        Font font = new Font("Arial", Font.BOLD, 30);
        FontMetrics fm = getFontMetrics(font);

        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        g2d.drawString(msg, (getWidth() - fm.stringWidth(msg)) / 2, getHeight() / 2);

        restartButton.setVisible(true);
        System.out.println("Game Won");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver && !gameWon && gameStarted) {
            ball.move();
            paddle.move();
            checkCollision();
            repaint();
            System.out.println("Game Updated: Ball Position - (" + ball.getX() + ", " + ball.getY() + ")");
        }
    }

    private void checkCollision() {
        if (ball.getBounds().intersects(paddle.getBounds())) {
            int paddleLPos = (int) paddle.getBounds().getMinX();
            int ballLPos = (int) ball.getBounds().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;

            if (ballLPos < first) {
                ball.setDX(-1);
                ball.setDY(-1);
            }

            if (ballLPos >= first && ballLPos < second) {
                ball.setDX(-1);
                ball.setDY(-1 * ball.getDY());
            }

            if (ballLPos >= second && ballLPos < third) {
                ball.setDX(0);
                ball.setDY(-1);
            }

            if (ballLPos >= third && ballLPos < fourth) {
                ball.setDX(1);
                ball.setDY(-1 * ball.getDY());
            }

            if (ballLPos >= fourth) {
                ball.setDX(1);
                ball.setDY(-1);
            }

            ball.increaseSpeed();
        }

        for (int i = 0; i < bricks.length; i++) {
            if (!bricks[i].isDestroyed() && ball.getBounds().intersects(bricks[i].getBounds())) {
                ball.reverseY();
                bricks[i].setDestroyed(true);
                score += bricks[i].getScore();
                ball.increaseSpeed();
                System.out.println("Brick Destroyed: Score - " + score);

                if (allBricksDestroyed()) {
                    gameWon = true;
                    timer.stop();
                }
            }
        }

        if (ball.getY() >= 600) {
            lives--;

            if (lives == 0) {
                gameOver = true;
                timer.stop();
            } else {
                ball = new Ball();
                paddle = new Paddle();
                System.out.println("Ball Reset");
            }
        }
    }

    private boolean allBricksDestroyed() {
        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            paddle.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            paddle.keyReleased(e);
        }
    }
}
