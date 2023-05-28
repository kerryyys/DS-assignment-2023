package MilargoMan;

public class Sales extends Food{
        private int quantity;
        private int day;

        public Sales(String name, double price, int day, int quantity){
            super(name,price);
            this.day=day;
            this.quantity=quantity;
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