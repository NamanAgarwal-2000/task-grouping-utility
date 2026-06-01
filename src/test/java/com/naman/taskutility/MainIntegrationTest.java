package com.naman.taskutility;

import org.junit.jupiter.api.Test;
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

    Main.main(args);
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

    Main.main(args);
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
            String[] args = {"mple.txt"};
            Main.main(args);

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
            Main.main(args);

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

        Main.main(args);
        assertTrue(Files.exists(outputFile));
        String content = Files.readString(outputFile);

        assertTrue(content.contains("\"validCount\" : 2"));
        assertTrue(content.contains("\"invalidCount\" : 1"));
        assertTrue(content.contains("\"completedProblems\" : 1"));
        assertTrue(content.contains("\"pendingProblems\" : 1"));
        assertTrue(content.contains("Missing category"));
    }
}