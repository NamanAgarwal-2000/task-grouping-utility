package com.naman.taskutility;

import com.naman.taskutility.Problem;
import com.naman.taskutility.ProblemCsvReader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProblemCsvReaderTest {

    @Test
    void shouldReadCsvProblemsSuccessfully() {

        ProblemCsvReader reader = new ProblemCsvReader();

        ValidationResult result =
                reader.readProblems("src/main/resources/problems.csv");

        List<Problem> problems = result.getValidProblems();

        assertEquals(2, problems.size());

        assertEquals("Two Sum", problems.get(0).getTitle());

        assertEquals("done", problems.get(0).getStatus());
    }

    @Test
    void shouldSkipInvalidCsvRows() {

        ProblemCsvReader reader = new ProblemCsvReader();
        ValidationResult result =
                reader.readProblems("src/main/resources/invalid-problems.csv");

        List<Problem> problems = result.getValidProblems();

        assertEquals(1, problems.size());

        assertEquals(
                "Valid Problem",
                problems.get(0).getTitle()
        );
    }

    @Test
    void shouldNormalizeStatusValues() {

        Problem problem = new Problem(
                "Binary Tree",
                "Tree",
                "Medium",
                "COMPLETED",
                60
        );

        assertEquals("completed", problem.getStatus());
    }

    @Test
    void shouldHandleEmptyCsvFile() {

        ProblemCsvReader reader = new ProblemCsvReader();

        ValidationResult result =
                reader.readProblems("src/main/resources/empty.csv");

        List<Problem> problems = result.getValidProblems();

        assertEquals(0, problems.size());
    }
}