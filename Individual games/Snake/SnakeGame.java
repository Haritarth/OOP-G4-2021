import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SnakeGame extends JPanel implements KeyListener {

    static final int SCREEN_WIDTH = 700, SCREEN_HEIGHT = 600;
    static Snake snake;
    static boolean gameOver;
    static Apple apple;
    static int FPS = 25;
    static long lastTime = 0;

    static final int font_size = 30;

    public static void main(String[] args) {
        JFrame f  = new JFrame("Snake Game");
        f.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        f.setLocationRelativeTo((null));
        f.add(new SnakeGame());
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setUndecorated(true);
        f.setVisible(true);
        snake = new Snake();
        apple = new Apple(snake);
    }

    public SnakeGame() {
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        requestFocus();
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        // Set Font
        g.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        long currentTime = 0;
        while((currentTime = System.currentTimeMillis()) < lastTime + (1000.0 / FPS)) ;
        lastTime = currentTime;

        snake.move();
        checkGameOver();
        apple.snakeCollision();

        if(!gameOver) {
            snake.draw(g);
            apple.draw(g);
            g.setColor(Color.GREEN);
            g.drawString("Score: " + apple.score, 0, font_size);
        }
        else {
            g.setColor(Color.WHITE);
            g.drawString("Game Over", SnakeGame.SCREEN_WIDTH / 2, SnakeGame.SCREEN_HEIGHT / 2);
            g.drawString("Score: " + apple.getScore(), SnakeGame.SCREEN_WIDTH / 2, SnakeGame.SCREEN_HEIGHT / 2 + font_size*2);
        }

        repaint();
    }

    public void checkGameOver() {
        if(snake.getX() < 0 || snake.getX() > SCREEN_WIDTH) {
            gameOver = true;
        }
        if(snake.getY() < 0 || snake.getY() > SCREEN_HEIGHT) {
            gameOver = true;
        }
        if(snake.snakeCollision()) {
            gameOver = true;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if(!snake.isMoving()){
            if(key == KeyEvent.VK_UP || key == KeyEvent.VK_RIGHT  || key == KeyEvent.VK_DOWN) {
                System.out.println("Moving started");
                snake.setIsMoving(true);
            }
        }

        if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            if(snake.getYDir() == 0) {
                snake.setYDir(-1);
                snake.setXDir(0);
            }
        }
        if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            if(snake.getYDir() == 0) {
                snake.setYDir(1);
                snake.setXDir(0);
            }
        }
        if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            if(snake.getXDir()  == 0) {
                snake.setXDir(-1);
                snake.setYDir(0);
            }
        }
        if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            if(snake.getXDir() == 0) {
                snake.setXDir(1);
                snake.setYDir(0);
            }
        }
    }

    public void keyReleased(KeyEvent e) {

    }
}
