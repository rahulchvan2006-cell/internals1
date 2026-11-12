package com.gradecalculator.service;

import com.gradecalculator.model.StudentResult;

import java.util.List;

/**
 * Service providing calculation of total marks, average, grade determination,
 * and input validation for student marks.
 */
public class GradeCalculatorService {

    public static final int REQUIRED_SUBJECT_COUNT = 5;
    public static final double MIN_MARK = 0.0;
    public static final double MAX_MARK = 100.0;

    /**
     * Validates a collection of marks.
     * Ensures:
     * - The collection is not null.
     * - Exactly 5 subjects are provided.
     * - Each mark is between 0 and 100 (inclusive).
     *
     * @param marks List of marks obtained in subjects.
     * @throws IllegalArgumentException if validation rules are violated.
     */
    public void validateMarks(List<Double> marks) {
        if (marks == null) {
            throw new IllegalArgumentException("Marks collection cannot be null.");
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
     * Calculates the total marks from a collection of marks.
     *
     * @param marks List of 5 marks.
     * @return Sum of all marks.
     */
    public double calculateTotal(List<Double> marks) {
        validateMarks(marks);
        return marks.stream().mapToDouble(Double::doubleValue).sum();
    }

    /**
     * Calculates the average marks percentage from a collection of marks.
     *
     * @param marks List of 5 marks.
     * @return Average percentage (total / 5.0).
     */
    public double calculateAverage(List<Double> marks) {
        validateMarks(marks);
        double total = calculateTotal(marks);
        return total / marks.size();
    }

    /**
     * Determines the letter grade based on the average percentage:
     * - 90% and above : A
     * - 80% to 89.99% : B
     * - 70% to 79.99% : C
     * - 60% to 69.99% : D
     * - Below 60%     : F
     *
     * @param average Average percentage score.
     * @return Assigned letter grade.
     * @throws IllegalArgumentException if average is outside [0.0, 100.0].
     */
    public String calculateGrade(double average) {
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

    /**
     * Complete workflow to validate marks, compute total, average, and grade,
     * and construct a StudentResult.
     *
     * @param studentName Name of the student.
     * @param marks       List of marks for 5 subjects.
     * @return StudentResult object.
     */
    public StudentResult processStudentGrade(String studentName, List<Double> marks) {
        validateMarks(marks);
        String name = (studentName == null || studentName.trim().isEmpty()) ? "Student" : studentName.trim();
        double total = calculateTotal(marks);
        double avg = calculateAverage(marks);
        String grade = calculateGrade(avg);
        return new StudentResult(name, marks, total, avg, grade);
    }
}
