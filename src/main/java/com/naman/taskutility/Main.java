package com.naman.taskutility;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Usage: mvn exec:java -Dexec.args=\"src/main/resources/problems.json\"");
            return;
        }
        String filePath = args[0];

        List<Problem> problems;

        if (filePath.trim().toLowerCase().endsWith(".json")) {

            ProblemJsonReader reader = new ProblemJsonReader();
            problems = reader.readProblems(filePath);

        } else if (filePath.trim().toLowerCase().endsWith(".csv")) {

            ProblemCsvReader reader = new ProblemCsvReader();
            problems = reader.readProblems(filePath);

        } else {

            System.out.println("Unsupported file type");
            return;
        }

        ProblemProgressReportGenerator utility =
                new ProblemProgressReportGenerator();

        ReportSummary report =
                utility.generateReport(problems);

        if (args.length > 1) {

            String outputPath = args[1];

            ReportJsonExporter exporter = new ReportJsonExporter();

            exporter.exportReport(report, outputPath);

            System.out.println("Report exported to: " + outputPath);
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
}