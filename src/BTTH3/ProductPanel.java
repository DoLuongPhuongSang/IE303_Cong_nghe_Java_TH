package BTTH3;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import javax.swing.*;

// Lớp Panel bo tròn
class RoundedPanel extends JPanel {
    int radius;

    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false); // Để có thể nhìn thấy hiệu ứng bo tròn
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
    }
}

public class ProductPanel extends RoundedPanel {
    private Color defaultBorderColor = Color.WHITE;
    private Color hoverBorderColor = Color.BLUE;
    private int borderThickness = 1; // Độ dày của viền

    public ProductPanel(Product product, Runnable onClick) {
        super(20); 
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 240));
        setBackground(new Color(240, 240, 240)); 
        // setBorder(BorderFactory.createLineBorder(defaultBorderColor, borderThickness));

        // Panel trên cùng: Tên sản phẩm + mô tả
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(product.getName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Thêm padding cho nameLabel

        JLabel descLabel = new JLabel("<html><div style='text-align: center;'>" + product.getDescription() + "</div></html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        descLabel.setForeground(Color.GRAY);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(nameLabel);
        topPanel.add(descLabel);

        // Ảnh sản phẩm ở giữa
        JLabel imgLabel;
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/BTTH3/" + product.getImagePath()));
            Image img = icon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
            imgLabel = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
        } catch (Exception e) {
            imgLabel = new JLabel("Image not found", SwingConstants.CENTER);
            imgLabel.setPreferredSize(new Dimension(140, 140));
        }
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Panel dưới cùng: Brand trái, giá phải
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel brandLabel = new JLabel(product.getBrand());
        brandLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        brandLabel.setForeground(Color.DARK_GRAY);

        // Sử dụng DecimalFormat để định dạng giá
        DecimalFormat df = new DecimalFormat("#.00");
        JLabel priceLabel = new JLabel("$" + df.format(product.getPrice())); // Định dạng giá
        priceLabel.setFont(new Font("Arial", Font.BOLD, 11));
        priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        bottomPanel.add(brandLabel, BorderLayout.WEST);
        bottomPanel.add(priceLabel, BorderLayout.EAST);

        // Add vào layout chính
        add(topPanel, BorderLayout.NORTH);
        add(imgLabel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onClick.run();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(hoverBorderColor, borderThickness));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(defaultBorderColor, borderThickness));
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(defaultBorderColor);
        g2d.setStroke(new BasicStroke(borderThickness));
        g2d.drawRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
    }
}
