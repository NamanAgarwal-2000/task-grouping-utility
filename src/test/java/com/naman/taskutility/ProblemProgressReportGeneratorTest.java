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
                        "completed",
                        30
                )
        );

        problems.add(
                new Problem(
                        "Binary Tree",
                        "Tree",
                        "Medium",
                        "pending",
                        60
                )
        );

        ProblemProgressReportGenerator generator =
                new ProblemProgressReportGenerator();

        ReportSummary report =
                generator.generateReport(problems);

        assertEquals(
                1,
                report.getGroupedResult().get("Array").get("completed")
        );

        assertEquals(
                1,
                report.getGroupedResult().get("Tree").get("pending")
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
                        "pending",
                        20
                )
        );

        ProblemProgressReportGenerator generator =
                new ProblemProgressReportGenerator();

        ReportSummary report =
                generator.generateReport(problems);

        assertTrue(report.getGroupedResult().isEmpty());
    }

    @Test
    void shouldHandleNegativeTime() {

        List<Problem> problems = new ArrayList<>();

        problems.add(
                new Problem(
                        "DP Problem",
                        "DP",
                        "Hard",
                        "completed",
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
                        .get("completed")
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
                        "in_progress",
                        40
                )
        );

        ProblemProgressReportGenerator generator =
                new ProblemProgressReportGenerator();

        ReportSummary report =
                generator.generateReport(problems);

        assertEquals(0, report.getTotalProblems());
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

    @Test
    void shouldHandleEmptyJsonFile() {

        ProblemJsonReader reader = new ProblemJsonReader();

        List<Problem> problems =
                reader.readProblems("src/main/resources/empty.json");

        assertEquals(0, problems.size());
    }
}
