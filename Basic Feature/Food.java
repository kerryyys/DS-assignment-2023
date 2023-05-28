package MilargoMan;

class Food {
        String name;
        double price;

        public Food(String name, double price) {
            this.name = name;
            this.price = price;
        }
        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public void printFoodInfo(){
            System.out.printf("%-40s ($%5.2f)",getName(),getPrice());
            System.out.println("");
        }
}
