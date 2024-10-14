abstract class Coffee {
    private String name;
    private double price;
    private double weight;
    private int quality;

    public Coffee(String name, double price, double weight, int quality) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.quality = quality;
    }

    public double getWeight() {
        return weight;
    }

    public int getQuality() {
        return quality;
    }

    public double priceToWeightRatio() {
        return price / weight;
    }

    public String toString() {
        return "Назва: " + name + ", Ціна: " + price + ", Вага: " + weight + ", Якість: " + quality;
    }
}

class CoffeeBeans extends Coffee {
    public CoffeeBeans(String name, double price, double weight, int quality) {
        super(name, price, weight, quality);
    }
}

class GroundCoffee extends Coffee {
    public GroundCoffee(String name, double price, double weight, int quality) {
        super(name, price, weight, quality);
    }
}

class InstantCoffee extends Coffee {
    public InstantCoffee(String name, double price, double weight, int quality) {
        super(name, price, weight, quality);
    }
}

class Van {
    private Coffee[] cargo;
    private double maxWeight;
    private double currentWeight;

    public Van(double maxWeight) {
        this.maxWeight = maxWeight;
        this.cargo = new Coffee[0];
        this.currentWeight = 0;
    }

    public void addCoffee(Coffee coffee) throws Exception {
        if (currentWeight + coffee.getWeight() > maxWeight) {
            throw new Exception("Перевищено вагу фургону!");
        }
        Coffee[] newCargo = new Coffee[cargo.length + 1];
        System.arraycopy(cargo, 0, newCargo, 0, cargo.length);
        newCargo[cargo.length] = coffee;
        cargo = newCargo;
        currentWeight += coffee.getWeight();
    }

    public void sortCoffeeByPriceToWeight() {
        for (int i = 0; i < cargo.length - 1; i++) {
            for (int j = 0; j < cargo.length - i - 1; j++) {
                if (cargo[j].priceToWeightRatio() > cargo[j + 1].priceToWeightRatio()) {
                    Coffee temp = cargo[j];
                    cargo[j] = cargo[j + 1];
                    cargo[j + 1] = temp;
                }
            }
        }
    }

    public Coffee[] findCoffeeByQualityRange(int minQuality, int maxQuality) {
        Coffee[] tempResult = new Coffee[cargo.length];
        int count = 0;

        for (int i = 0; i < cargo.length; i++) {
            if (cargo[i].getQuality() >= minQuality && cargo[i].getQuality() <= maxQuality) {
                tempResult[count] = cargo[i];
                count++;
            }
        }

        Coffee[] result = new Coffee[count];
        System.arraycopy(tempResult, 0, result, 0, count);

        return result;
    }

    public void displayCargo() {
        for (Coffee coffee : cargo) {
            System.out.println(coffee);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            Van van = new Van(50.0);

            Coffee coffee1 = new CoffeeBeans("Арабіка", 500, 10, 8);
            Coffee coffee2 = new GroundCoffee("Робуста", 300, 8, 6);
            Coffee coffee3 = new InstantCoffee("Nescafe", 200, 5, 5);

            van.addCoffee(coffee1);
            van.addCoffee(coffee2);
            van.addCoffee(coffee3);

            System.out.println("Список товарів у фургоні до сортування:");
            van.displayCargo();

            van.sortCoffeeByPriceToWeight();

            System.out.println("\nСписок товарів після сортування:");
            van.displayCargo();
            System.out.println("\nТовари, що відповідають якості від 6 до 8:");
            Coffee[] foundCoffees = van.findCoffeeByQualityRange(6, 8);
            for (Coffee coffee : foundCoffees) {
                System.out.println(coffee);
            }
        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }
}