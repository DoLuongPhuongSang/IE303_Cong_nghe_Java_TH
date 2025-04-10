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

public class Bai3 extends JFrame {

    public Bai3() {
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
        new Bai3();
    }

    // Panel game
    static class FlappyPanel extends JPanel implements ActionListener {
        private final Image background;
        private final Image bird;
        private final Image topPipeImg;
        private final Image bottomPipeImg;

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

            timer = new Timer(30, this);  //Game loop
            timer.start();
        }

        private void setupKeyBindings() {
            getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "jump");
            getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "jump");

            getActionMap().put("jump", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    velocity = JUMP;
                    System.out.println("Jump triggered!");
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (background != null) {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, getWidth(), getHeight());
            }

            g.drawImage(bird, 50, birdY, 40, 40, this);
            for (Pipe pipe : pipes) {
                pipe.draw(g);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            velocity += GRAVITY;
            birdY += velocity;

            if  (birdY > getHeight() - 40) {
                birdY = getHeight() - 40;
                velocity = 0;
            }

            if (birdY < 0) {
                birdY = 0;
                velocity = 0;
            }
            // Di chuyển ống và sinh ống mới
            pipeTimer += 30;
            if (pipeTimer >= 1500) {
                int gapY = 100 + rand.nextInt(300); // Vị trí khoảng trống ngẫu nhiên
                int gapHeight = 140; // Khoảng cách giữa hai ống
                pipes.add(new Pipe(getWidth(), gapY, gapHeight));
                pipeTimer = 0;
            }

            Iterator<Pipe> iter = pipes.iterator();
            while (iter.hasNext()) {
                Pipe pipe = iter.next();
                pipe.move();
                if (pipe.isOffscreen()) {
                    iter.remove();
                }
            }
            repaint();
        }
        class Pipe {
            private int x;
            private final int gapY;
            private final int gapHeight;
            private final int width = 52;

            public Pipe(int x, int gapY, int gapHeight) {
                this.x = x;
                this.gapY = gapY;
                this.gapHeight = gapHeight;
            }

            public void move() {
                x -= 3; // Tốc độ ống
            }

            public boolean isOffscreen() {
                return x + width < 0;
            }

            public void draw(Graphics g) {
                // Vẽ ống trên
                g.drawImage(topPipeImg, x, 0, width, gapY, null);
                // Vẽ ống dưới
                g.drawImage(bottomPipeImg, x, gapY + gapHeight, width, getHeight() - (gapY + gapHeight), null);
            }
        }
    }
}
