package com.naman.taskutility;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {


        ProblemJsonReader reader = new ProblemJsonReader();

        List<Problem> problems =
                reader.readProblems("src/main/resources/problems.json");


        ProblemProgressReportGenerator utility =
                new ProblemProgressReportGenerator();

        Map<String, Map<String, Integer>> result =
                utility.generateReport(problems);


        System.out.println("final grouped result");

        System.out.println(result);
    }
}