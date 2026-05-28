package com.naman.taskutility;

import java.util.List;

public class ValidationResult {

    private List<Problem> validProblems;
    private List<InvalidRecord> invalidRecords;

    public ValidationResult(List<Problem> validProblems,
                            List<InvalidRecord> invalidRecords) {

        this.validProblems = validProblems;
        this.invalidRecords = invalidRecords;
    }

    public List<Problem> getValidProblems() {
        return validProblems;
    }

    public List<InvalidRecord> getInvalidRecords() {
        return invalidRecords;
    }
}
