package com.naman.taskutility;

import java.util.List;

public class ExportResult {

    private ReportSummary reportSummary;
    private List<InvalidRecord> invalidRecords;
    private int validCount;
    private int invalidCount;

    public ExportResult(
            ReportSummary reportSummary,
            List<InvalidRecord> invalidRecords,
            int validCount,
            int invalidCount
    ) {
        this.reportSummary = reportSummary;
        this.invalidRecords = invalidRecords;
        this.validCount = validCount;
        this.invalidCount = invalidCount;
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