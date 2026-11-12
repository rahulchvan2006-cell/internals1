package com.gradecalculator;

import com.gradecalculator.model.StudentResult;
import com.gradecalculator.service.GradeCalculatorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main entry point for the interactive Student Grade Calculator application.
 */
public class Main {

    private static final String[] DEFAULT_SUBJECTS = {
        "Subject 1 (Mathematics)",
        "Subject 2 (Science)",
        "Subject 3 (English)",
        "Subject 4 (Social Studies)",
        "Subject 5 (Computer Science)"
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GradeCalculatorService service = new GradeCalculatorService();

        System.out.println("=================================================");
        System.out.println("       STUDENT GRADE CALCULATOR APPLICATION      ");
        System.out.println("=================================================");

        System.out.print("Enter Student Name (or press Enter for default): ");
        String studentName = scanner.nextLine().trim();
        if (studentName.isEmpty()) {
            studentName = "Student";
        }

        System.out.println("\nPlease enter marks obtained (0 to 100) for 5 subjects:");
        List<Double> marks = new ArrayList<>();

        for (int i = 0; i < GradeCalculatorService.REQUIRED_SUBJECT_COUNT; i++) {
            String subjectName = (i < DEFAULT_SUBJECTS.length) ? DEFAULT_SUBJECTS[i] : ("Subject " + (i + 1));
            double mark;
            while (true) {
                System.out.printf("Enter marks for %s: ", subjectName);
                String input = scanner.nextLine().trim();
                try {
                    mark = Double.parseDouble(input);
                    if (mark < GradeCalculatorService.MIN_MARK || mark > GradeCalculatorService.MAX_MARK) {
                        System.out.printf("  [Error] Marks must be between %.1f and %.1f. Please re-enter.\n",
                                GradeCalculatorService.MIN_MARK, GradeCalculatorService.MAX_MARK);
                        continue;
                    }
                    marks.add(mark);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("  [Error] Invalid input! Please enter a valid numeric value.");
                }
            }
        }

        System.out.println();
        StudentResult result = service.processStudentGrade(studentName, marks);
        System.out.println(result);
        System.out.println("\nThank you for using the Student Grade Calculator!");
    }
}
