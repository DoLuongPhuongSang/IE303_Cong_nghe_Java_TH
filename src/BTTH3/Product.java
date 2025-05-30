package BTTH3;
public class Product {
    private final String name;
    private final String brand;
    private final double price;
    private final String imagePath;
    private final String description;

    public Product(String name, String brand, double price, String imagePath, String description) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.imagePath = imagePath;
        this.description = description;
    }

    public String getName() { return name; }
    public String getBrand() { return brand; }
    public double getPrice() { return price; }
    public String getImagePath() { return imagePath; }
    public String getDescription() { return description; }
}
