package com.naman.taskutility;

import java.util.List;

public class ExportResult {

    private ReportSummary reportSummary;
    private List<InvalidRecord> invalidRecords;
    private List<Problem> problems;
    private int validCount;
    private int invalidCount;

    public ExportResult(
            ReportSummary reportSummary,
            List<InvalidRecord> invalidRecords,
            List<Problem> problems,
            int validCount,
            int invalidCount
    ) {
        this.reportSummary = reportSummary;
        this.invalidRecords = invalidRecords;
        this.problems = problems;
        this.validCount = validCount;
        this.invalidCount = invalidCount;
    }

    public List<Problem> getProblems() {
        return problems;
    }

    public ReportSummary getReportSummary() {
        return reportSummary;
    }

    public List<InvalidRecord> getInvalidRecords() {
        return invalidRecords;
    }

    public int getValidCount() {
        return validCount;
    }

    public int getInvalidCount() {
        return invalidCount;
    }
}