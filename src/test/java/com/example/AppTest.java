package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("App (Student Grade Calculator) Unit Tests")
public class AppTest {

    @Nested
    @DisplayName("Total Marks Calculation Tests")
    class TotalCalculationTests {

        @Test
        @DisplayName("Calculate total for typical subject marks")
        void testCalculateTotal_TypicalMarks() {
            List<Double> marks = Arrays.asList(85.0, 90.0, 78.0, 92.0, 88.0);
            double total = App.calculateTotal(marks);
            assertEquals(433.0, total, 0.001);
        }

        @Test
        @DisplayName("Calculate total when all marks are 100")
        void testCalculateTotal_AllMaxMarks() {
            List<Double> marks = Arrays.asList(100.0, 100.0, 100.0, 100.0, 100.0);
            double total = App.calculateTotal(marks);
            assertEquals(500.0, total, 0.001);
        }

        @Test
        @DisplayName("Calculate total when all marks are 0")
        void testCalculateTotal_AllZeroMarks() {
            List<Double> marks = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0);
            double total = App.calculateTotal(marks);
            assertEquals(0.0, total, 0.001);
        }

        @Test
        @DisplayName("Calculate total with decimal marks")
        void testCalculateTotal_DecimalMarks() {
            List<Double> marks = Arrays.asList(74.5, 82.25, 91.75, 66.5, 85.0);
            double total = App.calculateTotal(marks);
            assertEquals(400.0, total, 0.001);
        }
    }

    @Nested
    @DisplayName("Average Percentage Calculation Tests")
    class AverageCalculationTests {

        @Test
        @DisplayName("Calculate average for typical marks")
        void testCalculateAverage_TypicalMarks() {
            List<Double> marks = Arrays.asList(85.0, 90.0, 78.0, 92.0, 88.0);
            double avg = App.calculateAverage(marks);
            assertEquals(86.6, avg, 0.001);
        }

        @Test
        @DisplayName("Calculate average when marks are 100")
        void testCalculateAverage_AllMaxMarks() {
            List<Double> marks = Arrays.asList(100.0, 100.0, 100.0, 100.0, 100.0);
            double avg = App.calculateAverage(marks);
            assertEquals(100.0, avg, 0.001);
        }

        @Test
        @DisplayName("Calculate average when marks are 0")
        void testCalculateAverage_AllZeroMarks() {
            List<Double> marks = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0);
            double avg = App.calculateAverage(marks);
            assertEquals(0.0, avg, 0.001);
        }
    }

    @Nested
    @DisplayName("Grade Assignment Tests")
    class GradeAssignmentTests {

        @ParameterizedTest(name = "Average {0}% should yield Grade {1}")
        @CsvSource({
            "100.0, A",
            "95.0,  A",
            "90.0,  A",
            "89.99, B",
            "85.0,  B",
            "80.0,  B",
            "79.99, C",
            "75.0,  C",
            "70.0,  C",
            "69.99, D",
            "65.0,  D",
            "60.0,  D",
            "59.99, F",
            "40.0,  F",
            "0.0,   F"
        })
        void testCalculateGrade_ValidRanges(double average, String expectedGrade) {
            assertEquals(expectedGrade, App.calculateGrade(average));
        }

        @ParameterizedTest(name = "Invalid average {0} should throw IllegalArgumentException")
        @ValueSource(doubles = {-0.1, -15.0, 100.1, 120.0})
        void testCalculateGrade_InvalidAverages(double invalidAverage) {
            assertThrows(IllegalArgumentException.class, () -> App.calculateGrade(invalidAverage));
        }
    }

    @Nested
    @DisplayName("Marks Validation Tests")
    class MarksValidationTests {

        @Test
        @DisplayName("Throw exception when marks list is null")
        void testValidateMarks_NullList() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> App.validateMarks(null));
            assertTrue(ex.getMessage().contains("cannot be null"));
        }

        @Test
        @DisplayName("Throw exception when subject count is fewer than 5")
        void testValidateMarks_LessThanFiveSubjects() {
            List<Double> fourSubjects = Arrays.asList(80.0, 85.0, 90.0, 95.0);
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> App.validateMarks(fourSubjects));
            assertTrue(ex.getMessage().contains("Exactly 5 subject marks are required"));
        }

        @Test
        @DisplayName("Throw exception when subject count is more than 5")
        void testValidateMarks_MoreThanFiveSubjects() {
            List<Double> sixSubjects = Arrays.asList(80.0, 85.0, 90.0, 95.0, 70.0, 75.0);
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> App.validateMarks(sixSubjects));
            assertTrue(ex.getMessage().contains("Exactly 5 subject marks are required"));
        }

        @Test
        @DisplayName("Throw exception when mark is negative")
        void testValidateMarks_NegativeMark() {
            List<Double> marks = Arrays.asList(80.0, -10.0, 90.0, 95.0, 70.0);
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> App.validateMarks(marks));
            assertTrue(ex.getMessage().contains("must be between 0.0 and 100.0"));
        }

        @Test
        @DisplayName("Throw exception when mark exceeds 100")
        void testValidateMarks_MarkExceedingHundred() {
            List<Double> marks = Arrays.asList(80.0, 85.0, 101.0, 95.0, 70.0);
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> App.validateMarks(marks));
            assertTrue(ex.getMessage().contains("must be between 0.0 and 100.0"));
        }
    }
}
