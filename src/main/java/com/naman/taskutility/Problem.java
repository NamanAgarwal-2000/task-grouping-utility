package com.naman.taskutility;

public class Problem {

    private String title;
    private String category;
    private String difficulty;
    private String status;
    private int timeSpentMinutes;

    public Problem() {

    }

    public Problem(String title,
                   String category,
                   String difficulty,
                   String status,
                   int timeSpentMinutes) {

        this.title = title;
        this.category = category;
        this.difficulty = difficulty;
        this.status = status;
        this.timeSpentMinutes = timeSpentMinutes;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getStatus() {
        return status;
    }

    public int getTimeSpentMinutes() {
        return timeSpentMinutes;
    }
}