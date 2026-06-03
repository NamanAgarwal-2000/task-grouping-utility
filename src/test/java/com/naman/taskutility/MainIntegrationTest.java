package com.naman.taskutility;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;


public class MainIntegrationTest {
    private final PrintStream originalOut = System.out;
    @TempDir
    Path tempDir;

    @Test
    void shouldGenerateReportFromCsv() throws Exception {

        Path outputFile = tempDir.resolve("report.json");

        String[] args = {"src/main/resources/problems.csv", outputFile.toString()};

        Main app = new Main();

        int exitCode = app.run(args);

        assertEquals(0, exitCode);
        assertTrue(Files.exists(outputFile));
        String content = Files.readString(outputFile);

        assertTrue(content.contains("reportSummary"));
        assertTrue(content.contains("validCount"));
        assertTrue(content.contains("invalidCount"));
        assertTrue(content.contains("invalidRecords"));
    }

    @Test
    void shouldGenerateReportFromJson() throws Exception {

        Path outputFile = tempDir.resolve("report.json");

        String[] args = {"src/main/resources/problems.json", outputFile.toString()};

        Main app = new Main();

        int exitCode = app.run(args);

        assertEquals(0, exitCode);
        assertTrue(Files.exists(outputFile));
        String content = Files.readString(outputFile);

        assertTrue(content.contains("reportSummary"));
        assertTrue(content.contains("validCount"));
        assertTrue(content.contains("invalidCount"));
        assertTrue(content.contains("invalidRecords"));
    }

    @Test
    void shouldShowMessageForUnsupportedFileType() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(outputStream));
            String[] args = {
                    "--input",
                    "sample.txt",
                    "--output",
                    "output/report.json"
            };
            Main app = new Main();

            int exitCode = app.run(args);

            assertEquals(1, exitCode);

            String consoleOutput = outputStream.toString();
            assertTrue(consoleOutput.contains("Unsupported file type"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void shouldShowUsageMessageWhenNoArgsProvided() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(outputStream));
            String[] args = {};
            Main app = new Main();

            int exitCode = app.run(args);

            assertEquals(1, exitCode);

            String consoleOutput = outputStream.toString();
            assertTrue(consoleOutput.contains("Usage:"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void shouldHandleMixedValidAndInvalidCsv() throws Exception {

        Path outputFile = tempDir.resolve("report.json");

        String[] args = {"src/main/resources/mixed-problems.csv", outputFile.toString()};

        Main app = new Main();

        int exitCode = app.run(args);

        assertEquals(0, exitCode);
        assertTrue(Files.exists(outputFile));
        String content = Files.readString(outputFile);

        assertTrue(content.contains("\"validCount\" : 2"));
        assertTrue(content.contains("\"invalidCount\" : 1"));
        assertTrue(content.contains("\"completedProblems\" : 1"));
        assertTrue(content.contains("\"pendingProblems\" : 1"));
        assertTrue(content.contains("Missing category"));
    }

    @Test
    void shouldShowHelpMessage() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(outputStream));

            String[] args = {"--help"};

            Main app = new Main();

            int exitCode = app.run(args);

            assertEquals(0, exitCode);

            String consoleOutput = outputStream.toString();

            assertTrue(consoleOutput.contains("Usage:"));

        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void shouldShowMessageWhenInputMissing() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(outputStream));

            String[] args = {"--input"};

            Main app = new Main();

            int exitCode = app.run(args);

            assertEquals(1, exitCode);

            String consoleOutput = outputStream.toString();
            System.out.println("OUTPUT = " + consoleOutput);
            assertTrue(consoleOutput.contains("Missing value for --input"));

        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void shouldShowMessageWhenOutputValueMissing() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(outputStream));

            String[] args = {"--input", "src/main/resources/problems.csv", "--output"};

            Main app = new Main();

            int exitCode = app.run(args);

            assertEquals(1, exitCode);

            String consoleOutput = outputStream.toString();

            assertTrue(consoleOutput.contains("--output"));

        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void shouldHandleEmptyCsvFile() throws Exception {
        Path outputFile = tempDir.resolve("report.json");

        String[] args = {
                "src/main/resources/empty.csv",
                outputFile.toString()
        };

        Main app = new Main();

        int exitCode = app.run(args);

        assertEquals(0, exitCode);
        assertTrue(Files.exists(outputFile));

        String content = Files.readString(outputFile);

        assertTrue(content.contains("\"validCount\""));
        assertTrue(content.contains("\"invalidCount\""));
    }

    @Test
    void shouldReturnNonZeroWhenInputFileDoesNotExist() {
        Main app = new Main();

        int exitCode = app.run(new String[]{
                "--input",
                "src/main/resources/does-not-exist.csv"
        });

        assertEquals(1, exitCode);
    }

    @Test
    void shouldReturnNonZeroWhenExportFails() throws Exception {
        Path invalidOutput =
                tempDir.resolve("missing-dir")
                        .resolve("report.json");

        Main app = new Main();

        int exitCode = app.run(new String[]{
                "--input",
                "src/main/resources/problems.csv",
                "--output",
                invalidOutput.toString()
        });

        assertEquals(1, exitCode);
    }

    @Test
    void shouldShowMessageWhenOutputFileMissing() {
        Main app = new Main();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(output));

        int exitCode = app.run(new String[]{
                "--input", "src/main/resources/problems.csv"
        });

        System.setOut(originalOut);

        assertEquals(1, exitCode);
        assertTrue(output.toString().contains("Missing output file"));
    }

    @Test
    void shouldSortByTitleAscending() {

        Main app = new Main();

        int exitCode = app.run(new String[]{
                "--input",
                "src/main/resources/problems.csv",
                "--output",
                tempDir.resolve("report.json").toString(),
                "--sort-by",
                "title"
        });

        assertEquals(0, exitCode);
    }

    @Test
    void shouldSortByTimeDescending() {

        Main app = new Main();

        int exitCode = app.run(new String[]{
                "--input",
                "src/main/resources/problems.csv",
                "--output",
                tempDir.resolve("report.json").toString(),
                "--sort-by",
                "time",
                "--sort-order",
                "desc"
        });

        assertEquals(0, exitCode);
    }

    @Test
    void shouldIgnoreInvalidSortField() {

        Main app = new Main();

        int exitCode = app.run(new String[]{
                "--input",
                "src/main/resources/problems.csv",
                "--output",
                tempDir.resolve("report.json").toString(),
                "--sort-by",
                "random"
        });

        assertNotEquals(0, exitCode);
    }

    @Test
    void shouldHandleSortOrderWithoutSortBy() {

        Main app = new Main();

        int exitCode = app.run(new String[]{
                "--input",
                "src/main/resources/problems.csv",
                "--output",
                tempDir.resolve("report.json").toString(),
                "--sort-order",
                "desc"
        });

        assertNotEquals(0, exitCode);
    }

    @Test
    void shouldSortByDifficultyAscending() {

        Main app = new Main();

        int exitCode = app.run(new String[]{
                "--input",
                "src/main/resources/problems.csv",
                "--output",
                tempDir.resolve("report.json").toString(),
                "--sort-by",
                "difficulty"
        });

        assertEquals(0, exitCode);
    }

    @Test
    void shouldSortByDifficultyDescending() {

        Main app = new Main();

        int exitCode = app.run(new String[]{
                "--input",
                "src/main/resources/problems.csv",
                "--output",
                tempDir.resolve("report.json").toString(),
                "--sort-by",
                "difficulty",
                "--sort-order",
                "desc"
        });

        assertEquals(0, exitCode);
    }

    @Test
    void shouldHandleEmptySortValue() {

        Main app = new Main();

        int exitCode = app.run(new String[]{
                "--input",
                "src/main/resources/problems.csv",
                "--output",
                tempDir.resolve("report.json").toString(),
                "--sort-by",
                ""
        });

        assertNotEquals(0, exitCode);
    }

    @Test
    void shouldApplyCategoryAndStatusFiltersTogether() {

        Main app = new Main();

        int exitCode = app.run(new String[]{
                "--input",
                "src/main/resources/problems.csv",
                "--output",
                tempDir.resolve("report.json").toString(),
                "--category",
                "Array",
                "--status",
                "completed"
        });

        assertEquals(0, exitCode);
    }

    @Test
    void shouldApplyStatusFilterCaseInsensitive() {
        Main app = new Main();

        int exitCode = app.run(new String[]{
                "--input", "src/main/resources/problems.csv",
                "--output", tempDir.resolve("report.json").toString(),
                "--status", "COMPLETED"
        });

        assertEquals(0, exitCode);
    }

    @Test
    void shouldApplyCategoryFilterCaseInsensitive() {
        Main app = new Main();

        int exitCode = app.run(new String[]{
                "--input", "src/main/resources/problems.csv",
                "--output", tempDir.resolve("report.json").toString(),
                "--category", "array"
        });

        assertEquals(0, exitCode);
    }

    @Test
    void shouldApplyAllFiltersTogether() {
        Main app = new Main();

        int exitCode = app.run(new String[]{
                "--input", "src/main/resources/problems.csv",
                "--output", tempDir.resolve("report.json").toString(),
                "--category", "Array",
                "--status", "completed",
                "--difficulty", "Easy"
        });

        assertEquals(0, exitCode);
    }

    @Test
    void shouldIgnoreInvalidSortOrderValue() {
        Main app = new Main();

        int exitCode = app.run(new String[]{
                "--input", "src/main/resources/problems.csv",
                "--output", tempDir.resolve("report.json").toString(),
                "--sort-by", "title",
                "--sort-order", "random"
        });

        assertNotEquals(0, exitCode);
    }

    @Test
    void shouldNotFilterWhenStatusIsAll() throws Exception {

        Main app = new Main();

        Path outputFile =
                tempDir.resolve("report.json");

        int exitCode = app.run(new String[]{
                "--input",
                "src/main/resources/problems.csv",
                "--output",
                outputFile.toString(),
                "--status",
                "all"
        });

        assertEquals(0, exitCode);

        String report =
                Files.readString(outputFile);

        assertTrue(report.contains("\"totalProblems\" : 3"));
        assertTrue(report.contains("\"validCount\" : 3"));
    }
    @Test
    void shouldReturnNonZeroForInvalidStatusValue() {

        Main app = new Main();

        int exitCode = app.run(new String[]{
                "--input",
                "src/main/resources/problems.csv",
                "--output",
                tempDir.resolve("report.json").toString(),
                "--status",
                "bogus"
        });

        assertNotEquals(0, exitCode);
    }
    @Test
    void shouldReturnNonZeroForInvalidDifficultyValue() {

        Main app = new Main();

        int exitCode = app.run(new String[]{
                "--input",
                "src/main/resources/problems.csv",
                "--output",
                tempDir.resolve("report.json").toString(),
                "--difficulty",
                "impossible"
        });

        assertNotEquals(0, exitCode);
    }
    @Test
    void shouldReturnNonZeroForInvalidSortByValue() {

        Main app = new Main();

        int exitCode = app.run(new String[]{
                "--input",
                "src/main/resources/problems.csv",
                "--output",
                tempDir.resolve("report.json").toString(),
                "--sort-by",
                "random"
        });

        assertNotEquals(0, exitCode);
    }
    @Test
    void shouldReturnNonZeroForInvalidSortOrderValue() {

        Main app = new Main();

        int exitCode = app.run(new String[]{
                "--input",
                "src/main/resources/problems.csv",
                "--output",
                tempDir.resolve("report.json").toString(),
                "--sort-order",
                "sideways"
        });

        assertNotEquals(0, exitCode);
    }

    @Test
    void shouldExportProblemsSortedByTitleAscending() throws Exception {
        Path outputFile = tempDir.resolve("report-sorted-title.json");
        Main app = new Main();

        int exitCode = app.run(new String[]{
                "--input", "src/main/resources/problems.csv",
                "--output", outputFile.toString(),
                "--sort-by", "title"
        });

        assertEquals(0, exitCode);
        JsonNode problems = new ObjectMapper()
                .readTree(Files.readString(outputFile))
                .get("problems");

        assertEquals("Binary Tree", problems.get(0).get("title").asText());
        assertEquals("Graph Traversal", problems.get(1).get("title").asText());
        assertEquals("Two Sum", problems.get(2).get("title").asText());
    }

    @Test
    void shouldExportProblemsSortedByTimeDescending() throws Exception {
        Path outputFile = tempDir.resolve("report-sorted-time.json");
        Main app = new Main();

        int exitCode = app.run(new String[]{
                "--input", "src/main/resources/problems.csv",
                "--output", outputFile.toString(),
                "--sort-by", "time",
                "--sort-order", "desc"
        });

        assertEquals(0, exitCode);
        JsonNode problems = new ObjectMapper()
                .readTree(Files.readString(outputFile))
                .get("problems");

        assertEquals("Graph Traversal", problems.get(0).get("title").asText());
        assertEquals(120, problems.get(0).get("timeSpentMinutes").asInt());
        assertEquals("Binary Tree", problems.get(1).get("title").asText());
        assertEquals("Two Sum", problems.get(2).get("title").asText());
    }

    @Test
    void shouldExportDifferentOrderWhenSortedVsUnsorted() throws Exception {
        Path unsortedFile = tempDir.resolve("report-unsorted.json");
        Path sortedFile = tempDir.resolve("report-sorted.json");
        Main app = new Main();

        assertEquals(0, app.run(new String[]{
                "--input", "src/main/resources/problems.csv",
                "--output", unsortedFile.toString()
        }));
        assertEquals(0, app.run(new String[]{
                "--input", "src/main/resources/problems.csv",
                "--output", sortedFile.toString(),
                "--sort-by", "title"
        }));

        ObjectMapper mapper = new ObjectMapper();
        String unsortedFirst = mapper.readTree(Files.readString(unsortedFile))
                .get("problems").get(0).get("title").asText();
        String sortedFirst = mapper.readTree(Files.readString(sortedFile))
                .get("problems").get(0).get("title").asText();

        assertEquals("Two Sum", unsortedFirst);
        assertEquals("Binary Tree", sortedFirst);
        assertNotEquals(unsortedFirst, sortedFirst);
    }
    @Test
    void shouldUseFilteredValidCountWhenStatusFilterApplied() throws Exception {

        Path outputFile = Files.createTempFile("report", ".json");

        Main main = new Main();

        int exitCode = main.run(new String[]{
                "--input", "src/main/resources/problems.csv",
                "--status", "pending",
                "--output", outputFile.toString()
        });

        assertEquals(0, exitCode);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(outputFile.toFile());

        assertEquals(1,
                root.path("reportSummary")
                        .path("totalProblems")
                        .asInt());

        assertEquals(1,
                root.path("validCount")
                        .asInt());

        assertEquals(1,
                root.path("problems")
                        .size());

        assertEquals(
                "Binary Tree",
                root.path("problems")
                        .get(0)
                        .path("title")
                        .asText()
        );
    }
}