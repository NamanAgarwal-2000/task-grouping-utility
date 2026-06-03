package com.naman.taskutility;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Main app = new Main();

        int exitCode = app.run(args);

        System.exit(exitCode);
    }

    public int run(String[] args) {

        if (args.length == 0) {
            System.out.println("Usage: mvn exec:java -Dexec.args=\"src/main/resources/problems.json\"");
            return 1;
        }
        CliOptions options;
        if (args[0].startsWith("--")) {

            CliArgumentParser parser =
                    new CliArgumentParser();
            try {
                options = parser.parse(args);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                return 1;
            }

        } else {

            options = new CliOptions();

            options.setInputFile(args[0]);

            if (args.length > 1) {
                options.setOutputFile(args[1]);
            }
        }
        if (options.isHelp()) {
            System.out.println("Usage:");
            System.out.println("--input <file>");
            System.out.println("--output <file>");
            System.out.println("--help");
            return 0;
        }
        if (options.getInputFile() == null) {
            System.out.println("Missing input file");
            return 1;
        }
        if (options.getOutputFile() == null) {
            System.out.println("Missing output file");
            return 1;
        }
        String filePath = options.getInputFile();

        List<Problem> problems;
        ValidationResult validationResult = null;

        try {

            if (filePath.trim().toLowerCase().endsWith(".json")) {
                ProblemJsonReader reader = new ProblemJsonReader();

                validationResult =
                        reader.readProblems(filePath);

                problems = validationResult.getValidProblems();
                problems = applyFilters(
                        problems,
                        options
                );
                problems = applySorting(
                        problems,
                        options
                );

            } else if (filePath.trim().toLowerCase().endsWith(".csv")) {

                ProblemCsvReader reader = new ProblemCsvReader();

                validationResult = reader.readProblems(filePath);

                problems = validationResult.getValidProblems();
                problems = applyFilters(
                        problems,
                        options
                );
                problems = applySorting(
                        problems,
                        options
                );

            } else {

                System.out.println("Unsupported file type");
                return 1;
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return 1;
        }

        ProblemProgressReportGenerator utility =
                new ProblemProgressReportGenerator();

        ReportSummary report =
                utility.generateReport(problems);

        if (options.getOutputFile() != null) {

            String outputPath =
                    options.getOutputFile();

            ReportJsonExporter exporter = new ReportJsonExporter();


            ExportResult exportResult =
                    new ExportResult(
                            report,
                            validationResult.getInvalidRecords(),
                            validationResult.getValidProblems().size(),
                            validationResult.getInvalidRecords().size()
                    );

            try {
                exporter.exportReport(exportResult, outputPath);
                System.out.println("Report exported to: " + outputPath);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                return 1;
            }

            System.out.println("Completed Problems: " + report.getCompletedProblems());

            System.out.println("Pending Problems: " + report.getPendingProblems());

            System.out.println();

            System.out.println("Difficulty Summary:");
            System.out.println(report.getDifficultySummary());

            System.out.println();

            System.out.println("Total Problems: " + report.getTotalProblems());

            System.out.println("Total Time Spent: " + report.getTotalTimeSpent());

            System.out.println();

            System.out.println("Grouped Result:");
            System.out.println(report.getGroupedResult());
        }

        return 0;
    }
    private List<Problem> applyFilters(
            List<Problem> problems,
            CliOptions options) {

        if (options.getStatus() != null) {

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
    private List<Problem> applySorting(
            List<Problem> problems,
            CliOptions options
    ) {

        String sortBy = options.getSortBy();
        String sortOrder = options.getSortOrder();

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
            java.util.Collections.reverse(problems);
        }

        return problems;
    }
}