package com.naman.taskutility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.file.Files;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;


public class MainIntegrationTest {
    private final PrintStream originalOut = System.out;
    @TempDir
    Path tempDir;

    @Test
    void shouldGenerateReportFromCsv() throws Exception {

        Path outputFile = tempDir.resolve("report.json");

        String[] args = {"src/main/resources/problems.csv",outputFile.toString()};

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

        String[] args = { "src/main/resources/problems.json",outputFile.toString()};

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

        String[] args = { "src/main/resources/mixed-problems.csv",outputFile.toString()};

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
}