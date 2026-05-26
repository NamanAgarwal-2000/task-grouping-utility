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

        ReportSummary report =
                generator.generateReport(problems);

        assertEquals(
                1,
                report.getGroupedResult().get("Array").get("Completed")
        );

        assertEquals(
                1,
                report.getGroupedResult().get("Tree").get("Pending")
        );
    }

    @Test
    void shouldHandleMissingFields() {

        List<Problem> problems = new ArrayList<>();

        problems.add(
                new Problem(
                        "Test Problem",
                        "",
                        "",
                        "",
                        20
                )
        );

        ProblemProgressReportGenerator generator =
                new ProblemProgressReportGenerator();

        ReportSummary report =
                generator.generateReport(problems);

        assertEquals(
                1,
                report.getGroupedResult()
                        .get("Unknown Category")
                        .get("Unknown Status")
        );
    }

    @Test
    void shouldHandleNegativeTime() {

        List<Problem> problems = new ArrayList<>();

        problems.add(
                new Problem(
                        "DP Problem",
                        "DP",
                        "Hard",
                        "Completed",
                        -30
                )
        );

        ProblemProgressReportGenerator generator =
                new ProblemProgressReportGenerator();

        ReportSummary report =
                generator.generateReport(problems);

        assertEquals(
                1,
                report.getGroupedResult()
                        .get("DP")
                        .get("Completed")
        );
    }

    @Test
    void shouldHandleInvalidStatusAndDifficulty() {

        List<Problem> problems = new ArrayList<>();

        problems.add(
                new Problem(
                        "Graph Problem",
                        "Graph",
                        "",
                        "",
                        40
                )
        );

        ProblemProgressReportGenerator generator =
                new ProblemProgressReportGenerator();

        ReportSummary report =
                generator.generateReport(problems);

        assertEquals(
                1,
                report.getGroupedResult()
                        .get("Graph")
                        .get("Unknown Status")
        );
    }

    @Test
    void shouldThrowExceptionForInvalidJsonPath() {

        ProblemJsonReader reader =
                new ProblemJsonReader();

        assertThrows(
                RuntimeException.class,
                () -> reader.readProblems("invalid/path/problems.json")
        );
    }

    @Test
    void shouldHandleNullInput() {

        ProblemProgressReportGenerator generator =
                new ProblemProgressReportGenerator();

        ReportSummary report =
                generator.generateReport(null);

        assertTrue(report.getGroupedResult().isEmpty());
    }

    @Test
    void shouldReturnEmptyReportForEmptyInput() {

        List<Problem> problems = new ArrayList<>();

        ProblemProgressReportGenerator generator =
                new ProblemProgressReportGenerator();

        ReportSummary report =
                generator.generateReport(problems);

        assertTrue(report.getGroupedResult().isEmpty());
    }
}
