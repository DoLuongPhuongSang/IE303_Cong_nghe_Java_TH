package BTTH2;

import javax.swing.*;
import java.awt.*;

public class Bai1 extends JFrame {

    public Bai1() {
        // Đặt tiêu đề cửa sổ
        setTitle("Flappy Bird");

        // Thiết lập kích thước cửa sổ
        setSize(360, 640);
        setResizable(false); // Không cho thay đổi kích thước

        // Thoát khi đóng cửa sổ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setContentPane(new BackgroundPanel());
        setIconImage(new ImageIcon(getClass().getResource("flappybird.png")).getImage());

        // Hiển thị cửa sổ
        setVisible(true);
    }

    // Lớp nội để vẽ ảnh nền
    static class BackgroundPanel extends JPanel {
        private final Image backgroundImage;

        public BackgroundPanel() {
            backgroundImage = new ImageIcon(getClass().getResource("flappybirdbg.png")).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Vẽ ảnh nền phủ toàn bộ panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Hàm main để chạy chương trình
    public static void main(String[] args) {
        new Bai1();
    }
}
