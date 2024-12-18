package main;

public class EcosystemSimulationTest {

    public static void main(String[] args) {
        System.out.println("Running EcosystemSimulation Tests...\n");

        testPlantClone();
        testAnimalClone();
        testEcosystemSimulation();

        System.out.println("\nAll tests passed!");
    }

    private static void testPlantClone() {
        System.out.println("Test: Plant Clone");

        Plant oak = new Plant("Oak");
        Plant clonedOak = (Plant) oak.clone();

        assert oak != clonedOak : "Cloned plant should be a new instance.";
        assert oak.getSpecies().equals(clonedOak.getSpecies()) : "Species should match in the cloned plant.";

        System.out.println("✔ Plant Clone Test Passed");
    }

    private static void testAnimalClone() {
        System.out.println("Test: Animal Clone");

        Animal deer = new Animal("Deer");
        Animal clonedDeer = (Animal) deer.clone();

        assert deer != clonedDeer : "Cloned animal should be a new instance.";
        assert deer.getSpecies().equals(clonedDeer.getSpecies()) : "Species should match in the cloned animal.";

        System.out.println("✔ Animal Clone Test Passed");
    }

    private static void testEcosystemSimulation() {
        System.out.println("Test: Ecosystem Simulation");

        Ecosystem ecosystem = new Ecosystem();
        Plant oak = new Plant("Oak");
        Animal deer = new Animal("Deer");

        ecosystem.addComponent(oak);
        ecosystem.addComponent(deer);

        try {
            ecosystem.simulate();
            System.out.println("✔ Ecosystem Simulation Test Passed");
        } catch (Exception e) {
            assert false : "Ecosystem simulation threw an exception: " + e.getMessage();
        }
    }
}
