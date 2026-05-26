package com.naman.taskutility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProblemProgressReportGenerator {

    public ReportSummary generateReport(List<Problem> problems) {

        Map<String, Map<String, Integer>> groupedResult = new HashMap<>();

        if (problems == null || problems.isEmpty()) {
            return new ReportSummary(
                    0,
                    0,
                    0,
                    "0h 0m",
                    new HashMap<>(),
                    groupedResult
            );
        }

        int completedCount = 0;
        int pendingCount = 0;

        int totalTimeSpent = 0;

        Map<String, Integer> difficultySummary = new HashMap<>();

        for (Problem problem : problems) {

            String category = problem.getCategory();
            String status = problem.getStatus();
            String difficulty = problem.getDifficulty();
            int timeSpentMinutes = problem.getTimeSpentMinutes();

            if (category == null || category.isBlank()) {
                category = "Unknown Category";
            }


            if (status == null || status.isBlank()) {
                status = "Unknown Status";
            }

            if (difficulty == null || difficulty.isBlank()) {
                difficulty = "Unknown Difficulty";
            }


            if (timeSpentMinutes < 0) {
                timeSpentMinutes = 0;
            }


            if ("Completed".equalsIgnoreCase(status)) {
                completedCount++;
            }


            if ("Pending".equalsIgnoreCase(status)) {
                pendingCount++;
            }


            totalTimeSpent += timeSpentMinutes;


            difficultySummary.put(
                    difficulty,
                    difficultySummary.getOrDefault(difficulty, 0) + 1
            );

            groupedResult.putIfAbsent(category, new HashMap<>());

            Map<String, Integer> statusCounts = groupedResult.get(category);

            statusCounts.put(
                    status,
                    statusCounts.getOrDefault(status, 0) + 1
            );
        }

        int hours = totalTimeSpent / 60;
        int minutes = totalTimeSpent % 60;


        System.out.println("Completed Problems: " + completedCount);

        System.out.println("Pending Problems: " + pendingCount);

        System.out.println("Difficulty Summary:");
        System.out.println(difficultySummary);

        System.out.println("Total Problems: " + problems.size());

        System.out.println(
                "Total Time Spent: " + hours + "h " + minutes + "m"
        );

        return new ReportSummary(
                completedCount,
                pendingCount,
                problems.size(),
                hours + "h " + minutes + "m",
                difficultySummary,
                groupedResult
        );
    }
}