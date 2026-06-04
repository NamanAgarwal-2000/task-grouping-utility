package com.naman.taskutility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProblemService {
    public List<Problem> applyFilters(
            List<Problem> problems,
            CliOptions options) {
        if (options.getStatus() != null
                && !options.getStatus().equalsIgnoreCase("all")
                && !options.getStatus().equalsIgnoreCase("completed")
                && !options.getStatus().equalsIgnoreCase("pending")) {

            throw new ValidationException(
                    "Invalid status filter");
        }

        if (options.getDifficulty() != null
                && !options.getDifficulty().equalsIgnoreCase("easy")
                && !options.getDifficulty().equalsIgnoreCase("medium")
                && !options.getDifficulty().equalsIgnoreCase("hard")) {

            throw new ValidationException(
                    "Invalid difficulty filter");
        }

        if (options.getStatus() != null
                && !options.getStatus().equalsIgnoreCase("all")) {

            List<Problem> filtered = new ArrayList<>();

            for (Problem problem : problems) {

                if (problem.getStatus()
                        .equalsIgnoreCase(
                                options.getStatus())) {

                    filtered.add(problem);
                }
            }

            problems = filtered;
        }

        if (options.getCategory() != null) {

            List<Problem> filtered = new ArrayList<>();

            for (Problem problem : problems) {

                if (problem.getCategory()
                        .equalsIgnoreCase(
                                options.getCategory())) {

                    filtered.add(problem);
                }
            }

            problems = filtered;
        }

        if (options.getDifficulty() != null) {

            List<Problem> filtered = new ArrayList<>();

            for (Problem problem : problems) {

                if (problem.getDifficulty()
                        .equalsIgnoreCase(
                                options.getDifficulty())) {

                    filtered.add(problem);
                }
            }

            problems = filtered;
        }

        return problems;
    }
    public List<Problem> applySorting(
            List<Problem> problems,
            CliOptions options
    ) {

        String sortBy = options.getSortBy();
        String sortOrder = options.getSortOrder();
        if (sortBy != null
                && !sortBy.isBlank()
                && !sortBy.equalsIgnoreCase("title")
                && !sortBy.equalsIgnoreCase("difficulty")
                && !sortBy.equalsIgnoreCase("time")) {

            throw new ValidationException(
                    "Invalid sortBy");
        }

        if (sortOrder != null
                && !sortOrder.equalsIgnoreCase("asc")
                && !sortOrder.equalsIgnoreCase("desc")) {

            throw new ValidationException(
                    "Invalid sortOrder");
        }

        if (sortBy == null || sortBy.isBlank()) {
            return problems;
        }

        if ("title".equalsIgnoreCase(sortBy)) {

            problems.sort(
                    (a, b) ->
                            a.getTitle().compareToIgnoreCase(
                                    b.getTitle()
                            )
            );

        } else if ("difficulty".equalsIgnoreCase(sortBy)) {

            problems.sort(
                    (a, b) ->
                            a.getDifficulty().compareToIgnoreCase(
                                    b.getDifficulty()
                            )
            );

        } else if ("time".equalsIgnoreCase(sortBy)) {

            problems.sort(
                    (a, b) ->
                            Integer.compare(
                                    a.getTimeSpentMinutes(),
                                    b.getTimeSpentMinutes()
                            )
            );
        }

        if ("desc".equalsIgnoreCase(sortOrder)) {
            Collections.reverse(problems);
        }

        return problems;
    }
}