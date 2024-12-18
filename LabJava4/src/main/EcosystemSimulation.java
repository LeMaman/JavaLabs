
package main;
import java.util.*;

// Використання прототипу
abstract class EcosystemComponent implements Cloneable {
    private String species;

    public EcosystemComponent(String species) {
        this.species = species;
    }

    public String getSpecies() {
        return species;
    }

    @Override
    public EcosystemComponent clone() {
        try {
            return (EcosystemComponent) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning failed", e);
        }
    }

    public abstract void interact();
}

class Plant extends EcosystemComponent {
    public Plant(String species) {
        super(species);
    }

    @Override
    public void interact() {
        System.out.println("Plant " + getSpecies() + " photosynthesizes.");
    }
}

class Animal extends EcosystemComponent {
    public Animal(String species) {
        super(species);
    }

    @Override
    public void interact() {
        System.out.println("Animal " + getSpecies() + " is grazing.");
    }
}


class Ecosystem {
    private List<EcosystemComponent> components = new ArrayList<>();

    public void addComponent(EcosystemComponent component) {
        components.add(component);
    }

    public void simulate() {
        for (EcosystemComponent component : components) {
            component.interact();
        }
    }
}

public class EcosystemSimulation {
    public static void main(String[] args) {
        // Прототипи
        Plant oak = new Plant("Oak");
        Animal deer = new Animal("Deer");

        // Використання прототипів
        EcosystemComponent clonedOak = oak.clone();
        EcosystemComponent clonedDeer = deer.clone();

        Ecosystem ecosystem = new Ecosystem();
        ecosystem.addComponent(clonedOak);
        ecosystem.addComponent(clonedDeer);

        System.out.println("Simulating ecosystem:");
        ecosystem.simulate();
    }
}
