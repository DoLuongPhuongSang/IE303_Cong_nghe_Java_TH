package BTTH2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class Bai4 extends JFrame {

    public Bai4() {
        // Đặt tiêu đề cửa sổ
        setTitle("Flappy Bird");

        // Thiết lập kích thước cửa sổ
        setSize(360, 640);
        setResizable(false); // Không cho thay đổi kích thước

        // Thoát khi đóng cửa sổ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Gắn panel game vào cửa sổ
        setContentPane(new FlappyPanel());

        // Đặt icon cửa sổ
        setIconImage(new ImageIcon(getClass().getResource("flappybird.png")).getImage());

        // Căn giữa màn hình
        setLocationRelativeTo(null);

        // Hiển thị cửa sổ
        setVisible(true);
    }

    // Hàm main để chạy chương trình
    public static void main(String[] args) {
        new Bai4();
    }
    static class FlappyPanel extends JPanel implements ActionListener {
        private final Image background;
        private final Image bird;
        private final Image topPipeImg;
        private final Image bottomPipeImg;
    
        private boolean isGameOver = false;
        private int score = 0;
    
        private int birdY = 250;
        private int velocity = 0;
        private final int GRAVITY = 2;
        private final int JUMP = -13;
        private final Timer timer;
    
        private final ArrayList<Pipe> pipes = new ArrayList<>();
        private int pipeTimer = 0;
        private final Random rand = new Random();
    
        public FlappyPanel() {
            background = new ImageIcon(getClass().getResource("flappybirdbg.png")).getImage();
            bird = new ImageIcon(getClass().getResource("flappybird.png")).getImage();
            topPipeImg = new ImageIcon(getClass().getResource("toppipe.png")).getImage();
            bottomPipeImg = new ImageIcon(getClass().getResource("bottompipe.png")).getImage();
    
            setFocusable(true);
            setupKeyBindings();
    
            timer = new Timer(30, this);
            timer.start();
        }
    
        private void restartGame() {
            birdY = 250;
            velocity = 0;
            pipes.clear();
            score = 0;
            pipeTimer = 0;
            isGameOver = false;
            timer.start();
            repaint();
        }
    
        private void setupKeyBindings() {
            getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "jump");
            getActionMap().put("jump", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!isGameOver) velocity = JUMP;
                }
            });
    
            getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("R"), "restart");
            getActionMap().put("restart", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (isGameOver) {
                        restartGame();
                    }
                }
            });
        }
    
        private boolean birdCollidesWith(Pipe pipe) {
            int birdX = 50, birdWidth = 40, birdHeight = 40;
            return birdX + birdWidth > pipe.x && birdX < pipe.x + pipe.width &&
                   (birdY < pipe.gapY || birdY + birdHeight > pipe.gapY + pipe.gapHeight);
        }
    
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            g.drawImage(bird, 50, birdY, 40, 40, this);
            for (Pipe pipe : pipes) {
                pipe.draw(g);
            }
    
            g.setColor(Color.BLACK);
            g.drawString("Score: " + score, 10, 20);
    
            if (isGameOver) {
                g.setColor(Color.RED);
                g.drawString("GAME OVER! Press R to restart", 80, getHeight() / 2);
            }
        }
    
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isGameOver) return;
    
            velocity += GRAVITY;
            birdY += velocity;
    
            if (birdY > getHeight() - 40) {
                birdY = getHeight() - 40;
                isGameOver = true;
                timer.stop();
            }
    
            if (birdY < 0) {
                birdY = 0;
                velocity = 0;
            }
    
            pipeTimer += 30;
            if (pipeTimer >= 1500) {
                int gapY = 100 + rand.nextInt(200);
                pipes.add(new Pipe(getWidth(), gapY, 140));
                pipeTimer = 0;
            }
    
            Iterator<Pipe> iter = pipes.iterator();
            while (iter.hasNext()) {
                Pipe pipe = iter.next();
                pipe.move();
    
                if (pipe.isOffscreen()) {
                    iter.remove();
                    continue;
                }
    
                if (birdCollidesWith(pipe)) {
                    isGameOver = true;
                    timer.stop();
                }
    
                // Tính điểm khi chim vượt qua ống
                if (!pipe.scored && pipe.x + pipe.width < 50) {
                    score++;
                    pipe.scored = true;
                }
            }
    
            repaint();
        }
    
        class Pipe {
            private int x;
            private final int gapY;
            private final int gapHeight;
            private final int width = 52;
            public boolean scored = false;
    
            public Pipe(int x, int gapY, int gapHeight) {
                this.x = x;
                this.gapY = gapY;
                this.gapHeight = gapHeight;
            }
    
            public void move() {
                x -= 3;
            }
    
            public boolean isOffscreen() {
                return x + width < 0;
            }
    
            public void draw(Graphics g) {
                g.drawImage(topPipeImg, x, 0, width, gapY, null);
                g.drawImage(bottomPipeImg, x, gapY + gapHeight, width,
                            getHeight() - (gapY + gapHeight), null);
            }
        }
    }    
}
