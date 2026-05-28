package com.naman.taskutility;

public class InvalidRecord {

    private int sourceIndex;
    private String reason;

    public InvalidRecord(int sourceIndex, String reason) {
        this.sourceIndex = sourceIndex;
        this.reason = reason;
    }

    public int getSourceIndex() {
        return sourceIndex;
    }

    public String getReason() {
        return reason;
    }
}