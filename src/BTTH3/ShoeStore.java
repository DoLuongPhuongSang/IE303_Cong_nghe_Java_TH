package BTTH3;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class ShoeStore extends JFrame {

    private final JLabel mainImageLabel;
    private final JLabel nameLabel;
    private final JLabel brandLabel;
    private final JLabel priceLabel;
    private final JLabel descLabel;
    private final List<Product> productList = new ArrayList<>();

    public ShoeStore() {
        setTitle("Shoe Store");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // Đặt màu nền cho JFrame

        // Khởi tạo các label
        mainImageLabel = new JLabel();
        nameLabel = new JLabel();
        brandLabel = new JLabel();
        priceLabel = new JLabel();
        descLabel = new JLabel();

        loadProducts();

        // Panel trái (thông tin sản phẩm chính)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(300, getHeight()));
        // leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainImageLabel.setPreferredSize(new Dimension(250, 250));

        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        brandLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        brandLabel.setForeground(Color.DARK_GRAY); 
        
        descLabel.setFont(new Font("Arial", Font.BOLD, 10)); 
        descLabel.setForeground(Color.GRAY); 
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descLabel.setVerticalAlignment(SwingConstants.TOP);
        descLabel.setPreferredSize(new Dimension(250, 100));

        leftPanel.add(mainImageLabel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(nameLabel);
        leftPanel.add(priceLabel);
        leftPanel.add(brandLabel);
        leftPanel.add(descLabel);

        add(leftPanel, BorderLayout.WEST);

        // Panel phải (danh sách sản phẩm)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(4, 4, 10, 10)); // 4 hàng, 4 cột, khoảng cách 10 px

        int totalSlots = 16; // 4x4
        int productCount = productList.size();

        for (int i = 0; i < totalSlots; i++) {
            int index = i % productCount; // vòng lặp 6 sản phẩm
            Product p = productList.get(index);
            ProductPanel panel = new ProductPanel(p, () -> updateMainProduct(p));
            rightPanel.add(panel);
        }

        JScrollPane scrollPane = new JScrollPane(rightPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // Thiết lập viền màu trắng
        add(scrollPane, BorderLayout.CENTER);

        // Hiển thị sản phẩm đầu tiên
        updateMainProduct(productList.get(0));
        leftPanel.setBackground(Color.WHITE); // Đặt màu nền cho panel trái
        rightPanel.setBackground(Color.WHITE); // Đặt màu nền cho panel phải

    }

    private void loadProducts() {
        productList.add(new Product("4DFWD PULSE SHOES", "Adidas", 160.00, "img1.png", "This product is excluded from all promotional discounts and offers."));
        productList.add(new Product("FORUM MID SHOES", "Adidas", 100.00, "img2.png", "This product is excluded from all promotional discounts and offers."));
        productList.add(new Product("SUPERNOVA SHOES", "Adidas", 150.00, "img3.png", "NMD City Stock 2"));
        productList.add(new Product("NMD CITY", "Adidas", 160.00, "img4.png", "NMD City Stock 2"));
        productList.add(new Product("RED PULSE SHOES", "Adidas", 120.00, "img5.png", "NMD City Stock 2"));
        productList.add(new Product("GREEN PULSE SHOES", "Adidas", 160.00, "img6.png", "This product is excluded from all promotional discounts and offers."));
    }

    private void updateMainProduct(Product p) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/BTTH3/" + p.getImagePath()));
            Image img = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            mainImageLabel.setIcon(new ImageIcon(img));
            mainImageLabel.setText(""); // xóa chữ nếu có
        } catch (Exception e) {
            mainImageLabel.setIcon(null);
            mainImageLabel.setText("Image not found");
        }

        nameLabel.setText(p.getName());
        brandLabel.setText(p.getBrand()); 
        DecimalFormat df = new DecimalFormat("#.00");
        priceLabel.setText("$" + df.format(p.getPrice())); 
        descLabel.setText("<html><div style='width: 200px;'>" + p.getDescription() + "</div></html>");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ShoeStore().setVisible(true));
    }
}
