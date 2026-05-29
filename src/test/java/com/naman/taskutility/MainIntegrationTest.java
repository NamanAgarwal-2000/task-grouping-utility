package com.naman.taskutility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MainIntegrationTest {

    @BeforeEach
    void cleanOutputFile() {

        File outputDir = new File("output");

        if (!outputDir.exists()) {
            outputDir.mkdir();
        }

        File outputFile = new File("output/report.json");

        if (outputFile.exists()) {
            outputFile.delete();
        }
    }
    @Test
    void shouldGenerateReportFromCsv() throws Exception {

        String[] args = {"src/main/resources/problems.csv", "output/report.json"};

    Main.main(args);
    File outputFile = new File("output/report.json");

    assertTrue(outputFile.exists());
    String content =Files.readString(Paths.get("output/report.json"));

    assertTrue(content.contains("reportSummary"));
    assertTrue(content.contains("validCount"));
    assertTrue(content.contains("invalidCount"));
    assertTrue(content.contains("invalidRecords"));
}
    @Test
    void shouldGenerateReportFromJson() throws Exception {

        String[] args = { "src/main/resources/problems.json",  "output/report.json"};

    Main.main(args);
    File outputFile = new File("output/report.json");

    assertTrue(outputFile.exists());
    String content =Files.readString(Paths.get("output/report.json"));

    assertTrue(content.contains("reportSummary"));
    assertTrue(content.contains("validCount"));
    assertTrue(content.contains("invalidCount"));
    assertTrue(content.contains("invalidRecords"));
}
    @Test
    void shouldShowMessageForUnsupportedFileType() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String[] args = {"mple.txt"};
        Main.main(args);

        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("Unsupported file type"));
    }
    @Test
    void shouldShowUsageMessageWhenNoArgsProvided() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String[] args = {};
        Main.main(args);

        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("Usage:"));
    }
    @Test
    void shouldHandleMixedValidAndInvalidCsv() throws Exception {

        String[] args = {"src/main/resources/mixed-problems.csv", "output/report.json"};

        Main.main(args);
        File outputFile = new File("output/report.json");

        assertTrue(outputFile.exists());

        String content = Files.readString(Paths.get("output/report.json"));

        assertTrue(content.contains("\"validCount\""));
        assertTrue(content.contains("\"invalidCount\""));
        assertTrue(content.contains("\"invalidRecords\""));
    }
}