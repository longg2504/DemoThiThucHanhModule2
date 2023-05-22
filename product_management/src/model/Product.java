package model;

public class Product implements IModel<Product> {
    private int idProduct;
    private String name;
    private float price;
    private int quantity;
    private String describe;

    public Product(){

    }

    public Product(int id, String name, float price, int quantity, String describe) {
        this.idProduct = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.describe = describe;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public void parseData(String line) {
        //int id, String name, float price, float quantity, String describe //
        String[] items = line.split(",");
        idProduct = Integer.parseInt(items[0]);
        name = items[1];
        price = Float.parseFloat(items[2]);
        quantity = Integer.parseInt(items[3]);
        describe=items[4];

    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s",this.idProduct,this.name,this.price,this.quantity,this.describe);
    }
}
