package com.naman.taskutility;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ReportController {

    private final ProblemService problemService;

    public ReportController(
            ProblemService problemService) {

        this.problemService = problemService;
    }

    private final ProblemProgressReportGenerator generator =
            new ProblemProgressReportGenerator();


    @PostMapping("/reports")
    public ExportResult generateReport(
            @RequestBody ReportRequest request) {

        CliOptions options = new CliOptions();

        options.setStatus(request.getStatus());
        options.setCategory(request.getCategory());
        options.setDifficulty(request.getDifficulty());
        options.setSortBy(request.getSortBy());
        options.setSortOrder(request.getSortOrder());

        List<Problem> problems =
                request.getProblems();
        if (problems == null
                || problems.isEmpty()) {
            throw new ValidationException(
                    "Problems list cannot be empty");
        }
        for (Problem problem : problems) {

            if (problem.getStatus() == null
                    || problem.getStatus().isBlank()) {

                throw new ValidationException(
                        "Problem status is required");
            }

            if (problem.getCategory() == null
                    || problem.getCategory().isBlank()) {

                throw new ValidationException(
                        "Problem category is required");
            }

            if (problem.getDifficulty() == null
                    || problem.getDifficulty().isBlank()) {

                throw new ValidationException(
                        "Problem difficulty is required");
            }
        }
            problems =
                    problemService.applyFilters(
                            problems,
                            options
                    );

            problems =
                    problemService.applySorting(
                            problems,
                            options
                    );

        ReportSummary report =
                generator.generateReport(problems);

        return new ExportResult(
                report,
                List.of(),
                problems,
                problems.size(),
                0
        );
        }
    }