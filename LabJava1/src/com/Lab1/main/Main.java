package com.Lab1.main;

import com.Lab1.logic.NumberOperations;

public class Main {

    public static void main(String[] args) {
        NumberOperations numberOperations = new NumberOperations();

        numberOperations.addNumber(10);
        numberOperations.addNumber(20.5);
        numberOperations.addNumber(30);
        numberOperations.addNumber(40.7);
        numberOperations.addNumber(50);
        numberOperations.addNumber(60.3);
        numberOperations.addNumber(70);
        numberOperations.addNumber(80.1);
        numberOperations.addNumber(90);
        numberOperations.addNumber(100.9);

        numberOperations.displayNumbers();
        numberOperations.displayIntegers();
        numberOperations.displayDoubles();
        numberOperations.separateNumbers();

        double average = numberOperations.calculateAverage();
        System.out.println("\nСереднє значення всіх чисел: " + String.format("%.2f", average));
    }
}
