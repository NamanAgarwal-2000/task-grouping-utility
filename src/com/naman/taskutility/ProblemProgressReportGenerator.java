package com.naman.taskutility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProblemProgressReportGenerator {

    public Map<String, Map<String, Integer>> generateReport(List<Problem> problems) {

        Map<String, Map<String, Integer>> groupedResult = new HashMap<>();

        if (problems == null || problems.isEmpty()) {
            return groupedResult;
        }

        for (Problem problem : problems) {

            String category = problem.getCategory();
            String status = problem.getStatus();
            String difficulty = problem.getDifficulty();
            int timeSpentMinutes = problem.getTimeSpentMinutes();

            // Handle null or blank category values
            if (category == null || category.isBlank()) {
                category = "Unknown Category";
            }

            // Handle null or blank status values
            if (status == null || status.isBlank()) {
                status = "Unknown Status";
            }

            groupedResult.putIfAbsent(category, new HashMap<>());

            Map<String, Integer> statusCounts = groupedResult.get(category);

            statusCounts.put(status,
                    statusCounts.getOrDefault(status, 0) + 1);
        }

        return groupedResult;
    }
}
