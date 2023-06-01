package JOJOLands;

public class Sales extends Food{
    private double Sales;
    private int quantity;
    private int day;

    public Sales_MilargoMan(String name, double price, int day, int quantity, double Sales){
        super(name,price);
        this.day=day;
        this.quantity=quantity;
        this.Sales = Sales;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void addQuantity(int quantity){
        this.quantity+=quantity;
    }
}
