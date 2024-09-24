package com.Lab1.logic;

import java.util.ArrayList;

public class NumberOperations {
    private ArrayList<Number> numbers;

    public NumberOperations() {
        numbers = new ArrayList<>();
    }

    public void addNumber(Number number) {
        numbers.add(number);
    }

    public void displayNumbers() {
        System.out.println("Числа у початковому вигляді:");
        for (Number number : numbers) {
            System.out.println(number);
        }
    }

    public void displayIntegers() {
        System.out.println("\nЧисла у форматі цілих чисел:");
        for (Number number : numbers) {
            System.out.println(number.intValue());
        }
    }

    public void displayDoubles() {
        System.out.println("\nЧисла у форматі дробних чисел з двома знаками після коми:");
        for (Number number : numbers) {
            System.out.println(String.format("%.2f", number.doubleValue()));
        }
    }

    public void separateNumbers() {
        ArrayList<Integer> integerNumbers = new ArrayList<>();
        ArrayList<Double> doubleNumbers = new ArrayList<>();

        for (Number number : numbers) {
            if (number instanceof Integer) {
                integerNumbers.add((Integer) number);
            } else if (number instanceof Double) {
                doubleNumbers.add((Double) number);
            }
        }

        System.out.println("\nЦілі числа:");
        for (Integer integer : integerNumbers) {
            System.out.println(integer);
        }

        System.out.println("\nЧисла з плаваючою комою:");
        for (Double decimal : doubleNumbers) {
            System.out.println(decimal);
        }
    }

    public double calculateAverage() {
        double sum = 0;
        for (Number number : numbers) {
            sum += number.doubleValue();
        }
        return sum / numbers.size();
    }
}
