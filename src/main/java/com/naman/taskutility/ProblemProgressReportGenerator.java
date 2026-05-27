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
        int validCount = 0;

        int totalTimeSpent = 0;

        Map<String, Integer> difficultySummary = new HashMap<>();

        for (Problem problem : problems) {

            String category = problem.getCategory();
            String status = problem.getStatus();
            String difficulty = problem.getDifficulty();
            int timeSpentMinutes = problem.getTimeSpentMinutes();

            if (category == null || category.isBlank()) {
                continue;
            }


            if (status == null || status.isBlank()) {
                continue;
            }

            if (status.equalsIgnoreCase("done")) {
                status = "completed";
            }

            if (status.equalsIgnoreCase("pending")) {
                status = "pending";
            }

            if (!status.equals("completed")
                    && !status.equals("pending")) {
                continue;
            }

            if (difficulty == null || difficulty.isBlank()) {
                difficulty = "Unknown Difficulty";
            }


            if (timeSpentMinutes < 0) {
                timeSpentMinutes = 0;
            }

            validCount++;

            if ("completed".equalsIgnoreCase(status)) {
                completedCount++;
            }


            if ("pending".equalsIgnoreCase(status)) {
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


        return new ReportSummary(
                completedCount,
                pendingCount,
                validCount,
                hours + "h " + minutes + "m",
                difficultySummary,
                groupedResult
        );
    }
}