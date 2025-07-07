import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

class Product {
    int id;
    String name;
    double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

class CartItem {
    Product product;
    int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}

class Cart {
    List<CartItem> items = new ArrayList<>();

    public void addProduct(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.product.id == product.id) {
                item.quantity += quantity;
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    public void removeProduct(int productId) {
        items.removeIf(item -> item.product.id == productId);
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.product.price * item.quantity;
        }
        return total;
    }
}

public class ECommerceSwingApp extends JFrame {
    private List<Product> products = new ArrayList<>();
    private Cart cart = new Cart();

    private JTable productTable;
    private JTable cartTable;
    private DefaultTableModel cartTableModel;

    public ECommerceSwingApp() {
        setTitle("E-Commerce Cart System");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        products.add(new Product(1, "Laptop", 50000));
        products.add(new Product(2, "Headphones", 2000));
        products.add(new Product(3, "Smartphone", 25000));
        products.add(new Product(4, "Mouse", 500));
        products.add(new Product(5, "Keyboard", 800));

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Product List Panel
        String[] columns = {"ID", "Name", "Price"};
        String[][] data = new String[products.size()][3];
        for (int i = 0; i < products.size(); i++) {
            data[i][0] = String.valueOf(products.get(i).id);
            data[i][1] = products.get(i).name;
            data[i][2] = "₹" + products.get(i).price;
        }
        productTable = new JTable(data, columns);
        JScrollPane productScroll = new JScrollPane(productTable);

        JButton addButton = new JButton("Add to Cart");
        addButton.addActionListener(e -> addToCart());

        JButton viewCartButton = new JButton("View Cart");
        viewCartButton.addActionListener(e -> showCart());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(viewCartButton);

        add(productScroll, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addToCart() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to add.");
            return;
        }

        int productId = Integer.parseInt((String) productTable.getValueAt(selectedRow, 0));
        Product selected = null;
        for (Product p : products) {
            if (p.id == productId) {
                selected = p;
                break;
            }
        }

        String qtyStr = JOptionPane.showInputDialog(this, "Enter quantity:");
        int qty;
        try {
            qty = Integer.parseInt(qtyStr);
            if (qty <= 0) throw new NumberFormatException();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid quantity.");
            return;
        }

        cart.addProduct(selected, qty);
        JOptionPane.showMessageDialog(this, selected.name + " added to cart!");
    }

    private void showCart() {
        JDialog cartDialog = new JDialog(this, "Your Cart", true);
        cartDialog.setSize(600, 400);
        cartDialog.setLocationRelativeTo(this);

        String[] columns = {"ID", "Name", "Price", "Quantity", "Subtotal"};
        cartTableModel = new DefaultTableModel(columns, 0);

        for (CartItem item : cart.items) {
            double subtotal = item.product.price * item.quantity;
            cartTableModel.addRow(new Object[]{
                item.product.id,
                item.product.name,
                "₹" + item.product.price,
                item.quantity,
                "₹" + subtotal
            });
        }

        cartTable = new JTable(cartTableModel);
        JScrollPane cartScroll = new JScrollPane(cartTable);

        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> removeFromCart());

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(e -> checkout());

        JPanel panel = new JPanel();
        panel.add(removeButton);
        panel.add(checkoutButton);

        cartDialog.add(cartScroll, BorderLayout.CENTER);
        cartDialog.add(panel, BorderLayout.SOUTH);

        cartDialog.setVisible(true);
    }

    private void removeFromCart() {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to remove.");
            return;
        }
        int productId = (int) cartTableModel.getValueAt(selectedRow, 0);
        cart.removeProduct(productId);
        cartTableModel.removeRow(selectedRow);
        JOptionPane.showMessageDialog(this, "Item removed from cart.");
    }

    private void checkout() {
        double total = cart.getTotal();
        JOptionPane.showMessageDialog(this, "Checkout successful! Total: ₹" + total);
        cart.items.clear();
        cartTableModel.setRowCount(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ECommerceSwingApp().setVisible(true));
    }
}
