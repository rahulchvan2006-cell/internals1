package com.gradecalculator.model;

import java.util.Collections;
import java.util.List;

/**
 * Model class representing the calculated grade results for a student.
 */
public class StudentResult {
    private final String studentName;
    private final List<Double> marks;
    private final double totalMarks;
    private final double averagePercentage;
    private final String grade;

    public StudentResult(String studentName, List<Double> marks, double totalMarks, double averagePercentage, String grade) {
        this.studentName = studentName;
        this.marks = Collections.unmodifiableList(marks);
        this.totalMarks = totalMarks;
        this.averagePercentage = averagePercentage;
        this.grade = grade;
    }

    public String getStudentName() {
        return studentName;
    }

    public List<Double> getMarks() {
        return marks;
    }

    public double getTotalMarks() {
        return totalMarks;
    }

    public double getAveragePercentage() {
        return averagePercentage;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("         STUDENT GRADE REPORT          \n");
        sb.append("========================================\n");
        sb.append(String.format(" Student Name     : %s\n", studentName));
        sb.append(" ----------------------------------------\n");
        for (int i = 0; i < marks.size(); i++) {
            sb.append(String.format(" Subject %d        : %.2f / 100.00\n", (i + 1), marks.get(i)));
        }
        sb.append(" ----------------------------------------\n");
        sb.append(String.format(" Total Marks      : %.2f / %.2f\n", totalMarks, marks.size() * 100.0));
        sb.append(String.format(" Average Marks    : %.2f%%\n", averagePercentage));
        sb.append(String.format(" Final Grade      : %s\n", grade));
        sb.append("========================================");
        return sb.toString();
    }
}
