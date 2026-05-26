package com.naman.taskutility;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

         String filePath = args[0];
        ProblemJsonReader reader = new ProblemJsonReader();

        List<Problem> problems =
                reader.readProblems(filePath);


        ProblemProgressReportGenerator utility =
                new ProblemProgressReportGenerator();

        ReportSummary report =
                utility.generateReport(problems);

        System.out.println(report.getGroupedResult());
    }
}