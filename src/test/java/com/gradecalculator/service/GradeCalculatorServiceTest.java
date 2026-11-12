package com.gradecalculator.service;

import com.gradecalculator.model.StudentResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GradeCalculatorService Tests")
class GradeCalculatorServiceTest {

    private GradeCalculatorService service;

    @BeforeEach
    void setUp() {
        service = new GradeCalculatorService();
    }

    @Nested
    @DisplayName("Total Marks Calculation Tests")
    class TotalMarksTests {

        @Test
        @DisplayName("Should correctly calculate total for typical valid marks")
        void testCalculateTotal_TypicalMarks() {
            List<Double> marks = Arrays.asList(85.0, 90.0, 78.0, 92.0, 88.0);
            double total = service.calculateTotal(marks);
            assertEquals(433.0, total, 0.001);
        }

        @Test
        @DisplayName("Should correctly calculate total when all marks are maximum (100)")
        void testCalculateTotal_AllMaxMarks() {
            List<Double> marks = Arrays.asList(100.0, 100.0, 100.0, 100.0, 100.0);
            double total = service.calculateTotal(marks);
            assertEquals(500.0, total, 0.001);
        }

        @Test
        @DisplayName("Should correctly calculate total when all marks are zero (0)")
        void testCalculateTotal_AllZeroMarks() {
            List<Double> marks = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0);
            double total = service.calculateTotal(marks);
            assertEquals(0.0, total, 0.001);
        }

        @Test
        @DisplayName("Should correctly calculate total with decimal marks")
        void testCalculateTotal_DecimalMarks() {
            List<Double> marks = Arrays.asList(74.5, 82.25, 91.75, 66.5, 85.0);
            double total = service.calculateTotal(marks);
            assertEquals(400.0, total, 0.001);
        }
    }

    @Nested
    @DisplayName("Average Marks Calculation Tests")
    class AverageMarksTests {

        @Test
        @DisplayName("Should correctly calculate average for typical valid marks")
        void testCalculateAverage_TypicalMarks() {
            List<Double> marks = Arrays.asList(85.0, 90.0, 78.0, 92.0, 88.0);
            double average = service.calculateAverage(marks);
            assertEquals(86.6, average, 0.001);
        }

        @Test
        @DisplayName("Should return 100.0 average when all marks are 100")
        void testCalculateAverage_AllMaxMarks() {
            List<Double> marks = Arrays.asList(100.0, 100.0, 100.0, 100.0, 100.0);
            double average = service.calculateAverage(marks);
            assertEquals(100.0, average, 0.001);
        }

        @Test
        @DisplayName("Should return 0.0 average when all marks are 0")
        void testCalculateAverage_AllZeroMarks() {
            List<Double> marks = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0);
            double average = service.calculateAverage(marks);
            assertEquals(0.0, average, 0.001);
        }

        @Test
        @DisplayName("Should calculate average with floating point accuracy")
        void testCalculateAverage_FloatingPointAccuracy() {
            List<Double> marks = Arrays.asList(75.0, 80.0, 85.0, 90.0, 92.0);
            // sum = 422, average = 422 / 5 = 84.4
            double average = service.calculateAverage(marks);
            assertEquals(84.4, average, 0.001);
        }
    }

    @Nested
    @DisplayName("Grade Assignment Tests")
    class GradeAssignmentTests {

        @ParameterizedTest(name = "Average {0} should yield Grade {1}")
        @CsvSource({
            "100.0, A",
            "95.5, A",
            "90.0, A",
            "89.99, B",
            "85.0, B",
            "80.0, B",
            "79.99, C",
            "75.0, C",
            "70.0, C",
            "69.99, D",
            "65.0, D",
            "60.0, D",
            "59.99, F",
            "45.0, F",
            "0.0, F"
        })
        void testCalculateGrade_ValidBoundaries(double average, String expectedGrade) {
            String actualGrade = service.calculateGrade(average);
            assertEquals(expectedGrade, actualGrade);
        }

        @ParameterizedTest(name = "Invalid average {0} should throw IllegalArgumentException")
        @ValueSource(doubles = {-0.1, -25.0, 100.1, 150.0})
        void testCalculateGrade_InvalidAverages(double invalidAverage) {
            assertThrows(IllegalArgumentException.class, () -> service.calculateGrade(invalidAverage));
        }
    }

    @Nested
    @DisplayName("Marks Collection Validation Tests")
    class ValidationTests {

        @Test
        @DisplayName("Should throw IllegalArgumentException when marks collection is null")
        void testValidateMarks_NullCollection() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> service.validateMarks(null));
            assertTrue(ex.getMessage().contains("Marks collection cannot be null"));
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when fewer than 5 subjects are provided")
        void testValidateMarks_LessThanFiveSubjects() {
            List<Double> fourMarks = Arrays.asList(80.0, 85.0, 90.0, 95.0);
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> service.validateMarks(fourMarks));
            assertTrue(ex.getMessage().contains("Exactly 5 subject marks are required"));
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when more than 5 subjects are provided")
        void testValidateMarks_MoreThanFiveSubjects() {
            List<Double> sixMarks = Arrays.asList(80.0, 85.0, 90.0, 95.0, 70.0, 75.0);
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> service.validateMarks(sixMarks));
            assertTrue(ex.getMessage().contains("Exactly 5 subject marks are required"));
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when any mark is negative")
        void testValidateMarks_NegativeMark() {
            List<Double> marks = Arrays.asList(80.0, -5.0, 90.0, 95.0, 70.0);
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> service.validateMarks(marks));
            assertTrue(ex.getMessage().contains("must be between 0.0 and 100.0"));
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when any mark exceeds 100")
        void testValidateMarks_MarkExceedingMax() {
            List<Double> marks = Arrays.asList(80.0, 85.0, 105.0, 95.0, 70.0);
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> service.validateMarks(marks));
            assertTrue(ex.getMessage().contains("must be between 0.0 and 100.0"));
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when an element in marks is null")
        void testValidateMarks_NullElement() {
            List<Double> marks = Arrays.asList(80.0, null, 90.0, 95.0, 70.0);
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> service.validateMarks(marks));
            assertTrue(ex.getMessage().contains("cannot be null"));
        }
    }

    @Nested
    @DisplayName("Process Student Grade Full Workflow Tests")
    class ProcessStudentGradeTests {

        @Test
        @DisplayName("Should successfully generate StudentResult for valid student data")
        void testProcessStudentGrade_Success() {
            List<Double> marks = Arrays.asList(92.0, 88.0, 95.0, 90.0, 85.0);
            StudentResult result = service.processStudentGrade("Alice Smith", marks);

            assertNotNull(result);
            assertEquals("Alice Smith", result.getStudentName());
            assertEquals(5, result.getMarks().size());
            assertEquals(450.0, result.getTotalMarks(), 0.001);
            assertEquals(90.0, result.getAveragePercentage(), 0.001);
            assertEquals("A", result.getGrade());
            assertTrue(result.toString().contains("Alice Smith"));
            assertTrue(result.toString().contains("Final Grade      : A"));
        }

        @Test
        @DisplayName("Should fall back to default name when student name is null or whitespace")
        void testProcessStudentGrade_DefaultNameFallback() {
            List<Double> marks = Arrays.asList(70.0, 72.0, 68.0, 75.0, 70.0);
            StudentResult resultNull = service.processStudentGrade(null, marks);
            assertEquals("Student", resultNull.getStudentName());

            StudentResult resultBlank = service.processStudentGrade("   ", marks);
            assertEquals("Student", resultBlank.getStudentName());
            assertEquals("C", resultBlank.getGrade());
        }
    }
}
