package service;

import model.Product;
import utils.CSVUtils;
import view.ProductManagerView;

import java.util.List;
import java.util.Scanner;

public class ProductService {
    private static final String PATH = "./src/data/product.csv";
    private static final int ITEMS_PER_PAGE = 5;
    private static List<Product> list;
    private ProductManagerView productManagerView;
    public ProductService(){
        productManagerView = new ProductManagerView();
        list = productManagerView.getProductList();
    }

    private ProductService productService ;


    public Product findProductByID(int idProduct) {
        list = CSVUtils.readFile(PATH, Product.class);
        for (Product item : list) {
            if (item.getIdProduct() == idProduct) {
                return item;
            }
        }
        return null;
    }

    public void showFiveProduct(){
        int currentPage = 0;
        int totalPages = (int) Math.ceil((double) list.size() / ITEMS_PER_PAGE);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Page " + (currentPage + 1) + " ===");
            int startIndex = currentPage * ITEMS_PER_PAGE;
            int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, list.size());
            for (int i = startIndex; i < endIndex; i++) {
                System.out.println(list.get(i));
            }
            System.out.println("=== Page " + (currentPage + 1) + " ===");

            if (totalPages == 1) {
                System.out.println("No more pages available.");
                break;
            }

            System.out.print("Press Enter to view the next page (Q to quit): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("Q")) {
                break;
            }

            currentPage = (currentPage + 1) % totalPages;
        }
    }

    public void addProduct(Product product) {

        list.add(product);

    }

    public void editProduct(int idProduct, Product product) {
        for (Product item : list) {
            if (item.getIdProduct() == idProduct) {
//                int idProduct, String name, float price, float quantity, String describe
                item.setName(product.getName());
                item.setPrice(product.getPrice());
                item.setQuantity((int) product.getQuantity());
                item.setDescribe(product.getDescribe());
            }
        }

    }

    public boolean isProductExist(int idProduct) {
        for (Product items : list) {
            if (items.getIdProduct() == idProduct) {
                return true;
            }
        }
        return false;
    }
    public boolean isProductNameExist(String name) {
        for (Product items : list) {
            if (items.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public void deleteProduct(int idProduct) {
        list.removeIf(item -> item.getIdProduct() == idProduct);
    }





}
