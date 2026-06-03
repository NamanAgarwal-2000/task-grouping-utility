package com.naman.taskutility;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class CliArgumentParserTest {

    @Test
    void shouldParseHelpOption() {

        CliArgumentParser parser = new CliArgumentParser();

        CliOptions options =
                parser.parse(new String[]{"--help"});

        assertTrue(options.isHelp());
    }

    @Test
    void shouldParseInputAndOutputOptions() {

        CliArgumentParser parser = new CliArgumentParser();

        CliOptions options =
                parser.parse(new String[]{
                        "--input",
                        "problems.csv",
                        "--output",
                        "report.json"
                });

        assertEquals(
                "problems.csv",
                options.getInputFile());

        assertEquals(
                "report.json",
                options.getOutputFile());
    }

    @Test
    void shouldThrowWhenInputValueMissing() {

        CliArgumentParser parser = new CliArgumentParser();

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> parser.parse(
                                new String[]{"--input"})
                );

        assertEquals(
                "Missing value for --input",
                exception.getMessage());
    }

    @Test
    void shouldThrowWhenOutputValueMissing() {

        CliArgumentParser parser = new CliArgumentParser();

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> parser.parse(
                                new String[]{"--output"})
                );

        assertEquals(
                "Missing value for --output",
                exception.getMessage());
    }

    @Test
    void shouldThrowForUnknownOption() {

        CliArgumentParser parser = new CliArgumentParser();

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> parser.parse(
                                new String[]{"--abc"})
                );

        assertEquals(
                "Unknown option: --abc",
                exception.getMessage());
    }
    @Test
    void shouldParseOptionsInAnyOrder() {

        CliArgumentParser parser = new CliArgumentParser();

        CliOptions options =
                parser.parse(new String[]{
                        "--output",
                        "report.json",
                        "--input",
                        "problems.csv"
                });

        assertEquals("problems.csv",
                options.getInputFile());

        assertEquals("report.json",
                options.getOutputFile());
    }
    @Test
    void shouldParseOnlyInputOption() {

        CliArgumentParser parser =
                new CliArgumentParser();

        CliOptions options =
                parser.parse(new String[]{
                        "--input",
                        "problems.csv"
                });

        assertEquals("problems.csv",
                options.getInputFile());

        assertNull(options.getOutputFile());
    }
    @Test
    void shouldParseOnlyOutputOption() {

        CliArgumentParser parser =
                new CliArgumentParser();

        CliOptions options =
                parser.parse(new String[]{
                        "--output",
                        "report.json"
                });

        assertEquals("report.json",
                options.getOutputFile());

        assertNull(options.getInputFile());
    }
    @Test
    void shouldReturnEmptyOptionsForNoArguments() {

        CliArgumentParser parser =
                new CliArgumentParser();

        CliOptions options =
                parser.parse(new String[]{});

        assertNull(options.getInputFile());
        assertNull(options.getOutputFile());
        assertFalse(options.isHelp());
    }
    @Test
    void shouldSupportLegacyArguments() {

        CliArgumentParser parser =
                new CliArgumentParser();

        CliOptions options =
                parser.parse(new String[]{
                        "problems.csv",
                        "report.json"
                });

        assertEquals(
                "problems.csv",
                options.getInputFile());

        assertEquals(
                "report.json",
                options.getOutputFile());
    }
}