package BTTH2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import BTTH2.Bai2.FlappyPanel;

public class Bai2 extends JFrame {

    public Bai2() {
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
        new Bai2();
    }

    // Panel game
    static class FlappyPanel extends JPanel implements ActionListener {
        private final Image background;
        private final Image bird;
        private int birdY = 250; 
        private int velocity = 0;
        private final int GRAVITY = 2;
        private final int JUMP = -13; 
        private final Timer timer;

        public FlappyPanel() {
            background = new ImageIcon(getClass().getResource("flappybirdbg.png")).getImage();
            bird = new ImageIcon(getClass().getResource("flappybird.png")).getImage();

            setFocusable(true);
            setupKeyBindings();

            timer = new Timer(30, this); 
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
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            velocity += GRAVITY;
            birdY += velocity;

            if (birdY > getHeight() - 40) {
                birdY = getHeight() - 40;
                velocity = 0;
            }

            if (birdY < 0) {
                birdY = 0;
                velocity = 0;
            }

            repaint();
        }
    }
}
