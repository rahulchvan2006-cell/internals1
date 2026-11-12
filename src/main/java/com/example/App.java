package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Student Grade Calculator Application
 * 
 * Accepts marks obtained by a student in 5 subjects,
 * validates marks, and calculates:
 * 1. Total Marks
 * 2. Average Percentage
 * 3. Final Grade (A, B, C, D, F)
 */
public class App {

    public static final int REQUIRED_SUBJECT_COUNT = 5;
    public static final double MIN_MARK = 0.0;
    public static final double MAX_MARK = 100.0;

    private static final String[] SUBJECT_NAMES = {
        "Subject 1 (Mathematics)",
        "Subject 2 (Science)",
        "Subject 3 (English)",
        "Subject 4 (Social Studies)",
        "Subject 5 (Computer Science)"
    };

    /**
     * Validates a collection of marks for 5 subjects.
     *
     * @param marks List of marks
     * @throws IllegalArgumentException if list is null, size != 5, or any mark is outside [0, 100]
     */
    public static void validateMarks(List<Double> marks) {
        if (marks == null) {
            throw new IllegalArgumentException("Marks list cannot be null.");
        }
        if (marks.size() != REQUIRED_SUBJECT_COUNT) {
            throw new IllegalArgumentException(String.format(
                "Exactly %d subject marks are required, but received %d.",
                REQUIRED_SUBJECT_COUNT, marks.size()
            ));
        }
        for (int i = 0; i < marks.size(); i++) {
            Double mark = marks.get(i);
            if (mark == null) {
                throw new IllegalArgumentException(String.format("Mark for subject %d cannot be null.", i + 1));
            }
            if (mark < MIN_MARK || mark > MAX_MARK) {
                throw new IllegalArgumentException(String.format(
                    "Mark for subject %d must be between %.1f and %.1f. Received: %.2f",
                    i + 1, MIN_MARK, MAX_MARK, mark
                ));
            }
        }
    }

    /**
     * Calculates the sum of marks obtained across all 5 subjects.
     *
     * @param marks List of marks
     * @return Total marks obtained
     */
    public static double calculateTotal(List<Double> marks) {
        validateMarks(marks);
        return marks.stream().mapToDouble(Double::doubleValue).sum();
    }

    /**
     * Calculates the average percentage marks across the 5 subjects.
     *
     * @param marks List of marks
     * @return Average percentage
     */
    public static double calculateAverage(List<Double> marks) {
        validateMarks(marks);
        return calculateTotal(marks) / marks.size();
    }

    /**
     * Determines letter grade based on the average percentage:
     * - Average >= 90.0% : A (Outstanding)
     * - Average >= 80.0% : B (Very Good)
     * - Average >= 70.0% : C (Good)
     * - Average >= 60.0% : D (Satisfactory)
     * - Average <  60.0% : F (Fail)
     *
     * @param average Average percentage
     * @return Grade letter (A, B, C, D, or F)
     */
    public static String calculateGrade(double average) {
        if (average < MIN_MARK || average > MAX_MARK) {
            throw new IllegalArgumentException(String.format(
                "Average must be between %.1f and %.1f. Received: %.2f",
                MIN_MARK, MAX_MARK, average
            ));
        }

        if (average >= 90.0) {
            return "A";
        } else if (average >= 80.0) {
            return "B";
        } else if (average >= 70.0) {
            return "C";
        } else if (average >= 60.0) {
            return "D";
        } else {
            return "F";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=================================================");
        System.out.println("       STUDENT GRADE CALCULATOR APPLICATION      ");
        System.out.println("=================================================");

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            name = "Student";
        }

        System.out.println("\nPlease enter marks obtained (out of 100) for 5 subjects:");
        List<Double> marks = new ArrayList<>();

        for (int i = 0; i < REQUIRED_SUBJECT_COUNT; i++) {
            double mark;
            while (true) {
                System.out.printf("Enter marks for %s: ", SUBJECT_NAMES[i]);
                String input = scanner.nextLine().trim();
                try {
                    mark = Double.parseDouble(input);
                    if (mark < MIN_MARK || mark > MAX_MARK) {
                        System.out.printf("  [Error] Marks must be between %.1f and %.1f. Try again.\n",
                                MIN_MARK, MAX_MARK);
                        continue;
                    }
                    marks.add(mark);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("  [Error] Invalid input! Please enter a valid number.");
                }
            }
        }

        double total = calculateTotal(marks);
        double average = calculateAverage(marks);
        String grade = calculateGrade(average);

        System.out.println();
        System.out.println("========================================");
        System.out.println("         STUDENT GRADE REPORT          ");
        System.out.println("========================================");
        System.out.printf(" Student Name     : %s\n", name);
        System.out.println(" ----------------------------------------");
        for (int i = 0; i < marks.size(); i++) {
            System.out.printf(" %-17s: %.2f / 100.00\n", SUBJECT_NAMES[i], marks.get(i));
        }
        System.out.println(" ----------------------------------------");
        System.out.printf(" Total Marks      : %.2f / 500.00\n", total);
        System.out.printf(" Average Marks    : %.2f%%\n", average);
        System.out.printf(" Final Grade      : %s\n", grade);
        System.out.println("========================================");
        System.out.println("\nCalculation completed successfully!");
    }
}
