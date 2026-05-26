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

        ProblemJsonReader reader = new ProblemJsonReader();

        List<Problem> problems =
                reader.readProblems(filePath);


        ProblemProgressReportGenerator utility =
                new ProblemProgressReportGenerator();

        ReportSummary report =
                utility.generateReport(problems);

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