import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {
    private static final String FILENAME = "products.csv";
    private ArrayList<Product> inventory = new ArrayList<>();
    private ArrayList<Product> cart = new ArrayList<>();
    private double totalAmount = 0.0;

    public static void main(String[] args) {
        Store store = new Store();
        store.loadInventory(FILENAME);
        store.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 4) {
            System.out.println("Welcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Search Products");
            System.out.println("3. Show Cart");
            System.out.println("4. Exit");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayAllProducts();
                    break;
                case 2:
                    searchProducts(scanner);
                    break;
                case 3:
                    displayCart(scanner);
                    break;
                case 4:
                    System.out.println("Thank you for shopping with us!");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    public void loadInventory(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0].trim();
                String name = parts[1].trim();
                double price = Double.parseDouble(parts[2].trim());
                String department = parts[3].trim();
                inventory.add(new Product(id, name, price, department));
            }
        } catch (IOException e) {
            System.err.println("Error reading inventory file: " + e.getMessage());
        }
    }

    public void displayAllProducts() {
        System.out.println("**Products**");
        System.out.println("ID\tName\tPrice\tDepartment");
        for (Product product : inventory) {
            System.out.println(product.getId() + "\t" + product.getName() + "\t$" + product.getPrice() + "\t" + product.getDepartment());
        }
    }

    public void searchProducts(Scanner scanner) {
        System.out.println("Search by: ");
        System.out.println("1. Name");
        System.out.println("2. Price");
        System.out.println("3. Department");
        int searchChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (searchChoice) {
            case 1:
                searchByName(scanner);
                break;
            case 2:
                searchByPrice(scanner);
                break;
            case 3:
                searchByDepartment(scanner);
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
    }

    public void searchByName(Scanner scanner) {
        System.out.println("Enter product name to search:");
        String searchTerm = scanner.nextLine().trim().toLowerCase();
        boolean found = false;
        for (Product product : inventory) {
            if (product.getName().toLowerCase().contains(searchTerm)) {
                System.out.println(product);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No matching products found.");
        }
    }

    public void searchByPrice(Scanner scanner) {
        System.out.println("Enter maximum price:");
        double maxPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        boolean found = false;
        for (Product product : inventory) {
            if (product.getPrice() <= maxPrice) {
                System.out.println(product);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No products found within the specified price range.");
        }
    }

    public void searchByDepartment(Scanner scanner) {
        System.out.println("Enter department to search:");
        String department = scanner.nextLine().trim().toLowerCase();
        boolean found = false;
        for (Product product : inventory) {
            if (product.getDepartment().toLowerCase().equals(department)) {
                System.out.println(product);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No products found in the specified department.");
        }
    }

    public void displayCart(Scanner scanner) {
        System.out.println("**Cart**");
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        for (Product product : cart) {
            System.out.println(product);
        }
        System.out.println("Total Amount: $" + totalAmount);
        System.out.println("1. Remove Product");
        System.out.println("2. Checkout");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        switch (choice) {
            case 1:
                removeProductFromCart(scanner);
                break;
            case 2:
                checkout(scanner);
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
    }

    public void removeProductFromCart(Scanner scanner) {
        System.out.println("Enter product ID to remove from cart:");
        String productId = scanner.nextLine();
        Product productToRemove = findProductById(productId, cart);
        if (productToRemove != null) {
            cart.remove(productToRemove);
            totalAmount -= productToRemove.getPrice();
            System.out.println("Product removed from cart.");
        } else {
            System.out.println("Product not found in cart.");
        }
    }

    public void checkout(Scanner scanner) {
        System.out.println("Are you sure you want to checkout? (yes/no)");
        String answer = scanner.nextLine().trim().toLowerCase();
        if (answer.equals("yes")) {
            System.out.println("Thank you for your purchase!");
            cart.clear();
            totalAmount = 0.0;
        } else if (!answer.equals("no")) {
            System.out.println("Invalid choice!");
        }
    }

    public Product findProductById(String id, ArrayList<Product> products) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }
}

class Product {
    private String id;
    private String name;
    private double price;
    private String department;

    public Product(String id, String name, double price, String department) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.department = department;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Price: $" + price + ", Department: " + department;
    }
