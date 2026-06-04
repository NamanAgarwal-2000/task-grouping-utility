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
    public ReportSummary generateReport(
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

        return generator.generateReport(
                problems
        );
    }
}