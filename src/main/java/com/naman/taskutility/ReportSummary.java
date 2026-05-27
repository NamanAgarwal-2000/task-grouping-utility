package com.naman.taskutility;

import java.util.Map;

public class ReportSummary {

    private int completedProblems;
    private int pendingProblems;
    private int totalProblems;
    private String totalTimeSpent;
    private Map<String, Integer> difficultySummary;
    private Map<String, Map<String, Integer>> groupedResult;

    public ReportSummary(
            int completedProblems,
            int pendingProblems,
            int totalProblems,
            String totalTimeSpent,
            Map<String, Integer> difficultySummary,
            Map<String, Map<String, Integer>> groupedResult) {

        this.completedProblems = completedProblems;
        this.pendingProblems = pendingProblems;
        this.totalProblems = totalProblems;
        this.totalTimeSpent = totalTimeSpent;
        this.difficultySummary = difficultySummary;
        this.groupedResult = groupedResult;
    }

    public int getCompletedProblems() {
        return completedProblems;
    }

    public int getPendingProblems() {
        return pendingProblems;
    }

    public int getTotalProblems() {
        return totalProblems;
    }

    public String getTotalTimeSpent() {
        return totalTimeSpent;
    }

    public Map<String, Integer> getDifficultySummary() {
        return difficultySummary;
    }

    public Map<String, Map<String, Integer>> getGroupedResult() {
        return groupedResult;
    }
}