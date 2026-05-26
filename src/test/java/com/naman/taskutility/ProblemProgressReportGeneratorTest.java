package com.naman.taskutility;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ProblemProgressReportGeneratorTest {

    @Test
    void shouldGenerateGroupedReport() {

        List<Problem> problems = new ArrayList<>();

        problems.add(
                new Problem(
                        "Two Sum",
                        "Array",
                        "Easy",
                        "Completed",
                        30
                )
        );

        problems.add(
                new Problem(
                        "Binary Tree",
                        "Tree",
                        "Medium",
                        "Pending",
                        60
                )
        );

        ProblemProgressReportGenerator generator =
                new ProblemProgressReportGenerator();

        Map<String, Map<String, Integer>> result =
                generator.generateReport(problems);

        assertEquals(
                1,
                result.get("Array").get("Completed")
        );

        assertEquals(
                1,
                result.get("Tree").get("Pending")
        );
    }

    @Test
    void shouldReturnEmptyReportForEmptyInput() {

        List<Problem> problems = new ArrayList<>();

        ProblemProgressReportGenerator generator =
                new ProblemProgressReportGenerator();

        Map<String, Map<String, Integer>> result =
                generator.generateReport(problems);

        assertTrue(result.isEmpty());
    }
}
