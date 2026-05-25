package com.naman.taskutility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args){
        List<Problem> problems = new ArrayList<>();

        for (int i = 1; i <= 100000; i++) {

            String title = "Problem " + i;

            String category;
            String difficulty;
            String status;

            if (i % 3 == 0) {
                category = "arrays";
            } else if (i % 3 == 1) {
                category = "strings";
            } else {
                category = "dp";
            }

            if (i % 3 == 0) {
                difficulty = "easy";
            } else if (i % 3 == 1) {
                difficulty = "medium";
            } else {
                difficulty = "hard";
            }

            if (i % 2 == 0) {
                status = "done";
            } else {
                status = "pending";
            }

            int timeSpentMinutes = 20 + (i % 40);

            problems.add(new Problem(
                    title,
                    category,
                    difficulty,
                    status,
                    timeSpentMinutes
            ));
        }


        System.out.println("100k tasks created");

        ProblemProgressReportGenerator utility = new ProblemProgressReportGenerator();

        Map<String, Map<String, Integer>> groupedResult = utility.generateReport(problems);

        Map<String, Integer> difficultyCount = new HashMap<>();

        for (Problem problem : problems) {

            String difficulty = problem.getDifficulty();

            if (difficulty == null || difficulty.isBlank()) {
                difficulty = "unknown";
            }

            difficultyCount.put(
                    difficulty,
                    difficultyCount.getOrDefault(difficulty, 0) + 1
            );
        }

        System.out.println("Difficulty Summary:");
        System.out.println(difficultyCount);

        System.out.println("Total Problems: " + problems.size());

        int totalTime = 0;

        for (Problem problem : problems) {
            totalTime += problem.getTimeSpentMinutes();
        }


        int hours = totalTime / 60;
        int minutes = totalTime % 60;

        System.out.println("Total Time Spent: " + hours + "h "  + minutes + "m") ;

        System.out.println("final grouped result");
        System.out.println(groupedResult);
    }
}
