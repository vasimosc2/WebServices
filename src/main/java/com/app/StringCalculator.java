package com.app;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringCalculator {
    public int add(String numbers) {
        // If the string is empty, return 0
        if (numbers.isEmpty()) {
            return 0;
        }
        String delimiter = ",";
        if (numbers.startsWith("//")){
            int newlineIndex = numbers.indexOf("\\n" + //
                        "");
            delimiter = numbers.substring(2, newlineIndex);
            System.out.println(delimiter);
            numbers = numbers.substring(newlineIndex + 2);
        }

        System.out.println(numbers);
        String modifiedInput = numbers.replace("\\n" + //
                        "", delimiter);
        System.out.println(modifiedInput);
        // Split the modified input based on commas
        String[] tokens = modifiedInput.split(delimiter);

        // Print the resulting tokens for debugging
        System.out.println("Tokens: " + Arrays.toString(tokens));

        // Check for negative numbers
        List<Integer> negatives = Arrays.stream(tokens)
                .map(Integer::parseInt)
                .filter(n -> n < 0)
                .collect(Collectors.toList());

        // If there are any negative numbers, throw an exception
        if (!negatives.isEmpty()) {
            String negativeNumbers = negatives.stream()
                    .map(String::valueOf)  // Convert each negative number to a string
                    .collect(Collectors.joining(", "));  // Join them into a single string with commas
            throw new IllegalArgumentException("Negatives not allowed: " + negativeNumbers);
        }

        // Sum the numbers and return the result
        return Arrays.stream(tokens)
                .mapToInt(Integer::parseInt)
                .sum();
    }
    
    
    
    public static void main(String[] args) {
        StringCalculator sc = new StringCalculator();

        // Test cases
        System.out.println(sc.add("")); // 0
        System.out.println(sc.add("1")); // 1
        System.out.println(sc.add("1,2")); // 3
        System.out.println(sc.add("1\n2,3")); // 6
        System.out.println(sc.add("//;\n1;2")); // 3

        // Uncomment to test negative numbers
        // System.out.println(sc.add("1,-2,3")); // Throws exception
    }
}
