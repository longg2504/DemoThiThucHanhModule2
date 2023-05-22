package view;

import comparator.ComparatorByPrice;
import comparator.ComparatorByPriceDecrease;
import model.Product;
import service.ProductService;
import utils.CSVUtils;
import utils.ValidateUtils;

import java.awt.*;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class ProductManagerView {
    private static final String PATH = "./src/data/product.csv";
    private ProductService productService;
    private  static List<Product> productList =  CSVUtils.readFile(PATH,Product.class);
    public  List<Product> getProductList(){
        return productList;
    }

    public ProductManagerView() {
    }

    public static Scanner scanner = new Scanner(System.in);

    public void menuProduct() {
        productService = new ProductService();
        System.out.println("----------------------- CHƯƠNG TRÌNH QUẢN LÝ SẢN PHẨM -----------------------");
        System.out.println("Chọn chức năng theo số (để tiếp tục");
        System.out.println("1.Xem danh sách");
        System.out.println("2.Thêm mới");
        System.out.println("3.Cập nhật");
        System.out.println("4.Xoá");
        System.out.println("5.Sắp xếp");
        System.out.println("6.Tìm sản phẩm có giá đắt nhất");
        System.out.println("7.Đọc từ file");
        System.out.println("8.Ghi vào file");
        System.out.println("9.Thoát");
        System.out.println(" Chọn chức năng:");
    }

    public void ProductManagementView(){
        boolean checkAction = false;

        do{
            menuProduct();
            try{
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice){
                    case 1:
                        productService.showFiveProduct();
                        break;
                    case 2:
                        showCreateroduct();
                        break;
                    case 3:
                        editProduct();
                        break;
                    case 4:
                        showProduct(productList);
                        removeProduct();
                        break;
                    case 5:
                        showSort();
                        break;
                    case 6:
                        findMaxPrice();
                        break;
                    case 7:
                        System.out.println("Bạn có chắc chắn đọc file không");
                        System.out.println("1.có");
                        System.out.println("2.không");
                        int ActionCheck = Integer.parseInt(scanner.nextLine());
                        switch (ActionCheck){
                            case 1:
                                CSVUtils.readFile(PATH,Product.class);
                                checkAction = checkAgaint();
                            case 2:
                                break;
                        }
                        checkAction = checkAgaint();
                        break;
                    case 8:
                        System.out.println("Bạn có chắc chắn lưu không");
                        System.out.println("1.có");
                        System.out.println("2.không");
                        int choiceAction = Integer.parseInt(scanner.nextLine());
                        switch (choiceAction){
                            case 1:
                                CSVUtils.writeFile(PATH,productList);
                                checkAction = checkAgaint();
                            case 2:
                                break;
                        }
                        checkAction = checkAgaint();
                        break;
                    case 9:
                        System.out.println("Bye Bye");
                        checkAction = false;
                        break;
                    default:
                        System.out.println("chức năng chọn sai vui lòng chọn lại");
                        break;
                }
            }catch (NumberFormatException e){
                System.out.println("nhập sai định dạng chức năng muốn chọn vui lòng nhập lại");
                checkAction= true;
            }
        }while (checkAction);
        if (!checkAction) {
            ProductManagerView productManagerView = new ProductManagerView();
            productManagerView.ProductManagementView();
        }
    }

    private void removeProduct() {
        boolean checkIDProdcut = true;
        int idProduct = 0;
        do {
            System.out.println("Nhập ID sản phẩm muốn xoá");
            try{
                 idProduct = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("ID nhập vào không hợp lệ vui lòng nhập lại");
                continue;
            }
            if(idProduct <= 0){
                System.out.println("ID nhập vào phải lớn hơn 0");
                checkIDProdcut = true;
                }
                else{
                if(productService.isProductExist(idProduct)){
                    productService.deleteProduct(idProduct);
                    System.out.println("Đã xoá thành công");
                    showProduct(productList);
                    checkIDProdcut = checkAgaint();
                }
            }

        }while (checkIDProdcut);
    }

    private void showSort(){
        boolean checkAction = false;
        do {
            System.out.println("1.Sắp xếp Tăng dần");
            System.out.println("2.Sắp xếp Giảm dần");
            System.out.println("3.Exit");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1:
                    sortByPrice();
                    checkAction = checkAgaint();
                    break;
                case 2:
                    sortByPriceDEC();
                    checkAction = checkAgaint();
                    break;
                case 3:
                    checkAction = false;
                    break;
                default:
                    System.out.println("Nhập sai định dạng vui lòng nhập lại");
                    checkAction = false;
                    break;
            }
        }while (checkAction);
    }

    private void sortByPrice() {
//        Comparator comparator = new ComparatorByPrice();
        List<Product> product = productList;
        product.sort(new ComparatorByPrice());
        showProduct(product);
    }

    private void sortByPriceDEC(){
        List<Product> product = productList;
        product.sort(new ComparatorByPriceDecrease());
        showProduct(product);
    }

    private void findMaxPrice(){
        List<Product> product = productList;
        product.sort(new ComparatorByPriceDecrease());
        System.out.println(product.get(0));
    }


    public void showProduct(List<Product> listProduct) {
        //int idProduct, String name, float price, float quantity, String describe //
        System.out.println("+---------------+------------------------------+------------+---------------+----------------------+");
        System.out.printf("| %-13s | %-28s | %-10s | %-13s | %-20s |\n",
                "idProduct", "Name", "Price", "Quantity", "describe");
        System.out.println("+---------------+------------------------------+------------+---------------+----------------------+");
        for (Product p : listProduct) {
            //int idProduct, String name, float price, float quantity, String describe //
            System.out.printf("| %-13s | %-28s | %-10s | %-13s | %-20s |\n", p.getIdProduct(), p.getName(), p.getPrice(),
                    p.getQuantity(), p.getDescribe());
            System.out.println("+---------------+------------------------------+------------+---------------+----------------------+");
        }
    }

    private void showCreateroduct() {
        boolean checkAction = true;
        do{
            System.out.println("Chức Năng Thêm Sản Phẩm");
            //nhập id
            boolean checkIDProduct = true;
            int idProduct = 0;
            do {
                System.out.println("Nhập ID của sản phẩm");
                try {
                    idProduct = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException E) {
                    System.out.println("nhập sai định dạng vui lòng nhập lại");
                    continue;
                }
                if (idProduct <= 0) {
                    System.out.println("ID nhập vào phải lớn hơn 0");
                    checkIDProduct = true;
                } else {
                    if (productService.isProductExist(idProduct)) {
                        System.out.println("ID này đã có trong danh sách vui lòng nhập ID khác");
                        checkIDProduct = true;
                    } else {
                        checkIDProduct = false;
                    }
                }
            } while (checkIDProduct);
            // nhập tên
            boolean checkName = false;
            String name = null;
            do {
                System.out.println("Nhập tên của sản phẩm");
                name = scanner.nextLine();
                if (ValidateUtils.isStringValid(name)) {
                    if(productService.isProductNameExist(name)) {
                        System.out.println("Tên sản phẩm này đã có trong danh sách vui lòng không nhập lại");
                        checkName = true;
                    }else {
                        checkName = false;
                    }
                }
            } while (checkName);
            //nhập giá
            boolean checkPrice = true;
            float price = 0;
            do {
                System.out.println("Nhập giá tiền");
                try {
                    price = Float.parseFloat(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Nhập sai định dạng giá tiền vui lòng nhập lại");
                    continue;
                }
                if (price <= 0) {
                    System.out.println("giá tiền nhập phải lớn hơn 0 ");
                    checkPrice = true;
                } else {
                    checkPrice = false;
                }
            } while (checkPrice);
            //nhập số lượng
            boolean checkQuantity = true;
            int quantity = 0;
            do {
                System.out.println("Nhập số lượng");
                try {
                    quantity = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Nhập sai định dạng số lượng vui lòng nhập lại");
                    continue;
                }
                if (price <= 0) {
                    System.out.println("số lượng nhập phải lớn hơn 0 ");
                    checkQuantity = true;
                } else {
                    checkQuantity = false;
                }
            } while (checkQuantity);

            //nhập mô tả
            boolean checkDescribe = false;
            String describe = null;
            do{
                System.out.println("Nhập mô tả của sản phẩm");
                describe = scanner.nextLine();
                if (ValidateUtils.isStringValid(describe)) {
                    checkDescribe = false;
                } else {
                    System.out.println("Nhập sai định dạng vui lòng nhập lại");
                    checkDescribe = true;
                }
            }while(checkName);

            Product product =new Product(idProduct,name,price,quantity,describe);
            productService.addProduct(product);
            System.out.println("Đã thêm sản phẩm thành công");
            showProduct(productList);
            checkAction = checkAgaint();
        }while (checkAction);
    }

    public void editProduct(){
        Product product = inputIdProduct();
        if (product != null) {
            boolean checkActionEdit;
            do {
                //int id, String name, EGender gender, Date bod, String address, EClass eClass //
                checkActionEdit = false;
                System.out.println("Bạn muốn sửa thông tin gì: ");
                System.out.println("1.Tên sản phẩm");
                System.out.println("2.Giá");
                System.out.println("3.Số Lượng");
                System.out.println("4.Mô tả");
                System.out.println("5.Exits");
                int actionEdit = Integer.parseInt(scanner.nextLine());
                switch (actionEdit) {
                    case 1:
                        inputNameProduct(product);
                        break;
                    case 2:
                        inputPriceProduct(product);
                        break;
                    case 3:
                        inputQuantityProduct(product);
                        break;
                    case 4:
                        inputDescribeProduct(product);
                        break;
                    case 5:
                        checkActionEdit = false;
                        break;
                    default:
                        System.out.println("Nhập sai. Vui lòng nhập lại ");
                        checkActionEdit = true;
                        break;
                }
            } while (checkActionEdit);
        }
    }

    private void inputDescribeProduct(Product product) {
        String describe = null;
        boolean checkDescribe = false;
        do {
            System.out.println("Thông tin Sản phẩm: ");
            System.out.println(product);

            System.out.println("Nhập mô tả mới: ");
            describe = scanner.nextLine();
            if (ValidateUtils.isStringValid(describe)) {
                product.setDescribe(describe);
                productService.editProduct(product.getIdProduct(), product);

                System.out.println("Sửa thành công: ");
                System.out.println(product);
                checkDescribe = checkAgaint();
            } else {
                System.out.println("mô tả không hợp lệ vui lòng nhập lại");
                checkDescribe = true;
            }
        } while (checkDescribe);
    }

    private void inputQuantityProduct(Product product) {
        int quantity = 0;
        boolean checkQuantity = true;
        do{
            System.out.println("Thông tin Sản phẩm: ");
            System.out.println(product);

            System.out.println("Nhập số lượng mới");
            try{
                quantity = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("số lượng nhập vào không hợp lệ vui lòng nhập lại");
                continue;
            }
            if(quantity <=0){
                System.out.println("số lượng nhập vào phải lớn hơn 0 vui lòng nhập lại");
                checkQuantity = true;
            }
            else{
                product.setQuantity(quantity);
                productService.editProduct(product.getIdProduct(),product);
                System.out.println("Sửa thành công: ");
                System.out.println(product);
                checkQuantity = checkAgaint();
            }
        }while (checkQuantity);
    }

    private void inputPriceProduct(Product product) {
        float price = 0;
        boolean checkPrice = true;
        do{
            System.out.println("Thông tin Sản phẩm: ");
            System.out.println(product);

            System.out.println("Nhập giá mới");
            try{
                price = Float.parseFloat(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("giá nhập vào không hợp lệ vui lòng nhập lại");
                continue;
            }
            if(price <=0){
                System.out.println("giá nhập vào phải lớn hơn 0 vui lòng nhập lại");
                checkPrice = true;
            }
            else{
                product.setPrice(price);
                productService.editProduct(product.getIdProduct(),product);
                System.out.println("Sửa thành công: ");
                System.out.println(product);
                checkPrice = checkAgaint();
            }
        }while (checkPrice);
    }

    private void inputNameProduct(Product product) {
        String name = null;
        boolean checkName = false;
        do {
            System.out.println("Thông tin Sản phẩm: ");
            System.out.println(product);

            System.out.println("Nhập tên mới: ");
            name = scanner.nextLine();
            if (ValidateUtils.isStringValid(name)) {
                product.setName(name);
                productService.editProduct(product.getIdProduct(), product);

                System.out.println("Sửa thành công: ");
                System.out.println(product);
                checkName = checkAgaint();
            }
        } while (checkName);

    }


    private Product inputIdProduct() {
        Product product = null;
        boolean checkEditProductValid = false;
        do {
            try {
                System.out.println("Nhập ID sản phẩm bạn muốn sửa");
                int idProduct = Integer.parseInt(scanner.nextLine());
                product = productService.findProductByID(idProduct);
                if (product == null) {
                    System.out.println("ID sả phẩm không hợp lệ");
                    System.out.println("Chọn 1. Nhập lại");
                    System.out.println("Chọn 2. Quay lại");
                    int actionEditId = Integer.parseInt(scanner.nextLine());
                    switch (actionEditId) {
                        case 1:
                            checkEditProductValid = true;
                            break;
                        case 2:
                            checkEditProductValid = false;
                            break;
                    }
                }
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Id không đúng định dạng vui lòng nhập lại");
                checkEditProductValid = true;
            }
        } while (checkEditProductValid);
        return product;
    }

    public boolean checkAgaint() {
        boolean checkActionContinue = false;
        do {
            System.out.println("Nhập \"Y\" để tiếp tục, nhập \"N\" để quay về giao diện menu trước");
            String choice = scanner.nextLine().trim().toUpperCase();
            switch (choice) {
                case "Y":
                    return true;
                case "N":
                    return false;
                default:
                    System.out.println("Nhập sai lựa chọn");
                    checkActionContinue = false;
                    break;
            }
        } while (!checkActionContinue);
        return true;
    }




}
