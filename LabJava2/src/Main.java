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

class CargoManager {
    private Coffee[] cargo;
    private double currentWeight;
    private double maxWeight;

    public CargoManager(double maxWeight) {
        this.maxWeight = maxWeight;
        this.cargo = new Coffee[0];
        this.currentWeight = 0;
    }

    public void addCoffee(Coffee coffee) throws Exception {
        validateWeight(coffee);
        cargo = expandCargoArray(coffee);
        currentWeight += coffee.getWeight();
    }

    private void validateWeight(Coffee coffee) throws Exception {
        if (currentWeight + coffee.getWeight() > maxWeight) {
            throw new Exception("Перевищено вагу фургону!");
        }
    }

    private Coffee[] expandCargoArray(Coffee coffee) {
        Coffee[] newCargo = new Coffee[cargo.length + 1];
        System.arraycopy(cargo, 0, newCargo, 0, cargo.length);
        newCargo[cargo.length] = coffee;
        return newCargo;
    }

    public Coffee[] getCargo() {
        return cargo;
    }
}

interface CargoSorter {
    void sort(Coffee[] cargo);
}

class PriceToWeightSorter implements CargoSorter {
    @Override
    public void sort(Coffee[] cargo) {
        java.util.Arrays.sort(cargo, (c1, c2) -> Double.compare(c1.priceToWeightRatio(), c2.priceToWeightRatio()));
    }
}


interface CargoFilter {
    Coffee[] filter(Coffee[] cargo);
}

class QualityRangeFilter implements CargoFilter {
    private int minQuality;
    private int maxQuality;

    public QualityRangeFilter(int minQuality, int maxQuality) {
        this.minQuality = minQuality;
        this.maxQuality = maxQuality;
    }

    @Override
    public Coffee[] filter(Coffee[] cargo) {
        Coffee[] tempResult = new Coffee[cargo.length];
        int count = 0;

        for (Coffee coffee : cargo) {
            if (coffee.getQuality() >= minQuality && coffee.getQuality() <= maxQuality) {
                tempResult[count] = coffee;
                count++;
            }
        }

        Coffee[] result = new Coffee[count];
        System.arraycopy(tempResult, 0, result, 0, count);
        return result;
    }
}

class Van {
    private CargoManager cargoManager;

    public Van(double maxWeight) {
        this.cargoManager = new CargoManager(maxWeight);
    }

    public void addCoffee(Coffee coffee) throws Exception {
        cargoManager.addCoffee(coffee);
    }

    public void sortCargo(CargoSorter sorter) {
        sorter.sort(cargoManager.getCargo());
    }

    public Coffee[] filterCargo(CargoFilter filter) {
        return filter.filter(cargoManager.getCargo());
    }

    public void displayCargo() {
        for (Coffee coffee : cargoManager.getCargo()) {
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

            System.out.println("Список товарів у фургоні:");
            van.displayCargo();

            van.sortCargo(new PriceToWeightSorter());

            System.out.println("\nСписок товарів після сортування:");
            van.displayCargo();

            CargoFilter qualityFilter = new QualityRangeFilter(6, 8);
            Coffee[] filteredCoffees = van.filterCargo(qualityFilter);

            System.out.println("\nТовари, що відповідають якості від 6 до 8:");
            for (Coffee coffee : filteredCoffees) {
                System.out.println(coffee);
            }

        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }
}

