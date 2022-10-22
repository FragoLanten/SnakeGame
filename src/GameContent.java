import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameContent extends JPanel implements ActionListener {
    private final int FIELD_SIZE = 420;
    private final int BLOCK_SIZE = 20;
    private final int ALL_BLOCKS = 420;
    public int[] x = new int[ALL_BLOCKS];
    public int[] y = new int[ALL_BLOCKS];
    public int snakeLength;
    public int appleX;
    public int appleY;
    public Image snakeImage;
    public Image appleImage;
    public Image wallImage;
    boolean directionLeft = false;
    boolean directionRight = true;
    boolean directionUp = false;
    boolean directionDown = false;
    boolean gameOver = false;

    public GameContent() {
        setBackground(Color.BLACK);
        loadImages();
        startGame();
        addKeyListener(new GameKeyListener());
        setFocusable(true);
    }

    public void startGame() {
        Timer timer = new Timer(200,this);
        timer.start();
        generateSnake();
        generateApple();

    }

    public void generateSnake() {
        snakeLength = 3;
        for (int i = 0; i < snakeLength; i++) {
            x[i]=BLOCK_SIZE*4-i*BLOCK_SIZE;
            y[i]=BLOCK_SIZE*4;
        }
    }

    public void generateApple() {
        appleX = new Random().nextInt(20)*BLOCK_SIZE+BLOCK_SIZE;
        appleY = new Random().nextInt(20)*BLOCK_SIZE+BLOCK_SIZE;
    }

    public void loadImages() {
        ImageIcon appleIcon = new ImageIcon("apple.png");
        appleImage = appleIcon.getImage();
        ImageIcon snakeIcon = new ImageIcon("snake.png");
        snakeImage = snakeIcon.getImage();
        ImageIcon wallIcon = new ImageIcon("wall.png");
        wallImage = wallIcon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!gameOver) {
            for (int i = 0; i <= FIELD_SIZE + BLOCK_SIZE; i+=BLOCK_SIZE) {
                g.drawImage(wallImage,i,0,this);
                g.drawImage(wallImage,0,i,this);
                g.drawImage(wallImage,FIELD_SIZE+BLOCK_SIZE,i,this);
                g.drawImage(wallImage, i,FIELD_SIZE+BLOCK_SIZE,this);
            }

            g.drawImage(appleImage, appleX, appleY, this);
            for (int i = 0; i < snakeLength; i++) {
                g.drawImage(snakeImage, x[i], y[i], this);
            }
        }
        else {
            String str = "Game Over";
            g.setColor(Color.white);
            g.drawString(str, FIELD_SIZE/2 - 50, FIELD_SIZE / 2);
        }
    }

    public void snakeMove() {
        for (int i = snakeLength; i > 0; i--) {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if (directionRight) {
            x[0]=x[0]+BLOCK_SIZE;
        }
        else if (directionLeft) {
            x[0]=x[0]-BLOCK_SIZE;
        }
        else if (directionUp) {
            y[0]=y[0]-BLOCK_SIZE;
        }
        else if (directionDown) {
            y[0]=y[0]+BLOCK_SIZE;
        }
    }

    public void appleWasEaten() {
        if (x[0] == appleX && y[0]==appleY) {
            snakeLength++;
            generateApple();
        }
    }

    public void collisionOccurred() {
        if (snakeLength>4) {
            for (int i = 1; i < snakeLength; i++) {
                if (x[0]==x[i] && y[0]==y[i]) {
                    gameOver = true;
                }
            }
        }

        if (x[0]>FIELD_SIZE) {
            gameOver = true;
        }
        if (x[0]<BLOCK_SIZE) {
            gameOver = true;
        }
        if (y[0]>FIELD_SIZE) {
            gameOver = true;
        }
        if (y[0]<BLOCK_SIZE) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            snakeMove();
            appleWasEaten();
            collisionOccurred();
        }
        repaint();
    }

    class GameKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key==KeyEvent.VK_RIGHT && !directionLeft) {
                directionRight = true;
                directionUp = false;
                directionDown = false;
            }
            if (key==KeyEvent.VK_LEFT && !directionRight) {
                directionLeft = true;
                directionUp = false;
                directionDown = false;
            }
            if (key==KeyEvent.VK_UP && !directionDown) {
                directionLeft = false;
                directionUp = true;
                directionRight = false;
            }
            if (key==KeyEvent.VK_DOWN && !directionDown) {
                directionLeft = false;
                directionDown = true;
                directionRight = false;
            }

        }
    }

}
