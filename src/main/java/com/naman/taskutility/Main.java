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
                ProblemService problemService =
                        new ProblemService();

                problems = problemService.applyFilters(
                        problems,
                        options
                );

                problems = problemService.applySorting(
                        problems,
                        options
                );

            } else if (filePath.trim().toLowerCase().endsWith(".csv")) {

                ProblemCsvReader reader = new ProblemCsvReader();

                validationResult = reader.readProblems(filePath);
                problems = validationResult.getValidProblems();

                ProblemService problemService =
                        new ProblemService();

                problems = problemService.applyFilters(
                        problems,
                        options
                );

                problems = problemService.applySorting(
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
                            problems,
                            problems.size(),
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
}