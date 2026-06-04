package com.naman.taskutility;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProblemServiceTest {

    private final ProblemService problemService =
            new ProblemService();

    @Test
    void shouldThrowForInvalidStatus() {

        List<Problem> problems = List.of(
                new Problem(
                        "Two Sum",
                        "Array",
                        "Easy",
                        "completed",
                        30
                )
        );

        CliOptions options = new CliOptions();
        options.setStatus("xyz");

        assertThrows(
                ValidationException.class,
                () -> problemService.applyFilters(
                        problems,
                        options
                )
        );
    }
    @Test
    void shouldThrowForInvalidDifficulty() {

        List<Problem> problems = List.of(
                new Problem(
                        "Two Sum",
                        "Array",
                        "Easy",
                        "completed",
                        30
                )
        );

        CliOptions options = new CliOptions();
        options.setDifficulty("expert");

        assertThrows(
                ValidationException.class,
                () -> problemService.applyFilters(
                        problems,
                        options
                )
        );
    }
    @Test
    void shouldThrowForInvalidSortBy() {

        List<Problem> problems = List.of(
                new Problem(
                        "Two Sum",
                        "Array",
                        "Easy",
                        "completed",
                        30
                )
        );

        CliOptions options = new CliOptions();
        options.setSortBy("timeSpent");

        assertThrows(
                ValidationException.class,
                () -> problemService.applySorting(
                        problems,
                        options
                )
        );
    }
    @Test
    void shouldThrowForInvalidSortOrder() {

        List<Problem> problems = List.of(
                new Problem(
                        "Two Sum",
                        "Array",
                        "Easy",
                        "completed",
                        30
                )
        );

        CliOptions options = new CliOptions();
        options.setSortBy("title");
        options.setSortOrder("wrong");

        assertThrows(
                ValidationException.class,
                () -> problemService.applySorting(
                        problems,
                        options
                )
        );
    }
    @Test
    void shouldAllowSortOrderWithoutSortBy() {

        List<Problem> problems = List.of(
                new Problem(
                        "Two Sum",
                        "Array",
                        "Easy",
                        "completed",
                        30
                )
        );

        CliOptions options = new CliOptions();
        options.setSortOrder("asc");

        List<Problem> result =
                problemService.applySorting(
                        problems,
                        options
                );

        assertEquals(
                1,
                result.size()
        );
    }

}